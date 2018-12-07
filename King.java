package chess;

import javax.swing.ImageIcon;
/**
 * King is a class definition for
 * 
 * @author Trevor Tillman & Rashid Nelson
 *
 */
public class King extends ChessPiece {

    public King(Player player) {
        super(player );
        if (player ==Player.BLACK)
        {
            icon = new ImageIcon("bKing.png");
        }
        else if (player == Player.WHITE)
        {
            icon = new ImageIcon("wKing.png");
        }
    }

    public String type() {
        return "King";
    }

    public boolean isValidMove(Move move, IChessPiece[][] board)
    {
        return move.isValidMoveForKing(board);
    }	

}