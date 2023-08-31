package com.gopi;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.OutputStream;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

/**
 * Servlet implementation class DownloadServlet
 */
public class DownloadServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
   
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		HttpSession session = request.getSession();
	    String tempFilePath = (String) session.getAttribute("tempFile");

	    if (tempFilePath != null) {
	        File tempFile = new File(tempFilePath);
	        response.setContentType("application/octet-stream");
	        response.setHeader("Content-Disposition", "attachment; filename=CombinedFile.html");

	        try (FileInputStream fileInputStream = new FileInputStream(tempFile);
	             OutputStream outputStream = response.getOutputStream()) {
	            byte[] buffer = new byte[4096];
	            int bytesRead;
	            while ((bytesRead = fileInputStream.read(buffer)) != -1) {
	                outputStream.write(buffer, 0, bytesRead);
	            }
	        }
	        tempFile.delete(); // Delete the temporary file after serving
	    }
	}

	

}
