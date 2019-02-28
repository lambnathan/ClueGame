//Authors: Nathan Lambert and Elliott McCabe
package clueGame;

public class BoardCell {
	private int row;
	private int column;
	private char initial;
	
	public BoardCell(int row, int column) {
		super();
		this.row = row;
		this.column = column;
	}
	
	public boolean isWalkway() {
		return true;
	}
	
	public boolean isRoom() {
		return true;
	}
	
	public boolean isDoorway() {
		return true;
	}
}
