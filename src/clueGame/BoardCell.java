//Authors: Nathan Lambert and Elliott McCabe
package clueGame;

public class BoardCell {
	private int row;
	private int column;
	private char initial;
	private DoorDirection doorDirection;
	
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
	
	public boolean isDoorway() {
		if(this.initial == WALKWAY_INITIAL && this.)
	}
	
	public DoorDirection getDoorDirection() {
		return doorDirection;
	}
}
