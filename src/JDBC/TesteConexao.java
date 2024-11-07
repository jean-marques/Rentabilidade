package JDBC;
import java.sql.Statement;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class TesteConexao {
    
    public static void main(String[] args) throws SQLException {
        
        final String url = "jdbc:sqlserver://procfit.pharmagestao.com.br;encrypt=false;database=PHGSGC_PRODUCAO;";
        final String user = "SA";
        final String senha = "ERPM";
        
        Connection conexao = DriverManager.getConnection(url, user, senha);
        System.out.println("Conexao efetuada com sucesso");
        
//        Statement stmt = conexao.createStatement();
//        stmt.execute();
        conexao.close();
    }
}
