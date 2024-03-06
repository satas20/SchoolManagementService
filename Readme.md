# 🏫School Management Service🏫
This school management service provides a robust set of tools to manage your student and school records.
This service has a feature to log the changes made to the student and school records and also users can send announcements using web sockets.

## School and Student Controller API Endpoints

### Student Endpoints
| Method | URL | Description |
|---|---|---|
| `GET` | `/api/v1/student` | Retrieves a list of all students. |
| `GET` | `/api/v1/student/{id}` | Retrieves a specific student by ID. |
| `GET` | `/api/v1/student/image/{id}` | Retrieves a student's image file. |
| `POST` | `/api/v1/student` | Creates a new student (with optional image). |
| `PUT` | `/api/v1/student/{id}` | Updates an existing student (with optional image). |
| `DELETE` | `/api/v1/student/{id}` | Deletes a student. |
| `PUT` | `/api/v1/student/{studentId}/school/{schoolId}` | Assigns a student to a school. |

### School Endpoints

| Method | URL                   | Description                            |
|---|-----------------------|----------------------------------------|
| `GET` | `/api/v1/school`      | Retrieves a list of all schools.       |
| `GET` | `/api/v1/school/{id}` | Retrieves a specific school by its ID. |
| `PUT` | `/api/v1/school/{id}` | Updates an existing school             |
| `DELETE` | `/api/v1/school/{id}` | Deletes a school.                      |
| `POST` | `/api/v1/school` | Creates a new school.                  |


### Chat log system
 The app has a chat and log system where users can send   announcements to all the users in the system. 
 System automatically logs the changes made in the DB The chat log system is implemented using web sockets.
    


<p align="center">

  <img src="Media/ChatLog.png" height= "500"> 
  
</p>

### Testing
The project employs JUnit and Mockito for thorough testing to ensure reliability. The tests cover essential areas within the Student Management system, including:
- Student Repository Tests
- School Repository Tests

To run tests, navigate to the test directory and run the following command:
```bash
mvn test
```

# How to Run
Clone the repository:
```bash
git clone https://github.com/satas20/SchoolManagementService.git
```
Cd into the directory:
```bash
cd ./Executable
```

Edit the properties file to your database configuration:
```bash
spring.datasource.url=jdbc:postgresql://<target_host>:5432/<database_name>
spring.datasource.username=<username>
spring.datasource.password=<password>
```

Run the project:
```bash
java -jar SchoolManagementService.jar
```
Navigate to your browser and go to the specified port in (default: 8080).
```
http://localhost:8080/chat
```
Success! You are now running the School Management Service.


<p align="center">
  <img src="Media/Console.png" height= "300">
</p>
