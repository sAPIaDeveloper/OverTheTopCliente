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
import com.badlogic.gdx.graphics.Pixmap;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.TextureData;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable;
import com.badlogic.gdx.utils.Align;
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
import javax.swing.ImageIcon;
import jose.a.desarrollador.Principal;
import jose.a.desarrollador.Util.Assets;
import jose.a.desarrollador.Util.Codigos_Escritorio;
import jose.a.desarrollador.Util.Constantes;

/**
 *
 * @author josea
 */
public class PantallaEmparejamiento extends ScreenAdapter{
    
    Principal principal;
    String competicion;
    
    Stage stage; 
    BitmapFont font;
    Table tabla;  
    Image codigoQR;
    Label.LabelStyle label;
    Label mensajes_error;
    Socket socketJugador;
    private PrintWriter out= null;
    private BufferedReader in= null;
    String cod_emparejamiento;
    String nombre_boxeador;
    Image image;
    public PantallaEmparejamiento(Principal principal,String nombre_boxeador,String competicion) {        
        this.principal=principal;
        this.nombre_boxeador=nombre_boxeador;
        this.competicion=competicion;               
       
        init();
    }

    public void init(){
        AssetManager am = new AssetManager();
        Assets.instance.init(am);
        font = Assets.instance.assetsUi.generator.generateFont(Assets.instance.assetsUi.parameter);
        stage=new Stage(); 
        Gdx.input.setInputProcessor(stage);  
        
        crearLabel();
        
         try {
            socketJugador=new Socket(Constantes.IP,Constantes.PUERTO);
            out=new PrintWriter(socketJugador.getOutputStream(),true);// Flujo de salida hacia el socket
            in=new BufferedReader(new InputStreamReader(socketJugador.getInputStream()));
            out.println("4&"+nombre_boxeador);
        } catch (Exception e) {
            e.printStackTrace();
        }
         
        crearTabla();
        
    }
    
    public void update(){
        try {           
           
           String respuesta="";
           String mensaje="";
           boolean emparejado=false;           
           if(in.ready()){// Compruebo si hay algo en el flujo de entrada para leer del servidor
                mensaje=in.readLine();  // Leo del servidor
                respuesta=tratarMensaje(mensaje);
           }
           if(!respuesta.isEmpty()){
               out.println(respuesta);
               respuesta="";
           }
           
           
           
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
  
    public void crearLabel(){
        label=new Label.LabelStyle();
        label.font=font;       
        label.fontColor=Color.BLACK;
        mensajes_error= new Label("",label);
    }
    
    public void crearTabla(){     
        tabla= new Table();
        //tabla.setDebug(true);
        tabla.setFillParent(true);        
        tabla.add();
        tabla.row().spaceTop(100);
        tabla.add(mensajes_error).height(100).align(Align.center);
        stage.addActor(tabla);
    }
    
    @Override
    public void render(float delta) {
        update();
        Gdx.gl.glClearColor(128/255f,203/255f,196/255f,0.1f);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
        stage.act(delta);
        stage.draw();
    }
      
    public void hacerImagenQR(String datos){
        try {
            BitMatrix matrix;
            Writer escritor = new QRCodeWriter();
            matrix = escritor.encode(datos, BarcodeFormat.QR_CODE, 300, 300);

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
            image = new Image();
            image.setDrawable(new TextureRegionDrawable(new TextureRegion(t)));
            
            
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
                respuesta=mostrarError(cod_error);
                break;
            
            case MOVIL_EMPAREJADO:
                System.out.println("Movil fue emparejado");  
                //Mostrar mensaje de buscando oponente
                 mensajes_error.setText("Buscando un oponente...");
                 principal.setScreen(new PantallaEsperaBuscarOponente(principal,socketJugador,nombre_boxeador,competicion));
                 //tabla.removeActor(image);
                 
                break;
                
            case DAR_CODIGO_EMPAREJAMIENTO:
                cod_emparejamiento=datos[1];
                hacerImagenQR(cod_emparejamiento);
                tabla.row().spaceTop(10);
                tabla.add(image).align(Align.center).size(100,100);
                
                
                respuesta="6";
                break;
                
           
        }
        return respuesta;
    }
    
    public String mostrarError(int cod){
        String error="";
        if(cod==6){
            //mostrar mensaje de que debe emparejar el movil
            mensajes_error.setText("Debes emparejar el móvil para poder jugar.\n Para emparejar escanea el codigo QR con la aplicación movil que se le facilita");
            
            error="6";
        }
        
        return error;
    }

    @Override
    public void dispose() {
        Assets.instance.dispose();
    }
}
