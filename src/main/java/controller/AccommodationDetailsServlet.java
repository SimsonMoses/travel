package controller;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Servlet implementation class AccommodationDetailsServlet
 */
@WebServlet("/accommodationDetails")
public class AccommodationDetailsServlet extends HttpServlet {

    // Simulating a database record
    class Accommodation {
        String title;
        String location;
        String priceRange;
        String description;

        Accommodation(String title, String location, String priceRange, String description) {
            this.title = title;
            this.location = location;
            this.priceRange = priceRange;
            this.description = description;
        }
    }

    // Assume we have only one accommodation for this example
    Accommodation mockRecord = new Accommodation("Hotel XYZ", "New York", "High", "A luxurious hotel in downtown New York.");

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        // Normally you'd fetch the accommodation details based on an ID from the request
        PrintWriter out = response.getWriter();

        out.println("<h1>" + mockRecord.title + "</h1>");
        out.println("<p>Location: " + mockRecord.location + "</p>");
        out.println("<p>Price Range: " + mockRecord.priceRange + "</p>");
        out.println("<p>Description: " + mockRecord.description + "</p>");
    }
}