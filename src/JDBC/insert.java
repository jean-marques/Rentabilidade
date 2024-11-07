/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package JDBC;
import java.sql.SQLException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.util.Scanner;
/**
 *
 * @author jeanm
 */
public class insert {
    public static void main(String[] args) throws SQLException {
        Scanner entrada = new Scanner (System.in);
        System.out.println("Informe o seu nome:");
        String nome = entrada.nextLine();
        
        String sql = "INSERT INTO pessoas (nome) Values ('?')";
        Connection con = FabConexao.conSGC();
        PreparedStatement pstmt = con.prepareStatement(sql);
        pstmt.setString(1, nome);
        pstmt.execute();
        con.close();
    }
    
}
