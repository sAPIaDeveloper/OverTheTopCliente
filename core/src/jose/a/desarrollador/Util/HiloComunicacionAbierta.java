/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package jose.a.desarrollador.Util;

import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.Socket;

/**
 *
 * @author josea
 */
public class HiloComunicacionAbierta extends Thread{
    String usuario;
  

    public HiloComunicacionAbierta(String usuario) {
        this.usuario = usuario;   
    }

    @Override
    public void run() {
        try {
            String mensaje = "17&"+usuario;
            DatagramSocket socketD = new DatagramSocket();
            byte[] mesg=mensaje.getBytes();
            while(true){
         
                InetAddress address = InetAddress.getByName(Constantes.IP);// Creo un objeto InetAddress con la ip
                DatagramPacket packetToComunication = new DatagramPacket(mesg, mesg.length, address, Constantes.PUERTO); // Creo el paquete con la información
                socketD.send(packetToComunication);// Envio el paquete.
                Thread.sleep(30000);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        
    }
    
    
}
