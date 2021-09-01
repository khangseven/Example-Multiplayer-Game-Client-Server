/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Server;

import Entity.Player;
import Packet.JoinServer_RQ;
import Packet.JoinServer_RS;
import Packet.Packet;
import Packet.UpdateFromServer;
import Packet.UpdateToServer;
import java.io.IOException;
import java.net.Socket;
import java.net.ServerSocket;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;

public class Server implements Runnable {

	static ServerSocket server;
	Socket socket;
	
	ArrayList<ServerThread> Clients=new ArrayList<>();
	ArrayList<Player> Players=new ArrayList<>();
	
	public static void main(String[] args) {
		try {
			server=new ServerSocket(3003);
			System.out.println("Start server OK");
			Thread listenThread = new Thread(new Server());
			listenThread.start();
		} catch (IOException ex) {
			Logger.getLogger(Server.class.getName()).log(Level.SEVERE, null, ex);
		}
	}

	int IDRender=0;
	@Override
	public void run() {
		System.out.println("Start Listening..");
		while(true){
			try {
				socket =server.accept();
				System.out.println("New Client Join: "+ IDRender+ "IP: "+socket.getLocalAddress().toString());
				Clients.add(new ServerThread(IDRender,socket,this));
				IDRender++;
			} catch (IOException ex) {
				Logger.getLogger(Server.class.getName()).log(Level.SEVERE, null, ex);
			}
		}
	}
	
	public void PacketHandle(Packet packet,ServerThread client){
		if(packet instanceof JoinServer_RQ){
			JoinServer_RQ temp =(JoinServer_RQ) packet;
			//dong y
			Players.add(new Player(temp.name));
			client.send(new JoinServer_RS(true,client.id));
		}else if(packet instanceof UpdateToServer){
			UpdateToServer temp =(UpdateToServer) packet;
			Players.get(client.id).UpdatePosition(temp.x, temp.y);
			client.send((Packet) new UpdateFromServer(Players));
		}
	}
	
}
