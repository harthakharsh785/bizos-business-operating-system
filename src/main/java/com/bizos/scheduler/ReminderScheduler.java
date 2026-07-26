package com.bizos.scheduler;

import com.bizos.entity.Invoice;
import com.bizos.entity.InvoiceStatus;
import com.bizos.repository.InvoiceRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.time.LocalDate;
import java.util.List;

/**
 * Runs daily and flags overdue invoices.
 * In a full version this would trigger real WhatsApp/SMS/Email sends —
 * here it just logs, which is enough to demo the "automated reminders" concept.
 */
@Component
public class ReminderScheduler {

    private static final Logger log = LoggerFactory.getLogger(ReminderScheduler.class);

    private final InvoiceRepository invoiceRepository;

    public ReminderScheduler(InvoiceRepository invoiceRepository) {
        this.invoiceRepository = invoiceRepository;
    }

    // Runs every day at 9 AM.
    @Scheduled(cron = "0 0 9 * * *")
    public void flagOverdueInvoices() {
        List<Invoice> overdue = invoiceRepository.findByStatusNotAndDueDateBefore(InvoiceStatus.PAID, LocalDate.now());

        for (Invoice invoice : overdue) {
            invoice.setStatus(InvoiceStatus.OVERDUE);
            invoiceRepository.save(invoice);
            log.info("[REMINDER] Invoice #{} for customer '{}' is overdue. Amount pending: {}",
                    invoice.getId(),
                    invoice.getCustomer().getName(),
                    invoice.getAmount().subtract(invoice.getAmountPaid()));
        }

        if (!overdue.isEmpty()) {
            log.info("[REMINDER] Sent {} overdue payment reminders.", overdue.size());
        }
    }
}
