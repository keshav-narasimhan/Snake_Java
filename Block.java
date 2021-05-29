package game;

import java.awt.Color;

public class Block {
	
	// fields to create rectangle
	private int xpos;
	private int ypos;
	private int width;
	private int height;
	private Color color;
	
	// mvmt fields
	private boolean up = false;
	private boolean down = false;
	private boolean left = false;
	private boolean right = false;
	
	public Block(int x, int y) {
		this(x, y, 10, 10);
	}
	
	public Block(int x, int y, int w, int h) {
		this.xpos = x;
		this.ypos = y;
		this.width = w;
		this.height = h;
		this.color = Color.GREEN;
	}
	
	// Getters & Setters
	public int getXpos() {
		return xpos;
	}
	public int getYpos() {
		return ypos;
	}
	public void setXpos(int xpos) {
		this.xpos = xpos;
	}
	public void setYpos(int ypos) {
		this.ypos = ypos;
	}
	public int getWidth() {
		return width;
	}
	public int getHeight() {
		return height;
	}
	public Color getColor() {
		return color;
	}
	public boolean isUp() {
		return up;
	}
	public boolean isDown() {
		return down;
	}
	public boolean isLeft() {
		return left;
	}
	public boolean isRight() {
		return right;
	}
	public void setUp(boolean up) {
		this.up = up;
	}
	public void setDown(boolean down) {
		this.down = down;
	}
	public void setLeft(boolean left) {
		this.left = left;
	}
	public void setRight(boolean right) {
		this.right = right;
	}
	
}
