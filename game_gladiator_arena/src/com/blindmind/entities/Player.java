package com.blindmind.entities;


import java.awt.image.BufferedImage;
import java.awt.Graphics;

import com.blindmind.main.Game;
import com.blindmind.world.Camera;
import com.blindmind.world.World;

public class Player extends Entity{

	public boolean right,up,left,down;
	public int right_dir = 0,left_dir = 1,  right_idle = 0, left_idle = 1;
	public int dir = right_dir;
	public int stop = right_idle;
	public double speed = 0.7;
	
	//Carrega as sprites
	private int frames = 0,maxFrames = 7, index = 0, maxIndex = 3;
	private int	framesIdle = 0, maxFramesIdle = 15, indexIdle = 0, maxIndexIdle = 2;
	private boolean moved = false, idle = true;
	private BufferedImage[] rightPlayer;
	private BufferedImage[] leftPlayer;
	private BufferedImage[] idleRightPlayer;
	private BufferedImage[] idleLeftPlayer;

	
	public Player(int x, int y, int width, int height, BufferedImage sprite) {
		super(x, y, width, height, sprite);

		rightPlayer = new BufferedImage[4];
		leftPlayer = new BufferedImage[4];
		idleRightPlayer = new BufferedImage[3];
		idleLeftPlayer = new BufferedImage [3];
		
		//Controle de animações
		for(int i =0; i< 4; i++) {
			rightPlayer[i] = Game.spritesheet.getSprite(0 + (i*32), 128, 32, 32);
		}
		for(int i =0; i< 4; i++) {
			leftPlayer[i] = Game.spritesheet.getSprite(0 + (i*32), 160, 32, 32);
		}
		for(int i =0; i< 3; i++) {
			idleRightPlayer[i] = Game.spritesheet.getSprite(0 + (i*32), 0, 32, 32);
		}
		for(int i =0; i< 3; i++) {	
			idleLeftPlayer[i] = Game.spritesheet.getSprite(0 + (i*32), 32, 32, 32);
		}
	
	
	}
	
	

	public void tick() {
		moved = false;
		
		//movimentação para direita
		if(right && World.isFree((int)(x+speed),this.getY())) {
			idle = false;
			moved = true;
			dir = right_dir;
			x+=speed;	
		}
		//movimentação para esquerda
		else if(left && World.isFree((int)(x-speed),this.getY())) {
			idle = false;
			moved = true;
			dir = left_dir;
			x-=speed;
		}
		//movimentação para cima
		if(up && World.isFree(this.getX(),(int)(y-speed))){
			idle = false;
			moved = true;
			y-=speed;
		}
		//movimentação para baixo
		else if(down && World.isFree(this.getX(),(int)(y+speed))){
			idle = false;
			moved = true;
			y+=speed;
		}
		
		//Ativa animação enquanto está ocioso, ficará virado para direita
		if(moved == false && dir != left_dir) {
			idle = true;
			stop = right_idle;	
		}
		//Ativa animação enquanto está ocioso, ficará virado para esquerda
		else if(moved == false && dir != right_dir) {
			idle = true;
			stop = left_idle;	
		}
		
		//Sistema para rodar a animação	de ocioso
		if(idle) { //verifica se o jogador está parado, caso sim irá executar animação de ocioso
			framesIdle++;
			if(framesIdle == maxFramesIdle) {
				framesIdle = 0;
				indexIdle++;
				if(indexIdle > maxIndexIdle) {
					indexIdle = 0;
				}
			}
		}
		
		//Sistema para rodar a animação	de movimento
		if(moved) { //moved Verifica se o jogador está se movendo, caso sim irá executar a animação de movimento
			frames++;
			if(frames == maxFrames) {
				frames = 0;
				index++;
				if(index > maxIndex) {
					index = 0;
				}
			}
		}
		
		//Sistema de camera
		Camera.x = this.getX() - (Game.WIDTH/2);
		Camera.y = this.getY() - (Game.HEIGHT/2);
	}
	
	public void render(Graphics g) {
		if(stop == right_idle && moved == false) {
			g.drawImage(idleRightPlayer[indexIdle],this.getX() - Camera.x,this.getY() - Camera.y,null);
		}else if(stop == left_idle && moved == false) {
				g.drawImage(idleLeftPlayer[indexIdle],this.getX() - Camera.x,this.getY() - Camera.y,null);
		}else if(dir == right_dir) {
			g.drawImage(rightPlayer[index],this.getX() - Camera.x,this.getY() - Camera.y,null);
		}else if(dir == left_dir) {
		g.drawImage(leftPlayer[index],this.getX() - Camera.x,this.getY() - Camera.y,null);	
		}
	}
}