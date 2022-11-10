package com.example.lab9_base.Dao;

import com.example.lab9_base.Bean.Arbitro;

import java.sql.*;
import java.util.ArrayList;

public class DaoArbitros extends BaseDao{
    public ArrayList<Arbitro> listarArbitros() {
        ArrayList<Arbitro> listaArbitros = new ArrayList<>();

        String sql = "select * from arbitro";

        try (Connection connection = this.getConnection();
             Statement stmt = connection.createStatement();
             ResultSet rs = stmt.executeQuery(sql);) {
            while(rs.next()){
                Arbitro arbitro = new Arbitro();

                arbitro.setIdArbitro(rs.getInt(1));
                arbitro.setNombre(rs.getString(2));
                arbitro.setPais(rs.getString(3));

                listaArbitros.add(arbitro);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return listaArbitros;
    }



    public void crearArbitro(Arbitro arbitro) throws SQLException {

        String sql = "insert into arbitro (nombre,pais) values(?,?)";

        try (Connection conn1 = this.getConnection();
             PreparedStatement pstmt1 = conn1.prepareStatement(sql)) {

            pstmt1.setString(1,arbitro.getNombre());
            pstmt1.setString(2, arbitro.getPais());

            pstmt1.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }

    }

    public ArrayList<Arbitro> busquedaPais(String pais) {

        String sql = "select * from arbitro where pais like ?";
        String sql2 = "select * from arbitro";

        ArrayList<Arbitro> listaFiltradaPais = new ArrayList<>();


        try(Connection conn = this.getConnection();
            PreparedStatement pstmt = conn.prepareStatement(sql);){
            pstmt.setString(1,"%"+pais+"%");


            try(ResultSet rs = pstmt.executeQuery();){
                while(rs.next()){
                    Arbitro arbitro = new Arbitro();

                    arbitro.setIdArbitro(rs.getInt("idArbitro"));
                    arbitro.setNombre(rs.getString("nombre"));
                    arbitro.setPais(rs.getString("pais"));


                    try (Connection connection2 = this.getConnection();
                         Statement stmt2 = connection2.createStatement(ResultSet.TYPE_SCROLL_SENSITIVE, ResultSet.CONCUR_UPDATABLE);
                         ResultSet rs1 = stmt2.executeQuery(sql2);) {

                    } catch (SQLException e) {
                        throw new RuntimeException(e);
                    }

                    listaFiltradaPais.add(arbitro);
                }
            }
        }catch (SQLException e){
            e.printStackTrace();
        }

        return listaFiltradaPais;
    }



    public ArrayList<Arbitro> busquedaNombre(String nombre) {

        String sql = "select * from arbitro where nombre like ?";
        String sql1 = "select * from arbitro";


        ArrayList<Arbitro> listaFiltradaNombre = new ArrayList<>();


        try(Connection conn = this.getConnection();
            PreparedStatement pstmt = conn.prepareStatement(sql);){
            pstmt.setString(1,"%"+nombre+"%");


            try(ResultSet rs = pstmt.executeQuery();){
                while(rs.next()){
                    Arbitro arbitro = new Arbitro();

                    arbitro.setIdArbitro(rs.getInt("idArbitro"));
                    arbitro.setNombre(rs.getString("nombre"));
                    arbitro.setPais(rs.getString("pais"));


                    try (Connection connection2 = this.getConnection();
                         Statement stmt2 = connection2.createStatement(ResultSet.TYPE_SCROLL_SENSITIVE, ResultSet.CONCUR_UPDATABLE);
                         ResultSet rs1 = stmt2.executeQuery(sql1);) {

                    } catch (SQLException e) {
                        throw new RuntimeException(e);
                    }

                    listaFiltradaNombre.add(arbitro);
                }
            }
        }catch (SQLException e){
            e.printStackTrace();
        }


        return listaFiltradaNombre;
    }

    public Arbitro buscarArbitro(int id) {
        Arbitro ar = null;
        String sql = "SELECT * FROM arbitro WHERE idArbitro = ?";
        try (Connection conn5 = this.getConnection();
             PreparedStatement pstmt5 = conn5.prepareStatement(sql)){
            pstmt5.setInt(1, id);
            try (ResultSet rs = pstmt5.executeQuery()) {
                if (rs.next()) {
                    ar = new Arbitro();
                    ar.setIdArbitro(id);
                    ar.setNombre(rs.getString("nombre"));
                    ar.setPais(rs.getString("pais"));
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return ar;
    }
    public void borrarArbitro(int id) {
        String sql = "delete from arbitro where idArbitro = ?";

        try (Connection conn6 = this.getConnection();
             PreparedStatement pstmt7 = conn6.prepareStatement(sql);) {

            pstmt7.setInt(1,id);
            pstmt7.executeUpdate();

        } catch (SQLException e) {
            e.printStackTrace();
        }

    }
}
