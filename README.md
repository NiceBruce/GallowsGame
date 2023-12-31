<img width="1070" alt="logo" src="https://github.com/NiceBruce/GallowsGame/assets/23197323/a2c2ce6a-413c-4e0e-b4c8-7af483880645">

## Short description

---

_**Hangman**_ is a word game where the player is given a word that he must guess using the letters of the Russian alphabet and the opportunity to make no more than six mistakes.\
Repeated letters are not counted as a mistake.\
- The game is made in **Java language** and **JavaFX tool** for creating cross-platform graphic applications.
- I tried to implement an **MVC-pattern** that consists of three entities - a **MODEL** implemented by the **WordModel** class,\
two controllers **MenuController** and **GameController**, and a **view** implemented by the **GallowsView** class.
- I would like to note that _between the Model and the View_ I have implemented the **WATCH-pattern**.\
The Model is a store of data and current state, and the View _"subscribes"_ to the current state of the Model.




## Installation

--- 
- To start the game you need to run command:\
`mvn javafx:run`


## Game Mechanics

--- 

- At startup, we are greeted by a menu that prompts us to **start a new game** or **exit**.

  https://github.com/NiceBruce/GallowsGame/assets/23197323/e808b56d-458c-4713-9349-54292ce9d75f

- Further, if you start a new game - the menu can be called by the **ESC button**,\
where we will see a new item - _**"RESUME GAME"**_ (it returns the player to the current game).\
You can also close the menu by pressing the **ESC button**.

https://github.com/NiceBruce/GallowsGame/assets/23197323/74cdcd0d-6326-488c-bf50-625e643e3c51


- Also below is a video showing detailed gameplay

https://github.com/NiceBruce/GallowsGame/assets/23197323/51f0eb4e-bdfd-43bf-910d-68b9f2d8a804

