package com.example.lab9_base.Controller;

import com.example.lab9_base.Bean.Arbitro;
import com.example.lab9_base.Bean.Partido;
import com.example.lab9_base.Bean.Seleccion;
import com.example.lab9_base.Dao.DaoPartidos;
import jakarta.servlet.*;
import jakarta.servlet.http.*;
import jakarta.servlet.annotation.*;
import com.example.lab9_base.Dao.DaoSelecciones;
import com.example.lab9_base.Dao.DaoArbitros;
import java.io.IOException;
import java.sql.SQLException;
import java.util.ArrayList;

@WebServlet(name = "PartidoServlet", urlPatterns = {"/PartidoServlet",""})
public class PartidoServlet extends HttpServlet {
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String action = request.getParameter("action") == null ? "lista" : request.getParameter("action");
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
                //Partido partido = new Partido();
                String jornadaPartido = request.getParameter("jornada");
                int ID_jornada =Integer.parseInt(jornadaPartido);
                //partido.setNumeroJornada(ID_jornada);
                String fechaPartido = request.getParameter("fecha");
                //partido.setFecha(fechaPartido);

                Seleccion seleccionI = new Seleccion();
                String localPartido = request.getParameter("seleccionLocal");
                int ID_localPartido = Integer.parseInt(localPartido);
                seleccionI.setIdSeleccion(ID_localPartido);

                Seleccion seleccionII = new Seleccion();
                String visitantePartido = request.getParameter("seleccionVisitante");
                int ID_visitantePartido = Integer.parseInt(visitantePartido);
                seleccionII.setIdSeleccion(ID_visitantePartido);

                Arbitro arbitroI = new Arbitro();
                String arbitro = request.getParameter("arbitro");
                int ID_arbitroPartido = Integer.parseInt(arbitro);
                arbitroI.setIdArbitro(ID_arbitroPartido);
                /*
                partido.setSeleccionLocal(seleccionI);
                partido.setSeleccionVisitante(seleccionII);
                partido.setArbitro(arbitroI);
                daoPartidos.crearPartido(partido);

                response.sendRedirect(request.getContextPath()+ "/PartidoServlet?action=crear");*/
                try{
                    Partido partido = new Partido();
                    partido.setNumeroJornada(ID_jornada);
                    partido.setFecha(fechaPartido);
                    partido.setSeleccionLocal(seleccionI);
                    partido.setSeleccionVisitante(seleccionII);
                    partido.setArbitro(arbitroI);
                    daoPartidos.crearPartido(partido);
                    response.sendRedirect(request.getContextPath()+ "/PartidoServlet?");

                }catch (NumberFormatException e){
                    response.sendRedirect(request.getContextPath() + "/PartidoServlet?action=crear");
                }
        }
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

        String action = request.getParameter("action") == null ? "Lista" : request.getParameter("action");
        RequestDispatcher view;
        DaoArbitros daoArbitros = new DaoArbitros();
        DaoPartidos daoPartidos = new DaoPartidos();
        DaoSelecciones selecionesDaoI = new DaoSelecciones();
        DaoSelecciones seleccionesDaoII = new DaoSelecciones();
        switch (action) {
            case "Lista":
                request.setAttribute("Lista", daoPartidos.listaDePartidos());
                view = request.getRequestDispatcher("index.jsp");
                view.forward(request, response);
                break;
            case "crear":
                request.setAttribute("listaSeleccionesI", selecionesDaoI.listarSelecciones());
                request.setAttribute("listaSeleccionesII", seleccionesDaoII.listarSelecciones());
                request.setAttribute("listaArbitros", daoArbitros.listarArbitros());
                view = request.getRequestDispatcher("partidos/form.jsp");
                view.forward(request,response);
                break;
        }

    }
}
