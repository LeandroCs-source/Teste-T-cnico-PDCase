package org.entidades;

import java.time.LocalDate;

public class Locacoes {

    private int id;
    private int clienteId;
    private int itemId;
    private LocalDate dataLocacao;
    private LocalDate dataPrevistaDevolucao;
    private LocalDate dataDevolucao;
    private String status;


    public Locacoes(int clienteId, int itemId, LocalDate dataLocacao) {
        this.clienteId = clienteId;
        this.itemId = itemId;
        this.dataLocacao = dataLocacao;
        this.dataPrevistaDevolucao = dataLocacao.plusDays(7);
    }

    public Locacoes(int id, int clienteId, int itemId, LocalDate dataLocacao, LocalDate dataPrevistaDevolucao, LocalDate dataDevolucao, String status) {
        this.id = id;
        this.clienteId = clienteId;
        this.itemId = itemId;
        this.dataLocacao = dataLocacao;
        this.dataPrevistaDevolucao = dataPrevistaDevolucao;
        this.dataDevolucao = dataDevolucao;
        this.status = status;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getClienteId() {
        return clienteId;
    }

    public void setClienteId(int clienteId) {
        this.clienteId = clienteId;
    }

    public int getItemId() {
        return itemId;
    }

    public void setItemId(int itemId) {
        this.itemId = itemId;
    }

    public LocalDate getDataLocacao() {
        return dataLocacao;
    }

    public void setDataLocacao(LocalDate dataLocacao) {
        this.dataLocacao = dataLocacao;
    }

    public LocalDate getDataPrevistaDevolucao() {
        return dataPrevistaDevolucao;
    }

    public LocalDate getDataDevolucao() {
        return dataDevolucao;
    }

    public void setDataDevolucao(LocalDate dataDevolucao) {
        this.dataDevolucao = dataDevolucao;
    }

    public String getStatus(String alugada) {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    @Override
    public String toString() {
        return  "[" + id + "]"+
                "| ID do Cliente= " + clienteId +
                "| ID do Item= " + itemId +
                "| Data de Locacao= " + dataLocacao +
                "| Previsão de devolução= " + dataPrevistaDevolucao +
                "| Data da devolução= " + dataDevolucao +
                "| Status=" + status ;
    }
}
