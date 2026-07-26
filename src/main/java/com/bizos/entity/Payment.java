package com.bizos.entity;

import jakarta.persistence.*;
import java.math.BigDecimal;
import java.time.LocalDateTime;

@Entity
@Table(name = "payments")
public class Payment {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "invoice_id", nullable = false)
    private Invoice invoice;

    @Column(nullable = false)
    private BigDecimal amount;

    private String method;

    private LocalDateTime paidAt = LocalDateTime.now();

    public Payment() {
    }

    public Payment(Long id, Invoice invoice, BigDecimal amount, String method, LocalDateTime paidAt) {
        this.id = id;
        this.invoice = invoice;
        this.amount = amount;
        this.method = method;
        this.paidAt = paidAt;
    }

    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public Invoice getInvoice() { return invoice; }
    public void setInvoice(Invoice invoice) { this.invoice = invoice; }

    public BigDecimal getAmount() { return amount; }
    public void setAmount(BigDecimal amount) { this.amount = amount; }

    public String getMethod() { return method; }
    public void setMethod(String method) { this.method = method; }

    public LocalDateTime getPaidAt() { return paidAt; }
    public void setPaidAt(LocalDateTime paidAt) { this.paidAt = paidAt; }

    public static PaymentBuilder builder() {
        return new PaymentBuilder();
    }

    public static class PaymentBuilder {
        private Invoice invoice;
        private BigDecimal amount;
        private String method;

        public PaymentBuilder invoice(Invoice invoice) { this.invoice = invoice; return this; }
        public PaymentBuilder amount(BigDecimal amount) { this.amount = amount; return this; }
        public PaymentBuilder method(String method) { this.method = method; return this; }

        public Payment build() {
            return new Payment(null, invoice, amount, method, LocalDateTime.now());
        }
    }
}
