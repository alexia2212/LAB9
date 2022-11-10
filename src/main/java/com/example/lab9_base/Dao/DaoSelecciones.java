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

        try (Connection connection = this.getConnection();
             Statement stmt = connection.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {

            while (rs.next()) {
                Seleccion seleccion = new Seleccion();
                Estadio estadio = new Estadio();
                seleccion.setIdSeleccion(rs.getInt(1));
                seleccion.setNombre(rs.getString(2));
                seleccion.setTecnico(rs.getString(3));
                estadio.setIdEstadio(rs.getInt(4));
                seleccion.setEstadio(estadio);
                selecciones.add(seleccion);
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return selecciones;
    }

}
