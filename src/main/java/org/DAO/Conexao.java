package org.DAO;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class Conexao {

        private static final String URL = "jdbc:mysql://localhost:3306/locadora?useSSL=false&serverTimezone=UTC&allowPublicKeyRetrieval=true";
        private static final String USER = "root";
        private static final String PASS = "leandro99";

        public static Connection obterConexao() throws SQLException {
            return DriverManager.getConnection(URL,USER,PASS);
        }
    }

