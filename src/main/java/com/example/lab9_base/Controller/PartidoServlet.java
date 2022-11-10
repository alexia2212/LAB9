package com.example.lab9_base.Controller;

import com.example.lab9_base.Bean.Partido;
import com.example.lab9_base.Dao.DaoPartidos;
import jakarta.servlet.*;
import jakarta.servlet.http.*;
import jakarta.servlet.annotation.*;

import java.io.IOException;
import java.sql.SQLException;
import java.util.ArrayList;

@WebServlet(name = "PartidoServlet", urlPatterns = {"/PartidoServlet"})
public class PartidoServlet extends HttpServlet {
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String action = request.getParameter("action") == null ? "guardar" : request.getParameter("action");
        RequestDispatcher view;
        ArrayList<String> opciones = new ArrayList<>();
        opciones.add("seleccionLocal");
        opciones.add("seleccionVisitante");
        opciones.add("arbitro");
        opciones.add("fecha");
        opciones.add("numeroJornada");
        DaoPartidos daoPartidos = new DaoPartidos();
        switch (action) {
            case "buscar":
                String buscarPar = request.getParameter("keyword");
                ArrayList<Partido> listaFiltrada = daoPartidos.busqueda(buscarPar);

                request.setAttribute("listaPartido", listaFiltrada);
                view= request.getRequestDispatcher("/index.jsp");
                view.forward(request, response);
                break;

            case "guardar":
                String jornadaSer = request.getParameter("numeroJornada");
                int jonadaNum = Integer.parseInt(jornadaSer);
                String fechaSer = request.getParameter("fecha");
                String seleccionLocalSer = request.getParameter("seleccionLocal");
                String seleccionVisitanteSer = request.getParameter("seleccionVisitante");
                String arbitro = request.getParameter("arbitro");

                try{
                    Partido partidoNew = new Partido();

                    partidoNew.setNumeroJornada(jonadaNum);
                    partidoNew.setFecha(fechaSer);
                    partidoNew.setSeleccionLocal(seleccionLocalSer);
                    partidoNew.setSeleccionVisitante(seleccionVisitanteSer);
                    partidoNew.setArbitro(arbitro);

                    daoPartidos.crearPartido(partidoNew);
                    response.sendRedirect(request.getContextPath()+"/PartidoServlet");
                }catch (NumberFormatException e){
                    response.sendRedirect(request.getContextPath()+"/PartidoServlet?action=crear");
                }

        }
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

        String action = request.getParameter("action") == null ? "lista" : request.getParameter("action");
        RequestDispatcher view;
        switch (action) {
            case "lista":
                request.setAttribute("Lista", action);
                view = request.getRequestDispatcher("index.jsp");
                view.forward(request, response);
                break;
            case "crear":
                view = request.getRequestDispatcher("/PartidoServlet?action=crear");
                view.forward(request,response);
                break;
        }

    }
}
