package main;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;

import javax.swing.JPanel;

import entity.Player;
import tile.TileManager;

public class GamePanel extends JPanel implements Runnable {

	// SCREEN SETTINGS
	final int originalTileSize = 32; // 16x16 size
	final int scale = 3;
	
	public final int tileSize = originalTileSize * scale; // 96x96 tile
	public final int maxScreenCol = 16;
	public final int maxScreenRow = 12;
	public final int screenWidth = tileSize * maxScreenCol; // 1152 pixels
	public final int screenHeight = tileSize * maxScreenRow; // 864 pixels
	
	// WORLD SETTINGS
	public final int maxWorldCol = 112;
	public final int maxWorldRow = 84;
	public final int WorldWidth = tileSize * maxWorldCol;
	public final int worldHeight = tileSize * maxWorldRow;

	TileManager tileM = new TileManager(this);
	KeyHandler keyH = new KeyHandler();
	Thread gameThread;
	public Player player = new Player(this,keyH);

	int FPS = 60;
	
	
	public GamePanel()  {
	
		
		this.setPreferredSize(new Dimension(screenWidth, screenHeight));
		this.setBackground(Color.black);
		this.setDoubleBuffered(true);
		this.addKeyListener(keyH);
		this.setFocusable(true);
		
	}
	
	
	public void startGameThread()  {
		
		gameThread = new Thread(this);
		gameThread.start();
	}

	public void run() {
		
		double drawInterval = 1000000000/FPS; 
		double nextDrawTime = System.nanoTime() + drawInterval;
		
		while(gameThread != null)  {
			
			update();
			
			repaint();
			
			try  {
				double remainingTime = (nextDrawTime - System.nanoTime()) / 1000000;
				if (remainingTime < 0)  {
					remainingTime = 0;
				}
					
				Thread.sleep((long) remainingTime);
				nextDrawTime += drawInterval;
				
			} catch (InterruptedException e)  {
				e.printStackTrace();
			}
			
			
		}
	}
	public void update()  {

		player.update();
		
	}
		
	public void paintComponent(Graphics g)  {
		
		super.paintComponent(g);
		
		Graphics2D g2 = (Graphics2D)g;
		
		tileM.draw(g2);
		
		player.draw(g2);
// tileM before player cause if opposite, background tiles will hide player character	
		
		g2.dispose();
		
	}
}	
	
