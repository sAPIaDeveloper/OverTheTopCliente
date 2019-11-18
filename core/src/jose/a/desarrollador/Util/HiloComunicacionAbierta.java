/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package jose.a.desarrollador.Util;

import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import jose.a.desarrollador.Pantallas.PantallaLoguin;
import jose.a.desarrollador.Principal;

/**
 *
 * @author josea
 */
public class HiloComunicacionAbierta extends Thread{
    String usuario;
    Preferencias pref;
    Principal principal;

    public HiloComunicacionAbierta(String usuario,Principal principal) {
        this.usuario = usuario;  
        pref = new Preferencias();
        this.principal = principal;
    }

    @Override
    public void run() {
        try {
            String mensaje = "17&"+usuario;
            DatagramSocket socketD = new DatagramSocket();
            byte[] mesg=mensaje.getBytes();
            while(true){
         
                InetAddress address = InetAddress.getByName(pref.getDireccion_ip());// Creo un objeto InetAddress con la ip
                DatagramPacket packetToComunication = new DatagramPacket(mesg, mesg.length, address, Constantes.PUERTO); // Creo el paquete con la información
                socketD.setSoTimeout(1000);
                socketD.send(packetToComunication);// Envio el paquete.
                byte[] bufIn = new byte[256]; 
                DatagramPacket paqueteEntrada = new DatagramPacket(bufIn, bufIn.length);            
                socketD.receive(paqueteEntrada);
                
                Thread.sleep(30000);
            }
        } catch (Exception e) {
            //avisar que no hay conexion
            
        }
        
    }
    
    
}
