package com.bizos.service;

import com.bizos.dto.InvoiceRequest;
import com.bizos.entity.Customer;
import com.bizos.entity.Invoice;
import com.bizos.entity.InvoiceStatus;
import com.bizos.exception.ResourceNotFoundException;
import com.bizos.repository.InvoiceRepository;
import com.bizos.security.CurrentUser;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.List;

@Service
public class InvoiceService {

    private final InvoiceRepository invoiceRepository;
    private final CustomerService customerService;
    private final CurrentUser currentUser;

    public InvoiceService(InvoiceRepository invoiceRepository, CustomerService customerService, CurrentUser currentUser) {
        this.invoiceRepository = invoiceRepository;
        this.customerService = customerService;
        this.currentUser = currentUser;
    }

    public List<Invoice> getAll() {
        return invoiceRepository.findByOrganizationId(currentUser.organizationId());
    }

    public Invoice getById(Long id) {
        Invoice invoice = invoiceRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Invoice not found with id: " + id));
        assertSameTenant(invoice);
        return invoice;
    }

    public List<Invoice> getPending() {
        return invoiceRepository.findByOrganizationIdAndStatus(currentUser.organizationId(), InvoiceStatus.PENDING);
    }

    public Invoice create(InvoiceRequest request) {
        Customer customer = customerService.getById(request.getCustomerId());

        Invoice invoice = Invoice.builder()
                .customer(customer)
                .organization(currentUser.get().getOrganization())
                .amount(request.getAmount())
                .dueDate(request.getDueDate())
                .status(InvoiceStatus.PENDING)
                .build();

        return invoiceRepository.save(invoice);
    }

    public Invoice applyPayment(Invoice invoice, BigDecimal amount) {
        BigDecimal newPaidAmount = invoice.getAmountPaid().add(amount);
        invoice.setAmountPaid(newPaidAmount);

        if (newPaidAmount.compareTo(invoice.getAmount()) >= 0) {
            invoice.setStatus(InvoiceStatus.PAID);
        } else {
            invoice.setStatus(InvoiceStatus.PARTIALLY_PAID);
        }

        return invoiceRepository.save(invoice);
    }

    private void assertSameTenant(Invoice invoice) {
        if (!invoice.getOrganization().getId().equals(currentUser.organizationId())) {
            throw new ResourceNotFoundException("Invoice not found in your organization");
        }
    }
}
