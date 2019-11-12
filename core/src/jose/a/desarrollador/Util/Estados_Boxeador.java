/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package jose.a.desarrollador.Util;

/**
 *
 * @author josea
 */
public class Estados_Boxeador {
     public enum ESTADOS{
        PIVOTANDO,
        BLOQUEO,
        DIRECTO_DERECHA,
        DIRECTO_IZQUIERDA,
        GANCHO_IZQUIERDA,
        GANCHO_DERECHA,
        ESQUIVAR_IZQUIERDA,
        ESQUIVAR_DERECHA,
        GOLPEADO,
        DERROTADO
        
    }
     
    public static ESTADOS codigo_servidor(int num){
        ESTADOS[] cod = ESTADOS.values();
        return cod[num];
    }
}
