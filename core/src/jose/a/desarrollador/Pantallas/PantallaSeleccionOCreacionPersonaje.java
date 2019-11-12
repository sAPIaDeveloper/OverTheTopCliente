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
import static com.badlogic.gdx.graphics.Color.BLACK;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton.TextButtonStyle;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.utils.Align;
import java.util.ArrayList;
import java.util.TreeMap;
import jose.a.desarrollador.Entidades.Boxeador;
import jose.a.desarrollador.Principal;
import jose.a.desarrollador.Util.Assets;
import jose.a.desarrollador.Util.Constantes;

/**
 *
 * @author josea
 */
public class PantallaSeleccionOCreacionPersonaje extends ScreenAdapter{    
    Principal principal;
    Stage stage; 
    BitmapFont font;
    ArrayList<TextButton> botones;
    Table tabla;  
    private Skin boton_add;
    private Skin boxeador;
    TextureAtlas atlasUI;
    TextureAtlas atlasRetrato;
    
    public PantallaSeleccionOCreacionPersonaje(Principal principal) {
        this.principal=principal;  

        atlasUI=new TextureAtlas(Constantes.TEXTURE_ATLAS_UI);
        atlasRetrato=new TextureAtlas(Constantes.TEXTURE_ATLAS_ROSTRO_BOXEADORES);
        botones=new ArrayList<TextButton>();
        //Para que detecte los eventos de raton
        
        //Crear la fuente
        AssetManager am = new AssetManager();
        Assets.instance.init(am);
        font = Assets.instance.assetsUi.generator.generateFont(Assets.instance.assetsUi.parameter);
        boton_add=new Skin(atlasUI);
        boxeador=new Skin(atlasRetrato); 
        
        hacerBotonesBoxeadores();
        init();
        
    }        
    
    public void init(){        
                
        
        stage=new Stage();  
        Gdx.input.setInputProcessor(stage);
        
        tabla= new Table();
        
        tabla.setFillParent(true);
        for (int i = 0; i < botones.size(); i++) {
            TextButton boton=botones.get(i);
            final String informacion=boton.getLabel().getText().toString();
            System.out.println(informacion);
            boton.addListener(new ClickListener(){
            @Override
            public void clicked(InputEvent event, float x, float y) {                  
                if(informacion.equals("")){
                    principal.setScreen(new PantallaElegirPersonaje(principal));
                }else{
                    principal.setScreen(new PantallaAccionesBoxeador(principal,informacion));
                }
            }

        });
            boton.getLabel().setFontScaleX(1.5f);
            boton.getLabel().setFontScaleY(1.5f);
            boton.getLabel().setAlignment(Align.topRight);
            tabla.add(boton).width(150).height(150).space(10);
        }
        
        stage.addActor(tabla);
        
        
        
    }
    
    public void hacerBotonesBoxeadores(){
        TreeMap<String,Boxeador> lista_boxeadores=Constantes.DATOS_USUARIO.getLista_boxeadores();
        int numBox=lista_boxeadores.size();
        int numbotonesAdd=3-numBox;
        for (String nombre_box : lista_boxeadores.keySet()) {
            Boxeador b=lista_boxeadores.get(nombre_box);
            TextButtonStyle estilo=new TextButtonStyle();
            estilo.up= boxeador.getDrawable(b.getTipo_boxeador());
            estilo.down= boxeador.getDrawable(b.getTipo_boxeador()+"_seleccionado");            
            estilo.fontColor=Color.WHITE;
            estilo.font = font;
            estilo.pressedOffsetX=1;
            estilo.pressedOffsetY=-1;           
            botones.add(new TextButton(b.getNombre_boxeador(),estilo));
        }       
       
        hacerBotonesAddBoxeadores(numbotonesAdd);
        
    }
    
    public void hacerBotonesAddBoxeadores(int num_botones){
        for (int i = 0; i < num_botones; i++) {
            TextButtonStyle estilo=new TextButtonStyle();
            estilo.up= boton_add.getDrawable(Constantes.BOTON_ADD_BOXEADOR);
            estilo.down= boton_add.getDrawable(Constantes.BOTON_ADD_BOXEADOR_PULSADO);
            estilo.fontColor=new Color(BLACK);
            estilo.font = font;
            estilo.pressedOffsetX=1;
            estilo.pressedOffsetY=-1;
            botones.add(new TextButton("",estilo));
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
}
