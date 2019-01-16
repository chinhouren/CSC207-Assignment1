# Meeting 4
## New Games
* Minesweeper
* Grid Filler

## Minesweeper
* Difficulty levels:
  * Easy:   8x6 board
  * Medium: 10x8 board
  * Hard:   12x10 board
* Number of mines = ceil(# tiles / 7)  (adjust when testing)
* Tiles have 2 states: unrevealed and revealed
* 12 tile images: unrevealed, empty, mine, flagged, numbers 1-8
* Board generation:
  1. Generate board will empty and mine tiles
  2. Shuffle board
  3. Iterate through tiles and set numbers (according to neighbours)
* Timer
  * Saved in a variable
  * Pauses on loss focus (onPause)
  * Time is score at end
* Tapping tiles: tap on board to select a tile, then tap on action button (bottom of screen)
* On lose, go to a new GameOverActivity, which display the revealed board

## Grid Filler
* Size is constant
* Place one tetromino at a time
* Display preview below board
* FillerBoard will handle checking/clearing rows and columns, placing blocks
* Blocks have an 'anchor', anchor is placed where tapped
* Create new Tetromino class that holds colour and method to 'build' block
* Score is the number of blocks that were placed

## Refactoring
* Create abstract super classes Tile, Board, BoardManager, GameActivity; create subclasses for individual games
  * Change isValidTap, touchMove, gameOver (renamed puzzleSolved) to abstract
  * Add getScore and getGameType/Difficulty/??? to BoardManager 
* Create interface for Undoable and Saveable