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
                String buscar = request.getParameter("buscar");
                String opcion = request.getParameter("tipo");

                request.setAttribute("opciones", opciones);
                ArrayList<Arbitro> listaFiltrada = new ArrayList<>();

                if(opcion.equals("nombre")){
                    listaFiltrada = daoArbitros.busquedaNombre(buscar);
                }else{
                    listaFiltrada = daoArbitros.busquedaPais(buscar);
                }

                request.setAttribute("listaArbitros",listaFiltrada);
                view = request.getRequestDispatcher("/arbitros/list.jsp");
                view.forward(request,response);
                break;

            case "guardar":
                String nombre = request.getParameter("nombre");
                String pais = request.getParameter("tipo");
                if(nombre.isBlank()){
                    // está vacío  (espacios)
                    request.getSession().setAttribute("nombre1" ,"Dato Vacío");
                    response.sendRedirect(request.getContextPath() + "/ArbitroServlet?action=crear");
                }else if(daoArbitros.busquedaNombre(nombre).size()>0){
                    // está repetido
                    request.getSession().setAttribute("nombre2","Nombre Repetido");
                    response.sendRedirect(request.getContextPath() + "/ArbitroServlet?action=crear");
                }else{
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
                request.setAttribute("opciones", opciones);
                request.setAttribute("listaArbitros", daoArbitros.listarArbitros());
                view = request.getRequestDispatcher("/arbitros/list.jsp");
                view.forward(request, response);
                break;
            case "crear":
                request.setAttribute("paises", paises);
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
