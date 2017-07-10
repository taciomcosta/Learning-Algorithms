package player;

import java.util.ArrayList;
import java.util.Scanner;

import chessboard.Chessboard;
import chessboard.Utils;

import pieces.Bishop;
import pieces.King;
import pieces.Knight;
import pieces.Pawn;
import pieces.Piece;
import pieces.Piece.Color;
import pieces.Piece.Icon;
import pieces.Queen;
import pieces.Rook;

public class Player
{
	private Scanner input = new Scanner(System.in);
	private int row;
	private int col;

	private Color piecesColor;
	private Piece[] pieces = new Piece[16];
	private Chessboard board;

//	constructor
	public Player(Chessboard b, Color piecesColor)
	{
		setPiecesColor(piecesColor);
		setChessboard(b);
		setPieces();
	}

//	others
	public Piece[] getPieces()
	{
		return pieces;
	}
	
	public Piece getPiece(int i)
	{
		return pieces[i];
	}

	private void setPieces()
	{
//		FOR BLACK PIECES
		if (getPiecesColor() == Color.BLACK) {
//			set pawns
			for (int i = 0; i < 8; i++) {
				pieces[i] = new Pawn(Color.BLACK, board, 1, i);
			}
			
//	              	set Rooks
			pieces[8] = new Rook(Color.BLACK, board, 0, 0);
			pieces[9] = new Rook(Color.BLACK, board, 0, 7);

//	                set Bishops 
			pieces[10] = new Bishop(Color.BLACK, board, 0, 2);
			pieces[11] = new Bishop(Color.BLACK, board, 0, 5);

//	              	set Knight 
			pieces[12] = new Knight(Color.BLACK, board, 0, 6);
			pieces[13] = new Knight(Color.BLACK, board, 0, 1);
			
//	            	set Queen
			pieces[14] = new Queen(Color.BLACK, board, 0, 3);
			
//	              	set Kings
			pieces[15] = new King(Color.BLACK, board, 0, 4);

//		FOR WHITE PIECES
		} else {
//			set pawns
			for (int i = 0; i < 8; i++) {
				pieces[i] = new Pawn(Color.WHITE, board, 6, i);
			}
			
//	              	set Rooks
			pieces[8] = new Rook(Color.WHITE, board, 7, 0);
			pieces[9] = new Rook(Color.WHITE, board, 7, 7);

//	                set Bishops 
			pieces[10] = new Bishop(Color.WHITE, board, 7, 2);
			pieces[11] = new Bishop(Color.WHITE, board, 7, 5);

//	              	set Knight 
			pieces[12] = new Knight(Color.WHITE, board, 7, 6);
			pieces[13] = new Knight(Color.WHITE, board, 7, 1);
			
//	            	set Queen
			pieces[14] = new Queen(Color.WHITE, board, 7, 3);
			
//	              	set Kings
			pieces[15] = new King(Color.WHITE, board, 7, 4);
		}
	}
	
	public Color getPiecesColor()
	{
		return this.piecesColor;
	}
	
	public ArrayList<Piece> getCapturedPieces()
	{
		ArrayList<Piece> capturedPieces = new ArrayList<Piece>();

//		for each piece
		for (Piece p : pieces) {

//			add to array, if it's captured
			if (p.isCaptured())
				capturedPieces.add(p);
		}
		
		return capturedPieces;
	}

	public ArrayList<Piece> getPiecesAlive()
	{
		ArrayList<Piece> capturedPieces = new ArrayList<Piece>();

//		for each piece
		for (Piece p : pieces) {

//			add to array, if it's captured
			if (!p.isCaptured())
				capturedPieces.add(p);
		}
		
		return capturedPieces;
	}
	
	private void setPiecesColor(Color piecesColor)
	{
		this.piecesColor = piecesColor;
	}
	
	public boolean king_is_checked(Player enemy)
	{
		King k = (King) getPiece(15);

		return k.is_checked(enemy);
	}

	public boolean king_is_checkmated(Player enemy)
	{
		King k = (King) getPiece(15);

		return k.is_checkmated(this, enemy);
	}
	
	public void setChessboard(Chessboard board)
	{
		this.board = board;
	}
	

	public void castPosition(String pos)
	{
//		cast row and col. Ex: A5 -> col=0, row=3
		col = pos.charAt(0) - 97;
		row = 8 - Integer.parseInt(pos.substring(1, 2));
	}

	public boolean play()
	{
//		get move
		System.out.print("(" + getPiecesColor() + ") Piece to move: ");
		String pos = input.nextLine();
		castPosition(pos);
		
//		try to select piece
		Piece p = board.getPieceAt(row, col); 
		if(p ==  null)
			return false;
		
//		check if it's a friendly piece
		if (!p.hasSameColor(getPiecesColor()))
			return false;

//		get destination
		System.out.print("(" + getPiecesColor() + ") Move to: ");
		pos = input.nextLine();
		castPosition(pos);
		
//		if there's a piece in destination
		if (board.getPieceAt(row, col) == null) {

//			if can move to destination, then move and end play
			if(p.canMove(row, col)) {
				p.move(row, col);
				System.out.println("Piece moved!");
				return true;
			}

		} else {

//			if can capture, then capture and end play
			if(p.canCapture(row, col)) {
				p.capture(board.getPieceAt(row, col));
				System.out.println("Piece captured!");
				return true;
			}
		}
		
		
		return false;
	}
	
	public void check_pawn_promotion()
	{
		ArrayList<Piece> capturedPieces;

//		for each pawn
		for (int i = 0; i < 8; i++) {
			
			Pawn pawn = (Pawn) pieces[i];

//			check if can be promoted
			if (pawn.can_be_promoted()) {
				
//				get captured pieces
				capturedPieces = getCapturedPieces();
				
//				if there's no captured piece, return
				if (capturedPieces.size() == 0)
					return;
				
//				print captured pieces' initials
				for (Piece piece : capturedPieces) {
					System.out.print(
							piece.getPieceInitial() +
							" ");
				}
				System.out.println();


//				get player input 
				System.out.print("Promote to: ");
				String userChoice = input.nextLine();
				

//				select piece
				Piece pieceToPromote = null; 
				for (Piece p : capturedPieces) {

					String pInitial = p.getPieceInitial().
							toString();

					if (userChoice.equals(pInitial)) {
						
						pieceToPromote = p;
						break;
					}
				}
				
//				finally, promote pawn
				pawn.promote(pieceToPromote);
			}
		}
	}
	
	public void check_stalemate(ArrayList <Piece> enemyPieces)
	{
//		get its own king
		King king = (King) pieces[15];
		
//		if king is checked, then it can't be a stalemate
		if (king.is_checked())
			return;
		
//		get friend pieces alive that can move
		ArrayList <Piece> moveable = new ArrayList<Piece>();
		for (Piece p : getPiecesAlive()) {

			for (int i = 0; i < Utils.BOARD_LENGTH; i++) {
				for (int j = 0; j < Utils.BOARD_LENGTH; j++) {
					if (p.getPieceInitial() != Icon.K &&
							p.canMove(i, j))
						if (!moveable.contains(p))
							moveable.add(p);
				}
			}
		}
		
//		for each piece that can move 
		int wouldLetKingChecked = 0;
		for (Piece p : moveable) {
			
//			backup piece pos, for removing it temporarily
			int backupRow = p.getRow();
			int backupCol = p.getCol();

//			remove piece from board
			board.removePiece(backupRow, backupCol);
			p.setRow(Integer.MIN_VALUE);
			p.setCol(Integer.MIN_VALUE);
			p.setCaptured(true);
			
//			verify if king could be checked
//			without piece
			boolean isChecked = king.is_checked();

//			add piece back
			board.addPiece(p, backupRow, backupCol);
			p.setRow(backupRow);
			p.setCol(backupCol);
			p.setCaptured(false);
			
			if (isChecked)
				wouldLetKingChecked++;
		}
		
//		check if all pieces that can move would let king in check
		if (moveable.size() != wouldLetKingChecked) 
			return;

//		check if king can move safely
		for (int i = -1; i < 2; i++) {
			for (int j = -1; j < 2; j++) {

//				set propositions
				boolean p = king.would_be_checked(enemyPieces, 
						king.getRow() + i, 
						king.getCol() + j); 
				boolean q = board.getPieceAt(i, j) == null;
				boolean r = i != 0 || j != 0;

//				check them
				if (!p && q && r) {
					
					System.out.println("can move safely " + (king.getRow() + i) + " " + (king.getCol() + j));
					return;
				}
			}
		}

		
//		finally, declares stalemate and exit
		System.out.println("Stalemate");
		System.exit(0);
	}
}
