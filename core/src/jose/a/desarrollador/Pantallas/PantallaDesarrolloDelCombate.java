/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package jose.a.desarrollador.Pantallas;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.ScreenAdapter;
import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.utils.TimeUtils;
import com.badlogic.gdx.utils.viewport.ExtendViewport;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.concurrent.TimeUnit;
import jose.a.desarrollador.Entidades.PlayerCliente;
import jose.a.desarrollador.Entidades.Publico;
import jose.a.desarrollador.Entidades.Round;
import jose.a.desarrollador.Entidades.Tatami;
import jose.a.desarrollador.Overlays.BoxeadoresHUD;
import jose.a.desarrollador.Principal;
import jose.a.desarrollador.Util.Assets;
import jose.a.desarrollador.Util.Codigos_Escritorio;
import jose.a.desarrollador.Util.Constantes;

/**
 *
 * @author josea
 */
public class PantallaDesarrolloDelCombate extends ScreenAdapter{
    private Principal principal;
    private Socket socketJugador;
    private String nombre_boxeador;
    private String nombre_adversario;
    private String tipo_boxeador_propio;
    private String tipo_boxeador_adversario;
    private String accion_boxeador;
    private String accion_contrincante;
    private String ganador;
    
    private int asalto;
    BitmapFont font;
    private BufferedReader in= null;
    private PrintWriter out= null;
    
    private SpriteBatch spriteBatch;
    private ExtendViewport extendViewport;
    private long walkStartTime;
    int tiempoMax;
    long tiempoTranscurrido;
    long empiezo;
    private Publico publico;   
    private Tatami tatami;    
    private BoxeadoresHUD hud;
    private Round round;
    
    private PlayerCliente boxeador;
    private PlayerCliente contrincante;

    TextureRegion finish; 
    boolean combateTerminado;
    float scale;
    float velocidad_rotacion;
    Vector2 posicion_finish;
    long empiezo_finish;
    int rotacion;
    
    public PantallaDesarrolloDelCombate(Principal principal, Socket socketJugador, String nombre_boxeador, String nombre_adversario, String tipo_boxeador_propio, String tipo_boxeador_adversario) {
        this.principal = principal;
        this.socketJugador = socketJugador;
        this.nombre_boxeador = nombre_boxeador;
        this.nombre_adversario = nombre_adversario;
        this.tipo_boxeador_propio = tipo_boxeador_propio;
        this.tipo_boxeador_adversario = tipo_boxeador_adversario;
        accion_boxeador="";
        accion_contrincante="";        
        init();
        tiempoMax=60;
    }
    
    
    public void init(){
        tiempoTranscurrido = 0;
        AssetManager am= new AssetManager();
        Assets.instance.init(am);
        combateTerminado = false;
        extendViewport = new ExtendViewport(Constantes.WORLD_SIZE,Constantes.WORLD_SIZE);
        spriteBatch =new SpriteBatch();
        publico = new Publico();        
        tatami = new Tatami();
        hud = new BoxeadoresHUD(nombre_boxeador,nombre_adversario);
        round = new Round();
        font = Assets.instance.assetsUi.generator.generateFont(Assets.instance.assetsUi.parameter);
        font.setColor(Color.ORANGE);
        boxeador= new PlayerCliente("BOXEADOR",tipo_boxeador_propio,nombre_boxeador,extendViewport);
        contrincante= new PlayerCliente("CONTRINCANTE",tipo_boxeador_adversario,nombre_adversario,extendViewport);
        
        try {
            in=new BufferedReader(new InputStreamReader(this.socketJugador.getInputStream()));
            out=new PrintWriter(socketJugador.getOutputStream(),true);// Flujo de salida hacia el socket
            out.println("12"); // Listo para el combate
        } catch (Exception e) {
            e.printStackTrace();
        }
        
        posicion_finish = new Vector2();
        finish= (TextureRegion) Assets.instance.screen.finish;
        System.out.println(posicion_finish+""+finish);
        posicion_finish.x = (extendViewport.getWorldWidth() / 2 + finish.getRegionWidth() / 2);
        posicion_finish.y = (extendViewport.getWorldHeight() + finish.getRegionHeight()*2);
        
        asalto = 1;
        scale = 0;
        velocidad_rotacion = 10;
        rotacion = 10;
        
        
        empiezo = TimeUtils.millis();
    }

    @Override
    public void resize(int width, int height) {
         extendViewport.update(width,height,true);
    }

    @Override
    public void render(float delta) {
        long momentoExacto= TimeUtils.millis();
        tiempoTranscurrido = (int)tiempoMax - (TimeUnit.MILLISECONDS.toSeconds(momentoExacto) - TimeUnit.MILLISECONDS.toSeconds(empiezo));
        update();
        extendViewport.apply();
        Gdx.gl.glClearColor(0,0,0,1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
        spriteBatch.setProjectionMatrix(extendViewport.getCamera().combined);
        spriteBatch.begin();
        //Orden de pintado publico, tatami,contrincante,mi personaje
        
        publico.render(spriteBatch, extendViewport);
        tatami.render(spriteBatch, extendViewport);
        String informacionHud = boxeador.getVida()+"&"+boxeador.getStamina()+"&"+contrincante.getVida()+"&"+contrincante.getStamina();
        hud.render(spriteBatch, informacionHud,extendViewport);
        
       
        contrincante.render(spriteBatch,accion_contrincante);
        boxeador.render(spriteBatch,accion_boxeador);
        
        if(combateTerminado){
            
            spriteBatch.draw(finish.getTexture(),
                posicion_finish.x, posicion_finish.y, 
                finish.getRegionWidth() / 2,
                finish.getRegionHeight() / 2,
                finish.getRegionWidth(),
                finish.getRegionHeight(),
                scale,
                scale,
                rotacion += (int)(3 * velocidad_rotacion),
                finish.getRegionX(),
                finish.getRegionY(),
                finish.getRegionWidth(),
                finish.getRegionHeight(),
                false,false);
        }else if(!round.isActivo() || asalto > 3){
             font.draw(spriteBatch, tiempoTranscurrido+"", extendViewport.getWorldWidth()/2, extendViewport.getWorldHeight()-5);
        }
        
        if(round.isActivo()){
            round.render(spriteBatch, extendViewport, asalto);
        }
        
        accion_contrincante="";
        accion_boxeador="";
        spriteBatch.end();
        
    }
    
    
    public void update(){                
        if(tiempoTranscurrido <= 0){                
            asalto++; 
            out.println("18&"+asalto);       
            empiezo = TimeUtils.millis();
            if(asalto <= 3){
                round.restablecer();
            }
            

        }
        String mensaje="";
        if(combateTerminado){
            long momentoExacto= TimeUtils.millis();
            long tiempoTranscurrido = (int)(TimeUnit.MILLISECONDS.toSeconds(momentoExacto) - TimeUnit.MILLISECONDS.toSeconds(empiezo_finish));            
            if(tiempoTranscurrido >= 2){                
                if(scale >= 2){
                    principal.setScreen(new PantallaAccionesBoxeador(principal,nombre_boxeador));
                }else{
                    scale += 0.6;
                    velocidad_rotacion -= 3;
                    if(velocidad_rotacion <= 0){
                        velocidad_rotacion = 0;
                    }
                }
               empiezo_finish =  TimeUtils.millis();
            }
            
            if(scale >= 1.9){
                rotacion =0;
            }
        }else if(!round.isActivo()){
            try {
                if(in.ready()){
                     mensaje=in.readLine();
                     System.out.println("Mensaje recibido: --> "+mensaje);
                     tratarMensaje(mensaje);
                }
            } catch (IOException e) {
            }
        }
        
    }
    
    public void tratarMensaje(String mensaje){
         String respuesta="";
        String datos[]=mensaje.split("&");
        int cod=Integer.parseInt(datos[0]);
        switch(Codigos_Escritorio.codigo_escritorio(cod)){
            case ERROR:
                //int cod_error=Integer.parseInt(datos[1]);
               
                break;
            
            case ACTUALIZAR_ACCIONES:
                accion_boxeador = accionAPintar(datos[1],datos[2],boxeador);
                accion_contrincante = accionAPintar(datos[2],datos[1],contrincante);
                
                String mensaje_para_el_servidor = "14&"+boxeador.devolverInformacionParaElServidor()+"&"+accion_boxeador;
                out.println(mensaje_para_el_servidor);
                break;
                           
            case COMBATE_TERMINADO:// hacer que reciba el ganador para enviarlo
                System.out.println("Termino");
                empiezo_finish =  TimeUtils.millis();
                combateTerminado = true;
                break;
             
                
        }
    }
    
    public String accionAPintar(String accionBoxeador,String accionContrincante,PlayerCliente player){
        String respuesta="";
        /*
        PIVOTANDO,
        BLOQUEO,
        DIRECTO,
        GANCHO_IZQUIERDA,
        GANCHO_DERECHA,
        ESQUIVAR_IZQUIERDA,
        ESQUIVAR_DERECHA
        
        */
        int random=(int) (Math.random()*10+1);
        switch(accionBoxeador){
            case "PIVOTANDO":
                switch(accionContrincante){
                     
                    case "DIRECTO":
                          respuesta="GOLPEADO"; 
                          player.setVida(10);
                          
                    break;

                    case "GANCHO_IZQUIERDA":
                        respuesta="GOLPEADO"; 
                        player.setVida(20);
                    break;

                    case "GANCHO_DERECHA":
                        respuesta="GOLPEADO"; 
                        player.setVida(20);
                    break;
                    
                    default:
                        respuesta=accionBoxeador;
                    break;
                    }
            break;
            
            case "BLOQUEO":
                switch(accionContrincante){                    
                    case "DIRECTO":
                        //quitar stamina con respecto al directo
                        respuesta=accionBoxeador;
                    break;

                    case "GANCHO_IZQUIERDA":
                        //quitar stamina con respecto al directo
                        respuesta=accionBoxeador;
                    break;

                    case "GANCHO_DERECHA":
                        //quitar stamina con respecto al directo
                        respuesta=accionBoxeador;
                    break;

                    default:
                       respuesta=accionBoxeador; 
                    break;

                }
            break;
            
            case "DIRECTO":
                switch(accionContrincante){

                    case "DIRECTO":
                        if(random>5){
                        respuesta="DIRECTO_DERECHA";
                    }else{
                        respuesta="DIRECTO_IZQUIERDA";
                    }
                    break;
                    
                    case "BLOQUEO":
                        if(random>5){
                        respuesta="DIRECTO_DERECHA";
                    }else{
                        respuesta="DIRECTO_IZQUIERDA";
                    }
                        player.sumarGolpeLanzado();
                    break;
                    
                    default:
                        
                    if(random>5){
                        respuesta="DIRECTO_DERECHA";
                    }else{
                        respuesta="DIRECTO_IZQUIERDA";
                    }
                         player.sumarGolpeLanzado();
                         player.sumarGolpeConectado();
                    break;
                }
            break;
            
            case "GANCHO_IZQUIERDA":
                switch(accionContrincante){
                                       

                    case "DIRECTO":
                        respuesta="GOLPEADO";
                        player.setVida(10);
                        player.sumarGolpeLanzado();
                         
                        
                    break;

                    default:
                        respuesta=accionBoxeador;
                        player.sumarGolpeLanzado();
                        player.sumarGolpeConectado();
                    break;

                }
            break;
            
            case "GANCHO_DERECHA":
                switch(accionContrincante){
                    
                    case "DIRECTO":
                        respuesta="GOLPEADO";
                        player.setVida(10);
                        player.sumarGolpeLanzado();
                         
                    break;

                    default:
                        respuesta=accionBoxeador;
                        player.sumarGolpeLanzado();
                        player.sumarGolpeConectado();
                    break;
                }
            break;
            
            case "ESQUIVAR_IZQUIERDA":
                switch(accionContrincante){
                  
               

                    case "GANCHO_DERECHA":
                        respuesta="GOLPEADO";
                        player.setVida(15);
                    break;

                    default:
                        respuesta=accionBoxeador;
                    break;
                }
            break;
            
            case "ESQUIVAR_DERECHA":
                switch(accionContrincante){                    

                    case "GANCHO_IZQUIERDA":
                        respuesta="GOLPEADO";
                        player.setVida(15);
                    break;

                    default:
                        respuesta=accionBoxeador;
                    break;
                    
                }
            break;
            
        }
        
        return respuesta;
    }

    @Override
    public void dispose() {
        Assets.instance.dispose();
        spriteBatch.dispose();

    }
    
    
}
