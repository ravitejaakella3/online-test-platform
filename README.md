# Online Test Platform with Auto-Save

## Overview
REST API for auto-saving student responses during online tests, with 30-second auto-save functionality.

## Getting Started

### Clone Repository
```bash
# Clone the repository
git clone https://github.com/ravitejaakella3/online-test-platform

# Navigate to project directory
cd online-test-platform

# Install dependencies
mvn clean install
```

### Prerequisites
- Java 17 or higher
- Maven 3.6+
- PostgreSQL 12+
- Git

## Project Structure
```
online-test-platform
├── src
│   ├── main
│   │   ├── java
│   │   │   └── com
│   │   │       └── exam
│   │   │           ├── OnlineTestApplication.java     # Main application class
│   │   │           ├── config
│   │   │           │   └── DataInitializer.java       # Test data initialization
│   │   │           ├── controller
│   │   │           │   └── AutoSaveController.java
│   │   │           ├── model
│   │   │           │   ├── Student.java
│   │   │           │   ├── Test.java
│   │   │           │   ├── Question.java
│   │   │           │   └── StudentAnswer.java
│   │   │           ├── repository
│   │   │           │   ├── StudentRepository.java
│   │   │           │   ├── TestRepository.java
│   │   │           │   ├── QuestionRepository.java
│   │   │           │   └── StudentAnswerRepository.java
│   │   │           └── service
│   │   │               └── DataInitializationService.java  # Data initialization service
│   │   └── resources
│   │       ├── application.properties
│   │       └── schema.sql
│   └── test
│       └── java
│           └── com
│               └── exam
│                   └── OnlineTestApplicationTests.java
```

## Key Components

### Main Application
```java
@SpringBootApplication
@EnableScheduling
public class OnlineTestApplication {
    public static void main(String[] args) {
        SpringApplication.run(OnlineTestApplication.class, args);
    }
}
```

### Data Initialization
The application uses `DataInitializationService` to automatically create test data on startup:

```java
@Service
public class DataInitializationService {
    @PostConstruct
    @Transactional
    public void initializeData() {
        // Creates Science Quiz (ID: 2)
        Test test = new Test();
        test.setTitle("Science Quiz");
        test.setDuration(45);

        // Adds question "What is H2O?" (ID: 2)
        Question question = new Question();
        question.setText("What is H2O?");
        question.setTest(test);
    }
}
```

## Setup
1. Clone the repository as shown above
2. Install PostgreSQL if not already installed
3. Create database:
```sql
CREATE DATABASE online_test_db;
```

4. Update `application.properties` with your database credentials:
```properties
spring.datasource.url=jdbc:postgresql://localhost:5433/online_test_db
spring.datasource.username=your_username
spring.datasource.password=your_password
```

5. Run the application:
```bash
mvn spring-boot:run
```

## Features
- Auto-save every 30 seconds
- Optimistic locking for concurrency
- Answer status tracking (ATTEMPTED, SKIPPED, MARKED_FOR_REVIEW)
- Timestamp tracking
- Automatic test data initialization
- Question-test relationship validation

## API Endpoints

### Get Questions
```http
GET http://localhost:8080/api/questions/test/2
```

### Auto-save Answers
```http
POST http://localhost:8080/api/autosave
Content-Type: application/json

{
    "studentId": 1,
    "testId": 2,
    "answers": [
        {
            "questionId": 2,
            "response": "Water",
            "status": "ATTEMPTED"
        }
    ]
}
```

## Testing
```bash
# Run tests
mvn test

# Start application
mvn spring-boot:run
```

## License
This project is licensed under the MIT License - see the [LICENSE](LICENSE) file for details.