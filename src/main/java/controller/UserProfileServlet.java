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
 * Servlet implementation class UserProfileServlet
 */
@WebServlet("/userProfile")
public class UserProfileServlet extends HttpServlet {

    // JDBC URL, username, and password
    private static final String URL = "jdbc:mysql://localhost:3306/travel";
    private static final String USER = "root";
    private static final String PASSWORD = "root";

    // JDBC variables
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

        // Assuming you have a way to get the currently logged-in user's ID
        int userId = getCurrentUserId();

        String firstName = request.getParameter("first_name");
        String lastName = request.getParameter("last_name");
        String phone = request.getParameter("phone");

        try {
            String updateQuery = "UPDATE users SET first_name = ?, last_name = ?, phone = ? WHERE id = ?";
            PreparedStatement preparedStatement = connection.prepareStatement(updateQuery);
            preparedStatement.setString(1, firstName);
            preparedStatement.setString(2, lastName);
            preparedStatement.setString(3, phone);
            preparedStatement.setInt(4, userId);

            int result = preparedStatement.executeUpdate();

            if (result > 0) {
                response.getWriter().print("Profile updated successfully");
                response.sendRedirect("success.html"); // Assuming the success page is named 'success.html'

            } else {
                response.getWriter().print("Profile update failed");
            }
        } catch (Exception e) {
            e.printStackTrace();
            response.getWriter().print("An error occurred");
        }
    }

    private int getCurrentUserId() {
        // Implement logic to get the current user's ID, perhaps from a session
        return 1; // Placeholder
    }
}