package test;

import static org.junit.Assert.*;

import org.junit.Test;

import pieces.Piece.Color;
import pieces.Knight;

import chessboard.Chessboard;

public class KnightTest
{
	Chessboard board = new Chessboard();

	Knight knight = new Knight(Color.WHITE, board, 3, 4);

	Knight knight1 = new Knight(Color.WHITE, board, 4, 4);
	Knight friend = new Knight(Color.WHITE, board, 5, 6);
	Knight enemy = new Knight(Color.BLACK, board, 3, 6);

	@Test
	public void test()
	{
		board.printModel();
		
//		can move to any L empty cell 
		assertEquals(true, knight.canMove(1, 3));
		assertEquals(true, knight.canMove(1, 5));
		assertEquals(true, knight.canMove(2, 2));
		assertEquals(true, knight.canMove(2, 6));
		assertEquals(true, knight.canMove(4, 6));
		assertEquals(true, knight.canMove(4, 2));
		assertEquals(true, knight.canMove(5, 3));
		assertEquals(true, knight.canMove(5, 5));

//		can move to a no L empty cell
		assertEquals(false, knight.canMove(4, 5));
		assertEquals(false, knight.canMove(3, 5));
		assertEquals(false, knight.canMove(4, 4));

//		can move to a friend piece
		assertEquals(false, knight1.canMove(5, 6));

//		can capture a friend 
		assertEquals(false, knight1.canCapture(5, 6));

//		can move to an enemy piece
		assertEquals(false, knight1.canMove(3, 6));

//		can capture an enemy
		assertEquals(true, knight1.canCapture(3, 6));

//		can capture an empty space 
		assertEquals(true, knight1.canCapture(6, 3));
	}
}
