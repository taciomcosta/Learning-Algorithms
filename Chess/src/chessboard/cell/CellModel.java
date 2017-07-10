package chessboard.cell;

import pieces.Piece;

public class CellModel
{
	// ATTRIBUTES
	private Piece piece = null;

	// METHODS
	//constructor
	public CellModel()
	{}
	
	public CellModel(Piece pice)
	{
		setPiece(piece);
	}

	// setters and getters
	public void setPiece(Piece p)
	{
		this.piece = p;
	}
	
	public Piece getPiece()
	{
		return this.piece;
	}
	
	public void unsetPiece()
	{
		this.piece = null;
	}
}
