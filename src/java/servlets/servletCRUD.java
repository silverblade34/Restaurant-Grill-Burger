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
import java.util.ArrayList;
import Beans.productosBeans;
import Utils.conexionDB;
import java.util.List;
import javax.servlet.http.HttpSession;


/**
 *
 * @author Michel
 */
@WebServlet(name = "servletCRUD", urlPatterns = {"/servletCRUD"})
public class servletCRUD extends HttpServlet {

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

        String op = request.getParameter("op");
        if (op.equals("listar")) {
            try {
                PreparedStatement psta = conexionDB.getConexion().prepareStatement("select *from producto");
                ResultSet rs = psta.executeQuery();
                ArrayList<productosBeans> lista = new ArrayList<>();
                while (rs.next()) {
                    productosBeans pro = new productosBeans(rs.getInt(1), rs.getString(2), rs.getString(3), rs.getInt(4), rs.getDouble(5),rs.getString(6));
                    lista.add(pro);
                }
                request.setAttribute("lista", lista);
                request.getRequestDispatcher("carta/indexCarta.jsp").forward(request, response);
            } catch (Exception e) {
                System.out.println("Error" + e);
            }
        }else if(op.equals("consulta")){
           String cod=request.getParameter("cod");
           try {
               PreparedStatement psta = conexionDB.getConexion().prepareStatement("select *from producto where id_producto=?");
               psta.setString(1, cod);
               ResultSet rs = psta.executeQuery();
                ArrayList<productosBeans> lista = new ArrayList<>();
                while (rs.next()) {
                   productosBeans pro = new productosBeans(rs.getInt(1), rs.getString(2), rs.getString(3), rs.getInt(4), rs.getDouble(5),rs.getString(6));
                    lista.add(pro);
                }
                conexionDB.getConexion().close();
                request.setAttribute("lista", lista);
                request.getRequestDispatcher("carta/especificacionPedido.jsp").forward(request, response);
           }catch (Exception e) {
              System.out.println("Error" + e);
           }
        }else if(op.equals("listarAdmi")){
             try {
                PreparedStatement psta = conexionDB.getConexion().prepareStatement("select *from producto");
                ResultSet rs = psta.executeQuery();
                ArrayList<productosBeans> lista = new ArrayList<>();
                while (rs.next()) {
                    productosBeans pro = new productosBeans(rs.getInt(1), rs.getString(2), rs.getString(3), rs.getInt(4), rs.getDouble(5),rs.getString(6));
                    lista.add(pro);
                }
                request.setAttribute("lista", lista);
                request.getRequestDispatcher("administrador/listadoAdmi.jsp").forward(request, response);
            } catch (Exception e) {
                System.out.println("Error" + e);
            }
        } else if (op.equals("eliminar")) {
            String cod = request.getParameter("cod");
            try {
                PreparedStatement psta = conexionDB.getConexion().prepareStatement("delete from producto where id_producto=?");
                psta.setString(1, cod);
                psta.executeUpdate();
                request.getRequestDispatcher("servletCRUD?op=listarAdmi").forward(request, response);
            } catch (Exception e) {
                System.out.println("Error" + e);
            }
        }else if (op.equals("insertar")) {
            try {
                System.out.println("entra");
                PreparedStatement psta = conexionDB.getConexion().prepareStatement("insert into producto values(?,?,?,?,?,?)");
                int ID= Integer.parseInt(request.getParameter("idProducto"));
                String nombre = request.getParameter("nombreProducto");
                String descripcion = request.getParameter("descripcionProducto");
                double precio = Double.parseDouble(request.getParameter("precioProducto"));
                int categoria = Integer.parseInt(request.getParameter("categoriaProducto"));
                String imagen = request.getParameter("rutaImagen");
                psta.setInt(1,ID);
                psta.setString(2, nombre);
                psta.setString(3, descripcion);
                psta.setInt(4, categoria);
                psta.setDouble(5, precio);
                psta.setString(6, imagen);
                psta.executeUpdate();
                request.getRequestDispatcher("servletCRUD?op=listar").forward(request, response);
            } catch (Exception e) {
                System.out.println("Error" + e);
            }
        }
        
        
        
        //-------------------CODIGO CARRITO----------------------------------
        
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
        
        int idP=Integer.parseInt(request.getParameter("idProducto"));
        String nomP=request.getParameter("nombreProducto");
        String descP=request.getParameter("descripcionProducto");
        int catP=Integer.parseInt(request.getParameter("categoriaProducto"));
        double precioP=Double.parseDouble(request.getParameter("precioProducto"));
        String rutaP=request.getParameter("rutaImagen");
        
        try{
           PreparedStatement psta=conexionDB.getConexion()
                   .prepareStatement("insert into producto values(?,?,?,?,?,?)");
           psta.setInt(1, idP);
           psta.setString(2,nomP);
           psta.setString(3,descP);
           psta.setInt(4, catP);
           psta.setDouble(5,precioP);
           psta.setString(6,rutaP);
          psta.executeUpdate();
          request.getRequestDispatcher("administrador/agregarProductos.jsp").forward(request, response);
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
