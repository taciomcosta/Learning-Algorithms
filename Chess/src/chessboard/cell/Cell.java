package chessboard.cell;

import pieces.Piece;

public final class Cell
{
	// ATTRIBUTES
	private CellModel model;

	// METHODS
	//constructor
	public Cell()
	{
		model = new CellModel();
	}
	
	public Cell(Piece piece)
	{
		model = new CellModel(piece);
	}

	// setters and getters
	public void setPiece(Piece p)
	{
		model.setPiece(p);
	}
	
	public Piece getPiece()
	{
		return model.getPiece();
	}
	
	public void unsetPiece()
	{
		model.unsetPiece();
	}
	
	public CellModel getModel()
	{
		return this.model;
	}
}
