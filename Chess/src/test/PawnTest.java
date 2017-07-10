package test;

import static org.junit.Assert.*;

import org.junit.Test;

import pieces.Piece;
import pieces.Piece.Color;
import pieces.Pawn;

import chessboard.Chessboard;

public class PawnTest
{
	Chessboard board = new Chessboard();

	Pawn pawn = new Pawn(Color.WHITE, board, 4, 4);
	Pawn friend = new Pawn(Color.WHITE, board, 1, 5);
	Pawn enemy = new Pawn(Color.BLACK, board, 1, 4);

	Pawn pawn2 = new Pawn(Color.WHITE, board, 2, 5);

	Pawn pawn3 = new Pawn(Color.WHITE, board, 3, 0);
	Pawn friend1 = new Pawn(Color.WHITE, board, 0, 0);
	Pawn enemy1 = new Pawn(Color.BLACK, board, 3, 1);
	
	Pawn pawn4 = new Pawn(Color.WHITE, board, 0, 7);

	@Test
	public void test()
	{

//		can move to an empty cell 
		assertEquals(true, pawn.canMove(3, 4));

//		can move 2 cells on 1st move
		assertEquals(true, pawn.canMove(2, 4));
		
		move(pawn, 2, 4);
		
//		can move 2 cells after 1st move
		assertEquals(false, pawn.canMove(0, 4));

//		can move to an enemy piece
		assertEquals(false, pawn.canMove(1, 4));

//		can move to a friend piece
		assertEquals(false, pawn2.canMove(1, 5));

//		can capture an enemy by diagonal
		assertEquals(true, pawn2.canCapture(1, 4));

//		can capture a friend by diagonal
		assertEquals(false, pawn.canCapture(1, 5));

//		can capture an empty space by diagonal
		assertEquals(true, pawn.canCapture(1, 3));
		
//		can capture an enemy by en passant
//		enemy1.moves++;
//		assertEquals(true, pawn3.canCaptureEnPassant(3, 1));
		
//		can promote pawn
		assertEquals(true, pawn4.can_be_promoted());

		board.printModel();
	}

	public void move(Piece p, int row, int col)
	{
//		p.setLastPosition(p.getRow(), p.getCol());
		board.removePiece(p.getRow(), p.getCol());
		board.addPiece(p, row, col);
		p.setRow(row);
		p.setCol(col);
//		p.moves++;
	}
}