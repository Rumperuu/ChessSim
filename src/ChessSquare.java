/******************************************************************************
 *                             ChessSim 0.9                                   *
 *                  Copyright © 2015 Ben Goldsworthy (rumperuu)               *
 *                                                                            *
 * A program to simulate a game of chess between two human players.           *
 *                                                                            *
 * This file is part of ChessSim.                                             *
 *                                                                            *
 * ChessSim is free software: you can redistribute it and/or modify           *
 * it under the terms of the GNU General Public License as published by       *
 * the Free Software Foundation, either version 3 of the License, or          *
 * (at your option) any later version.                                        *
 *                                                                            *
 * ChessSim is distributed in the hope that it will be useful,                *
 * but WITHOUT ANY WARRANTY; without even the implied warranty of             *
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the              *
 * GNU General Public License for more details.                               *
 *                                                                            *
 * You should have received a copy of the GNU General Public License          *
 * along with ChessSim.  If not, see <http://www.gnu.org/licenses/>.          *
 ******************************************************************************/

/**
 ** This class represents a chess square, utilised in the ChessSim program.
 **/

import javax.swing.*;
import java.awt.*;

/**
 **   @author  Ben Goldsworthy (rumperuu) <me+chesssim@bengoldsworthy.net>
 **   @version 0.90
 **/
public class ChessSquare extends JButton {
   // C sells C shells on the C shore
   private final int NONE = 0, MOVABLE = 1, ATTACKABLE = 2;
   private final int NULL = 9999;
   private final int WHITE = 0, BLACK = 1;
   
   // declares the array for piece icons
	private ImageIcon[][] pieces = new ImageIcon[2][6];
   // declares the values for storing the details relevant to each instance of
   // the ChessSquare
   private Piece currentPiece;
	private int xPosition, yPosition;
	private int currentState;

   /**
    **   Constructor function.
    **   @param x the x-coord of the square, which doubles as its array x-index
    **   @param y the y-coord of the square, which doubles as its array y-index
    **   @param colour the colour of the square
    **/
	public ChessSquare(int x, int y, boolean colour) {
	   // populates the array of piece icons
	   for (int i = 1; i <= 6; i++) {
	      pieces[WHITE][i-1] = new ImageIcon("images/white"+i+".png");
	      pieces[BLACK][i-1] = new ImageIcon("images/black"+i+".png");
	   }
	   
		xPosition = x; 
		yPosition = y;
		
		// sets the background appropriately
		setBackground(colour ? new Color(0,0,0) : new Color(255,255,255));
		
		// sets the square to empty
		currentPiece = null;
	}
	
	/**
	 **   Removes the currently-occupying piece of this ChessSquare.
	 **/
	public void removePiece() {  
      currentPiece = null;
	   setIcon(null);
	}
	
	public boolean hasPiece() {
	   if (currentPiece != null) return true;
	   else return false;
	}
	
   /**
    **   Gets the current x-coord of this ChessSquare.
	 **   @return the x-coord of this ChessSquare within the ChessBoard
	 **/
	public int getXPosition() {
		return xPosition;
	}

	/**
	 **   Gets the current y-coord of this ChessSquare.
    **   @return the y-coord of this ChessSquare within the ChessBoard
	 **/
	public int getYPosition() {
		return yPosition;
	}
	
	/**
	 **   Gets the current piece on this ChessSquare.
    **   @return the Piece object on this ChessSquare within the ChessBoard
	 **/
	public Piece getPiece()
	{
		return currentPiece;
	}
	
	/**
	 **   Gets the current index of this ChessSquare.
    **   @return the index of this ChessSquare within the ChessBoard
	 **/
	public int getPieceIndex()
	{
		return currentPiece.getIndex();
	}
	
	/**
	 * Obtains the type of the currently-occupying piece of this ChessSquare.
    * @return the current piece type on this ChessSquare within the ChessBoard
	 */
	public int getPieceType()
	{
		return currentPiece.getType();
	}
	
	/**
	 * Obtains the team of the currently-occupying piece of this ChessSquare.
    * @return the current piece team on this ChessSquare within the ChessBoard
	 */
	public int getPieceTeam() {
		return currentPiece.getTeam();
	}
	
	/**
	 * Gets the state of this ChessSquare.
    * @return the state of this ChessSquare within the ChessBoard
	 */
	public int getState()
	{
	   return currentState;
	}

	/**
	 **   Sets the currently-occupying piece on this ChessSquare.
	 **   @param piece the Piece object
	 **/
	public void setPiece(Piece piece) { 
	   currentPiece = piece;
      setIcon(pieces[piece.getTeam()][piece.getType()]);
	}
	
	/**
	 **   Sets the current state of this ChessSquare.
	 **   @param state the new state
	 **/
	public void setState(int state) {
	   currentState = state;
	}
}
