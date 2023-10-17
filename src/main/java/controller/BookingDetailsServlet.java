package controller;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Servlet implementation class BookingDetailsServlet
 */
@WebServlet("/getBookingDetails")
public class BookingDetailsServlet extends HttpServlet {

    private static final String URL = "jdbc:mysql://localhost:3306/travel";
    private static final String USER = "root";
    private static final String PASSWORD = "root";

    private Connection connection;

    public BookingDetailsServlet() {
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            connection = DriverManager.getConnection(URL, USER, PASSWORD);
        } catch (Exception e) {
            e.printStackTrace();
            throw new RuntimeException("Failed to connect to the database");
        }
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws IOException {
    	System.out.println("Request recevied to the BookingDetailsServlet:");
        resp.setContentType("application/json");
        PrintWriter out = resp.getWriter();

        try {
            String query = "SELECT b.booking_id, a.name, a.location, a.price, a.amenities, a.rating " +
                           "FROM bookings b " +
                           "JOIN accommodations a ON b.accommodation_id = a.id " +
                           "WHERE b.user_id = ?";  // This is a placeholder for user's ID

            PreparedStatement statement = connection.prepareStatement(query);
            statement.setInt(1, 1);  // Assuming user's ID is 1 for demonstration. Replace as needed.

            ResultSet resultSet = statement.executeQuery();
            
            StringBuilder jsonResponse = new StringBuilder("[");  // Start with an array opening bracket

            while (resultSet.next()) {
                jsonResponse.append("{")
                            .append("\"bookingId\":\"").append(resultSet.getInt("booking_id"))
                            .append("\",\"name\":\"").append(resultSet.getString("name"))
                            .append("\",\"location\":\"").append(resultSet.getString("location"))
                            .append("\",\"price\":\"").append(resultSet.getInt("price"))
                            .append("\",\"amenities\":\"").append(resultSet.getString("amenities"))
                            .append("\",\"rating\":\"").append(resultSet.getFloat("rating"))
                            .append("\"},");  // Closing bracket for each booking object and a comma
            }

            // Remove the last comma and add the closing bracket for the array
            jsonResponse.setLength(jsonResponse.length() - 1);
            jsonResponse.append("]");
            
            out.print(jsonResponse.toString());

        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            out.close();
        }
    }
}