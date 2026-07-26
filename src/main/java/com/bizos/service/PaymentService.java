package com.bizos.service;

import com.bizos.dto.PaymentRequest;
import com.bizos.entity.Invoice;
import com.bizos.entity.Payment;
import com.bizos.repository.PaymentRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class PaymentService {

    private final PaymentRepository paymentRepository;
    private final InvoiceService invoiceService;

    public PaymentService(PaymentRepository paymentRepository, InvoiceService invoiceService) {
        this.paymentRepository = paymentRepository;
        this.invoiceService = invoiceService;
    }

    public List<Payment> getByInvoice(Long invoiceId) {
        invoiceService.getById(invoiceId);
        return paymentRepository.findByInvoiceId(invoiceId);
    }

    public Payment recordPayment(Long invoiceId, PaymentRequest request) {
        Invoice invoice = invoiceService.getById(invoiceId);

        Payment payment = Payment.builder()
                .invoice(invoice)
                .amount(request.getAmount())
                .method(request.getMethod())
                .build();
        payment = paymentRepository.save(payment);

        invoiceService.applyPayment(invoice, request.getAmount());

        return payment;
    }
}
