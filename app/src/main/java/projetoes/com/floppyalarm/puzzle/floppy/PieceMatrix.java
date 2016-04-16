package projetoes.com.floppyalarm.puzzle.floppy;

public class PieceMatrix {
	
	public class Element {
		private final Integer row;
		private final Integer column;

	    public Element(Integer row, Integer column) {
		this.row = row;
		this.column = column;
	    }

	    public Integer getRow()	{ return row; }
	    public Integer getColumn() { return column; }
	}
	
	private final Integer order;
	private Piece[][] matrix;
	
	public PieceMatrix(Integer order) {
		this.order = order;
		this.matrix = new Piece[order][order];
	}
	
	public Integer getOrder() {
		return order;
	}

	public Piece getElement(Integer row, Integer column) {
		return matrix[row][column];
	}
	
	protected void setElement(Integer row, Integer column, Piece piece) {
		matrix[row][column] = piece;
	}
	
	protected void reflectRow(int row) {
		Integer i = 0;
		Integer j = this.order - 1;
		
		while (i < j) {
			swap(new Element(row, i), new Element(row, j));
			getElement(row, i).flip(Orientation.HORIZONTAL);
			getElement(row, j).flip(Orientation.HORIZONTAL);
			i++;
			j--;
		}
		getElement(row, i).flip(Orientation.HORIZONTAL);
	}
	
	protected void reflectColumn(int column) {
		Integer i = 0;
		Integer j = this.order - 1;
		
		while (i < j) {
			swap(new Element(i, column), new Element(j, column));
			getElement(i, column).flip(Orientation.VERTICAL);
			getElement(j, column).flip(Orientation.VERTICAL);
			i++;
			j--;
		}
		getElement(i, column).flip(Orientation.VERTICAL);
	}
	
	private void swap(Element e1, Element e2){
		Piece temp;
		temp = matrix[e1.row][e1.column];
		
		matrix[e1.row][e1.column] = matrix[e2.row][e2.column];
		matrix[e2.row][e2.column] = temp;
	}
}
