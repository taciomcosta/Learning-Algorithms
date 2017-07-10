package test;

import static org.junit.Assert.*;

import org.junit.Test;

import pieces.Piece.Color;
import pieces.Rook;

import chessboard.Chessboard;

public class RookTest
{
	Chessboard board = new Chessboard();

	Rook rook = new Rook(Color.WHITE, board, 7, 7);
	Rook friend = new Rook(Color.WHITE, board, 0, 7);
	Rook enemy = new Rook(Color.BLACK, board, 7, 0);

	Rook rookAdjacent = new Rook(Color.WHITE, board, 5, 5);
	Rook friendAdjacent = new Rook(Color.WHITE, board, 5, 4);
	Rook enemyAdjacent = new Rook(Color.BLACK, board, 5, 6);

	@Test
	public void test()
	{
		board.printModel();
		
//		can move to an enemy piece
		assertEquals(false, rook.canMove(7, 0));

//		can move to a friend piece
		assertEquals(false, rook.canMove(7, 0));

//		can move to an empty cell 
		assertEquals(true, rook.canMove(7, 6));
		
//		can capture an enemy
		assertEquals(true, rook.canCapture(7, 0));

//		can capture a friend 
		assertEquals(true, rook.canCapture(7, 0));

//		can it capture an empty space? 
		assertEquals(true, rook.canCapture(7, 0));
		
//		can move to an adjacent cell
		assertEquals(false, rookAdjacent.canMove(5, 4));

//		can capture in an adjacent cell
		assertEquals(true, rookAdjacent.canCapture(5, 6));
	}
}
