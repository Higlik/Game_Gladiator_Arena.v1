package com.blindmind.main;

import java.awt.Canvas;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.image.BufferStrategy;
import java.awt.image.BufferedImage;
import java.util.ArrayList;
import java.util.List;

import javax.swing.JFrame;

import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

import com.blindmind.entities.Entity;
import com.blindmind.entities.Player;
import com.blindmind.graphics.SpriteSheet;
import com.blindmind.world.World;

public class Game extends Canvas implements Runnable, KeyListener{

		//Canvas contem as propriedades que serão implementadas em JFrame, por exemplo o tamanho de nossa janela.
		
	private static final long serialVersionUID = 1L;
		//Variaveis para a escala da janela
		public static JFrame frame;
		public static final int WIDTH =   320; //largura
		public static final int HEIGHT = 220; //Altura
		private final int SCALE = 3; //Mutiplica ambos(identico a dar zoom em uma tela)
		
		private BufferedImage image;
		
		public static List<Entity> entities;
		public static SpriteSheet spritesheet, spritesheetMap, spritesheetBuffs, spritesheetExecutioner;
		
		//instanciando classe World
		public static World world;
		
		//instanciando classe Player
		public static Player player;
		
		
		
		//Variaveis para iniciar o loop
		private Thread thread;
		private boolean isRunning = true;
		
		
		
		
		//Metodo construtor refente a abertura de janela
		public void initFrame() {
			frame = new JFrame(); //Altera o título da janela dentro de ().
			frame.add(this); // Pega todas as propriedade das dimensões da janela.
			frame.setResizable(false); //Impossibilita que o usuário redimensione a janela.
			frame.pack(); //Calcula dimensões do canvas.
			frame.setLocationRelativeTo(null); //Direciona a janela para ser iniciada no centro da tela.
			frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE); //Realiza o fechamento da janela ao encerra-la.
			frame.setVisible(true); //Deixa a janela visivel.
		}
		
		
		
		
		//metodo para abrir a janela
		public Game() {
			addKeyListener(this);
			this.setPreferredSize(new Dimension(WIDTH*SCALE,HEIGHT*SCALE));
			initFrame();
			//Inicia objetos
			
			image = new BufferedImage(WIDTH,HEIGHT,BufferedImage.TYPE_INT_RGB);
			entities = new ArrayList<Entity>();
			spritesheet = new SpriteSheet("/Lancer_Sprites.png");
			spritesheetExecutioner = new SpriteSheet("/Executioner_Sprites.png");
			spritesheetBuffs = new SpriteSheet("/Buffs.png");
			spritesheetMap = new SpriteSheet("/Arena_tileset.png");
			player = new Player(0,128,31,31,spritesheet.getSprite(0, 128, 31, 31));//Renderiza o player
			entities.add(player);
			world = new World("/Arena01.png");
		}
		
		//Metodo para manter o loop
		public synchronized void start() {
			thread = new Thread(this);
			isRunning = true;
			thread.start();
		}
		
		//Metodo para encerrar o loop
		public synchronized void stop() {
			isRunning = false;
			try {
			thread.join();
			}catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
		
		//Inicia o looping
		public static void main(String[] args) {
			Game game = new Game();
			game.start();
		}
		
		//Update lógico do game
		public void tick () {
			for(int i = 0; i < entities.size(); i++) {
				Entity e =  entities.get(i);
				e.tick();
			}
			
		}
		
		//Renderiza o game
		public void render() {
			BufferStrategy bs = this.getBufferStrategy(); //Função de sequencia de Buffers para otimizar a renderização.
			if(bs == null) {
				this.createBufferStrategy(3);
				return;
			}
			Graphics g = image.getGraphics();
			/*
			 * O Graphics (bem como sua subclasse Graphics2D) não representa uma imagem, e sim é um objeto que faz o desenho em imagens.
		Uma certa analogia que daria para fazer (embora bem imperfeita) é que "o BufferedImage é um papel, enquanto que o Graphics é uma caneta".
			 */
			g.setColor(new Color(0,0,0)); // Altera a cor da tela conforme a mudança dos números.
			g.fillRect(0,0,WIDTH,HEIGHT);
			
			/* Renderização do jogo*/
		//	Graphics2D g2 = (Graphics2D) g; //Graphics2D é um cast que possibilita a animação do player. 
			
			world.render(g); // renderiza o mapa
			for(int i = 0; i < entities.size(); i++) {
				Entity e =  entities.get(i);
				e.render(g);
			}
			
			
			
			g.dispose(); //metodo para otimização da imagens. 
			g = bs.getDrawGraphics(); //Pega os graficos instruidos.
			g.drawImage(image,0,0,WIDTH*SCALE,HEIGHT*SCALE,null);
			bs.show(); //Mostra os graficos
			
		} 
		
		
		
		// Loop para game
		public void run() {
			
			//mantem o padrão para rodar a 60 FPS
			long lastTime = System.nanoTime(); //System.nanoTime == pega o tempo real do computador em nano segundos
			double amountOfTicks = 60.0;
			double ns = 1000000000 / amountOfTicks; //pega o momento certo para fazer o update do game
			double delta = 0;
			int frames = 0;
			double timer = System.currentTimeMillis();
			
			//Inicia de fato o looping
			while(isRunning) {
				long now = System.nanoTime();
				delta+= (now - lastTime)/ ns;
				lastTime = now;
				if(delta >=1) {
					tick(); // tick sempre deve vir primeiro (sempre atualizar o jogo antes de renderizar)
					render();
					frames++;
					delta--;
				}
				
				if(System.currentTimeMillis() - timer >= 1000) {
					System.out.println("FPS: "+ frames);
					frames = 0;
					timer+=1000;
				}
			}
			
			stop(); //Garantia para que se o Game sair do loop por algum motivo ou erro o programa irá se encerrar
		}

		
		//Controle do personagem

		@Override
		public void keyPressed(KeyEvent e) {
		 if (e.getKeyCode() == KeyEvent.VK_RIGHT || 
				 e.getKeyCode() == KeyEvent.VK_D) {
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
		 
		}

		@Override
		public void keyReleased(KeyEvent e) {
		
			 if (e.getKeyCode() == KeyEvent.VK_RIGHT || 
					 e.getKeyCode() == KeyEvent.VK_D) {
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

	
}
