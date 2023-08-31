package com.gopi;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.nio.charset.StandardCharsets;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.commons.fileupload.FileItem;
import org.apache.commons.fileupload.FileUploadException;
import org.apache.commons.fileupload.disk.DiskFileItemFactory;
import org.apache.commons.fileupload.servlet.ServletFileUpload;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;

public class FileUploadServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
	
	private String extractStyleContent(String htmlContent) {
	    String styleContent = "";
	    String stylePattern = "<style[^>]*>([\\s\\S]*?)</style>";
	    Pattern pattern = Pattern.compile(stylePattern, Pattern.DOTALL);
	    Matcher matcher = pattern.matcher(htmlContent);

	    while (matcher.find()) {
	        styleContent += matcher.group(1);
	    }
	    return styleContent;
	}


	private String extractScriptContent(String htmlContent) {
	    String scriptContent = "";
	    String scriptPattern = "<script[^>]*>([\\s\\S]*?)</script>";
	    Pattern pattern = Pattern.compile(scriptPattern, Pattern.DOTALL);
	    Matcher matcher = pattern.matcher(htmlContent);

	    while (matcher.find()) {
	        scriptContent += matcher.group(1);
	    }
	    return scriptContent;
	}
	

	protected void doPost(HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException {
		
		PrintWriter out = res.getWriter();
	    StringBuilder htmlContent = new StringBuilder();
	    StringBuilder cssContent = new StringBuilder();
	    StringBuilder jsContent = new StringBuilder();
	    	    
//		out.print("Hello from servlet");
		ServletFileUpload sf = new ServletFileUpload(new DiskFileItemFactory());
		try {
			List<FileItem> files = sf.parseRequest(req);
			for(FileItem item : files) {
				String fieldName = item.getFieldName();
	            if ("htmlFile".equals(fieldName)) {
	                htmlContent.append(new String(item.get(), StandardCharsets.UTF_8));
	            } else if ("cssFile".equals(fieldName)) {
	                cssContent.append(new String(item.get(), StandardCharsets.UTF_8));
	            } else if ("jsFile".equals(fieldName)) {
	                jsContent.append(new String(item.get(), StandardCharsets.UTF_8));
	            }
	            item.write(new File("D:\\TestingFiles\\"+item.getName()));
			}
			
			String styleContent = extractStyleContent(htmlContent.toString());
			String scriptContent = extractScriptContent(htmlContent.toString());

			Document doc = Jsoup.parse(htmlContent.toString());
			Element bodyElement = doc.body();
			String bodyContent = bodyElement.html();
			
			String combinedPage = "<!DOCTYPE html><html lang=\"en\"><head>"
                    + "<meta charset=\"UTF-8\">"
                    + "<meta name=\"viewport\" content=\"width=device-width, initial-scale=1.0\">"
                    + "<title>Combined Page</title>";

			if (cssContent.length() > 0) {
			   combinedPage += "<style>" + cssContent + styleContent + "</style>";
			}
			
			combinedPage += "</head><body>" + bodyContent + "</body>";
			
			if (jsContent.length() > 0) {
			   combinedPage += "<script>" + jsContent + scriptContent + "</script>";
			}
			combinedPage += "</html>";
			

	        try {
	            FileWriter writer = new FileWriter("D:\\TestingFiles\\CombinedFile.html");
	            writer.write(combinedPage.toString());
	            writer.close();
	            System.out.println("Combined HTML file saved successfully.");
	        } catch (IOException e) {
	            System.out.println("An error occurred while saving the combined HTML file.");
	            e.printStackTrace();
	        }
	        catch(Exception e) {
	        	System.out.println("Cannot Write a File");
	        }
	        HttpSession session = req.getSession();
	        session.setAttribute("combinedPage", combinedPage);
	        File tempFile = File.createTempFile("CombinedFile", ".html");
	        try (FileWriter writer = new FileWriter(tempFile)) {
	            writer.write(combinedPage);
	        }
	        session.setAttribute("tempFile", tempFile.getAbsolutePath());
	        req.getRequestDispatcher("PreviewDownload.jsp").forward(req, res);
	        
		} catch (FileUploadException e) {
			System.out.println("Error in parsing file");
			e.printStackTrace();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

}
