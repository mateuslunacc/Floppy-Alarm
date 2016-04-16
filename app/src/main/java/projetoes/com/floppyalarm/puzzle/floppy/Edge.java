package projetoes.com.floppyalarm.puzzle.floppy;

import java.util.HashMap;

public class Edge extends Piece{

	public Edge(FaceColor defaultFaceColor, 
			Orientation orientation, SideColor color) {
		super(defaultFaceColor);

		sideFaces.put(orientation, color);
	}

	@Override
	protected void flip(Orientation orientation) {
		super.flip(orientation);
		
		if (orientation == Orientation.HORIZONTAL) {
			SideColor rightColor = sideFaces.get(Orientation.RIGHT);
			SideColor leftColor = sideFaces.get(Orientation.LEFT);
			
			this.sideFaces.put(Orientation.RIGHT, leftColor);
			this.sideFaces.put(Orientation.LEFT, rightColor);
			
		} else {
			SideColor topColor = sideFaces.get(Orientation.TOP);
			SideColor bottomColor = sideFaces.get(Orientation.BOTTOM);

			this.sideFaces.put(Orientation.TOP, bottomColor);
			this.sideFaces.put(Orientation.BOTTOM, topColor);
		}
	}
}
