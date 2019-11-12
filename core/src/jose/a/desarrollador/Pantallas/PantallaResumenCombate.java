/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package jose.a.desarrollador.Pantallas;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.utils.Align;
import jose.a.desarrollador.Entidades.PlayerCliente;
import jose.a.desarrollador.Util.Assets;
import jose.a.desarrollador.Util.Constantes;

/**
 *
 * @author josea
 */
public class PantallaResumenCombate {
    PlayerCliente player1;
    PlayerCliente player2;

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
    Label label_golpes_lanzados;
    Label golpes_lanzados_p1;
    Label golpes_lanzados_p2;
    Label label_golpes_conectados;
    Label golpes_conectados_p1;
    Label golpes_conectados_p2;
    Label label_porcentaje_golpes_conectados;
    Label porcentaje_golpes_conectados_p1;
    Label porcentaje_golpes_conectados_p2;
    Label label_ptos_acumulados;
    Label ptos_acumulados_p1;
    Label ptos_acumulados_p2;
    Label victoria;
    Label derrota;
    
    TextButton.TextButtonStyle textButtonStyleAceptar;
    TextButton aceptar;
    
    TextButton.TextButtonStyle textButtonStyleP1;
    TextButton.TextButtonStyle textButtonStyleP2;
    TextButton imagen_boxeador_p1;
    TextButton imagen_boxeador_p2;
    
    
    public PantallaResumenCombate(PlayerCliente player1, PlayerCliente player2) {
        this.player1 = player1;
        this.player2 = player2;
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
    }
    
    public void crearLabels(){
        label=new Label.LabelStyle();
        label.font=font;
        label.fontColor=Color.WHITE;
        
        nombre_p1 = new Label(player1.getNombre_boxeador(),label);
        nombre_p2 = new Label(player2.getNombre_boxeador(),label);
        
        label_golpes_lanzados = new Label("Golpes lanzados: ",label);
        
        golpes_lanzados_p1 = new Label(player1.getGolpes_lanzados()+"",label);
        golpes_lanzados_p2 = new Label(player2.getGolpes_lanzados()+"",label);
        
        label_golpes_conectados = new Label("Golpes conectados: ",label);
        
        golpes_conectados_p1 = new Label(player1.getGolpes_conectados()+"",label);
        golpes_conectados_p2 = new Label(player2.getGolpes_conectados()+"",label);
        
        label_porcentaje_golpes_conectados = new Label("% Golpes conectados: ",label);
        
        double porcentaje_conectado_p1 = (player1.getGolpes_conectados() * 100) / player1.getGolpes_lanzados();
        porcentaje_golpes_conectados_p1 = new Label(porcentaje_conectado_p1+"",label);
        
        double porcentaje_conectado_p2 = (player2.getGolpes_conectados() * 100) / player2.getGolpes_lanzados();
        porcentaje_golpes_conectados_p2 = new Label(porcentaje_conectado_p1+"",label);
        
        label_ptos_acumulados = new Label("PUNTOS",label);
        
        
        ptos_acumulados_p1 = new Label("",label);
        ptos_acumulados_p2 = new Label("",label);
    }
    
    
}
