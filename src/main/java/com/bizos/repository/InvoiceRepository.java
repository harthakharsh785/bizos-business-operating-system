package com.bizos.repository;

import com.bizos.entity.Invoice;
import com.bizos.entity.InvoiceStatus;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDate;
import java.util.List;

public interface InvoiceRepository extends JpaRepository<Invoice, Long> {
    List<Invoice> findByOrganizationId(Long organizationId);
    List<Invoice> findByOrganizationIdAndStatus(Long organizationId, InvoiceStatus status);
    List<Invoice> findByStatusNotAndDueDateBefore(InvoiceStatus status, LocalDate date);
}
