package com.DeFcompany.graficos;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;

import com.DeFcompany.main.Game;


public class UI {

	public void render(Graphics g) {
		g.setColor(Color.white);
		g.setFont(new Font("arial",Font.BOLD,18));
		g.drawString("MAÇÃS: "+Game.fa+"/"+Game.fc, 30, 30);
	}
	
	
}
