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

import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;




/**
 * Servlet implementation class BookingServlet
 */
@WebServlet("/bookAccommodation")
public class BookingServlet extends HttpServlet {
	 protected void doPost(HttpServletRequest request, HttpServletResponse response)
	            throws ServletException, IOException {
	        PrintWriter out = response.getWriter();

	        // Get parameters from the form submission
	        int accommodationId = Integer.parseInt(request.getParameter("accommodationId"));
	        int userId = Integer.parseInt(request.getParameter("userId"));

	        try {
	            // Database connection
	            Class.forName("com.mysql.jdbc.Driver");
	            Connection conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/travel", "root", "root");

	            // Insert booking data into bookings table
	            String sql = "INSERT INTO bookings (accommodation_id, user_id) VALUES (?, ?)";
	            PreparedStatement ps = conn.prepareStatement(sql);
	            ps.setInt(1, accommodationId);
	            ps.setInt(2, userId);
	            int result = ps.executeUpdate();

	            if (result > 0) {
	                out.println("Booking successful!");
	            } else {
	                out.println("Failed to book accommodation.");
	            }

	            ps.close();
	            conn.close();
	        } catch (Exception e) {
	            e.printStackTrace();
	            out.println("An error occurred while processing your request.");
	        }
	    }
}
