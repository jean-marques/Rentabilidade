/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package RMD;

// import App.Principal;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
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
        String pasta_diretorio = "C:\\Users\\suporte\\Desktop\\LOG_RENTABILIDADE";

        File diretorio = new File(pasta_diretorio);
        if(!diretorio.exists()) {
            diretorio.mkdirs();
        }

        DateTimeFormatter formatador = DateTimeFormatter.ofPattern("dd-MM-yyyy_HH-mm-ss");
        String carimbo = LocalDateTime.now().format(formatador);
        String arquivo = pasta_diretorio + "/Correcao_Rentabilidade_Diaria_" + carimbo + ".txt";

        try(BufferedWriter writer = new BufferedWriter(new FileWriter(arquivo, true))) {

            List<RMD> r = RMD_Consulta.getRMD(01, 2025);
            for (RMD rmd : r) {
                rmd = RMD_Consulta.getRentDiaria(rmd);
                rmd = RMD_Consulta.getRentAcumulada(rmd);
                RMD_Consulta.setRentabilidade(rmd);

                String output = "RMD: "+rmd.getCod_rmd()+" Loja: "+rmd.getProcfit()+" Diario(%): "+rmd.getRen_dia()+" Total: (%)"+rmd.getRen_acum()
                        +" Movimento: "+rmd.getMovimento();

                System.out.println(output);

                writer.write(output);
                writer.newLine();
            }

            System.out.println("Correcao Finalizada!");

        } catch (SQLException ex) {
            Logger.getLogger(Run_rmd.class.getName()).log(Level.SEVERE, null, ex);
        } catch (IOException ex) {
            Logger.getLogger(Run_rmd.class.getName()).log(Level.SEVERE, "Erro ao escrever no arquivo", ex);
        }
    }
}
