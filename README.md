School Management System - Microservices Architecture

## ⚠️ Project Context

This is a **learning project** built to explore microservices architecture patterns. It demonstrates distributed systems concepts but is intentionally over-engineered for educational purposes.



## Overview

A distributed school management system implementing core academic operations across multiple microservices:
- Student enrollment and management
- Course creation and assignment
- Teacher-course relationships
- Grade/result tracking
- User authentication and authorization

## Architecture

### Services
```
┌─────────────────┐
│   API Gateway   │ :8080
│   (Port 8080)   │
└────────┬────────┘
         │
    ┌────┴────────────────────────────┐
    │    Eureka Discovery Server      │
    │         (Port 8761)              │
    └────┬────────────────────────────┘
         │
    ┌────┴─────────────────────────────────────┐
    │                                           │
┌───▼────┐  ┌──────────┐  ┌─────────┐  ┌──────────┐
│  Auth  │  │ Student  │  │ Teacher │  │  Course  │
│ :8087  │  │  :8081   │  │  :8084  │  │  :8083   │
└────────┘  └──────────┘  └─────────┘  └──────────┘
                │              │             │
            ┌───▼──────────────▼─────────────▼───┐
            │        Enrollment Service          │
            │            :8085                    │
            └────────────────┬───────────────────┘
                             │
                      ┌──────▼──────┐
                      │   Result    │
                      │   :8082     │
                      └─────────────┘
```

- **Eureka Server** (8761): Service discovery and registration
- **API Gateway** (8080): Single entry point, routing
- **Auth Service** (8087): User registration, JWT authentication
- **Student Service** (8081): Student CRUD operations
- **Teacher Service** (8084): Teacher CRUD operations  
- **Course Service** (8083): Course management, teacher assignment
- **Enrollment Service** (8085): Student course enrollment, transfers
- **Result Service** (8082): Grade assignment and tracking

### Technology Stack

- **Framework:** Spring Boot 3.x, Spring Cloud
- **Service Discovery:** Netflix Eureka
- **API Gateway:** Spring Cloud Gateway
- **Communication:** OpenFeign (synchronous REST)
- **Security:** JWT, BCrypt, Spring Security
- **Database:** MySQL (separate DB per service)
- **Validation:** Jakarta Bean Validation
- **Logging:** SLF4J with Logback

## What I Learned

### Patterns Explored
✅ Service discovery and registration  
✅ API Gateway pattern for routing  
✅ Inter-service communication with Feign  
✅ Database per service pattern  
✅ Centralized exception handling  

### Critical Issues Discovered

#### 1. **No Distributed Transaction Handling**
**Problem:** When enrollment decrements course capacity but fails to create enrollment record, data becomes inconsistent.

**Example:**
```java
// In EnrollmentService.enrollForACourse()
courseDTO.setCourseCapacity(courseDTO.getCourseCapacity()-1); 
courseService.addCourses(courseDTO);  // ✅ Succeeds
enrollment.save();  // ❌ Fails - capacity already decremented!
```

**Solution needed:** Saga pattern (orchestration or choreography) or event sourcing

#### 2. **Tight Synchronous Coupling**
All services communicate via synchronous Feign calls. If one service is down, the entire flow fails.

**Better approach:** Event-driven architecture with message queues (Kafka/RabbitMQ)

#### 3. **Data Duplication Without Events**
Student/Teacher data is copied to other services via DTOs without proper event synchronization.

**Example:** If a student's email changes in Student Service, Enrollment Service still has the old data.

#### 4. **Shared Secret Key**
All services use the same JWT secret key - security anti-pattern in distributed systems.

**Should use:** Centralized auth service with token introspection or public/private key pairs

#### 5. **No Circuit Breakers**
No fallback mechanisms when services are unavailable. Would need Resilience4j or similar.

#### 6. **Testing Complexity**
Integration testing requires all 8 services running. No contract testing (Pact) implemented.

## Production Assessment

### Why This Is Over-Engineered

For a school management system at this scale, **a modular monolith would be significantly better**:

| Concern | Microservices (Current) | Modular Monolith |
|---------|------------------------|------------------|
| **Deployment** | 8 services to deploy, monitor | 1 application |
| **Transactions** | Distributed, complex | ACID, simple |
| **Testing** | Requires all services | Standard integration tests |
| **Performance** | Multiple network hops | In-process calls |
| **Development Speed** | Slow (coordination needed) | Fast |
| **Operational Cost** | High | Low |

### When Microservices Would Make Sense

Microservices are appropriate when you have:

1. **Large teams** (8+ developers) needing independent deployment
2. **Different scaling needs** per domain (e.g., enrollment surge during registration period)
3. **Polyglot persistence** requirements (SQL for some, NoSQL for others)
4. **Organizational maturity** with DevOps, monitoring, distributed tracing
5. **Independent release cycles** for different business domains

**This project has none of these.**

### The Right Approach

Start with a **modular monolith**:
```
src/
├── modules/
│   ├── student/
│   ├── teacher/
│   ├── course/
│   ├── enrollment/
│   └── results/
└── shared/
```

- Clear module boundaries (could extract later if needed)
- Shared database with proper foreign keys
- ACID transactions
- Simple deployment
- Fast development

**Only split to microservices when you hit real bottlenecks** - not before.

## Setup Instructions

### Prerequisites
- Java 17+
- MySQL 8.0+
- Maven 3.8+

### Database Setup

Create separate databases for each service:
```sql
CREATE DATABASE student_management_v1_authdb;
CREATE DATABASE student_management_v1_studentdb;
CREATE DATABASE student_management_v1_teacherdb;
CREATE DATABASE student_management_v1_coursedb;
CREATE DATABASE student_management_v1_enrollmentdb;
CREATE DATABASE student_management_v1_resultdb;
```

Update `application.properties` in each service with your MySQL credentials.

### Running the Services

**Start in this order:**

1. **Eureka Server** (wait for startup)
```bash
cd eureka-server
mvn spring-boot:run
```
Access at: http://localhost:8761

2. **All other services** (can start in parallel)
```bash
# In separate terminals:
cd auth-service && mvn spring-boot:run
cd student-service && mvn spring-boot:run
cd teacher-service && mvn spring-boot:run
cd course-service && mvn spring-boot:run
cd enrollment-service && mvn spring-boot:run
cd result-service && mvn spring-boot:run
cd api-gateway && mvn spring-boot:run
```

3. **Verify** all services registered in Eureka dashboard

### API Access

All requests go through API Gateway: `http://localhost:8080/`

**Example workflow:**
```bash
# 1. Register a user
POST http://localhost:8080/auth-service/register
{
  "userName": "john_doe",
  "name": "John",
  "role": "Student",
  "password": "password123",
  "contactInfo": "john@email.com",
  "age": 20
}

# 2. Login to get JWT token
POST http://localhost:8080/auth-service/log-in
{
  "userName": "john_doe",
  "password": "password123"
}

# 3. Use token in subsequent requests
GET http://localhost:8080/student-service/students/1
Authorization: Bearer 
```

## API Endpoints

### Auth Service
- `POST /register` - Register new user (auto-creates student/teacher record)
- `POST /log-in` - Authenticate and receive JWT token

### Student Service
- `GET /students` - List all students (Admin only)
- `GET /students/{studentId}` - Get student by ID
- `POST /students` - Create student (internal use)
- `DELETE /students/{studentId}` - Delete student (Admin only)

### Teacher Service  
- `GET /teachers` - List all teachers
- `GET /teachers/{teacherId}` - Get teacher by ID
- `POST /teachers` - Create teacher (internal use)
- `DELETE /teachers/{teacherId}` - Delete teacher

### Course Service
- `GET /courses` - List all courses
- `GET /courses/{courseId}` - Get course by ID
- `POST /courses` - Create course
- `DELETE /courses/{courseId}` - Delete course
- `POST /courses/teachers` - Assign teacher to course
- `POST /courses/syllabus` - Add syllabus to course

### Enrollment Service
- `POST /enrollment` - Enroll in course (Student only, own ID)
- `POST /transfer` - Transfer between courses (Student only, own ID)
- `GET /enrollment/student/{studentId}` - View student enrollments
- `GET /enrollment/course/{courseId}` - View course enrollments (Teacher/Admin)

### Result Service
- `POST /result` - Assign grade to student
- `GET /result/student/{studentId}` - View student results
- `GET /result/course/{courseId}` - View course results

## Security

**WIP:** Basic JWT authentication partially implemented.

- Users register and receive role (Student/Teacher/Admin)
- JWT token required for most endpoints
- Role-based access control on sensitive operations
- **Note:** Token propagation across services needs improvement

**Missing in current implementation:**
- Token refresh mechanism
- Proper token invalidation/blacklisting
- Centralized authorization service
- Fine-grained permissions beyond basic roles

## What I'd Do Differently

If building this for production (which, again, I wouldn't as microservices):

1. **Event-Driven Architecture**
   - Use Kafka/RabbitMQ for async communication
   - Event sourcing for critical operations
   - CQRS for read-heavy operations

2. **Distributed Transactions**
   - Implement Saga pattern for enrollment flow
   - Use outbox pattern for reliable events

3. **Resilience**
   - Circuit breakers (Resilience4j)
   - Retry logic with exponential backoff
   - Fallback mechanisms

4. **Observability**
   - Distributed tracing (Zipkin/Jaeger)
   - Centralized logging (ELK stack)
   - Metrics and monitoring (Prometheus/Grafana)

5. **Testing**
   - Contract testing (Pact)
   - Chaos engineering tests
   - Load testing for each service

6. **Security**
   - OAuth2/OpenID Connect
   - API rate limiting
   - Separate auth server with token introspection

7. **But Honestly?**
   - **Just build a modular monolith** and avoid all this complexity

## Project Status

- ✅ Core CRUD operations working
- ✅ Service discovery and routing functional
- ✅ Basic JWT authentication
- ⚠️ No distributed transaction handling
- ⚠️ No proper error recovery
- ⚠️ No tests
- ⚠️ Not production-ready

## Lessons for Future Projects

1. **Start with a monolith** - split only when necessary
2. **Understand the problem domain** before choosing architecture
3. **Operational complexity is real** - don't underestimate it
4. **Distributed transactions are hard** - avoid if possible
5. **Testing complexity multiplies** with service count
6. **Team size matters** - solo developer + microservices = pain

## Acknowledgments

Built as a learning exercise to understand:
- ✅ What microservices patterns look like in code
- ✅ Why they're complex
- ✅ When NOT to use them

**Next project:** A well-structured modular monolith that demonstrates better architectural judgment.

---

## License

MIT License - Educational purposes only

    
 
