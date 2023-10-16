package controller;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.json.simple.JSONObject; // import JSON-simple library
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

/**
 * Servlet implementation class BookingServlet
 */
@WebServlet("/bookAccommodation")
public class BookingServlet extends HttpServlet {
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        StringBuilder sb = new StringBuilder();
        String line;
        BufferedReader reader = request.getReader();
        while ((line = reader.readLine()) != null) {
            sb.append(line);
        }

        JSONParser parser = new JSONParser();
        JSONObject json;
        int accommodationId = 0 ;
        int userId = 0 ;
        try {
            json = (JSONObject) parser.parse(sb.toString());
             accommodationId = Integer.parseInt(json.get("accommodationId").toString());
             userId = Integer.parseInt(json.get("userId").toString());

            // Here you would handle the booking logic, like storing the booking in a database
            
            // After successful booking, send back a confirmation message
            JSONObject jsonResponse = new JSONObject();
            jsonResponse.put("message", "Booking successful.");
            response.setContentType("application/json");
            PrintWriter out = response.getWriter();
            out.println(jsonResponse);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        Connection conn = null;
        PreparedStatement stmt = null;
        try {
            // Initialize database connection
            Class.forName("com.mysql.cj.jdbc.Driver"); // Make sure to include the MySQL JDBC library
            conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/travel", "root", "root");

            // Create SQL query
            String sql = "INSERT INTO Bookings (accommodation_id, user_id) VALUES (?, ?)";
            stmt = conn.prepareStatement(sql);
            stmt.setInt(1, accommodationId);
            stmt.setInt(2, userId);

            // Execute query
            int rowInserted = stmt.executeUpdate();

            // Create JSON response
            JSONObject jsonResponse = new JSONObject();

            if (rowInserted > 0) {
                jsonResponse.put("message", "Booking successful.");
                response.setStatus(HttpServletResponse.SC_OK);
            } else {
                jsonResponse.put("message", "Booking failed.");
                response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
            }

            response.setContentType("application/json");
            PrintWriter out = response.getWriter();
            out.println(jsonResponse);

        } catch (SQLException | ClassNotFoundException e) {
            e.printStackTrace();
            response.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
        } finally {
            // Close resources
            try {
                if (stmt != null) {
                    stmt.close();
                }
                if (conn != null) {
                    conn.close();
                }
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }

    }
}
