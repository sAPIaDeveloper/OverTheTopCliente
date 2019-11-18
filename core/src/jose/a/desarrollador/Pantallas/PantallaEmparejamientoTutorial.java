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
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable;
import com.badlogic.gdx.utils.Align;
import com.badlogic.gdx.utils.TimeUtils;
import com.badlogic.gdx.utils.viewport.ExtendViewport;
import com.google.zxing.BarcodeFormat;
import com.google.zxing.Writer;
import com.google.zxing.common.BitMatrix;
import com.google.zxing.qrcode.QRCodeWriter;
import java.awt.image.BufferedImage;
import java.io.BufferedReader;
import java.io.File;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import javax.imageio.ImageIO;
import jose.a.desarrollador.Principal;
import jose.a.desarrollador.Util.Assets;
import jose.a.desarrollador.Util.Codigos_Escritorio;
import jose.a.desarrollador.Util.Constantes;
import jose.a.desarrollador.Util.Preferencias;

/**
 *
 * @author josea
 */
public class PantallaEmparejamientoTutorial extends ScreenAdapter{
    Preferencias pref;
    Principal principal;
    String codigo_emparejamiento;
    private ExtendViewport extendViewport;
    Stage stage; 
    BitmapFont font;
    Table tabla;  
    Image codigoQR;
    Label.LabelStyle label;
    Label mensaje;
    Socket socketJugador;
    private PrintWriter out= null;
    private BufferedReader in= null;
    
    public PantallaEmparejamientoTutorial(Principal principal, String codigo_emparejamiento) {
        this.principal = principal;
        this.codigo_emparejamiento = codigo_emparejamiento;
    }
    
    
    public void init(){
        AssetManager am = new AssetManager();
        Assets.instance.init(am);
        pref = new Preferencias();
        font = Assets.instance.assetsUi.generator.generateFont(Assets.instance.assetsUi.parameter);
        extendViewport = new ExtendViewport(Constantes.WORLD_SIZE,Constantes.WORLD_SIZE);
        stage = new Stage();
        stage.setViewport(extendViewport);       
        Gdx.input.setInputProcessor(stage);
        
        crearLabel();
        hacerImagenQR();
        crearTabla();
        stage.addActor(tabla);
    }
    
    public void crearLabel(){
        label=new Label.LabelStyle();
        label.font=font;       
        label.fontColor=Color.BLACK;
        mensaje= new Label("Escanea el codigo QR con la aplicacion movil para emparejar",label);
    }
    
    public void hacerImagenQR(){
        try {
            BitMatrix matrix;
            Writer escritor = new QRCodeWriter();
            matrix = escritor.encode(codigo_emparejamiento, BarcodeFormat.QR_CODE, 300, 300);

            BufferedImage imagen = new BufferedImage(300, 300, BufferedImage.TYPE_INT_RGB);

            for(int y = 0; y < 300; y++) {
                for(int x = 0; x < 300; x++) {
                    int grayValue = (matrix.get(x, y) ? 0 : 1) & 0xff;
                    imagen.setRGB(x, y, (grayValue == 0 ? 0 : 0xFFFFFF));
                }
            }
            
            File outputfile = new File("codigoQR.png");
            ImageIO.write(imagen, "png", outputfile);
            
            Texture t=new Texture(Gdx.files.internal("codigoQR.png"));
            codigoQR = new Image();
            codigoQR.setDrawable(new TextureRegionDrawable(new TextureRegion(t)));
            
            
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    
    public void crearTabla(){
        tabla= new Table();        
        tabla.setFillParent(true);        
        tabla.add(codigoQR).align(Align.center);
        tabla.row().spaceTop(100);
        tabla.add(mensaje).height(100).align(Align.center);
        
    }

    @Override
    public void resize(int width, int height) {
        stage.getViewport().update(width, height,true);
    }

    @Override
    public void render(float delta) {
       update();
        Gdx.gl.glClearColor(128/255f,203/255f,196/255f,0.1f);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
        stage.act(delta);
        stage.draw();
    }
    
    public void update(){
        try {           
           
           String respuesta="";
           String mensaje="";
           boolean emparejado=false;    
           if(in != null){
               if(in.ready()){// Compruebo si hay algo en el flujo de entrada para leer del servidor
                mensaje=in.readLine();  // Leo del servidor
                respuesta=tratarMensaje(mensaje);
           }
                if(!respuesta.isEmpty()){
                    out.println(respuesta);
                    respuesta="";
                }
           }
           
           
           
           
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    
    public String tratarMensaje(String mensaje){
        String respuesta="";
        String datos[]=mensaje.split("&");
        int cod=Integer.parseInt(datos[0]);
        switch(Codigos_Escritorio.codigo_escritorio(cod)){
            case ERROR:
                int cod_error=Integer.parseInt(datos[1]);
                
                break;
            
            case MOVIL_EMPAREJADO:
                System.out.println("Movil fue emparejado");                                                 
                break;
                
           
           
        }
        return respuesta;
    }
}
