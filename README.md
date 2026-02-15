# 🚦 Toll Plaza Service – Spring Boot Backend

A Spring Boot backend application that calculates toll plazas between two Indian pincodes using Google Maps APIs and geospatial logic.

The system:
- Converts pincodes → coordinates
- Fetches route using Google Directions API
- Decodes polyline
- Calculates toll plazas near the route
- Returns structured toll response

---

## 📌 Features

- ✅ Fetch toll plazas between two pincodes
- ✅ Google Maps integration (Geocoding + Directions API)
- ✅ Polyline decoding
- ✅ Distance-based toll filtering
- ✅ Exception handling (Invalid / Same pincode)
- ✅ Unit testing with JUnit & Mockito
- ✅ High test coverage (80%+)
- ✅ Clean layered architecture

---

## 🏗️ Tech Stack

- Java 17
- Spring Boot 3.x
- WebClient (Reactive HTTP client)
- JUnit 5
- Mockito
- JaCoCo (Code Coverage)
- Maven

---

## 📂 Project Structure

```
com.toll
│
├── controller       → REST Controllers
├── service          → Business Logic
├── util             → Utility classes (Geo, Polyline)
├── exception        → Custom Exceptions + Global Handler
├── dto              → Data Transfer Objects
├── entity           → Entity classes
├── config           → Configuration classes
```

---

## 🚀 How to Run the Project

### 1️⃣ Clone Repository

```bash
git clone https://github.com/YOUR_USERNAME/toll-plaza-service.git
cd toll-plaza-service
```

---

### 2️⃣ Configure Google API Key

Open:

```
src/main/resources/application.properties
```

Add your Google API key:

```properties
google.api.key=YOUR_GOOGLE_API_KEY
```

You must enable:

- ✅ Geocoding API
- ✅ Directions API

From Google Cloud Console.

---

### 3️⃣ Build the Project

```bash
mvn clean install
```

---

### 4️⃣ Run the Application

```bash
mvn spring-boot:run
```

OR run from IDE:

Run `TollPlazaServiceApplication.java`

---

### 5️⃣ Application Starts At

```
http://localhost:8080
```

---

## 📡 API Documentation

### 🔹 Get Toll Plazas Between Pincodes

**Endpoint:**

```
POST /api/v1/toll-plazas
```

### 🔹 Request Example

POST  http://localhost:8080/api/v1/toll-plazas

Request Body:

```json
{
  "sourcePincode": "110001",
  "destinationPincode": "560001"
}
```

---

### 🔹 Response Example

```json
{
  "route": {
  "sourcePincode": "110001",
  "destinationPincode": "560001",
  "distanceInKm": 2100
  },
  "tollPlazas": [
  {
    "name": "Toll Plaza 1",
    "latitude": 28.7041,
    "longitude": 77.1025,
    "distanceFromSource": 200
  },
  {
    "name": "Toll Plaza 2",
    "latitude": 19.076,
    "longitude": 72.8777,
    "distanceFromSource": 1400
  }
  ]
}
```

Status Code: `200 OK SUCCESS RESPONSE`

---

## ❌ Error Responses

### Invalid Pincode

```json
{
"error": "Invalid source or destination pincode"
}
```

Status Code: `400 BAD REQUEST`

---

### Same Pincode

```json
{
  "error": "Source and destination pincodes cannot be the same"
}
```

Status Code: `400 BAD REQUEST`

---

## 🧪 Running Tests

Run all unit tests:

```bash
mvn test
```

---

## 📊 Code Coverage

Generate coverage report:

```bash
mvn clean verify
```

Open:

```
target/site/jacoco/index.html
```

Coverage includes:

- Controller tests
- Service tests
- Exception tests
- Utility tests
- GoogleService mocking

---

## 🧠 How It Works (High-Level Flow)

1. Validate pincodes
2. Convert pincode → coordinates (Google Geocoding)
3. Fetch route (Google Directions API)
4. Decode polyline
5. Compare route points with toll coordinates
6. Filter tolls within threshold distance
7. Return structured response

---

## 🏛️ Architecture

```
Controller → Service → GoogleService → Utility → Response
```

Clean separation of concerns:
- Controller handles API layer
- Service contains business logic
- GoogleService handles external API
- Utils handle geo calculations

---

## 📈 Test Coverage Strategy

- Mocked WebClient for Google API
- Covered success & failure scenarios
- Covered edge cases (empty route, invalid pincode)
- Covered branch logic
- Covered exception handler

---

## 🔒 Future Improvements

- Add Swagger/OpenAPI documentation
- Add Docker support
- Add database persistence
- Add authentication

---

## 👩‍💻 Author

Anshita Nayak  
Backend Developer | Java | Spring Boot | DSA

---

## ⭐ If you found this useful

Give this repository a ⭐ on GitHub.
