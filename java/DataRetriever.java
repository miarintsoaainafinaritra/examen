package dao;

import java.sql.*;
import model.Order;
import model.Sale;

public class DataRetriever {
    
    public Order findOrderByReference(String reference) {
        String sql = "SELECT * FROM `Order` WHERE reference = ?";
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setString(1, reference);
            ResultSet rs = pstmt.executeQuery();
            if (rs.next()) {
                Order order = new Order();
                order.setId(rs.getInt("id"));
                order.setReference(rs.getString("reference"));
                order.setCustomerName(rs.getString("customer_name"));
                order.setTotalAmount(rs.getDouble("total_amount"));
                order.setPaymentStatus(Order.PaymentStatus.valueOf(rs.getString("payment_status")));
                return order;
            }
        } catch (SQLException e) {
            System.err.println("Erreur recherche commande: " + e.getMessage());
        }
        return null;
    }
    
    public Order saveOrder(Order order) {
        if (order.getId() == null) {
            return insertOrder(order);
        } else {
            return updateOrder(order);
        }
    }
    
    private Order insertOrder(Order order) {
        String sql = "INSERT INTO `Order` (reference, customer_name, total_amount, payment_status) VALUES (?, ?, ?, ?)";
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {
            pstmt.setString(1, order.getReference());
            pstmt.setString(2, order.getCustomerName());
            pstmt.setDouble(3, order.getTotalAmount());
            pstmt.setString(4, order.getPaymentStatus().name());
            pstmt.executeUpdate();
            
            ResultSet rs = pstmt.getGeneratedKeys();
            if (rs.next()) {
                order.setId(rs.getInt(1));
            }
            return order;
        } catch (SQLException e) {
            System.err.println("Erreur insertion commande: " + e.getMessage());
        }
        return null;
    }
    
    private Order updateOrder(Order order) {
        Order existing = findOrderByReference(order.getReference());
        if (existing != null && existing.getPaymentStatus() == Order.PaymentStatus.PAID) {
            throw new IllegalStateException("Commande déjà payée, modification impossible");
        }
        
        String sql = "UPDATE `Order` SET customer_name = ?, total_amount = ?, payment_status = ? WHERE id = ?";
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setString(1, order.getCustomerName());
            pstmt.setDouble(2, order.getTotalAmount());
            pstmt.setString(3, order.getPaymentStatus().name());
            pstmt.setInt(4, order.getId());
            pstmt.executeUpdate();
            return order;
        } catch (SQLException e) {
            System.err.println("Erreur mise à jour commande: " + e.getMessage());
        }
        return null;
    }
    
    public Sale createSaleFrom(Order order) {
        if (order.getPaymentStatus() != Order.PaymentStatus.PAID) {
            throw new IllegalStateException("Vente impossible : commande non payée");
        }
        
        if (saleExistsForOrder(order.getId())) {
            throw new IllegalStateException("Vente impossible : commande déjà vendue");
        }
        
        String sql = "INSERT INTO Sale (order_id) VALUES (?)";
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {
            pstmt.setInt(1, order.getId());
            pstmt.executeUpdate();
            
            Sale sale = new Sale();
            sale.setOrder(order);
            
            ResultSet rs = pstmt.getGeneratedKeys();
            if (rs.next()) {
                sale.setId(rs.getInt(1));
            }
            return sale;
        } catch (SQLException e) {
            throw new IllegalStateException("Erreur création vente: " + e.getMessage());
        }
    }
    
    private boolean saleExistsForOrder(Integer orderId) {
        String sql = "SELECT COUNT(*) FROM Sale WHERE order_id = ?";
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setInt(1, orderId);
            ResultSet rs = pstmt.executeQuery();
            if (rs.next()) {
                return rs.getInt(1) > 0;
            }
        } catch (SQLException e) {
            System.err.println("Erreur vérification vente: " + e.getMessage());
        }
        return false;
    }
}