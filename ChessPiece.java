package chess;

import javax.swing.*;
/******************************************************
 * Chess piece and icon are inseparable.
 * 
 * 
 * @author Trevor Tillman & Rashid Nelson
 *
 ******************************************************/
public abstract class ChessPiece implements IChessPiece {
	
    private Player   owner;	    /** owner of the chess piece */
    public Location  location;	/** location location on the board */
    public ImageIcon icon;	    /** identifying icon */
    
    
    protected ChessPiece(Player player)
    {
        this.owner  = player;
        this.location = new Location( -1, -1 );
    }

    protected ChessPiece(Player player, Location location )
    {
        this.owner  = player;
        this.location = location;
    }

    public abstract String type();  /** Not implemented by this abstract class */

    
    public Player player()
    {
        return owner;
    }
    
    public ImageIcon imageIcon()
    {
    	return icon;
    }
    
    /**
     * setLocation gives this chess piece its new location on the board.
     */
    public void setLocation( int row, int column )
    {
        location.set(row, column);
    }	
  
	public String toString( )
	{
		return player( ).toString() + " " + type() + " " + location.toString( );
	}
	
	

     public boolean isValidMove(Move move, IChessPiece[][] board) {
    	 boolean validMove = false;
         if(move.from.column != move.to.column && move.to.row != move.from.row){
             if (board[move.from.row][move.from.column].equals(board)) {
                 if(this.player() != board[move.from.row][move.from.column].player() ){
                     validMove = true;
                 }
             }
         }
         return validMove;

     }

 	}
     
     


