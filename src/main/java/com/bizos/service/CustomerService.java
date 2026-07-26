package com.bizos.service;

import com.bizos.dto.CustomerRequest;
import com.bizos.entity.Customer;
import com.bizos.entity.Organization;
import com.bizos.exception.ResourceNotFoundException;
import com.bizos.repository.CustomerRepository;
import com.bizos.security.CurrentUser;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CustomerService {

    private final CustomerRepository customerRepository;
    private final CurrentUser currentUser;

    public CustomerService(CustomerRepository customerRepository, CurrentUser currentUser) {
        this.customerRepository = customerRepository;
        this.currentUser = currentUser;
    }

    public List<Customer> getAll() {
        return customerRepository.findByOrganizationId(currentUser.organizationId());
    }

    public Customer getById(Long id) {
        Customer customer = customerRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Customer not found with id: " + id));
        assertSameTenant(customer);
        return customer;
    }

    public Customer create(CustomerRequest request) {
        Organization organization = currentUser.get().getOrganization();

        Customer customer = Customer.builder()
                .name(request.getName())
                .phone(request.getPhone())
                .email(request.getEmail())
                .address(request.getAddress())
                .organization(organization)
                .build();

        return customerRepository.save(customer);
    }

    public Customer update(Long id, CustomerRequest request) {
        Customer customer = getById(id);
        customer.setName(request.getName());
        customer.setPhone(request.getPhone());
        customer.setEmail(request.getEmail());
        customer.setAddress(request.getAddress());
        return customerRepository.save(customer);
    }

    public void delete(Long id) {
        Customer customer = getById(id);
        customerRepository.delete(customer);
    }

    private void assertSameTenant(Customer customer) {
        if (!customer.getOrganization().getId().equals(currentUser.organizationId())) {
            throw new ResourceNotFoundException("Customer not found in your organization");
        }
    }
}
