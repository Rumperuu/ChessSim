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
 ** This class represents a chess piece, utilised in the ChessSim program.
 **/
 
import java.util.*; 

/**
 **   @author  Ben Goldsworthy (rumperuu) <me+chesssim@bengoldsworthy.net>
 **   @version 0.92
 **/
public class Piece {
   // they C me rollin', they hatin'
   private final int NULL = 9999;
   private final int WHITE = 0, BLACK = 1;
   private final int PAWN = 0, ROOK = 1, KNIGHT = 2, BISHOP = 3, QUEEN = 4,
                     KING = 5;
   
   // declares all the variables that every piece, regardless of type, has
	private int xPosition, yPosition;
	private int pieceNum;
	private int pieceTeam;
   private int pieceType;
   private List<Integer> moves = new ArrayList<Integer>();
   private boolean firstMove = false;
   
   
   /**
    **   Constructor function.
    **   @param x the x-coord of the piece
    **   @param y the y-coord of the piece
    **   @param team the team of the piece
    **   @param type the type of the piece
    **/
	public Piece(int x, int y, int team, int type, int num) {
	   xPosition = x;
	   yPosition = y;
	   pieceTeam = team;
	   pieceType = type;
	   pieceNum = num;
	   
	   if (type == PAWN)
	      firstMove = true;
	}
	
	/**
	 *	Gets whether this is the first move for the piece, if it is a Pawn.
	 * @return a boolean value
	 */
	public boolean firstMove() {
      return firstMove;
	}
	
	/**
	 **   Sets the first move as used up.
	 **/
	public void usedUpFirstMove() {
	   firstMove = false;
	}
		
	/**
    **   Populates the movelist of the piece.
    **   @return The list of moves, with NULL characters seperating linear paths
    **/
	public List showMoves() {
	   // clears the movelist
	   getMoves().clear();
	   
	   switch(pieceType) {
	   case PAWN:
	      // adds the the square(s) directly ahead of the piece
	      addPawn(firstMove);
	      break;
	   case ROOK: 
	      // adds linear paths in all four orthagonal directions
         addOrthagonals(1, 8);
	      break;
	   case KNIGHT: 
	      // adds the non-linear L-shaped jump squares
	      addLs();
	      break;
	   case BISHOP:
	      // adds linear paths in all four diagonal directions
	      addDiagonals(1, 8);
	      break;
	   case QUEEN:
	      // adds linear paths in all four diagonal and orthagonal directions
	      addDiagonals(1, 8);
	      addSeparator();
	      addOrthagonals(1, 8);
	      break;
	   case KING:
	      // adds all adjacent squares
         addDiagonals(1, 1);
         addSeparator();
         addOrthagonals(1, 1);
	      break;
	   }
	   		
	   // returns the movelist 
		return getMoves();
	}	
	
   /**
	 **   Gets the current x-coord of this Piece.
	 **   @return the x-coord of this Piece within the ChessBoard.
	 **/
	public int getXPosition() {
		return xPosition;
	}

	/**
	 **   Gets the current y-coord of this Piece.
    **   @return the y-coord of this Piece within the ChessBoard.
	 **/
	public int getYPosition() {
		return yPosition;
	}
	
	/**
	 **   Gets the array index of this Piece.
    **   @return the array index of this Piece within the ChessBoard.
	 **/
	public int getIndex() {
		return pieceNum;
	}

	/**
	 **   Obtains the type of this Piece.
    **   @return the current type of this Piece within the ChessBoard.
	 **/
	public int getType() {
		return pieceType;
	}
	
	/**
	 **   Obtains the team of this Piece.
    **   @return the team of this Piece within the ChessBoard.
	 **/
	public int getTeam() {
		return pieceTeam;
	}
	
	/**
	 **   Sets the x-coord of this Piece.
    **   @param x the x-coord of this Piece within the ChessBoard.
	 **/
	public void setXPosition(int x) {
		xPosition = x;
	}
	
	/**
	 **   Sets the y-coord of this Piece.
    **   @param y the y-coord of this Piece within the ChessBoard.
	 **/
	public void setYPosition(int y) {
		yPosition = y;
	}
	
	/*
	 * Adds a move to this Piece.
	 */
   private void addMovement(int x, int y) {
		moves.add(x);
		moves.add(y);
	}
	
	/*
	 * Adds a separator to the movelist.
	 */
	private void addSeparator() {
	   moves.add(NULL);
	}
	
	/**
	 **   Gets the list of on-board moves for the piece
	 **   @return the list of moves.
	 **/
	public List getMoves() {
	   return moves;
	}
	
	/*
	 * Determines whether a given move is on the board or not, i.e. that neither
	 * the x- or y-coords are less than 0 or more than 7. The direction passed
	 * as a param starts at diagonally up and to the left, and proceeds
	 * clockwise
	 */
	private boolean moveOnBoard(int x, int dir) {
	   switch(dir) {
	   case 0:
	      return ((xPosition - x >= 0) && (yPosition - x >= 0));
	   case 1:
	      return (yPosition - x >= 0);
	   case 2:
	      return ((xPosition + x <= 7) && (yPosition - x >= 0));
	   case 3:
	      return (xPosition + x <= 7);
	   case 4:
	      return ((xPosition + x <= 7) && (yPosition + x <= 7));
	   case 5:
	      return (yPosition + x <= 7);
	   case 6:
	      return ((xPosition - x >= 0) && (yPosition + x <= 7));
	   case 7:
	      return (xPosition - x >= 0);
	   default:
	      System.out.println("Error 02: no direction sent.");
	      return false;
	   }
	}	
	
   /*
	 * Adds a number of diagonal vectors to the movelist, separated by the
	 * NULL separator.
	 */
	private void addDiagonals(int x1, int x2) {
	   for (int x = x1; x <= x2; x++) {
	      if (moveOnBoard(x, 0)) 
			   addMovement(xPosition - x, yPosition - x);
	   }
	   addSeparator();  
	   for (int x = x1; x <= x2; x++) {
		   if (moveOnBoard(x, 2))
			   addMovement(xPosition + x, yPosition - x);
	   }
	   addSeparator(); 
	   for (int x = x1; x <= x2; x++) {
		   if (moveOnBoard(x, 4))
			   addMovement(xPosition + x, yPosition + x);
	   }
	   addSeparator();  
	   for (int x = x1; x <= x2; x++) {
		   if (moveOnBoard(x, 6))
			   addMovement(xPosition - x, yPosition + x);
	   }       
   }
   
	/*
	 * Adds a number of orthagonal vectors to the movelist, separated by the
	 * NULL separator.
	 */
	private void addOrthagonals(int x1, int x2) {
	   for (int x = x1; x <= x2; x++) {
	      if (moveOnBoard(x, 1)) 
		      addMovement(xPosition, yPosition - x);
	   }
	   addSeparator();  
	   for (int x = x1; x <= x2; x++) {
		   if (moveOnBoard(x, 3))
			   addMovement(xPosition + x, yPosition);
	   }
	   addSeparator(); 
	   for (int x = x1; x <= x2; x++) {
		   if (moveOnBoard(x, 5))
			   addMovement(xPosition, yPosition + x);
	   }
	   addSeparator();  
	   for (int x = x1; x <= x2; x++) {
		   if (moveOnBoard(x, 7))
			   addMovement(xPosition - x, yPosition);	
	   }
	}
   
   /*
	 * Adds a number of non-linear L-shaped paths to the movelist, separated
	 * by the NULL separator.
	 */
   private void addLs() {
      for (int x = 1, x2 = 2; x <= 2; x++, x2--) {
         if ((xPosition + x <= 7) && (yPosition + x2 <= 7))
            addMovement(xPosition + x, yPosition + x2);
	      addSeparator();  
         if ((xPosition - x >= 0) && (yPosition + x2 <= 7))
            addMovement(xPosition - x, yPosition + x2);  
	      addSeparator();        
         if ((xPosition + x <= 7) && (yPosition - x2 >= 0))
            addMovement(xPosition + x, yPosition - x2);
	      addSeparator();  
         if ((xPosition - x >= 0) && (yPosition - x2 >= 0))
            addMovement(xPosition - x, yPosition - x2);
	      if (x2 == 2) addSeparator();   
      }
   }
   
   /*
	 * Adds the square(s) directly ahead of the pawn's current coords.
	 */
   private void addPawn(boolean firstMove) {
      // adds the the square(s) directly ahead of the piece
	   if (pieceTeam == WHITE) {
		   if (moveOnBoard(1, 1))
		      addMovement(getXPosition(), getYPosition() - 1);
	      if (firstMove) addMovement(getXPosition(), getYPosition() - 2);
		} else if (pieceTeam == BLACK) {
		   if (moveOnBoard(1, 5)) 
		      addMovement(getXPosition(), getYPosition() + 1);
	      if (firstMove) addMovement(getXPosition(), getYPosition() + 2);
		}
   }
}
