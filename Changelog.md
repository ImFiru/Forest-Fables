# Changelog
All notable changes to this project will be documented in this file.
 
## [v0.1] - 28/04/2024
 
This is the initial release of the game, for now its only a jFrame with a rectangle.
 
### Added
- Main Class:
  - Game
  - GamePanel
  - GameWindow
  - MainClass

## [v0.1.1] - 28/04/2024
 
Added input handling to allow the user to control the movement of the rectangle, and enabled the rectangle to move in response to the user's input.
 
### Added
- Inputs Class:
  - KeyboardInputs
  - MouseInputs

### Changed
- Main Class:
  - Game
  - GamePanel

## [v0.1.2] - 28/04/2024
 
Added the game loop and fps counter to the game. Made so the rectangles now move until they touch the edges of the screen and change directions, and spawn more with the user input.
 
### Changed
- Main Class:
  - Game
  - GamePanel

## [v0.1.3] - 28/04/2024
 
Added images to the game. For that i created a res folder where all the images will be saved.

### Added
- Res Folder:
  - player_animations.png
### Changed
- Main Class:
  - GameWindow
  - GamePanel
- Inputs Class:
  - MouseInputs

## [v0.1.4] - 28/04/2024
 
Added animations to the game. Updated and created some methods to work with the animations.

### Added
- Utilz Class:
  - Constants
### Changed
- Main Class:
  - GamePanel
- Inputs Class:
  - MouseInputs
  - KeyboardInputs

## [v0.1.5] - 29/04/2024
 
Updated the game loop so now it has a diferent logic to update the game instead of using the render section.

### Changed
- Main Class:
  - Game
  - GamePanel

## [v0.1.6] - 29/04/2024
 
Created the player class and organized the code. Changed the direction control of the player to boolean values. Fixed problems when the window loses focus.

### Added
- Entities Class:
  - Entity
  - Player
### Changed
- Main Class:
  - Game
  - GamePanel
- Inputs Class:
  - KeyboardInputs

## [v0.1.7] - 29/04/2024
 
Created the level creation classes and add the sprites for the tiles and level data, and cleaned up the code.

### Added
- Levels Class:
  - Level
  - LevelManager
- Utilz Class:
  - LoadSave
### Changed
- Main Class:
  - Game
  - GamePanel
- Entities Class:
  - Player
- Utilz Class:
  - LoadSave

## [v0.1.8] - 29/04/2024
 
Added collisions to the player, and fixed the problem with the player clipping trough the tiles when he is bigger than the tile size.

### Added
- Utilz Class:
  - HelpMethods
### Changed
- Levels Class:
  - LevelManager
  - Level
- Entities Class:
  - Entity
  - Player

## [v0.1.9] - 29/04/2024
 
Added gamestates to the game. And added 3 buttons to the menu to swich between gamestates.

### Added
- UI Class:
  - MenuButton
- Gamestates Class:
  - GameState
  - Menu
  - Playing
  - State
  - StateMethods
### Changed
- Inputs Class:
  - MouseInputs
  - KeyboardInputs
- Main Class:
  - Game

  ## [v0.1.10] - 29/04/2024
 
Added game menus and an animated background for the levels

### Added
- UI Class:
  - PauseOverlay
  - PauseButton
  - SoundButton
  - VolumeButton
  - RrsmButton
### Changed
- Entities Class:
  - Player
- GameStates Class:
  - Menu
  - Playing
- Levels Class:
  - Level
  - LevelManager
- Utilz Class:
  - LoadSave
  - Constants

    ## [v0.1.11] - 29/04/2024
 
Added enemies to the game, cleand up the code and fixed some bugs. Added some overlays and modified a little of the classes

### Added
- Managers:
  - EnemyManager
  - LevelManager
- Overlays:
  - GameOverOverlay
  - PauseOverlay
  - LevelCompletedOverlay
### Changed
- Entities Class:
  - Player
  - Enemy
  - Entitiy
  - Mushroom
- GameStates Class:
  - Menu
  - Playing
  - State
  - StateMethods
  - GameState
- Levels Class:
  - Level
- Utilz Class:
  - LoadSave
  - Constants
  - HelpMethods