//--------------------DoorDirection--------------------

//Authors: Nathan Lambert and Elliott McCabe

package clueGame;

public enum DoorDirection {
	UP("U"), DOWN("D"), LEFT("L"), RIGHT("R"), NONE("N");
	private String temp;
	
	DoorDirection(String direction) {
		temp = direction;
	}
}
