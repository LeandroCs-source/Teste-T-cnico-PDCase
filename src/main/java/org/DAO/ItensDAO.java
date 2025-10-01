package org.DAO;

import org.entidades.Itens;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class ItensDAO {
    public void inserirItens(Itens item) throws SQLException {

        String sql = "INSERT INTO ITENS (TITULO, CATEGORIA, DATA_LANCAMENTO, CLASSIFICACAO_ETARIA, QUANTIDADE_COPIAS) values (?,?,?,?,?)";

        try (Connection conexao = Conexao.obterConexao(); PreparedStatement smt = conexao.prepareStatement(sql)) {
            smt.setString(1, item.getTitulo());
            smt.setString(2, item.getCategoria());
            smt.setDate(3, Date.valueOf(item.getDataLancamento()));
            smt.setString(4, item.getClassificacaoEtaria());
            smt.setInt(5, item.getQuantidadeCopias());
            smt.executeUpdate();

        } catch (SQLException e) {
            throw new SQLException(e);
        }
    }

    public List<Itens> buscarItens() throws SQLException {
        String sql = "SELECT * FROM ITENS";

        List<Itens> itemLista = new ArrayList<>();


        try (Connection conexao = Conexao.obterConexao(); PreparedStatement smt = conexao.prepareStatement(sql)) {
            try (ResultSet rs = smt.executeQuery()) {
                while (rs.next()) {
                    Itens item = new Itens(rs.getInt("ID"),
                            rs.getString("TITULO"),
                            rs.getString("CATEGORIA"),
                            rs.getDate("DATA_LANCAMENTO").toLocalDate(),
                            rs.getString("CLASSIFICACAO_ETARIA"),
                            rs.getInt("QUANTIDADE_COPIAS"));

                    itemLista.add(item);
                }

            } catch (SQLException e) {
                throw new SQLException(e);
            }
        }
        return itemLista;
    }
    public void excluirItens(int id) throws SQLException {

        String sql = "DELETE FROM ITENS WHERE ID = ?";

        try (Connection conexao = Conexao.obterConexao(); PreparedStatement smt = conexao.prepareStatement(sql)) {
            smt.setInt(1, id);
            smt.executeUpdate();

        } catch (SQLException e) {
            throw new SQLException(e);
        }
    }
}

