package com.example.lab9_base.Dao;
import java.sql.*;
import com.example.lab9_base.Bean.Partido;

import java.util.ArrayList;

public class DaoPartidos {
    public ArrayList<Partido> listaDePartidos() {

        ArrayList<Partido> partidos = new ArrayList<>();
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }

        String user = "root";
        String password = "123456";
        String url = "jdbc:mysql://127.0.0.1:3306/lab9";

        String sql = "select * from partido";

        try (Connection conn = DriverManager.getConnection(url, user, password);
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {

            while (rs.next()) {
                Partido partido = new Partido();
                partido.setIdPartido(rs.getInt(1));
                partido.setNumeroJornada(rs.getInt(2));
                partido.setSeleccionLocal(rs.getString(3));
                partido.setSeleccionVisitante(rs.getString(4));
                partido.setArbitro(rs.getString(5));
                partidos.add(partido);
            }


        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }

        return partidos;
    }

    public void crearPartido(Partido partido) {
        String user = "root";
        String password = "123456";
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
        } catch (ClassNotFoundException e) {
            throw new RuntimeException(e);
        }

        String url = "jdbc:mysql://localhost:3306/lab9";
        String sql = "INSERT INTO partido (numeroJornada,fecha, seleccionLocal, seleccionVisitante, arbitro) VALUES (?,?,?,?,?) ";
        try (Connection conn = DriverManager.getConnection(url, user, password);
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {
                PreparedStatement preparedStatement = conn.prepareStatement(sql);
                preparedStatement.setInt(1, partido.getNumeroJornada());
                preparedStatement.setString(2, partido.getFecha());
                preparedStatement.setString(3, partido.getSeleccionLocal());
                preparedStatement.setString(4, partido.getSeleccionVisitante());
                preparedStatement.setString(5, partido.getArbitro());

                preparedStatement.executeUpdate();

        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
    }




}
