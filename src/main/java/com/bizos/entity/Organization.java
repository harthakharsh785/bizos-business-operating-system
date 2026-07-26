package com.bizos.entity;

import jakarta.persistence.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "organizations")
public class Organization {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, unique = true)
    private String name;

    private String businessType;

    private LocalDateTime createdAt = LocalDateTime.now();

    public Organization() {
    }

    public Organization(Long id, String name, String businessType, LocalDateTime createdAt) {
        this.id = id;
        this.name = name;
        this.businessType = businessType;
        this.createdAt = createdAt;
    }

    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public String getName() { return name; }
    public void setName(String name) { this.name = name; }

    public String getBusinessType() { return businessType; }
    public void setBusinessType(String businessType) { this.businessType = businessType; }

    public LocalDateTime getCreatedAt() { return createdAt; }
    public void setCreatedAt(LocalDateTime createdAt) { this.createdAt = createdAt; }

    public static OrganizationBuilder builder() {
        return new OrganizationBuilder();
    }

    public static class OrganizationBuilder {
        private String name;
        private String businessType;

        public OrganizationBuilder name(String name) { this.name = name; return this; }
        public OrganizationBuilder businessType(String businessType) { this.businessType = businessType; return this; }

        public Organization build() {
            return new Organization(null, name, businessType, LocalDateTime.now());
        }
    }
}
