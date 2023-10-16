package controller;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Servlet implementation class SubmitReviewServlet
 */
@WebServlet("/SubmitReviewServlet")
public class SubmitReviewServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
//	 private static final String URL = "jdbc:mysql://localhost:3306/travel";
//	    private static final String USER = "root";
//	    private static final String PASSWORD = "root";
//
//	    // JDBC variables for opening and managing connection
//	    private static Connection connection;
//
//	    static {
//	        try {
//	            Class.forName("com.mysql.cj.jdbc.Driver");
//	            connection = DriverManager.getConnection(URL, USER, PASSWORD);
//	        } catch (Exception e) {
//	            e.printStackTrace();
//	            throw new RuntimeException("Failed to connect to the database");
//	        }
//	    }
	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        String review = request.getParameter("review");
        String rating = request.getParameter("rating");
        PrintWriter out = response.getWriter();
        
        Connection conn = null;
        PreparedStatement pstmt = null;
        try {
            // Load the database driver (make sure the JDBC library is in your classpath)
            Class.forName("com.mysql.cj.jdbc.Driver");


            // Establish a database connection (update these values according to your database setup)
            conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/travel", "root", "root");

            // Prepare the SQL statement
            String sql = "INSERT INTO rateandreview (user_id, accommodation_id, rating, review_text) VALUES (?, ?, ?, ?)";
            pstmt = conn.prepareStatement(sql);

            // Set parameter values (these should be obtained dynamically, but for now, let's hardcode some values)
            pstmt.setInt(1, 1); // user_id
            pstmt.setInt(2, 1); // accommodation_id
            pstmt.setInt(3, Integer.parseInt(rating)); // rating
            pstmt.setString(4, review); // review_text

            // Execute the query
            int affectedRows = pstmt.executeUpdate();

            if (affectedRows > 0) {
                out.println("Thank you for your review!");
            } else {
                out.println("Error submitting your review.");
            }

        } catch (Exception e) {
            out.println("Database error: " + e.getMessage());
        } finally {
            // Close resources
            try {
                if (pstmt != null) {
                    pstmt.close();
                }
                if (conn != null) {
                    conn.close();
                }
            } catch (Exception e) {
                out.println("Error closing resources: " + e.getMessage());
            }
        }
    }
    

}
