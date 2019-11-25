/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package jose.a.desarrollador.Pantallas;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.ScreenAdapter;
import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.Color;
import static com.badlogic.gdx.graphics.Color.*;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Pixmap;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Label.LabelStyle;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton.TextButtonStyle;
import com.badlogic.gdx.scenes.scene2d.ui.TextField;
import com.badlogic.gdx.scenes.scene2d.ui.TextField.TextFieldStyle;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.utils.Align;
import com.badlogic.gdx.utils.viewport.ExtendViewport;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import jose.a.desarrollador.Entidades.Boxeador;
import jose.a.desarrollador.Principal;
import jose.a.desarrollador.Util.Assets;
import jose.a.desarrollador.Util.Codigos_Escritorio;
import jose.a.desarrollador.Util.Constantes;
import jose.a.desarrollador.Util.HiloComunicacionAbierta;
import jose.a.desarrollador.Util.Preferencias;
import org.apache.commons.codec.digest.DigestUtils;

/**
 * Posibles colores de fondo:
 *  77/255f,182/255f,172/255f,1
 *  128/255f,203/255f,196/255f,1
 * 
 *
 * @author josea
 */
public class PantallaLoguin extends ScreenAdapter {
    Principal principal;
    Stage stage;   
    Preferencias pref;
    private ExtendViewport extendViewport;
    TextButton boton_login;
    TextButton boton_registro;
    TextButtonStyle textButtonStyle;
    TextButton boton_opciones;    
    TextFieldStyle textFieldStyle;
    TextField email;
    TextField contrasena;
    LabelStyle label;
    Label texto_email;
    Label texto_contrasena;
    LabelStyle mensaje_error;
    LabelStyle f;
    BitmapFont font;
    Table tabla;    
    private Skin ui;
    Sound click;   
    int volumen;
    TextureAtlas atlas;
    Label error;
    Pixmap cursorColor;
    public static Thread t;
    public PantallaLoguin(Principal principal) {  
        this.principal=principal;
        
        init();
    }

    public void init(){
        AssetManager am = new AssetManager();
        Assets.instance.init(am);
        
        font = Assets.instance.assetsUi.generator.generateFont(Assets.instance.assetsUi.parameter);
        click = Assets.instance.assetsSonido.click_boton;
        stage=new Stage();  
        pref = new Preferencias();
        volumen = pref.getVolumen_sfx();
        Gdx.input.setInputProcessor(stage);//Para que detecte los eventos de raton
        extendViewport=new ExtendViewport(Constantes.WORLD_SIZE,Constantes.WORLD_SIZE);
        stage.setViewport(extendViewport);
        atlas=new TextureAtlas(Constantes.TEXTURE_ATLAS_UI);
        ui=new Skin(atlas); 
        
        crearLabels();        
        crearBotones();        
        crearTextFields();
        crearTabla();
        
        stage.addActor(tabla);
        stage.setKeyboardFocus(email);
        
        
    }
    
    public void crearLabels(){
        label=new LabelStyle();
        label.font=font;
        
        texto_email= new Label("Email",label);
        texto_contrasena= new Label("Contraseña",label);
        
        mensaje_error=new LabelStyle();
        mensaje_error.font=font;
        mensaje_error.fontColor= Color.RED;
        error=new Label("",mensaje_error);
        error.setWrap(true);
        error.setAlignment(Align.center);
        f=new LabelStyle();
        f.font=font;
        Label oneCharSizeCalibrationThrowAway = new Label("|",f);
        
        cursorColor = new Pixmap((int) oneCharSizeCalibrationThrowAway.getWidth(),
            (int) oneCharSizeCalibrationThrowAway.getHeight(),Pixmap.Format.RGB888);
        cursorColor.setColor(Color.BLACK);
        cursorColor.fill();
    }
    
    public void crearBotones(){
        // Creacion de como va a ser el boton con textbuttonStyle
        textButtonStyle = new TextButtonStyle();
        textButtonStyle.up= ui.getDrawable(Constantes.BOTON);
        textButtonStyle.down= ui.getDrawable(Constantes.BOTON_PULSADO);
        textButtonStyle.fontColor=Color.WHITE;
        textButtonStyle.font = font;
        
        textButtonStyle.pressedOffsetX=1;
        textButtonStyle.pressedOffsetY=-1;
        
        boton_login = new TextButton("Iniciar Sesion", textButtonStyle);
        boton_registro = new TextButton("Registro", textButtonStyle);
        
        //Eventos de click en los botones
        
        boton_registro.addListener(new ClickListener(){
            @Override
            public void clicked(InputEvent event, float x, float y) { 
                click.play(volumen);
                String texto_email=email.getText();
                 String texto_contrasena=contrasena.getText();  
                 String ContraseñaCodificada =  DigestUtils.sha256Hex(texto_contrasena);                 
                 if(texto_email.equals("") | texto_contrasena.equals("")){
                    error.setText("No puedes dejar campos vacios");
                }else{
                    if(formatoCorrectoCorreo(texto_email)){
                         enviarDatos(texto_email,ContraseñaCodificada,0);
                    }else{
                        error.setText("El formato del correo electronico es invalido");
                    }
                }
                
               
                
            }

        });
        
        boton_login.addListener(new ClickListener(){
            @Override
            public void clicked(InputEvent event, float x, float y) { 
                click.play(volumen);
                String texto_email=email.getText().trim();
                String texto_contrasena=contrasena.getText().trim();
                String ContraseñaCodificada =  DigestUtils.sha256Hex(texto_contrasena);
                
                if(texto_email.equals("") | texto_contrasena.equals("")){
                    error.setText("No puedes dejar campos vacios");
                }else{
                    if(formatoCorrectoCorreo(texto_email)){
                         enviarDatos(texto_email,ContraseñaCodificada,1);
                    }else{
                        error.setText("El formato del correo electronico es invalido");
                    }
                }
                
                
            }

        });               
        
        boton_opciones = new TextButton("Opciones",textButtonStyle);
        boton_opciones.addListener(new ClickListener(){
            @Override
            public void clicked(InputEvent event, float x, float y) { 
                click.play(volumen);
                principal.setScreen(new PantallaOpciones(principal));
                
            }

        });
    }
    
    public void crearTextFields(){
         // Creacion de como va a ser el textField con textFieldStyle
        textFieldStyle= new TextFieldStyle();
        textFieldStyle.background=ui.getDrawable(Constantes.TEXTFIELD);
        textFieldStyle.font=font;
        textFieldStyle.fontColor=new Color(BLACK);
        textFieldStyle.cursor= new Image(new Texture(cursorColor)).getDrawable();
        
        // Creacion de los textField 
        email= new TextField("",textFieldStyle);
        email.setAlignment(Align.center);
        
        
        contrasena= new TextField("",textFieldStyle);
        contrasena.setPasswordMode(true);
        contrasena.setPasswordCharacter('*');
        contrasena.setAlignment(Align.center);
    }
    
    public void crearTabla(){
        tabla= new Table();               
        tabla.setFillParent(true);
        
        tabla.add(texto_email).width(50);
        tabla.add(email).width(200).height(50);
        tabla.row().spaceTop(20);
        tabla.add(texto_contrasena);
        tabla.add(contrasena).width(200).height(50);
        tabla.row();
                     
        
        
        tabla.add(error).colspan(2).height(25);
        tabla.row();  
        tabla.add(boton_login).width(200).height(60).space(20).align(Align.center).colspan(2);
        tabla.row();  
        
        tabla.add(boton_registro).width(200).height(60).space(20).align(Align.center).colspan(2);
        tabla.row();  
        
        tabla.add(boton_opciones).width(200).height(60).space(20).align(Align.center).colspan(2);
        tabla.row();  
        
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
    
    public boolean formatoCorrectoCorreo(String email){        
        Pattern pattern = Pattern
                .compile("^[_A-Za-z0-9-\\+]+(\\.[_A-Za-z0-9-]+)*@"
                        + "[A-Za-z0-9-]+(\\.[A-Za-z0-9]+)*(\\.[A-Za-z]{2,})$");        
 
        Matcher mather = pattern.matcher(email);
 
        if (mather.find() == true) {
           return true;
        } else {
           return false;
        }
    }
            
    public void enviarDatos(String email,String contrasena,int accion){
        String mensaje=accion+"&"+email+"&"+contrasena;
        Constantes.DATOS_USUARIO.setEmail(email);
        System.out.println("enviando datos...");
        try {
            DatagramSocket socketD = new DatagramSocket();// Creo un socket tipo datagrama
            socketD.setSoTimeout(2000);
            byte[] mesg=mensaje.getBytes();// Paso el mensaje a un array de bytes
            InetAddress address = InetAddress.getByName(pref.getDireccion_ip());// Creo un objeto InetAddress con la ip
            DatagramPacket packetToComunication = new DatagramPacket(mesg, mesg.length, address, Constantes.PUERTO); // Creo el paquete con la información
            socketD.send(packetToComunication);// Envio el paquete.
            System.out.println(socketD.isConnected());
            byte[] bufIn = new byte[256]; 
            DatagramPacket paqueteEntrada = new DatagramPacket(bufIn, bufIn.length); 
           
            socketD.receive(paqueteEntrada);// Espero para recibir respuesta            
            String recibido = new String(paqueteEntrada.getData(), 0, paqueteEntrada.getLength());

            mostrarMensaje(recibido);
            
            
        } catch (Exception e) {
            error.setText("Compruebe si ha escrito bien la direccion IP o si tiene conexion a internet");
        }
        
    }
    
    public void mostrarMensaje(String mensaje){
        System.out.println("Mensaje recibido: "+mensaje);        
        String codigos[]=mensaje.split("&");
        int cod=Integer.parseInt(codigos[0]);
        switch(Codigos_Escritorio.codigo_escritorio(cod)){
            case ERROR:
                mostrarError(Integer.parseInt(codigos[1]));
                break;
                
            case REGISTRO_USUARIO_COMPLETADO:
                t = new HiloComunicacionAbierta(codigos[1],principal);
                t.start();
                System.out.println("Registrado con exito");
                principal.setScreen(new PantallaSeleccionOCreacionPersonaje(principal));
                break;
                
            case INICIAR_SESION_ACEPTADO:
                System.out.println("Logueado con exito"); 
                t = new HiloComunicacionAbierta(codigos[1],principal);
                t.start();
                if(codigos.length>2){                    
                    for (int i = 2; i < codigos.length; i++) {
                        String [] datos_boxeador=codigos[i].split("@");
                        Boxeador b=new Boxeador();
                        String nombre=datos_boxeador[0];
                        String tipo_box=datos_boxeador[1];
                        String peso=datos_boxeador[2];
                        String pais=datos_boxeador[3];
                        b.setNombre_boxeador(nombre);
                        b.setTipo_boxeador(tipo_box);
                        b.setPeso(Double.parseDouble(peso));
                        b.setPais(pais);
                        Constantes.DATOS_USUARIO.setBoxeador(nombre, b);
                    }
                }
                
                principal.setScreen(new PantallaSeleccionOCreacionPersonaje(principal));
                
                break;
        }
    }
    
    public void mostrarError(int codigo_error){
        switch(codigo_error){
            case 1:
                
                error.setText("Email ya registrado, pruebe con otro.");
                break;
                
            case 2:
                error.setText("Problema al registrarse, intentelo de nuevo.");
                break;
                
            case 3:
                error.setText("Error al iniciar sesion");
                break;
                
            case 12:
                error.setText("Ya se encuentra logueado desde otro ordenador,\n por favor cierre sesion antes");
                break;
        }
        
        
    }

    @Override
    public void dispose() {
        Assets.instance.dispose();


    }

}
