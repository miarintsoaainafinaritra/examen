package model;

import java.time.LocalDateTime;

public class Sale {
    private Integer id;
    private Order order;
    private LocalDateTime saleDate;
    
    public Sale() {
        this.saleDate = LocalDateTime.now();
    }
    
    public Integer getId() { return id; }
    public void setId(Integer id) { this.id = id; }
    public Order getOrder() { return order; }
    public void setOrder(Order order) { this.order = order; }
    public LocalDateTime getSaleDate() { return saleDate; }
    public void setSaleDate(LocalDateTime saleDate) { this.saleDate = saleDate; }
}