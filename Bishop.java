package chess;
import javax.swing.ImageIcon;

/*********************************************
 * Bishop is a class definition for
 * 
 * @author Trevor Tillman & Rashid Nelson
 *
 **********************************************/
public class Bishop extends ChessPiece {

	public Bishop(Player player ) {
		super(player);
		
		if (player ==Player.BLACK)
		{
			icon = new ImageIcon("bBishop.png");
		}
		else if (player == Player.WHITE)
		{
			icon = new ImageIcon("wBishop.png");
		}
	}
	
	public Bishop(Player player, Location s )
	{
		super(player, s);
		if (player ==Player.BLACK)
		{
			icon = new ImageIcon("bBishop.png");
		}
		else if (player == Player.WHITE)
		{
			icon = new ImageIcon("WBishop.png");
		}
	}
	


	public String type() {
	    
		return "Bishop";
	}

	public boolean isValidMove( Move move, IChessPiece[][] board )
	{
		return  move.isValidMoveForBishop(board);
				
	}
	
}

