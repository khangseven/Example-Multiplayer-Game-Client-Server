/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Client;

import Entity.Player;
import Packet.JoinServer_RQ;
import Packet.JoinServer_RS;
import Packet.Packet;
import Packet.UpdateFromServer;
import Packet.UpdateToServer;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.io.IOException;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import java.io.ObjectInputStream;
import  java.io.ObjectOutputStream;
import java.net.Socket;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;
/**
 *
 * @author K7
 */
public class GamePanel extends JPanel implements Runnable{
	
	Player player;
	ArrayList <Player> Players = new ArrayList<>();
	
	boolean running=false;
	
	boolean left=false;
	boolean down=false;
	boolean up=false;
	boolean right=false;
	
	//online 
	Socket socket;
	ObjectInputStream input=null;
	ObjectOutputStream output=null;
	
	public GamePanel(){
		this.setSize(800, 600);
		this.setLocation(0,0);
		this.setBackground(Color.GRAY);
		this.setFocusable(true);
		this.requestFocusInWindow();
		this.setVisible(true);
		GameInit();
	}
	
	void GameInit(){
		
		this.addKeyListener(new KeyAdapter(){
			@Override
			public void keyPressed(KeyEvent e) {
				KeyPressed(e);
			}

			@Override
			public void keyReleased(KeyEvent e) {
				KeyReleased(e);
			}
			
		});
		
		String name = JOptionPane.showInputDialog("what ur name?");
		player = new Player(name);
		
		running=true;
		
		//connect server
		Thread OnlineThread=new Thread(new Runnable(){
			@Override
			public void run() {
				try {
					socket=new Socket("localhost",3003);
					output=new ObjectOutputStream(socket.getOutputStream());
					input=new ObjectInputStream(socket.getInputStream());
					System.out.println("Connected");
					
					output.writeObject(new JoinServer_RQ(player.name));
					output.flush();
					System.out.println("Request to JOIN....");
					
					while(running){
						try {
							Packet packet = (Packet) input.readObject();
							PacketHandle(packet);
						} catch (ClassNotFoundException ex) {
							Logger.getLogger(GamePanel.class.getName()).log(Level.SEVERE, null, ex);
						}
					}
				} catch (IOException ex) {
					Logger.getLogger(GamePanel.class.getName()).log(Level.SEVERE, null, ex);
				}
			}
		});
		
		OnlineThread.start();
	}
	
	void PacketHandle(Packet packet){
		if(packet instanceof JoinServer_RS){
			JoinServer_RS temp =(JoinServer_RS) packet;
			if(temp.Accept){
				player.id=temp.id;
				new Thread(this).start();
				System.out.println("Joined! :)");
			}else{
				System.out.println("Cant JOIN :(");
			}
		}else if(packet instanceof UpdateFromServer){
			UpdateFromServer temp = (UpdateFromServer) packet;
			Players=temp.Players;
			Players.forEach((p)->{System.out.println(p.toString());});
			Players.remove(player.id);
		}
	}
	
	private void UpdateToServer(Packet packet) {
		try {
			output.writeObject(packet);
			output.flush();
		} catch (IOException ex) {
			Logger.getLogger(GamePanel.class.getName()).log(Level.SEVERE, null, ex);
		}
	}
	
	//Game session
	@Override
	public void run() {
		long last = System.nanoTime();
		int gameTick=128;
		double timeForTick = 1000000000/gameTick;
		double delta=0;
		long timer = System.currentTimeMillis();
		long now;
		int tickCount=0;
		while(running){
			now=System.nanoTime();
			delta+=(now-last)/timeForTick;
			last=now;
			if(delta >= 1){
				delta--;
				tick();
				repaint();
				tickCount++;
			}
			if(System.currentTimeMillis()-timer>1000){
				timer+=1000;
				//System.out.println("Tick: "+tickCount);
				tickCount=0;
			}
		}
	}
	
	int HorizontalMove=0;
	int VerticalMove=0;
	void tick(){
		if(left==right) HorizontalMove=0;
		else if(left) HorizontalMove=-1;
		else HorizontalMove=1;
		
		if(up==down) VerticalMove=0;
		else if(up) VerticalMove=-1;
		else VerticalMove=1;
		
		player.x+=5*HorizontalMove;
		player.y+=5*VerticalMove;
		
		UpdateToServer(new UpdateToServer(player.x,player.y));
	}

	@Override
	public void paint(Graphics g) {
		super.paint(g); //To change body of generated methods, choose Tools | Templates.
		render(g);
	}
	void render(Graphics g){
		player.render(g);
		Players.forEach((p)->{
			p.render(g);
		});
	}

	//control init
	void KeyPressed(KeyEvent e){
		switch(e.getKeyCode()){
			case 65:
				left=true;
				break;
			case 68:
				right=true;
				break;
			case 83:
				down=true;
				break;
			case 87:
				up=true;
				break;
		}
	}
	void KeyReleased(KeyEvent e){
		switch(e.getKeyCode()){
			case 65:
				left=false;
				break;
			case 68:
				right=false;
				break;
			case 83:
				down=false;
				break;
			case 87:
				up=false;
				break;
		}
	}

	
}
