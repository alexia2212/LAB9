package com.example.lab9_base.Controller;

import com.example.lab9_base.Bean.Partido;
import com.example.lab9_base.Dao.DaoPartidos;
import jakarta.servlet.*;
import jakarta.servlet.http.*;
import jakarta.servlet.annotation.*;
import com.example.lab9_base.Bean.Seleccion;
import com.example.lab9_base.Dao.DaoSelecciones;
import com.example.lab9_base.Bean.Arbitro;
import com.example.lab9_base.Dao.DaoArbitros;

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
            case "guardar":
                String jornadaSer = request.getParameter("numeroJornada");
                int jonadaNum = Integer.parseInt(jornadaSer);
                String fechaSer = request.getParameter("fecha");
                String seleccionLocalSer = request.getParameter("seleccionLocal");
                int seleccionLocalID = Integer.parseInt(seleccionLocalSer);
                String seleccionVisitanteSer = request.getParameter("seleccionVisitante");
                int selecionVisitaneID = Integer.parseInt(seleccionVisitanteSer);
                String arbitro = request.getParameter("arbitro");
                int arbitroSer = Integer.parseInt(arbitro);

                try{
                    Partido partidoNew = new Partido();
                    Seleccion seleccionNew = new Seleccion();
                    Seleccion seleccionNeww = new Seleccion();
                    Arbitro arbitro1 = new Arbitro();
                    partidoNew.setNumeroJornada(jonadaNum);
                    partidoNew.setFecha(fechaSer);
                    seleccionNew.setIdSeleccion(seleccionLocalID);
                    partidoNew.setSeleccionLocal(seleccionNew);
                    seleccionNeww.setIdSeleccion(selecionVisitaneID);
                    partidoNew.setSeleccionVisitante(seleccionNeww);
                    arbitro1.setIdArbitro(arbitroSer);
                    partidoNew.setArbitro(arbitro1);
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
        DaoArbitros daoArbitros = new DaoArbitros();
        DaoPartidos daoPartidos = new DaoPartidos();
        DaoSelecciones daosSeleciones = new DaoSelecciones();
        DaoSelecciones daoSeleccioness = new DaoSelecciones();
        switch (action) {
            case "lista":
                request.setAttribute("Lista", daoPartidos.listaDePartidos());
                view = request.getRequestDispatcher("index.jsp");
                view.forward(request, response);
                break;
            case "crear":
                request.setAttribute("listaArbitro", daoArbitros.listarArbitros());
                request.setAttribute("ListaSelecciones", daosSeleciones.listarSelecciones());
                request.setAttribute("listaSleccioness", daoSeleccioness.listarSelecciones());
                view = request.getRequestDispatcher("partidos/form.jps");
                view.forward(request,response);
                break;
        }

    }
}
