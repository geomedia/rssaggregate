/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package rssaggregator.servlet;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.PrintWriter;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.apache.commons.io.IOUtils;

/**
 *
 * @author Clément RILLON
 */
@WebServlet(name = "LogView", urlPatterns = {"/LogView"})
public class LogView extends HttpServlet {

    /**
     * Processes requests for both HTTP
     * <code>GET</code> and
     * <code>POST</code> methods.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("text/html;charset=UTF-8");
        
//        String file = request.getParameter("file");
//      String path =  System.getProperty("confpath");
         String fileLog = null;
         String nbligne = request.getParameter("nbrLigne");
        try {
             fileLog = System.getProperty("confpath")+"log/"+request.getParameter("file");
        } catch (Exception e) {
        }
       
        
         try {
 
        Process p = Runtime.getRuntime().exec("tail -n "+nbligne +" "+fileLog);
        InputStream out = new BufferedInputStream(p.getInputStream());
       String s = IOUtils.toString(out);
//       s = s.replaceAll("(\n)[1-9]{1}", "</p><p>");

       
       // On remplace les \n par des paragraphes
       String retour = null;
       Pattern pattern = Pattern.compile("(\n)[1-9]{1}");
       Matcher m = pattern.matcher(s);
       if(m.find()){
           retour = m.replaceAll("<p></p>");
       }
       
       request.setAttribute("log", retour);
  
 
    } catch (Exception e) {
        e.printStackTrace();
    }
         
               
        

        this.getServletContext().getRequestDispatcher("/WEB-INF/viewlog.jsp").forward(request, response);
    }

    // <editor-fold defaultstate="collapsed" desc="HttpServlet methods. Click on the + sign on the left to edit the code.">
    /**
     * Handles the HTTP
     * <code>GET</code> method.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        processRequest(request, response);
    }

    /**
     * Handles the HTTP
     * <code>POST</code> method.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        processRequest(request, response);
    }

    /**
     * Returns a short description of the servlet.
     *
     * @return a String containing servlet description
     */
    @Override
    public String getServletInfo() {
        return "Short description";
    }// </editor-fold>
}
