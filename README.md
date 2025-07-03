### ✅ `README.md`


# 🧠 Code Analyzer

[![Java](https://img.shields.io/badge/Java-17%2B-blue.svg)](https://www.oracle.com/java/)
[![Spring Boot](https://img.shields.io/badge/Spring%20Boot-3.0-green.svg)](https://spring.io/projects/spring-boot)
[![React](https://img.shields.io/badge/React-18-blue.svg)](https://reactjs.org/)
[![License](https://img.shields.io/badge/License-MIT-yellow.svg)](LICENSE)
[![PMD](https://img.shields.io/badge/PMD-7.14.0-orange.svg)](https://pmd.github.io/)

---

A web-based Java code analyzer tool built with **Spring Boot** (backend) and **React** (frontend).  
It supports uploading `.java` files or scanning public GitHub repositories using PMD to detect code issues and best practice violations.

---

## 🚀 Features

- ✅ Upload `.java` files to analyze for code issues
- ✅ Scan public GitHub repositories
- ✅ Uses [PMD](https://pmd.github.io/) for static analysis
- ✅ Displays rule violations with line numbers, rule name, and message
- ✅ Clean React frontend interface

---

## 🧰 Tech Stack

| Layer     | Technology                 |
|-----------|----------------------------|
| Frontend  | React, Axios, Tailwind CSS |
| Backend   | Java 17, Spring Boot 3     |
| Static Analysis | PMD 7.14.0             |
| Build Tools | Maven, Vite (for React)  |

---

## 📂 Project Structure


code-analyzer-/
├── backend/        # Spring Boot app
└── frontend/       # React app



---

## ⚙️ Backend Setup (Spring Boot)

### 📦 Prerequisites
- Java 17+
- Maven
- PMD installed locally  
  Make sure the `.bat` path in code is correct in `AnalyzerController.java`

### ▶️ Run the Backend

```bash
cd backend
./mvnw spring-boot:run
````

> The API will start at `http://localhost:8080`

---

## 🖼️ Frontend Setup (React)

### 📦 Prerequisites

* Node.js + npm

### ▶️ Run the Frontend

```bash
cd frontend
npm install
npm run dev
```

> The frontend will run at `http://localhost:5173`

---

## 🌐 API Endpoints

### 🔹 `POST /analyze`

Analyze uploaded `.java` file.

| Param | Type      | Description      |
| ----- | --------- | ---------------- |
| file  | Multipart | Java source file |

### 🔹 `POST /analyze/github`

Analyze Java files from a public GitHub repository.

| Param   | Type   | Description                     |
| ------- | ------ | ------------------------------- |
| repoUrl | String | URL of public GitHub repository |

---

## 🔒 CORS

Spring Boot is configured to allow requests from `http://localhost:5173` using a global `CorsConfig.java`.

---

## 🛠 PMD Integration

PMD runs as an external process using the path:

```java
"C:\\path\\to\\pmd\\bin\\pmd.bat"
```

> Make sure this path is correctly set in your system and accessible.

You can download PMD from: [https://pmd.github.io/](https://pmd.github.io/)

---

## 📸 Screenshots

| Upload `.java` file                 | Analyze GitHub repo                 |
| ----------------------------------- | ----------------------------------- |
| ![upload](./screenshots/upload.png) | ![github](./screenshots/github.png) |

> *(Add these images in `screenshots/` folder in your repo)*

---

```
## 🎞️ Demo (GIF)

![demo](./screenshots/demo.gif)

> *(Optional: You can record a short screen capture of usage with tools like Loom or ScreenToGif and save as `demo.gif`)*

---
```
## 👨‍💻 Author

**Adarsh Kumar**
GitHub: [@AdarshKumarSr](https://github.com/AdarshKumarSr)

---

## 📜 License

This project is licensed under the **MIT License**.
Feel free to use, modify, and distribute as needed.

````

