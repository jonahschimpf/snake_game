=:=:=:=:=:=:=:=:=:=:=:=:=:=:=:=:=:=:=
CIS 120 Game Project README
PennKey: _______
=:=:=:=:=:=:=:=:=:=:=:=:=:=:=:=:=:=:=

===================
=: Core Concepts :=
===================

- List the four core concepts, the features they implement, and why each feature
  is an appropriate use of the concept. Incorporate the feedback you got after
  submitting your proposal.
I didn't get any feedback, but I did change some stuff up.
  1.  IO
I log the highscore and user inputed username into a file using IO. The scores are ordered from 
highest to lowest and stores three users. The format is SCORE:NAME and deals with errors in this 
format
  2. JUnit
I test every testable function and constructors in SnakePiece, Snake, and BoardArray.
This is because there are invariants that are not allowed to be broken in Snake. I also created 
useful functions that simulate a game when called, and when called compared the values of the game
 state to the expected game state values
  3. 2DArray/LinkedList
The snake lives in a 2d array. In fact the entire game is situated within a 2d GameObj array. 
Moreover, the snake is literally a custom built doubly linked list.
  4. Interfaces
The interface powerup. There a three power ups in this game of snake. 1) Is the food up which adds 
a piece to the snake. Another is the death up with ends the game. Another is the winUp which makes
 you automatically win the game. All three of these implement power up which makes these objects 
 have two functions:
1) Effect() - takes in the snake, effects it accordingly, and returns it
   isReady() - which analyzes the current game state and returns a boolean if the game should place
    the powerup into the board.

=========================
=: Your Implementation :=
=========================

- Provide an overview of each of the classes in your code, and what their
  function is in the overall game.
 BoardArray- Models the board in which the Snake is played. Takes in gameobjects and displays them
 GameObj- An item that is placed into the array (board array). Is a generic object that PowerUps and
 SnakePieces implement
 SnakePiece- This is what a Snake is made up of. It implements GameObj and is placed into the array
 It is essentially a node with two pointers which will be placed into a doubly linked list
 Snake- This is made up of snake pieces. It is essentially a linked list
 FoodUp - The "food" piece a snake eats to gain a piece
 WinUp - Interface class where if you eat it, you win! Is placed very briefly and sporadically on 
 the board
 DeathUP - The intereface class where if you eat it, you lose! Is similiarlly placed sporadically
 and on certain levels.
 PowerUp - The interface the previous three classes all implement
 Positions - A custom class that acts as an (int,int) tuple. 
 Logs the positions of game object
 Score - A custom class that represenets a score. A score has points and a user associated with it
 

- Were there any significant stumbling blocks while you were implementing your
  game (related to your design, or otherwise)?
No.
- Evaluate your design. Is there a good separation of functionality? How well is
  private state encapsulated? What would you refactor, if given the chance?

I wish I would have looked at the javadocs way more thoroughly prior to starting. I would implement
 more methods that deal with timers because I feel that they could be used in a better way
  (such as when generating randomness or time the PowerUps should be on screen). 
  I might also refractor the implementation of gameobj to be more inclusive.
  Other than this, 
  I feel that the design is straightforward and relatively intuitive.


========================
=: External Resources :=
========================

- Cite any external resources (libraries, images, tutorials, etc.) that you may
  have used while implementing your game.
N/A