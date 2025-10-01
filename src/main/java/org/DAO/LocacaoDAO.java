package org.DAO;

import org.entidades.Itens;
import org.entidades.Locacoes;

import java.sql.*;
import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.List;

public class LocacaoDAO {
        public void realizaLocacao(Locacoes locacao) throws SQLException {


            try (Connection conexao = Conexao.obterConexao()) {
                if (!clientePodeAlugar(conexao, locacao.getClienteId())) {
                    throw new SQLException(" O Cliente já possui 3 locações ativas.");
                }

                if (!itemDisponivel(conexao, locacao.getItemId())) {
                    throw new SQLException("Não há cópias disponíveis deste item.");
                }

                String sql = "INSERT INTO LOCACOES (CLIENTE_ID, ITEM_ID, DATA_LOCACAO,DATA_PREVISTA_DEVOLUCAO, STATUS_LOCACAO) values (?,?,?,?,?)";

                try (PreparedStatement smt = conexao.prepareStatement(sql)) {
                    smt.setInt(1, locacao.getClienteId());
                    smt.setInt(2, locacao.getItemId());
                    smt.setDate(3, Date.valueOf(locacao.getDataLocacao()));
                    smt.setDate(4, Date.valueOf(locacao.getDataPrevistaDevolucao()));
                    smt.setString(5, "ALUGADA");
                    smt.executeUpdate();

                }

                String sqlUpdateItem = "UPDATE ITENS SET QUANTIDADE_COPIAS = QUANTIDADE_COPIAS - 1 WHERE ID = ?";
                try (PreparedStatement smt = conexao.prepareStatement(sqlUpdateItem)) {
                    smt.setInt(1, locacao.getItemId());
                    smt.executeUpdate();
                    System.out.println("Locação realizada com sucesso!");
                }

                catch (SQLIntegrityConstraintViolationException e) {
                    throw new SQLException(e);
                }

            }
        }
    private boolean clientePodeAlugar(Connection conexao, int clienteId) throws SQLException {
        String sql = "SELECT COUNT(*) FROM LOCACOES WHERE CLIENTE_ID = ? AND STATUS_LOCACAO = 'ALUGADA'";
        try (PreparedStatement smt = conexao.prepareStatement(sql)) {
            smt.setInt(1, clienteId);
            ResultSet rs = smt.executeQuery();
            rs.next();
            return rs.getInt(1) < 3;
        }

    }
    private boolean itemDisponivel(Connection conexao, int itemId) throws SQLException {
        String sql = "SELECT QUANTIDADE_COPIAS FROM ITENS WHERE ID = ?";
        int totalCopias;
        try (PreparedStatement smt = conexao.prepareStatement(sql)) {
            smt.setInt(1, itemId);
            ResultSet rs = smt.executeQuery();
            if (!rs.next()) throw new SQLException("Item não encontrado.");
            totalCopias = rs.getInt("QUANTIDADE_COPIAS");
        }

        String sqlAlugadas = "SELECT COUNT(*) FROM LOCACOES WHERE ITEM_ID = ? AND STATUS_LOCACAO = 'ALUGADA'";
        try (PreparedStatement smt = conexao.prepareStatement(sqlAlugadas)) {
            smt.setInt(1, itemId);
            ResultSet rs = smt.executeQuery();
            rs.next();
            int alugadas = rs.getInt(1);
            return alugadas < totalCopias;
        }
    }
    public void devolverLocacao(int locacaoId) throws SQLException {
        try (Connection conexao = Conexao.obterConexao()) {

            String sql = "SELECT CLIENTE_ID, ITEM_ID, DATA_PREVISTA_DEVOLUCAO, STATUS_LOCACAO FROM LOCACOES WHERE ID = ?";
            int clienteId;
            int itemId;
            LocalDate dataPrevista;
            String status;

            try (PreparedStatement smt = conexao.prepareStatement(sql)) {
                smt.setInt(1, locacaoId);
                ResultSet rs = smt.executeQuery();

                if (!rs.next()) {
                    throw new SQLException("Locação não encontrada.");
                }

                clienteId = rs.getInt("CLIENTE_ID");
                itemId = rs.getInt("ITEM_ID");
                dataPrevista = rs.getDate("DATA_PREVISTA_DEVOLUCAO").toLocalDate();
                status = rs.getString("STATUS_LOCACAO");
            }

            if (!status.equals("ALUGADA")) {
                throw new SQLException("Esta locação não está mais pendente");
            }

            LocalDate dataDevolucao = LocalDate.now();

            long diasAtraso = ChronoUnit.DAYS.between(dataPrevista, dataDevolucao);

            String sqlUpdate = "UPDATE LOCACOES SET STATUS_LOCACAO = ?, DATA_DEVOLUCAO = ? WHERE ID = ?";
            try (PreparedStatement smt = conexao.prepareStatement(sqlUpdate)) {
                smt.setString(1, "DEVOLVIDA");
                smt.setDate(2, Date.valueOf(dataDevolucao));
                smt.setInt(3, locacaoId);
                smt.executeUpdate();
            }

            String sqlUpdateItem = "UPDATE ITENS SET QUANTIDADE_COPIAS = QUANTIDADE_COPIAS + 1 WHERE ID = ?";
            try (PreparedStatement smt = conexao.prepareStatement(sqlUpdateItem)) {
                smt.setInt(1, itemId);
                smt.executeUpdate();
            }

            System.out.println("Locação devolvida com sucesso!");

        } catch (SQLException e) {
            throw new SQLException("Erro ao devolver locação: " + e.getMessage(), e);
        }
    }
    public List<Locacoes> buscarLocacoes() throws SQLException {
        String sql = "SELECT * FROM LOCACOES";

        List<Locacoes> locacoesLista = new ArrayList<>();

        try (Connection conexao = Conexao.obterConexao(); PreparedStatement smt = conexao.prepareStatement(sql)) {
            try (ResultSet rs = smt.executeQuery()) {
                while (rs.next()) {


                    Locacoes locacao = new Locacoes(rs.getInt("ID"),
                            rs.getInt("CLIENTE_ID"),
                            rs.getInt("ITEM_ID"),
                            rs.getDate("DATA_LOCACAO").toLocalDate(),
                            rs.getDate("DATA_PREVISTA_DEVOLUCAO").toLocalDate(),
                            toLocalDateNullable(rs.getDate("DATA_DEVOLUCAO")),
                            rs.getString("STATUS_LOCACAO"));

                    locacoesLista.add(locacao);
                }

            } catch (SQLException e) {
                throw new SQLException(e);
            }
        }
        return locacoesLista;
    }
    private LocalDate toLocalDateNullable(java.sql.Date date) {
        return date != null ? date.toLocalDate() : null;
    }
}

