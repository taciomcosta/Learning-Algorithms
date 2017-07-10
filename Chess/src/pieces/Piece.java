package pieces;

import chessboard.Chessboard;

public abstract class Piece
{
	public enum Color {BLACK, WHITE};
	public enum Icon {P, R, N, B, Q, K};

	// ATTRIBUTES
	protected Chessboard board;
	private Color color;
	private Icon pieceInitial;
	private int row;
	private int col;
	private int firstRow;
	private int firstCol;
	private int lastRow;
	private int lastCol;
	private int moves = 0;
	private boolean captured = false;
	
	// METHODS
	// constructor
	public Piece(Color color, Icon i, Chessboard b, int row, int col)  
	{
		setColor(color);

		setPieceInitial(i);

		setRow(row);
		setCol(col);
		setFirstPosition(row, col);
		setLastPosition(row, col);
		setChessboard(b);

		b.addPiece(this, row, col);
	}
	
	private void setChessboard(Chessboard b)
	{
		this.board = b;
	}

	// setters and getters
	public void setColor(Color color)
	{
		this.color = color;
	}
	
	public Color getColor()
	{
		return this.color;
	}
	
	public void setRow(int row)
	{
		this.row = row;
	}
	
	public int getRow()
	{
		return this.row;
	}
	
	public void setCol(int col)
	{
		this.col = col;
	}
	
	public int getCol()
	{
		return this.col;
	}
	
	private void setFirstPosition(int firstRow, int firstCol)
	{
		this.firstRow = firstRow;
		this.firstCol = firstCol;
	}

	private void setLastPosition(int lastRow, int lastCol)
	{
		this.lastRow = lastRow;
		this.lastCol = lastCol;
	}
	
	public int getFirstRow()
	{
		return this.firstRow;
	}
	
	public int getFirstCol()
	{
		return this.firstCol;
	}

	public int getLastRow()
	{
		return this.lastRow;
	}
	
	public int getLastCol()
	{
		return this.lastCol;
	}
	
	public void setPieceInitial(Icon i)
	{
		this.pieceInitial = i;
	}
	
	public Icon getPieceInitial()
	{
		return this.pieceInitial;
	}
	
	public int getMoves()
	{
		return this.moves;
	}
	
	// others
	public void move(int row, int col)
	{
		setLastPosition(getRow(), getCol());
		board.removePiece(getRow(), getCol());
		board.addPiece(this, row, col);
		setRow(row);
		setCol(col);
		this.moves++;
	}

	public void setCaptured(boolean b)
	{
		this.captured = b;
	}
	
	public boolean isCaptured()
	{
		return this.captured;
	}
	
	public boolean hasSameColor(Piece p2)
	{
		if (p2.getColor() != getColor())
			return false;

		return true;
	}

	public boolean hasSameColor(Color c)
	{
		if (c != getColor())
			return false;

		return true;
	}
	
	public String toString()
	{
		if (getColor() == Color.BLACK)
			return getPieceInitial().toString().toUpperCase();
		if (getColor() == Color.WHITE)
			return getPieceInitial().toString().toLowerCase();

		return "-";
	}
	
	public void capture(Piece pieceToCapture)
	{
		int enemyRow = pieceToCapture.getRow();
		int enemyCol = pieceToCapture.getCol();

		if (!hasSameColor(pieceToCapture)) {

//			remove captured piece
			board.removePiece(enemyRow, enemyCol);
			pieceToCapture.setCaptured(true);

//			move this to captured pieces' position
			setLastPosition(getRow(), getCol());
			board.removePiece(getRow(), getCol());
			board.addPiece(this, enemyRow, enemyCol);
			setRow(enemyRow);
			setCol(enemyCol);
			this.moves++;

			pieceToCapture.unsetPositionFromBoardRange();
		}
		
	}
	
	public void unsetPositionFromBoardRange()
	{
		setRow(Integer.MIN_VALUE);
		setCol(Integer.MIN_VALUE);
	}
	
	public boolean canMove(int row, int col)
	{
		if (isCaptured())
			return false;
		
		return true;
	}

	public boolean canCapture(int row, int col)
	{
//		check if it's captured
		if (isCaptured())
			return false;

//		if it's an empty cell, then it's considered capturable
		if (board.getPieceAt(row, col) == null)
			return true;
		
//		check colors 
		if (board.getPieceAt(row, col).getColor() == getColor())
			return false;

		return true;
	}
}
