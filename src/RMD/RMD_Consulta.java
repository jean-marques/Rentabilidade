/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package RMD;

import JDBC.FabConexao;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author jeanm
 */
public class RMD_Consulta {
    public static List getRMD (int mes, int ano) throws SQLException {
        Connection con = FabConexao.conSGC();
        String sql = """
                    SELECT R.COD_RMD, E.COD_PROCFIT AS PROCFIT , R.PER_RENTABILIDADE as REN_DIA, R.PER_RENTABILIDADE_ACUMULADA AS REN_ACUMULADA, 
                    FORMAT(R.DAT_MOVIMENTO, 'dd/MM/yyyy')  AS MOVIMENTO 
                     FROM RMD R 
                    INNER JOIN EMPRESA E 
                    ON  E.COD_EMPRESA = R.COD_EMPRESA 
                    WHERE 
                    ((R.PER_RENTABILIDADE <= 0 OR R.PER_RENTABILIDADE_ACUMULADA <= 0) OR
                    (R.PER_RENTABILIDADE > 60 OR R.PER_RENTABILIDADE_ACUMULADA = 100)) AND
                    (MONTH (R.DAT_MOVIMENTO) = ? AND YEAR (R.DAT_MOVIMENTO) = ? AND 
                    R.INTEGRACAO_PROCFIT = 1 AND 
                    E.COD_PROCFIT != 0
                    AND IDT_SISTEMA = 1 
                    AND IDT_FINANCEIRO is not null) 
                    ORDER BY R.DAT_MOVIMENTO, E.Cod_Procfit DESC  
                     """;
        
        PreparedStatement pstmt = con.prepareStatement(sql);
        pstmt.setInt(1, mes);
        pstmt.setInt(2, ano);
        ResultSet resultado = pstmt.executeQuery();
        
        List<RMD> listRMD = new ArrayList<>();
        
        while(resultado.next()){
            int cod_rmd = resultado.getInt("COD_RMD");
            int procfit = resultado.getInt("PROCFIT");
            double ren_dia = resultado.getInt("REN_DIA");
            double ren_acum = resultado.getInt("REN_ACUMULADA");
            String movimento = resultado.getString("MOVIMENTO");
            listRMD.add(new RMD(cod_rmd, procfit, ren_dia, ren_acum, movimento));
        }
        pstmt.close();
        con.close();
        return listRMD;
        
        
    }
    
    public static RMD getRentDiaria (RMD rmd) throws SQLException{
        Connection con = FabConexao.conRET();
        String sql_con = """
                        SELECT SUM(VENDA_LIQUIDA) as VENDAS_ATUAL,
                        SUM(CMV) as CMV
                        FROM PBS_FARMACRO_DADOS.DBO.VENDAS_AGRUPADAS 
                        WHERE EMPRESA = ?
                        AND MOVIMENTO = ?
                         """;
        PreparedStatement pstmt = con.prepareStatement(sql_con);
        pstmt.setInt(1, rmd.getProcfit());
        pstmt.setString(2, rmd.getMovimento());
        ResultSet resultado = pstmt.executeQuery();
        while(resultado.next()){
            if (resultado.getDouble("VENDAS_ATUAL") > 0){
                rmd.setVendas_atuais(resultado.getDouble("VENDAS_ATUAL"));
                rmd.setCmv_atual(resultado.getDouble("CMV"));
                rmd.setRen_dia(100.00 - (rmd.getCmv_atual()/ rmd.getVendas_atuais()) * 100.00);
            }else{
                rmd.setRen_dia(0.0);
            }
            
        }
        pstmt.close();
        return rmd;
    }
    
    public static RMD getRentAcumulada (RMD rmd) throws SQLException{
        Connection con = FabConexao.conRET();
        String sql_con = """
                        SELECT SUM(VENDA_LIQUIDA) as VENDAS_ATUAL,
                        SUM(CMV) as CMV
                        FROM PBS_FARMACRO_DADOS.DBO.VENDAS_AGRUPADAS 
                        WHERE EMPRESA = ?
                        AND MOVIMENTO >= DATEFROMPARTS(YEAR(?),MONTH(?),1)
                        AND MOVIMENTO <= ?
                         """;
        PreparedStatement pstmt = con.prepareStatement(sql_con);
        pstmt.setInt(1, rmd.getProcfit());
        pstmt.setString(2, rmd.getMovimento());
        pstmt.setString(3, rmd.getMovimento());
        pstmt.setString(4, rmd.getMovimento());
        ResultSet resultado = pstmt.executeQuery();
        while(resultado.next()){
            if (resultado.getDouble("VENDAS_ATUAL") > 0){
                rmd.setVendas_acumulada(resultado.getDouble("VENDAS_ATUAL"));
                rmd.setCmv_acumulada(resultado.getDouble("CMV"));
                rmd.setRen_acum(100.00 - (rmd.getCmv_acumulada()/ rmd.getVendas_acumulada()) * 100.00);
            }else{
                rmd.setRen_acum(0.0);
            }
        }
        pstmt.close();
        return rmd;
    }
    
    public static void setRentabilidade (RMD rmd) throws SQLException{
        Connection con = FabConexao.conSGC();
        String sql_con = """
                        UPDATE PHGSGC_PRODUCAO.DBO.RMD SET 
                        PER_RENTABILIDADE = ? ,
                        PER_RENTABILIDADE_ACUMULADA = ?
                        WHERE COD_RMD = ?
                         """;
        PreparedStatement pstmt = con.prepareStatement(sql_con);
        pstmt.setDouble(1, rmd.getRen_dia());
        pstmt.setDouble(2, rmd.getRen_acum());
        pstmt.setInt(3, rmd.getCod_rmd());
        pstmt.execute();
        pstmt.close();
    }
}
