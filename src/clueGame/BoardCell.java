//Authors: Nathan Lambert and Elliott McCabe
package clueGame;

import java.awt.Color;
import java.awt.Graphics;

public class BoardCell {
	private int row;
	private int column;
	private char initial;
	private DoorDirection doorDirection;
	private boolean isDoor;
	
	//size of cell
	private static final int WIDTH = 25;
	private static final int HEIGHT = 25;
	
	
	public static final char WALKWAY_INITIAL = 'W';
	
	public BoardCell(int row, int column, char initial, DoorDirection doorDirection) {
		super();
		this.row = row;
		this.column = column;
		this.initial = initial;
		this.doorDirection = doorDirection;
	}
	
	public boolean isWalkway() {
		if(this.initial == WALKWAY_INITIAL) {
			return true;
		}
		else {
			return false;
		}
	}
	
	public boolean isRoom() {
		if(this.initial == WALKWAY_INITIAL) {
			return false;
		}
		else {
			return true;
		}
	}
	
	public void setDoorwayBool(boolean doorway) {
		this.isDoor = doorway;
	}
	
	public boolean isDoorway() {
		return isDoor;
	}
	
	public DoorDirection getDoorDirection() {
		return doorDirection;
	}
	
	public char getInitial() {
		return initial;
	}
	
	public int getRow() {
		return row;
	}
	
	public int getColumn() {
		return column;
	}
	
	public void draw(Graphics g) {
		if(this.isRoom()) {
			g.setColor(Color.GRAY);
			g.fillRect(column * WIDTH, row * HEIGHT, WIDTH, HEIGHT);
		}
		if(this.isDoorway()) {
			g.setColor(Color.BLUE);
			g.fillRect(column * WIDTH, row * HEIGHT, WIDTH, HEIGHT);
		}
		if(this.isWalkway()) {
			g.setColor(Color.ORANGE);
			g.drawRect(column * WIDTH, row * HEIGHT, WIDTH, HEIGHT);
		}
	}
}
