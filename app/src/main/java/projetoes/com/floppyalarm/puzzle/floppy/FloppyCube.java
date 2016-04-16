package projetoes.com.floppyalarm.puzzle.floppy;

import java.util.Random;
import java.util.Scanner;

public class FloppyCube {

	private final static Integer DIMENSION = 3;
	
	private PieceMatrix pieces;

	public FloppyCube(FaceColor defaultFaceColor, 
			SideColor topSideColor, 
			SideColor bottomSideColor, 
			SideColor rightSideColor,
			SideColor leftSideColor) {
		
		this.pieces = new PieceMatrix(DIMENSION);
		
		pieces.setElement(0, 0, 
				new Corner(defaultFaceColor, Orientation.TOP, topSideColor, Orientation.LEFT, leftSideColor));
		pieces.setElement(0, 1, 
				new Edge(defaultFaceColor, Orientation.TOP, topSideColor));
		pieces.setElement(0, 2, 
				new Corner(defaultFaceColor, Orientation.TOP, topSideColor, Orientation.RIGHT, rightSideColor));
		pieces.setElement(1, 0, 
				new Edge(defaultFaceColor, Orientation.LEFT, leftSideColor));
		pieces.setElement(1, 1, 
				new Center(defaultFaceColor));
		pieces.setElement(1, 2, 
				new Edge(defaultFaceColor, Orientation.RIGHT, rightSideColor));
		pieces.setElement(2, 0, 
				new Corner(defaultFaceColor, Orientation.BOTTOM, bottomSideColor, Orientation.LEFT, leftSideColor));
		pieces.setElement(2, 1, 
				new Edge(defaultFaceColor, Orientation.BOTTOM, bottomSideColor));
		pieces.setElement(2, 2, 
				new Corner(defaultFaceColor, Orientation.BOTTOM, bottomSideColor, Orientation.RIGHT, rightSideColor));
	}

	public void turn(Character move){
		switch (move) {
		case 'U':
			pieces.reflectRow(0);
			break;
		case 'E':
			pieces.reflectRow(1);
			break;
		case 'D':
			pieces.reflectRow(2);			
			break;
		case 'L':
			pieces.reflectColumn(0);
			break;
		case 'M':
			pieces.reflectColumn(1);
			break;
		case 'R':
			pieces.reflectColumn(2);
			break;

		default:
			break;
		}
	}
	
	public Piece getPiece(Integer row, Integer column) {
		return pieces.getElement(row, column);
	}
	
	public void scramble(Integer numberOfTurns) {
		final Character[] MOVES = {'U', 'D', 'R', 'L', 'M', 'E'};
		Random random = new Random();

		for (int turn = 0; turn < numberOfTurns; turn++) {
			this.turn(MOVES[random.nextInt(MOVES.length)]);
		}
		if (this.isSolved()) {
			this.turn(MOVES[random.nextInt(MOVES.length)]);
		}
	}
	
	public boolean isSolved() {
		Piece comparablePiece = pieces.getElement(0, 0);
		Piece comparablePiece2 = pieces.getElement(2, 2);

		for (int row = 0; row < DIMENSION; row++) {
			for (int column = 0; column < DIMENSION; column++) {
				if (!pieces.getElement(row, column).compareFace(comparablePiece)) {
					return false;
				}			}
		}
		for (int column = 0; column < DIMENSION; column++) {
			if (!pieces.getElement(0, column).compareSide(comparablePiece, Orientation.TOP)) {
				return false;	
			}
		}
		for (int column = 0; column < DIMENSION; column++) {
			if (!pieces.getElement(2, column).compareSide(comparablePiece2, Orientation.BOTTOM)) {
				return false;
			}
		}
		for (int row = 0; row < DIMENSION; row++) {
			if (!pieces.getElement(row, 0).compareSide(comparablePiece, Orientation.LEFT)) {
				return false;
			}
		}
		for (int row = 0; row < DIMENSION; row++) {
			if (!pieces.getElement(row, 2).compareSide(comparablePiece2, Orientation.RIGHT)) {
				return false;
			}
		}
		
		return true;
	}
	
/*
	public static void main(String[] args) {
		FloppyCube cube = new FloppyCube(FaceColor.WHITE, SideColor.GREEEN, SideColor.BLUE, SideColor.ORANGE, SideColor.RED);
		
		Scanner input = new Scanner(System.in);
		
		cube.scramble(20);
		while (!input.equals("")) {
			for (int i = 0; i < 3; i++) {
				for (int j = 0; j < 3; j++) {
					System.out.printf("(%d,%d)  Face: %s, Side Colors: %s, %s, %s, %s\n",
							i,j,
							cube.getPiece(i, j).getFaceColor(),
							cube.getPiece(i, j).getSideColor(Orientation.TOP),
							cube.getPiece(i, j).getSideColor(Orientation.BOTTOM),
							cube.getPiece(i, j).getSideColor(Orientation.RIGHT),
							cube.getPiece(i, j).getSideColor(Orientation.LEFT));
				}
			}
			System.out.printf("\nSolved: %s", cube.isSolved());
			System.out.println("\nMove: ");
			String m = input.next();
			cube.turn(m.toCharArray()[0]);
		}
		
		input.close();
	}*/
}
