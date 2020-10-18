package dev.liwaak.com.chunks;

import java.awt.Graphics2D;
import java.util.ArrayList;

import dev.liwaak.com.Main;
import dev.liwaak.com.obstacle.Obstacle;

public class ChunkManager {

	public static int RENDER_DISTANCE = 3;

	Chunk[][] chunks;

	Chunk[] toRender;

	Obstacle[] obsToRender;

	int xChunks, yChunks;

	Chunk currChunk;

	ArrayList<Chunk> loaded = new ArrayList<Chunk>();

	public ChunkManager() {
		xChunks = 3000 / Chunk.CHUNK_SIZE;
		yChunks = 3000 / Chunk.CHUNK_SIZE;

		chunks = new Chunk[xChunks][yChunks];

		toRender = new Chunk[0];
		obsToRender = new Obstacle[0];

	}

	public int getRenderChunkCount(int renderDistance) {
		return (int) Math.pow((renderDistance * 2) + 1, 2);
	}

	public void loadRenderChunks(Chunk c) {
		if (c == null)
			return;
		int renderChunkCount = getRenderChunkCount(RENDER_DISTANCE);
		int obsCount = 3*renderChunkCount;
		if (toRender.length != renderChunkCount) {
			toRender = new Chunk[renderChunkCount];
			obsToRender = new Obstacle[obsCount];
		}

		int indexX = c.indexX;
		int indexY = c.indexY;

		int rd = RENDER_DISTANCE;
		int currIndex = 0;
		int obsIndex = 0;
		for (int x = indexX - rd; x <= indexX + rd; x++) {
			for (int y = indexY - rd; y <= indexY + rd; y++) {
				Chunk ch = getChunkAtIndex(x, y);
				for (Obstacle obs: ch.getObstacles()) {
					obsToRender[obsIndex] = obs;
					obsIndex++;
				}
				toRender[currIndex] = ch;

				currIndex++;
			}
		}
		
		for (int i = obsIndex; i<obsCount; i++) {
			obsToRender[i] = null;
		}
	}

	public Chunk[] getRenderChunks() {

		loadRenderChunks(currChunk);
		return toRender;
	}

	public void render(Graphics2D g2d) {

		for (Chunk c : loaded) {
			c.renderBackg(g2d);
		}

		for (Chunk c : getRenderChunks()) {
			if (c == null) {

				continue;
			}
			c.render(g2d);
		}
		
		for (Obstacle obs: obsToRender) {
			if (obs == null) break;
			obs.render(g2d);
		}

	}

	public Chunk getChunkAtIndex(int indexX, int indexY) {
		indexX = indexX < 0 ? 0 : indexX > xChunks - 1 ? xChunks - 1 : indexX;
		indexY = indexY < 0 ? 0 : indexY > yChunks - 1 ? yChunks - 1 : indexY;
		Chunk c;
		if (chunks[indexX][indexY] == null) {
			c = new Chunk(indexX, indexY);
			chunks[indexX][indexY] = c;
			loaded.add(c);
		}

		return chunks[indexX][indexY];
	}

	public Chunk getChunkAtPos(int posX, int posY) {

		int indexX = (int) Math.floor(posX / Chunk.CHUNK_SIZE);
		int indexY = (int) Math.floor(posY / Chunk.CHUNK_SIZE);

		indexX = indexX < 0 ? 0 : indexX > xChunks - 1 ? xChunks - 1 : indexX;
		indexY = indexY < 0 ? 0 : indexY > yChunks - 1 ? yChunks - 1 : indexY;

		return getChunkAtIndex(indexX, indexY);
	}

	public void setCurrChunk(Chunk c) {
		if (currChunk != null && !currChunk.equals(c)) {
			currChunk.setCurrChunk(false);
		}
		c.setCurrChunk(true);
		currChunk = c;
	}
}
