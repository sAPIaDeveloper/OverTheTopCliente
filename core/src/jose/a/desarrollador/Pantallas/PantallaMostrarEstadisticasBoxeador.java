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
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.List;
import com.badlogic.gdx.scenes.scene2d.ui.ScrollPane;
import com.badlogic.gdx.scenes.scene2d.ui.SelectBox;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.utils.Align;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import jose.a.desarrollador.Principal;
import jose.a.desarrollador.Util.Assets;
import jose.a.desarrollador.Util.Codigos_Escritorio;
import jose.a.desarrollador.Util.Constantes;

/**
 *
 * @author josea
 */
public class PantallaMostrarEstadisticasBoxeador extends ScreenAdapter{
    Principal principal;
    String nombre_boxeador;
    String tipo_boxeador;
    String peso_dato;
    String categoria_dato;
    String pais;
    int combates_realizados_dato;
    int combates_ganados_dato;
    double porcentaje_combates_ganados_dato;
    int combates_ganados_ko_dato;
    int combates_ganados_ptos_dato;
    double porcentaje_ganados_ko_dato;
    double porcentaje_ganados_ptos_dato;
    int combates_perdidos_dato;
    String[] competiciones_ganadas;
    int puntos_acumulados_dato;
    int golpes_lanzados_dato;
    int golpes_conectados_dato;
    double porcentaje_golpes_no_conectados;
    
    
    Stage stage; 
    BitmapFont font;
    
    TextureAtlas atlasUi;
    TextureAtlas box;
    Table tabla;  
    private Skin ui;
    private Skin boxin;
    
    
    
    
    Label.LabelStyle label;
    Label titulo;
    Label nombre;
    Label peso;
    Label categoria;
    Label label_combates_realizados;
    Label combates_realizados;
    Label label_combates_ganados;
    Label combates_ganados;
    Label label_porcentaje_combates_ganados;
    Label porcentaje_combates_ganados;
    Label label_combates_ganados_ko;
    Label combates_ganados_ko;
    Label label_combates_ganados_ptos;
    Label combates_ganados_ptos;
    Label label_porcentaje_combates_ganados_ko;
    Label porcentaje_combates_ganados_ko;
    Label label_porcentaje_combates_ganados_ptos;
    Label porcentaje_combates_ganados_ptos;
    Label label_combates_perdidos;
    Label combates_perdidos;
    Label label_competiciones_ganadas;    
    Label label_ptos_acumulados;
    Label ptos_acumulados;
    Label label_golpes_lanzados;
    Label golpes_lanzados;
    Label label_golpes_conectados;
    Label golpes_conectados;
    Label label_golpes_no_conectados;
    Label golpes_no_conectados;
    
    TextButton bandera;
    TextButton imagen_boxeador;
    
    
    
    SelectBox.SelectBoxStyle boxStyle;
    SelectBox<String> competiciones;
    
    TextButton.TextButtonStyle textButtonStyleAtras;
    TextButton.TextButtonStyle textButtonStyleBoxe;
    TextButton.TextButtonStyle textButtonStyleBandera;
    TextButton atras;
    
    
    public PantallaMostrarEstadisticasBoxeador(Principal principal, String nombre_boxeador) {
        this.principal = principal;
        this.nombre_boxeador = nombre_boxeador;
        
        stage=new Stage();
        Gdx.input.setInputProcessor(stage);
        AssetManager am = new AssetManager();
        Assets.instance.init(am);
        font = Assets.instance.assetsUi.generator.generateFont(Assets.instance.assetsUi.parameter);
        atlasUi=new TextureAtlas(Constantes.TEXTURE_ATLAS_UI);        
        ui=new Skin(atlasUi);
        
        box=new TextureAtlas(Constantes.TEXTURE_ATLAS_ROSTRO_BOXEADORES);
        boxin=new Skin(box);                
        
        obtenerEstadisticasJugador();
        crearBotones();
        crearLabels();
        crearSelectBox();
        crearTabla();
        
        stage.addActor(tabla);
        
    }
    
    @Override
    public void render(float delta) {
        //p.apply();
        Gdx.gl.glClearColor(128/255f,203/255f,196/255f,0.1f);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
        stage.act(delta);
        stage.draw();
    }

    @Override
    public void resize(int width, int height) {
        stage.getViewport().update(width, height,true);
    }
    
    public void crearBotones(){
        textButtonStyleAtras=new TextButton.TextButtonStyle();
        textButtonStyleAtras.font=font;
        textButtonStyleAtras.fontColor=Color.WHITE;
        textButtonStyleAtras.up= ui.getDrawable(Constantes.BOTON);
        textButtonStyleAtras.down= ui.getDrawable(Constantes.BOTON_PULSADO);
        textButtonStyleAtras.pressedOffsetX=1;
        textButtonStyleAtras.pressedOffsetY=-1;
        
        atras=new TextButton("ATRAS",textButtonStyleAtras);
        atras.addListener(new ClickListener(){
            @Override
            public void clicked(InputEvent event, float x, float y) {                  
               principal.setScreen(new PantallaAccionesBoxeador(principal,nombre_boxeador));
            }

        }); 
        
        textButtonStyleBoxe=new TextButton.TextButtonStyle();
        textButtonStyleBoxe.font=font;
        textButtonStyleBoxe.fontColor=Color.WHITE;
        textButtonStyleBoxe.up= boxin.getDrawable(tipo_boxeador);        
        textButtonStyleBoxe.pressedOffsetX=1;
        textButtonStyleBoxe.pressedOffsetY=-1;
        
        imagen_boxeador=new TextButton("",textButtonStyleBoxe);
        
        textButtonStyleBandera=new TextButton.TextButtonStyle();
        textButtonStyleBandera.font=font;
        textButtonStyleBandera.fontColor=Color.WHITE;
        textButtonStyleBandera.up= ui.getDrawable(pais);        
        textButtonStyleBandera.pressedOffsetX=1;
        textButtonStyleBandera.pressedOffsetY=-1;
        
        bandera=new TextButton("",textButtonStyleBandera);
    }
    
    public void crearLabels(){
        label=new Label.LabelStyle();
        label.font=font;
        label.fontColor=Color.WHITE;
        
        titulo= new Label("ESTADISTICAS DEL PERSONAJE",label);
        titulo.setAlignment(Align.center);
        titulo.setFontScale(1);
        
        nombre = new Label(nombre_boxeador,label);
        
        peso = new Label(peso_dato,label);
       
        categoria = new Label(categoria_dato,label);
        
        
        label_combates_realizados = new Label("Combates realizados: ",label);
        label_combates_realizados.setFontScale(1);
        combates_realizados = new Label(combates_realizados_dato+"",label);
       
        
        label_combates_ganados = new Label("Combates ganados: ",label);
            
        combates_ganados = new Label(combates_ganados_dato+"",label);
      
        
        label_porcentaje_combates_ganados = new Label("% combates ganados: ",label);
     
        porcentaje_combates_ganados = new Label(porcentaje_combates_ganados_dato+"",label);
     
        
        label_combates_ganados_ko = new Label("Combates ganados por knockout: ",label);
 
        combates_ganados_ko = new Label(combates_ganados_ko_dato+"",label);
   
        
        label_combates_ganados_ptos = new Label("Combates  ganados por puntos: ",label);
       
        combates_ganados_ptos = new Label(combates_ganados_ptos_dato+"",label);
       
        
        label_porcentaje_combates_ganados_ko = new Label("% combates ganados por knockout: ",label);
     
        porcentaje_combates_ganados_ko = new Label(porcentaje_ganados_ko_dato+"",label);
        
        
        label_porcentaje_combates_ganados_ptos = new Label("% combates ganados por puntos: ",label);
        
        porcentaje_combates_ganados_ptos = new Label(porcentaje_ganados_ptos_dato+"",label);
        
        
        label_combates_perdidos = new Label("Combates perdidos: ",label);
        
        combates_perdidos = new Label(combates_perdidos_dato+"",label);
      
        
        label_competiciones_ganadas = new Label("Competiciones ganadas: ",label);
              
        
        
        label_ptos_acumulados = new Label("Puntos totales acumulados: ",label);               
        ptos_acumulados = new Label(puntos_acumulados_dato+"",label);    
        
        label_golpes_lanzados = new Label("Golpes lanzados en total: ",label);
        golpes_lanzados = new Label(golpes_lanzados_dato+"",label);
        
        label_golpes_conectados = new Label("Golpes conectados en total: ",label);
        golpes_conectados = new Label(golpes_conectados_dato+"",label);
        
        label_golpes_no_conectados = new Label("% de golpes no conectados: ",label);
        golpes_no_conectados = new Label(porcentaje_golpes_no_conectados+"",label);
        
    }
    
    public void crearTabla(){
        tabla= new Table();
       // tabla.setDebug(true);
        tabla.setSize(500, 500);           
        tabla.setPosition((Gdx.graphics.getWidth()/2)-250,(Gdx.graphics.getHeight()/2)-250);
        
        tabla.add(titulo).width(200).align(Align.center).colspan(2);
        tabla.row().spaceTop(20);
        // Poner imagen boxeador, nombre, peso, categoria y nacionalidad
        Table intermedia= new Table();
        intermedia.add(imagen_boxeador).width(70).height(70).align(Align.left);
        
        
        Table t= new Table();
        //t.setDebug(true);
        t.add(nombre);
        t.row().spaceTop(5);
        t.add(peso);
        t.row().spaceTop(5);
        t.add(categoria);
        
        intermedia.add(t).spaceLeft(10).spaceRight(10);
        intermedia.add(bandera).width(100).height(70).align(Align.right);
        
        tabla.add(intermedia).colspan(2);
        tabla.row().spaceTop(20);
        
        tabla.add(label_combates_realizados);
        tabla.add(combates_realizados);
        
        
        tabla.row().spaceTop(5);
        
        
        tabla.add(label_combates_ganados);
        tabla.add(combates_ganados);
        
        tabla.row().spaceTop(5);
        
        
        tabla.add(label_porcentaje_combates_ganados);
        tabla.add(porcentaje_combates_ganados);
        
        tabla.row().spaceTop(5);
        
        
        tabla.add(label_combates_ganados_ko);
        tabla.add(combates_ganados_ko);
        
        tabla.row().spaceTop(5);
        
        
        tabla.add(label_combates_ganados_ptos);
        tabla.add(combates_ganados_ptos);
        
        tabla.row().spaceTop(5);
        
        
        tabla.add(label_porcentaje_combates_ganados_ko);
        tabla.add(porcentaje_combates_ganados_ko);
        
        tabla.row().spaceTop(5);
        
        
        tabla.add(label_porcentaje_combates_ganados_ptos);
        tabla.add(porcentaje_combates_ganados_ptos);        
        tabla.row().spaceTop(5);
        
        
        tabla.add(label_combates_perdidos);
        tabla.add(combates_perdidos);        
        tabla.row().spaceTop(5);
        
        
        tabla.add(label_competiciones_ganadas);
        
        tabla.add(competiciones).height(30).width(100);
        tabla.row().spaceTop(5);
        
        
        tabla.add(label_golpes_lanzados);
        tabla.add(golpes_lanzados);        
        tabla.row().spaceTop(5);
                
        
        tabla.add(label_golpes_conectados);
        tabla.add(golpes_conectados);        
        tabla.row().spaceTop(5);
                
        
        tabla.add(label_golpes_no_conectados);
        tabla.add(golpes_no_conectados);        
        tabla.row().spaceTop(5);
                
        
        tabla.add(label_ptos_acumulados);
        tabla.add(ptos_acumulados);        
        tabla.row().spaceTop(5);
        
        tabla.add(atras).height(30).width(100);
    }
    
    public void crearSelectBox(){
        
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
        
        competiciones = new SelectBox<String>(boxStyle);
        competiciones.setAlignment(Align.center);
        competiciones.getList().setAlignment(Align.center);
        
        competiciones.setItems(competiciones_ganadas);
    }
    
    public void obtenerEstadisticasJugador(){
         String mensaje="13&"+nombre_boxeador;                
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
    
    public void tratarMensaje(String mensaje){
        String codigos[]=mensaje.split("&");
        int cod=Integer.parseInt(codigos[0]);
        switch(Codigos_Escritorio.codigo_escritorio(cod)){ 
            case ESTADISTICAS_BOXEADOR:
                tipo_boxeador = codigos[1];
                peso_dato = codigos[2];
                categoria_dato = codigos[3];
                pais = codigos[4];
                combates_realizados_dato = Integer.parseInt(codigos[5]);
                combates_ganados_dato = Integer.parseInt(codigos[6]);
                
                if(combates_realizados_dato == 0){
                    porcentaje_combates_ganados_dato = 0;
                }else porcentaje_combates_ganados_dato = (combates_ganados_dato*100)/combates_realizados_dato;
                
                combates_ganados_ko_dato = Integer.parseInt(codigos[7]);
                combates_ganados_ptos_dato = combates_ganados_dato - combates_ganados_ko_dato;
                
                if(combates_ganados_dato == 0){
                    combates_ganados_dato = 0;
                }else porcentaje_ganados_ko_dato = (combates_ganados_ko_dato*100)/combates_ganados_dato;
                
                
                
                if(combates_ganados_dato == 0){
                    combates_ganados_dato = 0;
                }else porcentaje_ganados_ptos_dato = (combates_ganados_ptos_dato*100)/combates_ganados_dato;
                
               
                combates_perdidos_dato = combates_realizados_dato - combates_ganados_dato;
                competiciones_ganadas = codigos[8].split("@");
                puntos_acumulados_dato = Integer.parseInt(codigos[9]);
                
                golpes_lanzados_dato = Integer.parseInt(codigos[10]);
                golpes_conectados_dato = Integer.parseInt(codigos[11]);
                System.out.println("Golpes lanzados en total: "+golpes_lanzados_dato);
                if(golpes_lanzados_dato == 0){
                    porcentaje_golpes_no_conectados = 0;
                }else porcentaje_golpes_no_conectados = 100 - ((golpes_conectados_dato*100)/golpes_lanzados_dato);
                
                
                break;
        }
    }

    @Override
    public void dispose() {
        Assets.instance.dispose();

    }
}
