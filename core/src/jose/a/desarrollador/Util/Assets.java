/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package jose.a.desarrollador.Util;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.assets.AssetDescriptor;
import com.badlogic.gdx.assets.AssetErrorListener;
import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.graphics.g2d.TextureAtlas.AtlasRegion;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.graphics.g2d.freetype.FreeTypeFontGenerator;
import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.Disposable;

/**
 *
 * @author josea
 */
public class Assets implements Disposable,AssetErrorListener {
    
    public static final Assets instance = new Assets();
    private AssetManager assetManager;
    public ScreenInicio screen;
    public AssetsUI assetsUi;    
    public AssetsNEGRO assetsNegro;
    public AssetsBUZZ assetsBuzz;
    public AssetsKATE assetsKate;
    public AssetsJESSI assetsJessi;    
    public AssetsSonido assetsSonido;
    
    public void init(AssetManager assetManager){
        this.assetManager = new AssetManager();
        assetManager.setErrorListener(this);
        assetManager.load(Constantes.TEXTURE_ATLAS_INICIO, TextureAtlas.class);
        assetManager.load(Constantes.TEXTURE_ATLAS_UI, TextureAtlas.class);
        assetManager.load(Constantes.TEXTURE_ATLAS_MOVIMIENTOS_JESSI, TextureAtlas.class);
        assetManager.load(Constantes.TEXTURE_ATLAS_MOVIMIENTOS_KATE, TextureAtlas.class);     
        assetManager.finishLoading();
        
        TextureAtlas atlasInicio = assetManager.get(Constantes.TEXTURE_ATLAS_INICIO);
        TextureAtlas atlasUI = assetManager.get(Constantes.TEXTURE_ATLAS_UI);
        TextureAtlas atlasJessi = assetManager.get(Constantes.TEXTURE_ATLAS_MOVIMIENTOS_JESSI);
        TextureAtlas atlasKate = assetManager.get(Constantes.TEXTURE_ATLAS_MOVIMIENTOS_KATE);
        
        screen = new ScreenInicio(atlasInicio);
        assetsUi = new AssetsUI(atlasUI);
        assetsJessi = new AssetsJESSI(atlasJessi);
        assetsKate = new AssetsKATE(atlasKate);
        assetsSonido = new AssetsSonido();
    }
    
    @Override
    public void dispose() {
       
    }

    @Override
    public void error(AssetDescriptor asset, Throwable throwable) {
        
    }
    
    public class ScreenInicio{
        public final AtlasRegion tatami;
        public final AtlasRegion logo;
        public final AtlasRegion finish;
        public final AtlasRegion sangre;
        public final AtlasRegion round_uno;
        public final AtlasRegion round_dos;
        public final AtlasRegion round_tres;
        
        public final Animation focos;
         
        public final Animation publico;
        
        
        public ScreenInicio(TextureAtlas atlas){
            tatami=atlas.findRegion(Constantes.TATAMI);
            logo = atlas.findRegion(Constantes.LOGO);
            sangre = atlas.findRegion(Constantes.SANGRE);
            
            finish = atlas.findRegion(Constantes.FINISH);
            
            round_uno = atlas.findRegion(Constantes.ROUND_1);
            round_dos = atlas.findRegion(Constantes.ROUND_2);
            round_tres = atlas.findRegion(Constantes.ROUND_3);
            
            
            Array<AtlasRegion> luces = new Array<AtlasRegion>();
            luces.add(atlas.findRegion(Constantes.FONDO_0));
            luces.add(atlas.findRegion(Constantes.FONDO_0));
            luces.add(atlas.findRegion(Constantes.FONDO_0));
            luces.add(atlas.findRegion(Constantes.FOCO_0));
            luces.add(atlas.findRegion(Constantes.FOCO_1));
            luces.add(atlas.findRegion(Constantes.FOCO_2));
            luces.add(atlas.findRegion(Constantes.FOCO_3));
            luces.add(atlas.findRegion(Constantes.FOCO_4));
            luces.add(atlas.findRegion(Constantes.FOCO_5));
            luces.add(atlas.findRegion(Constantes.FOCO_6));
            luces.add(atlas.findRegion(Constantes.FOCO_7));
            luces.add(atlas.findRegion(Constantes.FOCO_7));
            
            focos=new Animation(Constantes.FOCO_LOOP_DURATION,luces,Animation.PlayMode.LOOP_PINGPONG);

            Array<AtlasRegion> personas = new Array<AtlasRegion>();

            personas.add(atlas.findRegion(Constantes.FONDO_0));
            personas.add(atlas.findRegion(Constantes.FONDO_1));
            personas.add(atlas.findRegion(Constantes.FONDO_0));
            personas.add(atlas.findRegion(Constantes.FONDO_0));
            personas.add(atlas.findRegion(Constantes.FONDO_1));
            personas.add(atlas.findRegion(Constantes.FONDO_0));
            personas.add(atlas.findRegion(Constantes.FONDO_2));
            personas.add(atlas.findRegion(Constantes.FONDO_0));
            personas.add(atlas.findRegion(Constantes.FONDO_0));
            personas.add(atlas.findRegion(Constantes.FONDO_3));

            publico=new Animation(Constantes.PUBLICO_LOOP_DURATION,personas,Animation.PlayMode.LOOP);
            
            
        }
    }
    
    public class AssetsUI{
        
        public final Animation saco_golpeado;
        public final Animation progress_bar;
        public final TextureRegion saco;
        public final TextureRegion corazon;
        public final TextureRegion rayo;
        public final TextureRegion barra_vida;
        public final TextureRegion barra_stamina;       
        public final FreeTypeFontGenerator generator ;
        public final FreeTypeFontGenerator.FreeTypeFontParameter parameter;
        
        public AssetsUI(TextureAtlas atlas){
            
            saco= atlas.findRegion(Constantes.SACO_00);
            corazon = atlas.findRegion(Constantes.CORAZON);
            rayo = atlas.findRegion(Constantes.RAYO);
            barra_vida = atlas.findRegion(Constantes.BARRA_VIDA);
            barra_stamina = atlas.findRegion(Constantes.BARRA_STAMINA);            
            
            Array<AtlasRegion> golpeo_normal = new Array<AtlasRegion>();
            golpeo_normal.add(atlas.findRegion(Constantes.SACO_00));
            golpeo_normal.add(atlas.findRegion(Constantes.SACO_01));
            golpeo_normal.add(atlas.findRegion(Constantes.SACO_02));
            golpeo_normal.add(atlas.findRegion(Constantes.SACO_03));
            golpeo_normal.add(atlas.findRegion(Constantes.SACO_04));
            
            
            saco_golpeado=new Animation(Constantes.SACO_LOOP_DURATION,golpeo_normal,Animation.PlayMode.LOOP);
            
            Array<AtlasRegion> carga_barra = new Array<AtlasRegion>();
            carga_barra.add(atlas.findRegion(Constantes.PROGRESS_BAR_0));
            carga_barra.add(atlas.findRegion(Constantes.PROGRESS_BAR_1));
            carga_barra.add(atlas.findRegion(Constantes.PROGRESS_BAR_2));
            carga_barra.add(atlas.findRegion(Constantes.PROGRESS_BAR_3));
            carga_barra.add(atlas.findRegion(Constantes.PROGRESS_BAR_4));
            carga_barra.add(atlas.findRegion(Constantes.PROGRESS_BAR_5));
            carga_barra.add(atlas.findRegion(Constantes.PROGRESS_BAR_6));
            carga_barra.add(atlas.findRegion(Constantes.PROGRESS_BAR_7));
            carga_barra.add(atlas.findRegion(Constantes.PROGRESS_BAR_8));
            carga_barra.add(atlas.findRegion(Constantes.PROGRESS_BAR_9));
            carga_barra.add(atlas.findRegion(Constantes.PROGRESS_BAR_10));
            carga_barra.add(atlas.findRegion(Constantes.PROGRESS_BAR_11));
            carga_barra.add(atlas.findRegion(Constantes.PROGRESS_BAR_12));
            
            progress_bar=new Animation(Constantes.PROGRESS_BAR_LOOP_DURATION,carga_barra,Animation.PlayMode.LOOP);
            
            generator = new FreeTypeFontGenerator(Gdx.files.internal(Constantes.FUENTE));
            parameter = new FreeTypeFontGenerator.FreeTypeFontParameter();
            parameter.size = 18;
        }
        
    }           
    
    public class AssetsBUZZ{
        public AssetsBUZZ(TextureAtlas atlas) {
        }
    }
    
    public class AssetsNEGRO{
        public AssetsNEGRO(TextureAtlas atlas) {
        }
    }
    
    public class AssetsKATE{
        public final Animation pivotando_frente;
        public final Animation directo_izquierda_frente;
        public final Animation directo_derecha_frente;
        public final Animation esquivar_izquierda_frente;
        public final Animation esquivar_derecha_frente;
        public final Animation gancho_izquierda_frente;
        public final Animation gancho_derecha_frente;
        public final Animation bloqueo_frente;
        public final Animation golpeado_frente;
        public final Animation derrotado_frente;
        
        public final Animation pivotando_espalda;
        public final Animation directo_izquierda_espalda;
        public final Animation directo_derecha_espalda;
        public final Animation esquivar_izquierda_espalda;
        public final Animation esquivar_derecha_espalda;
        public final Animation gancho_izquierda_espalda;
        public final Animation gancho_derecha_espalda;
        public final Animation bloqueo_espalda;
        public final Animation golpeado_espalda;
        public final Animation derrotado_espalda;
        
        public AssetsKATE(TextureAtlas atlas) {
            //PERSONAJE DE FRENTE
            
            Array<AtlasRegion> accion_pivotar_frente = new Array<AtlasRegion>();            
            accion_pivotar_frente.add(atlas.findRegion(Constantes.BOXEADORA_KATE_FRENTE_PIVOTAR_0));
            accion_pivotar_frente.add(atlas.findRegion(Constantes.BOXEADORA_KATE_FRENTE_PIVOTAR_1));            
            pivotando_frente=new Animation(Constantes.PIVOTEO_LOOP,accion_pivotar_frente,Animation.PlayMode.LOOP);
            
            Array<AtlasRegion> directo_izq_frente = new Array<AtlasRegion>();            
            directo_izq_frente.add(atlas.findRegion(Constantes.BOXEADORA_KATE_FRENTE_DIRECTO_IZQUIERDA_0));
            directo_izq_frente.add(atlas.findRegion(Constantes.BOXEADORA_KATE_FRENTE_DIRECTO_IZQUIERDA_1)); 
           // directo_izq_frente.add(atlas.findRegion(Constantes.BOXEADORA_KATE_FRENTE_DIRECTO_IZQUIERDA_0));
            directo_izq_frente.add(atlas.findRegion(Constantes.BOXEADORA_KATE_FRENTE_PIVOTAR_0));
            directo_izquierda_frente=new Animation(Constantes.DIRECTO_LOOP,directo_izq_frente,Animation.PlayMode.LOOP);
            
            Array<AtlasRegion> directo_dcha_frente = new Array<AtlasRegion>();            
            directo_dcha_frente.add(atlas.findRegion(Constantes.BOXEADORA_KATE_FRENTE_DIRECTO_DERECHA_0));
            directo_dcha_frente.add(atlas.findRegion(Constantes.BOXEADORA_KATE_FRENTE_DIRECTO_DERECHA_1));
           // directo_dcha_frente.add(atlas.findRegion(Constantes.BOXEADORA_KATE_FRENTE_DIRECTO_DERECHA_0)); 
            directo_dcha_frente.add(atlas.findRegion(Constantes.BOXEADORA_KATE_FRENTE_PIVOTAR_0));
            directo_derecha_frente=new Animation(Constantes.DIRECTO_LOOP,directo_dcha_frente,Animation.PlayMode.LOOP);
            
            Array<AtlasRegion> gancho_izq_frente = new Array<AtlasRegion>();            
            gancho_izq_frente.add(atlas.findRegion(Constantes.BOXEADORA_KATE_FRENTE_GANCHO_IZQUIERDA_0));
            gancho_izq_frente.add(atlas.findRegion(Constantes.BOXEADORA_KATE_FRENTE_GANCHO_IZQUIERDA_1));               
            gancho_izq_frente.add(atlas.findRegion(Constantes.BOXEADORA_KATE_FRENTE_PIVOTAR_0));
            gancho_izquierda_frente=new Animation(Constantes.GANCHO_LOOP,gancho_izq_frente,Animation.PlayMode.LOOP);
            
            Array<AtlasRegion> gancho_dcha_frente = new Array<AtlasRegion>();            
            gancho_dcha_frente.add(atlas.findRegion(Constantes.BOXEADORA_KATE_FRENTE_GANCHO_DERECHA_0));
            gancho_dcha_frente.add(atlas.findRegion(Constantes.BOXEADORA_KATE_FRENTE_GANCHO_DERECHA_1));              
            gancho_dcha_frente.add(atlas.findRegion(Constantes.BOXEADORA_KATE_FRENTE_PIVOTAR_0));
            gancho_derecha_frente=new Animation(Constantes.GANCHO_LOOP,gancho_dcha_frente,Animation.PlayMode.LOOP);
            
            Array<AtlasRegion> accion_esquivar_derecha_frente = new Array<AtlasRegion>();                        
            accion_esquivar_derecha_frente.add(atlas.findRegion(Constantes.BOXEADORA_KATE_FRENTE_ESQUIVAR_DERECHA));
            accion_esquivar_derecha_frente.add(atlas.findRegion(Constantes.BOXEADORA_KATE_FRENTE_ESQUIVAR_DERECHA));            
            accion_esquivar_derecha_frente.add(atlas.findRegion(Constantes.BOXEADORA_KATE_FRENTE_PIVOTAR_0));            
            esquivar_derecha_frente=new Animation(Constantes.ESQUIVAR_LOOP,accion_esquivar_derecha_frente,Animation.PlayMode.LOOP);
            
            Array<AtlasRegion> accion_esquivar_izquierda_frente = new Array<AtlasRegion>();                        
            accion_esquivar_izquierda_frente.add(atlas.findRegion(Constantes.BOXEADORA_KATE_FRENTE_ESQUIVAR_IZQUIERDA));    
            accion_esquivar_izquierda_frente.add(atlas.findRegion(Constantes.BOXEADORA_KATE_FRENTE_ESQUIVAR_IZQUIERDA));    
            accion_esquivar_izquierda_frente.add(atlas.findRegion(Constantes.BOXEADORA_KATE_FRENTE_PIVOTAR_0));            
            esquivar_izquierda_frente=new Animation(Constantes.ESQUIVAR_LOOP,accion_esquivar_izquierda_frente,Animation.PlayMode.LOOP);
            
            Array<AtlasRegion> accion_bloquear_frente = new Array<AtlasRegion>();            
            //accion_bloquear_frente.add(atlas.findRegion(Constantes.BOXEADORA_KATE_FRENTE_PIVOTAR_0));
            accion_bloquear_frente.add(atlas.findRegion(Constantes.BOXEADORA_KATE_FRENTE_BLOQUEO));
            accion_bloquear_frente.add(atlas.findRegion(Constantes.BOXEADORA_KATE_FRENTE_BLOQUEO));
            accion_bloquear_frente.add(atlas.findRegion(Constantes.BOXEADORA_KATE_FRENTE_PIVOTAR_0));            
            bloqueo_frente=new Animation(Constantes.BLOQUEO_LOOP,accion_bloquear_frente,Animation.PlayMode.LOOP);
            
            Array<AtlasRegion> accion_golpeado_frente = new Array<AtlasRegion>();            
            accion_golpeado_frente.add(atlas.findRegion(Constantes.BOXEADORA_KATE_FRENTE_PIVOTAR_0));
            accion_golpeado_frente.add(atlas.findRegion(Constantes.BOXEADORA_KATE_FRENTE_GOLPEADO)); 
            accion_golpeado_frente.add(atlas.findRegion(Constantes.BOXEADORA_KATE_FRENTE_PIVOTAR_0));            
            accion_golpeado_frente.add(atlas.findRegion(Constantes.BOXEADORA_KATE_FRENTE_GOLPEADO));             
            golpeado_frente=new Animation(Constantes.GOLPEADO_LOOP,accion_golpeado_frente,Animation.PlayMode.LOOP);
            
            Array<AtlasRegion> accion_derrotado_frente = new Array<AtlasRegion>();
            accion_derrotado_frente.add(atlas.findRegion(Constantes.BOXEADORA_KATE_FRENTE_DESCOMPONER_0));
            accion_derrotado_frente.add(atlas.findRegion(Constantes.BOXEADORA_KATE_FRENTE_DESCOMPONER_1));
            accion_derrotado_frente.add(atlas.findRegion(Constantes.BOXEADORA_KATE_FRENTE_DESCOMPONER_2));
            accion_derrotado_frente.add(atlas.findRegion(Constantes.BOXEADORA_KATE_FRENTE_DESCOMPONER_3));
            accion_derrotado_frente.add(atlas.findRegion(Constantes.BOXEADORA_KATE_FRENTE_DESCOMPONER_4));
            derrotado_frente = new Animation(Constantes.DERROTADO,accion_derrotado_frente,Animation.PlayMode.NORMAL);
            

            //------------------------------------------ PERSONAJE DE ESPALDA -------------------------------------------------------------
            
            Array<AtlasRegion> accion_pivotar_espalda = new Array<AtlasRegion>();            
            accion_pivotar_espalda.add(atlas.findRegion(Constantes.BOXEADORA_KATE_ESPALDA_PIVOTAR_0));
            accion_pivotar_espalda.add(atlas.findRegion(Constantes.BOXEADORA_KATE_ESPALDA_PIVOTAR_1));            
            pivotando_espalda=new Animation(Constantes.PIVOTEO_LOOP,accion_pivotar_espalda,Animation.PlayMode.LOOP);
            
            Array<AtlasRegion> directo_izq_espalda = new Array<AtlasRegion>();       
            
            directo_izq_espalda.add(atlas.findRegion(Constantes.BOXEADORA_KATE_ESPALDA_DIRECTO_IZQUIERDA_0));
            directo_izq_espalda.add(atlas.findRegion(Constantes.BOXEADORA_KATE_ESPALDA_DIRECTO_IZQUIERDA_1)); 
          //  directo_izq_espalda.add(atlas.findRegion(Constantes.BOXEADORA_KATE_ESPALDA_DIRECTO_IZQUIERDA_0));
            directo_izq_espalda.add(atlas.findRegion(Constantes.BOXEADORA_KATE_ESPALDA_PIVOTAR_0));
            directo_izquierda_espalda=new Animation(Constantes.DIRECTO_LOOP,directo_izq_espalda,Animation.PlayMode.LOOP);
            
            Array<AtlasRegion> directo_dcha_espalda = new Array<AtlasRegion>();   
            
            directo_dcha_espalda.add(atlas.findRegion(Constantes.BOXEADORA_KATE_ESPALDA_DIRECTO_DERECHA_0));
            directo_dcha_espalda.add(atlas.findRegion(Constantes.BOXEADORA_KATE_ESPALDA_DIRECTO_DERECHA_1));  
           // directo_dcha_espalda.add(atlas.findRegion(Constantes.BOXEADORA_KATE_ESPALDA_DIRECTO_DERECHA_0));
            directo_dcha_espalda.add(atlas.findRegion(Constantes.BOXEADORA_KATE_ESPALDA_PIVOTAR_0));
            directo_derecha_espalda=new Animation(Constantes.DIRECTO_LOOP,directo_dcha_espalda,Animation.PlayMode.LOOP);
            
            Array<AtlasRegion> gancho_izq_espalda = new Array<AtlasRegion>();  
            
            gancho_izq_espalda.add(atlas.findRegion(Constantes.BOXEADORA_KATE_ESPALDA_GANCHO_IZQUIERDA_0));
            gancho_izq_espalda.add(atlas.findRegion(Constantes.BOXEADORA_KATE_ESPALDA_GANCHO_IZQUIERDA_1));            
            gancho_izq_espalda.add(atlas.findRegion(Constantes.BOXEADORA_KATE_ESPALDA_PIVOTAR_0));            
            gancho_izquierda_espalda=new Animation(Constantes.GANCHO_LOOP,gancho_izq_espalda,Animation.PlayMode.LOOP);
            
            Array<AtlasRegion> gancho_dcha_espalda = new Array<AtlasRegion>();   
            
            gancho_dcha_espalda.add(atlas.findRegion(Constantes.BOXEADORA_KATE_ESPALDA_GANCHO_DERECHA_0));
            gancho_dcha_espalda.add(atlas.findRegion(Constantes.BOXEADORA_KATE_ESPALDA_GANCHO_DERECHA_1));            
            gancho_dcha_espalda.add(atlas.findRegion(Constantes.BOXEADORA_KATE_ESPALDA_PIVOTAR_0));
            gancho_derecha_espalda=new Animation(Constantes.GANCHO_LOOP,gancho_dcha_espalda,Animation.PlayMode.LOOP);
            
            Array<AtlasRegion> accion_bloquear_espalda = new Array<AtlasRegion>();            
            //accion_bloquear_espalda.add(atlas.findRegion(Constantes.BOXEADORA_KATE_ESPALDA_PIVOTAR_0));
            accion_bloquear_espalda.add(atlas.findRegion(Constantes.BOXEADORA_KATE_ESPALDA_BLOQUEO));
            accion_bloquear_espalda.add(atlas.findRegion(Constantes.BOXEADORA_KATE_ESPALDA_BLOQUEO));
            accion_bloquear_espalda.add(atlas.findRegion(Constantes.BOXEADORA_KATE_ESPALDA_PIVOTAR_0));            
            bloqueo_espalda=new Animation(Constantes.BLOQUEO_LOOP,accion_bloquear_espalda,Animation.PlayMode.LOOP);                        
            
            Array<AtlasRegion> accion_esquivar_derecha_espalda = new Array<AtlasRegion>();                        
            accion_esquivar_derecha_espalda.add(atlas.findRegion(Constantes.BOXEADORA_KATE_ESPALDA_ESQUIVAR_DERECHA));
            accion_esquivar_derecha_espalda.add(atlas.findRegion(Constantes.BOXEADORA_KATE_ESPALDA_ESQUIVAR_DERECHA));            
            accion_esquivar_derecha_espalda.add(atlas.findRegion(Constantes.BOXEADORA_KATE_ESPALDA_PIVOTAR_0));            
            esquivar_derecha_espalda=new Animation(Constantes.ESQUIVAR_LOOP,accion_esquivar_derecha_espalda,Animation.PlayMode.LOOP);
            
            Array<AtlasRegion> accion_esquivar_izquierda_espalda = new Array<AtlasRegion>();            
            accion_esquivar_izquierda_espalda.add(atlas.findRegion(Constantes.BOXEADORA_KATE_ESPALDA_ESQUIVAR_IZQUIERDA));    
            accion_esquivar_izquierda_espalda.add(atlas.findRegion(Constantes.BOXEADORA_KATE_ESPALDA_ESQUIVAR_IZQUIERDA));    
            accion_esquivar_izquierda_espalda.add(atlas.findRegion(Constantes.BOXEADORA_KATE_ESPALDA_PIVOTAR_0));            
            esquivar_izquierda_espalda=new Animation(Constantes.ESQUIVAR_LOOP,accion_esquivar_izquierda_espalda,Animation.PlayMode.LOOP);
            
            
            Array<AtlasRegion> accion_golpeado_espalda = new Array<AtlasRegion>();            
            accion_golpeado_espalda.add(atlas.findRegion(Constantes.BOXEADORA_KATE_ESPALDA_PIVOTAR_0));
            accion_golpeado_espalda.add(atlas.findRegion(Constantes.BOXEADORA_KATE_ESPALDA_GOLPEADO));  
            accion_golpeado_espalda.add(atlas.findRegion(Constantes.BOXEADORA_KATE_ESPALDA_PIVOTAR_0));
            accion_golpeado_espalda.add(atlas.findRegion(Constantes.BOXEADORA_KATE_ESPALDA_GOLPEADO));                       
            golpeado_espalda=new Animation(Constantes.GOLPEADO_LOOP,accion_golpeado_espalda,Animation.PlayMode.LOOP);
            
            Array<AtlasRegion> accion_derrotado_espalda = new Array<AtlasRegion>();
            accion_derrotado_espalda.add(atlas.findRegion(Constantes.BOXEADORA_KATE_ESPALDA_DESCOMPONER_0));
            accion_derrotado_espalda.add(atlas.findRegion(Constantes.BOXEADORA_KATE_ESPALDA_DESCOMPONER_1));
            accion_derrotado_espalda.add(atlas.findRegion(Constantes.BOXEADORA_KATE_ESPALDA_DESCOMPONER_2));
            accion_derrotado_espalda.add(atlas.findRegion(Constantes.BOXEADORA_KATE_ESPALDA_DESCOMPONER_3));
            accion_derrotado_espalda.add(atlas.findRegion(Constantes.BOXEADORA_KATE_ESPALDA_DESCOMPONER_4));
            derrotado_espalda = new Animation(Constantes.DERROTADO,accion_derrotado_espalda,Animation.PlayMode.NORMAL);
            
        }
    }
    
    public class AssetsJESSI{
        
        public final Animation pivotando_frente;
        public final Animation directo_izquierda_frente;
        public final Animation directo_derecha_frente;
        public final Animation esquivar_izquierda_frente;
        public final Animation esquivar_derecha_frente;
        public final Animation gancho_izquierda_frente;
        public final Animation gancho_derecha_frente;
        public final Animation bloqueo_frente;
        public final Animation golpeado_frente;
        public final Animation derrotado_frente;
        
        public final Animation pivotando_espalda;
        public final Animation directo_izquierda_espalda;
        public final Animation directo_derecha_espalda;
        public final Animation esquivar_izquierda_espalda;
        public final Animation esquivar_derecha_espalda;
        public final Animation gancho_izquierda_espalda;
        public final Animation gancho_derecha_espalda;
        public final Animation bloqueo_espalda;
        public final Animation golpeado_espalda;
        public final Animation derrotado_espalda;
        
        public AssetsJESSI(TextureAtlas atlas) {
            //PERSONAJE DE FRENTE
            
            Array<AtlasRegion> accion_pivotar_frente = new Array<AtlasRegion>();            
            accion_pivotar_frente.add(atlas.findRegion(Constantes.BOXEADORA_JESSI_FRENTE_PIVOTAR_0));
            accion_pivotar_frente.add(atlas.findRegion(Constantes.BOXEADORA_JESSI_FRENTE_PIVOTAR_1));            
            pivotando_frente=new Animation(Constantes.PIVOTEO_LOOP,accion_pivotar_frente,Animation.PlayMode.LOOP);
            
            Array<AtlasRegion> directo_izq_frente = new Array<AtlasRegion>();            
            directo_izq_frente.add(atlas.findRegion(Constantes.BOXEADORA_JESSI_FRENTE_DIRECTO_IZQUIERDA_0));
            directo_izq_frente.add(atlas.findRegion(Constantes.BOXEADORA_JESSI_FRENTE_DIRECTO_IZQUIERDA_1)); 
            directo_izq_frente.add(atlas.findRegion(Constantes.BOXEADORA_JESSI_FRENTE_DIRECTO_IZQUIERDA_0));
            directo_izq_frente.add(atlas.findRegion(Constantes.BOXEADORA_JESSI_FRENTE_PIVOTAR_0));
            directo_izquierda_frente=new Animation(Constantes.DIRECTO_LOOP,directo_izq_frente,Animation.PlayMode.LOOP);
            
            Array<AtlasRegion> directo_dcha_frente = new Array<AtlasRegion>();            
            directo_dcha_frente.add(atlas.findRegion(Constantes.BOXEADORA_JESSI_FRENTE_DIRECTO_DERECHA_0));
            directo_dcha_frente.add(atlas.findRegion(Constantes.BOXEADORA_JESSI_FRENTE_DIRECTO_DERECHA_1));
            directo_dcha_frente.add(atlas.findRegion(Constantes.BOXEADORA_JESSI_FRENTE_DIRECTO_DERECHA_0)); 
            directo_dcha_frente.add(atlas.findRegion(Constantes.BOXEADORA_JESSI_FRENTE_PIVOTAR_0));
            directo_derecha_frente=new Animation(Constantes.DIRECTO_LOOP,directo_dcha_frente,Animation.PlayMode.LOOP);
            
            Array<AtlasRegion> gancho_izq_frente = new Array<AtlasRegion>();            
            gancho_izq_frente.add(atlas.findRegion(Constantes.BOXEADORA_JESSI_FRENTE_GANCHO_IZQUIERDA_0));
            gancho_izq_frente.add(atlas.findRegion(Constantes.BOXEADORA_JESSI_FRENTE_GANCHO_IZQUIERDA_1));               
            gancho_izq_frente.add(atlas.findRegion(Constantes.BOXEADORA_JESSI_FRENTE_PIVOTAR_0));
            gancho_izquierda_frente=new Animation(Constantes.GANCHO_LOOP,gancho_izq_frente,Animation.PlayMode.LOOP);
            
            Array<AtlasRegion> gancho_dcha_frente = new Array<AtlasRegion>();            
            gancho_dcha_frente.add(atlas.findRegion(Constantes.BOXEADORA_JESSI_FRENTE_GANCHO_DERECHA_0));
            gancho_dcha_frente.add(atlas.findRegion(Constantes.BOXEADORA_JESSI_FRENTE_GANCHO_DERECHA_1));              
            gancho_dcha_frente.add(atlas.findRegion(Constantes.BOXEADORA_JESSI_FRENTE_PIVOTAR_0));
            gancho_derecha_frente=new Animation(Constantes.GANCHO_LOOP,gancho_dcha_frente,Animation.PlayMode.LOOP);
            
            Array<AtlasRegion> accion_bloquear_frente = new Array<AtlasRegion>();            
            accion_bloquear_frente.add(atlas.findRegion(Constantes.BOXEADORA_JESSI_FRENTE_PIVOTAR_0));
            accion_bloquear_frente.add(atlas.findRegion(Constantes.BOXEADORA_JESSI_FRENTE_BLOQUEO));
            accion_bloquear_frente.add(atlas.findRegion(Constantes.BOXEADORA_JESSI_FRENTE_BLOQUEO));
            accion_bloquear_frente.add(atlas.findRegion(Constantes.BOXEADORA_JESSI_FRENTE_PIVOTAR_0));            
            bloqueo_frente=new Animation(Constantes.BLOQUEO_LOOP,accion_bloquear_frente,Animation.PlayMode.LOOP);
            
            Array<AtlasRegion> accion_esquivar_derecha_frente = new Array<AtlasRegion>();            
            accion_esquivar_derecha_frente.add(atlas.findRegion(Constantes.BOXEADORA_JESSI_FRENTE_ESQUIVAR_DERECHA));
            accion_esquivar_derecha_frente.add(atlas.findRegion(Constantes.BOXEADORA_JESSI_FRENTE_ESQUIVAR_DERECHA));            
            accion_esquivar_derecha_frente.add(atlas.findRegion(Constantes.BOXEADORA_JESSI_FRENTE_PIVOTAR_0));            
            esquivar_derecha_frente=new Animation(Constantes.ESQUIVAR_LOOP,accion_esquivar_derecha_frente,Animation.PlayMode.LOOP);
            
            Array<AtlasRegion> accion_esquivar_izquierda_frente = new Array<AtlasRegion>();            
            accion_esquivar_izquierda_frente.add(atlas.findRegion(Constantes.BOXEADORA_JESSI_FRENTE_ESQUIVAR_IZQUIERDA));    
            accion_esquivar_izquierda_frente.add(atlas.findRegion(Constantes.BOXEADORA_JESSI_FRENTE_ESQUIVAR_IZQUIERDA));    
            accion_esquivar_izquierda_frente.add(atlas.findRegion(Constantes.BOXEADORA_JESSI_FRENTE_PIVOTAR_0));            
            esquivar_izquierda_frente=new Animation(Constantes.ESQUIVAR_LOOP,accion_esquivar_izquierda_frente,Animation.PlayMode.LOOP);
            
            Array<AtlasRegion> accion_golpeado_frente = new Array<AtlasRegion>();            
            accion_golpeado_frente.add(atlas.findRegion(Constantes.BOXEADORA_JESSI_FRENTE_PIVOTAR_0));
            accion_golpeado_frente.add(atlas.findRegion(Constantes.BOXEADORA_JESSI_FRENTE_GOLPEADO));                
            accion_golpeado_frente.add(atlas.findRegion(Constantes.BOXEADORA_JESSI_FRENTE_PIVOTAR_0));
            accion_golpeado_frente.add(atlas.findRegion(Constantes.BOXEADORA_JESSI_FRENTE_GOLPEADO));                
            golpeado_frente=new Animation(Constantes.GOLPEADO_LOOP,accion_golpeado_frente,Animation.PlayMode.LOOP);
            
            Array<AtlasRegion> accion_derrotado_frente = new Array<AtlasRegion>();
            accion_derrotado_frente.add(atlas.findRegion(Constantes.BOXEADORA_JESSI_FRENTE_DESCOMPONER_0));
            accion_derrotado_frente.add(atlas.findRegion(Constantes.BOXEADORA_JESSI_FRENTE_DESCOMPONER_1));
            accion_derrotado_frente.add(atlas.findRegion(Constantes.BOXEADORA_JESSI_FRENTE_DESCOMPONER_2));
            accion_derrotado_frente.add(atlas.findRegion(Constantes.BOXEADORA_JESSI_FRENTE_DESCOMPONER_3));
            accion_derrotado_frente.add(atlas.findRegion(Constantes.BOXEADORA_JESSI_FRENTE_DESCOMPONER_4));
            accion_derrotado_frente.add(atlas.findRegion(Constantes.BOXEADORA_JESSI_FRENTE_DESCOMPONER_5));
            accion_derrotado_frente.add(atlas.findRegion(Constantes.BOXEADORA_JESSI_FRENTE_DESCOMPONER_6));
            derrotado_frente = new Animation(Constantes.DERROTADO,accion_derrotado_frente,Animation.PlayMode.NORMAL);
            
            //------------------------------------------ PERSONAJE DE ESPALDA -------------------------------------------------------------
            
            Array<AtlasRegion> accion_pivotar_espalda = new Array<AtlasRegion>();            
            accion_pivotar_espalda.add(atlas.findRegion(Constantes.BOXEADORA_JESSI_ESPALDA_PIVOTAR_0));
            accion_pivotar_espalda.add(atlas.findRegion(Constantes.BOXEADORA_JESSI_ESPALDA_PIVOTAR_1));            
            pivotando_espalda=new Animation(Constantes.PIVOTEO_LOOP,accion_pivotar_espalda,Animation.PlayMode.LOOP);
            
            Array<AtlasRegion> directo_izq_espalda = new Array<AtlasRegion>();       
            
            directo_izq_espalda.add(atlas.findRegion(Constantes.BOXEADORA_JESSI_ESPALDA_DIRECTO_IZQUIERDA_0));
            directo_izq_espalda.add(atlas.findRegion(Constantes.BOXEADORA_JESSI_ESPALDA_DIRECTO_IZQUIERDA_1)); 
            directo_izq_espalda.add(atlas.findRegion(Constantes.BOXEADORA_JESSI_ESPALDA_DIRECTO_IZQUIERDA_0));
            directo_izq_espalda.add(atlas.findRegion(Constantes.BOXEADORA_JESSI_ESPALDA_PIVOTAR_0));
            directo_izquierda_espalda=new Animation(Constantes.DIRECTO_LOOP,directo_izq_espalda,Animation.PlayMode.LOOP);
            
            Array<AtlasRegion> directo_dcha_espalda = new Array<AtlasRegion>();   
            
            directo_dcha_espalda.add(atlas.findRegion(Constantes.BOXEADORA_JESSI_ESPALDA_DIRECTO_DERECHA_0));
            directo_dcha_espalda.add(atlas.findRegion(Constantes.BOXEADORA_JESSI_ESPALDA_DIRECTO_DERECHA_1));  
            directo_dcha_espalda.add(atlas.findRegion(Constantes.BOXEADORA_JESSI_ESPALDA_DIRECTO_DERECHA_0));
            directo_dcha_espalda.add(atlas.findRegion(Constantes.BOXEADORA_JESSI_ESPALDA_PIVOTAR_0));
            directo_derecha_espalda=new Animation(Constantes.DIRECTO_LOOP,directo_dcha_espalda,Animation.PlayMode.LOOP);
            
            Array<AtlasRegion> gancho_izq_espalda = new Array<AtlasRegion>();  
            
            gancho_izq_espalda.add(atlas.findRegion(Constantes.BOXEADORA_JESSI_ESPALDA_GANCHO_IZQUIERDA_0));
            gancho_izq_espalda.add(atlas.findRegion(Constantes.BOXEADORA_JESSI_ESPALDA_GANCHO_IZQUIERDA_1));
            
            gancho_izq_espalda.add(atlas.findRegion(Constantes.BOXEADORA_JESSI_ESPALDA_PIVOTAR_0));            
            gancho_izquierda_espalda=new Animation(Constantes.GANCHO_LOOP,gancho_izq_espalda,Animation.PlayMode.LOOP);
            
            Array<AtlasRegion> gancho_dcha_espalda = new Array<AtlasRegion>();   
            
            gancho_dcha_espalda.add(atlas.findRegion(Constantes.BOXEADORA_JESSI_ESPALDA_GANCHO_DERECHA_0));
            gancho_dcha_espalda.add(atlas.findRegion(Constantes.BOXEADORA_JESSI_ESPALDA_GANCHO_DERECHA_1));
            
            gancho_dcha_espalda.add(atlas.findRegion(Constantes.BOXEADORA_JESSI_ESPALDA_PIVOTAR_0));
            gancho_derecha_espalda=new Animation(Constantes.GANCHO_LOOP,gancho_dcha_espalda,Animation.PlayMode.LOOP);
            
            Array<AtlasRegion> accion_bloquear_espalda = new Array<AtlasRegion>();            
            accion_bloquear_espalda.add(atlas.findRegion(Constantes.BOXEADORA_JESSI_ESPALDA_PIVOTAR_0));
            accion_bloquear_espalda.add(atlas.findRegion(Constantes.BOXEADORA_JESSI_ESPALDA_BLOQUEO));
            accion_bloquear_espalda.add(atlas.findRegion(Constantes.BOXEADORA_JESSI_ESPALDA_BLOQUEO));
            accion_bloquear_espalda.add(atlas.findRegion(Constantes.BOXEADORA_JESSI_ESPALDA_PIVOTAR_0));            
            bloqueo_espalda=new Animation(Constantes.BLOQUEO_LOOP,accion_bloquear_espalda,Animation.PlayMode.LOOP);
            
            Array<AtlasRegion> accion_esquivar_derecha_espalda = new Array<AtlasRegion>();            
            accion_esquivar_derecha_espalda.add(atlas.findRegion(Constantes.BOXEADORA_JESSI_ESPALDA_PIVOTAR_0));
            accion_esquivar_derecha_espalda.add(atlas.findRegion(Constantes.BOXEADORA_JESSI_ESPALDA_ESQUIVAR_DERECHA));
            accion_esquivar_derecha_espalda.add(atlas.findRegion(Constantes.BOXEADORA_JESSI_ESPALDA_ESQUIVAR_DERECHA));            
            accion_esquivar_derecha_espalda.add(atlas.findRegion(Constantes.BOXEADORA_JESSI_ESPALDA_PIVOTAR_0));            
            esquivar_derecha_espalda=new Animation(Constantes.ESQUIVAR_LOOP,accion_esquivar_derecha_espalda,Animation.PlayMode.LOOP);
            
            Array<AtlasRegion> accion_esquivar_izquierda_espalda = new Array<AtlasRegion>();            
            accion_esquivar_izquierda_espalda.add(atlas.findRegion(Constantes.BOXEADORA_JESSI_ESPALDA_PIVOTAR_0));
            accion_esquivar_izquierda_espalda.add(atlas.findRegion(Constantes.BOXEADORA_JESSI_ESPALDA_ESQUIVAR_IZQUIERDA));    
            accion_esquivar_izquierda_espalda.add(atlas.findRegion(Constantes.BOXEADORA_JESSI_ESPALDA_ESQUIVAR_IZQUIERDA));    
            accion_esquivar_izquierda_espalda.add(atlas.findRegion(Constantes.BOXEADORA_JESSI_ESPALDA_PIVOTAR_0));            
            esquivar_izquierda_espalda=new Animation(Constantes.ESQUIVAR_LOOP,accion_esquivar_izquierda_espalda,Animation.PlayMode.LOOP);
            
            Array<AtlasRegion> accion_golpeado_espalda = new Array<AtlasRegion>();            
            accion_golpeado_espalda.add(atlas.findRegion(Constantes.BOXEADORA_JESSI_ESPALDA_PIVOTAR_0));
            accion_golpeado_espalda.add(atlas.findRegion(Constantes.BOXEADORA_JESSI_ESPALDA_GOLPEADO));                                   
            accion_golpeado_espalda.add(atlas.findRegion(Constantes.BOXEADORA_JESSI_ESPALDA_PIVOTAR_0));
            accion_golpeado_espalda.add(atlas.findRegion(Constantes.BOXEADORA_JESSI_ESPALDA_GOLPEADO));                                   
            golpeado_espalda=new Animation(Constantes.GOLPEADO_LOOP,accion_golpeado_espalda,Animation.PlayMode.LOOP);
            
            Array<AtlasRegion> accion_derrotado_espalda = new Array<AtlasRegion>();
            accion_derrotado_espalda.add(atlas.findRegion(Constantes.BOXEADORA_JESSI_ESPALDA_DESCOMPONER_0));
            accion_derrotado_espalda.add(atlas.findRegion(Constantes.BOXEADORA_JESSI_ESPALDA_DESCOMPONER_1));
            accion_derrotado_espalda.add(atlas.findRegion(Constantes.BOXEADORA_JESSI_ESPALDA_DESCOMPONER_2));
            accion_derrotado_espalda.add(atlas.findRegion(Constantes.BOXEADORA_JESSI_ESPALDA_DESCOMPONER_3));
            accion_derrotado_espalda.add(atlas.findRegion(Constantes.BOXEADORA_JESSI_ESPALDA_DESCOMPONER_4));
            accion_derrotado_espalda.add(atlas.findRegion(Constantes.BOXEADORA_JESSI_ESPALDA_DESCOMPONER_5));
            derrotado_espalda = new Animation(Constantes.DERROTADO,accion_derrotado_espalda,Animation.PlayMode.NORMAL);
            
            
        }
    }
    
    public class AssetsSonido{
        public final Sound voz_buzz;        
        public final Sound voz_negro;
        public final Sound voz_jessi;
        public final Sound voz_kate;        
        
        public final Sound click_boton;
        public final Sound empezar_asalto;
        public final Sound empezar_combate;
        public final Sound ganar_combate;
        public final Sound perder_combate;
        public final Sound golpeo_uno;
        public final Sound golpeo_dos;
        
        public final Music musica_inicio;
        public final Music musica_combate;
        
        public AssetsSonido() {
            voz_buzz = Gdx.audio.newSound(Gdx.files.internal(Constantes.VOZ_BUZZ));
           //voz_john = Gdx.audio.newSound(Gdx.files.internal(Constantes.VOZ_JOHN));
            voz_negro = Gdx.audio.newSound(Gdx.files.internal(Constantes.VOZ_NEGRO));
            voz_jessi = Gdx.audio.newSound(Gdx.files.internal(Constantes.VOZ_JESSI));
            voz_kate = Gdx.audio.newSound(Gdx.files.internal(Constantes.VOZ_KATE));
           // voz_cecilia = Gdx.audio.newSound(Gdx.files.internal(Constantes.VOZ_CECILIA));
            
            click_boton = Gdx.audio.newSound(Gdx.files.internal(Constantes.SONIDO_BOTON));
            empezar_asalto = Gdx.audio.newSound(Gdx.files.internal(Constantes.SONIDO_EMPEZAR_ASALTO));
            empezar_combate = Gdx.audio.newSound(Gdx.files.internal(Constantes.SONIDO_EMPEZAR_COMBATE));
            ganar_combate = Gdx.audio.newSound(Gdx.files.internal(Constantes.SONIDO_KO));
            perder_combate = Gdx.audio.newSound(Gdx.files.internal(Constantes.SONIDO_PERDER_COMBATE));
            golpeo_uno = Gdx.audio.newSound(Gdx.files.internal(Constantes.SONIDO_GOLPEO_UNO));
            golpeo_dos = Gdx.audio.newSound(Gdx.files.internal(Constantes.SONIDO_GOLPEO_DOS));
            
            musica_inicio = Gdx.audio.newMusic(Gdx.files.internal(Constantes.MUSICA_INICIO));
            musica_combate = Gdx.audio.newMusic(Gdx.files.internal(Constantes.MUSICA_COMBATE));
        }
        
    }
}
