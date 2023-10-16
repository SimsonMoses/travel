//package controller;
//
//import java.io.IOException;
//import java.net.Authenticator;
//import java.net.PasswordAuthentication;
//import java.util.Properties;
//import java.util.UUID;
//
//import javax.mail.*;
//import javax.mail.internet.*;
//import javax.servlet.ServletException;
//import javax.servlet.annotation.WebServlet;
//import javax.servlet.http.HttpServlet;
//import javax.servlet.http.HttpServletRequest;
//import javax.servlet.http.HttpServletResponse;
//import javax.websocket.Session;
//
//import com.mysql.cj.protocol.Message;
//
///**
// * Servlet implementation class RequestResetServlet
// */
//@WebServlet("/requestReset")
//public class RequestResetServlet extends HttpServlet {
//	private static final long serialVersionUID = 1L;
//
//	  
//	    protected void doPost(HttpServletRequest request, HttpServletResponse response)
//	            throws ServletException, IOException {
//
//	        String email = request.getParameter("email");
//	        String token = UUID.randomUUID().toString(); // Generate random token
//
//	        // Store token and email in database
//	        // ... (Same as your previous database code)
//
//	        // Send the token by email
//	        try {
//	            sendEmail(email, token);
//	            response.getWriter().print("Check your email for the reset token");
//	        } catch (Exception e) {
//	            e.printStackTrace();
//	            response.getWriter().print("Failed to send email");
//	        }
//	    }
//
//	    private void sendEmail(String to, String token) throws Exception {
//	        Properties props = new Properties();
//	        props.put("mail.smtp.host", "smtp.example.com"); // Replace with your SMTP server
//	        props.put("mail.smtp.auth", "true");
//
//	        Session session = Session.getInstance(props, new Authenticator() {
//	            protected PasswordAuthentication getPasswordAuthentication() {
//	                return new PasswordAuthentication("yourEmail@example.com", "yourPassword"); // Replace with your email and password
//	            }
//	        });
//
//	        Message message = new MimeMessage(session);
//	        message.setFrom(new InternetAddress("yourEmail@example.com")); // Replace with your email
//	        message.setRecipients(Message.RecipientType.TO, InternetAddress.parse(to));
//	        message.setSubject("Password Reset Token");
//	        message.setText("Your password reset token is: " + token);
//
//	        Transport.send(message);
//	    }
//
//}
