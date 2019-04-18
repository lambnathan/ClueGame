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
	private boolean drawName = false; //keeps track of what cell should draw the name of the room, dtermined by the config file
	
	
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
	
	public void shouldDrawName() {
		this.drawName = true;
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
			if(this.initial == 'X') {
				Color c = new Color(109, 12, 33);
				g.setColor(c);
				g.fillRect(column * Board.CELL_SIZE, row * Board.CELL_SIZE, Board.CELL_SIZE, Board.CELL_SIZE);
			}	
			else {
				g.setColor(Color.LIGHT_GRAY);
				g.fillRect(column * Board.CELL_SIZE, row * Board.CELL_SIZE, Board.CELL_SIZE, Board.CELL_SIZE);
			}
			if(drawName) { //if this is a cell that should draw the name of the room
				String name = Board.getLegend().get(this.initial);
				g.setColor(Color.BLACK);
				g.drawString(name, column * Board.CELL_SIZE, row * Board.CELL_SIZE - 10);
			}
		}
		if(this.isDoorway()) {
			g.setColor(Color.LIGHT_GRAY);
			g.fillRect(column * Board.CELL_SIZE, row * Board.CELL_SIZE, Board.CELL_SIZE, Board.CELL_SIZE);
			drawDoorways(g);
			
		}
		if(this.isWalkway()) {
			g.setColor(Color.GRAY);
			g.fillRect(column * Board.CELL_SIZE, row * Board.CELL_SIZE, Board.CELL_SIZE, Board.CELL_SIZE);
			g.setColor(Color.BLACK);
			g.drawRect(column * Board.CELL_SIZE, row * Board.CELL_SIZE, Board.CELL_SIZE, Board.CELL_SIZE);
		}
		
		//if the current player is human, show available tiles for them to select
		if(Board.getInstance().getCurrentPlayer() instanceof HumanPlayer) {
			g.setColor(Color.CYAN);
			if(Board.getInstance().getTargets().contains(this)) {
				g.fillRect(column * Board.CELL_SIZE, row * Board.CELL_SIZE, Board.CELL_SIZE, Board.CELL_SIZE);
			}
			if(this.isDoorway()) {
				drawDoorways(g);
			}
		}
	}
	public void drawDoorways(Graphics g) {
		g.setColor(Color.BLUE); //blue will let players know that the cell is a doorway
		//draws the blue sliver that indicates a door deoending on the cell's door direction
		if(this.getDoorDirection() == DoorDirection.DOWN) {
			g.fillRect(column * Board.CELL_SIZE, row * Board.CELL_SIZE + Board.DOOR_OFFSET, Board.CELL_SIZE, Board.CELL_SIZE - Board.DOOR_OFFSET);
		}
		else if(this.getDoorDirection() == DoorDirection.UP) {
			g.fillRect(column * Board.CELL_SIZE, row * Board.CELL_SIZE, Board.CELL_SIZE, Board.CELL_SIZE - Board.DOOR_OFFSET);
		}
		else if(this.getDoorDirection() == DoorDirection.LEFT) {
			g.fillRect(column * Board.CELL_SIZE, row * Board.CELL_SIZE, Board.CELL_SIZE - Board.DOOR_OFFSET, Board.CELL_SIZE);
		}
		else if(this.getDoorDirection() == DoorDirection.RIGHT) {
			g.fillRect(column * Board.CELL_SIZE + Board.DOOR_OFFSET, row * Board.CELL_SIZE, Board.CELL_SIZE - Board.DOOR_OFFSET, Board.CELL_SIZE);
		}
	}
}
