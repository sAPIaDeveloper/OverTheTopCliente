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
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.utils.Align;
import jose.a.desarrollador.Entidades.PlayerCliente;
import jose.a.desarrollador.Principal;
import jose.a.desarrollador.Util.Assets;
import jose.a.desarrollador.Util.Constantes;

/**
 *
 * @author josea
 */
public class PantallaResumenCombate extends ScreenAdapter{
    Principal principal;
    PlayerCliente player1;
    PlayerCliente player2;
    String nombre_ganador;
    Stage stage; 
    BitmapFont font;
    
    TextureAtlas atlasUi;
    TextureAtlas box;
    Table tabla;  
    private Skin ui;
    private Skin boxin;
    
    Label.LabelStyle label;
    Label nombre_p1;
    Label nombre_p2;
    Label label_golpes_lanzadosP1;
    Label label_golpes_lanzadosP2;
    Label golpes_lanzados_p1;
    Label golpes_lanzados_p2;
    Label label_golpes_conectadosP1;
    Label label_golpes_conectadosP2;
    Label golpes_conectados_p1;
    Label golpes_conectados_p2;
    Label label_porcentaje_golpes_conectadosP1;
    Label label_porcentaje_golpes_conectadosP2;
    Label porcentaje_golpes_conectados_p1;
    Label porcentaje_golpes_conectados_p2;
    Label label_ptos_acumuladosP1;
    Label label_ptos_acumuladosP2;
    Label ptos_acumulados_p1;
    Label ptos_acumulados_p2;
    Label resultadoP1;
    Label resultadoP2;
    
    TextButton.TextButtonStyle textButtonStyleAceptar;
    TextButton aceptar;
    
    TextButton.TextButtonStyle textButtonStyleP1;
    TextButton.TextButtonStyle textButtonStyleP2;
    TextButton imagen_boxeador_p1;
    TextButton imagen_boxeador_p2;        
    
    public PantallaResumenCombate(Principal principal,PlayerCliente player1, PlayerCliente player2,String nombre_ganador) {
        this.principal = principal;
        this.player1 = player1;
        this.player2 = player2;
        this.nombre_ganador = nombre_ganador;
        init();   
        
       
    }
    
    public void init(){
        
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
        crearBotones();        
        
        crearTabla();
        stage.addActor(tabla);
    }
    
    public void crearLabels(){
        label=new Label.LabelStyle();
        label.font=font;
        label.fontColor=Color.WHITE;
        
        nombre_p1 = new Label(player1.getNombre_boxeador().toUpperCase(),label);
        nombre_p2 = new Label(player2.getNombre_boxeador().toUpperCase(),label);
        
        label_golpes_lanzadosP1 = new Label("Golpes lanzados: ",label);
        label_golpes_lanzadosP2 = new Label("Golpes lanzados: ",label);
        
        golpes_lanzados_p1 = new Label(player1.getGolpes_lanzados()+"",label);
        golpes_lanzados_p2 = new Label(player2.getGolpes_lanzados()+"",label);
        
        label_golpes_conectadosP1 = new Label("Golpes conectados: ",label);
        label_golpes_conectadosP2 = new Label("Golpes conectados: ",label);
        
        golpes_conectados_p1 = new Label(player1.getGolpes_conectados()+"",label);
        golpes_conectados_p2 = new Label(player2.getGolpes_conectados()+"",label);
        
        label_porcentaje_golpes_conectadosP1 = new Label("% Golpes conectados: ",label);
        label_porcentaje_golpes_conectadosP2 = new Label("% Golpes conectados: ",label);
        
        double porcentaje_conectado_p1;
        if(player1.getGolpes_lanzados() == 0){
            porcentaje_conectado_p1 = 0;
        }else{
            porcentaje_conectado_p1 = (player1.getGolpes_conectados() * 100) / player1.getGolpes_lanzados();
        }
        
        
        porcentaje_golpes_conectados_p1 = new Label(porcentaje_conectado_p1+"",label);
        
        double porcentaje_conectado_p2;
        if(player2.getGolpes_lanzados() == 0){
            porcentaje_conectado_p2= 0;
        }else{
            porcentaje_conectado_p2= (player2.getGolpes_conectados() * 100) / player2.getGolpes_lanzados();
        }
        
        
        porcentaje_golpes_conectados_p2 = new Label(porcentaje_conectado_p2+"",label);
        
        label_ptos_acumuladosP1 = new Label("PUNTOS OBTENIDOS",label);
        label_ptos_acumuladosP2 = new Label("PUNTOS OBTENIDOS",label);
        
        int puntosP1;
        int puntosP2;
        
        if(player1.getNombre_boxeador().equals(nombre_ganador)){
            puntosP1 = player1.getGolpes_conectados()+15;
            puntosP2 = player2.getGolpes_conectados()-5;
            if(puntosP2 < 0) puntosP2 = 0;
            
            resultadoP1 = new Label("VICTORIA",label);
            resultadoP2 = new Label("DERROTA",label);
        }else{
            puntosP1 = player1.getGolpes_conectados()-5;
            puntosP2 = player2.getGolpes_conectados()+15;
            if(puntosP1< 0) puntosP1 = 0;
            
            resultadoP2 = new Label("VICTORIA",label);
            resultadoP1 = new Label("DERROTA",label);
        }
        
        ptos_acumulados_p1 = new Label(puntosP1+"",label);
        ptos_acumulados_p2 = new Label(puntosP2+"",label);
        
        
    }
    
    public void crearBotones(){
        textButtonStyleAceptar=new TextButton.TextButtonStyle();
        textButtonStyleAceptar.font=font;
        textButtonStyleAceptar.fontColor=Color.WHITE;
        textButtonStyleAceptar.up= ui.getDrawable(Constantes.BOTON);
        textButtonStyleAceptar.down= ui.getDrawable(Constantes.BOTON_PULSADO);
        textButtonStyleAceptar.pressedOffsetX=1;
        textButtonStyleAceptar.pressedOffsetY=-1;
        
        aceptar=new TextButton("ACEPTAR",textButtonStyleAceptar);
        aceptar.addListener(new ClickListener(){
            @Override
            public void clicked(InputEvent event, float x, float y) {                  
               principal.setScreen(new PantallaAccionesBoxeador(principal,player1.getNombre_boxeador()));
            }

        });
        
        textButtonStyleP1=new TextButton.TextButtonStyle();
        textButtonStyleP1.font=font;
        textButtonStyleP1.fontColor=Color.WHITE;
        textButtonStyleP1.up= boxin.getDrawable(player1.getTipo_boxeador());        
        textButtonStyleP1.pressedOffsetX=1;
        textButtonStyleP1.pressedOffsetY=-1;
        
        imagen_boxeador_p1 = new TextButton("",textButtonStyleP1);
        
        textButtonStyleP2=new TextButton.TextButtonStyle();
        textButtonStyleP2.font=font;
        textButtonStyleP2.fontColor=Color.WHITE;
        textButtonStyleP2.up= boxin.getDrawable(player2.getTipo_boxeador());        
        textButtonStyleP2.pressedOffsetX=1;
        textButtonStyleP2.pressedOffsetY=-1;
        
        imagen_boxeador_p2 = new TextButton("",textButtonStyleP2);
    }
    
    public void crearTabla(){
     
        tabla= new Table();
        tabla.setDebug(true); 
        tabla.setFillParent(true);
        
        tabla.add(nombre_p1).colspan(2).align(Align.center);
        tabla.add(new Image( ui.getDrawable(Constantes.TABLA))).width(5).expandY().fillY();
        tabla.add(nombre_p2).colspan(2).align(Align.center);
        
        tabla.row().spaceTop(20);
        
        tabla.add(imagen_boxeador_p1).colspan(2).width(150).height(100).align(Align.center);
        tabla.add(new Image( ui.getDrawable(Constantes.TABLA))).width(5).expandY().fillY();
        tabla.add(imagen_boxeador_p2).colspan(2).width(150).height(100).align(Align.center);
        
        tabla.row().spaceTop(20);
        
        tabla.add(label_golpes_lanzadosP1).align(Align.center);
        tabla.add(golpes_lanzados_p1).align(Align.center);
        tabla.add(new Image( ui.getDrawable(Constantes.TABLA))).width(5).expandY().fillY().align(Align.center);
        tabla.add(label_golpes_lanzadosP2).align(Align.center);
        tabla.add(golpes_lanzados_p2).align(Align.center);
        
        tabla.row().spaceTop(20);
        
        tabla.add(label_golpes_conectadosP1).align(Align.center);
        tabla.add(golpes_conectados_p1).align(Align.center);
        tabla.add(new Image( ui.getDrawable(Constantes.TABLA))).width(5).expandY().fillY().align(Align.center);
        tabla.add(label_golpes_conectadosP2).align(Align.center);
        tabla.add(golpes_conectados_p2).align(Align.center);
        
        tabla.row().spaceTop(20);
        
        tabla.add(label_porcentaje_golpes_conectadosP1).align(Align.center);
        tabla.add(porcentaje_golpes_conectados_p1).align(Align.center);
        tabla.add(new Image( ui.getDrawable(Constantes.TABLA))).width(5).expandY().fillY().align(Align.center);
        tabla.add(label_porcentaje_golpes_conectadosP2).align(Align.center);
        tabla.add(porcentaje_golpes_conectados_p2).align(Align.center);
        
        tabla.row().spaceTop(20);
        
        tabla.add(label_ptos_acumuladosP1).colspan(2).align(Align.center);
        tabla.add(new Image( ui.getDrawable(Constantes.TABLA))).width(5).expandY().fillY().align(Align.center);
        tabla.add(label_ptos_acumuladosP2).colspan(2).align(Align.center);
        
        tabla.row().spaceTop(20);
        
        tabla.add(ptos_acumulados_p1).colspan(2).align(Align.center);
        tabla.add(new Image( ui.getDrawable(Constantes.TABLA))).width(5).expandY().fillY().align(Align.center);
        tabla.add(ptos_acumulados_p2).colspan(2).align(Align.center);
        
        tabla.row().spaceTop(20);
        
        tabla.add(resultadoP1).colspan(2).align(Align.center);
        tabla.add(new Image( ui.getDrawable(Constantes.TABLA))).width(5).expandY().fillY().align(Align.center);
        tabla.add(resultadoP2).colspan(2).align(Align.center);
        
        tabla.row().spaceTop(20);
        
        tabla.add(aceptar).width(120).height(50).colspan(5).align(Align.right);
        
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
    
    @Override
    public void dispose() {
        Assets.instance.dispose();

    }
}
