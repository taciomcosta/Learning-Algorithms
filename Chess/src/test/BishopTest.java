package test;

import static org.junit.Assert.*;

import org.junit.Test;

import pieces.Piece.Color;
import pieces.Bishop;

import chessboard.Chessboard;

public class BishopTest
{
	Chessboard board = new Chessboard();

	Bishop bishop = new Bishop(Color.WHITE, board, 3, 4);
	Bishop friend = new Bishop(Color.WHITE, board, 0, 7);
	Bishop enemy = new Bishop(Color.BLACK, board, 7, 0);

	Bishop bishopAdjacent = new Bishop(Color.WHITE, board, 5, 5);
	Bishop friendAdjacent = new Bishop(Color.WHITE, board, 6, 6);
	Bishop enemyAdjacent = new Bishop(Color.BLACK, board, 4, 4);

	@Test
	public void test()
	{
		board.printModel();
		
//		can move to an enemy piece
		assertEquals(false, bishop.canMove(7, 0));

//		can move to a friend piece
		assertEquals(false, bishop.canMove(0, 7));

//		can move to an empty cell 
		assertEquals(true, bishop.canMove(5, 4));

//		can capture an enemy
		assertEquals(true, bishop.canCapture(7, 0));

//		can capture a friend 
		assertEquals(false, bishop.canCapture(0, 7));

//		can capture an empty space 
		assertEquals(true, bishop.canCapture(4, 3));
		
//		can move to an adjacent cell
		assertEquals(true, bishopAdjacent.canMove(4, 6));

//		can capture in an adjacent cell
		assertEquals(true, bishopAdjacent.canCapture(4, 4));
	}
}