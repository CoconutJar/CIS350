package chess;
import javax.swing.ImageIcon;
/*************************************************************************************
 * Move is a class definition for a chess piece   Two board squares are involved
 * in a move:
 *      1) a from square
 *      2) a to square
 * 
 * Let the chess piece being moved be denoted as the fromPiece, and let the chess piece
 * that is removed, if one is in fact removed, be denoted as the toPiece.
 * 
 * The information stored by a Move is sufficient for a move undo.
 * 
 *@author Trevor Tillman & Rashid Nelson
 *
 *************************************************************************************/
public class Move {
    public Location from;           // the "from" square in a move
    public Location to;             // the "to" square in a move

    public IChessPiece fromPiece; // stores the "from" chess piece in the move
    public IChessPiece toPiece;   // stores the "to" chess piece, if one exists, in the move
    //This will allow to move the proper images
    public ImageIcon fromPieceIcon;
	public ImageIcon toPieceIcon;

    /*************************************************************
     * The Move constructor initializes the two locations in a
     * move, the "from" square location and the "to" square
     * location.
     * 
     * @param from
     * @param to
     * @return 
     *************************************************************/
    public Move(Location from, Location to) {
    	//Initialize the fact that "from" and "to" are locations
    		this.from = from;
		this.to = to;
    }
    
    
    /*************************************************************
     * The Move constructor initializes the two squares in a move
     * from a string description of the 
     * 
     * @param str
     *************************************************************/
    public Move(String str) {
    	//Creates the squares that are considered "From location -> to location" move
        this.from = new Location(str.substring(0, 2)); // Important to make a new Location
        this.to   = new Location(str.substring(2, 4));
    }

    /*********************************************************
     *  The toString method returns a string description of
     *  the 
     *  
     *  @return
     *********************************************************/
    public String toString() {
		return "from " + from.toString() + " to " + to.toString();
	}
/************************************************
 * What am I supposed to do with this?
 * @param n
 * @return
 *********************************************************/
    private boolean isWithinRange(int n)
    {
        return 0 <= n && n < 8;
    }

    private boolean isOnTheBoard(Location square)
    {
    	
        return !square.equals(to);
    }
    /**********************************************************************
     *  A valid Move consists of a move from any square on the board,
     *  denoted by "from", to some other square, on the board, denoted
     *  by "to"; i.e. the squares must be two distinctly different 
     *  locations.
     *  
     *  @return boolean
     **********************************************************************/
   private boolean isMoveToDifferentLocation() {
    	//Makes sure the move is a "from location -> to location" move
    	return !from.equals(to);
    }

    /**************************************************************************
     *  The squareIsOccupied method returns whether a square contains a piece.
     *    
     *  @param square
     *  @param board
     *  @return boolean
     **************************************************************************/
    private boolean squareIsOccupied( Location square, IChessPiece[][] board) {
    	//Similar to "isMoveToEmptySquare" but checks new square so it can be called
    	//in the "isMoveOverEmptySquares" method
        return (board[square.row][square.column] != null);
    }

    /**************************************************************************
     *  The isMoveOverEmptySquares method returns : 
     *      1) true - if all squares exclusively between the "from" square and
     *         the "to" square are empty. 
     *      2) false - if at least one square between the "from" square and the
     *         "to" square is occupied.
     *         
     *  @param board
     *  @return boolean
     **************************************************************************/
   private boolean isMoveOverEmptySquares(IChessPiece[][] board) {
        //Creates new unitDirection instance from location class
     	Location unit = from.unitDirection(to);
     	//Creates new square location instance
    		Location square = new Location( from );
    		
    		//Location change is a positive move
    		square.plus(unit);
    		//While the piece is still on the board
    		while(isOnTheBoard(square)){
    			//Does the square that you desire to move to have a piece? 
    			//if occupied, return false(Cant move)
    			if(squareIsOccupied(square, board)){
    				//Square was occupied 
    				return false;
    			}
    			//Square location change is a positive unit change
    			square.plus(unit);
    		}
    		
    		return true;	
    }

    /********************************************************************
     *  The isMoveToEmptySquare method returns whether the move is to an
     *  empty square on the board. 
     *  
     *  @param boardfromLocationHasChessPiece
     *  @return
     ********************************************************************/
    private boolean isMoveToEmptySquare(IChessPiece[][] board )
    {
    	//Square must be empty, cannot equal null(piece is located there)
    	return board[to.row][to.column] != null;
    }

    /********************************************************************
     * The isMoveToOpponentPiece method returns whether the move is to
     * remove an opposing piece
     *  
     * @param board
     * @return
     ********************************************************************/
    private boolean isMoveToOpponentPiece(IChessPiece[][] board )
    {
    	//Can you legally take a players piece?
    	return board[from.row][from.column].player() != board[to.row][to.column].player();
    }

    /********************************************************************
     *  The fromLocationHasChessPiece method returns whether the from
     *  location square is occupied by a chess piece.
     *  
     *  @param board
     *  @return
     ********************************************************************/
    private boolean fromLocationHasChessPiece(IChessPiece[][] board )
    {
    		//Checks for chess piece in location selected on board
        return board[to.row][to.column] == null && board[from.row][from.column] != null;
    }

    /***********************************************************************
     * A valid move for any chess piece must satisfy the following general
     * requirements:
     *    1) Every move involves two distinctly different locations on the board;
     *    2) The "from" location must contain a chess piece;
     *    3) The "to" location is either exclusively empty or it contains a chess
     *       piece of the opposite color.
     **********************************************************************/    
    public boolean isBasicallyValid(IChessPiece[][] board)
    {
    //Is the move a valid "to -> from" movement?
    	return	isMoveToDifferentLocation() &&
    			//Does the from location have a chess piece?
    			fromLocationHasChessPiece(board) ||
    			//Can the opponents piece be taken?
    			isMoveToOpponentPiece( board );
	
    }

    /********************************************************************
     * The isHorizontalMove method returns whether the move stays
     * within the row.
     *  
     * @return
     ********************************************************************/
    private boolean isHorizontalMove() {
    	//Can move horizontally across rows as much as desired
        return to.row == from.row;
    }

    /*********************************************************************
     *  This isVerticalMove method returns whether the move stays within
     *  the column.
     *  
     *  @return
     *********************************************************************/
    private boolean isVerticalMove() {
	//Basic vertical move that can move forward as much as desired
        return to.column == from.column;
    }

    /********************************************************************
     *  The isDiagonalMove method returns whether the move stays within
     *  the diagonal.
     *  
     *  @return
     ********************************************************************/
    private boolean isDiagonalMove() {
       // Location direction = this.from.direction(this.to);
        //Defines what a diagonal move is, can move as many spaces diagonal as desired
    		Location distance = from.direction(to);
        return (Math.abs(distance.row) == Math.abs(distance.column));
    }

    /**************************************************************************
     * The follow methods pertain to moves for Black and White pawns. 
     * 
     * The rows on a chess board are numbered from 0 to 7. A chess game starts
     * with the black pawns in row 1 and the white pawns in row 6.
     * 
     * In addition to satisfying the general requirements for moving a chess 
     * piece, a pawn can move forward one square, two squares for a white pawn
     * from row 6 and two squares for a black pawn from row 2, but only if no
     * other piece is in its way. A pawn can move diagonally one unit, but only
     * to take a piece.
     **************************************************************************/

    /********************************************************************
     *  This isVerticalMove method returns whether the move is forward
     *  n rows.
     *  
     *  @param n
     *  @return
     ********************************************************************/
    @SuppressWarnings("unused")
	private boolean isVerticalMove(int n) {
     	//Moving forward "n" spaces
   
    	Location distance = from.direction(to);
    	n = 2;
    	if(isWithinRange(n)) {
    		System.out.println("range");
    	}
       
        
        return Math.abs(distance.row) == n &&
    			isVerticalMove();
    }

    /********************************************************************
     *  The isForwardMoveForBlackPawn method returns whether the move
     *  is forward over n rows.
     *  
     *  @param n
     *  @return
     ********************************************************************/
    private boolean isForwardMoveForBlackPawn() {
    	//If starting space (6) then allow for moving forward 2, if not starting space,
    	//Then can only move forward 1 space
    	//N is how many rows you can move vertically
    	Location distance = from.direction(to);
        	return 	(from.row == 1 &&
        			Math.abs(distance.row) == 2 &&
        			isVerticalMove() ||
        			Math.abs(distance.row) == 1 &&
        			isVerticalMove());
					
	}

    /********************************************************************
     *  The isForwardMoveForWhitePawn method returns whether the move
     *  is forward over n rows.
     *  
     *  @param n
     *  @return
     ********************************************************************/
    private boolean isForwardMoveForWhitePawn() {
    	//If starting space (6) then allow for moving forward 2, if not starting space,
    	//Then can only move forward 1 space
    	//N is how many rows you can move vertically
    	Location distance = from.direction(to);
    	return 	(from.row == 6 &&
    			Math.abs(distance.row) == 2 &&
    			isVerticalMove() ||
    			Math.abs(distance.row) == 1 &&
    			isVerticalMove());

    }

    /********************************************************************
     *  The isDiagonalMoveForBlackPawn method returns whether the move
     *  is forward one row and diagonal one column.
     *  
     *  @return
     ********************************************************************/
    private boolean isDiagonalMoveForBlackPawn() {
    //	Location distance = from.direction(to);
    	//Can move diagonal 1 over rows or columns, black pawn
    	Location distance = from.direction(to);
    	return (Math.abs(distance.row) == 1 && Math.abs(distance.column) == 1);
    }

    /********************************************************************
     *  The isDiagonalMoveForWhitePawn method returns whether the move
     *  is forward one row and diagonal one column.
     *  
     *  @return
     ********************************************************************/
    private boolean isDiagonalMoveForWhitePawn() {
    	//Location distance = from.direction(to);
    	//Checks to move diagonal 1 over rows or columns, white pawn
    	Location distance = from.direction(to);
    	return (Math.abs(distance.row) == 1 && Math.abs(distance.column) == 1);
    }

    /*******************************************************************************
     * The isValidMoveForBlackPawn method returns whether the move is a valid
     * diagonal move or a valid forward move for a black pawn.
     * 
     * @param board
     * @return
     ******************************************************************************/
    protected boolean isValidMoveForBlackPawn(IChessPiece[][] board )
    {
    	//Is the move valid in general?
    	return (	isBasicallyValid(board) &&
    			//Is the move a forward move from starting place (2) or normal move (1)?
    			isForwardMoveForBlackPawn() ||
        		//Is the move diagonal allowing you to take an opponents pawn?
        		(isDiagonalMoveForBlackPawn() &&
        		//Is the square you are moving to empty?
        		isMoveToEmptySquare(board)) &&
        		//Is the square occupied or empty?
        		isMoveOverEmptySquares(board)
    		);
    }

    /*******************************************************************************
     * The isValidMoveForWhitePawn method returns whether the move is a valid
     * diagonal move or a valid forward move for a white pawn.
     * 
     * @param board
     * @return
     ******************************************************************************/
    protected boolean isValidMoveForWhitePawn(IChessPiece[][] board )
    {
    	//Is the move valid in general?
    return (	isBasicallyValid(board) &&
    		//Is the move a forward move from starting place (2) or normal move (1)?
    		isForwardMoveForWhitePawn() ||
    		//Is the move diagonal allowing you to take an opponents pawn?
    		(isDiagonalMoveForWhitePawn() &&
         //Is the square you are moving to empty?
    		isMoveToEmptySquare(board)) &&
    		//Is the square occupied or empty?
		isMoveOverEmptySquares(board)
		);
     }

    /**************************************************************************
     * The follow methods pertain to moves for chess pieces, other than pawns. 
     **************************************************************************/

    /**********************************************************************
     * A valid move for a Knight must satisfy the following requirements:
     *    1) A valid move for any chess piece in general;
     *    2) The move must be restricted exclusively to either 
     *          A) two rows and one column, or
     *          B) one row and two columns.
     * 
     * @param board
     * @return
     **********************************************************************/    
    protected boolean isValidMoveForKnight(IChessPiece[][] board)
    {
        Location distance = from.direction(to);

        return (    
        		//Is the move valid in general?
        		isBasicallyValid(board) &&
            //Can move up or down 2 and side one
            Math.abs(distance.row) == 2 &&
            Math.abs(distance.column) == 1 ||
            //Can move up or down 1 and side 2
            Math.abs(distance.row) == 1 &&
            Math.abs(distance.column) == 2
        );
    }

    /*******************************************************************
     * A valid move for a King must satisfy the following requirements:
     *    1) A valid move for any chess piece in general;
     *    2) The move must be restricted exclusively to one square in
     *       any direction.
     * 
     * @param board
     * @return
     *****************************************************************/
    protected boolean isValidMoveForKing(IChessPiece[][] board)
    {
    	 Location distance = from.direction(to);
    	//Is the move valid in general?
    	 return isBasicallyValid(board) &&
    			 //Can move any direction 1 via row or column
 				(Math.abs(distance.row) <=1 &&
 				Math.abs(distance.column) <= 1) &&
 				//Square must be unoccupied
 				isMoveOverEmptySquares(board);	

    }

    /*********************************************************************
     * A valid move for a Bishop on the board must satisfy the following
     * requirements:
     *    1) A valid move for any chess piece in general;
     *    2) The move must be restricted exclusively to a diagonal;
     *    3) The locations strictly between the "from" location and the
     *       "to" location must be empty (unoccupied).
     * 
     * @param board
     * @return
     **********************************************************************/    
    protected boolean isValidMoveForBishop( IChessPiece[][] board )
    {
    	//Is the move valid in general?
    	return isBasicallyValid(board) &&
    			//Can only move diagonal
				isDiagonalMove() &&
				//Move must be empty square
				isMoveOverEmptySquares(board);	
    }

    /***********************************************************************
     * A valid move for a Rook must satisfy the following requirements:
     *    1) A valid move for any chess piece in general;
     *    2) The move must be restricted exclusively to either a row or a
     *       column;
     *    3) The locations strictly between the "from" location and the
     *       "to" location must be empty (unoccupied).
     * 
     * @param board
     * @return
     ***********************************************************************/   
    protected boolean isValidMoveForRook( IChessPiece[][] board )
    {
    	//Is the move valid in general?
    	return isBasicallyValid(board) &&
    			//move restricted to row or column
				(isHorizontalMove() || isVerticalMove()) &&
				//Move must be empty square
				isMoveOverEmptySquares(board);
    }

}
