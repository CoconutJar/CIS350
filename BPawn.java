package chess;

import javax.swing.ImageIcon;

/**
 * BPawn is a class definition for
 * 
 *@author Trevor Tillman & Rashid Nelson
 *
 */
public class BPawn extends Pawn {
    
    public BPawn(Player player) {
        super(player);

        if (player == Player.BLACK) {
            icon = new ImageIcon("bPawn.png");
        } else if (player == Player.WHITE) {
            icon = new ImageIcon("wPawn.png");
        }
    }

    public boolean isValidMove(Move move, IChessPiece[][] board) {
        
    	return move.isValidMoveForBlackPawn(board);
	}

}