/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Packet;

import Entity.Player;
import java.io.Serializable;
import java.util.ArrayList;

/**
 *
 * @author K7
 */
public class UpdateFromServer implements Serializable, Packet{
	public ArrayList<Player> Players;

	public UpdateFromServer(ArrayList<Player> Players) {
		Players.forEach((p)->{System.out.println(p.toString());});
		this.Players = Players;
	}
	
}
