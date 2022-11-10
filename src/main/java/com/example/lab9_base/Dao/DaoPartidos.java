package com.example.lab9_base.Dao;
import java.sql.*;

import com.example.lab9_base.Bean.Arbitro;
import com.example.lab9_base.Bean.Partido;
import com.example.lab9_base.Bean.Estadio;
import com.example.lab9_base.Bean.Seleccion;

import java.util.ArrayList;

public class DaoPartidos extends BaseDao{
    public ArrayList<Partido> listaDePartidos() {

        ArrayList<Partido> listaPartidos = new ArrayList<>();
        String sql = "select p.idPartido, p.numeroJornada, p.fecha, l.nombre, v.nombre, e.nombre, a.nombre\n" +
                "from partido p\n" +
                "    inner join arbitro a on p.arbitro = a.idArbitro\n" +
                "inner join seleccion l on p.seleccionLocal = l.idSeleccion\n" +
                "inner join seleccion v on p.seleccionVisitante = v.idSeleccion\n" +
                "inner join estadio e on l.estadio_idEstadio = e.idEstadio;";

        try (Connection connection = this.getConnection();
             Statement stmt = connection.createStatement();
             ResultSet rs = stmt.executeQuery(sql);) {

            while(rs.next()){
                Partido partido = new Partido();
                Seleccion local = new Seleccion();
                Seleccion visitante = new Seleccion();
                Arbitro arbitro = new Arbitro();
                Estadio estadioLocal = new Estadio();

                partido.setIdPartido(rs.getInt(1));
                partido.setNumeroJornada(rs.getInt(2));
                partido.setFecha(rs.getString(3));
                partido.setSeleccionLocal(local);
                local.setNombre(rs.getString(4));
                partido.setSeleccionVisitante(visitante);
                visitante.setNombre(rs.getString(5));
                local.setEstadio(estadioLocal);
                estadioLocal.setNombre(rs.getString(6));
                partido.setArbitro(arbitro);
                arbitro.setNombre(rs.getString(7));
                listaPartidos.add(partido);
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return listaPartidos;
    }

    public void crearPartido(Partido partido) {

        String sql = "INSERT INTO partido (numeroJornada,fecha, seleccionLocal, seleccionVisitante, arbitro) VALUES (?,?,?,?,?) ";
        try (Connection conn1 = this.getConnection();
             PreparedStatement pstmt1 = conn1.prepareStatement(sql)){
            pstmt1.setInt(1, partido.getSeleccionLocal().getIdSeleccion());
            pstmt1.setInt(2, partido.getSeleccionVisitante().getIdSeleccion());
            pstmt1.setInt(3, partido.getArbitro().getIdArbitro());
            pstmt1.setString(4, partido.getFecha());
            pstmt1.setInt(5, partido.getNumeroJornada());


            pstmt1.executeUpdate();

        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
    }


}
