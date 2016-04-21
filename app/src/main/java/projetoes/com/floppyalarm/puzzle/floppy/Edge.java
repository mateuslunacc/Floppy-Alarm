package projetoes.com.floppyalarm.puzzle.floppy;

public class Edge extends Piece{

	public Edge(Integer defaultFaceColor, Integer oppositeFaceColor,
			Orientation orientation, Integer color) {
		super(defaultFaceColor, oppositeFaceColor);

		sideFaces.put(orientation, color);
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
