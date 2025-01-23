# **Ayuda - Digital Wallet for FIAT & Crypto**
🚀 *Work in Progress*

## **📌 Overview**
Ayuda is a **modern digital wallet** that enables users to securely store, transfer, and manage both **FIAT** and **crypto assets (Solana tokens)**. The platform also provides users with **virtual and physical cards**, ensuring a seamless transaction experience.

### **Key Features**
- 💰 **E-wallet** for FIAT and Solana tokens
- 🔄 **Send & Receive Money** within the platform
- 🏦 **Deposit & Withdraw Funds** via bank transfers & crypto wallets
- 💳 **Virtual & Physical Cards** for online/offline payments
- ⚡ **Secure Authentication** using JWT & Bcrypt for password hashing
- 🔒 **Role-Based Access Control (RBAC)** for secure transactions
- 🔔 **In-App & Email Notifications**
- 🚀 **Microservices Architecture** for scalability

---

## **🛠️ Tech Stack**

### **Backend Services**
| Service | Technologies | Description |
|---------|-------------|-------------|
| **API Gateway** | Java, Spring Cloud Gateway | Routes requests, authentication, load balancing |
| **Service Discovery** | Spring Cloud Eureka | Registers and discovers microservices |
| **Config Server** | Spring Cloud Config | Manages centralized configurations |
| **User Service** | Java, Spring Boot, PostgreSQL, Redis | Manages user data & profiles |
| **Authentication Service** | Java, Spring Boot, Bcrypt, JWT | Handles authentication & authorization |
| **Wallet Service** | Java, Spring Boot, PostgreSQL, RabbitMQ | Manages wallets, transactions & balances |
| **Transaction Service** | Java, Spring Boot, PostgreSQL | Handles all transactions (FIAT & Crypto) |
| **Payment Service** | Java, Spring Boot | Processes payments (bank, crypto, card funding) |
| **Card Service** | Java, Spring Boot | Manages virtual & physical cards |
| **Web3 Service** | Rust, Solana SDK | Manages crypto transactions (Solana tokens) |
| **Notification Service** | Java, Spring Boot, RabbitMQ, Firebase | Sends push/email notifications |

---

## **📌 Ayuda Feature Checklist**

### **E-Wallet Microservices Development Progress**

✅ - Completed | 🚧 - In Progress | ⏳ - Pending

#### **1. API Gateway** ✅
- [x] Route requests to appropriate microservices
- [x] Implement authentication & authorization (JWT/OAuth2)
- [x] Load balancing with service discovery
- [x] Logging and monitoring

#### **2. Service Discovery** ✅
- [x] Register and discover microservices
- [x] Enable dynamic scaling

#### **3. Config Server** ✅
- [x] Centralized configuration management
- [x] Secure storage for environment variables
- [x] Support dynamic configuration updates

#### **4. User Service** ✅
- [x] User registration
- [x] Profile management
- [x] Role-based access control

#### **5. Wallet Service** ✅
- [x] Create, update, delete wallets
- [x] Manage both FIAT and Solana wallets
- [x] Track wallet balances & transactions
- [x] Fund and withdraw money

#### **6. Transaction Service** ✅
- [x] Peer-to-peer transactions (Internal users)
- [x] Withdraw funds to external bank accounts or crypto wallets
- [x] Transaction history & reconciliation

#### **7. Payment Service** ✅
- [x] Process FIAT payments
- [x] Handle refunds and chargebacks
- [x] Process payment webhooks
- [x] Payment authorization and settlement

#### **8. Card Service** ⏳
- [ ] Create virtual cards linked to wallets
- [ ] Issue physical cards
- [ ] Fund cards from wallets
- [ ] Process transactions using cards
- [ ] Freeze, unfreeze, and delete cards
- [ ] Secure PIN management

#### **9. Web3 Service (Rust)** 🚧
- [ ] Generate Solana wallet addresses
- [ ] Process token purchases using FIAT
- [ ] Synchronize Solana balances with Wallet Service
- [ ] Handle token transfers between users

#### **10. Notification Service** ⏳
- [ ] Send email notifications
- [ ] Send in-app notifications

#### **11. Authentication Service** ✅
- [x] Account creation
- [x] Login
- [x] JWT token issuing

---

## **🔧 Setup Instructions**

### **1️⃣ Prerequisites**
Ensure you have the following installed:
- [JDK 17+](https://adoptium.net/)
- [PostgreSQL](https://www.postgresql.org/)
- [Redis](https://redis.io/)
- [RabbitMQ](https://www.rabbitmq.com/)
- [Docker](https://www.docker.com/) (optional)

---

### **2️⃣ Clone the Repository**
```sh
git clone https://github.com/your-username/ayuda.git
cd ayuda
```

---

### **3️⃣ Configuration**

#### **🔑 Environment Variables**
Each microservice requires environment variables to be properly configured. The **recommended approach** is to use **environment variables** rather than `.env` files in production.

Set environment variables for your services:
```sh
export CLOUDINARY_CLOUD_NAME="your-cloudinary-cloud-name"
export CLOUDINARY_API_KEY="your-cloudinary-api-key"
export CLOUDINARY_API_SECRET="your-cloudinary-api-secret"
export JWT_SECRET="your-secret-key"
```

Alternatively, if using `.env` for **development**, create a `.env` file inside each service:
```
CLOUDINARY_CLOUD_NAME=your-cloudinary-cloud-name
CLOUDINARY_API_KEY=your-cloudinary-api-key
CLOUDINARY_API_SECRET=your-cloudinary-api-secret
JWT_SECRET=your-secret-key
```

---

### **4️⃣ Running Services Locally**
Each microservice can be run separately.

#### **Run User Service**
```sh
cd user-service
mvn spring-boot:run
```

#### **Run Authentication Service**
```sh
cd authentication-service
mvn spring-boot:run
```

#### **Run Wallet Service**
```sh
cd wallet-service
mvn spring-boot:run
```

To **run all services together**, use **Docker Compose**:
```sh
docker-compose up -d
```

---

## **⚙️ Architecture**
### **Microservices Communication**
- **Synchronous Communication**: Services communicate via **Feign Clients** (HTTP REST).
- **Asynchronous Communication**: Services use **RabbitMQ** for event-driven interactions.

### **Security**
- **JWT-Based Authentication**
- **Bcrypt Password Hashing**
- **Role-Based Access Control (RBAC)**

---

## **📜 Contributing**
We welcome contributions! Please follow these steps:
1. Fork the repository
2. Create a feature branch (`git checkout -b feature-branch`)
3. Commit your changes (`git commit -m "Added feature"`)
4. Push to the branch (`git push origin feature-branch`)
5. Open a Pull Request 🚀

---

## **💬 Contact**
For any issues, feel free to open a GitHub **Issue** or reach out:  
📩 **Email**: [boluadetifa@gmail.com](mailto:boluadetifa@gmail.com)  
🌐 **Website**: [boluwatife.dev](https://boluwatife.dev)

---

## **📜 License**
This project is licensed under the **MIT License**.
