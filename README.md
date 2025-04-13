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

## Project Structure
```
online-test-platform/
├── src/                    # Backend source files
├── frontend/              # React frontend
├── pom.xml                # Maven configuration
└── README.md
```

## Features
- Auto-save every 30 seconds
- Real-time save status display
- Answer status tracking (ATTEMPTED, SKIPPED, MARKED_FOR_REVIEW)
- Optimistic locking for concurrency
- Debug information panel

## Prerequisites
- Java 17+
- Maven 3.6+
- PostgreSQL 12+
- Node.js 14+
- npm 6+

## Setup

### Backend
1. Clone repository:
```bash
git clone https://github.com/yourusername/online-test-platform.git
cd online-test-platform
```

2. Configure database:
```sql
CREATE DATABASE online_test_db;
```

3. Update `application.properties`:
```properties
spring.datasource.url=jdbc:postgresql://localhost:5433/online_test_db
spring.datasource.username=your_username
spring.datasource.password=your_password
```

4. Run backend:
```bash
mvn spring-boot:run
```

### Frontend
1. Install dependencies:
```bash
cd frontend
npm install
```

2. Start development server:
```bash
npm start
```

## Usage
Access the application at http://localhost:3000

The interface shows:
- Questions from the test
- Auto-save countdown timer
- Last save timestamp
- Debug information panel

## API Endpoints

### Auto-save Answers
```http
POST /api/autosave
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

### Get Test Questions
```http
GET /api/questions/test/{testId}
```

## Development

### Running Tests
```bash
# Backend tests
mvn test

# Frontend tests
cd frontend
npm test
```

### Building for Production
```bash
# Backend
mvn clean package

# Frontend
cd frontend
npm run build
```

## License
MIT License - see LICENSE file