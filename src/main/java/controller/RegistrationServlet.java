package controller;

import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Servlet implementation class RegistrationServlet
 */
@WebServlet("/register")
public class RegistrationServlet extends HttpServlet {
	

	   // JDBC URL, username, and password of MySQL server
    private static final String URL = "jdbc:mysql://localhost:3306/travel";
    private static final String USER = "root";
    private static final String PASSWORD = "root";

    // JDBC variables for opening and managing connection
    private static Connection connection;

    static {
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            connection = DriverManager.getConnection(URL, USER, PASSWORD);
        } catch (Exception e) {
            e.printStackTrace();
            throw new RuntimeException("Failed to connect to the database");
        }
    }
	
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		  String username = request.getParameter("username");
	        String email = request.getParameter("email");
	        String password = hashPassword(request.getParameter("password"));

	        try {
	            String insertQuery = "INSERT INTO users (username, email, password) VALUES (?, ?, ?)";
	            PreparedStatement preparedStatement = connection.prepareStatement(insertQuery);
	            preparedStatement.setString(1, username);
	            preparedStatement.setString(2, email);
	            preparedStatement.setString(3, password);

	            int result = preparedStatement.executeUpdate();

	            if (result > 0) {
	                response.sendRedirect("login.html"); // Redirect to login page
	            } else {
	                response.getWriter().print("Registration failed"); // Output registration failed message
	            }
	        } catch (Exception e) {
	            e.printStackTrace();
	            response.getWriter().print("An error occurred"); // Output error message
	        }
	    }

	    private String hashPassword(String plainTextPassword) {
	        // Implement password hashing here
	        // For demonstration, returning plain text (Don't do this in production)
	        return plainTextPassword;
	}

}
