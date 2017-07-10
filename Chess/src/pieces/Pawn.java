package pieces;

import chessboard.Chessboard;
import chessboard.Utils;

public final class Pawn extends Piece
{
	// constructor
	public Pawn(Color color, Chessboard b, int row, int col)
	{
		super(color, Icon.P, b, row, col);
	}

	//others
	@Override
	public void move(int row, int col)
	{
		if (canMove(row, col))
			super.move(row, col);
	}
	

	@Override
	public boolean canMove(int row, int col)
	{
		if (!super.canMove(row, col))
			return false;

//		check if destination is in range
		if (!Utils.inRange(row, col))
			return false;
		
//		check if there's piece on destination
		if (board.getPieceAt(row, col) != null)
			return false;
		
//		check if it's moving to the same column
		if (col != getCol())
			return false;
		
//		check if it's a valid first move
		if (getMoves() == 0 && Math.abs(row - getRow()) == 2)
			return true;
		
//		check if it's a valid row, according to its color
		if (getColor() == Color.BLACK) {
			if (row - getRow() != 1)
				return false;
			
		} else {
			if (row - getRow() != -1)
				return false;
		}

		return true;
	}

	@Override
	public void capture(Piece pieceToCapture)
	{
		int enemyRow = pieceToCapture.getRow();
		int enemyCol = pieceToCapture.getCol();

//		check the kind of capture
		if (canCaptureByDiagonal(enemyRow, enemyCol)) {

			super.capture(pieceToCapture);

		} else if (canCaptureEnPassant(enemyRow, enemyCol)) {
			

			super.capture(pieceToCapture);

//			move one cell forward, since it's en passant
			int rowToMove = (enemyRow + getRow()) / 2;
			int colToMove = enemyCol;
			super.move(rowToMove, colToMove);
		}
	}
	
	@Override
	public boolean canCapture(int row, int col)
	{
//		check if destination is in range
		if (!Utils.inRange(row, col))
			return false;

//		check color
		if (!super.canCapture(row, col))
			return false;

		if (canCaptureByDiagonal(row, col))
			return true;

		if (canCaptureEnPassant(row, col))
			return true;
		
		return false;
	}
	
	private boolean canCaptureByDiagonal(int row, int col)
	{
//		check if it is on its diagonal
		if (Math.abs(row - getRow()) == 1 &&
				Math.abs(col - getCol()) == 1) {
//			for black pawn
			if ((hasSameColor(Color.BLACK) && getRow() < row))
				return true;
//			for white pawn
			if ((hasSameColor(Color.WHITE) && getRow() > row))
				return true;
		}

		return false;
	}

	private boolean canCaptureEnPassant(int enemyRow, int enemyCol)
	{
//		check if there's a piece on destination,
//		since En Passant is only applied between pawns
//		this method shouldn't be used for verifying if empty cells
//		can be captured by En Passant
		if (board.getPieceAt(enemyRow, enemyCol) == null)
			return false;

//		get enemy piece
		Piece pieceToCapture = board.getPieceAt(enemyRow, enemyCol);

//		if enemy isn't a pawn, then el passant can't be applied to it
		if (pieceToCapture.getPieceInitial() != Icon.P)
			return false;
		
//		check number of moves of enemy
		if (pieceToCapture.getMoves() != 1)
			return false;
		
//		check if enemy piece is in a neighbour column
		if (Math.abs(getCol() - enemyCol) != 1)
			return false;
		
//		check distance of rows
		if (Math.abs(getRow() - enemyRow) != 0)
			return false;

		return true;
	}

	public boolean can_be_promoted()
	{
		if (hasSameColor(Color.WHITE) && getRow() == 0)
			return true;
		
		if (hasSameColor(Color.BLACK) && getRow() == 7)
			return true;
		
		return false;
	}
	
	public void promote(Piece p)
	{
//		set pawn captured
		setCaptured(true);
		
//		set piece to be changed alive 
		p.setCaptured(false);
		
//		remove pawn from board
		board.removePiece(getRow(), getCol());

//		add piece to pawn position
		board.addPiece(p, getRow(), getCol());

//		set piece position 
		p.setRow(getRow());
		p.setCol(getCol());
		
//		unset pawn position
		unsetPositionFromBoardRange();
	}
}
