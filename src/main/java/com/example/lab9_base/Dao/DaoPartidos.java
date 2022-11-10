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
        String sql = "select * from partido";

        try (Connection connection = this.getConnection();
             Statement stmt = connection.createStatement();
             ResultSet rs = stmt.executeQuery(sql);) {

            while(rs.next()){
                Partido partido = new Partido();
                Seleccion seleccionLocal = new Seleccion();
                Seleccion seleccionVisitante = new Seleccion();
                Estadio estadio = new Estadio();
                Arbitro arbitro = new Arbitro();
                partido.setIdPartido(rs.getInt(1));
                partido.setFecha(rs.getString(2));
                partido.setNumeroJornada(rs.getInt(3));
                seleccionLocal.setNombre(rs.getString(4));
                partido.setSeleccionLocal(seleccionLocal);
                seleccionVisitante.setNombre(rs.getString(5));
                partido.setSeleccionVisitante(seleccionVisitante);
                estadio.setNombre(rs.getString(6));
                seleccionLocal.setEstadio(estadio);
                arbitro.setNombre(rs.getString(7));
                partido.setArbitro(arbitro);

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
            pstmt1.setInt(1, partido.getNumeroJornada());
            pstmt1.setString(2, partido.getFecha());
            pstmt1.setInt(3, partido.getSeleccionLocal().getIdSeleccion());
            pstmt1.setInt(4, partido.getSeleccionVisitante().getIdSeleccion());
            pstmt1.setInt(5, partido.getArbitro().getIdArbitro());

            pstmt1.executeUpdate();

        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
    }


}
