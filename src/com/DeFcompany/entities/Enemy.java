package com.DeFcompany.entities;



import java.awt.Graphics;
import java.awt.Rectangle;
import java.awt.image.BufferedImage;
import java.util.Random;

import com.DeFcompany.main.Game;
import com.DeFcompany.world.AStar;
import com.DeFcompany.world.Camera;
import com.DeFcompany.world.Vector2i;



public class Enemy extends Entity{
	
	
	
	public boolean ghostMode = false;
	public int ghostFrames = 0;
	public int nextTime = Entity.rand.nextInt(60*5-60*3)+60*3;

	public Enemy(int x, int y, int width, int height,int speed, BufferedImage sprite) {
		super(x, y, width, height, speed, sprite);
	}
	
	public void tick() {
		
		
		
		depth = 0;
		if(ghostMode == false) {
		
				if(path == null || path.size() == 0) {
					Vector2i start = new Vector2i((int)(x/16),(int)(y/16));
					Vector2i end = new Vector2i((int)(Game.player.x/16),(int)(Game.player.y/16));
					path = AStar.findPath(Game.world, start, end);
				}
				if(new Random().nextInt(100)<70)
					followPath(path);
				if(!colid()) {
				if(x % 16 == 0 && y % 16 == 0) {
					if(new Random().nextInt(100)<10) {
						Vector2i start = new Vector2i(((int)(x/16)),((int)(y/16)));
						Vector2i end = new Vector2i(((int)(Game.player.x/16)),((int)(Game.player.y/16)));
						path = AStar.findPath(Game.world, start, end);
					}
				}
				}else {
					Player.lose = true;
				}
		}
		
				
				ghostFrames++;
				if(ghostFrames == 60*4) 
				{
					nextTime = Entity.rand.nextInt(60*5 - 60*3)+60*3;
					ghostFrames = 0;
					if(ghostMode == false){
						ghostMode = true;
					}else {
						ghostMode = false;
					}
				}
	}
	
	public boolean colid() {
		Rectangle enemyCurrent = new Rectangle(this.getX(), this.getY(), width, height);
		Rectangle player = new Rectangle(Game.player.getX(),Game.player.getY(),16,16);
		
		return enemyCurrent.intersects(player);
	}
		
		
		
	public void render(Graphics g) {
		if(ghostMode == false) {
		super.render(g);	
		}else {
			g.drawImage(Entity.ENENY_GHOST,this.getX() - Camera.x,this.getY() - Camera.y,null);
		}
	 }
	
}
		
	

