package model;

public class Order {
    private Integer id;
    private String reference;
    private String customerName;
    private Double totalAmount;
    private PaymentStatus paymentStatus;
    
    public enum PaymentStatus {
        UNPAID, PAID
    }
    
    public Order() {
        this.paymentStatus = PaymentStatus.UNPAID;
    }
    
    public Integer getId() { return id; }
    public void setId(Integer id) { this.id = id; }
    public String getReference() { return reference; }
    public void setReference(String reference) { this.reference = reference; }
    public String getCustomerName() { return customerName; }
    public void setCustomerName(String customerName) { this.customerName = customerName; }
    public Double getTotalAmount() { return totalAmount; }
    public void setTotalAmount(Double totalAmount) { this.totalAmount = totalAmount; }
    public PaymentStatus getPaymentStatus() { return paymentStatus; }
    public void setPaymentStatus(PaymentStatus paymentStatus) { this.paymentStatus = paymentStatus; }

    @Override
    public String toString() {
        return "Order{" +
                "id=" + id +
                ", reference='" + reference + '\'' +
                ", customerName='" + customerName + '\'' +
                ", totalAmount=" + totalAmount +
                ", paymentStatus=" + paymentStatus +
                '}';
    }

}