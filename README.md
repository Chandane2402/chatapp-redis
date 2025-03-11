# 🚀 Simple Chat Application Using Redis

## 📌 Project Overview
This project is a **Spring Boot-based chat application** using **Redis** as the primary data store. The application allows users to:
- ✅ **Create and Join Chat Rooms**  
- ✅ **Send & Retrieve Messages**  
- ✅ **Real-time Chat with Redis Pub/Sub**  
- ✅ **Efficient Message Storage using Redis Lists**  

### 🛠️ Tech Stack
- **Backend:** Java, Spring Boot  
- **Database:** Redis (Data Persistence with Snapshots)  
- **Testing:** JUnit, Postman  
- **Build Tool:** Maven  

---

## 📑 **API Endpoints & Usage**
### 1️⃣ **Create a Chat Room**
- **Endpoint:** `POST /api/chatapp/chatrooms`
- **Request Body:**  
    ```json
    {
      "roomName": "general"
    }
    ```
- **Response:**  
    ```json
    {
      "message": "Chat room 'general' created successfully.",
      "roomId": "general",
      "status": "success"
    }
    ```

### 2️⃣ **Join a Chat Room**
- **Endpoint:** `POST /api/chatapp/chatrooms/{roomId}/join`
- **Example:** `POST /api/chatapp/chatrooms/general/join`
- **Request Body:**  
    ```json
    {
      "participant": "guest_user"
    }
    ```
- **Response:**  
    ```json
    {
      "message": "User 'guest_user' joined chat room 'general'.",
      "status": "success"
    }
    ```

### 3️⃣ **Send a Message**
- **Endpoint:** `POST /api/chatapp/chatrooms/{roomId}/messages`
- **Example:** `POST /api/chatapp/chatrooms/general/messages`
- **Request Body:**  
    ```json
    {
      "participant": "guest_user",
      "message": "Hello, everyone!"
    }
    ```
- **Response:**  
    ```json
    {
      "message": "Message sent successfully.",
      "status": "success"
    }
    ```

### 4️⃣ **Retrieve Chat History**
- **Endpoint:** `GET /api/chatapp/chatrooms/{roomId}/messages?limit=10`
- **Example:** `GET /api/chatapp/chatrooms/general/messages?limit=10`
- **Response:**  
    ```json
    {
      "messages": [
        {
          "participant": "guest_user",
          "message": "Hello, everyone!",
          "timestamp": "2024-01-01T10:00:00Z"
        },
        {
          "participant": "another_user",
          "message": "Hi, guest_user!",
          "timestamp": "2024-01-01T10:01:00Z"
        }
      ]
    }
    ```

---

## 🏗️ **Project Setup & Running Instructions**
### 🔹 **Prerequisites**
- Install **Java 17+**
- Install **Maven**
- Install & Run **Redis Server**

### 🔹 **Clone the Repository**
```sh
git clone https://github.com/your-username/chatapp-redis.git
cd chatapp-redis
