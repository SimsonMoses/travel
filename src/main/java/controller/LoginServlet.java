package controller;

import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@WebServlet("/login")
public class LoginServlet extends HttpServlet {

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

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        String username = request.getParameter("username");
        String password = hashPassword(request.getParameter("password"));

        try {
            String selectQuery = "SELECT * FROM users WHERE username = ? AND password = ?";
            PreparedStatement preparedStatement = connection.prepareStatement(selectQuery);
            preparedStatement.setString(1, username);
            preparedStatement.setString(2, password);

            ResultSet resultSet = preparedStatement.executeQuery();

            if (resultSet.next()) {
                response.getWriter().print("Login successful"); // Or redirect to user dashboard
                response.sendRedirect("user_profile.html");
            } else {
                response.getWriter().print("Invalid credentials"); // Output invalid credentials message
                response.sendRedirect("login.html"); 
            }
            
            /*
             * 
	            if (result > 0) {
	                response.sendRedirect("login.html"); // Redirect to login page
	            } else {
	                response.getWriter().print("Registration failed"); // Output registration failed message
	            }
             * */
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

