package chess;

/**
 * Pawn is a class definition for
 * 
 * @author Trevor Tillman & Rashid Nelson
 *
 */
abstract public class Pawn extends ChessPiece {

	public Pawn(Player player)
	{
		super(player);
	}

	public String type()
	{
		return "Pawn";
	}
	
}