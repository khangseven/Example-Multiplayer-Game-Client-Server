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
public class JoinServer_RS implements Serializable, Packet {
	public boolean Accept;
	public int id;

	public JoinServer_RS(boolean Accept, int id) {
		this.Accept = Accept;
		this.id = id;
	}
	
}
