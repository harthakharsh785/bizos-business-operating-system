package com.bizos.controller;

import com.bizos.dto.PaymentRequest;
import com.bizos.entity.Payment;
import com.bizos.service.PaymentService;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/invoices/{invoiceId}/payments")
@Tag(name = "Payments", description = "Record payments against an invoice")
public class PaymentController {

    private final PaymentService paymentService;

    public PaymentController(PaymentService paymentService) {
        this.paymentService = paymentService;
    }

    @GetMapping
    public ResponseEntity<List<Payment>> getByInvoice(@PathVariable Long invoiceId) {
        return ResponseEntity.ok(paymentService.getByInvoice(invoiceId));
    }

    @PostMapping
    public ResponseEntity<Payment> recordPayment(@PathVariable Long invoiceId,
                                                   @Valid @RequestBody PaymentRequest request) {
        return ResponseEntity.status(HttpStatus.CREATED).body(paymentService.recordPayment(invoiceId, request));
    }
}
