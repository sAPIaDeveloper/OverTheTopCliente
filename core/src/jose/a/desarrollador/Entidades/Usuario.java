/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package jose.a.desarrollador.Entidades;

import java.util.TreeMap;

/**
 *
 * @author josea
 */
public class Usuario {
    
    private String email;
    private String cod_emparejamiento;
    private TreeMap<String,Boxeador> lista_boxeadores; // nombre  del boxeador-y objeto boxeador

    public Usuario() {
        lista_boxeadores=new TreeMap();
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getCod_emparejamiento() {
        return cod_emparejamiento;
    }

    public void setCod_emparejamiento(String cod_emparejamiento) {
        this.cod_emparejamiento = cod_emparejamiento;
    }

    public TreeMap<String, Boxeador> getLista_boxeadores() {
        return lista_boxeadores;
    }

   
    public void setBoxeador(String nombre_boxeador,Boxeador boxeador){
        lista_boxeadores.put(nombre_boxeador, boxeador);
    }
    
    public Boxeador getBoxeador(String nombre_boxeador){
        return lista_boxeadores.get(nombre_boxeador);
    }

    public void deleteBoxeador(String nombre_boxeador){
        lista_boxeadores.remove(nombre_boxeador);
    }
    
    public void clearBoxeadores(){
        lista_boxeadores.clear();
    }
    
    public boolean existeBoxeador(String nombre_boxeador){
        return lista_boxeadores.containsKey(nombre_boxeador);
    }
}

