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
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.List;
import com.badlogic.gdx.scenes.scene2d.ui.ScrollPane;
import com.badlogic.gdx.scenes.scene2d.ui.SelectBox;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.utils.Align;
import com.badlogic.gdx.utils.viewport.ExtendViewport;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.util.ArrayList;
import jose.a.desarrollador.Entidades.Boxeador;
import jose.a.desarrollador.Principal;
import jose.a.desarrollador.Util.Assets;
import jose.a.desarrollador.Util.Codigos_Escritorio;
import jose.a.desarrollador.Util.Constantes;
import jose.a.desarrollador.Util.HiloComunicacionAbierta;
import jose.a.desarrollador.Util.Preferencias;

/**
 *
 * @author josea
 */
public class PantallaConsultarMisCampeonatos extends ScreenAdapter{
    Preferencias pref;
    Principal principal;
    String nombre_boxeador;
    private ExtendViewport extendViewport;
    String [] competiciones;
    
    int posicion_boxeador;
    String nombre_boxeador_actual;
    int numero_victorias;
    int numero_derrotas;
    int puntos_totales;
    
    Stage stage;    
    Table tabla;
    Table contenido;
    BitmapFont font;
    private Skin ui;
    
    SelectBox.SelectBoxStyle boxStyle;
    SelectBox<String> spinner_competiciones;
    
    TextButton.TextButtonStyle textButtonStyle;
    TextButton buscar;
    TextButton aceptar;
    
    Label.LabelStyle label;
    Label mi_posicion;
    

    public PantallaConsultarMisCampeonatos(Principal principal, String nombre_boxeador) {
        this.principal = principal;
        this.nombre_boxeador = nombre_boxeador;
        
        init();
    }
    
    public void init(){
        stage=new Stage();  
        pref = new Preferencias();
        extendViewport=new ExtendViewport(Constantes.WORLD_SIZE,Constantes.WORLD_SIZE);
        stage.setViewport(extendViewport);
        Gdx.input.setInputProcessor(stage);  
        
        AssetManager am = new AssetManager();
        Assets.instance.init(am);
        
        font = Assets.instance.assetsUi.generator.generateFont(Assets.instance.assetsUi.parameter);  
        
        TextureAtlas atlas=new TextureAtlas(Constantes.TEXTURE_ATLAS_UI);
        ui=new Skin(atlas);
        
        crearSpinner();
        crearBotones();
        crearLabel();
        crearTabla();
        
        stage.addActor(tabla);
        
    }
    
    public void crearSpinner(){
        boxStyle = new SelectBox.SelectBoxStyle();
        boxStyle.fontColor = Color.BLACK;
        boxStyle.background = ui.getDrawable(Constantes.TEXTFIELD);
        boxStyle.font = font;
        
        boxStyle.scrollStyle = new ScrollPane.ScrollPaneStyle();
        boxStyle.scrollStyle.background = ui.getDrawable(Constantes.TEXTFIELD);
        boxStyle.listStyle = new List.ListStyle();
        boxStyle.listStyle.font = font;
        boxStyle.listStyle.fontColorSelected = Color.WHITE;
        boxStyle.listStyle.fontColorUnselected = Color.BLACK;
        boxStyle.listStyle.selection = ui.getDrawable(Constantes.TABLA);
        boxStyle.listStyle.background = ui.getDrawable(Constantes.TEXTFIELD);

        //SelectBox
          
        String [] competiciones=obtenerCompeticionesApuntadas();
        
        spinner_competiciones = new SelectBox<String>(boxStyle);
        spinner_competiciones.setAlignment(Align.center);
        spinner_competiciones.getList().setAlignment(Align.center);
        spinner_competiciones.setMaxListCount(5);
        spinner_competiciones.setItems(competiciones);
        spinner_competiciones.getScrollPane().sizeBy(300, 100);
    }
    
    public void crearBotones(){
        textButtonStyle= new TextButton.TextButtonStyle();
        textButtonStyle = new TextButton.TextButtonStyle();
        textButtonStyle.up= ui.getDrawable(Constantes.BOTON);
        textButtonStyle.down= ui.getDrawable(Constantes.BOTON_PULSADO);
        textButtonStyle.fontColor=Color.WHITE;
        textButtonStyle.font = font;        
        textButtonStyle.pressedOffsetX=1;
        textButtonStyle.pressedOffsetY=-1;
        
        buscar =new TextButton("MOSTRAR",textButtonStyle);
        buscar.addListener(new ClickListener(){
            @Override
            public void clicked(InputEvent event, float x, float y) {
                obtenerInformacionCompeticion();
            }
        });
        
        aceptar =new TextButton("ACEPTAR",textButtonStyle);
        aceptar.addListener(new ClickListener(){
            @Override
            public void clicked(InputEvent event, float x, float y) {
                principal.setScreen(new PantallaAccionesBoxeador(principal,nombre_boxeador));
            }
        });
        
    }
    
    public void crearLabel(){
        label=new Label.LabelStyle();
        label.font=font;
        label.fontColor=Color.WHITE;
        
        mi_posicion= new Label("Mi posicion: ",label);
        mi_posicion.setAlignment(Align.center);
    }
    
    public void crearTabla(){
        tabla= new Table();
        contenido = new Table();
        
        tabla.setFillParent(true);        
        
        tabla.add(spinner_competiciones).width(200).height(40);   
        tabla.add().width(100).height(40);   
        tabla.add(buscar).width(120).height(50).colspan(5);
        tabla.row().spaceTop(20);
        
        tabla.add(contenido).colspan(3);
        tabla.row().spaceTop(20);
        tabla.add(mi_posicion);
        tabla.add().width(100).height(40);   
        tabla.add(aceptar).width(120).height(50);
        
    }
    
    public String[] obtenerCompeticionesApuntadas(){
        String [] respuestas=null;        
        
        String mensaje="3&"+nombre_boxeador;                
        try {
            DatagramSocket socketD = new DatagramSocket();// Creo un socket tipo datagrama
            byte[] mesg=mensaje.getBytes();// Paso el mensaje a un array de bytes
            InetAddress address = InetAddress.getByName(pref.getDireccion_ip());// Creo un objeto InetAddress con la ip
            DatagramPacket packetToComunication = new DatagramPacket(mesg, mesg.length, address, Constantes.PUERTO); // Creo el paquete con la información
            socketD.send(packetToComunication);// Envio el paquete.
            byte[] bufIn = new byte[256]; 
            DatagramPacket paqueteEntrada = new DatagramPacket(bufIn, bufIn.length); 
            socketD.receive(paqueteEntrada);// Espero para recibir respuesta
            String recibido = new String(paqueteEntrada.getData(), 0, paqueteEntrada.getLength());
            respuestas=recibido.split("&");
            
            
        } catch (Exception e) {
            e.printStackTrace();
        }
        
        return respuestas;
    }
    
    public String[] obtenerInformacionCompeticion(){
        String [] informacion = null;
        
        String mensaje="20&"+nombre_boxeador+"&"+spinner_competiciones.getSelected();  
        try {
            DatagramSocket socketD = new DatagramSocket();// Creo un socket tipo datagrama
            byte[] mesg=mensaje.getBytes();// Paso el mensaje a un array de bytes
            InetAddress address = InetAddress.getByName(pref.getDireccion_ip());// Creo un objeto InetAddress con la ip
            DatagramPacket packetToComunication = new DatagramPacket(mesg, mesg.length, address, Constantes.PUERTO); // Creo el paquete con la información
            socketD.send(packetToComunication);// Envio el paquete.
            byte[] bufIn = new byte[256]; 
            DatagramPacket paqueteEntrada = new DatagramPacket(bufIn, bufIn.length); 
            socketD.receive(paqueteEntrada);// Espero para recibir respuesta
            String recibido = new String(paqueteEntrada.getData(), 0, paqueteEntrada.getLength());
            
            tratarMensaje(recibido);
            
        } catch (Exception e) {
            e.printStackTrace();
        }
        
        return informacion;
    }
    
    @Override
    public void render(float delta) {
        Gdx.gl.glClearColor(128/255f,203/255f,196/255f,0.1f);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
        stage.act(delta);
        stage.draw();
    }
    
    @Override
    public void resize(int width, int height) {
        stage.getViewport().update(width, height,true);
        
    }
    
    @Override
    public void dispose() {
        Assets.instance.dispose();


    }
    
    public void tratarMensaje(String mensaje){
               
        String codigos[]=mensaje.split("&");
        int cod=Integer.parseInt(codigos[0]);
        switch(Codigos_Escritorio.codigo_escritorio(cod)){
            case ERROR:
                
                break;
                
            case DATOS_DE_COMPETICION_SELECCIONADA:
                String datos_competicion[];
                contenido.clearChildren();
                contenido.add(new Label("NOMBRE",label)).pad(5);
                contenido.add(new Label("VICTORIAS",label)).pad(5);
                contenido.add(new Label("PUNTOS TOTALES",label)).pad(5);
                
                for (int i = 1; i < codigos.length; i++) {
                    contenido.row().space(10);
                    datos_competicion = codigos[i].split("@");
                    if(datos_competicion[0].equals(nombre_boxeador)){
                        mi_posicion.setText("Mi posicion: "+i);
                    }
                    if(i < 7){
                        contenido.add(new Label(datos_competicion[0],label)).pad(5);
                        contenido.add(new Label(datos_competicion[1],label)).pad(5);
                        contenido.add(new Label(datos_competicion[2],label)).pad(5);
                    }
                }
                break;
                
            
        }
    }
}
