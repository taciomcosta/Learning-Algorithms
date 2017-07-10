package test;

import static org.junit.Assert.*;

import org.junit.Test;

import pieces.Piece;
import pieces.Piece.Color;
import pieces.King;
import pieces.Piece.Icon;
import pieces.Rook;
import player.Player;

import chessboard.Chessboard;
import chessboard.Utils;

public class KingTest
{
	Chessboard board = new Chessboard();

	Player pFriend = new Player(board, Color.WHITE);
	Player pEnemy = new Player(board, Color.BLACK);

	King king = (King) pFriend.getPiece(15);

	@Test
	public void test()
	{
//		moveAndCaptureTest();
//		checksTest();
		stalemateTest();
		
		board.printModel();
	}
	
	public void checksTest()
	{
//		set game state
		move(king, 3, 4);
		move(pEnemy.getPiece(8), 3, 5); //rook
		move(pEnemy.getPiece(9), 3, 3); //rook
		move(pEnemy.getPiece(14), 5, 4); //queen
		move(pEnemy.getPiece(10), 2, 3); //bishop

//		verify check mate
//		assertEquals(false, king.is_checkmated(pFriend, pEnemy));
//		assertEquals(true, king.can_move_safely(pFriend.getPieces(), pEnemy.getPieces()));
//		assertEquals(false, king.can_be_defended(pFriend.getPieces()));


//		verify check
//		assertEquals(true, king.is_checked());
		
	}

	public void moveAndCaptureTest()
	{}
	
	public void stalemateTest()
	{
//		prepare to check stalemate
		for (int i = 0; i < 16; i++)
			if (i != 8 && i != 15 && i != 10) {
				capture(pEnemy.getPiece(i));
				capture(pFriend.getPiece(i));
			}
		capture(pFriend.getPiece(8));
		capture(pEnemy.getPiece(10));

		move(pEnemy.getPiece(15), 2, 1);
		move(pEnemy.getPiece(8), 0, 7);
		move(pFriend.getPiece(10), 0, 1);
		move(king, 0, 0);
		
		pFriend.check_stalemate(pEnemy.getPiecesAlive());
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

	public void capture(Piece pieceToCapture)
	{
		int enemyRow = pieceToCapture.getRow();
		int enemyCol = pieceToCapture.getCol();

//		remove captured piece
		board.removePiece(enemyRow, enemyCol);
		pieceToCapture.setCaptured(true);
		pieceToCapture.unsetPositionFromBoardRange();
	}
}