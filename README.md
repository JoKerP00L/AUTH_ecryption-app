---
# Java Encryption Cryptography for Login and Sign-up

This Java application demonstrates the implementation of encryption and cryptography methods for secure user login and sign-up processes. It uses cryptographic techniques to store and verify user credentials securely. This project is a fundamental example and should be adapted and extended for production use.

## Features

- **User Registration:** Users can create a new account by providing their email, fullname, username and password. Passwords are securely hashed before storage.

- **User Login:** Registered users can log in using their email and password. Passwords are securely hashed and compared during the login process.

- **Password Hashing:** Passwords are hashed using a strong cryptographic hashing algorithm (e.g., bcrypt) before being stored in the database.

## Prerequisites

Before you begin, ensure you have the following:

- Java Development Kit (JDK) installed on your system.
- A MySQL database for storing user information.

## Setup

1. Clone the repository to your local machine:

   ```bash
   git clone https://github.com/JoKerP00L/AUTH_ecryption-app.git
   ```

2. Open the project in your favorite Java development environment (e.g., android studio, etc).

3. Configure the Backend:
   - You can download the backend files form aboue link:
   ```bash
   https://github.com/JoKerP00L/php-api.git
   ```
   - Upload the PHP scripts located in the `backend` folder to your web server.
   - Modify the PHP scripts to connect to your MySQL database and update the database credentials in `config.php`.

4. Build and run the Java application.

## Usage

1. Run the Java application.

2. Register a new account by providing a username and password.

3. Log in using your registered username and password.

4. The application will verify your credentials using secure password hashing.

## Libraries Used

This project use com.github.VishnuSivadasVS:Advanced-HttpURLConnection:1.2 libraries

## Security Considerations

- Always use strong cryptographic hashing algorithms (e.g., bcrypt) for password hashing.
- Protect the database connection credentials and encryption keys.

---
