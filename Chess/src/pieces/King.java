package pieces;

import java.util.ArrayList;
import java.util.LinkedList;

import player.Player;

import chessboard.Chessboard;
import chessboard.Utils;

public class King extends Piece
{
//	ATTRIBUTES
	private LinkedList<Piece> piecesAttacking = new LinkedList<Piece>();
	private boolean[][] mapWithoutEnemies = new boolean[3][3];

//	METHODS
//	constructor
	public King(Color color, Chessboard b, int row, int col)
	{
		super(color, Icon.K, b, row, col);
	}

//	others
	@Override
	public void capture(Piece pieceToCapture)
	{
		int enemyRow = pieceToCapture.getRow();
		int enemyCol = pieceToCapture.getCol();

		if (canCapture(enemyRow, enemyCol)) {
			super.capture(pieceToCapture);
		}
	}

	@Override
	public void move(int row, int col)
	{
		if (canMove(row, col))
			super.move(row, col);
		else
			castle(row, col);
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
			
//		check if destination is valid for king 
		if (Math.abs(row - getRow()) > 1 || 
				Math.abs(col - getCol()) > 1)
			return false;

//		return true if there's no friend piece in its way
		return true;
	}

	public boolean canMove(int row, int col)
	{
		if (!super.canMove(row, col))
			return false;

//		check if destination is in range
		if (!Utils.inRange(row, col))
			return false;
		
//		check if there's a pice on destination
		if (board.getPieceAt(row, col) != null)
			return false;
			
//		check if destination is valid for king 
		if (Math.abs(row - getRow()) > 1 || 
				Math.abs(col - getCol()) > 1)
			return false;
		
		return true;
	}
	

//	Consider a matrix 3x3 of possible moves, where king is at a(1,1)
//	and a(i,j) is false if it's being attacked	
	public boolean is_checkmated(Player friend, Player enemy)
	{
		Piece[] enemyPieces = enemy.getPieces();
		Piece[] friendPieces = friend.getPieces();
		
//		check if it's being attacked
		if (!is_checked(enemy))
			return false;
		
		if (can_move_safely(friendPieces, enemyPieces))
			return false;

		if (can_be_defended(friendPieces))
			return false;

		System.out.println("can not move safely or be defended");
		return true;

	}
	
	public boolean is_checked(Player enemy)
	{
		Piece[] enemyPieces = enemy.getPieces();
		
//		empty list of pieces attacking
		piecesAttacking.clear();
		
//		for each enemy piece
		for (Piece piece : enemyPieces) {
			
//			check if king current position is a possible capture position
			if (piece.canCapture(getRow(), getCol()) &&
					!piece.isCaptured()) {
				piecesAttacking.add(piece);
			}
		}

//		if there's at least one piece attacking return true
		if (piecesAttacking.size() > 0)
			return true;

		return false;
	}

	public boolean is_checked()
	{
//		get enemy pieces based on color
		LinkedList <Piece> enemyPieces = new LinkedList <Piece>();
		Color enemyColor;

		if (hasSameColor(Color.BLACK))
			enemyColor = Color.BLACK;
		else
			enemyColor = Color.WHITE;

		for (int i = 0; i < 8; i++) {
			for (int j = 0; j < 8; j++) {

				if (board.getPieceAt(i, j) != null) {

					Piece p = board.getPieceAt(i, j);  

					if (!p.hasSameColor(enemyColor) &&
							!p.isCaptured())
						enemyPieces.add(p);
				}
			}
		}
			
//		empty list of pieces attacking
		piecesAttacking.clear();
		
//		for each enemy piece
		for (Piece piece : enemyPieces) {
			
//			check if king current position is "capturable"
			if (piece.canCapture(getRow(), getCol())) {

				piecesAttacking.add(piece);
			}
		}
		
//		if there's at least one piece attacking return true
		if (piecesAttacking.size() > 0)
			return true;

		return false;
	}
	
	public boolean would_be_checked(ArrayList<Piece> enemies, 
			int kingRow, 
			int kingCol)
	{
//		check if it's out of range
		if (!Utils.inRange(kingRow, kingCol))
			return true;
		
//		if there's an enemy piece on cell being checked
		Piece tmpEnemy = board.getPieceAt(kingRow, kingCol); 
		if (tmpEnemy != null && tmpEnemy.getColor() != getColor()) {

//			remove it from board, temporarily
			board.removePiece(kingRow, kingCol);
			tmpEnemy.setRow(Integer.MIN_VALUE);
			tmpEnemy.setCol(Integer.MIN_VALUE);
			tmpEnemy.setCaptured(true);


		}
		
//		for each enemy piece
		for (Piece p : enemies) {

//			verify if it could capture king
			if (p.canCapture(kingRow, kingCol)) {
				
//				add piece removed back
				if (tmpEnemy != null && tmpEnemy.getColor() != getColor()) {
					board.addPiece(tmpEnemy, kingRow, kingCol);
					tmpEnemy.setRow(kingRow);
					tmpEnemy.setCol(kingCol);
					tmpEnemy.setCaptured(false);
				}

				return true;
			}
		}

//		add piece removed back
		if (tmpEnemy != null && tmpEnemy.getColor() != getColor()) {
			board.addPiece(tmpEnemy, kingRow, kingCol);
			tmpEnemy.setRow(kingRow);
			tmpEnemy.setCol(kingCol);
			tmpEnemy.setCaptured(false);
		}
		
		return false;
	}
	
	private boolean can_be_defended(Piece[] friendPieces)
	{
//		check if number of pieces attacking directly > 1
		if (piecesAttacking.size() > 1)
			return false;
		
//		check if gap to attacked cell on mapWithoutEnemies can be protected
		return can_protect_cells(friendPieces);
	}
	
	private boolean can_move_safely(Piece[] friendPieces, Piece[] enemyPieces)
	{
		boolean[][] map;

//		map king possible moves without considering friend/enemy pieces
		map = map_possible_moves_or_captures();

//		consider friend pieces
		map = map_friends(friendPieces, map);
		
//		backup mapWithoutEnemies
		for (int i = 0; i < 3; i++) {
			for (int j = 0; j < 3; j++) {
				mapWithoutEnemies[i][j] = map[i][j];
			}
		}

//		consider enemy pieces
		map = map_enemies(enemyPieces, map);

//		return true if there's a possible safe move
		for (boolean[] rows : map)
			for (boolean isPossibleMove: rows)
				if (isPossibleMove)
					return true;

		return false;
	}
	
/* MAP FOR AN ATTACKING FROM
 * knight    bishop & others
 * F F F     F F F
 * F T F     F T F
 * F F F     T F F
 * 
 */
	private boolean can_protect_cells(Piece[] friendPieces)
	{
		Piece piece = (Piece) piecesAttacking.getFirst();
		int enemyRow = piece.getRow();
		int enemyCol = piece.getCol();

//		check if attacking piece can be captured
		for (Piece friend : friendPieces)
			if (friend.canCapture(enemyRow, enemyCol)
					&& friend.getPieceInitial() != Icon.K
					&& !friend.isCaptured())
				return true;
		
//		if couldn't be captured and it's a knight
		if (piece.getPieceInitial() == Icon.N)
			return false;
		
//		if it wasn't a knight so M[0][0] can't be attacked directly
		mapWithoutEnemies[0][0] = false;
		
//		since it's not a knight: 

//		find gap
		int rowGap = 0;
		int colGap = 0;
		for (int i = 0; i < 3; i++) {
			for (int j = 0; j < 3; j++) {
				if (mapWithoutEnemies[i][j] == true) {
					rowGap = i;
					colGap = j;
					break;
				}
			}
		}
		
//		trace attack direction
		int vertical = trace_row(rowGap, enemyRow);
		int horizontal = trace_col(colGap, enemyCol);
		System.out.printf("attack direction: %d, %d\n", vertical, horizontal);
		
		rowGap = getRow() + vertical;
		colGap = getCol() + horizontal;
		
//		check if some friend can move to the gap
		while (rowGap != enemyRow && colGap != enemyCol) {
			for (Piece friend : friendPieces)
				if (friend.canMove(rowGap, colGap) &&
						friend.getPieceInitial() != Icon.K
						&& !friend.isCaptured())
					return true;

			rowGap += vertical;
			colGap += horizontal;
		}
		
		
//		finally, if friend couldn't block it
		return false;
	}
	
	private boolean[][] map_possible_moves_or_captures()
	{
		boolean[][] map = new boolean[3][3];

//		for each direction
		for (int i = -1; i < 2; i++) {
			for (int j = -1; j < 2; j++) {
//				set propositions
				boolean p = canMove(getRow() + i, 
						getCol() + j); 
				boolean q = canCapture(getRow() + i, 
						getCol() + j);

//				add their result to map
				map[1 + i][1 + j] = p || q;
			}
		}
		
		return map;
	}
	
	private boolean[][] map_friends(Piece[] friendPieces, boolean[][] map)
	{
		
//		for each direction around
		for (int i = -1; i < 2; i++) {
			for (int j = -1; j < 2; j++) {

//				get piece, if there's some
				Piece p = board.getPieceAt(getRow() + i,
						getCol() + j); 
				if (p != null) {

//					compare colors
					if (p.hasSameColor(getColor()))
						map[1 + i][1 + j] = false;
				}
			}
		}
		
		return map;
	}

	private boolean[][] map_enemies(Piece[] enemyPieces, boolean[][] map)
	{
//		current position
		map[1][1] = false;

//		for each enemy piece
		for (Piece piece : enemyPieces) {

//			for each king direction around
			for (int i = -1; i < 2; i++) {
				for (int j = -1; j < 2; j++) {
					if (map[1 + i][1 + j]) {

//						set propositions to
//						P(n) = cells being attacked
						boolean p = piece.canCapture(
								getRow() + i, 
								getCol() + j);
						boolean q = piece.isCaptured();

//						P(n) = true -> map[i][j] = False
						map[1 + i][1 + j] = !(p && q);
					}
				}
			}
		}
		
		return map;
	}
	
	private int trace_row(int rowGap, int enemyRow)
	{
		if (rowGap == 0)
			return -1;
		else if (rowGap == 1)
			return 0;

		return 1;
	}

	private int trace_col(int colGap, int enemyCol)
	{
		if (colGap == 0)
			return -1;
		else if (colGap == 1)
			return 0;

		return 1;
	}
	
	private void castle(int row, int col)
	{
//		check if destination is proper for castle
		if (Math.abs(getCol() - col) != 2 || 
				Math.abs(getRow() - row) != 0)
			return;

//		check if it's its first move
		if (getMoves() > 0)
			return;

//		check if king is checked
		if(is_checked())
			return;
		
//		check direction to castle
		int direction = col < getCol() ? -1 : 1;

//		check if there's a rook
		Rook rook;
		if (direction == 1) {
			
			if (board.getPieceAt(row, 7) == null)
				return;
			
			rook = (Rook) board.getPieceAt(row, 7);
			
//		left
		} else {
			
			if (board.getPieceAt(row, 0) == null)
				return;
			
			rook = (Rook) board.getPieceAt(row, 0);
		}

//		check if that's a friend piece and a rook
		if (rook.getColor() != getColor() || 
				rook.getPieceInitial() != Icon.R)
			return;

//		check if it's rook first move
		if (rook.getMoves() > 0)
			return;
		
//		for each cell between rook and king
		int i = getCol() + direction;
		for (; i != rook.getCol(); i+=direction) {

//			check if there's no piece between king and rook
			if (board.getPieceAt(getRow(), i) != null)
				return;
		}

//		finally, move king
		super.move(row, col);

//		move rook
		rook.castle(getRow(), getCol() - direction);
	}
}
