
package com.DeFcompany.main;

	import java.awt.Canvas;
	import java.awt.Color;
import java.awt.Dimension;

import java.awt.Graphics;

import java.awt.Image;

import java.awt.Toolkit;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;
import java.awt.image.BufferStrategy;
	import java.awt.image.BufferedImage;

import java.lang.Thread;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;



import javax.swing.JFrame;


import com.DeFcompany.entities.Entity;
import com.DeFcompany.entities.Player;
import com.DeFcompany.graficos.Spritesheet;
import com.DeFcompany.graficos.UI;

import com.DeFcompany.world.World;


	public class Game extends Canvas implements Runnable, KeyListener, MouseListener, MouseMotionListener{
		
		
		
		
		private static final long serialVersionUID = 1L;
			public static JFrame frame;
			private Thread thread;
			private boolean isRunning = true;
			public static final int width = 240;
			public static int height = 240;
			public static int scale = 2;
			
			
			private BufferedImage image;
			
			
			
			public static List<Entity> entities;
			public static Spritesheet spritesheet;
			public static World world;
			public static Player player;
			
			public UI ui;
			
			
			public static int fc = 0;
			
			public static int  fa = 0;
			
			
			
			public boolean saveGame = false;
			
			
			public Game() {
				addKeyListener(this);
				addMouseListener(this);
				addMouseMotionListener(this);
				//setPreferredSize(new Dimension(Toolkit.getDefaultToolkit().getScreenSize()));
				this.setPreferredSize(new Dimension(width*scale, height*scale));
				initFrame();
				image = new BufferedImage(width, height, BufferedImage.TYPE_INT_RGB);
				
				spritesheet = new Spritesheet("/spritesheet.png"); 
				entities = new ArrayList<Entity>();
				player = new Player(0, 0 , 16, 16,1,  spritesheet.getSprite(32,0,16,16));
				world = new World("/level1.png");
				ui = new UI();
				
				
				
				
				entities.add(player);
			}
			
			public void initFrame() {
				frame = new JFrame("Pac-Man");
				frame.add(this);
				//frame.setUndecorated(true);
				frame.setResizable(false);
				frame.pack();
				frame.setLocationRelativeTo(null);
				frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
				frame.setVisible(true);
				//icone
				//Image imagem = null;
				/*try {
					imagem = ImageIO.read(getClass().getResource("/icon.png"));
				}catch(IOException e) {
					e.printStackTrace();
				}*/
				Toolkit toolkit = Toolkit.getDefaultToolkit();
				Image image = toolkit.getImage(getClass().getResource("/icon.png"));
				//Cursor c = toolkit.createCustomCursor(image, new Point(0,0), "img");
				//frame.setCursor(c);
				frame.setIconImage(image);
				frame.setAlwaysOnTop(true);
				frame.setLocationRelativeTo(null);
				frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
				frame.setVisible(true);
				
				
			}
			
			public synchronized void start() {
				thread = new Thread(this);
				isRunning = true;
				thread.start();
			}	
			
			public synchronized void stop() {
				isRunning = false;
				try {
					thread.join();
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
			}
			
			public static void main(String[] args){
				Game game = new Game();
				game.start();
			}
			
			public void tick() {
				 
				for(int i = 0; i < entities.size(); i++) {
					Entity e = entities.get(i);
					e.tick();
					
				}
					
			}
			
			
			
			
			public void render() {
				BufferStrategy bs = this.getBufferStrategy();
				if(bs == null) {
					this.createBufferStrategy(3);
					return;
				}
				
				Graphics g = image.getGraphics();
				g.setColor(new Color(0, 0, 0));
				g.fillRect(0, 0, width, height);
				//Graphics2D g2 = (Graphics2D) g;
				world.render(g);
				Collections.sort(entities,Entity.nodeSorter);
				for(int i = 0; i < entities.size(); i++) {
					Entity e = entities.get(i);
					e.render(g);	
				}
				
				g.dispose();
				g = bs.getDrawGraphics();
				//drawRE(xx,yy);
				g.drawImage(image, 0, 0, /*Toolkit.getDefaultToolkit().getScreenSize().*/width*scale, /*Toolkit.getDefaultToolkit().getScreenSize().*/height*scale, null);
				ui.render(g);
				bs.show();
			}
			
			public void run() {
				requestFocus();
				long lastTime = System.nanoTime();
				double amountOfTicks = 60.0;
				double ns = 1000000000 / amountOfTicks;
				double delta = 0;
				double timer = System.currentTimeMillis();
				int frames = 0;
				while(isRunning) {
					long now = System.nanoTime();
					delta += (now - lastTime) / ns;
					lastTime = now;
					if (delta >= 1) {
						tick();
						render();
						frames ++;
						delta --;
					}
					if(System.currentTimeMillis() - timer >= 1000) {
						System.out.println("FPS: "+frames);
						frames = 0;
						timer += 1000;
					}
				}
				stop(); 
			}

			@Override
			public void keyPressed(KeyEvent e) {
				if(e.getKeyCode() == KeyEvent.VK_RIGHT ||
						e.getKeyCode() == KeyEvent.VK_D){
					player.right = true;
				}else if(e.getKeyCode() == KeyEvent.VK_LEFT ||
						e.getKeyCode() == KeyEvent.VK_A) {
					player.left = true;
				}
				if(e.getKeyCode() == KeyEvent.VK_UP ||
						e.getKeyCode() == KeyEvent.VK_W) {
					player.up = true;
				}else if(e.getKeyCode() == KeyEvent.VK_DOWN ||
						e.getKeyCode() == KeyEvent.VK_S) {
					player.down = true;
				}
				if(e.getKeyCode() == KeyEvent.VK_ENTER) {
					if(Player.win == true || Player.lose == true) {
						World.restartGame();
						Player.win = false;
						Player.lose = false;
						Player.seg = 0;
					}
				}

			}

			@Override
			public void keyReleased(KeyEvent e) {
				if(e.getKeyCode() == KeyEvent.VK_RIGHT ||
						e.getKeyCode() == KeyEvent.VK_D){
					player.right = false;
				}else if(e.getKeyCode() == KeyEvent.VK_LEFT ||
						e.getKeyCode() == KeyEvent.VK_A) {
					player.left = false;
				}
				if(e.getKeyCode() == KeyEvent.VK_UP ||
						e.getKeyCode() == KeyEvent.VK_W) {
					player.up = false;
				}else if(e.getKeyCode() == KeyEvent.VK_DOWN ||
						e.getKeyCode() == KeyEvent.VK_S) {
					player.down = false;
				}	
			}

			@Override
			public void keyTyped(KeyEvent e) {
				
				
			}

			@Override
			public void mouseClicked(MouseEvent arg0) {
				// TODO Auto-generated method stub
				
			}

			@Override
			public void mouseEntered(MouseEvent arg0) {
				// TODO Auto-generated method stub
				
			}

			@Override
			public void mouseExited(MouseEvent arg0) {
				// TODO Auto-generated method stub
				
			}

			@Override
			public void mousePressed(MouseEvent e) {
				
			}

			@Override
			public void mouseReleased(MouseEvent arg0) {
				// TODO Auto-generated method stub
				
			}

			@Override
			public void mouseDragged(MouseEvent arg0) {
				// TODO Auto-generated method stub
				
			}

			@Override
			public void mouseMoved(MouseEvent e) {
			}
		 
				
		}



