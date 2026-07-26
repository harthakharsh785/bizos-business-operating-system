package com.bizos.dto;

import jakarta.validation.constraints.*;

public class RegisterRequest {

    @NotBlank(message = "Organization name is required")
    private String organizationName;

    private String businessType;

    @NotBlank(message = "Full name is required")
    private String fullName;

    @NotBlank @Email(message = "Valid email is required")
    private String email;

    @NotBlank @Size(min = 6, message = "Password must be at least 6 characters")
    private String password;

    public String getOrganizationName() { return organizationName; }
    public void setOrganizationName(String organizationName) { this.organizationName = organizationName; }

    public String getBusinessType() { return businessType; }
    public void setBusinessType(String businessType) { this.businessType = businessType; }

    public String getFullName() { return fullName; }
    public void setFullName(String fullName) { this.fullName = fullName; }

    public String getEmail() { return email; }
    public void setEmail(String email) { this.email = email; }

    public String getPassword() { return password; }
    public void setPassword(String password) { this.password = password; }
}
