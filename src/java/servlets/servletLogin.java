/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package servlets;

import java.io.IOException;
import java.io.PrintWriter;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import java.sql.*;
import beans.usuariosBeans;
import Utils.conexionDB;
import javax.servlet.http.HttpSession;

/**
 *
 * @author Michel
 */
@WebServlet(name = "servletLogin", urlPatterns = {"/servletLogin"})
public class servletLogin extends HttpServlet {

    /**
     * Processes requests for both HTTP <code>GET</code> and <code>POST</code>
     * methods.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("text/html;charset=UTF-8");
    }

    // <editor-fold defaultstate="collapsed" desc="HttpServlet methods. Click on the + sign on the left to edit the code.">
    /**
     * Handles the HTTP <code>GET</code> method.
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
        
        HttpSession sesionOk = request.getSession();
        sesionOk.invalidate();
        request.getRequestDispatcher("index.jsp").forward(request,response);
    }

    /**
     * Handles the HTTP <code>POST</code> method.
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
        
        String user=request.getParameter("username");
        String pass=request.getParameter("password");
        
        try{
           PreparedStatement psta=conexionDB.getConexion()
                   .prepareStatement("select *from usuarios where codUsu=? and pass=?");
           psta.setString(1, user);
           psta.setString(2,pass);
           ResultSet rs=psta.executeQuery();
           if(rs.next()){
             HttpSession sesionOk= request.getSession();
             sesionOk.setAttribute("Usuario", rs.getString(2).toString());
             sesionOk.setAttribute("perfil", rs.getString(4).toString());
             request.getRequestDispatcher("index.jsp").forward(request,response);
           }else{
             String msg="Usuario o password incorrectos";
             request.setAttribute("msg",msg);
             request.getRequestDispatcher("indexLogin.jsp").forward(request,response);
           }
        }catch(Exception e){
        
        }
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
