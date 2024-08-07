# Voting_Management_System
https://github.com/hsdeshmukh9922/Voting_Management_System.git
Voter Management System
This Voter Management System is a web-based application designed to facilitate the management of voters and elections. The system allows administrators to schedule elections and manage voter details, while voters can view and update their profiles, check election statuses, and select roles.

Features
Role Selection: Users can select their roles as either Admin or Voter.
Admin Dashboard: Allows admins to schedule new elections and view all scheduled elections.
Voter Dashboard: Provides voters with options to view their profile, update their details, and check election statuses.
Election Scheduling: Admins can schedule new elections by providing details such as the election name, start date, end date, and type.
Profile Management: Voters can view and update their personal information, including name, age, mobile number, and Aadhaar number.
Technologies Used
Frontend: HTML, CSS (with consistent styling across pages), and Thymeleaf for dynamic content rendering.
Backend: Java with Spring Boot for RESTful APIs and server-side logic.
Database: Hibernate ORM for database interactions.
Project Structure
src/main/java: Contains the main application code, including controllers, services, and entity classes.
src/main/resources/templates: Contains Thymeleaf templates for the web pages.
src/main/resources/static: Contains static resources such as CSS and JavaScript files.
src/main/resources/application.properties: Contains configuration properties for the application.
Getting Started
Clone the repository:

bash
Copy code
git clone https://github.com/yourusername/voter-management-system.git
cd voter-management-system
Build and run the application:

bash
Copy code
mvn clean install
mvn spring-boot:run
Access the application:
Open your web browser and navigate to http://localhost:8080.

Contributing
Contributions are welcome! Please fork the repository and create a pull request with your changes. Ensure that your code follows the existing code style and includes appropriate tests.

License
This project is licensed under the MIT License. See the LICENSE file for more details.
