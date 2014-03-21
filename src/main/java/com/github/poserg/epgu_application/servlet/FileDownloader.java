package com.github.poserg.epgu_application.servlet;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Servlet implementation class FileDownloader
 */
public class FileDownloader extends HttpServlet {
    private static final long serialVersionUID = 1L;

    private static final String DIV = "/";
    private static final int BYTES_DOWNLOAD = 1024;

    /**
     * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
     */
    @Override
    protected void doGet(final HttpServletRequest request, final HttpServletResponse response) throws ServletException, IOException {
        String xsdParam = request.getParameter("xsd");
        if (xsdParam != null && !xsdParam.isEmpty()) {
            String fileName;
            if (xsdParam.contains(DIV)) {
                fileName = xsdParam.substring(xsdParam.lastIndexOf(DIV) + 1);
            } else {
                fileName = xsdParam;
            }
            if (fileName != null && !fileName.isEmpty()) {
                ServletContext servletContext = getServletContext();
                InputStream is = servletContext.getResourceAsStream("WEB-INF/classes/xsd/" + fileName);

                if (is != null) {
                    int read = 0;
                    byte[] bytes = new byte[BYTES_DOWNLOAD];
                    OutputStream os = response.getOutputStream();
                    response.setContentType("text/plain");
                    response.setHeader("Content-Disposition", "attachment;filename=" + fileName);

                    while ((read = is.read(bytes)) != -1) {
                        os.write(bytes, 0, read);
                    }
                    os.flush();
                    os.close();
                }
            }
        }
    }

}
