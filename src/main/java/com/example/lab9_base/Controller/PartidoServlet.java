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
                String jornadaPartido = request.getParameter("numeroJornada");
                int ID_jornada =Integer.parseInt(jornadaPartido);
                Seleccion seleccionI = new Seleccion();
                String seleccionLocal = request.getParameter("seleccionLocal");
                int ID_localPartido = Integer.parseInt(seleccionLocal);
                seleccionI.setIdSeleccion(ID_localPartido);

                Seleccion seleccionII = new Seleccion();
                String seleccionVisitante = request.getParameter("seleccionVisitante");
                int ID_visitantePartido = Integer.parseInt(seleccionVisitante);
                seleccionII.setIdSeleccion(ID_visitantePartido);

                Arbitro arbitroI = new Arbitro();
                String arbitroSer = request.getParameter("arbitro");
                int ID_arbitroPartido = Integer.parseInt(arbitroSer);
                arbitroI.setIdArbitro(ID_arbitroPartido);
                String fechaSer = request.getParameter("fecha");
                try{
                    Partido newpartido = new Partido();
                    newpartido.setNumeroJornada(ID_jornada);
                    newpartido.setSeleccionLocal(seleccionI);
                    newpartido.setSeleccionVisitante(seleccionII);
                    newpartido.setArbitro(arbitroI);
                    newpartido.setFecha(fechaSer);


                    daoPartidos.crearPartido(newpartido);
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
