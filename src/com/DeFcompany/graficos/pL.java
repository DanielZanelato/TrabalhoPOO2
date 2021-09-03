package com.DeFcompany.graficos;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;

import com.DeFcompany.main.Game;

public class pL {
	public void render(Graphics g) {
	g.setFont(new Font("arial",Font.BOLD, 30));
	g.setColor(Color.BLACK);
	g.drawString("PROXIMO LEVEL",120,100);
}

	public void remove(Game game) {
		// TODO Auto-generated method stub
		
	}
}