package dev.liwaak.com;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseMotionListener;
import java.awt.image.BufferStrategy;

import javax.swing.JFrame;

import dev.liwaak.com.chunks.ChunkManager;
import dev.liwaak.com.renderer.Methods.Methods;

public class Main extends JFrame implements Runnable {

	public static int WIDTH = 600;
	public static int HEIGHT = 600;
	public int FPS = 0;

	int mouseX = 0, mouseY = 0;
	
	int velx =0,  vely = 0;

	public static void main(String[] args) {
		Main m = new Main();
		m.getThread().start();
	}

	ChunkManager cm;

	private boolean running = false;
	private Thread t;

	public Main() {
		this.setSize(600, 600);
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		this.setUndecorated(true);
		this.setLocationRelativeTo(null);
		this.setVisible(true);

		this.addMouseMotionListener(new MouseMotionListener() {

			@Override
			public void mouseDragged(MouseEvent e) {
				// TODO Auto-generated method stub

			}

			@Override
			public void mouseMoved(MouseEvent e) {
				mouseX = e.getX();
				mouseY = e.getY();

			}

		});

		this.addKeyListener(new KeyListener() {

			@Override
			public void keyTyped(KeyEvent e) {
				// TODO Auto-generated method stub

			}

			@Override
			public void keyPressed(KeyEvent e) {
				switch (e.getKeyCode()) {
					case KeyEvent.VK_UP:
						vely-=1;
						break;
					case KeyEvent.VK_DOWN:
						vely+=1;
						break;
					case KeyEvent.VK_LEFT:
						velx-=1;
						break;
					case KeyEvent.VK_RIGHT:
						velx+=1;
						break;
				}

			}

			@Override
			public void keyReleased(KeyEvent e) {
				switch (e.getKeyCode()) {
				case KeyEvent.VK_UP:
					vely =0;
					break;
				case KeyEvent.VK_DOWN:
					vely = 0;
					break;
				case KeyEvent.VK_LEFT:
					velx = 0;
					break;
				case KeyEvent.VK_RIGHT:
					velx = 0;
					break;
			}

			}

		});

		cm = new ChunkManager();
		cm.setCurrChunk(cm.getChunkAtIndex(0, 0));
		t = new Thread(this);
		running = true;
	}

	public Thread getThread() {
		return t;
	}

	int x = 0, y = 0;

	public void render() {
		BufferStrategy bs = this.getBufferStrategy();

		if (bs == null) {
			this.createBufferStrategy(3);
			return;
		}
		x+=velx;
		y+=vely;

		Methods.translate(WIDTH / 2 - x, HEIGHT / 2 - y);
		Graphics2D g2d = (Graphics2D) bs.getDrawGraphics();
		g2d.setColor(Color.CYAN);
		g2d.fillRect(0, 0, WIDTH, HEIGHT);

		cm.setCurrChunk(cm.getChunkAtPos(x, y));
		cm.render(g2d);

		g2d.setColor(Color.YELLOW);
		g2d.fillOval((x - 10) + Methods.TRANSLATE_X, (y - 10) + Methods.TRANSLATE_Y, 20, 20);
		g2d.dispose();
		bs.show();

	}

	@Override
	public void run() {

		System.out.println("Thread started");
		int frames = 0;

		double requiredFPS = 60;

		double timeBetFrames = 1 / requiredFPS * Math.pow(10, 9);

		long lastRender = 0;

		double lastFPS = System.currentTimeMillis();

		while (running) {
			while (System.nanoTime() - lastRender < timeBetFrames)
				;

			lastRender = System.nanoTime();

			render();

			frames++;

			if (System.currentTimeMillis() - lastFPS > 1000) {
				lastFPS = System.currentTimeMillis();
				FPS = frames;
				frames = 0;
				System.out.println(FPS);
			}
		}

	}
}
