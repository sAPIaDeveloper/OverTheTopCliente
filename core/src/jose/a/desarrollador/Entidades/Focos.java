/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package jose.a.desarrollador.Entidades;

import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.utils.TimeUtils;
import com.badlogic.gdx.utils.viewport.ExtendViewport;
import jose.a.desarrollador.Util.Assets;
import jose.a.desarrollador.Util.Constantes;

/**
 *
 * @author josea
 */
public class Focos {
    long walkStartTime;
    TextureRegion foco;

    public Focos() {
        AssetManager am = new AssetManager();
        Assets.instance.init(am);
    }
    
    
    
    public void render(SpriteBatch spriteBatch,ExtendViewport extendViewport){
        float walkTimeSeconds = MathUtils.nanoToSec * (TimeUtils.nanoTime() - walkStartTime);
        
        foco=(TextureRegion) (TextureRegion) Assets.instance.screen.focos.getKeyFrame(walkTimeSeconds);
        
        spriteBatch.draw(foco.getTexture(),
                0, 0, 0, 0,
                extendViewport.getWorldWidth(),
                extendViewport.getWorldHeight(),
                1, 1,0,
                foco.getRegionX(),
                foco.getRegionY(),
                foco.getRegionWidth(),
                foco.getRegionHeight(),
                false,false);
    }


}
