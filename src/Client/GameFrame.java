/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Client;

import java.awt.Color;
import javax.swing.JFrame;

/**
 *
 * @author K7
 */
public class GameFrame extends JFrame{
	public GameFrame(){
		this.setSize(800, 600);
		this.setLocationRelativeTo(null);
		this.setBackground(Color.BLACK);
		this.getContentPane().setBackground(Color.BLUE);
		this.setDefaultCloseOperation(this.EXIT_ON_CLOSE);

		this.add(new GamePanel());

		this.setVisible(true);
	}
}
