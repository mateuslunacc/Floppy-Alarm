package projetoes.com.floppyalarm.puzzle.floppy;

public class Corner extends Piece{

	public Corner(FaceColor defaultFaceColor,
			Orientation orientation1, SideColor color1,
			Orientation orientation2, SideColor color2) {
		super(defaultFaceColor);
		
		sideFaces.put(orientation1, color1);
		sideFaces.put(orientation2, color2);
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
