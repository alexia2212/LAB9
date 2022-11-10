package com.example.lab9_base.Controller;

import com.example.lab9_base.Bean.Arbitro;
import com.example.lab9_base.Dao.DaoArbitros;
import jakarta.servlet.*;
import jakarta.servlet.http.*;
import jakarta.servlet.annotation.*;

import java.io.IOException;
import java.sql.SQLException;
import java.util.ArrayList;

@WebServlet(name = "ArbitroServlet", urlPatterns = {"/ArbitroServlet"})
public class ArbitroServlet extends HttpServlet {

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String action = request.getParameter("action") == null ? "lista" : request.getParameter("action");
        RequestDispatcher view;
        ArrayList<String> opciones = new ArrayList<>();
        opciones.add("nombre");
        opciones.add("pais");
        DaoArbitros daoArbitros = new DaoArbitros();
        switch (action) {

            case "buscar":
                String buscar = request.getParameter("keyword");
                ArrayList<Arbitro> listaFiltrada =daoArbitros.busquedaNombre(buscar);

                request.setAttribute("listaArbitros",listaFiltrada);
                view = request.getRequestDispatcher("/arbitros/list.jsp");
                view.forward(request,response);
                break;

            case "guardar":

                String nombre = request.getParameter("nombre");
                String pais = request.getParameter("pais");

                try{
                    Arbitro newarbitro = new Arbitro();

                    newarbitro.setNombre(nombre);
                    newarbitro.setPais(pais);

                    daoArbitros.crearArbitro(newarbitro);
                    response.sendRedirect(request.getContextPath()+ "/ArbitroServlet?");

                }catch (NumberFormatException e){
                    response.sendRedirect(request.getContextPath() + "/ArbitroServlet?action=crear");
                } catch (SQLException e) {
                    throw new RuntimeException(e);
                }


        }
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

        String action = request.getParameter("action") == null ? "lista" : request.getParameter("action");
        RequestDispatcher view;
        ArrayList<String> paises = new ArrayList<>();
        paises.add("Peru");
        paises.add("Chile");
        paises.add("Argentina");
        paises.add("Paraguay");
        paises.add("Uruguay");
        paises.add("Colombia");
        ArrayList<String> opciones = new ArrayList<>();
        opciones.add("nombre");
        opciones.add("pais");
        DaoArbitros daoArbitros = new DaoArbitros();

        switch (action) {
            case "lista":

                request.setAttribute("listaArbitros", daoArbitros.listarArbitros());
                view = request.getRequestDispatcher("/arbitros/list.jsp");
                view.forward(request, response);
                break;
            case "crear":
                view = request.getRequestDispatcher("/arbitros/form.jsp");
                view.forward(request,response);
                break;

            case "borrar":
                String spell = request.getParameter("id");
                try{
                    int spelli = Integer.parseInt(spell);
                    daoArbitros.borrarArbitro(spelli);
                    response.sendRedirect(request.getContextPath()+"/ArbitroServlet");

                }catch (NumberFormatException e){
                    response.sendRedirect(request.getContextPath()+ "/ArbitroServlet");
                }
                break;
        }
    }
}
