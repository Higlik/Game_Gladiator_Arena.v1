package com.blindmind.entities;

import java.awt.Graphics;
import java.awt.image.BufferedImage;

import com.blindmind.main.Game;
import com.blindmind.world.Camera;

public class Entity {
	
	/*
	public BufferedImage[] LIFE_PACK_EN;
	public BufferedImage[] DAMAGE_BONUS_EN;
	public BufferedImage[] ARMOR_BONUS_EN;
	*/
	
	public static BufferedImage LIFE_PACK_EN = Game.spritesheetBuffs.getSprite(0, 0, 16, 16);
	public static BufferedImage DAMAGE_BONUS_EN = Game.spritesheetBuffs.getSprite(0, 16, 16, 16);;
	public static BufferedImage DEFENSE_BONUS_EN = Game.spritesheetBuffs.getSprite(0, 32, 16, 16);;
	public static BufferedImage EXECUTIONER_ENEMY_EN = Game.spritesheetExecutioner.getSprite(0, 0, 32, 32);;

	
	
	protected double x;
	protected double y;
	protected int width;
	protected int height;

	//Renderização da entidade
	private BufferedImage sprite;
	
	//metodo construtor da entidade
	public Entity(int x, int y, int width, int height, BufferedImage sprite) {
		this.x = x;
		this.y = y;
		this.width = width;
		this.height = height;
		this.sprite = sprite;
		

	}
	
	public void setX(int newX) {
		this.x = newX;
	}
	
	public void setY(int newY) {
		this.y = newY;
	}
	
	public int getX() {
		return (int)this.x;
	}
	
	public int getY() {
		return (int)this.y;
	}
	public int getWidth() {
		return this.width;
	}
	public int getHeight() {
		return this.height;
	}
	
	public void tick() {
		
	}
	
	//metodo para renderizar
	public void render(Graphics g) {
		g.drawImage(sprite, this.getX() - Camera.x,this.getY() - Camera.y,null);
	}
	
}
