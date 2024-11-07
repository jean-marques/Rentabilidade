/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package JDBC;

import java.sql.Connection;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

/**
 *
 * @author jeanm
 */
public class FabConexao {
    
    public static Connection conSGC(){
        try {
            final String url = "jdbc:sqlserver://192.168.175.18;encrypt=false;database=PHGSGC_PRODUCAO;";
            final String user = "SA";
            final String senha = "ERPM";

            return DriverManager.getConnection(url, user, senha);
        } catch (SQLException e){
            throw new RuntimeException(e);
        }
    }
    
    public static Connection conRET(){
        try {
            final String url = "jdbc:sqlserver://procfit.pharmagestao.com.br;encrypt=false;database=PBS_FARMACRO_DADOS;";
            final String user = "procfit.lojas";
            final String senha = "Pr0cf1t@2$1*";
            
            return DriverManager.getConnection(url, user, senha);
        } catch (SQLException e){
            throw new RuntimeException(e);
        }
    }
}
