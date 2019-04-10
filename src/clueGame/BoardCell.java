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
	//door will either have length or width that is this value less than the cell's width or height, and will be moved either up and down or left and right this ammount
	private static final int DOOR_OFFSET = 21; 
	
	
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
			g.setColor(Color.LIGHT_GRAY);
			g.fillRect(column * WIDTH, row * HEIGHT, WIDTH, HEIGHT);	
		}
		if(this.isDoorway()) {
			g.setColor(Color.LIGHT_GRAY);
			g.fillRect(column * WIDTH, row * HEIGHT, WIDTH, HEIGHT);
			g.setColor(Color.BLUE); //blue will let players know that the cell is a doorway
			//draws the blue sliver that indicates a door deoending on the cell's door direction
			if(this.getDoorDirection() == DoorDirection.DOWN) {
				g.fillRect(column * WIDTH, row * HEIGHT + DOOR_OFFSET, WIDTH, HEIGHT - DOOR_OFFSET);
			}
			else if(this.getDoorDirection() == DoorDirection.UP) {
				g.fillRect(column * WIDTH, row * HEIGHT, WIDTH, HEIGHT - DOOR_OFFSET);
			}
			else if(this.getDoorDirection() == DoorDirection.LEFT) {
				g.fillRect(column * WIDTH, row * HEIGHT, WIDTH - DOOR_OFFSET, HEIGHT);
			}
			else if(this.getDoorDirection() == DoorDirection.RIGHT) {
				g.fillRect(column * WIDTH + DOOR_OFFSET, row * HEIGHT, WIDTH - DOOR_OFFSET, HEIGHT);
			}
		}
		if(this.isWalkway()) {
			g.setColor(Color.BLACK);
			g.drawRect(column * WIDTH, row * HEIGHT, WIDTH, HEIGHT);
		}
	}
}
