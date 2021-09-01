/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Entity;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.io.Serializable;

/**
 *
 * @author K7
 */
public class Player implements Serializable{
	public int x,y,id;
	public String name;
	
	public Player(String name){
		this.name=name;
		this.x=50;
		this.y=50;
	}
	
	public void render(Graphics g){
		g.setColor(Color.PINK);
		g.fillRect(x, y, 80, 80);
		g.setColor(Color.BLUE);
		g.setFont(new Font("Arial",1,20));
		g.drawString(name, x+10, y+40);
	}
	
	public void UpdatePosition(int x, int y){
		this.x=x;
		this.y=y;
	}

	@Override
	public String toString() {
		return "Player{" + "x=" + x + ", y=" + y + ", id=" + id + ", name=" + name + '}';
	}
}
