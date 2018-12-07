package chess;
/**
 * IChessModel defines the interface for a chess game model.
 * 
 * @author Larry Kotman
 *
 */
public interface IChessModel {

	/**
	 * Returns whether the game is over.
	 *
	 * @return {@code true} if game is over, {@code false} otherwise.
	 */
	boolean isCheckMate( );

	/**
	 * Returns whether the piece at location {@code [move.from ]} is allowed to move to location
	 * {@code [move.to]}.
	 *
	 * @param move a {@link ChessProject3.chess.Move} object specifies the move to be made.
	 * @return {@code true} if the proposed move is valid, {@code false} otherwise.
	 */
	boolean isValidMove(Move move);

	/**
	 * Moves the piece from location {@code [move.from]} to location {@code [move.to]}.
	 *
	 * @param move a {@link ChessProject3.chess.Move} object specifies the move to be made.
	 */
	void move(Move move);

	/**
	 * Report whether the current player p is in check.
	 * @param  p {@link ChessProject3.chess.Move} the Player being checked
	 * @return {@code true} if the current player is in check, {@code false} otherwise.
	 */
	boolean inCheck(Player p);

	/**
	 * Return the current player.
	 *
	 * @return the current player
	 */
	Player currentPlayer();

}