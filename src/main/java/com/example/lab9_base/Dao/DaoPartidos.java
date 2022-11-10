package com.example.lab9_base.Dao;
import java.sql.*;

import com.example.lab9_base.Bean.Arbitro;
import com.example.lab9_base.Bean.Partido;

import java.util.ArrayList;

public class DaoPartidos extends BaseDao{
    public ArrayList<Partido> listarPartidos() {

        ArrayList<Partido> listaPartidos = new ArrayList<>();
        String sql = "select * from partido";

        try (Connection connection = this.getConnection();
             Statement stmt = connection.createStatement();
             ResultSet rs = stmt.executeQuery(sql);) {

            while(rs.next()){
                Partido partido = new Partido();
                partido.setIdPartido(rs.getInt(1));
                partido.setFecha(rs.getString(2));
                partido.setNumeroJornada(rs.getInt(3));
                partido.setSeleccionLocal(rs.getString(4));
                partido.setSeleccionVisitante(rs.getString(5));
                partido.setArbitro(rs.getString(6));

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
            pstmt1.setString(3, partido.getSeleccionLocal());
            pstmt1.setString(4, partido.getSeleccionVisitante());
            pstmt1.setString(5, partido.getArbitro());

            pstmt1.executeUpdate();

        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
    }

    public ArrayList<Partido> busqueda(String idPartido){
        String sql = "select * from partido where idPartido like ?";
        String sql1 = "select * from arbitro";

        ArrayList<Partido> listaFiltradaID = new ArrayList<>();

        try(Connection conn = this.getConnection();
            PreparedStatement pstmt = conn.prepareStatement(sql);){
            pstmt.setString(1,"%"+idPartido+"%");


            try(ResultSet rs = pstmt.executeQuery();){
                while(rs.next()){
                    Partido partido = new Partido();

                    partido.setIdPartido(rs.getInt("idPartido"));
                    partido.setFecha(rs.getString("fecha"));
                    partido.setSeleccionLocal(rs.getString("seleccionLocal"));
                    partido.setSeleccionVisitante(rs.getString("seleccionVisitante"));
                    partido.setArbitro(rs.getString("arbitro"));


                    try (Connection connection2 = this.getConnection();
                         Statement stmt2 = connection2.createStatement(ResultSet.TYPE_SCROLL_SENSITIVE, ResultSet.CONCUR_UPDATABLE);
                         ResultSet rs1 = stmt2.executeQuery(sql1);) {

                    } catch (SQLException e) {
                        throw new RuntimeException(e);
                    }

                    listaFiltradaID.add(partido);
                }
            }
        }catch (SQLException e){
            e.printStackTrace();
        }

        return listaFiltradaID;
    }


}
