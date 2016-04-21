package projetoes.com.floppyalarm.puzzle.floppy;

import java.util.HashMap;

public abstract class Piece {
	
	protected Integer faceColor;
	protected Integer oppositeFaceColor;
	protected HashMap<Orientation, Integer> sideFaces;
	
	public Piece(Integer defaultFaceColor, Integer oppositeFaceColor){
		this.faceColor = defaultFaceColor;
		this.oppositeFaceColor = oppositeFaceColor;

		this.sideFaces = new HashMap<>();
		sideFaces.put(Orientation.TOP, 0);
		sideFaces.put(Orientation.BOTTOM, 0);
		sideFaces.put(Orientation.RIGHT, 0);
		sideFaces.put(Orientation.LEFT, 0);
	}
	
	public Integer getSideColor(Orientation orientation){
		return sideFaces.get(orientation);
	}
	
	public Integer getFaceColor(){
		return this.faceColor;
	}

	protected void flip(Orientation orientation){
		Integer temp = this.faceColor;
		this.faceColor = this.oppositeFaceColor;
		this.oppositeFaceColor = temp;
	}
	
	public boolean compareFace(Piece piece){
		return this.getFaceColor().equals(piece.getFaceColor());
	}
	
	public boolean compareSide(Piece piece, Orientation orientation) {
		return 	this.getSideColor(orientation).equals(piece.getSideColor(orientation)) ;
	}
}
