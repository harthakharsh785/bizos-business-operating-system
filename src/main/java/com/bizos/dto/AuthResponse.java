package com.bizos.dto;

public class AuthResponse {
    private String token;
    private String email;
    private String fullName;
    private String role;
    private Long organizationId;
    private String organizationName;

    public AuthResponse() {
    }

    public AuthResponse(String token, String email, String fullName, String role,
                         Long organizationId, String organizationName) {
        this.token = token;
        this.email = email;
        this.fullName = fullName;
        this.role = role;
        this.organizationId = organizationId;
        this.organizationName = organizationName;
    }

    public String getToken() { return token; }
    public void setToken(String token) { this.token = token; }

    public String getEmail() { return email; }
    public void setEmail(String email) { this.email = email; }

    public String getFullName() { return fullName; }
    public void setFullName(String fullName) { this.fullName = fullName; }

    public String getRole() { return role; }
    public void setRole(String role) { this.role = role; }

    public Long getOrganizationId() { return organizationId; }
    public void setOrganizationId(Long organizationId) { this.organizationId = organizationId; }

    public String getOrganizationName() { return organizationName; }
    public void setOrganizationName(String organizationName) { this.organizationName = organizationName; }

    public static AuthResponseBuilder builder() {
        return new AuthResponseBuilder();
    }

    public static class AuthResponseBuilder {
        private String token;
        private String email;
        private String fullName;
        private String role;
        private Long organizationId;
        private String organizationName;

        public AuthResponseBuilder token(String token) { this.token = token; return this; }
        public AuthResponseBuilder email(String email) { this.email = email; return this; }
        public AuthResponseBuilder fullName(String fullName) { this.fullName = fullName; return this; }
        public AuthResponseBuilder role(String role) { this.role = role; return this; }
        public AuthResponseBuilder organizationId(Long organizationId) { this.organizationId = organizationId; return this; }
        public AuthResponseBuilder organizationName(String organizationName) { this.organizationName = organizationName; return this; }

        public AuthResponse build() {
            return new AuthResponse(token, email, fullName, role, organizationId, organizationName);
        }
    }
}
