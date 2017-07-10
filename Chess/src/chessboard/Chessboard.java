/*
 * The archives of this project may have more comments than it need, because
 * I was learning several new stuffs and developing it at the same time
 * 
 * DESCRIPTION:
 *  - Control of the Chessboard. 
 *  - Executes the chessboard operation using the Chessboard View
 */
package chessboard;

import chessboard.cell.Cell;

import pieces.Piece;

@SuppressWarnings("serial") // supress the serial warning of a final class
public final class Chessboard
{
	private ChessboardModel model;
	private Cell[][] cells;

        public Chessboard()
        {
        	model = new ChessboardModel();
        	cells = new Cell[Utils.BOARD_LENGTH][Utils.BOARD_LENGTH];

//        	initialize empty cells and add them to chessboard model
        	for (int i = 0; i < Utils.BOARD_LENGTH; i++) {
        		for (int j = 0; j < Utils.BOARD_LENGTH; j++) {
        			cells[i][j] = new Cell();
        			model.setCell(cells[i][j].getModel(), i, j);
        		}
		}
        }
        
        public void addPiece(Piece p, int row, int col)
        {
        	cells[row][col].setPiece(p);

        }
        
        public void removePiece(int row, int col)
        {
//        	get piece
        	Piece p = getPieceAt(row, col);

//        	remove piece from cell
        	cells[row][col].unsetPiece();
        }
        
        public Piece getPieceAt(int row, int col)
        {
        	if (!Utils.inRange(row, col))
        		return null;

        	return cells[row][col].getPiece();
        }
        
        public void printModel()
        {
        	model.print();
        }
}
