/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package jose.a.desarrollador.Util;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Preferences;

/**
 *
 * @author josea
 */
public class Preferencias {
    private Preferences preferencias;
    private int volumen_sfx;
    private int volumen_musica;
    private String direccion_ip;

    public Preferencias() {
        preferencias = (Preferences) Gdx.app.getPreferences("datosPrueba");
        
        volumen_sfx = preferencias.getInteger("sfx",100);
        volumen_musica = preferencias.getInteger("musica",100);
        
        direccion_ip = preferencias.getString("direccion_ip", "");
    
    }

    public int getVolumen_sfx() {        
        volumen_sfx = preferencias.getInteger("sfx",100);        
        return volumen_sfx;
    }

    public void setVolumen_sfx(boolean activado) {
        if(activado){
            preferencias.putInteger("sfx", 100);
        }else{
            preferencias.putInteger("sfx", 0);
        }
        
        preferencias.flush();
       
    }

    public int getVolumen_musica() {       
        volumen_musica = preferencias.getInteger("musica",100);       
        return volumen_musica;
    }

    public void setVolumen_musica(boolean activado) {
        if(activado){
            preferencias.putInteger("musica", 100);
        }else{
            preferencias.putInteger("musica", 0);
        }
        
        preferencias.flush();
    }

    public String getDireccion_ip() {
        return direccion_ip;
    }

    public void setDireccion_ip(String direccion_ip) {
        preferencias.putString("direccion_ip", direccion_ip);
        preferencias.flush();
    }
    
    
    
}
