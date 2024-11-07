/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package RMD;

// import App.Principal;
import java.sql.SQLException;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author jeanm
 */
public class Run_rmd {
    public static void main(String[] args) {
        try {
            List<RMD> r = RMD_Consulta.getRMD(11, 2024);
                for (RMD rmd : r) {
                    rmd = RMD_Consulta.getRentDiaria(rmd);
                    rmd = RMD_Consulta.getRentAcumulada(rmd);
                    RMD_Consulta.setRentabilidade(rmd);
                    System.out.println("RMD: "+rmd.getCod_rmd()+" Procfit: "+rmd.getProcfit()+" Diario(%): "+rmd.getRen_dia()+" Total: (%)"+rmd.getRen_acum()
                            +" Movimento: "+rmd.getMovimento());                    
                }
        } catch (SQLException ex) {

        }        
    }
}
