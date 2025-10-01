package org.entidades;

import java.time.LocalDate;

public class Itens {

    private int id;
    private String titulo;
    private String categoria;
    private LocalDate dataLancamento;   // << aqui
    private String classificacaoEtaria;
    private int quantidadeCopias;

    public Itens(int id, String titulo, String categoria, LocalDate dataLancamento, String classificacaoEtaria, int quantidadeCopias) {
        this.id = id;
        this.titulo = titulo;
        this.categoria = categoria;
        this.dataLancamento = dataLancamento;
        this.classificacaoEtaria = classificacaoEtaria;
        this.quantidadeCopias = quantidadeCopias;
    }

    public Itens(String titulo, String categoria, LocalDate dataLancamento, String classificacaoEtaria, int quantidadeCopias) {
        this.titulo = titulo;
        this.categoria = categoria;
        this.dataLancamento = dataLancamento;
        this.quantidadeCopias = quantidadeCopias;
        this.classificacaoEtaria = classificacaoEtaria;
    }


    public String getTitulo() {
        return titulo;
    }

    public void setTitulo(String titulo) {
        this.titulo = titulo;
    }

    public String getCategoria() {
        return categoria;
    }

    public void setCategoria(String categoria) {
        this.categoria = categoria;
    }

    public LocalDate getDataLancamento() {
        return dataLancamento;
    }

    public void setDataLancamento(LocalDate dataLancamento) {
        this.dataLancamento = dataLancamento;
    }

    public String getClassificacaoEtaria() {
        return classificacaoEtaria;
    }

    public void setClassificacaoEtaria(String classificacaoEtaria) {
        this.classificacaoEtaria = classificacaoEtaria;
    }

    public int getQuantidadeCopias() {
        return quantidadeCopias;
    }

    public void setQuantidadeCopias(int quantidadeCopias) {
        this.quantidadeCopias = quantidadeCopias;
    }

    @Override
    public String toString() {
        return  "[" + id + "]" +
                "| Titulo= " + titulo +
                "| Categoria= " + categoria +
                "| Data de Lancamento= " + dataLancamento +
                "| Classificação Etaria= " + classificacaoEtaria +
                "| QuantidadeCopias= " + quantidadeCopias;
    }
}
