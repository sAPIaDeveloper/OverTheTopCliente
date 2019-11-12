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
import static com.badlogic.gdx.graphics.Color.BLACK;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.List;
import com.badlogic.gdx.scenes.scene2d.ui.ScrollPane;
import com.badlogic.gdx.scenes.scene2d.ui.SelectBox;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton.TextButtonStyle;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.utils.Align;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.util.ArrayList;
import jose.a.desarrollador.Principal;
import jose.a.desarrollador.Util.Assets;
import jose.a.desarrollador.Util.Constantes;

/**
 *
 * @author josea
 */
public class PantallaAccionesBoxeador extends ScreenAdapter{
    Principal principal;
    String nombre_boxeador;
    Stage stage;    
    Table tabla;
    TextButton boton_jugar;
    TextButton boton_crear_competicion;
    TextButton boton_apuntarse_competicion;
    TextButton boton_consultar_campeonatos;
    TextButton boton_estadisticas;
    TextButton boton_modificar_datos_boxeador;
    TextButton boton_cambiar_boxeador;
    TextButton.TextButtonStyle textButtonStyle;
    
    SelectBox.SelectBoxStyle boxStyle;
    SelectBox<String> spinner_competiciones;
    
    BitmapFont font;
    private Skin ui;

    public PantallaAccionesBoxeador(final Principal principal, final String nombre_boxeador) {
        this.principal = principal;
        this.nombre_boxeador=nombre_boxeador;
        
        init();
        
    }
    
    public void init(){
        stage=new Stage();        
        Gdx.input.setInputProcessor(stage);  
        
        AssetManager am = new AssetManager();
        Assets.instance.init(am);
        
        font = Assets.instance.assetsUi.generator.generateFont(Assets.instance.assetsUi.parameter);  
        
        TextureAtlas atlas=new TextureAtlas(Constantes.TEXTURE_ATLAS_UI);
        ui=new Skin(atlas);
        
        crearBotones();
        crearSpinner();
        crearTabla();
        
        stage.addActor(tabla);        
    }
    
    public void crearBotones(){
        textButtonStyle= new TextButtonStyle();
        textButtonStyle = new TextButtonStyle();
        textButtonStyle.up= ui.getDrawable(Constantes.BOTON);
        textButtonStyle.down= ui.getDrawable(Constantes.BOTON_PULSADO);
        textButtonStyle.fontColor=Color.WHITE;
        textButtonStyle.font = font;        
        textButtonStyle.pressedOffsetX=1;
        textButtonStyle.pressedOffsetY=-1;
        
        boton_jugar=new TextButton("JUGAR",textButtonStyle);
        
        boton_jugar.addListener(new ClickListener(){
            @Override
            public void clicked(InputEvent event, float x, float y) {
                System.out.println("nombre: "+nombre_boxeador);
               // if(){}//comprobar que esta seleccionada la competicion
                principal.setScreen(new PantallaEmparejamiento(principal,nombre_boxeador,spinner_competiciones.getSelected()));
            }

        });
        
        boton_crear_competicion=new TextButton("CREAR COMPETICION",textButtonStyle);
        boton_crear_competicion.addListener(new ClickListener(){
            @Override
            public void clicked(InputEvent event, float x, float y) {                  
                principal.setScreen(new PantallaCrearCompeticiones(principal,nombre_boxeador));
            }

        });
        
        boton_apuntarse_competicion=new TextButton("APUNTARSE EN COMPETICION",textButtonStyle);
        boton_apuntarse_competicion.addListener(new ClickListener(){
            @Override
            public void clicked(InputEvent event, float x, float y) {                  
                principal.setScreen(new PantallaApuntarseCompeticion(principal,nombre_boxeador));
            }

        });
        
        boton_consultar_campeonatos=new TextButton("CONSULTAR MIS COMPETICIONES",textButtonStyle);
        boton_consultar_campeonatos.addListener(new ClickListener(){
            @Override
            public void clicked(InputEvent event, float x, float y) {                  
                principal.setScreen(new PantallaConsultarMisCampeonatos(principal,nombre_boxeador));
            }

        });
        
        boton_estadisticas=new TextButton("CONSULTAR  ESTADISTICAS",textButtonStyle);
        boton_estadisticas.addListener(new ClickListener(){
            @Override
            public void clicked(InputEvent event, float x, float y) {                  
                principal.setScreen(new PantallaMostrarEstadisticasBoxeador(principal,nombre_boxeador));
            }

        });
        
        boton_modificar_datos_boxeador=new TextButton("MODIFICAR DATOS",textButtonStyle);
        boton_modificar_datos_boxeador.addListener(new ClickListener(){
            @Override
            public void clicked(InputEvent event, float x, float y) {                  
                principal.setScreen(new PantallaModificarDatosBoxeador(principal,nombre_boxeador));
            }

        });
        
        boton_cambiar_boxeador=new TextButton("CAMBIAR DE BOXEADOR",textButtonStyle);
        boton_cambiar_boxeador.addListener(new ClickListener(){
            @Override
            public void clicked(InputEvent event, float x, float y) {                  
                principal.setScreen(new PantallaSeleccionOCreacionPersonaje(principal));
            }

        });
        
        
        
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
    
    public void crearTabla(){
        tabla=new Table();        
        tabla.setFillParent(true);
        tabla.add(boton_jugar).width(300).height(70);
        tabla.add(spinner_competiciones).width(300).height(40);        
        tabla.row().spaceTop(20);        
        tabla.add(boton_crear_competicion).width(300).height(70);
        tabla.add(boton_apuntarse_competicion).width(300).height(70);
        tabla.row().spaceTop(30);
        tabla.add(boton_consultar_campeonatos).colspan(2).fillX().height(70);
        tabla.row().spaceTop(30);
        tabla.add(boton_estadisticas).colspan(2).fillX().height(70);
        tabla.row().spaceTop(20);
        tabla.add(boton_modificar_datos_boxeador).width(300).height(70);        
        tabla.add(boton_cambiar_boxeador).width(300).height(70);
        
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
        stage.getViewport().update(width, height);
    }
    
    public String[] obtenerCompeticionesApuntadas(){
        String [] respuestas=null;
        ArrayList<String>lista_competiciones = new ArrayList();
        
        String mensaje="3&"+nombre_boxeador;                
        try {
            DatagramSocket socketD = new DatagramSocket();// Creo un socket tipo datagrama
            byte[] mesg=mensaje.getBytes();// Paso el mensaje a un array de bytes
            InetAddress address = InetAddress.getByName(Constantes.IP);// Creo un objeto InetAddress con la ip
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

    @Override
    public void dispose() {
        Assets.instance.dispose();


    }
}
