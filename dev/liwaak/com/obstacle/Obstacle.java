package dev.liwaak.com.obstacle;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.Rectangle;

public abstract class Obstacle {

	protected Rectangle r;
	protected Color c;
	
	public Obstacle(Rectangle r) {
		this.r = r;
		c = new Color((int)(Math.random()*255), (int)(Math.random()*255),(int)(Math.random()*255));
		
	}
	public Obstacle(int x, int y, int w, int h) {
		this(new Rectangle(x, y, w, h));
		
	}
	


	public abstract void render(Graphics2D g2d);

	public boolean intersects(Rectangle rect) {

		return (this.r.x > rect.x && this.r.x < rect.x + rect.width && this.r.y > rect.y
				&& this.r.y < rect.y + rect.height)
				|| (this.r.x + this.r.width > rect.x && this.r.x + this.r.width < rect.x + rect.width
						&& this.r.y + this.r.height > rect.y && this.r.y + this.r.height < rect.y + rect.height);

	}

}
