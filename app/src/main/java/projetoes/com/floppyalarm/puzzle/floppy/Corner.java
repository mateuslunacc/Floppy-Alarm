package projetoes.com.floppyalarm.puzzle.floppy;

public class Corner extends Piece{

	public Corner(Integer defaultFaceColor, Integer oppositeFaceColor,
			Orientation orientation1, Integer color1,
			Orientation orientation2, Integer color2) {
		super(defaultFaceColor, oppositeFaceColor);
		
		sideFaces.put(orientation1, color1);
		sideFaces.put(orientation2, color2);
	}

	@Override
	protected void flip(Orientation orientation) {
		super.flip(orientation);
		
		if (orientation == Orientation.HORIZONTAL) {
			Integer rightColor = sideFaces.get(Orientation.RIGHT);
			Integer leftColor = sideFaces.get(Orientation.LEFT);
			
			this.sideFaces.put(Orientation.RIGHT, leftColor);
			this.sideFaces.put(Orientation.LEFT, rightColor);
			
		} else {
			Integer topColor = sideFaces.get(Orientation.TOP);
			Integer bottomColor = sideFaces.get(Orientation.BOTTOM);

			this.sideFaces.put(Orientation.TOP, bottomColor);
			this.sideFaces.put(Orientation.BOTTOM, topColor);
		}
	}
}
