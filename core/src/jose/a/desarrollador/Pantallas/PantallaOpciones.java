/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package jose.a.desarrollador.Pantallas;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Graphics.DisplayMode;
import com.badlogic.gdx.ScreenAdapter;
import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.Color;
import static com.badlogic.gdx.graphics.Color.BLACK;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Pixmap;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.ui.TextField;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.utils.Align;
import com.badlogic.gdx.utils.viewport.ExtendViewport;
import jose.a.desarrollador.Principal;
import jose.a.desarrollador.Util.Assets;
import jose.a.desarrollador.Util.Constantes;
import jose.a.desarrollador.Util.Preferencias;

/**
 *
 * @author josea
 */
public class PantallaOpciones extends ScreenAdapter{
    Principal principal;
    Stage stage; 
    Table tabla;
    BitmapFont font;
    private Skin ui;
    Sound click;
    Music sonido;
    int volumen;
    TextureAtlas atlas;
    private ExtendViewport extendViewport;
    Preferencias pref;
    Label.LabelStyle label;
    Label.LabelStyle f;
    Label titulo;
    Label sfx;
    Label musica;
    Label pantalla_completa;
    Label direccion_ip_servidor;
    
    TextButton activar_desactivar_sfx;
    TextButton activar_desactivar_musica;    
    TextButton activar_desactivar_pantallaCompleta;    
    TextButton boton_aceptar;
    TextButton.TextButtonStyle textButtonStyle;
    TextButton.TextButtonStyle textButtonStyleCheckboxSfx;
    TextButton.TextButtonStyle textButtonStyleCheckboxMusic;
    TextButton.TextButtonStyle textButtonStyleCheckboxPantallaCompleta;
    
    TextField.TextFieldStyle textFieldStyle;
    TextField direccion_ip;
    Pixmap cursorColor;
    public PantallaOpciones(Principal principal) {
        this.principal = principal;
        this.sonido = PantallaInicio.inicio;
        init();
    }
    
    public void init(){
        AssetManager am = new AssetManager();
        Assets.instance.init(am);
        
        font = Assets.instance.assetsUi.generator.generateFont(Assets.instance.assetsUi.parameter);
        click = Assets.instance.assetsSonido.click_boton;
        
        stage = new Stage();
        tabla = new Table();
        pref = new Preferencias();
        
        Gdx.input.setInputProcessor(stage);//Para que detecte los eventos de raton
        extendViewport=new ExtendViewport(Constantes.WORLD_SIZE,Constantes.WORLD_SIZE);
        stage.setViewport(extendViewport);
        atlas=new TextureAtlas(Constantes.TEXTURE_ATLAS_UI);
        ui=new Skin(atlas);
        
        
        crearLabels();
        crearBotones();
        crearTextField();
        crearTabla();
        
        stage.addActor(tabla);
    }
    
    public void crearLabels(){
        label=new Label.LabelStyle();
        label.font=font;
        
        titulo = new Label("OPCIONES",label);
        sfx = new Label("SFX",label);
        musica = new Label("MUSICA",label);
        direccion_ip_servidor = new Label("DIRECCION IP DEL SERVIDOR",label);
        pantalla_completa = new Label("PANTALLA COMPLETA",label);
           
        f=new Label.LabelStyle();
        f.font=font;
        Label oneCharSizeCalibrationThrowAway = new Label("|",f);
        
        cursorColor = new Pixmap((int) oneCharSizeCalibrationThrowAway.getWidth(),
            (int) oneCharSizeCalibrationThrowAway.getHeight(),Pixmap.Format.RGB888);
        cursorColor.setColor(Color.BLACK);
        cursorColor.fill();
    }
    
    public void crearBotones(){
        textButtonStyle = new TextButton.TextButtonStyle();
        textButtonStyle.up= ui.getDrawable(Constantes.BOTON);
        textButtonStyle.down= ui.getDrawable(Constantes.BOTON_PULSADO);
        textButtonStyle.fontColor=Color.WHITE;
        textButtonStyle.font = font;
        
        textButtonStyle.pressedOffsetX=1;
        textButtonStyle.pressedOffsetY=-1;
        
        boton_aceptar = new TextButton("ACEPTAR",textButtonStyle);
        boton_aceptar.addListener(new ClickListener(){
            @Override
            public void clicked(InputEvent event, float x, float y) {  
                volumen = pref.getVolumen_sfx();
              if(pref.getVolumen_sfx() == 100) {
                  click.play(volumen);
              }
              pref.setDireccion_ip(direccion_ip.getText().toString());
              principal.setScreen(new PantallaLoguin(principal));
            }

        });
        
        textButtonStyleCheckboxSfx = new TextButton.TextButtonStyle();
        if(pref.getVolumen_sfx() == 100){
            textButtonStyleCheckboxSfx.up = ui.getDrawable(Constantes.CHECKBOX_ACTIVADO);
        }else{
            textButtonStyleCheckboxSfx.up = ui.getDrawable(Constantes.CHECKBOX_DESACTIVADO);
        }                
        textButtonStyleCheckboxSfx.font = font;        
        textButtonStyleCheckboxSfx.pressedOffsetX=1;
        textButtonStyleCheckboxSfx.pressedOffsetY=-1;
        
        activar_desactivar_sfx = new TextButton("",textButtonStyleCheckboxSfx);
        activar_desactivar_sfx.addListener(new ClickListener(){
            @Override
            public void clicked(InputEvent event, float x, float y) {                 
              if(pref.getVolumen_sfx() == 100) {
                  pref.setVolumen_sfx(false);
                  textButtonStyleCheckboxSfx.up = ui.getDrawable(Constantes.CHECKBOX_DESACTIVADO);
              }else{
                  pref.setVolumen_sfx(true);
                  textButtonStyleCheckboxSfx.up = ui.getDrawable(Constantes.CHECKBOX_ACTIVADO);
              }
            }

        });
        
        textButtonStyleCheckboxMusic = new TextButton.TextButtonStyle();
        if(pref.getVolumen_musica() == 100){
            textButtonStyleCheckboxMusic.up= ui.getDrawable(Constantes.CHECKBOX_ACTIVADO);
        }else{
            textButtonStyleCheckboxMusic.up= ui.getDrawable(Constantes.CHECKBOX_DESACTIVADO);
        }               
        textButtonStyleCheckboxMusic.font = font;
        
        textButtonStyleCheckboxMusic.pressedOffsetX=1;
        textButtonStyleCheckboxMusic.pressedOffsetY=-1;
        
        activar_desactivar_musica = new TextButton("",textButtonStyleCheckboxMusic);
        activar_desactivar_musica.addListener(new ClickListener(){
            @Override
            public void clicked(InputEvent event, float x, float y) {                 
              if(pref.getVolumen_musica() == 100) {
                  pref.setVolumen_musica(false);
                  textButtonStyleCheckboxMusic.up = ui.getDrawable(Constantes.CHECKBOX_DESACTIVADO);
              }else{
                  pref.setVolumen_musica(true);
                  textButtonStyleCheckboxMusic.up = ui.getDrawable(Constantes.CHECKBOX_ACTIVADO);
              }
              
            sonido.setVolume(pref.getVolumen_musica());
            }
            
        });
        
        textButtonStyleCheckboxPantallaCompleta = new TextButton.TextButtonStyle();
        if(pref.isPantalla_completa()){
            textButtonStyleCheckboxPantallaCompleta.up= ui.getDrawable(Constantes.CHECKBOX_ACTIVADO);
        }else{
            textButtonStyleCheckboxPantallaCompleta.up= ui.getDrawable(Constantes.CHECKBOX_DESACTIVADO);
        }               
        textButtonStyleCheckboxPantallaCompleta.font = font;
        
        textButtonStyleCheckboxPantallaCompleta.pressedOffsetX=1;
        textButtonStyleCheckboxPantallaCompleta.pressedOffsetY=-1;
        
        activar_desactivar_pantallaCompleta = new TextButton("",textButtonStyleCheckboxPantallaCompleta);
        activar_desactivar_pantallaCompleta.addListener(new ClickListener(){
            @Override
            public void clicked(InputEvent event, float x, float y) {                 
              if(pref.isPantalla_completa()) {
                pref.setPantalla_completa(false);
                Gdx.graphics.setWindowedMode(800, 600);
                textButtonStyleCheckboxPantallaCompleta.up = ui.getDrawable(Constantes.CHECKBOX_DESACTIVADO);
              }else{
                  pref.setPantalla_completa(true);
                  Gdx.graphics.setFullscreenMode(Gdx.graphics.getDisplayMode());
                  textButtonStyleCheckboxPantallaCompleta.up = ui.getDrawable(Constantes.CHECKBOX_ACTIVADO);
              }
              
            
            }
            
        });
    }
    
    public void crearTextField(){
        textFieldStyle= new TextField.TextFieldStyle();
        textFieldStyle.background=ui.getDrawable(Constantes.TEXTFIELD);
        textFieldStyle.font=font;
        textFieldStyle.fontColor=new Color(BLACK);
        textFieldStyle.cursor= new Image(new Texture(cursorColor)).getDrawable();
        direccion_ip= new TextField(pref.getDireccion_ip(),textFieldStyle);
        direccion_ip.setAlignment(Align.center);
    }
    
    public void crearTabla(){
        tabla= new Table();       
        tabla.setFillParent(true);
        tabla.add(titulo).colspan(2);
        tabla.row().spaceTop(20);
        tabla.add(sfx);
        tabla.add(activar_desactivar_sfx).width(50).height(50);
        tabla.row().spaceTop(20);
        tabla.add(musica);
        tabla.add(activar_desactivar_musica).width(50).height(50);
        
        tabla.row().spaceTop(20);
        tabla.add(pantalla_completa);
        tabla.add(activar_desactivar_pantallaCompleta).width(50).height(50);
        
        tabla.row().spaceTop(20);
        tabla.add(direccion_ip_servidor).colspan(2);
        tabla.row().spaceTop(20);
        tabla.add(direccion_ip).colspan(2).width(200).height(50);
        tabla.row().spaceTop(20);
        tabla.add(boton_aceptar).colspan(2).width(120).height(50);
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
}
