/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package jose.a.desarrollador.Entidades;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.utils.viewport.ExtendViewport;
import jose.a.desarrollador.Util.Assets;
import jose.a.desarrollador.Util.Constantes;

/**
 *
 * @author josea
 */
public class Round {
    boolean activo;
    Vector2 posicion_round;
    TextureRegion round;
    float velocidad_transicion;
    final float DESPLAZAMIENTO = 1f;
    float posicion;
    float velocidad;
    float scale;    
    public Round() {
        AssetManager am = new AssetManager();
        Assets.instance.init(am);
        velocidad = 1;
        posicion_round = new Vector2();      
        scale = 1;
        posicion = Gdx.graphics.getWidth();
        round=(TextureRegion) Assets.instance.screen.round_uno;
        activo = true;
    }
    
    public void render(SpriteBatch spriteBatch,ExtendViewport extendViewport,int asalto){        
        update(extendViewport);
        switch(asalto){
            case 1:
                round=(TextureRegion) Assets.instance.screen.round_uno;
                
                break;
                
            case 2:
                round=(TextureRegion) Assets.instance.screen.round_dos;
                break;
                
            case 3: 
                round=(TextureRegion) Assets.instance.screen.round_tres;
                break;
        }
        
        spriteBatch.draw(round.getTexture(),
                posicion_round.x, posicion_round.y, 
                round.getRegionWidth()/2,
                round.getRegionHeight()/2,
                round.getRegionWidth(),
                round.getRegionHeight(),
                scale,
                scale,
                0,
                round.getRegionX(),
                round.getRegionY(),
                round.getRegionWidth(),
                round.getRegionHeight(),
                false,false);
    }
    
    public void update(ExtendViewport extendViewport){
        posicion -=  DESPLAZAMIENTO * velocidad;       
        posicion_round.x  = posicion;
        posicion_round.y = (extendViewport.getWorldHeight()/2);
        
        if(posicion_round.x <= ((extendViewport.getWorldWidth()/2) - (extendViewport.getWorldWidth()/4)) 
                && posicion_round.x >= ((extendViewport.getWorldWidth()/2) - (extendViewport.getWorldWidth()/3))  - (round.getRegionWidth()/2)){
            velocidad = 1.5f;
            
        }else{
            velocidad += 1;
            if(posicion_round.x > 160){
                scale += 0.03;
                if(scale > 2){
                    scale = 2f;
                }
            }else{
                scale -= 0.03;
                if(scale < 2){
                    scale = 1;
                }
            }
        }
        
        if(posicion_round.x <= round.getRegionWidth() * -1){
            activo = false;
        }
    }
    
    public void restablecer(){
        scale = 1;
        posicion = Gdx.graphics.getWidth();
        velocidad = 1;
        activo = true;
    }

    public boolean isActivo() {
        return activo;
    }

    
    
    
}
