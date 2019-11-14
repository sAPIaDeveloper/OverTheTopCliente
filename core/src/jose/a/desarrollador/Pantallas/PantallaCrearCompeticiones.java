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
import static com.badlogic.gdx.graphics.Color.BLACK;
import static com.badlogic.gdx.graphics.Color.WHITE;
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
import com.badlogic.gdx.scenes.scene2d.ui.List;
import com.badlogic.gdx.scenes.scene2d.ui.ScrollPane;
import com.badlogic.gdx.scenes.scene2d.ui.SelectBox;
import com.badlogic.gdx.scenes.scene2d.ui.SelectBox.SelectBoxStyle;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton.TextButtonStyle;
import com.badlogic.gdx.scenes.scene2d.ui.TextField;
import com.badlogic.gdx.scenes.scene2d.ui.TextField.TextFieldStyle;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.utils.Align;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import jose.a.desarrollador.Principal;
import jose.a.desarrollador.Util.Assets;
import jose.a.desarrollador.Util.Codigos_Escritorio;
import jose.a.desarrollador.Util.Constantes;

/**
 *
 * @author josea
 */
public class PantallaCrearCompeticiones extends ScreenAdapter {
    Principal principal;
    String nombre_boxeador;
    Stage stage;    
    Table tabla;
    BitmapFont font;   
    TextureAtlas atlasUi;
    private Skin ui;
    Pixmap cursorColor;
    Sound click;
    
    LabelStyle label;
    LabelStyle style_error;
    Label titulo_pantalla;
    Label titulo_nombre;
    Label titulo_fecha_comienzo;
    Label titulo_categoria;
    Label mensaje_error;
    
    TextFieldStyle textFieldStyle;
    TextField nombre;
    TextField fecha_comienzo;
    
    SelectBoxStyle boxStyle;
    SelectBox<String> spinner_categorias;
    
    TextButtonStyle textButtonStyle;
    TextButton crear_competicion;
    TextButton atras;

    public PantallaCrearCompeticiones(final Principal principal,final String nombre_boxeador) {
        this.principal=principal;
        this.nombre_boxeador=nombre_boxeador;
        
        init();
    }
    
    public void init(){
        stage=new Stage();                      
        Gdx.input.setInputProcessor(stage);  
        
        AssetManager am = new AssetManager();
        Assets.instance.init(am);
        click = Assets.instance.assetsSonido.click_boton;
        font = Assets.instance.assetsUi.generator.generateFont(Assets.instance.assetsUi.parameter);
        
        atlasUi=new TextureAtlas(Constantes.TEXTURE_ATLAS_UI);
        ui=new Skin(atlasUi);
        
        crearLabels();
        crearTextFields();
        crearSpinner();
        crearBotones();
        crearTabla();
        
        stage.addActor(tabla);
        stage.setKeyboardFocus(nombre);
    }
    
    public void crearLabels(){
        LabelStyle f=new LabelStyle();
        f.font=font;
        Label oneCharSizeCalibrationThrowAway = new Label("|",f);
        
        cursorColor = new Pixmap((int) oneCharSizeCalibrationThrowAway.getWidth(),
            (int) oneCharSizeCalibrationThrowAway.getHeight(),Pixmap.Format.RGB888);
        cursorColor.setColor(Color.BLACK);
        cursorColor.fill();
        
        style_error=new LabelStyle();
        style_error.font=font;
        style_error.fontColor= Color.RED;
        mensaje_error=new Label("",style_error);
        
        label=new LabelStyle();
        label.font=font;
                
        titulo_pantalla=new Label("COMPETICION",label);
        titulo_nombre=new Label("Nombre: ",label);
        titulo_fecha_comienzo=new Label("Fecha de inicio: ",label);
        titulo_categoria=new Label("Categoría: ",label);
    }
    
    public void crearTextFields(){
        textFieldStyle= new TextFieldStyle();
        textFieldStyle.background=ui.getDrawable(Constantes.TEXTFIELD);
        textFieldStyle.font=font;
        textFieldStyle.fontColor=new Color(BLACK);
        textFieldStyle.cursor= new Image(new Texture(cursorColor)).getDrawable();        
        nombre= new TextField("",textFieldStyle);
        nombre.setAlignment(Align.center);
        fecha_comienzo= new TextField("AAAA/MM/DD",textFieldStyle);
        fecha_comienzo.setAlignment(Align.center);
    }
    
    public void crearBotones(){
        textButtonStyle = new TextButtonStyle();
        textButtonStyle.up= ui.getDrawable(Constantes.BOTON);
        textButtonStyle.down= ui.getDrawable(Constantes.BOTON_PULSADO);
        textButtonStyle.fontColor=Color.WHITE;
        textButtonStyle.font = font;
        
        textButtonStyle.pressedOffsetX=1;
        textButtonStyle.pressedOffsetY=-1;
        
        crear_competicion=new TextButton("CREAR",textButtonStyle);
        
        crear_competicion.addListener(new ClickListener(){
            @Override
            public void clicked(InputEvent event, float x, float y) {  
                click.play(100);                
                String texto_nombre=nombre.getText().trim();
                String texto_fecha=fecha_comienzo.getText().trim();
                if(!texto_nombre.equals("") && !texto_fecha.equals("")){
                    try {
                        SimpleDateFormat formatoFecha = new SimpleDateFormat("yyyy/MM/dd");
                        Date fecha=formatoFecha.parse(texto_fecha); 
                        if(fecha.getTime()<new Date().getTime()){
                            mensaje_error.setText("Fecha mal introducida");
                        }else{
                            mensaje_error.setText("");
                            String categoria=spinner_categorias.getSelected();
                            //Enviar datos para registrar
                            enviarDatos(texto_nombre,texto_fecha,categoria,8);
                             
                        }
                        
                        
                        
                    } catch (ParseException e) {
                        //informar error de la fecha
                        mensaje_error.setText("Formato de fecha no valido. Debe ser AAAA/MM/DD");
                    }
                    
                    
                    
                }else{
                    mensaje_error.setText("No puedes dejar campos vacios");
                }
                
                
            }

        });
        
        atras=new TextButton("ATRAS",textButtonStyle);
        atras.addListener(new ClickListener(){
            @Override
            public void clicked(InputEvent event, float x, float y) {
                click.play(100);
                principal.setScreen(new PantallaAccionesBoxeador(principal,nombre_boxeador));
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
        
        String[] categorias = new String[]{Constantes.CATEGORIA_PESO_PLUMA, Constantes.CATEGORIA_PESO_LIGERO,Constantes.CATEGORIA_PESO_WELTER,Constantes.CATEGORIA_PESO_MEDIO,Constantes.CATEGORIA_PESO_SEMIPESADO,Constantes.CATEGORIA_PESO_PESADO};
        spinner_categorias = new SelectBox<String>(boxStyle);
        spinner_categorias.setAlignment(Align.center);
        spinner_categorias.getList().setAlignment(Align.center);
        spinner_categorias.setItems(categorias);
    }
    
    public void crearTabla(){
        tabla=new Table();                
        tabla.setSize(400, 400);           
        tabla.setPosition((Gdx.graphics.getWidth()/2)-200,(Gdx.graphics.getHeight()/2)-200);         
        tabla.add(titulo_pantalla).colspan(3);
        tabla.row().spaceTop(20);
        
        tabla.add(titulo_nombre).width(100);
        tabla.add().width(50);
        tabla.add(nombre).width(200).height(25);
        tabla.row().spaceTop(20);
        tabla.add(titulo_fecha_comienzo).width(100);
        tabla.add().width(50);
        tabla.add(fecha_comienzo).width(200).height(25);
        tabla.row().spaceTop(20);
        tabla.add(titulo_categoria).width(100);
        tabla.add().width(50);
        tabla.add(spinner_categorias).width(200).height(25);
        tabla.row().spaceTop(25);
        tabla.add(mensaje_error).align(Align.center).colspan(3);
        tabla.row().spaceTop(25);
        tabla.add(atras).width(100).height(50).colspan(1);
        tabla.add(crear_competicion).width(200).height(50).colspan(2).align(Align.right);
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

    @Override
    public void dispose() {
        Assets.instance.dispose();


    }


    public void enviarDatos(String nombre,String fecha,String categoria,int accion){
        String mensaje=accion+"&"+nombre+"&"+fecha+"&"+categoria+"&"+Constantes.DATOS_USUARIO.getEmail();
        
        System.out.println("enviando datos...");
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
            
            mostrarMensaje(recibido);
        } catch (Exception e) {
            e.printStackTrace();
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
                
            case COMPETICION_CREADA:                
                principal.setScreen(new PantallaAccionesBoxeador(principal,nombre_boxeador));
                break;
                
            
        }
    }
    
    public void mostrarError(int codigo_error){
        switch(codigo_error){
            case 8:               
                mensaje_error.setText("Ya existe una competición con ese nombre. Pruebe otro");
            break;
               

            case 9:               
                mensaje_error.setText("Error al crear competicion");
            break;
        }
        
        
    }
    
}
