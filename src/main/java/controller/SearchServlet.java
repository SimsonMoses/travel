package controller;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Servlet implementation class SearchServlet
 */
@WebServlet("/search")
public class SearchServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	 private static final String URL = "jdbc:mysql://localhost:3306/travel";
	    private static final String USER = "root";
	    private static final String PASSWORD = "root";

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
	    protected void doGet(HttpServletRequest request, HttpServletResponse response)
	            throws ServletException, IOException {

	        String location = request.getParameter("location");
	        String price = request.getParameter("price");
	        String amenities = request.getParameter("amenities");

	        PrintWriter out = response.getWriter();
	        try {
	            String searchQuery = "SELECT * FROM accommodations WHERE location LIKE ? AND price <= ? AND amenities LIKE ?";
	            PreparedStatement preparedStatement = connection.prepareStatement(searchQuery);

	            preparedStatement.setString(1, "%" + location + "%");
	            preparedStatement.setInt(2, Integer.parseInt(price));
	            preparedStatement.setString(3, "%" + amenities + "%");

	            ResultSet resultSet = preparedStatement.executeQuery();
	            while (resultSet.next()) {
	                out.println("Name: " + resultSet.getString("name"));
	                out.println("Location: " + resultSet.getString("location"));
	                out.println("Price: " + resultSet.getInt("price"));
	                out.println("Amenities: " + resultSet.getString("amenities"));
	                out.println("Rating: " + resultSet.getFloat("rating"));
	                out.println("-----------------------------");
	            }
	        } catch (Exception e) {
	            e.printStackTrace();
	            out.print("An error occurred");
	        }
	    }

}
