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
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.GlyphLayout;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.utils.TimeUtils;
import com.badlogic.gdx.utils.viewport.ExtendViewport;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import jose.a.desarrollador.Principal;
import jose.a.desarrollador.Util.Assets;
import jose.a.desarrollador.Util.Codigos_Escritorio;
import jose.a.desarrollador.Util.Constantes;
import jose.a.desarrollador.Util.Estados_Boxeador.ESTADOS;

/**
 *
 * @author josea
 */
public class PantallaEsperaBuscarOponente extends ScreenAdapter{
    Principal principal;
    String nombre_boxeador;
    String competicion;
    String tipo_boxeador;
    Socket socketJugador;
    private BufferedReader in= null;
    private PrintWriter out= null;
    private SpriteBatch spriteBatch;
    private ExtendViewport extendViewport;
    long walkStartTime;
    TextureRegion saco;
    TextureRegion barra_progreso;
    ESTADOS estado_saco;
    boolean animacion_terminada;
    int animacion_mostrar;
    BitmapFont font;
    float widthTexto;
    public PantallaEsperaBuscarOponente(Principal principal, Socket socketJugador,String nombre_boxeador,String competicion) {
        this.principal = principal;
        this.socketJugador = socketJugador;
        this.nombre_boxeador=nombre_boxeador;
        this.competicion=competicion;
        
        init();
    }
    
    public void init(){
        tipo_boxeador=Constantes.DATOS_USUARIO.getBoxeador(nombre_boxeador).getTipo_boxeador();
        
        AssetManager am = new AssetManager();
        Assets.instance.init(am);
               
        font = Assets.instance.assetsUi.generator.generateFont(Assets.instance.assetsUi.parameter);
        font.setColor(Color.WHITE);
        
        spriteBatch=new SpriteBatch();
      
        extendViewport=new ExtendViewport(Constantes.WORLD_SIZE,Constantes.WORLD_SIZE);

        
        estado_saco=ESTADOS.PIVOTANDO;
        
        animacion_terminada=true;
        animacion_mostrar=0;        
        
        saco=(TextureRegion) Assets.instance.assetsUi.saco;
        
        GlyphLayout layout = new GlyphLayout();     
        layout.setText(font,"Buscando oponente..");
        widthTexto = layout.width;
        
        try {
                    
            in=new BufferedReader(new InputStreamReader(this.socketJugador.getInputStream()));
            out=new PrintWriter(socketJugador.getOutputStream(),true);// Flujo de salida hacia el socket
            out.println("7&"+nombre_boxeador+"&"+this.competicion+"&"+this.tipo_boxeador);
            
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    
    @Override
    public void resize(int width, int height) {
        extendViewport.update(width,height,true);

    }
    
    @Override
    public void render(float delta) {
        update();
        
        float walkTimeSeconds = MathUtils.nanoToSec * (TimeUtils.nanoTime() - walkStartTime);
        
        extendViewport.apply();
        spriteBatch.setProjectionMatrix(extendViewport.getCamera().combined);
        Gdx.gl.glClearColor(128/255f,203/255f,196/255f,0.1f);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        spriteBatch.setProjectionMatrix(extendViewport.getCamera().combined);
        spriteBatch.begin();
        barra_progreso= (TextureRegion) Assets.instance.assetsUi.progress_bar.getKeyFrame(walkTimeSeconds);            ;
        switch(estado_saco){
            case PIVOTANDO:
                
                    saco=(TextureRegion) Assets.instance.assetsUi.saco;
                 
                    
                break;
            
            case DIRECTO_DERECHA:                
                    
                    saco = (TextureRegion) Assets.instance.assetsUi.saco_golpeado.getKeyFrame(walkTimeSeconds);                    
                    int frame=Assets.instance.assetsUi.saco_golpeado.getKeyFrameIndex(walkTimeSeconds);
                    if(frame==4){                                 
                        estado_saco=ESTADOS.PIVOTANDO;
                    }
                
                break;
        }
        
        spriteBatch.draw(
                    saco.getTexture(),
                    (extendViewport.getWorldWidth()/2)-((saco.getRegionWidth()*4)/2),
                    (extendViewport.getWorldHeight()/2)-((saco.getRegionHeight()*4)/3),
                    0,
                    0,
                    saco.getRegionWidth()*4,
                    saco.getRegionHeight()*4,
                    1,
                    1,
                    0,
                    saco.getRegionX(),
                    saco.getRegionY(),
                    saco.getRegionWidth(),
                    saco.getRegionHeight(),
                    false,
                    false);
        
        font.draw(spriteBatch, "Buscando oponente..", (extendViewport.getWorldWidth()/2) - (widthTexto/2), extendViewport.getWorldHeight()/4);
        
        spriteBatch.draw(
                    barra_progreso.getTexture(),
                    (extendViewport.getWorldWidth()/2)-((barra_progreso.getRegionWidth()*4)/2),
                    (extendViewport.getWorldHeight()/8)-((barra_progreso.getRegionHeight()*4)/2),
                    0,
                    0,
                    barra_progreso.getRegionWidth()*4,
                    barra_progreso.getRegionHeight()*4,
                    1,
                    1,
                    0,
                    barra_progreso.getRegionX(),
                    barra_progreso.getRegionY(),
                    barra_progreso.getRegionWidth(),
                    barra_progreso.getRegionHeight(),
                    false,
                    false);
        
        
        spriteBatch.end();
    }
    
   public void update(){
       String mensaje="";
       try {
           if(in.ready()){
                mensaje=in.readLine();                
                tratarMensaje(mensaje);
           }
       } catch (IOException e) {
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
            
            case GOLPE_SACO:
                switch(datos[1]){
                    case "DIRECTO": case "GANCHO_IZQUIERDA": case "GANCHO_DERECHA":
                        estado_saco=ESTADOS.DIRECTO_DERECHA;
                        break;
                        
                    default:                        
                        break;
                }
                
                break;
                
            case OPONENTE_ENCONTRADO:
                System.out.println("Encontre el oponente");                
                principal.setScreen(new PantallaDesarrolloDelCombate(principal,socketJugador,datos[3],datos[4],datos[1],datos[2]));
                break;
                
        }

   }

    @Override
    public void dispose() {
        Assets.instance.dispose();
        spriteBatch.dispose();

    }
}
