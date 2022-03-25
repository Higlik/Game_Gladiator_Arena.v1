package com.blindmind.world;

import java.awt.image.BufferedImage;
import java.awt.Graphics;


import com.blindmind.main.Game;


public class Tile {

	
	public static BufferedImage TILE_FLOOR1 = Game.spritesheetMap.getSprite(16, 0, 16, 16);
	/*
		Tilesets reservados ----------------- // -------------  
 	public static BufferedImage TILE_FLOOR2 = Game.spritesheetMap.getSprite(0, 16, 16, 16);	
	public static BufferedImage TILE_FLOOR3 = Game.spritesheetMap.getSprite(0, 32, 16, 16);
	public static BufferedImage TILE_FLOOR4 = Game.spritesheetMap.getSprite(0, 48, 16, 16);
	 */

	public static BufferedImage TILE_WALL1 = Game.spritesheetMap.getSprite(0, 0, 16, 16);
	public static BufferedImage TILE_WALL2 = Game.spritesheetMap.getSprite(0, 16, 16, 16);
	public static BufferedImage TILE_WALL3 = Game.spritesheetMap.getSprite(0, 32, 16, 16);

	
	private BufferedImage sprite;
	private int x,y;
	
	public Tile(int x, int y,BufferedImage sprite) {
		this.x = x;
		this.y = y;
		this.sprite = sprite;
	}
	
	public void render(Graphics g) {
		g.drawImage(sprite, x - Camera.x, y - Camera.y, null);
	}

}
