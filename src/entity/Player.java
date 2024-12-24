package entity;

import java.awt.Graphics2D;
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
		if(keyH.upPressed == true)  {
			direction = "up";
			worldY -= speed;
		}
		if(keyH.downPressed == true)  {
			direction = "down";
			worldY += speed;
		}
		if(keyH.leftPressed == true)  {
			direction = "left";
			worldX -= speed;
		}
		if(keyH.rightPressed == true)  {
			direction = "right";
			worldX += speed;;
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
