package tile;

import java.awt.Graphics2D;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

import javax.imageio.ImageIO;

import main.GamePanel;

public class TileManager {
	
	GamePanel gp;
	public Tile[]  tile;
	public int mapTileNum[][];
	
	public TileManager(GamePanel gp)  {
		
		this.gp = gp;
		
		tile = new Tile[10];
		mapTileNum = new int[gp.maxWorldCol][gp.maxWorldRow];
		
		getTileImage();
		loadMap("/maps/world01.txt");
		
// if i want to load another map, just need to call this loadMap and type the path
	}
		
	public void	getTileImage()  {
		
		try {
			
			tile[0] = new Tile();
			tile[0].image = ImageIO.read(getClass().getResourceAsStream("/tiles/grass.png"));
			
			tile[1] = new Tile();
			tile[1].image = ImageIO.read(getClass().getResourceAsStream("/tiles/stone.png"));
			tile[1].collision = true;

			tile[2] = new Tile();
			tile[2].image = ImageIO.read(getClass().getResourceAsStream("/tiles/water.png"));
			tile[2].collision = true;

			tile[3] = new Tile();
			tile[3].image = ImageIO.read(getClass().getResourceAsStream("/tiles/dirt.png"));
			
			tile[4] = new Tile();
			tile[4].image = ImageIO.read(getClass().getResourceAsStream("/tiles/sand.png"));
			
		}catch(IOException e)  {
			e.printStackTrace();
		}
		
	}
	public void loadMap(String filePath)  {
		
		try  {
			InputStream is = getClass().getResourceAsStream(filePath);
			BufferedReader br = new BufferedReader(new InputStreamReader(is));
			
			int col = 0;
			int row = 0;
			
			while(col < gp.maxWorldCol && row < gp.maxWorldRow)  {
				
				String line = br.readLine();
				
				while(col < gp.maxWorldCol)  {
					
					String numbers[] = line.split(" ");
					
					int num = Integer.parseInt(numbers[col]);
					
					mapTileNum[col][row] = num;
					col++;
				}
				if(col == gp.maxWorldCol)  {
					col = 0;
					row++;
				}
			}
			br.close();
	
		} catch (Exception e)  {
			
		}
		
	}		
	public void draw(Graphics2D g2)  {

		
		int worldCol = 0;
		int worldRow = 0;
		
		while(worldCol < gp.maxWorldCol && worldRow < gp.maxWorldRow)  {
			

//everything is stored inside this mapTileNum array
			
			int tileNum = mapTileNum[worldCol][worldRow];
// world X: position on the map
// screen X: where on the screen we draw it
			int worldX = worldCol * gp.tileSize;
			int worldY = worldRow * gp.tileSize;
			
// minus the player's world X and world Y from the tile of world X and world Y
			int screenX = worldX - gp.player.worldX + gp.player.screenX;
			int screenY = worldY - gp.player.worldY + gp.player.screenY;
// for ex, player's position is 200:200 => screen pos is -200:-200
// we put player in middle of screen, so everything outside map from the left is minus. Even if player stand at top left of world map 
// => we add screen X and screen Y to the cal to prevent those kinds of situation
			if (worldX + gp.tileSize > gp.player.worldX - gp.player.screenX &&
				worldX - gp.tileSize < gp.player.worldX + gp.player.screenX &&
				worldY + gp.tileSize > gp.player.worldY - gp.player.screenY &&
				worldY + gp.tileSize < gp.player.worldY + gp.player.screenY)  {
				
// useless loop, just put there for higher rendering
					
			
					g2.drawImage(tile[tileNum].image, screenX, screenY, gp.tileSize, gp.tileSize, null);
			}
			
			worldCol++;
			

			
			if(worldCol == gp.maxWorldCol)  {
				worldCol = 0;
				worldRow++;
				
// we draw the map 1 by one, when hit final tile on the right => reset col, increase row by 1
			}
		}
	}
}
