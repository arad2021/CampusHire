# CampusHire

A comprehensive job application platform designed to connect students with recruiters. CampusHire provides a seamless interface for students to discover and apply for jobs, while enabling recruiters to post job listings and manage applications efficiently.

## 🎯 Overview

CampusHire is a full-stack web application built with Spring Boot that facilitates the job search and recruitment process. The platform features intelligent job matching based on student preferences, application management, and a user-friendly interface for both students and recruiters.

## 📸 Project Diagrams

The project includes comprehensive diagrams located in `src/main/resources/texts/Diagrams/`:
- **Class Diagram**: Shows the complete class structure and relationships
- **Use Case Model**: Illustrates user interactions and system functionality
- Additional design diagrams (1.png, 2.png, 3.png)

## ✨ Features

### For Students
- **User Registration & Authentication**: Secure sign-up and login system
- **Profile Management**: Create and edit personal profiles with academic information
- **Job Discovery**: Browse and filter jobs based on preferences (location, job type, industry, remote options, experience level)
- **Smart Job Matching**: Automatic job filtering based on student preferences (Ticket system)
- **Application Management**: Apply to jobs, track application status, and manage applications
- **Application Status Tracking**: View pending, accepted, and denied applications

### For Recruiters
- **Recruiter Registration & Authentication**: Secure account creation and login
- **Job Posting**: Create and manage job listings with detailed specifications
- **Application Review**: View and manage job applications
- **Application Response**: Accept or deny job applications
- **Job Management**: Edit and remove job postings

## 🏗️ Architecture & Design Patterns

### Design Patterns Implemented

1. **Factory Pattern** (`EntityFactory`)
   - Centralized object creation for entities (Student, Recruiter, Job, JobApplication)
   - Simplifies entity instantiation and maintains consistency

2. **Command Pattern** (`JobApplicationCommand`)
   - Encapsulates application response actions (Accept/Deny)
   - Provides flexibility for extending application handling logic
   - Implementations: `AcceptJobApplicationCommand`, `DenyJobApplicationCommand`

3. **Cache Pattern** (`StudentCache`)
   - LRU (Least Recently Used) cache implementation for student data
   - Reduces database queries and improves performance
   - Implementation: `StudentCacheConcrete` with size limit of 3 entries

4. **Repository Pattern**
   - Data access abstraction using Spring Data JPA
   - Clean separation between business logic and data access

5. **Service Layer Pattern**
   - Business logic encapsulation in service classes
   - Controllers delegate to services for processing

### Architecture Layers

```
┌─────────────────┐
│   Controllers   │  REST API Endpoints
├─────────────────┤
│    Services     │  Business Logic
├─────────────────┤
│   Repositories  │  Data Access
├─────────────────┤
│     Models      │  Entity Classes
└─────────────────┘
```

## 🛠️ Technology Stack

### Backend
- **Java 24**: Core programming language
- **Spring Boot 3.4.5**: Application framework
- **Spring Data JPA**: Data persistence
- **Hibernate**: ORM framework
- **PostgreSQL**: Primary database
- **H2 Database**: Development/testing database
- **Lombok**: Reduces boilerplate code
- **Jackson**: JSON processing

### Frontend
- **HTML5**: Structure
- **CSS3**: Styling
- **JavaScript (ES6+)**: Client-side logic
- **Fetch API**: HTTP requests

### Build Tools
- **Maven**: Dependency management and build automation

## 📁 Project Structure

```
CampusHire/
├── src/
│   ├── main/
│   │   ├── java/com/CampusHire/
│   │   │   ├── CampusHireApplication.java      # Main application class
│   │   │   ├── Controllers/                    # REST Controllers
│   │   │   │   ├── StudentController.java
│   │   │   │   └── RecruiterController.java
│   │   │   ├── Services/                       # Business logic layer
│   │   │   │   ├── StudentService.java
│   │   │   │   ├── RecruiterService.java
│   │   │   │   ├── JobService.java
│   │   │   │   └── JobApplicationService.java
│   │   │   ├── Model/                          # Entity classes
│   │   │   │   ├── Student.java
│   │   │   │   ├── Recruiter.java
│   │   │   │   ├── Job.java
│   │   │   │   ├── JobApplication.java
│   │   │   │   ├── Ticket.java                 # Abstract base class
│   │   │   │   ├── StudentTicket.java          # Embedded preference ticket
│   │   │   │   ├── JobTicket.java              # Embedded job requirements
│   │   │   │   ├── EntityFactory.java          # Factory pattern
│   │   │   │   ├── StudentCache.java           # Cache interface
│   │   │   │   └── StudentCacheConcrete.java   # LRU cache implementation
│   │   │   ├── Reposetories/                   # Data access layer
│   │   │   │   ├── StudentRepository.java
│   │   │   │   ├── RecruiterRepository.java
│   │   │   │   ├── JobRepository.java
│   │   │   │   └── JobApplicationRepository.java
│   │   │   └── Commands/                       # Command pattern
│   │   │       ├── JobApplicationCommand.java
│   │   │       ├── AcceptJobApplicationCommand.java
│   │   │       └── DenyJobApplicationCommand.java
│   │   └── resources/
│   │       ├── application.properties          # Configuration
│   │       └── static/                         # Frontend files
│   │           ├── index.html                  # Landing page
│   │           ├── StudentLogin.html
│   │           ├── StudentRegister.html
│   │           ├── student-portal.html
│   │           ├── RecruiterLogin.html
│   │           ├── RecruiterRegister.html
│   │           ├── recruiter-portal.html
│   │           ├── Scripts/                    # JavaScript files
│   │           └── Style/                      # CSS files
│   └── test/                                   # Test files
└── pom.xml                                     # Maven configuration
```

## 🗄️ Database Schema

### Entities

#### Student
- `id` (Long, Primary Key)
- `name` (String)
- `phoneNumber` (String)
- `Email` (String)
- `subject` (String)
- `age` (int)
- `graduateYear` (int)
- `ticket` (Embedded StudentTicket)

#### Recruiter
- `id` (Long, Primary Key)
- `name` (String)
- `phoneNumber` (String)
- `Email` (String)
- `company` (String)

#### Job
- `jobId` (int, Primary Key, Auto-generated)
- `name` (String)
- `company` (String)
- `recruiterId` (Long, Foreign Key)
- `jobTicket` (Embedded JobTicket)

#### JobApplication
- `applicationId` (int, Primary Key, Auto-generated)
- `applicantId` (Long, Foreign Key to Student)
- `jobId` (int, Foreign Key to Job)
- `closed` (boolean)
- `accepted` (String: "pending", "accepted", "denied")

#### Ticket (Abstract Base Class)
- `remote` (String: "Remote", "Hybrid", "Onsite")
- `experience` (int: years of experience)
- `jobType` (String: "Full-time", "Part-time", "Internship", "Contract")
- `location` (String: city name)
- `industry` (String: industry sector)

## 🚀 Getting Started

### Prerequisites

- Java 24 or higher
- Maven 3.6+
- PostgreSQL 12+ (for production) or H2 (for development)
- IDE (IntelliJ IDEA, Eclipse, or VS Code)

### Installation

1. **Clone the repository**
   ```bash
   git clone https://github.com/NissanShuby/CampusHire.git
   cd CampusHire
   ```

2. **Configure Database**

   Edit `src/main/resources/application.properties`:
   ```properties
   # For PostgreSQL (Production)
   spring.datasource.url=jdbc:postgresql://localhost:5432/campushire
   spring.datasource.username=your_username
   spring.datasource.password=your_password
   spring.datasource.driverClassName=org.postgresql.Driver
   
   # For H2 (Development - already configured)
   # H2 is included as a dependency and will be used if PostgreSQL is not configured
   
   spring.jpa.hibernate.ddl-auto=create
   spring.jpa.show-sql=true
   spring.jpa.properties.hibernate.dialect=org.hibernate.dialect.PostgreSQLDialect
   ```

3. **Build the project**
   ```bash
   mvn clean install
   ```

4. **Run the application**
   ```bash
   mvn spring-boot:run
   ```

5. **Access the application**
   - Open your browser and navigate to: `http://localhost:8080`
   - The application will be available on port 8080 by default

## 📡 API Endpoints

### Student Endpoints

| Method | Endpoint | Description |
|--------|----------|-------------|
| POST | `/api/Student/signUp` | Register a new student |
| GET | `/api/Student/SignIn/{id}` | Sign in a student |
| GET | `/api/Student/details/{id}` | Get student details |
| POST | `/api/Student/edit` | Update student profile |
| GET | `/api/Student/filterAllJobs/{id}` | Get filtered jobs based on student preferences |
| GET | `/api/Student/getJobById/{id}` | Get job details by ID |
| POST | `/api/Student/addJobApplication` | Apply to a job |
| GET | `/api/Student/getAllJobApplicationByStudentId/{id}` | Get all applications by student |
| DELETE | `/api/Student/deleteJobApplication/{id}` | Delete an application |

### Recruiter Endpoints

| Method | Endpoint | Description |
|--------|----------|-------------|
| POST | `/api/Recruiter/signUp` | Register a new recruiter |
| GET | `/api/Recruiter/SignIn/{id}` | Sign in a recruiter |
| GET | `/api/Recruiter/details/{id}` | Get recruiter details |
| POST | `/api/Recruiter/edit` | Update recruiter profile |
| POST | `/api/Recruiter/registerJob` | Create a new job posting |
| GET | `/api/Recruiter/jobs/{recruiterId}` | Get all jobs by recruiter |
| GET | `/api/Recruiter/getJobById/{id}` | Get job details by ID |
| DELETE | `/api/Recruiter/removeJob/{id}` | Delete a job posting |
| GET | `/api/Recruiter/jobApplications/{jobId}` | Get all applications for a job |
| POST | `/api/Recruiter/respondToApplication/{applicationId}` | Accept or deny an application (query param: `response=accept|deny`) |

## 💡 Usage Examples

### Student Registration (JSON)
```json
{
  "id": 123456789,
  "name": "John Doe",
  "phoneNumber": "050-1234567",
  "Email": "john.doe@example.com",
  "subject": "Computer Science",
  "age": 22,
  "graduateYear": 2025,
  "ticket": {
    "remote": "Hybrid",
    "experience": 0,
    "location": "Tel Aviv-Yafo",
    "jobType": "Full-time",
    "industry": "Software Development"
  }
}
```

### Job Creation (JSON)
```json
{
  "company": "Tech Corp",
  "name": "Junior Software Developer",
  "recruiterId": 987654321,
  "remote": "Hybrid",
  "experience": 1,
  "jobType": "Full-time",
  "location": "Tel Aviv-Yafo",
  "industry": "Software Development"
}
```

## 🎨 Key Features Explained

### Smart Job Matching (Ticket System)

The platform uses a sophisticated matching system where:
- Students define their preferences in a `StudentTicket` (location, job type, industry, remote preference, experience level)
- Jobs have requirements defined in a `JobTicket`
- The system filters jobs where the job requirements match or are less restrictive than student preferences
- Experience matching: Jobs require experience ≤ student's experience level

### Caching Strategy

- **StudentCache**: Implements LRU (Least Recently Used) cache
- Maximum cache size: 3 entries
- Automatically evicts least recently used entries when limit is reached
- Improves performance by reducing database queries for frequently accessed student data

### Application Status Management

- **Pending**: Initial state when application is submitted
- **Accepted**: Recruiter accepts the application
- **Denied**: Recruiter rejects the application
- Once accepted or denied, the application is marked as `closed`

## 🔧 Configuration

### Application Properties

Key configuration options in `application.properties`:

- `spring.jpa.hibernate.ddl-auto=create`: Automatically creates database schema (use `update` in production)
- `spring.jpa.show-sql=true`: Logs SQL queries (disable in production)
- Database connection settings (commented out - uncomment and configure for PostgreSQL)

## 🧪 Testing

Run tests using Maven:
```bash
mvn test
```

## 📝 Notes

- The application uses ID-based authentication (simplified for demonstration)
- In production, implement proper authentication (JWT, OAuth2, etc.)
- Database schema is auto-generated on startup (`ddl-auto=create`)
- Consider using `update` or `validate` in production environments

## 🔮 Future Enhancements

- [ ] Implement proper authentication and authorization (JWT tokens)
- [ ] Add password hashing and security features
- [ ] Email notifications for application status changes
- [ ] Advanced search and filtering options
- [ ] Resume/CV upload functionality
- [ ] Interview scheduling system
- [ ] Analytics dashboard for recruiters
- [ ] Student recommendation system
- [ ] Multi-language support
- [ ] Mobile application (React Native/Flutter)

## 📄 License

This project is open source and available for educational purposes.

## 👥 Contributing

Contributions are welcome! Please feel free to submit a Pull Request.

## 📧 Contact

For questions or support, please open an issue in the repository.

---

**Built with ❤️ using Spring Boot**


