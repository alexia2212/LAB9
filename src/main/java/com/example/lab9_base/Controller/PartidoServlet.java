package com.example.lab9_base.Controller;

import com.example.lab9_base.Bean.Partido;
import com.example.lab9_base.Dao.DaoPartidos;
import jakarta.servlet.*;
import jakarta.servlet.http.*;
import jakarta.servlet.annotation.*;

import java.io.IOException;

@WebServlet(name = "PartidoServlet", urlPatterns = {"/PartidoServlet", ""})
public class PartidoServlet extends HttpServlet {
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String action = request.getParameter("action") == null ? "guardar" : request.getParameter("action");
        RequestDispatcher view;
        DaoPartidos daoPartidos = new DaoPartidos();
        switch (action) {

            case "guardar":
                String jornadaSer = request.getParameter("numeroJornada");
                int jonadaNum = Integer.parseInt(jornadaSer);
                String fechaSer = request.getParameter("fecha");
                String seleccionLocalSer = request.getParameter("seleccionLocal");
                String seleccionVisitanteSer = request.getParameter("seleccionVisitante");
                String arbitro = request.getParameter("arbitro");

                try{
                    Partido partido = new Partido();
                    partido.setNumeroJornada(jonadaNum);
                    partido.setFecha(fechaSer);
                    partido.setSeleccionLocal(seleccionLocalSer);
                    partido.setSeleccionVisitante(seleccionVisitanteSer);
                    partido.setArbitro(arbitro);
                    daoPartidos.crearPartido(partido);
                    response.sendRedirect(request.getContextPath()+"/PartidoServlet");
                }catch (NumberFormatException e){
                    response.sendRedirect(request.getContextPath()+"/PartidoServlet?action=crear");
                }
                break;

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
