/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Packet;

import java.io.Serializable;

/**
 *
 * @author K7
 */
public class UpdateToServer implements Serializable, Packet{
	public int x,y;

	public UpdateToServer(int x, int y) {
		this.x = x;
		this.y = y;
	}
	
}
