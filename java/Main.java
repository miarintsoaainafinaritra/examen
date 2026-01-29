import dao.DataRetriever;
import dao.DBConnection;
import model.Order;
import model.Sale;

public class Main {
    public static void main(String[] args) {
        DBConnection.initDatabase();
        DataRetriever data = new DataRetriever();
        
        Order order = new Order();
        order.setReference("CMD-001");
        order.setCustomerName("Client Test");
        order.setTotalAmount(50.0);
        order.setPaymentStatus(Order.PaymentStatus.PAID);
        
        data.saveOrder(order);
        
        try {
            Sale sale = data.createSaleFrom(order);
            System.out.println("Vente créée: " + sale.getId());
        } catch (Exception e) {
            System.out.println("Erreur: " + e.getMessage());
        }
    }
}