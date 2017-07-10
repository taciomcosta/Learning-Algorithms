package test;

import static org.junit.Assert.*;

import org.junit.Test;

import pieces.Piece.Color;
import pieces.Queen;

import chessboard.Chessboard;

public class QueenTest
{
	Chessboard board = new Chessboard();

	Queen queen = new Queen(Color.WHITE, board, 3, 4);
	Queen friend = new Queen(Color.WHITE, board, 0, 7);
	Queen enemy = new Queen(Color.BLACK, board, 7, 0);

	Queen queenAdjacent = new Queen(Color.WHITE, board, 7, 7);
	Queen friendAdjacent = new Queen(Color.WHITE, board, 7, 6);
	Queen enemyAdjacent = new Queen(Color.BLACK, board, 6, 7);

	@Test
	public void test()
	{
		board.printModel();
		
//		can move to an enemy piece
		assertEquals(false, queen.canMove(7, 0));

//		can move to a friend piece
		assertEquals(false, queen.canMove(0, 7));

//		can move to an empty cell 
		assertEquals(true, queen.canMove(0, 1));

//		can capture an enemy
		assertEquals(true, queen.canCapture(7, 0));

//		can capture a friend 
		assertEquals(false, queen.canCapture(0, 7));

//		can capture an empty space 
		assertEquals(true, queen.canCapture(4, 3));
		
//		can capture an enemy straight
		assertEquals(false, queenAdjacent.canCapture(7, 6));

//		can capture a friend straight
		assertEquals(true, queenAdjacent.canCapture(6, 7));
	}
}