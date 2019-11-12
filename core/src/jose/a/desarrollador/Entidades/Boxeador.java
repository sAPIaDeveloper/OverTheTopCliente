/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package jose.a.desarrollador.Entidades;

/**
 *
 * @author josea
 */
public class Boxeador {
    private int cod_boxeador;
    private String nombre_boxeador;
    private String tipo_boxeador;
    private char sexo;
    private double peso;
    private String pais;
    private String categoria;

    public Boxeador() {
    }

    public Boxeador(int cod_boxeador, String nombre_boxeador, String tipo_boxeador, char sexo, double peso, String pais, String categoria) {
        this.cod_boxeador = cod_boxeador;
        this.nombre_boxeador = nombre_boxeador;
        this.tipo_boxeador = tipo_boxeador;
        this.sexo = sexo;
        this.peso = peso;
        this.pais = pais;
        this.categoria = categoria;
    }

    
    
    public int getCod_boxeador() {
        return cod_boxeador;
    }

    public void setCod_boxeador(int cod_boxeador) {
        this.cod_boxeador = cod_boxeador;
    }

    public String getNombre_boxeador() {
        return nombre_boxeador;
    }

    public void setNombre_boxeador(String nombre_boxeador) {
        this.nombre_boxeador = nombre_boxeador;
    }

    public String getTipo_boxeador() {
        return tipo_boxeador;
    }

    public void setTipo_boxeador(String tipo_boxeador) {
        this.tipo_boxeador = tipo_boxeador;
    }

    public char getSexo() {
        return sexo;
    }

    public void setSexo(char sexo) {
        this.sexo = sexo;
    }

    public double getPeso() {
        return peso;
    }

    public void setPeso(double peso) {
        this.peso = peso;
    }

    public String getPais() {
        return pais;
    }

    public void setPais(String pais) {
        this.pais = pais;
    }

    public String getCategoria() {
        return categoria;
    }

    public void setCategoria(String categoria) {
        this.categoria = categoria;
    }
    
    
    
}
