package com.example.lab6_v6;

import java.io.*;

import jakarta.servlet.ServletOutputStream;
import jakarta.servlet.http.*;
import jakarta.servlet.annotation.*;

@WebServlet(name = "image", value = "/image")
public class HelloServlet extends HttpServlet {
    private final String IMAGE_DIRECTORY = "C:/Users/Max/Desktop/lab6_v6/src/main/resources";

    public void doGet(HttpServletRequest request, HttpServletResponse response)
            throws IOException {

        // Get the image filename parameter from the request
        String filename = request.getParameter("filename");

        // Construct the file object for the requested image
        File file = new File(IMAGE_DIRECTORY + "/" + filename);

        System.out.println(file.getAbsolutePath());

        // If the file doesn't exist, show an error page
        if (!file.exists()) {
            response.setContentType("text/html");
            PrintWriter out = response.getWriter();
            out.println("<html><head><title>Image Not Found</title></head>");
            out.println("<body><h1>Sorry, the image \"" + filename + "\" was not found.</h1></body></html>");
            return;
        }

        // Read the image into memory
        FileInputStream fis = new FileInputStream(file);
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        byte[] buffer = new byte[1024];
        int bytesRead;
        while ((bytesRead = fis.read(buffer)) != -1) {
            baos.write(buffer, 0, bytesRead);
        }
        fis.close();

        // Set the content type of the response to the image MIME type
        String mimeType = getServletContext().getMimeType(filename);
        response.setContentType(mimeType);

        // Write the image data to the response output stream
        ServletOutputStream sos = response.getOutputStream();
        sos.write(baos.toByteArray());
        sos.flush();
        sos.close();
    }
}