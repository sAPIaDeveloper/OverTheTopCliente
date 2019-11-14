/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package jose.a.desarrollador.Entidades;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Vector2;

import com.badlogic.gdx.utils.TimeUtils;
import com.badlogic.gdx.utils.viewport.ExtendViewport;
import java.util.Stack;
import jose.a.desarrollador.Util.Assets;
import jose.a.desarrollador.Util.Estados_Boxeador.ESTADOS;

/**
 *
 * @author josea
 */
public class PlayerCliente {
    
    String rol_boxeador;// para saber si pintar al jugador o al contrincante 
    Stack lista_acciones;
    //Elementos necesarios para pintar el boxeador
    String tipo_boxeador; // para saber que boxeador pintar
    String nombre_boxeador;
    int vida;
    double stamina;
    int golpes_lanzados;
    int golpes_conectados;
    String estado;
    public Vector2 position_boxeador;
    public ESTADOS estados_boxeador;
    public ESTADOS estados_boxeador_anterior;
    long walkStartTime;    
    TextureRegion boxeador;    
    Sound golpeo;
    ExtendViewport extendViewport;
    float elapsed_time = 0f;
    boolean animacion_terminada;
    
    public PlayerCliente(String rol_boxeador, String tipo_boxeador, String nombre_boxeador,ExtendViewport extendViewport) {
        this.rol_boxeador = rol_boxeador;
        this.tipo_boxeador = tipo_boxeador;
        this.nombre_boxeador = nombre_boxeador;
        this.extendViewport = extendViewport;
        init();
    }
    
    public void init(){
        AssetManager am = new AssetManager();
        Assets.instance.init(am);
        
        position_boxeador= new Vector2(0,0);
        estados_boxeador= ESTADOS.PIVOTANDO;
        estados_boxeador_anterior= ESTADOS.PIVOTANDO;
        
        vida = 200;
        stamina = 150;
        golpes_lanzados = 0;
        golpes_conectados = 0;
        lista_acciones = new Stack();
        estado = "PIVOTANDO";
        animacion_terminada = false;
        
    }
    
    public void update(String estado){
       /*if(lista_acciones.size() > 0 && animacion_terminada){
           estado = (String) lista_acciones.pop();
           animacion_terminada = false;
       }*/
       stamina += 0.1;
       if(stamina > 150){
           stamina = 150;
       }
       estados_boxeador_anterior = estados_boxeador;
        switch(estado){
            case "PIVOTANDO":
                estados_boxeador=ESTADOS.PIVOTANDO;
                break;
            case "BLOQUEO":
                estados_boxeador=ESTADOS.BLOQUEO;
                break;
            case "DIRECTO_DERECHA":
                estados_boxeador=ESTADOS.DIRECTO_DERECHA; 
                golpeo = Assets.instance.assetsSonido.golpeo_dos;
                reproducirSonido();
                break;
                
            case "DIRECTO_IZQUIERDA":
                estados_boxeador=ESTADOS.DIRECTO_IZQUIERDA;   
                golpeo = Assets.instance.assetsSonido.golpeo_dos;
                reproducirSonido();
                break;    
            
            case "GANCHO_IZQUIERDA":
                estados_boxeador=ESTADOS.GANCHO_IZQUIERDA;
                golpeo = Assets.instance.assetsSonido.golpeo_uno;
                reproducirSonido();
                break;
            case "GANCHO_DERECHA":
                estados_boxeador=ESTADOS.GANCHO_DERECHA;
                golpeo = Assets.instance.assetsSonido.golpeo_uno;
                reproducirSonido();
                break;
            case "ESQUIVAR_IZQUIERDA":
                estados_boxeador=ESTADOS.ESQUIVAR_IZQUIERDA;
                break;
            case "ESQUIVAR_DERECHA":
                estados_boxeador=ESTADOS.ESQUIVAR_DERECHA;
                break;
            case "GOLPEADO":
                estados_boxeador=ESTADOS.GOLPEADO;
                break;
            case "DERROTADO":
                estados_boxeador=ESTADOS.DERROTADO;
                break;
            default:
                //estados_boxeador=ESTADOS.PIVOTANDO;
                break;
        }
        
        if(estados_boxeador_anterior != ESTADOS.PIVOTANDO && ESTADOS.PIVOTANDO != estados_boxeador && rol_boxeador.equals("BOXEADOR")){System.out.println(estados_boxeador+" ---> "+estados_boxeador_anterior);}
        
        if(estados_boxeador_anterior != estados_boxeador){
            
            elapsed_time = 0f;
        }
    }
    
    public void render(SpriteBatch spriteBatch,String estado){
        //lista_acciones.push(estado);
        update(estado);
        obtenerFrame();
      
        spriteBatch.draw(
                    boxeador.getTexture(),
                    position_boxeador.x,
                    position_boxeador.y,
                    0,
                    0,
                    boxeador.getRegionWidth()*1.5f,
                    boxeador.getRegionHeight()*1.5f,
                    1,
                    1,
                    0,
                    boxeador.getRegionX(),
                    boxeador.getRegionY(),
                    boxeador.getRegionWidth(),
                    boxeador.getRegionHeight(),
                    false,
                    false);
        
    }
    
    public void obtenerFrame(){
        float walkTimeSeconds = MathUtils.nanoToSec * (TimeUtils.nanoTime() - walkStartTime);
        elapsed_time += Gdx.graphics.getDeltaTime();
        int frame;
        switch(estados_boxeador){
            case PIVOTANDO:
                switch(tipo_boxeador){
                    case "Boxeador_john":
                        if(rol_boxeador.equals("CONTRINCANTE")){
                        
                        }else{
                        
                        }
                        break;
                    case "Boxeador_negro":
                        if(rol_boxeador.equals("CONTRINCANTE")){
                        
                        }else{
                        
                        }
                        break;
                    case "Boxeador_Buzz":
                        if(rol_boxeador.equals("CONTRINCANTE")){
                        
                        }else{
                        
                        }
                        break;
                    case "Boxeadora_Kate":
                        if(rol_boxeador.equals("CONTRINCANTE")){
                            // Restablezco su posicion
                            boxeador= (TextureRegion) Assets.instance.assetsKate.pivotando_frente.getKeyFrame(elapsed_time);
                            position_boxeador.x= (extendViewport.getWorldWidth()/2)-((boxeador.getRegionWidth()*1.5f)/2);  
                            position_boxeador.y= (extendViewport.getWorldHeight()/2)-((boxeador.getRegionHeight()*1.7f)/3);
                        }else{
                            boxeador= (TextureRegion) Assets.instance.assetsKate.pivotando_espalda.getKeyFrame(elapsed_time);
                            position_boxeador.x= (extendViewport.getWorldWidth()/2)-((boxeador.getRegionWidth()*2)/2);  
                            position_boxeador.y= (extendViewport.getWorldHeight()/2)-((boxeador.getRegionHeight()*2)/2); 
                        }
                        break;
                    case "Boxeadora_Jessi":
                        if(rol_boxeador.equals("CONTRINCANTE")){
                            // Restablezco su posicion
                            boxeador= (TextureRegion) Assets.instance.assetsJessi.pivotando_frente.getKeyFrame(elapsed_time);
                            position_boxeador.x= (extendViewport.getWorldWidth()/2)-((boxeador.getRegionWidth()*1.5f)/2);  
                            position_boxeador.y= (extendViewport.getWorldHeight()/2)-((boxeador.getRegionHeight()*1.7f)/3);
                        }else{
                            boxeador= (TextureRegion) Assets.instance.assetsJessi.pivotando_espalda.getKeyFrame(elapsed_time);
                            position_boxeador.x= (extendViewport.getWorldWidth()/2)-((boxeador.getRegionWidth()*2)/2);  
                            position_boxeador.y= (extendViewport.getWorldHeight()/2)-((boxeador.getRegionHeight()*2)/2); 
                        }
                        
                        
                        break;
                    case "Boxeadora_cecilia":
                        if(rol_boxeador.equals("CONTRINCANTE")){
                        
                        }else{
                        
                        }
                        break;
                }
                
                break;
                
            case BLOQUEO:
                switch(tipo_boxeador){
                    case "Boxeador_john":
                        if(rol_boxeador.equals("CONTRINCANTE")){
                        
                        }else{
                        
                        }
                        break;
                    case "Boxeador_negro":
                        if(rol_boxeador.equals("CONTRINCANTE")){
                        
                        }else{
                        
                        }
                        break;
                    case "Boxeador_Buzz":
                        if(rol_boxeador.equals("CONTRINCANTE")){
                        
                        }else{
                        
                        }
                        break;
                    case "Boxeadora_Kate":
                        if(rol_boxeador.equals("CONTRINCANTE")){
                            boxeador= (TextureRegion) Assets.instance.assetsKate.bloqueo_frente.getKeyFrame(elapsed_time);                            
                            comprobarAnimacionTerminada(Assets.instance.assetsKate.bloqueo_frente,elapsed_time);
                            
                        }else{
                             boxeador=(TextureRegion) Assets.instance.assetsKate.bloqueo_espalda.getKeyFrame(elapsed_time);
                             comprobarAnimacionTerminada(Assets.instance.assetsKate.bloqueo_espalda,elapsed_time);
                        }
                        break;
                    case "Boxeadora_Jessi":
                        if(rol_boxeador.equals("CONTRINCANTE")){
                            boxeador= (TextureRegion) Assets.instance.assetsJessi.bloqueo_frente.getKeyFrame(elapsed_time);                            
                            comprobarAnimacionTerminada(Assets.instance.assetsJessi.bloqueo_frente,elapsed_time);
                            
                        }else{
                             boxeador= (TextureRegion) Assets.instance.assetsJessi.bloqueo_espalda.getKeyFrame(elapsed_time);
                             comprobarAnimacionTerminada(Assets.instance.assetsJessi.bloqueo_espalda,elapsed_time);
                        }
                        break;
                    case "Boxeadora_cecilia":
                        if(rol_boxeador.equals("CONTRINCANTE")){
                        
                        }else{
                        
                        }
                        break;
                }
                break;
                
            case DIRECTO_IZQUIERDA:
                switch(tipo_boxeador){
                    case "Boxeador_john":
                        if(rol_boxeador.equals("CONTRINCANTE")){
                        
                        }else{
                        
                        }
                        break;
                    case "Boxeador_negro":
                        if(rol_boxeador.equals("CONTRINCANTE")){
                        
                        }else{
                        
                        }
                        break;
                    case "Boxeador_Buzz":
                        if(rol_boxeador.equals("CONTRINCANTE")){
                        
                        }else{
                        
                        }
                        break;
                    case "Boxeadora_Kate":
                        if(rol_boxeador.equals("CONTRINCANTE")){
                             boxeador= (TextureRegion) Assets.instance.assetsKate.directo_izquierda_frente.getKeyFrame(elapsed_time);
                             
                             comprobarAnimacionTerminada(Assets.instance.assetsKate.directo_izquierda_frente,elapsed_time);
                             // Tengo que bajar un poco la animacion para que este a la misma altura el golpeo 
                            position_boxeador.x= (extendViewport.getWorldWidth()/2)-((boxeador.getRegionWidth()*1.5f)/2);  
                            position_boxeador.y= (extendViewport.getWorldHeight()/2)-((boxeador.getRegionHeight()*2.5f)/3);
                            
                             
                        }else{
                             boxeador= (TextureRegion) Assets.instance.assetsKate.directo_izquierda_espalda.getKeyFrame(elapsed_time);
                             
                             comprobarAnimacionTerminada(Assets.instance.assetsKate.directo_izquierda_espalda,elapsed_time);
                        }
                        break;
                    case "Boxeadora_Jessi":
                        if(rol_boxeador.equals("CONTRINCANTE")){
                             boxeador=(TextureRegion) Assets.instance.assetsJessi.directo_izquierda_frente.getKeyFrame(elapsed_time);
                             
                             comprobarAnimacionTerminada(Assets.instance.assetsJessi.directo_izquierda_frente,elapsed_time);
                             // Tengo que bajar un poco la animacion para que este a la misma altura el golpeo 
                            position_boxeador.x= (extendViewport.getWorldWidth()/2)-((boxeador.getRegionWidth()*1.5f)/2);  
                            position_boxeador.y= (extendViewport.getWorldHeight()/2)-((boxeador.getRegionHeight()*2.5f)/3);
                            
                             
                        }else{
                             boxeador= (TextureRegion) Assets.instance.assetsJessi.directo_izquierda_espalda.getKeyFrame(elapsed_time);
                             
                             comprobarAnimacionTerminada(Assets.instance.assetsJessi.directo_izquierda_espalda,elapsed_time);
                        }
                        break;
                    case "Boxeadora_cecilia":
                        if(rol_boxeador.equals("CONTRINCANTE")){
                        
                        }else{
                        
                        }
                        break;
                }
                break;
                
            case DIRECTO_DERECHA:
                switch(tipo_boxeador){
                    case "Boxeador_john":
                        if(rol_boxeador.equals("CONTRINCANTE")){
                        
                        }else{
                        
                        }
                        break;
                    case "Boxeador_negro":
                        if(rol_boxeador.equals("CONTRINCANTE")){
                        
                        }else{
                        
                        }
                        break;
                    case "Boxeador_Buzz":
                        if(rol_boxeador.equals("CONTRINCANTE")){
                        
                        }else{
                        
                        }
                        break;
                    case "Boxeadora_Kate":
                        if(rol_boxeador.equals("CONTRINCANTE")){
                             boxeador= (TextureRegion) Assets.instance.assetsKate.directo_derecha_frente.getKeyFrame(elapsed_time);
                             comprobarAnimacionTerminada(Assets.instance.assetsKate.directo_derecha_frente,elapsed_time);
                            // Tengo que bajar un poco la animacion para que este a la misma altura el golpeo 
                            position_boxeador.x= (extendViewport.getWorldWidth()/2)-((boxeador.getRegionWidth()*1.5f)/2);  
                            position_boxeador.y= (extendViewport.getWorldHeight()/2)-((boxeador.getRegionHeight()*2.5f)/3);
                            
                        }else{
                             boxeador= (TextureRegion) Assets.instance.assetsKate.directo_derecha_espalda.getKeyFrame(elapsed_time);
                             comprobarAnimacionTerminada(Assets.instance.assetsKate.directo_derecha_espalda,elapsed_time);
                        }
                        break;
                    case "Boxeadora_Jessi":
                        if(rol_boxeador.equals("CONTRINCANTE")){
                             boxeador= (TextureRegion) Assets.instance.assetsJessi.directo_derecha_frente.getKeyFrame(elapsed_time);
                             comprobarAnimacionTerminada(Assets.instance.assetsJessi.directo_derecha_frente,elapsed_time);
                            // Tengo que bajar un poco la animacion para que este a la misma altura el golpeo 
                            position_boxeador.x= (extendViewport.getWorldWidth()/2)-((boxeador.getRegionWidth()*1.5f)/2);  
                            position_boxeador.y= (extendViewport.getWorldHeight()/2)-((boxeador.getRegionHeight()*2.5f)/3);
                            
                        }else{
                             boxeador= (TextureRegion) Assets.instance.assetsJessi.directo_derecha_espalda.getKeyFrame(elapsed_time);
                             comprobarAnimacionTerminada(Assets.instance.assetsJessi.directo_derecha_espalda,elapsed_time);
                        }
                        break;
                    case "Boxeadora_cecilia":
                        if(rol_boxeador.equals("CONTRINCANTE")){
                        
                        }else{
                        
                        }
                        break;
                }
                break;
                
            case GANCHO_IZQUIERDA:
                switch(tipo_boxeador){
                    case "Boxeador_john":
                        if(rol_boxeador.equals("CONTRINCANTE")){
                        
                        }else{
                        
                        }
                        break;
                    case "Boxeador_negro":
                        if(rol_boxeador.equals("CONTRINCANTE")){
                        
                        }else{
                        
                        }
                        break;
                    case "Boxeador_Buzz":
                        if(rol_boxeador.equals("CONTRINCANTE")){
                        
                        }else{
                        
                        }
                        break;
                    case "Boxeadora_Kate":
                        if(rol_boxeador.equals("CONTRINCANTE")){
                             boxeador= (TextureRegion) Assets.instance.assetsKate.gancho_izquierda_frente.getKeyFrame(walkTimeSeconds);
                             comprobarAnimacionTerminada(Assets.instance.assetsKate.gancho_izquierda_frente,elapsed_time);
                              // Tengo que bajar un poco la animacion para que este a la misma altura el golpeo 
                            position_boxeador.x= (extendViewport.getWorldWidth()/2)-((boxeador.getRegionWidth()*1.5f)/2);  
                            position_boxeador.y= (extendViewport.getWorldHeight()/2)-((boxeador.getRegionHeight()*2.5f)/3);
                        }else{
                             boxeador= (TextureRegion) Assets.instance.assetsKate.gancho_izquierda_espalda.getKeyFrame(walkTimeSeconds);
                             comprobarAnimacionTerminada(Assets.instance.assetsKate.gancho_izquierda_espalda,elapsed_time);
                        }
                        break;
                    case "Boxeadora_Jessi":
                        if(rol_boxeador.equals("CONTRINCANTE")){
                             boxeador= (TextureRegion) Assets.instance.assetsJessi.gancho_izquierda_frente.getKeyFrame(walkTimeSeconds);
                             comprobarAnimacionTerminada(Assets.instance.assetsJessi.gancho_izquierda_frente,elapsed_time);
                              // Tengo que bajar un poco la animacion para que este a la misma altura el golpeo 
                            position_boxeador.x= (extendViewport.getWorldWidth()/2)-((boxeador.getRegionWidth()*1.5f)/2);  
                            position_boxeador.y= (extendViewport.getWorldHeight()/2)-((boxeador.getRegionHeight()*2.5f)/3);
                        }else{
                             boxeador= (TextureRegion) Assets.instance.assetsJessi.gancho_izquierda_espalda.getKeyFrame(walkTimeSeconds);
                             comprobarAnimacionTerminada(Assets.instance.assetsJessi.gancho_izquierda_espalda,elapsed_time);
                        }
                        break;
                    case "Boxeadora_cecilia":
                        if(rol_boxeador.equals("CONTRINCANTE")){
                        
                        }else{
                        
                        }
                        break;
                }
                break;
                
            case GANCHO_DERECHA:
                switch(tipo_boxeador){
                    case "Boxeador_john":
                        if(rol_boxeador.equals("CONTRINCANTE")){
                        
                        }else{
                        
                        }
                        break;
                    case "Boxeador_negro":
                        if(rol_boxeador.equals("CONTRINCANTE")){
                        
                        }else{
                        
                        }
                        break;
                    case "Boxeador_Buzz":
                        if(rol_boxeador.equals("CONTRINCANTE")){
                        
                        }else{
                        
                        }
                        break;
                    case "Boxeadora_Kate":
                        if(rol_boxeador.equals("CONTRINCANTE")){
                             boxeador= (TextureRegion) Assets.instance.assetsKate.gancho_derecha_frente.getKeyFrame(walkTimeSeconds);
                             comprobarAnimacionTerminada(Assets.instance.assetsKate.gancho_derecha_frente,elapsed_time);
                              // Tengo que bajar un poco la animacion para que este a la misma altura el golpeo 
                            position_boxeador.x= (extendViewport.getWorldWidth()/2)-((boxeador.getRegionWidth()*1.5f)/2);  
                            position_boxeador.y= (extendViewport.getWorldHeight()/2)-((boxeador.getRegionHeight()*2.5f)/3);
                        }else{
                             boxeador= (TextureRegion) Assets.instance.assetsKate.gancho_derecha_espalda.getKeyFrame(walkTimeSeconds);
                             comprobarAnimacionTerminada(Assets.instance.assetsKate.gancho_derecha_espalda,elapsed_time);
                        }
                        break;
                    case "Boxeadora_Jessi":
                        if(rol_boxeador.equals("CONTRINCANTE")){
                             boxeador= (TextureRegion) Assets.instance.assetsJessi.gancho_derecha_frente.getKeyFrame(walkTimeSeconds);
                             comprobarAnimacionTerminada(Assets.instance.assetsJessi.gancho_derecha_frente,elapsed_time);
                              // Tengo que bajar un poco la animacion para que este a la misma altura el golpeo 
                            position_boxeador.x= (extendViewport.getWorldWidth()/2)-((boxeador.getRegionWidth()*1.5f)/2);  
                            position_boxeador.y= (extendViewport.getWorldHeight()/2)-((boxeador.getRegionHeight()*2.5f)/3);
                        }else{
                             boxeador= (TextureRegion) Assets.instance.assetsJessi.gancho_derecha_espalda.getKeyFrame(walkTimeSeconds);
                             comprobarAnimacionTerminada(Assets.instance.assetsJessi.gancho_derecha_espalda,elapsed_time);
                        }
                        break;
                    case "Boxeadora_cecilia":
                        if(rol_boxeador.equals("CONTRINCANTE")){
                        
                        }else{
                        
                        }
                        break;
                }
                break;
                
            case ESQUIVAR_IZQUIERDA:
                switch(tipo_boxeador){
                    case "Boxeador_john":
                        if(rol_boxeador.equals("CONTRINCANTE")){
                        
                        }else{
                        
                        }
                        break;
                    case "Boxeador_negro":
                        if(rol_boxeador.equals("CONTRINCANTE")){
                        
                        }else{
                        
                        }
                        break;
                    case "Boxeador_Buzz":
                        if(rol_boxeador.equals("CONTRINCANTE")){
                        
                        }else{
                        
                        }
                        break;
                    case "Boxeadora_Kate":
                        if(rol_boxeador.equals("CONTRINCANTE")){
                             boxeador= (TextureRegion) Assets.instance.assetsKate.esquivar_izquierda_frente.getKeyFrame(walkTimeSeconds);
                             comprobarAnimacionTerminada(Assets.instance.assetsKate.esquivar_izquierda_frente,elapsed_time);
                        }else{
                             boxeador= (TextureRegion) Assets.instance.assetsKate.esquivar_izquierda_espalda.getKeyFrame(walkTimeSeconds);
                             comprobarAnimacionTerminada(Assets.instance.assetsKate.esquivar_izquierda_espalda,elapsed_time);
                        }
                        break;
                    case "Boxeadora_Jessi":
                        // Poner que esquiva
                        if(rol_boxeador.equals("CONTRINCANTE")){
                             boxeador= (TextureRegion) Assets.instance.assetsJessi.esquivar_izquierda_frente.getKeyFrame(walkTimeSeconds);
                             comprobarAnimacionTerminada(Assets.instance.assetsJessi.esquivar_izquierda_frente,elapsed_time);
                        }else{
                             boxeador= (TextureRegion) Assets.instance.assetsJessi.esquivar_izquierda_espalda.getKeyFrame(walkTimeSeconds);
                             comprobarAnimacionTerminada(Assets.instance.assetsJessi.esquivar_izquierda_espalda,elapsed_time);
                        }                        
                        break;
                    case "Boxeadora_cecilia":
                        if(rol_boxeador.equals("CONTRINCANTE")){
                        
                        }else{
                        
                        }
                        break;
                }
                break;
                
            case ESQUIVAR_DERECHA:
                switch(tipo_boxeador){
                    case "Boxeador_john":
                        if(rol_boxeador.equals("CONTRINCANTE")){
                        
                        }else{
                        
                        }
                        break;
                    case "Boxeador_negro":
                        if(rol_boxeador.equals("CONTRINCANTE")){
                        
                        }else{
                        
                        }
                        break;
                    case "Boxeador_Buzz":
                        if(rol_boxeador.equals("CONTRINCANTE")){
                        
                        }else{
                        
                        }
                        break;
                    case "Boxeadora_Kate":
                        if(rol_boxeador.equals("CONTRINCANTE")){
                             boxeador= (TextureRegion) Assets.instance.assetsKate.esquivar_derecha_frente.getKeyFrame(walkTimeSeconds);
                             comprobarAnimacionTerminada(Assets.instance.assetsKate.esquivar_derecha_frente,elapsed_time);
                        }else{
                             boxeador= (TextureRegion) Assets.instance.assetsKate.esquivar_derecha_espalda.getKeyFrame(walkTimeSeconds);
                             comprobarAnimacionTerminada(Assets.instance.assetsKate.esquivar_derecha_espalda,elapsed_time);
                        }
                        break;
                    case "Boxeadora_Jessi":
                        if(rol_boxeador.equals("CONTRINCANTE")){
                             boxeador= (TextureRegion) Assets.instance.assetsJessi.esquivar_derecha_frente.getKeyFrame(walkTimeSeconds);
                             comprobarAnimacionTerminada(Assets.instance.assetsJessi.esquivar_derecha_frente,elapsed_time);
                        }else{
                             boxeador= (TextureRegion) Assets.instance.assetsJessi.esquivar_derecha_espalda.getKeyFrame(walkTimeSeconds);
                             comprobarAnimacionTerminada(Assets.instance.assetsJessi.esquivar_derecha_espalda,elapsed_time);
                        }
                        break;
                    case "Boxeadora_cecilia":
                        if(rol_boxeador.equals("CONTRINCANTE")){
                        
                        }else{
                        
                        }
                        break;
                }
                break;
                
            case GOLPEADO:
                switch(tipo_boxeador){
                    case "Boxeador_john":
                        if(rol_boxeador.equals("CONTRINCANTE")){
                        
                        }else{
                        
                        }
                        break;
                    case "Boxeador_negro":
                        if(rol_boxeador.equals("CONTRINCANTE")){
                        
                        }else{
                        
                        }
                        break;
                    case "Boxeador_Buzz":
                        if(rol_boxeador.equals("CONTRINCANTE")){
                        
                        }else{
                        
                        }
                        break;
                    case "Boxeadora_Kate":
                        if(rol_boxeador.equals("CONTRINCANTE")){
                            boxeador= (TextureRegion) Assets.instance.assetsKate.golpeado_frente.getKeyFrame(elapsed_time);                            
                            comprobarAnimacionTerminada(Assets.instance.assetsKate.golpeado_frente,elapsed_time);
                            
                        }else{
                             boxeador= (TextureRegion) Assets.instance.assetsKate.golpeado_espalda.getKeyFrame(elapsed_time);
                             comprobarAnimacionTerminada(Assets.instance.assetsKate.golpeado_espalda,elapsed_time);
                        }
                        break;
                    case "Boxeadora_Jessi":
                        if(rol_boxeador.equals("CONTRINCANTE")){
                            boxeador= (TextureRegion) Assets.instance.assetsJessi.golpeado_frente.getKeyFrame(elapsed_time);                            
                            comprobarAnimacionTerminada(Assets.instance.assetsJessi.golpeado_frente,elapsed_time);
                            
                        }else{
                             boxeador= (TextureRegion) Assets.instance.assetsJessi.golpeado_espalda.getKeyFrame(elapsed_time);
                             comprobarAnimacionTerminada(Assets.instance.assetsJessi.golpeado_espalda,elapsed_time);
                        }
                        break;
                    case "Boxeadora_cecilia":
                        if(rol_boxeador.equals("CONTRINCANTE")){
                        
                        }else{
                        
                        }
                        break;
                }
                break;
                
            case DERROTADO:
                switch(tipo_boxeador){
                    case "Boxeador_john":
                        if(rol_boxeador.equals("CONTRINCANTE")){
                        
                        }else{
                        
                        }
                        break;
                    case "Boxeador_negro":
                        if(rol_boxeador.equals("CONTRINCANTE")){
                        
                        }else{
                        
                        }
                        break;
                    case "Boxeador_Buzz":
                        if(rol_boxeador.equals("CONTRINCANTE")){
                        
                        }else{
                        
                        }
                        break;
                    case "Boxeadora_Kate":
                        if(rol_boxeador.equals("CONTRINCANTE")){
                            boxeador= (TextureRegion) Assets.instance.assetsKate.derrotado_frente.getKeyFrame(elapsed_time);                                                                                    
                        }else{
                             boxeador= (TextureRegion) Assets.instance.assetsKate.derrotado_espalda.getKeyFrame(elapsed_time);                            
                        }
                        break;
                    case "Boxeadora_Jessi":
                       if(rol_boxeador.equals("CONTRINCANTE")){
                            boxeador= (TextureRegion) Assets.instance.assetsJessi.derrotado_frente.getKeyFrame(elapsed_time);                                                                                    
                        }else{
                             boxeador= (TextureRegion) Assets.instance.assetsJessi.derrotado_espalda.getKeyFrame(elapsed_time);                            
                        }
                        break;
                    case "Boxeadora_cecilia":
                        if(rol_boxeador.equals("CONTRINCANTE")){
                        
                        }else{
                        
                        }
                        break;
                }
                break;
        }
        
        
    }
    
    public void comprobarAnimacionTerminada(Animation animacion,float walkTimeSeconds){
        int frame= animacion.getKeyFrameIndex(walkTimeSeconds);
        int framesAnimacion = animacion.getKeyFrames().length-1;
                
        if(frame == framesAnimacion){
            estados_boxeador = ESTADOS.PIVOTANDO;
            animacion_terminada = true;
            elapsed_time = 0f;
        }
       
    }

    public int getVida() {
        return vida;
    }

    public void setVida(int vida) {
        
        this.vida -= vida;
        if(this.vida<=0){
            this.vida=0;
        }
    }

    public double getStamina() {
        return stamina;
    }

    public void setStamina(int stamina) {
        this.stamina -= stamina;
        if(this.stamina<=0){
            this.stamina=0;
        }
    }
    
    public void sumarGolpeLanzado(){
        
       golpes_lanzados++;}
    
    public void sumarGolpeConectado(){
        
       golpes_conectados++;}

    public int getGolpes_lanzados() {
        return golpes_lanzados;
    }

    public int getGolpes_conectados() {
        return golpes_conectados;
    }

    public String getTipo_boxeador() {
        return tipo_boxeador;
    }

    public String getNombre_boxeador() {
        return nombre_boxeador;
    }
            
    public String devolverInformacionParaElServidor(){
        return vida+"&"+golpes_lanzados+"&"+golpes_conectados;
    }
    
    
    public void reproducirSonido(){
        golpeo.play(100);
    }
}
