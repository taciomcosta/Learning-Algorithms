package chessboard;

public class Utils
{
	public static final int BOARD_LENGTH = 8;

	public final static boolean inRange(int row, int col)
	{
        	if (row < 0 || row > 7 || col < 0 || col > 7)
        		return false;
        	return true;
	}
}
