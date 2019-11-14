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
import java.util.ArrayList;
import jose.a.desarrollador.Entidades.Boxeador;
import jose.a.desarrollador.Principal;
import jose.a.desarrollador.Util.Assets;
import jose.a.desarrollador.Util.Constantes;
import jose.a.desarrollador.Util.Codigos_Escritorio;

/**
 *
 * @author josea
 */
public class PantallaRellenarPerfil extends ScreenAdapter {
    
    Principal principal;
    Stage stage; 
    BitmapFont font;
    Boxeador b;
    String tipo_boxeador;
    TextureAtlas atlasUi;
    Table tabla;  
    private Skin ui;
    Sound click;
    
    TextFieldStyle textFieldStyle;
    TextField nombre;
    TextField peso;
    
    Label.LabelStyle label;
    LabelStyle mensaje_error;
    Label titulo;
    Label texto_nombre;
    Label texto_nacionalidad;
    Label texto_peso;
    Label error;
    LabelStyle f;
    
    TextButton atras;
    TextButton aceptar;
    TextButton.TextButtonStyle textButtonStyle;
    TextButton izquierda;
    TextButton derecha;
    
    SelectBox<String> spinner_peso;
    
    ArrayList<String> paises;
    int indice_pais;
    TextButton bandera;
    
    String nombre_usuario;
    double peso_boxead;
    Pixmap cursorColor;
    
    public PantallaRellenarPerfil(Principal principal,String tipo_boxeador) {
        this.principal = principal;
        this.tipo_boxeador = tipo_boxeador;
        
        init(); 
    }

    public void init(){
        paises=new ArrayList();
        paises.add("Espana");
        paises.add("Inglaterra");
        paises.add("Francia");
        indice_pais=0;
        
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
        crearBotones();
        crearSpinner();
        crearTabla();
        
        stage.addActor(tabla);
        stage.setKeyboardFocus(nombre);
        
        
    }
    
    public void crearLabels(){
        label=new LabelStyle();
        label.font=font;
        label.fontColor=Color.WHITE;
        
        titulo= new Label("PERFIL DEL PERSONAJE",label);
        titulo.setAlignment(Align.center);
        titulo.setFontScale(2);
        
        texto_nombre= new Label("Nombre:",label);
        texto_nombre.setAlignment(Align.left);
        texto_nacionalidad= new Label("Nacionalidad:",label);
        texto_peso= new Label("Peso:",label);
        texto_peso.setAlignment(Align.left);
        
        mensaje_error=new LabelStyle();
        mensaje_error.font=font;
        mensaje_error.fontColor= Color.RED;
        error=new Label("",mensaje_error);
        error.setAlignment(Align.center);
        
        f=new LabelStyle();
        f.font=font;
        Label oneCharSizeCalibrationThrowAway = new Label("|",f);
        
        cursorColor = new Pixmap((int) oneCharSizeCalibrationThrowAway.getWidth(),
            (int) oneCharSizeCalibrationThrowAway.getHeight(),Pixmap.Format.RGB888);
        cursorColor.setColor(Color.BLACK);
        cursorColor.fill();
    }
    
    public void crearTextFields(){
        textFieldStyle=new TextFieldStyle();
        textFieldStyle.font=font;
        textFieldStyle.fontColor=Color.BLACK;
        textFieldStyle.background=ui.getDrawable(Constantes.TEXTFIELD);
        
        nombre=new TextField("",textFieldStyle);
        nombre.setAlignment(Align.center);
        peso=new TextField("",textFieldStyle);
        peso.setAlignment(Align.center);
        
        textFieldStyle.cursor= new Image(new Texture(cursorColor)).getDrawable();
    }
    
    public void crearBotones(){
        textButtonStyle=new TextButtonStyle();
        textButtonStyle.font=font;
        textButtonStyle.fontColor=Color.WHITE;
        textButtonStyle.up= ui.getDrawable(Constantes.BOTON);
        textButtonStyle.down= ui.getDrawable(Constantes.BOTON_PULSADO);
        textButtonStyle.pressedOffsetX=1;
        textButtonStyle.pressedOffsetY=-1;
        
        atras=new TextButton("ATRAS",textButtonStyle);
        aceptar=new TextButton("ACEPTAR",textButtonStyle);
        
        aceptar.addListener(new ClickListener(){
            @Override
            public void clicked(InputEvent event, float x, float y) { 
                click.play(100);
                System.out.println("Nacionalidad: "+paises.get(indice_pais));
                if(nombre.getText().equals("") || peso.getText().equals("")){
                    error.setText("Todos los campos deben estar relleno");
                }else{
                    try {
                        double peso_box=Double.parseDouble(peso.getText());
                        String nombre_boxeador=nombre.getText();
                        String pais_boxeador=paises.get(indice_pais);
                        char sexo;
                        if(tipo_boxeador.contains("Boxeadora")){
                            sexo='M';
                        }else{
                            sexo='H';
                        }
                        String categoria=asignarCategoria(peso_box,spinner_peso.getSelected());
                        registrarBoxeador(nombre_boxeador,tipo_boxeador,sexo,peso_box,pais_boxeador,categoria);
                    } catch (Exception eo) {
                        error.setText("El campo de peso no puede contener letras o signos.");
                    }
                }
            }

        });
        
        atras.addListener(new ClickListener(){
            @Override
            public void clicked(InputEvent event, float x, float y) { 
                click.play(100);
               principal.setScreen(new PantallaElegirPersonaje(principal));
            }

        });
        
       
        
        
        
        
        
        TextButtonStyle textButtonDerecha=new TextButtonStyle();
        textButtonDerecha.font=font;
        textButtonDerecha.fontColor=Color.BLACK;
        textButtonDerecha.up= ui.getDrawable(Constantes.FLECHA_DERECHA);
        textButtonDerecha.down= ui.getDrawable(Constantes.FLECHA_DERECHA_SELECCIONADA);
        
        TextButtonStyle textButtonIzquierda=new TextButtonStyle();
        textButtonIzquierda.font=font;
        textButtonIzquierda.fontColor=Color.BLACK;
        textButtonIzquierda.up= ui.getDrawable(Constantes.FLECHA_IZQUIERDA);
        textButtonIzquierda.down= ui.getDrawable(Constantes.FLECHA_IZQUIERDA_SELECCIONADA);        
        
        izquierda= new TextButton("", textButtonIzquierda);
        
        derecha=new TextButton("",textButtonDerecha);
        
        TextButtonStyle textButtonBandera=new TextButtonStyle();
        textButtonBandera.font=font;
        textButtonBandera.fontColor=Color.WHITE;
        textButtonBandera.up= ui.getDrawable(paises.get(indice_pais));
       
        
        bandera=new TextButton("",textButtonBandera);
        
        
        
        izquierda.addListener(new ClickListener(){
            @Override
            public void clicked(InputEvent event, float x, float y) {                  
               indice_pais--;
               if(indice_pais<0){
                   indice_pais=paises.size()-1;
                }
               bandera.getStyle().up=ui.getDrawable(paises.get(indice_pais));
               
            }

        }); 
        
        derecha.addListener(new ClickListener(){
            @Override
            public void clicked(InputEvent event, float x, float y) {                  
                indice_pais++;
                if(indice_pais==paises.size()){
                   indice_pais=0;
                }
                bandera.getStyle().up=ui.getDrawable(paises.get(indice_pais));
            }

        });
    }
    
    public void crearSpinner(){
        SelectBox.SelectBoxStyle boxStyle = new SelectBox.SelectBoxStyle();
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
        
        String[] medida_peso = new String[]{"Kg", "Lb"};
        spinner_peso = new SelectBox<String>(boxStyle);
        spinner_peso.setAlignment(Align.center);
        spinner_peso.getList().setAlignment(Align.center);
        
        spinner_peso.setItems(medida_peso);
    }
    
    public void crearTabla(){
        tabla= new Table();   
        tabla.setSize(400, 400);           
        tabla.setPosition((Gdx.graphics.getWidth()/2)-200,(Gdx.graphics.getHeight()/2)-200); 
        
        tabla.add(titulo).colspan(4).width(400).top();
        tabla.row().spaceTop(20);
        tabla.add(texto_nombre).height(30).colspan(2).fillX().padLeft(15);
        tabla.add(nombre).height(30).colspan(2);
        tabla.row().spaceTop(20);
        tabla.add(texto_nacionalidad).height(30).padLeft(10).padRight(10);
        tabla.add(izquierda).width(50).height(50);
        tabla.add(bandera).width(100).height(70);
        tabla.add(derecha).width(50).height(50).padRight(10);
        tabla.row().spaceTop(20);
        tabla.add(texto_peso).colspan(2).height(30).fillX().padLeft(15);
        tabla.add(peso).width(150).height(30);
        tabla.add(spinner_peso).width(50).height(30).padRight(10);
        tabla.row().spaceTop(20);
        tabla.add(error).colspan(4).width(400);
        tabla.row().spaceTop(20);
        tabla.add(atras).colspan(2).height(40).width(100);
        tabla.add(aceptar).colspan(2).height(40).width(100);
       
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
    
    public void registrarBoxeador(String nombre,String tipo_boxeador,char sexo,double peso, String pais,String categoria){
        String mensaje="2&"+Constantes.DATOS_USUARIO.getEmail()+"&"+nombre+"&"+tipo_boxeador+"&"+sexo+"&"+peso+"&"+pais+"&"+categoria;
        b=new Boxeador();
        b.setNombre_boxeador(nombre);
        b.setTipo_boxeador(tipo_boxeador);
        b.setSexo(sexo);
        b.setPeso(peso);
        b.setPais(pais);
        b.setCategoria(categoria);
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
    
    public String asignarCategoria(double peso,String medida){
        
        switch(medida){
            case "Kg":
                if(peso <Constantes.PESO_LIGERO_KG){
                    return Constantes.CATEGORIA_PESO_PLUMA;
                }else if(peso>= Constantes.PESO_LIGERO_KG && peso < Constantes.PESO_WELTER_KG){
                    return Constantes.CATEGORIA_PESO_LIGERO;
                }else if(peso >= Constantes.PESO_WELTER_KG && peso < Constantes.PESO_MEDIO_KG){
                    return Constantes.CATEGORIA_PESO_WELTER;
                }else if(peso >= Constantes.PESO_MEDIO_KG && peso < Constantes.PESO_SEMIPESADO_KG){
                    return Constantes.CATEGORIA_PESO_MEDIO;
                }else if(peso >= Constantes.PESO_SEMIPESADO_KG && peso < Constantes.PESO_PESADO_KG){
                    return Constantes.CATEGORIA_PESO_SEMIPESADO;
                }else if(peso>= Constantes.PESO_PESADO_KG){
                    return Constantes.CATEGORIA_PESO_PESADO;
                }
                break;
                
            case "Lb":
                if(peso <Constantes.PESO_LIGERO_LB){
                    return Constantes.CATEGORIA_PESO_PLUMA;
                }else if(peso>= Constantes.PESO_LIGERO_LB && peso < Constantes.PESO_WELTER_LB){
                    return Constantes.CATEGORIA_PESO_LIGERO;
                }else if(peso >= Constantes.PESO_WELTER_LB && peso < Constantes.PESO_MEDIO_LB){
                    return Constantes.CATEGORIA_PESO_WELTER;
                }else if(peso >= Constantes.PESO_MEDIO_LB && peso < Constantes.PESO_SEMIPESADO_LB){
                    return Constantes.CATEGORIA_PESO_MEDIO;
                }else if(peso >= Constantes.PESO_SEMIPESADO_LB && peso < Constantes.PESO_PESADO_LB){
                    return Constantes.CATEGORIA_PESO_SEMIPESADO;
                }else if(peso>= Constantes.PESO_PESADO_LB){
                    return Constantes.CATEGORIA_PESO_PESADO;
                }
                break;
        }
        return "";
    }
    
    public void mostrarMensaje(String mensaje){
        System.out.println("Mensaje recibido: "+mensaje);        
        String codigos[]=mensaje.split("&");
        int cod=Integer.parseInt(codigos[0]);
        switch(Codigos_Escritorio.codigo_escritorio(cod)){
            case ERROR:
                mostrarError(Integer.parseInt(codigos[1]));
                break;
                
            case REGISTRO_BOXEADOR_COMPLETADO:
                System.out.println("Registrado con exito");
                Constantes.DATOS_USUARIO.setBoxeador(b.getNombre_boxeador(), b);
                principal.setScreen(new PantallaSeleccionOCreacionPersonaje(principal));
                break;
                
            
        }
    }
    
    public void mostrarError(int codigo_error){
        switch(codigo_error){
            case 4:                
                error.setText("Ya existe este nombre de usuario, introduzca otro por favor.");
                break;
                
            case 5:
                error.setText("Problema con el registro del boxeador, vuelva a intentarlo.");
                break;
        }
    }

    @Override
    public void dispose() {
        Assets.instance.dispose();


    }
}
