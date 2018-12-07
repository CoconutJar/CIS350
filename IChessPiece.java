package chess;

import javax.swing.ImageIcon;

/**
 * IChessPiece defines the interface for the chess pieces in the game.
 * 
 * @author Trevor Tillman & Rashid Nelson
 *
 */
public interface IChessPiece {

	/**
	 * Returns the player that owns this piece.
	 *
	 * @return the player that owns this piece.
	 */
	Player player();
	
	/**
	 * Return the icon for this piece.
	 *
	 * @return the icon for this piece.
	 */
    ImageIcon imageIcon();

	/**
	 * Returns the type of this piece ("King", "Queen", "Rook", etc.). Note: In this
	 * case "type" refers to the game of chess, not the type of the Java class.
	 *
	 * @return the type of this piece
	 */
	String type();

	/**
	 * Each chess piece stores its own location on the chess board: row, column.
	 * The setSquare method is called to update the location of a chess piece as
	 * the result of a Move.
	 * 
	 * A chess piece is removed from the board by the assignments:
	 *       this.row = -1
	 *       this.column = -1
	 * 
	 * @param row
	 * @param column
	 */
	void setLocation(int row, int column);

	/**
	 * Note: Each chess piece stores its own location on the board, and each occupied
	 * location on the board contains a reference to the occupied piece.
	 * 
	 * Returns whether the piece at location {@code [move.from]} is allowed to move
	 * to location {@code [move.to]}.
	 *
	 * @param move
	 *            a {@link ChessProject3.chess.Move} object specifies the move to
	 *            be made.
	 * @param board
	 *            {@link ChessProject3.chess.IChessBoard} shows the board configuration
	 * @return {@code true} if the proposed move is valid, {@code false} otherwise.

	 */
	boolean isValidMove(Move move, IChessPiece[][] board);
}