package chess;

import javax.swing.ImageIcon;

/**
 * WPawn is a class definition for
 * 
 * @author Trevor Tillman & Rashid Nelson 
 *
 */
public class WPawn extends Pawn{

	public WPawn(Player player) {
		super(player);
		
		if (player == Player.BLACK)
		{
			icon = new ImageIcon("bPawn.png");
		}
		else if (player == Player.WHITE)
		{
			icon = new ImageIcon("wPawn.png");
		}
	}
	public WPawn (Player player, Location location) {
					super(player);
				}

	public boolean isValidMove(Move move, IChessPiece[][] board) {

		return 
				 move.isValidMoveForWhitePawn(board);
}
}