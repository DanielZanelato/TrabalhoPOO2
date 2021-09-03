package com.DeFcompany.entities;


import java.awt.Color;
import java.awt.Graphics;
import java.awt.image.BufferedImage;

import com.DeFcompany.main.Game;
import com.DeFcompany.world.Camera;
import com.DeFcompany.world.World;

public class Player extends Entity {
	
	public boolean right, up, left, down;
	
	public static int seg = 0;
	public int maxseg = 30;
	
	public BufferedImage sprite_left;
	
	public static boolean win = false;
	public static boolean lose = false;
	
	public int lastDir = 1;
	
	public Player(int x, int y, int width, int height,double speed,  BufferedImage sprite) {
		super(x, y, width, height,speed, sprite); 
		sprite_left = Game.spritesheet.getSprite(48, 0, 16, 16);
		
	}
	public void tick() {
		depth = 1;
		if(right && World.isFree((int)(x+speed),this.getY())){
			x += speed;
			lastDir = 1;
			
		}else if(left && World.isFree((int)(x-speed),this.getY())) {
			x -= speed;
			lastDir = -1;
		}
		if(up && World.isFree(this.getX(), (int)(y-speed))) {
	
			y -= speed;
		}else if(down && World.isFree(this.getX(),(int)(y+speed))) {
			y += speed;
		}
		Giveapple();
		
		if(Game.fc == Game.fa) {
			//vencedor!!!!!
			win = true;
			
		}
	}
	
	public void Giveapple() {
		for(int i = 0; i < Game.entities.size(); i++) {
			Entity current = Game.entities.get(i);
			if(current instanceof Fruta) {
				if(Entity.isColidding(this, current)) {
					Game.fa++;
					Game.entities.remove(i);
					return;
				}
			}
		}
	}
	
	public void render(Graphics g) {
		if(lastDir == 1) {
			super.render(g);
		}else {
			g.drawImage(sprite_left,this.getX() - Camera.x,this.getY() - Camera.y,null);
		}
		if(win == true) {
			seg++;
			if(seg > maxseg) {
			g.setColor(Color.black);
			g.fillRect(0, 0, Game.width, Game.height);
			g.setColor(Color.red);
			g.drawString("PARABENS VOCE GANHOU!!", 40, 100);
			g.drawString("PRESSIONE ENTER PARA REINICIAR", 15, 130);
			}
		}else if(lose == true) {
			g.setColor(Color.black);
			g.fillRect(0, 0, Game.width, Game.height);
			g.setColor(Color.red);
			g.drawString("VOCE PERDEU", 80, 100);
			g.drawString("PRESSIONE ENTER PARA REINICIAR", 15, 130);

		}
	}
}
