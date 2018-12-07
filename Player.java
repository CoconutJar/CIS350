package chess;

/**
 * Player defines a Player enumerated type for two constants:
 * 						BLACK and WHITE
 * 
 * @author Trevor Tillman & Rashid Nelson
 *
 */
public enum Player
{
	BLACK, WHITE;

	/**
	 * Return the {@code Player} whose turn is next.
	 *
	 * @return the {@code Player} whose turn is next
	 */
	public Player next()
	{
		return this == BLACK ? WHITE : BLACK;
	}
	
	public String toString( )
	{
	    return this == BLACK ? "B" : "W";
	}
}