package com.bizos.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;

@Entity
@Table(name = "invoices")
public class Invoice {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "customer_id", nullable = false)
    private Customer customer;

    @JsonIgnore
    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "organization_id", nullable = false)
    private Organization organization;

    @Column(nullable = false)
    private BigDecimal amount;

    private BigDecimal amountPaid = BigDecimal.ZERO;

    private LocalDate dueDate;

    @Enumerated(EnumType.STRING)
    private InvoiceStatus status = InvoiceStatus.PENDING;

    private LocalDateTime createdAt = LocalDateTime.now();

    public Invoice() {
    }

    public Invoice(Long id, Customer customer, Organization organization, BigDecimal amount,
                    BigDecimal amountPaid, LocalDate dueDate, InvoiceStatus status, LocalDateTime createdAt) {
        this.id = id;
        this.customer = customer;
        this.organization = organization;
        this.amount = amount;
        this.amountPaid = amountPaid;
        this.dueDate = dueDate;
        this.status = status;
        this.createdAt = createdAt;
    }

    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public Customer getCustomer() { return customer; }
    public void setCustomer(Customer customer) { this.customer = customer; }

    public Organization getOrganization() { return organization; }
    public void setOrganization(Organization organization) { this.organization = organization; }

    public BigDecimal getAmount() { return amount; }
    public void setAmount(BigDecimal amount) { this.amount = amount; }

    public BigDecimal getAmountPaid() { return amountPaid; }
    public void setAmountPaid(BigDecimal amountPaid) { this.amountPaid = amountPaid; }

    public LocalDate getDueDate() { return dueDate; }
    public void setDueDate(LocalDate dueDate) { this.dueDate = dueDate; }

    public InvoiceStatus getStatus() { return status; }
    public void setStatus(InvoiceStatus status) { this.status = status; }

    public LocalDateTime getCreatedAt() { return createdAt; }
    public void setCreatedAt(LocalDateTime createdAt) { this.createdAt = createdAt; }

    public static InvoiceBuilder builder() {
        return new InvoiceBuilder();
    }

    public static class InvoiceBuilder {
        private Customer customer;
        private Organization organization;
        private BigDecimal amount;
        private LocalDate dueDate;
        private InvoiceStatus status = InvoiceStatus.PENDING;

        public InvoiceBuilder customer(Customer customer) { this.customer = customer; return this; }
        public InvoiceBuilder organization(Organization organization) { this.organization = organization; return this; }
        public InvoiceBuilder amount(BigDecimal amount) { this.amount = amount; return this; }
        public InvoiceBuilder dueDate(LocalDate dueDate) { this.dueDate = dueDate; return this; }
        public InvoiceBuilder status(InvoiceStatus status) { this.status = status; return this; }

        public Invoice build() {
            return new Invoice(null, customer, organization, amount, BigDecimal.ZERO, dueDate, status, LocalDateTime.now());
        }
    }
}
