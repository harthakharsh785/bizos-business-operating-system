package com.bizos.repository;

import com.bizos.entity.Customer;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface CustomerRepository extends JpaRepository<Customer, Long> {
    List<Customer> findByOrganizationId(Long organizationId);
    boolean existsByIdAndOrganizationId(Long id, Long organizationId);
}
