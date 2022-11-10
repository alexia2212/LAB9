package com.example.lab9_base.Dao;

import com.example.lab9_base.Bean.Seleccion;
import com.example.lab9_base.Bean.Estadio;
import java.sql.Connection;
import java.util.ArrayList;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class DaoSelecciones extends BaseDao{
    public ArrayList<Seleccion> listarSelecciones() {

        ArrayList<Seleccion> selecciones = new ArrayList<>();
        String sql = "SELECT * FROM seleccion";
        //String sql = "INSERT INTO seleccion (idSeleccion, nombre, tecnico, estadio_idEstadio) VALUES (?,?,?,?)";

        try (Connection connection = this.getConnection();
             Statement stmt = connection.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {

            while (rs.next()) {
                Seleccion seleccionDao = new Seleccion();

                seleccionDao.setIdSeleccion(rs.getInt(1));
                seleccionDao.setNombre(rs.getString(2));
                seleccionDao.setTecnico(rs.getString(3));
                Estadio estadio = new Estadio();
                estadio.setIdEstadio(rs.getInt(4));
                seleccionDao.setEstadio(estadio);
                selecciones.add(seleccionDao);

            }

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return selecciones;
    }

}
