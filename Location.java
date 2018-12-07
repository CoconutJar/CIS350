package chess;

/***************************************************************
 * Location defines an encapsulation for a location on a chess
 * board, a row and a column.
 ***************************************************************/
public class Location
{
    public int row;       /** 0 <= row    < 8 */
    public int column;    /** 0 <= column < 8 */

    /** letter serves to label columns */
    private static char[] letter = { 'a','b','c','d','e','f','g','h'};

    
    public Location( int row, int column )
    {
        this.row    = row;
        this.column = column;
    }

    public Location( Location s )
    {
        this.row    = s.row;
        this.column = s.column;
    }

    /****************************************************************
     * Constructor that could be used with date retrieved from file.
     * 
     * @param str
     ***************************************************************/
    public Location( String str )
    {
        this.row = Integer.parseInt( str.substring(0,1) );      
        
        char ch = str.substring(1,2).charAt( 0 );
        this.column = (int)ch - (int)'a';
     }

    public void set( int row, int column )
    {
        this.row    = row;
        this.column = column;
    }

    public void set(Location square)
    {
        this.row    = square.row;
        this.column = square.column;
    }

    public boolean equals(Location square)
    {
        return this.row == square.row && this.column == square.column;
    }

    /**********************************************************************
     * 
     * The unitDirection method returns a Location that represents a unit
     * step to its neighbor in the direction of the target Location.
     * 
     * @param target
     * @return a unit direction from this Location to the target Location
     *********************************************************************/
    public Location unitDirection( Location target )
    {
        Location step = this.direction( target );

        if (step.row != 0)
        {
            step.row = step.row / Math.abs( step.row );
        }
        if (step.column != 0)
        {
            step.column = step.column / Math.abs( step.column );
        }
        return step;
    }

    /**********************************************************
     * The direction method returns a Location that consists of
     * the row and column differences between two squares, i.e.
     * the direction from this square to the s square.
     * 
     * @param target
     * @return
     **********************************************************/	
         public Location direction( Location target )
    {
        Location vector = new Location( 0, 0 );

        vector.row    = target.row    - this.row;
        vector.column = target.column - this.column;

        return vector;
    }

    /****************************************************     
     * The plus method modifies this Location so that it
     * may identify some other square on the board, such
     * as an adjacent Location.
     * 
     * @param change
     ****************************************************/	
    public void plus( Location change )
    {	
        this.row    = this.row    + change.row;
        this.column = this.column + change.column;
    }

    /*****************************************************
     * The minus method modifies this Location so that it
     * may identify some other square on the board, such
     * as an adjacent Location.
     * 
     * @param change
     *****************************************************/	
    public void minus( Location change )
    {	
        this.row    = this.row    - change.row;
        this.column = this.column - change.column;
    }

    /*****************************************************
     * This toString method returns a string description
     * of a chess board location, comparable to what a
     * chess player would use to identify a square
     * on the chess board, i.e. 0a, 0b, ... 1a, 1b, etc.
     * 
     * @return
     *****************************************************/
    public String toString( )
    {
        return  "" + row + letter[column];
    }

}