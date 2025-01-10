/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package RMD;

import java.text.DecimalFormat;

/**
 *
 * @author jeanm
 */
public class RMD {
    private int cod_rmd;
    private double ren_dia;
    private double ren_acum;
    private int procfit;
    private String movimento;
    private double vendas_atuais;
    private double cmv_atual;
    private double vendas_acumulada;
    private double cmv_acumulada;
    
    
    public RMD(int cod_rmd, int procfit, double ren_dia, double ren_acum, String movimento) {
        this.cod_rmd = cod_rmd;
        this.ren_dia = ren_dia;
        this.ren_acum = ren_acum;
        this.procfit = procfit;
        this.movimento = movimento;
    }

    public double getRen_dia() {
        return ren_dia;
    }

    public double getRen_acum() {
        return ren_acum;
    }

    public int getProcfit() {
        return procfit;
    }

    public String getMovimento() {
        return movimento;
    }

    public int getCod_rmd() {
        return cod_rmd;
    }

    public double getVendas_atuais() {
        
        return vendas_atuais;
    }

    public double getCmv_atual() {
        return cmv_atual;
    }

    public double getVendas_acumulada() {
        return vendas_acumulada;
    }

    public double getCmv_acumulada() {
        return cmv_acumulada;
    }

    
    
    public void setCod_rmd(int cod_rmd) {
        this.cod_rmd = cod_rmd;
    }

    public void setRen_dia(double ren_dia) {
        this.ren_dia = ren_dia;
    }

    public void setRen_acum(double ren_acum) {  
        this.ren_acum = ren_acum;
    }

    public void setProcfit(int procfit) {
        this.procfit = procfit;
    }

    public void setMovimento(String movimento) {
        this.movimento = movimento;
    }

    public void setVendas_atuais(double vendas_atuais) {
        this.vendas_atuais = vendas_atuais;
    }

    public void setCmv_atual(double cmv_atual) {
        this.cmv_atual = cmv_atual;
    }

    public void setVendas_acumulada(double vendas_acumulada) {
        this.vendas_acumulada = vendas_acumulada;
    }

    public void setCmv_acumulada(double cmv_acumulada) {
        this.cmv_acumulada = cmv_acumulada;
    }
    
    
    
}
