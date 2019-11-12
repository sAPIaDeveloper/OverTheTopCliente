/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package jose.a.desarrollador.Entidades;

import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.utils.viewport.ExtendViewport;
import jose.a.desarrollador.Util.Assets;
import jose.a.desarrollador.Util.Constantes;

/**
 *
 * @author josea
 */
public class Tatami {
    
    TextureRegion tatami;

    public Tatami() {
        AssetManager am = new AssetManager();
        Assets.instance.init(am);
        tatami=(TextureRegion) Assets.instance.screen.tatami;
    }
    
    public void render(SpriteBatch spriteBatch,ExtendViewport extendViewport){
        
        spriteBatch.draw(tatami.getTexture(),
                0, 0, 0, 0,
                extendViewport.getWorldWidth(),
                extendViewport.getWorldHeight(),
                1, 1,0,
                tatami.getRegionX(),
                tatami.getRegionY(),
                tatami.getRegionWidth(),
                tatami.getRegionHeight(),
                false,false);
    }
    
}
