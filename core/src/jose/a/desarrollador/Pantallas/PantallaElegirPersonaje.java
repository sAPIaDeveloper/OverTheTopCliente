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
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.utils.Align;
import java.util.ArrayList;
import jose.a.desarrollador.Principal;
import jose.a.desarrollador.Util.Assets;
import jose.a.desarrollador.Util.Constantes;

/**
 *
 * @author josea
 */
public class PantallaElegirPersonaje extends ScreenAdapter {

    Principal principal;
    Stage stage; 
    BitmapFont font;
    ArrayList<TextButton> botones;
    Table tabla;  
    private Skin boxeadores;
    private Skin boton;
    String tipo_boxeador;
    TextButton atras;
    TextButton siguiente;
    Sound sonidoVoz;
    Sound click;
    
    public PantallaElegirPersonaje(Principal principal) {
        this.principal=principal;
        
        init();
    }
    
    public void init(){
        tipo_boxeador="";
        botones=new ArrayList<TextButton>();
        stage=new Stage();              
        //Para que detecte los eventos de raton
        Gdx.input.setInputProcessor(stage);
        //Crear la fuente
        AssetManager am = new AssetManager();
        Assets.instance.init(am);
        click = (Sound) Assets.instance.assetsSonido.click_boton;
        font = Assets.instance.assetsUi.generator.generateFont(Assets.instance.assetsUi.parameter);
        
        TextureAtlas atlasRetrato=new TextureAtlas(Constantes.TEXTURE_ATLAS_ROSTRO_BOXEADORES);
        TextureAtlas atlasUi=new TextureAtlas(Constantes.TEXTURE_ATLAS_UI);
        boxeadores=new Skin(atlasRetrato);
        boton=new Skin(atlasUi);
        
        hacerBotonesBoxeadores();
        crearBotonesSiguienteAtras();
        crearTabla();
         
        stage.addActor(tabla);
    }
    
    public void crearBotonesSiguienteAtras(){
        TextButton.TextButtonStyle estilo=new TextButton.TextButtonStyle();
        estilo.up=boton.getDrawable(Constantes.BOTON);
        estilo.fontColor=Color.WHITE;
        estilo.font= font;
        atras=new TextButton("ATRAS",estilo);
        siguiente=new TextButton("SIGUIENTE",estilo);
        estilo.pressedOffsetX=1;
        estilo.pressedOffsetY=-1;  
        
        atras.addListener(new ClickListener(){
            @Override
            public void clicked(InputEvent event, float x, float y) {  
                click.play(100);
                cambiarPantalla("atras");
            }

        });   
        
        siguiente.addListener(new ClickListener(){
            @Override
            public void clicked(InputEvent event, float x, float y) {  
                click.play(100);
                if(!tipo_boxeador.isEmpty()){
                    if(sonidoVoz == null){
                        cambiarPantalla("siguiente");
                    }else{
                        sonidoVoz.stop();
                        cambiarPantalla("siguiente");
                    }
                    
                }
            }

        });  
    }
    
    public void crearTabla(){
        tabla= new Table();
        
        tabla.setFillParent(true);
        for (int i = 0; i < botones.size(); i++) {
            final TextButton boton=botones.get(i);
            boton.getLabel().setVisible(false);
            boton.addListener(new ClickListener(){
                @Override
                public void clicked(InputEvent event, float x, float y) {   
                    for (int i = 0; i < botones.size(); i++) {
                        TextButton boton_interno=botones.get(i);
                        if(boton==boton_interno){
                            tipo_boxeador=boton_interno.getLabel().getText().toString();
                            boton.getStyle().up=boxeadores.getDrawable(tipo_boxeador+"_seleccionado");
                            startVoice();
                        }else{
                            boton_interno.getStyle().up=boxeadores.getDrawable(boton_interno.getLabel().getText().toString());
                        }
                    }
                    
                    
                }

            });            
            tabla.add(boton).width(150).height(150).space(10);
            if(i==(botones.size()/2)-1){
                tabla.row();
            }
        }
        
        tabla.row();
        tabla.add(atras).width(110).height(50).space(20).align(Align.left);
        tabla.add(siguiente).colspan(2).width(130).height(50).space(20).align(Align.right).fillX();
    }
    
    public void hacerBotonesBoxeadores(){   
        ArrayList<String> tipos=new ArrayList();
        tipos.add(Constantes.BOXEADOR_NEGRO);
        tipos.add(Constantes.BOXEADORA_JESSI);
        tipos.add(Constantes.BOXEADOR_BUZZ);
        tipos.add(Constantes.BOXEADORA_KATE);        
        tipos.add(Constantes.BOXEADOR_JOHN);        
        tipos.add(Constantes.BOXEADORA_CECILIA);
        
        
        for (int i = 0; i < tipos.size(); i++) {
            TextButton.TextButtonStyle estilo=new TextButton.TextButtonStyle();
            estilo.up=boxeadores.getDrawable(tipos.get(i));
            estilo.down=boxeadores.getDrawable(tipos.get(i)+"_seleccionado");
            estilo.fontColor=new Color(BLACK);
            estilo.font = font;
            estilo.pressedOffsetX=1;
            estilo.pressedOffsetY=-1;           
            botones.add(new TextButton(tipos.get(i),estilo));            
        }
               
        
    }
    
    public void cambiarPantalla(String boton_pulsado){
        if(boton_pulsado.equals("atras")){
            principal.setScreen(new PantallaSeleccionOCreacionPersonaje(principal));
        }else{
            principal.setScreen(new PantallaRellenarPerfil(principal,tipo_boxeador));
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

    @Override
    public void dispose() {
        Assets.instance.dispose();


    }
    
    public void startVoice(){
        switch(tipo_boxeador){
            case "Boxeador_john":
                sonidoVoz = (Sound) Assets.instance.assetsSonido.voz_john;
                break;
            case "Boxeador_negro":
                sonidoVoz = (Sound) Assets.instance.assetsSonido.voz_negro;
                break;
            case "Boxeador_Buzz":
                sonidoVoz = (Sound) Assets.instance.assetsSonido.voz_buzz;
                break;
            case "Boxeadora_Kate":
                sonidoVoz = (Sound) Assets.instance.assetsSonido.voz_kate;
                break;
            case "Boxeadora_Jessi":
                sonidoVoz = (Sound) Assets.instance.assetsSonido.voz_jessi;
                break;
            case "Boxeadora_cecilia":
                sonidoVoz = (Sound) Assets.instance.assetsSonido.voz_cecilia;
                break;
        }
        
        sonidoVoz.play(100);
    }
    
}
