package chessgame;

import java.util.ArrayList;

import chessboard.Chessboard;

import pieces.Piece;
import pieces.Piece.Color; // enum colors

import player.Player;

public class ChessGame
{
	private Chessboard board;
	
	private Player player1;
	private Player player2;
	private Player currentPlayer;

        
	public ChessGame()
	{
		board =  new Chessboard();
		player1 = new Player(board, Color.WHITE);
		player2 = new Player(board, Color.BLACK);

		setUp();
		gameLoop();
	}

        public void setUp()
        {
//		set first current player
        	this.currentPlayer = player2;
        }
        
        public void gameLoop()
        {
        	for(;;) {

			setCurrentPlayer();

//	        	while it's not a successfully play
			do {
				board.printModel();
			} 
			while(!currentPlayer.play());
			
//			verify stalemate
			player1.check_stalemate(player2.getPiecesAlive());
			player2.check_stalemate(player1.getPiecesAlive());

//			verify promote
			currentPlayer.check_pawn_promotion();

//		        verify checkmate
			if(player1.king_is_checkmated(player2)) {

				System.out.println(player2.getPiecesColor() + 
						" wins!");
				break;

			} else if(player2.king_is_checkmated(player1)) {

				System.out.println(player1.getPiecesColor() + 
						" wins!");
				break;

//	        	verify check
			} else if (player1.king_is_checked(player2)) {

				System.out.println(player1.getPiecesColor() + 
						" is checked!");

			} else if (player2.king_is_checked(player1)) {

				System.out.println(player2.getPiecesColor() + 
						" is checked!");

			}
        	}
        }
        
        public void end()
        {
		System.exit(0);
        }

	public Player getCurrentPlayer()
	{
		return this.currentPlayer;
	}

//	alternate each player turn
	public void setCurrentPlayer()
	{
		if (getCurrentPlayer() == player1)
			currentPlayer = player2;
		else
			currentPlayer = player1;
	}
}
