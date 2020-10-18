package dev.liwaak.com.obstacle;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.Rectangle;

import dev.liwaak.com.renderer.Methods.Methods;

public class Tree extends Obstacle{
	
	
	public Tree(Rectangle r) {
		super(r);
	
	}
	public Tree(int x, int y, int w, int h) {
		super(x, y, w,h);
		
	}

	@Override
	public void render(Graphics2D g2d) {
		g2d.setColor(c);
		g2d.fillOval(this.r.x + Methods.TRANSLATE_X, this.r.y + Methods.TRANSLATE_Y, this.r.width, this.r.height);
		
	}

	
	
}
