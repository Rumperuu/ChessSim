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
 ** This class represents a chess board, utilised in the ChessSim program.
 **/

import javax.swing.*;
import java.awt.Frame;
import java.awt.GridLayout;
import java.awt.Color;
import java.awt.event.*;
import java.util.List; 
import java.util.ArrayList; 

/**
 **   @author  Ben Goldsworthy (rumperuu) <me+chesssim@bengoldsworthy.net>
 **   @version 0.92
 **/
public class ChessBoard implements ActionListener, MouseListener {
   private final int DEFAULT = 0, PIECESELECTED = 1;
   private final int PAWN = 0, ROOK = 1, KNIGHT = 2, BISHOP = 3, QUEEN = 4,
                     KING = 5;
   private final int WHITE = 0, BLACK = 1;
   private final int NONE = 0, MOVABLE = 1, ATTACKABLE = 2;
   private final int NULL = 9999;
   
   private boolean colour = true;
   // Sets the initial piece (as a BLACK PAWN because the first piece placed
   // is `piece++`, or the ROOK).
   private int piece = PAWN, team = BLACK;
   // Creates the 2D array to hold the `ChessBoard` of `ChessSquare`s.
   private ChessSquare[][] chessSquare = new ChessSquare[8][8];
   // Creates a 3D array to hold the `ChessBoard` of chess `Piece`s.
   private Piece[][][] pieces = new Piece[6][2][8];
	private Piece selectedPiece;
	// Admittedly, the `ChessLogic` object doesn't do an awful lot right now
	// but it'll be useful if I come back to this to add actual game rules
	// (https://github.com/Rumperuu/ChessSim/issues/1).
	private ChessLogic logic = new ChessLogic();
	
	public ChessBoard() {
	   // Initialises the piece indices.
	   int pawnNum = 0, rookNum = 0, knightNum = 0, bishopNum = 0;
	   
	   // Sorts out the window, fullscreening and suchlike.
      JFrame window = new JFrame();
		window.setExtendedState(Frame.MAXIMIZED_BOTH);
		window.setUndecorated(true);
		window.setTitle("Chess, but not as you know it");
		window.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
	
	   // Creates a grid panel for the `ChessBoard` of `ChessSquare`s.
		JPanel board = new JPanel();
		GridLayout grid = new GridLayout(8,8);
		board.setLayout(grid);
		
		// For each rank 'y'...
		for (int y = 0; y <= 7; y++) {
		   // ...switch the populating team to white if the black team's done.
	      if (y == 2) {
            team = WHITE;
            pawnNum = 0;
            rookNum = 0;
            knightNum = 0;
            bishopNum = 0;
	      }
	      // Reset the square colour (e.g. the last square of row 0 is black,
	      // meaning the first square of row 1 would be set to white later on
	      // if not for this line).
		   colour = !colour;
		   // Resets the piece to `PAWN`.
		   piece = 0;
		   // ...and for each square in file 'x' of rank 'y'...
		   for (int x = 0; x <= 7; x++) { 
		      // ...populate the board array with a new `ChessSquare`.
		      chessSquare[x][y] = new ChessSquare(x,y,colour);
		      // Change the colour for the next square.
		      colour = !colour;
		      // Add the `ChessSquare` to the board.
		      board.add(chessSquare[x][y]);
		      // Checks if the current rank is the top or bottom two
		      // (one of the only current uses of the `ChessLogic` class).
		      if (logic.startingRow(y)) {
		         // Checks if the current rank is the back rank
		         // (one of the other uses).
	            if (logic.backRank(y)) {
	               // If the square in file 'x' is the Queen or lower, increment
	               // `piece`...
	               if (x <= 4) piece++;
	               // ...else, if the square is the King, subtract 2 (to avoid 
                  // two Queens).
	               else if (x == 5) piece = piece - 2;
	               // Otherwise, decrement `piece`.
	               else piece--;
	               
	               // Depending on the piece currently being added, populate
	               // the appropriate array dimension.
	               switch(piece) { 
	               case ROOK:
	                  pieces[ROOK][team][rookNum] = new Piece(x,y,team,
	                                                          ROOK,rookNum);
	                  chessSquare[x][y].setPiece(pieces[ROOK][team][rookNum++]);
	                  break;
	               case KNIGHT:
	                  pieces[KNIGHT][team][knightNum]= new Piece(x,y,team,KNIGHT,
	                                                             knightNum);
	                  chessSquare[x][y].setPiece(pieces[KNIGHT][team][knightNum++]);
	                  break;
	               case BISHOP:
	                  pieces[BISHOP][team][bishopNum]= new Piece(x,y,team,BISHOP,
	                                                             bishopNum);
	                  chessSquare[x][y].setPiece(pieces[BISHOP][team][bishopNum++]);
	                  break;
	               case QUEEN:
	                  pieces[QUEEN][team][0] = new Piece(x,y,team,QUEEN,0);
	                  chessSquare[x][y].setPiece(pieces[QUEEN][team][0]);
	                  break;
	               case KING:
	                  pieces[KING][team][0] = new Piece(x,y,team,KING,0);
	                  chessSquare[x][y].setPiece(pieces[KING][team][0]);
	                  break;
	               }
	             } else {
	               pieces[PAWN][team][pawnNum]= new Piece(x,y,team,PAWN,pawnNum);
	               chessSquare[x][y].setPiece(pieces[PAWN][team][pawnNum++]);
                }
	         }
	         // Adds the relevant listeners to the `ChessSquare`s.
		      chessSquare[x][y].addActionListener(this);
		      chessSquare[x][y].addMouseListener(this);
		   }
		}		
		// Finishes off the display.
		window.setContentPane(board);
		window.setVisible(true);
	}
		
   /*
	 * Handles the event of a `JButton` (or rather a `JButton`-extending 
    * `ChessSquare`) being moused over. As long as no piece is currently 
    * selected, the function displays faintly-highlighted available moves for 
    * the piece moused over.
	 */
	public void mouseEntered(MouseEvent e) {	
	   if (logic.getState() == DEFAULT) {
         ChessSquare enteredSquare = (ChessSquare)e.getSource();
         Piece presentPiece = null;
         if (enteredSquare.hasPiece()) {
            presentPiece = enteredSquare.getPiece();
	         displayMoves(presentPiece, false);
         } else defaultSquares();
     }
	}
	
	/*
	 * Handle the other `MouseEvent`s, by not doing anything. Mouse dragging 
    * could potentially be useful in a later version for some sort of click-and-
    * drag movement of pieces, but for now they're just here to get the compiler
    * to stop throwing up errors at me.
	 */
	public void mouseExited(MouseEvent e) {
	}
	public void mousePressed(MouseEvent e) {
   }
   public void mouseReleased(MouseEvent e) {
   }
   public void mouseClicked(MouseEvent e) {
   }
	
	/*
	 * Handles the event of a `JButton` (or rather a `JButton`-extending 
    * `ChessSquare`) being clicked. The function, depending on the game state
    * and state of the `ChessSquare` clicked on, wipes the displayed moves from
    * the `ChessBoard` or moves the selected piece, removing an attacked piece
    * is necessary.
    */
	public void actionPerformed(ActionEvent e) {
	   switch(logic.getState()) {
	   case DEFAULT:
	      ChessSquare selectedSquare = (ChessSquare) e.getSource();
	      if (selectedSquare.hasPiece()) {
	         selectedPiece = selectedSquare.getPiece();
	         // If no piece is selected, select the clicked piece...
	         displayMoves(selectedPiece, true);
	         logic.setState(PIECESELECTED);
	      }
	      break;
      case PIECESELECTED:
         // ...else, if a piece is selected...
         ChessSquare clickedSquare = (ChessSquare)e.getSource();
         switch(clickedSquare.getState()) {
         case NONE:
            // ...if the square just clicked is an illegal move, wipe all moves
            // from the `ChessBoard`...
            defaultSquares();
            break;
         case MOVABLE:case ATTACKABLE:
            // get the coords of the new, clicked square
            int newX = clickedSquare.getXPosition();
            int newY = clickedSquare.getYPosition();
            // and the details of the old, selected piece
            int oldX = selectedPiece.getXPosition();
            int oldY = selectedPiece.getYPosition();
            int oldType = selectedPiece.getType();
            int oldTeam = selectedPiece.getTeam();
            int oldIndex = selectedPiece.getIndex();
            // move (and take the currently-residing piece, if
            // applicable) to the square clicked
            chessSquare[oldX][oldY].removePiece();         
            clickedSquare.setPiece(selectedPiece);
            // update the location of the moved piece
            pieces[oldType][oldTeam][oldIndex].setXPosition(newX);
            pieces[oldType][oldTeam][oldIndex].setYPosition(newY); 
            // if the moved piece was a pawn on its first move, disable its
            // two-square move ability
            if (oldType == PAWN) 
               pieces[PAWN][oldTeam][oldIndex].usedUpFirstMove();
            
            // wipes the `ChessBoard` clean
            defaultSquares();
         }
         // resets the state of the game
         logic.setState(DEFAULT);
      }
   }
   
   /*
    * Displays the legal moves a selected or hovered-over piece can make,
    * coloured according to whether the piece is selected or just hovered on.
    */
   private void displayMoves(Piece piece, boolean hard) {
      // get the relevant details of the piece now
      List moves = new ArrayList();
      int pieceIndex = piece.getIndex();
      int pieceTeam = piece.getTeam();
      int pieceType = piece.getType();
      // get the relevant piece's complete moveset
      moves = pieces[pieceType][pieceTeam][pieceIndex].showMoves();
      
      // wipe the ChessBoard
      defaultSquares();
      
      // initialise the variable used to truncate movement paths due to
      // obstacles
      boolean skip = false;
      boolean pawn = (pieceType == PAWN) ? true : false;
      
      // for the list of moves...
      for (int i = 0; i < moves.size()-1; i++) {
         // if the current move isn't the NULL character used as a terminator
         // between chains of moves (e.g. all in a given direction)...
         if ((int)moves.get(i) != NULL) {
            // gets the x- and y-coords for later
            int moveX = (int)moves.get(i);
            int moveY = (int)moves.get(++i);
            // sets skip to false, in case i got here from a truncate
            skip = false;
            // determines if the given ChessSquare is legal or illegal
            skip = highlightSquare(moveX, moveY, pieceTeam, pawn, hard);
            // if the square is illegal...
            if (skip) {
               // sets j to i to start from the current point in the movelist
               int j = i;
               // increment down the list from i until the next NULL
               // separator/end of the line
               while (((int)moves.get(j) != NULL) && (j < moves.size()-1)) {
                  j++;
               }
               // set i to the value after that NULL
               i = j++;
            }
         }
      }     
      
      // if the given piece is a pawn...
      if (pieceType == PAWN) {
         // get the coordinate for the square up and to the right of the pawn
         // (pX and pY have been used instead of something perhaps more
         // informative in order to keep the source code within 80
         // characters' width in the for loop below)
         int pX = pieces[PAWN][pieceTeam][pieceIndex].getXPosition() + 1;
         int pY = pieces[PAWN][pieceTeam][pieceIndex].getYPosition();
         
         // gets the appropriate rank for the direction the pawn is moving
         if (pieceTeam == WHITE) pY -= 1;
         else pY += 1;
         
         // performs the same task as highlightSquare(), but for the squares
         // a pawn can make an attack to
         for (int i=0;(pX < 8) && (pX > -1) && (i < 2);pX-=2,i++) {
            boolean squareEmpty = false;
            Piece presentPiece = null;
            if (chessSquare[pX][pY].hasPiece())
               presentPiece = chessSquare[pX][pY].getPiece();
            else squareEmpty = true;
               
            if (!squareEmpty) {
               if ((presentPiece.getTeam() != pieceTeam)
                   && (presentPiece.getType() != KING)) {
                  if (hard) {
                     chessSquare[pX][pY].setBackground(new Color(255,0,0));
                     chessSquare[pX][pY].setState(ATTACKABLE);
                  } else {
                     chessSquare[pX][pY].setBackground(new Color(127,127,127));
                  }
               }
            }
         }
      }         
      
      // if this displaySquares() call is as a result of a piece being
      // selected...
      if (hard) {
         // changes the game state
         logic.setState(PIECESELECTED);
      }
   }
   
   /*
    * Wipes the ChessBoard restoring the initial black & white checks.
    */
   private void defaultSquares() {
      // this is the same code from the constructor earlier
      colour = true;
      for (int x = 0; x <= 7; x++) {
		   colour = !colour;
	      for (int y = 0; y <= 7; y++) { 
	         if (colour) chessSquare[x][y].setBackground(new Color(0, 0, 0));
	         else chessSquare[x][y].setBackground(new Color(255, 255, 255));
            chessSquare[x][y].setState(NONE);
		      
		      colour = !colour;
		   }  
	   }
   }
   
   /*
    * Highlights a given ChessSquare with colours dependent on a number of 
    * factors.
    */
   private boolean highlightSquare(int x, int y, int team,
                                   boolean pawn, boolean selected) {
      boolean squareEmpty = false;
      // get the piece on the selected square, if applicable
      Piece presentPiece = null;
      if (chessSquare[x][y].hasPiece())
         presentPiece = chessSquare[x][y].getPiece();
      else squareEmpty = true;
      // if this highlighting is a result of a piece being selected, rather
      // than just moused over...
      if (selected) {
         // if the square is clear...
         if (squareEmpty) {
            // sets the colour to green, and the state to that of a legal move
            chessSquare[x][y].setBackground(new Color(0, 100, 0));
            chessSquare[x][y].setState(MOVABLE);
            
            // return that the square doesn't represent an obstacle
            return false;
         // ...else...
         } else {
            // ...if the square is occupied by an enemy piece...
            if (presentPiece.getTeam() != team) {
               // ...and the selected piece isn't a pawn (because their move
               // forward can't be used as an attack), and the occupying piece
               // isn't the enemy's king (because they can't be taken, only
               // put in check)...
               if ((!pawn) && (presentPiece.getType() != KING)) {
                  // sets the colour to red, and the state to that of a legal
                  // attack
                  chessSquare[x][y].setBackground(new Color(255, 0, 0));
                  chessSquare[x][y].setState(ATTACKABLE);
               }
            }
            
            // return that the square does represent an obstacle
            return true;
         }
      // ...else, if the piece has just been highlighted over, do the same as
      // before, but with fainter shades of red and green 
      } else {
         if (squareEmpty) {
            chessSquare[x][y].setBackground(new Color(127, 127, 127));
         
            return false;
         } else {
            if (presentPiece.getTeam() != team) {
               if ((pawn) 
                   && (presentPiece.getType() != KING)) {
                  // sets the colour to red, and the state to that of a legal
                  // attack
                  chessSquare[x][y].setBackground(new Color(127, 0, 0));
               }
            }
         
            return true;
         }
      }
   }
}
