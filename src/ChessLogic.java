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
 ** This class represents chess logic, utilised in the ChessSim program.
 **/
 
/**
 **   @author  Ben Goldsworthy (rumperuu) <me+chesssim@bengoldsworthy.net>
 **   @version 0.90
 **/
public class ChessLogic {
   // rumperuu <3 C-style pre-processor macros
   private final int DEFAULT = 0, PIECESELECTED = 1;
   
   // stores the current state of the game
   private int currentState;
   
   /**
    **   Constructor function.
    **/
	public ChessLogic() {
	   currentState = DEFAULT;
	}
	
   /**
    **   Determines if a given rank is the top or bottom two.
    **   @param y the rank number
    **   @return a boolean value
    **/
   public boolean startingRow(int y) {
      return (((y <= 1) || (y >= 6)) ? true : false); 
   }
   
   /**
    **   Determines if a given rank is either team's back rank. Currently
    **   used to populate pieces, but could also be called to deal with pawn
    **   promotion if I ever got around to adding that.
    **   @param y the rank number
    **   @return a boolean value
    **/
   public boolean backRank(int y) {
      return (((y == 0) || (y == 7)) ? true: false);
   }
   
   /**
	 **   Gets the current state of this chess game.
	 **   @return the state of this chess game
	 */
	public int getState() {
		return currentState;
	}
	
	/**
	 **   Sets the state of this chess game.
    **   @param state the state of this chess game
	 */
	public void setState(int state) {
	   currentState = state;
	}
}
