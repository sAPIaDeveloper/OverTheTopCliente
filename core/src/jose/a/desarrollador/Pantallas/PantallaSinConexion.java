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
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable;
import com.badlogic.gdx.utils.Align;
import com.badlogic.gdx.utils.TimeUtils;
import com.badlogic.gdx.utils.viewport.ExtendViewport;
import java.util.concurrent.TimeUnit;
import jose.a.desarrollador.Principal;
import jose.a.desarrollador.Util.Assets;
import jose.a.desarrollador.Util.Constantes;
import jose.a.desarrollador.Util.Preferencias;

/**
 *
 * @author josea
 */
public class PantallaSinConexion extends ScreenAdapter{
    Preferencias pref;
    Principal principal;
    private ExtendViewport extendViewport;
    Stage stage; 
    BitmapFont font;
    Table tabla;      
    Label.LabelStyle label;
    Label mensajes_error;
    Image image;
    long empiezo;
    private Skin ui;
    public PantallaSinConexion(Principal principal) {
        this.principal = principal;
        
        init();
    }
    
    
    public void init(){
        AssetManager am = new AssetManager();
        Assets.instance.init(am);
        font = Assets.instance.assetsUi.generator.generateFont(Assets.instance.assetsUi.parameter);
        stage=new Stage(); 
        pref = new Preferencias();
        extendViewport=new ExtendViewport(Constantes.WORLD_SIZE,Constantes.WORLD_SIZE);
        stage.setViewport(extendViewport);
        Gdx.input.setInputProcessor(stage); 
        empiezo = TimeUtils.millis();
        TextureAtlas atlas=new TextureAtlas(Constantes.TEXTURE_ATLAS_UI);
        ui=new Skin(atlas);
        
        crearLabel();
        crearImagen();
        crearTabla();
        
    }
    
    public void crearLabel(){
        label=new Label.LabelStyle();
        label.font=font;       
        label.fontColor=Color.WHITE;
        mensajes_error= new Label("Ha perdido la conexion con el servidor.\n Para proteger su cuenta le cerraremos sesion automaticamente",label);
      //  mensajes_error.setWrap(true);
    }
    
    public void crearImagen(){
        image = new Image();
        image.setDrawable(ui.getDrawable(Constantes.SIN_CONEXION));
    }
    
    public void crearTabla(){     
        tabla= new Table();
        //tabla.setDebug(true);
        tabla.setFillParent(true);        
        tabla.add(image).width(300).height(300).align(Align.center);
        tabla.row().spaceTop(100);
        tabla.add(mensajes_error).height(100).align(Align.left);
        stage.addActor(tabla);
    }
    
    @Override
    public void render(float delta) {
        long momentoExacto= TimeUtils.millis();
        long tiempoTranscurrido = (int)(TimeUnit.MILLISECONDS.toSeconds(momentoExacto) - TimeUnit.MILLISECONDS.toSeconds(empiezo));   
        if(tiempoTranscurrido > 5){
            principal.setScreen(new PantallaLoguin(principal));
            
        }
        
        Gdx.gl.glClearColor(128/255f,203/255f,196/255f,0.1f);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
        stage.act(delta);
        stage.draw();
    }

    @Override
    public void resize(int width, int height) {
        stage.getViewport().update(width, height,true); //To change body of generated methods, choose Tools | Templates.
    }
    
    @Override
    public void dispose() {
        Assets.instance.dispose();
    }
}
