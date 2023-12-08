# Selenium-automation-testing-management

## Introduction
This project is a part of the INFO6255 course, focusing on Selenium test automation. It includes automated testing scenarios for various web applications related to Northeastern University. The project is set up using Maven and supports Java or Python as the programming language.

## Project Setup

### Requirements
- Java JDK 8+ or Python 3.6+
- Maven (for Java) / Pip (for Python)
- An IDE (e.g., IntelliJ, Eclipse, PyCharm)
- Selenium WebDriver
- A Web browser (Chrome, Firefox, etc.) with the corresponding WebDriver

### Installation & Configuration
1. **Clone the Repository**: Clone or download this project to your local machine.
2. **Maven Setup (for Java)**:
   - Navigate to the project directory.
   - Run `mvn clean install` to install dependencies specified in `pom.xml`.

## Data File
Create an Excel file in your user directory for test data and configure the script to read from this file. This file should include necessary details like login credentials, event details, etc.

## Writing Test Cases
- Implement test scenarios as outlined in the assignment.
- Utilize the Page Object Model (POM) for maintainability.
- Include assertions for validating test outcomes.
- Implement a mechanism for capturing screenshots before and after each test step.

## Running Tests
Execute tests via your IDE or the command line. Tests should run sequentially without manual intervention, except for the two-factor authentication step.

## Reporting
An HTML report is generated post-execution, detailing test scenario names, actual vs. expected results, and pass/fail statuses.

## Troubleshooting
In case of test failures, check browser and WebDriver compatibility, data file accuracy, and script syntax/logic.
