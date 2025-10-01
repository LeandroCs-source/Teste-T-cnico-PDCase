package org.example;

import org.DAO.ItensDAO;
import org.DAO.LocacaoDAO;
import org.entidades.Clientes;
import org.entidades.Itens;
import org.entidades.Locacoes;
import org.DAO.ClientesDAO;

import java.sql.SQLException;
import java.text.ParseException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.List;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) throws ParseException {

      Scanner sc = new Scanner(System.in);

        int opcao;
        do {
            exibirMenu();
            opcao = sc.nextInt();
            switch (opcao) {
                case 1 -> cadastroCliente();
                case 2 -> cadastroItem();
                case 3 -> registroLocacao();
                case 4 -> devolverLocacao();
                case 5 -> listarClientes();
                case 6 -> listarItens();
                case 7 -> listarLocacao();
                case 8 -> excluirClientes();
                case 9 -> excluirItem();
                case 0 -> System.out.println("Aplicação encerrada!");
                default -> System.out.println("Opção inválida!");
            }
        } while (opcao != 0);

        sc.close();
    }

    private static void exibirMenu() {
        System.out.println("===============================================");
        System.out.println("|               LOCADORA DATABASE             |");
        System.out.println("===============================================");
        System.out.println("| 1 - Cadastrar Cliente                       |");
        System.out.println("| 2 - Cadastrar Item                          |");
        System.out.println("| 3 - Registrar Locação                       |");
        System.out.println("| 4 - Devolver Locação                        |");
        System.out.println("| 5 - Listar Clientes                         |");
        System.out.println("| 6 - Listar Itens                            |");
        System.out.println("| 7 - Listar Locações                         |");
        System.out.println("| 8 - Excluir Clientes                        |");
        System.out.println("| 9 - Excluir Itens                           |");
        System.out.println("| 0 - Sair                                    |");
        System.out.println("===============================================");
        System.out.println();
        System.out.print("Digite uma opção: ");
    }
    private static void cadastroCliente(){
        Scanner sc = new Scanner(System.in);
        System.out.println("****** Cadastrar Cliente ******** ");
        System.out.print("Digite o nome do cliente: ");
        String name = sc.nextLine();
        System.out.print("Digite o CPF do cliente: ");
        String cpf = sc.nextLine();
        System.out.print("Digite o Telefone do cliente: ");
        String telefone = sc.nextLine();
        System.out.print("Digite o E-mail do cliente: ");
        String email = sc.nextLine();

        Clientes cliente = new Clientes(name, cpf, telefone, email);
        ClientesDAO clientesDAO = new ClientesDAO();

        try {
            clientesDAO.inserirClientes(cliente);
            System.out.println("Cliente inserido com sucesso!");
            System.out.println();
        } catch (SQLException e) {
            System.err.println(e.getMessage());
        }


    }

    private static void cadastroItem(){
        Scanner sc = new Scanner(System.in);
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");
        System.out.println("****** Cadastrar Item ******** ");
        System.out.print("Digite o título do item: ");
        String titulo = sc.nextLine();
        System.out.print("Digite a categoria do item (Filme / Jogo): ");
        String categoria = sc.nextLine();
        System.out.print("Digite a data de lançamento do seu item (dd/MM/yyyy): ");
        LocalDate dataLancamento = LocalDate.parse(sc.next(), formatter);
        sc.nextLine();
        System.out.print("Digite a classificação etária do item: ");
        String classificacaoEtaria = sc.nextLine();
        System.out.print("Digite a quantidade de cópias: ");
        int quantidadeCopias = sc.nextInt();

        Itens item = new Itens(titulo, categoria, dataLancamento, classificacaoEtaria,quantidadeCopias);
        ItensDAO itensDAO = new ItensDAO();

        try {
            itensDAO.inserirItens(item);
            System.out.println("Item inserido com sucesso!");
            System.out.println();
        } catch (SQLException e) {
            System.err.println(e.getMessage());
        }

    }

    private static void registroLocacao() {
        Scanner sc = new Scanner(System.in);
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");
        System.out.println("****** Registrar Locação ******** ");
        System.out.print("Digite o ID do cliente: ");
        int clienteId = sc.nextInt();
        System.out.print("Digite o ID do item a ser alugado: ");
        int itemId = sc.nextInt();
        System.out.print("Digite a data de locação do seu item (dd/MM/yyyy):");
        LocalDate dataLocacao = LocalDate.parse(sc.next(), formatter);
        sc.nextLine();

        Locacoes locacao = new Locacoes(clienteId, itemId, dataLocacao);
        LocacaoDAO locacaoDAO = new LocacaoDAO();

        try {
            locacaoDAO.realizaLocacao(locacao);
            System.out.println("Locação realizada com sucesso!");
            System.out.println();
        } catch (SQLException e) {
            System.err.println(e.getMessage());
            System.out.println();
        }

    }
    private static void devolverLocacao(){
        Scanner sc = new Scanner(System.in);
        System.out.println("****** Devolver um Item:  ******** ");
        LocacaoDAO locacaoDAO = new LocacaoDAO();
        System.out.print("Digite o ID do Item que deseja devolver: ");
        int id = sc.nextInt();

        try {
            locacaoDAO.devolverLocacao(id);
            System.out.println("Item devolvido com sucesso!");
            System.out.println();

        } catch (SQLException e) {
            System.out.println("Erro ao devolver item: " + e.getMessage());
            System.out.println();
        }

    }
    private static void listarClientes() {
        System.out.print("****** Listar Clientes:  ******** \n");
        ClientesDAO clientesDAO1 = new ClientesDAO();

        try {
            List<Clientes> clientes = clientesDAO1.buscarCLientes();
            if (clientes != null && !clientes.isEmpty()){
                System.out.println("Listagem de Clientes:");

                for( Clientes clientes1 : clientes ){
                    System.out.println(clientes1);
                    System.out.println();
                }
            }else{
                System.out.println("Clientes não encontrados!");
                System.out.println();
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
    private static void listarItens() {
        System.out.println("****** Listar Itens:  ******** \n");
        ItensDAO itemDAO = new ItensDAO();

        try {
            List<Itens> itens = itemDAO.buscarItens();
            if (itens != null && !itens.isEmpty()){
                System.out.println("Listagem de Itens:");

                for( Itens item : itens ){
                    System.out.println(item);
                    System.out.println();
                }
            } else{
                System.out.println("Itens não encontrados!");
                System.out.println();
            }

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
    private static void listarLocacao(){
        System.out.println("****** Listar Locações:  ******** \n");
        LocacaoDAO locacaoDAO = new LocacaoDAO();

        try {
            List<Locacoes> locacao = locacaoDAO.buscarLocacoes();
            if (locacao != null && !locacao.isEmpty()){
                System.out.println("Listagem de Locações:");

                for( Locacoes locacoes : locacao ){
                    System.out.println(locacoes);
                    System.out.println();
                }
            }else{
                System.out.println("Locações não encontrados!");
                System.out.println();
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
    private static void excluirClientes(){
        Scanner sc = new Scanner(System.in);
        System.out.println("****** Excluir Clientes:  ******** ");
        ClientesDAO clientesDAO = new ClientesDAO();
        System.out.print("Digite o ID do Cliente que deseja excluir: ");
        int id = sc.nextInt();

        try {
            clientesDAO.excluirClientes(id);
            System.out.println("Cliente excluído com sucesso!");
            System.out.println();

        } catch (SQLException e) {
            System.out.println("Erro ao excluir cliente: " + e.getMessage());
            System.out.println();
        }

    }
    private static void excluirItem(){
        Scanner sc = new Scanner(System.in);
        System.out.println("****** Excluir Itens:  ******** ");
        ItensDAO itemDAO = new ItensDAO();
        System.out.print("Digite o ID do Item que deseja excluir: ");
        int id = sc.nextInt();

        try {
            itemDAO.excluirItens(id);
            System.out.println("Item excluído com sucesso!");
            System.out.println();

        } catch (SQLException e) {
            System.out.println("Erro ao excluir item: " + e.getMessage());
            System.out.println();
        }


    }

}