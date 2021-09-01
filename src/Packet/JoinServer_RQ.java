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
public class JoinServer_RQ implements Serializable, Packet{
	public String name;

	public JoinServer_RQ(String name) {
		this.name = name;
	}

	public JoinServer_RQ() {
		throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
	}
	
}
