package pieces;

import chessboard.Chessboard;
import chessboard.Utils;

public class Rook extends Piece
{

	//METHODS
	//constructor
	public Rook(Color color, Chessboard b, int row, int col)
	{
		super(color, Icon.R, b, row, col);
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

//		check if there's no piece on destination
		if (board.getPieceAt(row, col) != null)
			return false;

//		check if there's no piece between origin and destination
		if (getRow() == row)
			return !is_piece_in_its_col(row, col);
		else if (getCol() == col)
			return !is_piece_in_its_row(row, col);
		
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

		if (getRow() == row)
			return !is_piece_in_its_col(row, col);
		else if (getCol() == col)
			return !is_piece_in_its_row(row, col);
		
		return false;
	}

	@Override
	public void capture(Piece pieceToCapture)
	{
		int enemyRow = pieceToCapture.getRow();
		int enemyCol = pieceToCapture.getCol();

		if (canCapture(enemyRow, enemyCol))
			super.capture(pieceToCapture);
	}
	
	public boolean is_piece_in_its_col(int row, int col)
	{
		// move right
		if (col > getCol()) {
			int i;
			for (i = getCol() + 1; i < col; i++) {
				if (board.getPieceAt(row, i) != null)
					break;
			}

			if (i == col)
				return false;
		// move left
		} else if (getCol() > col){
			int i;
			for (i = getCol() - 1; i > col; i--) {
				if (board.getPieceAt(row, i) != null)
					break;
			}

			if (i == col)
				return false;
		}

		return true;
	}

	public boolean is_piece_in_its_row(int row, int col)
	{
		// move down 
		if (row > getRow()) {
			int i;
			for (i = getRow() + 1; i < row; i++) {
				if (board.getPieceAt(i, col) != null)
					break;
			}

			if (i == row)
				return false;
		// move up
		} else if (getRow() > row){
			int i;
			for (i = getRow() - 1; i > row; i--) {
				if (board.getPieceAt(i, col) != null)
					break;
			}

			if (i == row)
				return false;
		}
		
		return true;
	}
	
	public void castle(int row, int col)
	{
		super.move(row, col);
	}
}
