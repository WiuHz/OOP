package entity;

import java.lang.Math;

import java.awt.Graphics2D;
import java.awt.Rectangle;
import java.awt.image.BufferedImage;
import java.io.IOException;

import javax.imageio.ImageIO;

import main.GamePanel;
import main.KeyHandler;

public class Player extends Entity{
	
	GamePanel gp;
	KeyHandler keyH;
	
	public final int screenX;
	public final int screenY;
	
	public Player (GamePanel gp, KeyHandler keyH)  {
		
		this.gp = gp;
		this.keyH = keyH;
		
		screenX = gp.screenWidth/2 - (gp.tileSize/2);
		screenY = gp.screenHeight/2 - (gp.tileSize/2);

		solidArea = new Rectangle(0, 0, 96, 96);
		solidArea.x = 16;
		solidArea.x = 32;
		solidArea.width = 64;
		solidArea.height = 64;

		
		
		setDefaultValues();
		getPlayerImage();

	}
	public void setDefaultValues()  {
		
		worldX = gp.tileSize * 3;
		worldY = gp.tileSize * 3;
		speed = 4;
		direction = "left";
		
	}
	public void getPlayerImage()  {
		
		try {
			
			up1 = ImageIO.read(getClass().getResourceAsStream("/player/sorceress_up_1.png"));
			up2 = ImageIO.read(getClass().getResourceAsStream("/player/sorceress_up_2.png"));
			down1 = ImageIO.read(getClass().getResourceAsStream("/player/sorceress_down_1.png"));
			down2 = ImageIO.read(getClass().getResourceAsStream("/player/sorceress_down_2.png"));
			left1 = ImageIO.read(getClass().getResourceAsStream("/player/sorceress_left_1.png"));
			left2 = ImageIO.read(getClass().getResourceAsStream("/player/sorceress_left_2.png"));
			right1 = ImageIO.read(getClass().getResourceAsStream("/player/sorceress_right_1.png"));
			right2 = ImageIO.read(getClass().getResourceAsStream("/player/sorceress_right_2.png"));
			
			
		}catch(IOException e)  {
			e.printStackTrace();
		}
	}
	public void update()  {
		boolean isStanding = true;

		// Moving in one direction
		if(keyH.upPressed == true)  {
			direction = "up";
			isStanding = false;
		}
		if(keyH.downPressed == true)  {
			direction = "down";
			isStanding = false;
		}
		if(keyH.leftPressed == true)  {
			direction = "left";
			isStanding = false;
		}
		if(keyH.rightPressed == true)  {
			direction = "right";
			isStanding = false;
		}


		// check tile collision
		collisionOn = false;
		gp.cChecker.checkTile(this);

		if (!collisionOn && !isStanding) {
			switch (direction) {
				case "upleft":
					worldY -= speed/Math.sqrt(2);
					worldX -= speed/Math.sqrt(2);
					break;
				case "upright":
					worldY -= speed/Math.sqrt(2);
					worldX += speed/Math.sqrt(2);
					break;
				case "downleft":
					worldY += speed/Math.sqrt(2);
					worldX -= speed/Math.sqrt(2);
					break;
				case "downright":
					worldY += speed/Math.sqrt(2);
					worldX += speed/Math.sqrt(2);
					break;
				case "up": 
					worldY -= speed; 
					break;
				case "down": 
					worldY += speed; 
					break;
				case "left": 
					worldX -= speed; 
					break;
				case "right": 
					worldX += speed; 
					break;
			}
		}
		
	}
	public void draw(Graphics2D g2)  {
		
		BufferedImage image = null;
		
		switch(direction)  {
		case "up":
			image = up1;
			break;
		case "down":
			image = down1;
			break;
		case "left":
			image = left1;
			break;
		case "right":
			image = right1;
			break;
			
		}
		g2.drawImage(image, screenX, screenY, gp.tileSize, gp.tileSize, null);

		
	}
}
