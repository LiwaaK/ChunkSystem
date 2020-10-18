package dev.liwaak.com.chunks;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.Rectangle;
import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;

import dev.liwaak.com.obstacle.Obstacle;
import dev.liwaak.com.obstacle.Obstacles;
import dev.liwaak.com.renderer.Methods.Methods;

public class Chunk {

	public static int CHUNK_SIZE = 50;

	public int indexX, indexY, posX, posY;

	private Color c;

	private boolean currChunk = false;
	
	private Obstacle[] obstacles;
	
	
	public Chunk(int indexX, int indexY) {
		this.indexX = indexX;
		this.indexY = indexY;

		this.posX = indexX * CHUNK_SIZE;
		this.posY = indexY * CHUNK_SIZE;
		
		c = new Color(0, (int) Math.round(Math.random() * 255), 0);
		obstacles = new Obstacle[(int)Math.floor(Math.random()*3)];
		
		for (int i = 0; i<obstacles.length; i++) {
			try {
				int r = CHUNK_SIZE/5;
			    Constructor ctor = Obstacles.TREE.getCls().getDeclaredConstructor(Rectangle.class);
			    ctor.setAccessible(true);
			    Obstacle obs = (Obstacle)ctor.newInstance(new Rectangle(this.posX + (int) (Math.random()*(CHUNK_SIZE)), this.posY + (int) (Math.random()*(CHUNK_SIZE)), r, r));
			    obstacles[i] = obs;
		        // production code should handle these exceptions more gracefully
			} catch (InstantiationException x) {
			    x.printStackTrace();
			} catch (IllegalAccessException x) {
			    x.printStackTrace();
			} catch (InvocationTargetException x) {
			    x.printStackTrace();
			} catch (NoSuchMethodException x) {
			    x.printStackTrace();
			}
			
		}
	}

	public boolean isInChunk(int x, int y) {
		return x >= this.posX && x < this.posX + CHUNK_SIZE && y >= this.posY && y < this.posY + CHUNK_SIZE;
	}

	public void setCurrChunk(boolean value) {
		currChunk = value;
	}

	public void render(Graphics2D g2d) {
		g2d.setColor(c);
		if (currChunk)
			g2d.setColor(Color.RED);
		g2d.fillRect(this.posX + Methods.TRANSLATE_X, this.posY + Methods.TRANSLATE_Y, CHUNK_SIZE, CHUNK_SIZE);
	
		

	}
	
	public Obstacle[] getObstacles() {
		return obstacles;
	}
	
	public void renderBackg(Graphics2D g2d) {
		g2d.setColor(new Color(255-c.getRed(), 255-c.getGreen(), 255-c.getBlue()));
		g2d.fillRect(this.posX + Methods.TRANSLATE_X, this.posY + Methods.TRANSLATE_Y, CHUNK_SIZE, CHUNK_SIZE);
	}
}
