/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package jose.a.desarrollador.Pantallas;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.ScreenAdapter;
import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Cell;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.utils.Align;
import com.badlogic.gdx.utils.Array;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.util.ArrayList;
import jose.a.desarrollador.Principal;
import jose.a.desarrollador.Util.Assets;
import jose.a.desarrollador.Util.Codigos_Escritorio;
import jose.a.desarrollador.Util.Constantes;

/**
 *
 * @author josea
 */
public class PantallaApuntarseCompeticion extends ScreenAdapter{
    Principal principal;
    String competiciones;
    String nombre_boxeador;
    String nombre_competicion;
    Stage stage;    
    Table tabla;
    BitmapFont font;
    private Skin ui;
    int offsetConsulta;
    int maximoFilas;
    Label.LabelStyle label;
    Label.LabelStyle label_estilo_seleccionado;
    Label.LabelStyle label_error;
    
    Label label_seleccionado;
    Label nombre;
    Label error;
    Label creador;
    Label fecha;
    Label participantes;
    Label dato;
    
    TextButton.TextButtonStyle textButtonStyle;
    TextButton atras;
    TextButton siguiente;
    TextButton aceptar;
    TextButton cancelar;

    Sound click;
    public PantallaApuntarseCompeticion(Principal principal, String nombre_boxeador) {
        this.principal = principal;
        this.nombre_boxeador = nombre_boxeador;
        init();
        
    }
    
    public void init(){
        nombre_competicion = ""; 
        offsetConsulta = 0;
        
        TextureAtlas atlas=new TextureAtlas(Constantes.TEXTURE_ATLAS_UI);
        ui=new Skin(atlas);
        
        stage=new Stage();        
        Gdx.input.setInputProcessor(stage); 
        
        AssetManager am = new AssetManager();
        Assets.instance.init(am);
        click = Assets.instance.assetsSonido.click_boton;
        font = Assets.instance.assetsUi.generator.generateFont(Assets.instance.assetsUi.parameter);
                
        crearLabels();        
        crearBotones();
        
        tabla=new Table();
        pedirCompeticiones();
                       
        stage.addActor(tabla);
        
    }
    
    public void crearLabels(){
        label_error=new Label.LabelStyle();
        label_error.font=font;
        label_error.fontColor=Color.RED;
        
        error = new Label("",label_error);
        
        label=new Label.LabelStyle();
        label.font=font;
        label.fontColor=Color.WHITE;
        label.background = ui.getDrawable(Constantes.TEXTFIELD);
        
        
        label_estilo_seleccionado=new Label.LabelStyle();
        label_estilo_seleccionado.font=font;
        label_estilo_seleccionado.fontColor=Color.WHITE;
        label_estilo_seleccionado.background = ui.getDrawable(Constantes.TABLA);
        
        nombre= new Label("Nombre",label);
        nombre.setAlignment(Align.center);
        
        
        creador = new Label("Creador",label);
        creador.setAlignment(Align.center);
        
        fecha = new Label("Fecha comienzo",label);
        fecha.setAlignment(Align.center);
        
        participantes = new Label("Participantes",label);
        participantes.setAlignment(Align.center);
    }
    
    public void crearBotones(){
        textButtonStyle=new TextButton.TextButtonStyle();
        textButtonStyle.font=font;
        textButtonStyle.fontColor=Color.WHITE;
        textButtonStyle.up= ui.getDrawable(Constantes.BOTON);
        textButtonStyle.down= ui.getDrawable(Constantes.BOTON_PULSADO);
        textButtonStyle.pressedOffsetX=1;
        textButtonStyle.pressedOffsetY=-1;
        
        atras=new TextButton("ATRAS",textButtonStyle);
        atras.addListener(new ClickListener(){
            @Override
            public void clicked(InputEvent event, float x, float y) {  
                nombre_competicion = "";                
               offsetConsulta -= 5;
               if(offsetConsulta < 0){
                   offsetConsulta = 0;
               }
               click.play(100);
               pedirCompeticiones();
            }

        }); 
        
        siguiente=new TextButton("SIGUIENTE",textButtonStyle);
        siguiente.addListener(new ClickListener(){
            @Override
            public void clicked(InputEvent event, float x, float y) {                  
               nombre_competicion = "";
               if((offsetConsulta + 5)< maximoFilas){
                   offsetConsulta += 5;   
               }
               click.play(100);
                System.out.println("Offset: "+offsetConsulta);
               pedirCompeticiones();
            }

        });
        
        cancelar=new TextButton("CANCELAR",textButtonStyle);
        cancelar.addListener(new ClickListener(){
            @Override
            public void clicked(InputEvent event, float x, float y) {    
                click.play(100);
               principal.setScreen(new PantallaAccionesBoxeador(principal,nombre_boxeador));
            }

        });
        
        aceptar=new TextButton("ACEPTAR",textButtonStyle);
        aceptar.addListener(new ClickListener(){
            @Override
            public void clicked(InputEvent event, float x, float y) {                  
               if(nombre_competicion.equals("")){                   
                   error.setText("No tiene seleccionada ninguna competicion");
               }else{
                   click.play(100);
                   error.setText("");
                   registrarEnCompeticion();
               }
            }

        });
    }
    
    public void pedirCompeticiones(){
         String mensaje="15&"+nombre_boxeador+"&"+offsetConsulta;
         
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
            tratarMensaje(recibido);
            
            
        } catch (Exception e) {
            e.printStackTrace();
        }
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
    
    public void pintarTabla(){
        tabla.clear();
        tabla.setClip(true);
        tabla.setFillParent(true);
        tabla.add(atras).colspan(1).align(Align.left).height(40).width(120);
        tabla.add();
        tabla.add(siguiente).colspan(1).align(Align.right).height(40).width(120);
        tabla.row().space(10);
        tabla.add(nombre).width(150).height(40);                  
       // tabla.add(creador).width(150).height(40);                  
        tabla.add(fecha).width(150).height(40);                  
        tabla.add(participantes).width(150).height(40);                  
        tabla.row().space(10);
        tabla.addListener(new ClickListener(){
            @Override
            public void clicked(InputEvent event, float x, float y) {
                int num = tabla.getRow(y);                
                if(num >= 2 ){
                    Array<Cell> l= tabla.getCells();                                                         
                    for (int i = 0; i < l.size-1; i++) {
                        String tipo="Tipo de actor: "+l.get(i).getActor();                        
                        if(tipo.contains("Label") && !tipo.contains("error")){
                            Label l_selec= (Label) l.get(i).getActor();
                            if(i >= (num*3) && i < ((num*3)+3)){                            
                                l_selec.setStyle(label_estilo_seleccionado);
                                if(i == (num*3)){
                                    nombre_competicion = l_selec.getText().toString();
                                }
                            }else{
                                l_selec.setStyle(label);
                            }
                        }
                        
                       
                    }
                }
                
                
            }
            
        });
       
        tabla.setFillParent(true);
        
        pintarDatosTabla();
    }
    
    public void pintarDatosTabla(){
        
        String datos[] = competiciones.split("&");
        
        if(datos.length == 0){
            // informar de que no existe competicion a la que apuntarse   
       
            error.setText("En estos momentos no existen competiciones a las que apuntarse.");
            
        }else{
            error.setText("");
            for (int i = 2; i < datos.length; i++) {
                String informacion[] = datos[i].split(";");
                for (int j = 0; j < informacion.length; j++) {
                    
                    dato= new Label(informacion[j],label);
                    dato.setWrap(true);
                    dato.setAlignment(Align.center);
                    tabla.add(dato).width(150).height(40).fillX();                  

                }
                tabla.row().space(10);
            }
        }
        
        tabla.row().space(10);
        tabla.add(cancelar).colspan(1).align(Align.left).height(40).width(120);
        tabla.add();
        tabla.add(aceptar).colspan(1).align(Align.right).height(40).width(120);
        tabla.row().space(10);
        //tabla.add();
        tabla.add(error).colspan(3);
       // tabla.add();
        
        
    }
    
    public void tratarMensaje(String mensaje){
        String codigos[]=mensaje.split("&");
        int cod=Integer.parseInt(codigos[0]);
        switch(Codigos_Escritorio.codigo_escritorio(cod)){ 
            case COMPETICIONES_PARA_APUNTARSE:
                System.out.println(mensaje);
                maximoFilas = Integer.parseInt(codigos[1]);
                competiciones = mensaje;
                pintarTabla();
                break;
                
            case ERROR:
                tratarError(Integer.parseInt(codigos[1]));
                break;
                
            case BOXEADOR_APUNTADO_EXITO:
                principal.setScreen(new PantallaAccionesBoxeador(principal,nombre_boxeador));
                break;
        }
    }
    
    public void registrarEnCompeticion(){
        String mensaje="16&"+nombre_boxeador+"&"+nombre_competicion;
         
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
            tratarMensaje(recibido);
            
            
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    
    public void tratarError(int cod_error){
        switch(cod_error){
            case 13:
                error.setText("No pudo apuntarse en la competicion, intentelo más tarde.");
                break;
                
            case 14:
                error.setText("Ya estas apuntado a esa competicion");
                break;
        }
    }
}
