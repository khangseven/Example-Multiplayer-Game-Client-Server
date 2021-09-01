/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Server;

import Packet.Packet;
import Packet.UpdateFromServer;
import java.io.IOException;
import java.net.Socket;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.logging.Level;
import java.util.logging.Logger;

public class ServerThread extends Thread {
	
	public int id;
	Server server;
	Socket socket;
	ObjectOutputStream output=null;
	ObjectInputStream input=null;
	
	
	public ServerThread(int id, Socket socket, Server server){
		try {
			this.id=id;
			this.socket=socket;
			this.server =server;
			
			output=new ObjectOutputStream(socket.getOutputStream());
			input=new ObjectInputStream(socket.getInputStream());
			
			this.start();
		} catch (IOException ex) {
			Logger.getLogger(ServerThread.class.getName()).log(Level.SEVERE, null, ex);
		}
	}

	@Override
	public void run() {
		while (true) {			
			try {
				Packet packet = (Packet) input.readObject();
				server.PacketHandle(packet,this);
			} catch (IOException | ClassNotFoundException ex) {
				Logger.getLogger(ServerThread.class.getName()).log(Level.SEVERE, null, ex);
			}
		}
	}
	
	public void send(Packet packet){
		try {
			if(packet instanceof UpdateFromServer){
				output.reset();
				output.writeUnshared(packet);
				output.flush();
				return;
			}
			output.writeObject(packet);
			output.flush();
		} catch (IOException ex) {
			Logger.getLogger(ServerThread.class.getName()).log(Level.SEVERE, null, ex);
		}
	}
}
