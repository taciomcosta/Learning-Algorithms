package pieces;

import chessboard.Chessboard;
import chessboard.Utils;

public class Queen extends Piece
{

	//METHODS
	//constructor
	public Queen(Color color, Chessboard b, int row, int col)
	{
		super(color, Icon.Q, b, row, col);
	}

	//others
	@Override
	public void capture(Piece pieceToCapture)
	{
		int enemyRow = pieceToCapture.getRow();
		int enemyCol = pieceToCapture.getCol();

//		capture straight
		if (canCapture(enemyRow, enemyCol))
			super.capture(pieceToCapture);
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

//		check if it's a valid move for queen
//		diagonal			
		if (Math.abs(getCol() - col) == Math.abs(row - getRow())) {
			if (!is_piece_in_diagonal(row, col))
				return true;
//		straight
		} else if (getRow() == row || getCol() == col) {
			if (!is_piece_in_straight(row, col))
				return true;
		}

		return false;
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

//		check if it's a valid move for queen
//		diagonal			
		if (Math.abs(getCol() - col) == Math.abs(row - getRow())) {
			if (!is_piece_in_diagonal(row, col))
				return true;
//		straight
		} else if (getRow() == row || getCol() == col) {
			if (!is_piece_in_straight(row, col))
				return true;
		}

		return false;
	}

	@Override
	public void move(int row, int col)
	{
		if (!canMove(row, col))
			return;

		if (getRow() == row || getCol() == col)
			moveStraight(row, col);
		else if (Math.abs(getCol() - col) == Math.abs(row - getRow()))
			moveDiagonally(row, col);
	}

	private void moveStraight(int row, int col)
	{
		if (!is_piece_in_straight(row, col))
			super.move(row, col);
	}

	private void moveDiagonally(int row, int col)
	{
		if (!is_piece_in_diagonal(row, col))
			super.move(row, col);
	}

	
	private boolean is_piece_in_diagonal(int row, int col)
	{
		//down-right
		if (row > getRow() && col > getCol()) {
			row--;
			col--;
			while (row > getRow()) {
				if (board.getPieceAt(row, col) != null) {
					return true;
				}
				row--;
				col--;
			}

		//down-left
		} else if (row > getRow() && col < getCol()) {
			row--;
			col++;
			while (row > getRow()) {
				if (board.getPieceAt(row, col) != null) {
					return true;
				}
				row--;
				col++;
			}

		//up-right
		} else if (row < getRow() && col > getCol()) {
			row++;
			col--;
			while (row < getRow()) {
				if (board.getPieceAt(row, col) != null) {
					return true;
				}
				row++;
				col--;
			}

		//up-left
		} else if (row < getRow() && col < getCol()) {
			row++;
			col++;
			while (row < getRow()) {
				if (board.getPieceAt(row, col) != null) {
					return true;
				}
				row++;
				col++;
			}
		}

		return false;
	}
	
	private boolean is_piece_in_straight(int row, int col)
	{
		// checks from which side its coming
		if (row > getRow())
			row--;
		else if (row < getRow())
			row++;
		
		if (col > getCol())
			col--;
		else if (col < getCol())
			col++;
		
		// check if there's any piece in its way
		while (row > getRow()) {
			if (board.getPieceAt(row, col) != null)
				return true;
			row--;
		}

		while (row < getRow()) {
			if (board.getPieceAt(row, col) != null)
				return true;
			row++;
		}

		while (col > getCol()) {
			if (board.getPieceAt(row, col) != null)
				return true;
			col--;
		}

		while (col < getCol()) {
			if (board.getPieceAt(row, col) != null)
				return true;
			col++;
		}
		
		return false;
	}
	

}