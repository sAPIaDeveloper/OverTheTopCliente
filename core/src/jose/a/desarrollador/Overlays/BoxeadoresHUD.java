/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package jose.a.desarrollador.Overlays;

import com.badlogic.gdx.ScreenAdapter;
import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.GlyphLayout;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.utils.viewport.ExtendViewport;
import com.badlogic.gdx.utils.viewport.Viewport;
import jose.a.desarrollador.Util.Assets;
import jose.a.desarrollador.Util.Constantes;

/**
 *
 * @author josea
 */
public class BoxeadoresHUD extends ScreenAdapter{
    public final Viewport viewport;    
    BitmapFont font;
    float width_vida_j1;
    float width_vida_j2;
    float width_stamina_j1;
    float width_stamina_j2;
    float widthNombreJ2;
    String nombreJ1;
    String nombreJ2;
    TextureRegion corazon;
    TextureRegion rayo;
    
    TextureRegion barra_vida_j1;
    TextureRegion barra_vida_j2;
    TextureRegion barra_stamina_j1;
    TextureRegion barra_stamina_j2;
    SpriteBatch batch;
    public BoxeadoresHUD(String nombreJ1,String nombreJ2){
        viewport = new ExtendViewport(Constantes.HUD_VIEWPORT_SIZE,Constantes.HUD_VIEWPORT_SIZE);
        AssetManager am = new AssetManager();
        Assets.instance.init(am);  
        batch= new SpriteBatch();
        this.nombreJ1=nombreJ1;
        this.nombreJ2=nombreJ2;
        init();
        
        // Lo hago para obtener el ancho del texto y ai poder situarlo
        GlyphLayout layout = new GlyphLayout();     
        layout.setText(font,nombreJ2.toUpperCase());
        widthNombreJ2 = layout.width;
        
    }
    
    
    public void init(){
        font = Assets.instance.assetsUi.generator.generateFont(Assets.instance.assetsUi.parameter);
        corazon = Assets.instance.assetsUi.corazon;
        rayo = Assets.instance.assetsUi.rayo;
        barra_vida_j1 = Assets.instance.assetsUi.barra_vida;
        barra_vida_j2 = Assets.instance.assetsUi.barra_vida;
        barra_stamina_j1 = Assets.instance.assetsUi.barra_stamina;
        barra_stamina_j2 = Assets.instance.assetsUi.barra_stamina;
        width_vida_j1=270;
        width_stamina_j1=200;
    }
    

    public void render(SpriteBatch batch,String datos,ExtendViewport viewport){
        update(datos);
       viewport.apply();
       // batch.setProjectionMatrix(viewport.getCamera().combined);
       // batch.begin();
       // width_vida_j1-=delta*10;
        font.draw(batch, nombreJ1.toUpperCase(), 15, viewport.getWorldHeight()-10);
        font.draw(batch, nombreJ2.toUpperCase(), viewport.getWorldWidth()-(widthNombreJ2+15), viewport.getWorldHeight()-10);
       
        batch.draw(
                    corazon.getTexture(),
                    10,
                    viewport.getWorldHeight()-60,
                    0,
                    0,
                    corazon.getRegionWidth()/3,
                    corazon.getRegionHeight()/3,
                    1,
                    1,
                    0,
                    corazon.getRegionX(),
                    corazon.getRegionY(),
                    corazon.getRegionWidth(),
                    corazon.getRegionHeight(),
                    false,
                    false);
        
        batch.draw(
                    barra_vida_j1.getTexture(),
                    corazon.getRegionWidth()/2,
                    (viewport.getWorldHeight()-70) ,
                    0,
                    0,
                    width_vida_j1,
                    barra_vida_j1.getRegionHeight()/2,
                    1,
                    1,
                    0,
                    barra_vida_j1.getRegionX(),
                    barra_vida_j1.getRegionY(),
                    barra_vida_j1.getRegionWidth(),
                    barra_vida_j1.getRegionHeight(),
                    false,
                    false);
        
        
        batch.draw(
                    rayo.getTexture(),
                    0,
                    viewport.getWorldHeight()-105,
                    0,
                    0,
                    rayo.getRegionWidth()/2,
                    rayo.getRegionHeight()/2,
                    1,
                    1,
                    0,
                    rayo.getRegionX(),
                    rayo.getRegionY(),
                    rayo.getRegionWidth(),
                    rayo.getRegionHeight(),
                    false,
                    false);
        
        batch.draw(
                    barra_stamina_j1.getTexture(),
                    rayo.getRegionWidth()/2,
                    (viewport.getWorldHeight()-100) ,
                    0,
                    0,
                    width_stamina_j1,
                    barra_stamina_j1.getRegionHeight()/2,
                    1,
                    1,
                    0,
                    barra_stamina_j1.getRegionX(),
                    barra_stamina_j1.getRegionY(),
                    barra_stamina_j1.getRegionWidth(),
                    barra_stamina_j1.getRegionHeight(),
                    false,
                    false);
        
        // ENEMIGO
        batch.draw(
                    corazon.getTexture(),
                    viewport.getWorldWidth()-(corazon.getRegionWidth()/3+10),
                    viewport.getWorldHeight()-60,
                    0,
                    0,
                    corazon.getRegionWidth()/3,
                    corazon.getRegionHeight()/3,
                    1,
                    1,
                    0,
                    corazon.getRegionX(),
                    corazon.getRegionY(),
                    corazon.getRegionWidth(),
                    corazon.getRegionHeight(),
                    false,
                    false);
        
        batch.draw(
                    barra_vida_j2.getTexture(),
                    viewport.getWorldWidth() - (width_vida_j2 + corazon.getRegionWidth()/2),
                    (viewport.getWorldHeight()-70) ,
                    0,
                    0,
                    width_vida_j2,
                    barra_vida_j2.getRegionHeight()/2,
                    1,
                    1,
                    0,
                    barra_vida_j2.getRegionX(),
                    barra_vida_j2.getRegionY(),
                    barra_vida_j2.getRegionWidth(),
                    barra_vida_j2.getRegionHeight(),
                    true,
                    false);
        
        
        batch.draw(
                    rayo.getTexture(),
                    viewport.getWorldWidth()-rayo.getRegionWidth()/2,
                    viewport.getWorldHeight()-105,
                    0,
                    0,
                    rayo.getRegionWidth()/2,
                    rayo.getRegionHeight()/2,
                    1,
                    1,
                    0,
                    rayo.getRegionX(),
                    rayo.getRegionY(),
                    rayo.getRegionWidth(),
                    rayo.getRegionHeight(),
                    false,
                    false);
        
        batch.draw(
                    barra_stamina_j2.getTexture(),
                    viewport.getWorldWidth() - (width_stamina_j2+rayo.getRegionWidth()/2),
                    (viewport.getWorldHeight()-100) ,
                    0,
                    0,
                    width_stamina_j2,
                    barra_stamina_j2.getRegionHeight()/2,
                    1,
                    1,
                    0,
                    barra_stamina_j2.getRegionX(),
                    barra_stamina_j2.getRegionY(),
                    barra_stamina_j2.getRegionWidth(),
                    barra_stamina_j2.getRegionHeight(),
                    true,
                    false);

       // batch.end();
    }

   
     
    
    public void update(String datos){
        String informacion_desglosada[] = datos.split("&");
        
        width_vida_j1 = Integer.parseInt(informacion_desglosada[0]);
        width_vida_j2 = Integer.parseInt(informacion_desglosada[2]);
        width_stamina_j1 = Integer.parseInt(informacion_desglosada[1]);
        width_stamina_j2 = Integer.parseInt(informacion_desglosada[3]);
        
        
    }
}
