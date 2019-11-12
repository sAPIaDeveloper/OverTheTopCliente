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
import com.badlogic.gdx.scenes.scene2d.ui.Label.LabelStyle;
import com.badlogic.gdx.scenes.scene2d.ui.List;
import com.badlogic.gdx.scenes.scene2d.ui.ScrollPane;
import com.badlogic.gdx.scenes.scene2d.ui.SelectBox;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.ui.TextField;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.utils.Align;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.util.ArrayList;
import jose.a.desarrollador.Entidades.Boxeador;
import jose.a.desarrollador.Principal;
import jose.a.desarrollador.Util.Assets;
import jose.a.desarrollador.Util.Codigos_Escritorio;
import jose.a.desarrollador.Util.Constantes;

/**
 *
 * @author josea
 */
public class PantallaModificarDatosBoxeador extends ScreenAdapter{
    
    Principal principal;
    String nombre_boxeador_antiguo;
    Boxeador boxeador;
    Stage stage; 
    BitmapFont font;
    Boxeador b;
    
    TextureAtlas atlasUi;
    TextureAtlas box;
    Table tabla;  
    private Skin ui;
    private Skin boxin;
    
    
    TextField.TextFieldStyle textFieldStyle;
    TextField nombre;
    TextField peso;
    
    Label.LabelStyle label;
    Label titulo;
    Label texto_nombre;
    Label texto_nacionalidad;
    Label texto_peso;
    Label texto_tipo_boxeador;
    Label error;
    
    TextButton atras;
    TextButton aceptar;
    TextButton borrar;
    TextButton izquierdaB;
    TextButton izquierdaBx;
    TextButton derechaB;
    TextButton derechaBx;
    TextButton.TextButtonStyle textButtonStyle;
    
    SelectBox<String> spinner_peso;
    
    ArrayList<String> paises;
    ArrayList<String> boxeadores;
    int indice_pais;
    int indice_boxeadores;
    TextButton bandera;
    TextButton imagen_boxeador;
    
    String nombre_usuario;
    double peso_boxead;

    public PantallaModificarDatosBoxeador(final Principal principal, final String nombre_boxeador_antiguo) {
        this.principal = principal;
        this.nombre_boxeador_antiguo = nombre_boxeador_antiguo;
        
        init();  
    }
    
    public void init(){
        boxeador=Constantes.DATOS_USUARIO.getBoxeador(nombre_boxeador_antiguo);
        paises=new ArrayList();
        paises.add("Espana");
        paises.add("Inglaterra");
        paises.add("Francia");
        indice_pais=0;
        indice_boxeadores=0;
        
        boxeadores=new ArrayList();
        boxeadores.add(Constantes.BOXEADORA_CECILIA);
        boxeadores.add(Constantes.BOXEADORA_JESSI);
        boxeadores.add(Constantes.BOXEADORA_KATE);
        boxeadores.add(Constantes.BOXEADOR_BUZZ);
        boxeadores.add(Constantes.BOXEADOR_JOHN);
        boxeadores.add(Constantes.BOXEADOR_NEGRO);
        indice_boxeadores=0;
        
        stage=new Stage();
        Gdx.input.setInputProcessor(stage);
        AssetManager am = new AssetManager();
        Assets.instance.init(am);
        font = Assets.instance.assetsUi.generator.generateFont(Assets.instance.assetsUi.parameter);
        atlasUi=new TextureAtlas(Constantes.TEXTURE_ATLAS_UI);        
        ui=new Skin(atlasUi);
        
        box=new TextureAtlas(Constantes.TEXTURE_ATLAS_ROSTRO_BOXEADORES);
        boxin=new Skin(box);
        
        crearLabels();
        crearTextFields();
        crearSpinner();
        crearBotones();
        crearTabla();
        
        stage.addActor(tabla);
    }
    
    public void crearLabels(){
        label=new Label.LabelStyle();
        label.font=font;
        label.fontColor=Color.WHITE;
        
        titulo= new Label("PERFIL DEL PERSONAJE",label);
        titulo.setAlignment(Align.center);
        titulo.setFontScale(1);
        
        LabelStyle styleError=new LabelStyle();
        styleError.font=font;
        styleError.fontColor=Color.RED;
        
        error=new Label("",styleError);
        
        texto_nombre= new Label("Nombre:",label);
        texto_nombre.setAlignment(Align.left);
        texto_nacionalidad= new Label("Nacionalidad:",label);
        texto_peso= new Label("Peso:",label);
        texto_peso.setAlignment(Align.left);
        texto_tipo_boxeador= new Label("Imagen:",label);
        texto_tipo_boxeador.setAlignment(Align.left);
    }
    
    public void crearTextFields(){
        textFieldStyle=new TextField.TextFieldStyle();
        textFieldStyle.font=font;
        textFieldStyle.fontColor=Color.BLACK;
        textFieldStyle.background=ui.getDrawable(Constantes.TEXTFIELD);
        
        nombre=new TextField(boxeador.getNombre_boxeador(),textFieldStyle);
        nombre.setAlignment(Align.center);
        peso=new TextField(boxeador.getPeso()+"",textFieldStyle);
        peso.setAlignment(Align.center);
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
        
        String[] medida_peso = new String[]{"Kg", "Lb"};
        spinner_peso = new SelectBox<String>(boxStyle);
        spinner_peso.setAlignment(Align.center);
        spinner_peso.getList().setAlignment(Align.center);
        
        spinner_peso.setItems(medida_peso);
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
               principal.setScreen(new PantallaAccionesBoxeador(principal,nombre_boxeador_antiguo));
            }

        }); 
        
        aceptar=new TextButton("ACEPTAR",textButtonStyle);
        aceptar.addListener(new ClickListener(){
            @Override
            public void clicked(InputEvent event, float x, float y) {                  
               if(comprobarErrores()){
                   char l;
                   if(boxeadores.get(indice_boxeadores).contains("Boxeadora")){
                    l='M';
                }else l='H';
                   registrarBoxeador(nombre_boxeador_antiguo,nombre.getText(),boxeadores.get(indice_boxeadores),l,peso.getText(),paises.get(indice_pais),asignarCategoria(Double.parseDouble(peso.getText()),spinner_peso.getSelected()));
               } 
            }

        }); 
        borrar=new TextButton("BORRAR",textButtonStyle);
        borrar.addListener(new ClickListener(){
            @Override
            public void clicked(InputEvent event, float x, float y) { 
                final Table tabla2= new Table();
                Label mensaje_seguridad= new Label("¿Seguro que desea borrar el boxeador y todos sus datos?. Esta accion no se podra deshacer",label);
                mensaje_seguridad.setWrap(true);
                TextButton aceptar_borrar=new TextButton("BORRAR",textButtonStyle);
                TextButton cancelar_borrar=new TextButton("CANCELAR",textButtonStyle);
                cancelar_borrar.addListener(new ClickListener(){
                    @Override
                    public void clicked(InputEvent event, float x, float y) {                  
                       tabla2.remove();
                    }

                });
                
                aceptar_borrar.addListener(new ClickListener(){
                    @Override
                    public void clicked(InputEvent event, float x, float y) {                  
                       tabla2.remove();
                       borrarBoxeador(nombre_boxeador_antiguo);
                    }

                });
                  
              //  tabla2.setDebug(true);
                tabla2.setBackground(ui.getDrawable(Constantes.TABLA));        
                tabla2.setSize(300, 300);           
                tabla2.setPosition((Gdx.graphics.getWidth()/2)-150,(Gdx.graphics.getHeight()/2)-150); 
                tabla2.add(mensaje_seguridad).width(250).height(100).align(Align.center).colspan(2);
                tabla2.row().spaceTop(100);
                tabla2.add(aceptar_borrar).width(100).height(50);
                tabla2.add(cancelar_borrar).width(100).height(50);
                stage.addActor(tabla2);
             
           
            }

        }); 
        
        TextButton.TextButtonStyle textButtonDerecha=new TextButton.TextButtonStyle();
        textButtonDerecha.font=font;
        textButtonDerecha.fontColor=Color.BLACK;
        textButtonDerecha.up= ui.getDrawable(Constantes.FLECHA_DERECHA);
        textButtonDerecha.down= ui.getDrawable(Constantes.FLECHA_DERECHA_SELECCIONADA);
        
        TextButton.TextButtonStyle textButtonIzquierda=new TextButton.TextButtonStyle();
        textButtonIzquierda.font=font;
        textButtonIzquierda.fontColor=Color.BLACK;
        textButtonIzquierda.up= ui.getDrawable(Constantes.FLECHA_IZQUIERDA);
        textButtonIzquierda.down= ui.getDrawable(Constantes.FLECHA_IZQUIERDA_SELECCIONADA); 
        
        izquierdaB=new TextButton("",textButtonIzquierda);
        derechaB=new TextButton("",textButtonDerecha);
        
        TextButton.TextButtonStyle textButtonBandera=new TextButton.TextButtonStyle();
        textButtonBandera.font=font;
        textButtonBandera.fontColor=Color.BLACK;
        textButtonBandera.up= ui.getDrawable(boxeador.getPais());
       
        bandera=new TextButton("",textButtonBandera);
        
        izquierdaB.addListener(new ClickListener(){
            @Override
            public void clicked(InputEvent event, float x, float y) {                  
               indice_pais--;
               if(indice_pais<0){
                   indice_pais=paises.size()-1;
                }
               bandera.getStyle().up=ui.getDrawable(paises.get(indice_pais));
               
            }

        }); 
        
        derechaB.addListener(new ClickListener(){
            @Override
            public void clicked(InputEvent event, float x, float y) {                  
                indice_pais++;
                if(indice_pais==paises.size()){
                   indice_pais=0;
                }
                bandera.getStyle().up=ui.getDrawable(paises.get(indice_pais));
            }

        });
        
        izquierdaBx=new TextButton("",textButtonIzquierda);
        derechaBx=new TextButton("",textButtonDerecha);
        TextButton.TextButtonStyle textButtonBoxeador=new TextButton.TextButtonStyle();
        textButtonBoxeador.font=font;
        textButtonBoxeador.fontColor=Color.BLACK;
        textButtonBoxeador.up= boxin.getDrawable(boxeador.getTipo_boxeador());
        imagen_boxeador=new TextButton("",textButtonBoxeador);
        
        izquierdaBx.addListener(new ClickListener(){
            @Override
            public void clicked(InputEvent event, float x, float y) {                  
               indice_boxeadores--;
               if(indice_boxeadores<0){
                   indice_boxeadores=boxeadores.size()-1;
                }
               
               imagen_boxeador.getStyle().up=boxin.getDrawable(boxeadores.get(indice_boxeadores));
               
            }

        }); 
        
        derechaBx.addListener(new ClickListener(){
            @Override
            public void clicked(InputEvent event, float x, float y) {
                
                indice_boxeadores++;
                if(indice_boxeadores==boxeadores.size()){
                   indice_boxeadores=0;
                }
                
                imagen_boxeador.getStyle().up=boxin.getDrawable(boxeadores.get(indice_boxeadores));
            }

        });
        
        
    }
    
    public void crearTabla(){
        tabla= new Table();          
        tabla.setSize(500, 500);           
        tabla.setPosition((Gdx.graphics.getWidth()/2)-250,(Gdx.graphics.getHeight()/2)-250); 
        
        tabla.add(titulo).width(200).align(Align.center).colspan(4);
        tabla.row().spaceTop(20);
        tabla.add(texto_nombre).width(100).height(50).colspan(1).fillX();
        tabla.add(nombre).width(150).height(30).colspan(3).left();
        tabla.row().spaceTop(20);
        tabla.add(texto_peso).width(100).height(50).colspan(1);
        tabla.add(peso).width(150).height(30).colspan(2).left();
        tabla.add(spinner_peso).width(50).height(20).colspan(1);
        tabla.row().spaceTop(20);
        tabla.add(texto_nacionalidad).width(100).height(50);
        tabla.add(izquierdaB).width(50).height(50).padRight(10);
        tabla.add(bandera).width(100).height(70);
        tabla.add(derechaB).width(50).height(50).padLeft(10);
        tabla.row().spaceTop(20);
        tabla.add(texto_tipo_boxeador).fillX();
        tabla.add(izquierdaBx).width(50).height(50).padRight(10);
        tabla.add(imagen_boxeador).width(100).height(70);
        tabla.add(derechaBx).width(50).height(50).padLeft(10);
        
        tabla.row().spaceTop(20);
        tabla.add(error).width(100).height(40).colspan(4).left();
        tabla.row().spaceTop(20);
        tabla.add(aceptar).width(100).height(40).align(Align.left).colspan(1);
        tabla.add(atras).width(100).height(40).align(Align.center).colspan(2);       
        tabla.add(borrar).width(100).height(40).align(Align.right).colspan(1);
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
    
    public boolean comprobarErrores(){
        if(nombre.getText().equals("") || nombre.getText().equals("")){
            error.setText("No puedes dejar campos vacios");
            return false;
        }
        
        
        return true;
    }
    
    public void registrarBoxeador(String nombre_antiguo,String nombre,String tipo_boxeador,char sexo,String peso, String pais,String categoria){
        String mensaje;
        if(boxeador.getNombre_boxeador().equals(nombre)){
            mensaje="9&"+nombre+"&"+tipo_boxeador+"&"+sexo+"&"+peso+"&"+pais+"&"+categoria;        
        }else{
            mensaje="9&"+nombre_antiguo+"&"+nombre+"&"+tipo_boxeador+"&"+sexo+"&"+peso+"&"+pais+"&"+categoria;        
        }
         
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
                int cod_error=Integer.parseInt(codigos[1]);
                if(cod_error == 10){
                    error.setText("No se pudo actualizar la información del boxeador");
                }else if(cod_error == 11){
                    error.setText("No se pudo borrar el boxeador");
                }else if(cod_error == 4){
                    error.setText("Ya existe este nombre de usuario, introduzca otro por favor.");
                }
                
                break;
                
            case BOXEADOR_MODIFICADO:
                boxeador.setNombre_boxeador(nombre.getText());
                boxeador.setPeso(Double.parseDouble(peso.getText()));
                boxeador.setCategoria(asignarCategoria(boxeador.getPeso(),spinner_peso.getSelected()));                
                boxeador.setTipo_boxeador(boxeadores.get(indice_boxeadores));
                if(boxeadores.get(indice_boxeadores).contains("Boxeadora")){
                    boxeador.setSexo('M');
                }else boxeador.setSexo('H');
                
                boxeador.setPais(paises.get(indice_pais));
                principal.setScreen(new PantallaSeleccionOCreacionPersonaje(principal));
        
                break;
                
            case BOXEADOR_BORRADO:
                Constantes.DATOS_USUARIO.deleteBoxeador(nombre_boxeador_antiguo);
                principal.setScreen(new PantallaSeleccionOCreacionPersonaje(principal));
                break;
        }
    }
    
    public void borrarBoxeador(String nombre_boxeador){
        String mensaje="10&"+nombre_boxeador;        
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

    @Override
    public void dispose() {
        Assets.instance.dispose();


    }
}
