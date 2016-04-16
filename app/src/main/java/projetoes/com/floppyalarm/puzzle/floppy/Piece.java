package projetoes.com.floppyalarm.puzzle.floppy;

import java.util.HashMap;

public abstract class Piece {
	
	protected FaceColor faceColor;
	protected HashMap<Orientation, SideColor> sideFaces;
	
	public Piece(FaceColor defaultFaceColor){
		this.faceColor = defaultFaceColor;
		
		this.sideFaces = new HashMap<Orientation, SideColor>();
		sideFaces.put(Orientation.TOP, SideColor.NONE);
		sideFaces.put(Orientation.BOTTOM, SideColor.NONE);
		sideFaces.put(Orientation.RIGHT, SideColor.NONE);
		sideFaces.put(Orientation.LEFT, SideColor.NONE);
	}
	
	public SideColor getSideColor(Orientation orientation){
		return sideFaces.get(orientation);
	}
	
	public FaceColor getFaceColor(){
		return this.faceColor;
	}
	
	protected void flip(Orientation orientation){
		this.faceColor = this.faceColor == FaceColor.WHITE ? FaceColor.YELLOW : FaceColor.WHITE;
	}
	
	public boolean compareFace(Piece piece){
		return this.getFaceColor().equals(piece.getFaceColor());
	}
	
	public boolean compareSide(Piece piece, Orientation orientation) {
		return 	this.getSideColor(orientation).equals(piece.getSideColor(orientation)) ;
	}
}
