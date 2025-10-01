package org.DAO;

import org.entidades.Clientes;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class ClientesDAO {

    public void inserirClientes(Clientes cliente) throws SQLException {

        String sql = "insert into clientes (NOME, CPF, TELEFONE, EMAIL) values (?,?,?,?)";

        try (Connection conexao = Conexao.obterConexao(); PreparedStatement smt = conexao.prepareStatement(sql)) {
            smt.setString(1, cliente.getNome());
            smt.setString(2, cliente.getCpf());
            smt.setString(3, cliente.getTelefone());
            smt.setString(4, cliente.getEmail());
            smt.executeUpdate();

        } catch (SQLException e) {
            throw new SQLException(e);
        }
    }

    public List<Clientes> buscarCLientes() throws SQLException {
        String sql = "SELECT * FROM CLIENTES";

        List<Clientes> clientesLista = new ArrayList<>();

        try (Connection conexao = Conexao.obterConexao(); PreparedStatement smt = conexao.prepareStatement(sql)) {
            try (ResultSet rs = smt.executeQuery()) {
                while (rs.next()) {
                    Clientes clientes = new Clientes(rs.getInt("ID"),
                            rs.getString("NOME"),
                            rs.getString("CPF"),
                            rs.getString("TELEFONE"),
                            rs.getString("EMAIL"));

                    clientesLista.add(clientes);
                }

            } catch (SQLException e) {
                throw new SQLException(e);
            }
        }
        return clientesLista;
    }
    public void excluirClientes(int id) throws SQLException {

        String sql = "DELETE FROM CLIENTES WHERE ID = ?";

        try (Connection conexao = Conexao.obterConexao(); PreparedStatement smt = conexao.prepareStatement(sql)) {
            smt.setInt(1, id);
            smt.executeUpdate();

        } catch (SQLException e) {
            throw new SQLException(e);
        }
    }
}
