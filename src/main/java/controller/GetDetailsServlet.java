//package controller;
//
//import java.io.PrintWriter;
//import java.sql.Connection;
//import java.sql.DriverManager;
//import java.sql.PreparedStatement;
//import java.sql.ResultSet;
//
//import javax.servlet.http.HttpServlet;
//import javax.servlet.http.HttpServletRequest;
//import javax.servlet.http.HttpServletResponse;
//
///**
// * Servlet implementation class GetDetailsServlet
// */
//public class GetDetailsServlet extends HttpServlet {
//	private static final long serialVersionUID = 1L;
//
//	private static final String URL = "jdbc:mysql://localhost:3306/travel";
//    private static final String USER = "root";
//    private static final String PASSWORD = "root";
//    private static Connection connection;
//
//    static {
//        try {
//            Class.forName("com.mysql.cj.jdbc.Driver");
//            connection = DriverManager.getConnection(URL, USER, PASSWORD);
//        } catch (Exception e) {
//            e.printStackTrace();
//            throw new RuntimeException("Failed to connect to the database");
//        }
//    }
//
//    @Override
//    protected void doGet(HttpServletRequest request, HttpServletResponse response) {
//        String id = request.getParameter("id");
//        JSONObject json = new JSONObject();
//
//        try (PreparedStatement preparedStatement = connection.prepareStatement("SELECT * FROM accommodations WHERE id = ?")) {
//            preparedStatement.setInt(1, Integer.parseInt(id));
//            ResultSet resultSet = preparedStatement.executeQuery();
//
//            if (resultSet.next()) {
//                json.put("name", resultSet.getString("name"));
//                json.put("location", resultSet.getString("location"));
//                json.put("price", resultSet.getInt("price"));
//                json.put("amenities", resultSet.getString("amenities"));
//                json.put("rating", resultSet.getFloat("rating"));
//            }
//
//            response.setContentType("application/json");
//            PrintWriter out = response.getWriter();
//            out.print(json);
//            out.flush();
//        } catch (Exception e) {
//            e.printStackTrace();
//        }
//    }
//
//}
