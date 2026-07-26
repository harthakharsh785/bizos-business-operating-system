# BizOS — Business Operating System (MVP)

> "One Platform to Run Your Entire Business" — AI-powered SaaS backend for small businesses (retail shops, clinics, gyms, coaching centers, etc.) to manage customers, invoices, payments and automated reminders from one place.

## Tech Stack
- **Java 21**, **Spring Boot 3.3**
- **Spring Security + JWT** (stateless auth)
- **Spring Data JPA / Hibernate**
- **H2** (in-memory, zero setup) — switchable to **MySQL** for production
- **Springdoc OpenAPI / Swagger UI**
- **Maven** (plain Java — no Lombok, so it runs in any IDE with zero extra setup)

## Architecture
Multi-tenant SaaS: every business = one `Organization`. Every `User`, `Customer`, `Invoice` belongs to an organization, and every API call is automatically scoped to the logged-in user's organization via the JWT claims — so two businesses using the platform can never see each other's data.

```
com.bizos
 ├── config        → Security & OpenAPI configuration
 ├── controller     → REST endpoints
 ├── service        → Business logic (tenant-scoped)
 ├── repository     → Spring Data JPA repositories
 ├── entity         → Organization, User, Customer, Invoice, Payment
 ├── dto            → Request/response objects
 ├── security       → JWT filter, JWT util, current-user helper
 ├── scheduler      → Automated overdue-invoice reminder job
 └── exception      → Centralized error handling
```

## Features (MVP)
- ✅ Business registration (creates Organization + Admin user)
- ✅ JWT login/auth, role-based access (`ADMIN` / `STAFF`)
- ✅ Multi-tenant data isolation
- ✅ Customer CRUD
- ✅ Invoice creation & tracking (`PENDING` → `PARTIALLY_PAID` → `PAID` / `OVERDUE`)
- ✅ Payment recording against invoices
- ✅ Daily scheduled job that auto-flags overdue invoices (stand-in for WhatsApp/SMS reminders)
- ✅ Swagger UI for live API docs/testing
- ✅ Global exception handling with clean JSON errors

## Roadmap (not built yet — planned)
- 🔲 Real WhatsApp/SMS/Email reminder integration
- 🔲 AI Assistant (chat-based business insights)
- 🔲 Inventory & Expense modules
- 🔲 Reports & Analytics dashboard
- 🔲 React frontend

## Running Locally (STS / Spring Tool Suite)
1. **Import** → File → Import → Maven → Existing Maven Projects → select this folder.
2. Wait for Maven to download dependencies.
3. Run `BizosApplication.java` as **Spring Boot App** (right-click → Run As).
4. App starts on `http://localhost:8080` using an in-memory H2 database (no setup needed).
5. Open Swagger UI: **http://localhost:8080/swagger-ui.html**
6. (Optional) H2 console: **http://localhost:8080/h2-console** (JDBC URL: `jdbc:h2:mem:bizosdb`)

To use MySQL instead, uncomment the MySQL block in `application.properties` and comment out the H2 block.

## Quick API Walkthrough

**1. Register a business**
```
POST /api/auth/register
{
  "organizationName": "Sharma Retail Shop",
  "businessType": "Retail",
  "fullName": "Rahul Sharma",
  "email": "rahul@shop.com",
  "password": "password123"
}
```
Returns a JWT token — copy it and click **Authorize** in Swagger UI (`Bearer <token>`).

**2. Add a customer**
```
POST /api/customers
{ "name": "Neha Kumari", "phone": "9876543210" }
```

**3. Create an invoice**
```
POST /api/invoices
{ "customerId": 1, "amount": 1200, "dueDate": "2026-08-01" }
```

**4. Record a payment**
```
POST /api/invoices/1/payments
{ "amount": 500, "method": "UPI" }
```

**5. View pending invoices**
```
GET /api/invoices/pending
```

## Why this project
Built to demonstrate real-world Spring Boot skills beyond CRUD: JWT auth, multi-tenancy, layered architecture, scheduled jobs, and clean API design — modeled as a genuine SaaS startup idea for small businesses in India.
