/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package jose.a.desarrollador.Pantallas;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.InputProcessor;
import com.badlogic.gdx.ScreenAdapter;
import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.GlyphLayout;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.utils.viewport.ExtendViewport;
import jose.a.desarrollador.Entidades.Focos;
import jose.a.desarrollador.Entidades.Publico;
import jose.a.desarrollador.Entidades.Tatami;
import jose.a.desarrollador.Principal;
import jose.a.desarrollador.Util.Assets;
import jose.a.desarrollador.Util.Constantes;
import jose.a.desarrollador.Util.Preferencias;

/**
 *
 * @author josea
 */
public class PantallaInicio extends ScreenAdapter implements  InputProcessor{
    Preferencias pref;
    Principal principal;
    private SpriteBatch spriteBatch;
    private ExtendViewport extendViewport;
    Publico publico;
    Focos focos;
    Tatami tatami;
    BitmapFont font;
    TextureRegion logo;    
    float widthTexto;
    public static Music inicio;
    int volumen;
    public PantallaInicio(Principal principal) {
        this.principal = principal;
        
        init();                      
    }

    public void init(){
        pref = new Preferencias();
        volumen = pref.getVolumen_musica();
        Gdx.input.setInputProcessor(this);
        
        AssetManager am = new AssetManager();
        Assets.instance.init(am);
        
        inicio = Assets.instance.assetsSonido.musica_inicio;
        inicio.setLooping(true);
        inicio.setVolume(volumen);
        inicio.play();
        logo=(TextureRegion) Assets.instance.screen.logo;
        
        font = Assets.instance.assetsUi.generator.generateFont(Assets.instance.assetsUi.parameter);
        font.setColor(Color.BLACK);
        
        spriteBatch = new SpriteBatch();
        extendViewport = new ExtendViewport(Constantes.WORLD_SIZE,Constantes.WORLD_SIZE);        
        publico = new Publico();
        focos = new Focos();
        tatami = new Tatami();
        GlyphLayout layout = new GlyphLayout();     
        layout.setText(font,"Pulse cualquier boton para continuar");
        widthTexto = layout.width;
        
        
    }
    
    @Override
    public void resize(int width, int height) {
        extendViewport.update(width,height,true);
    }

    @Override
    public void render(float delta) {
       
        extendViewport.apply();
        Gdx.gl.glClearColor(0,0,0,1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
        
        spriteBatch.setProjectionMatrix(extendViewport.getCamera().combined);
        spriteBatch.begin();
        
        publico.render(spriteBatch,extendViewport);
         
        focos.render(spriteBatch,extendViewport);
        
        tatami.render(spriteBatch,extendViewport);
        
        spriteBatch.draw(logo.getTexture(),
                (extendViewport.getWorldWidth()/2)- ((logo.getRegionWidth()*2)/2),
                (extendViewport.getWorldHeight()/2)-((logo.getRegionHeight()*2)/3),
                0,
                0,
                logo.getRegionWidth()*2,
                logo.getRegionHeight()*2,
                1, 1,0,
                logo.getRegionX(),
                logo.getRegionY(),
                logo.getRegionWidth(),
                logo.getRegionHeight(),
                false,false);    
        
       /* spriteBatch.draw(
                    saco.getTexture(),
                    (extendViewport.getWorldWidth()/2)-((saco.getRegionWidth()*4)/2),
                    (extendViewport.getWorldHeight()/2)-((saco.getRegionHeight()*4)/3),
                    0,
                    0,
                    saco.getRegionWidth()*4,
                    saco.getRegionHeight()*4,
                    1,
                    1,
                    0,
                    saco.getRegionX(),
                    saco.getRegionY(),
                    saco.getRegionWidth(),
                    saco.getRegionHeight(),
                    false,
                    false);*/
       
        font.draw(spriteBatch, "Pulse cualquier boton para continuar", (extendViewport.getWorldWidth()/2) - (widthTexto/2), extendViewport.getWorldHeight()/5);
      
        spriteBatch.end();
    }


    @Override
    public void dispose() {
        Assets.instance.dispose();
        spriteBatch.dispose();
    }

    @Override
    public boolean keyDown(int keycode) {
        principal.setScreen(new PantallaLoguin(principal));
        return false;
    }

    @Override
    public boolean keyUp(int keycode) {
       return false;
    }

    @Override
    public boolean keyTyped(char character) {
        return false;
    }

    @Override
    public boolean touchDown(int screenX, int screenY, int pointer, int button) {
        return false;
    }

    @Override
    public boolean touchUp(int screenX, int screenY, int pointer, int button) {
        return false;
    }

    @Override
    public boolean touchDragged(int screenX, int screenY, int pointer) {
        return false;
    }

    @Override
    public boolean mouseMoved(int screenX, int screenY) {
       return false;
    }

    @Override
    public boolean scrolled(int amount) {
        return false;
    }
}
