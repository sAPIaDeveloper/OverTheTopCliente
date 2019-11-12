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
public class Codigos_Escritorio {
    
    public enum Codigo{
        ERROR,
        REGISTRO_USUARIO_COMPLETADO,
        INICIAR_SESION_ACEPTADO,
        REGISTRO_BOXEADOR_COMPLETADO,
        MOVIL_EMPAREJADO,
        DAR_CODIGO_EMPAREJAMIENTO,
        OPONENTE_ENCONTRADO,
        COMPETICION_CREADA,
        BOXEADOR_MODIFICADO,
        BOXEADOR_BORRADO,
        GOLPE_SACO,
        ASALTO_TERMINADO,
        COMBATE_TERMINADO,
        ACTUALIZAR_ACCIONES,
        ESTADISTICAS_BOXEADOR,
        COMPETICIONES_PARA_APUNTARSE,
        BOXEADOR_APUNTADO_EXITO
    }
    
    public static Codigo codigo_escritorio(int num){
        Codigo[] cod = Codigo.values();
        return cod[num];
    }
}
