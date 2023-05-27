# My Personal Project

## An Introduction

This project is one I've wanted to do for a while. It is based on Adenture, one of the first and most popular purely 
text-based games, mixed with Undertale, a text-heavy game with a graphical interface, and Inscryption, a game that
constantly changes graphical interfaces. The reason for this choice is twofold: One, I need to begin with a console 
application and then create a graphical interface, so my project must be text-heavy, and two, I'd like to create a game 
I am proud of and that my friends can play.

Originally, I was going to create this game for IOS. I felt like the text-based game market for mobile phones was 
lacking, and I thought that mixing an old concept with new graphical elements could definitely be popular. However,
I don't know the Swift programming language, and I feel like making this game in a controlled environment with clear
deadlines will both speed up the process and let me do something I've wanted to do for a while within an educational
framework.

## A description

### Themes
The player finds themselves in front of an old house on a dilapidated gravel road, with a forest surrounding them. 
With a limited inventory capacity, they must then find items, and use them accordingly (e.g. finding a key to open a 
door), to eventually solve the mystery of the world. I would like to pull from traditional text-based themes in a modern
framework. For example, Adventure was published in 1980, long before the World Wide Web was made public, where floppy
disks and blocky monolithic Cellular Phones were the peak of portable tech. The game Adventure itself was created 
in the rise of technology, and, in a way, its dilapidated old houses and heavy nature themes were a nostalgic effort
to reminisce of the days gone, washed away by a sudden wave of technology. In a world where technology is normalized,
especially in the younger generations, these themes are practically meaningless, as these younger generations had never
experienced a techless world. In other words, although one can appreciate these themes for their purity, it is difficult
to relate to them and therefore a text-based game with similar themes to the original Adventure would not be very 
popular amongst young gamers. For this reason, I will, at some point in the game, subvert the expectations of the 
player, and drastically change the context and themes of the game, similar to how Inscryption subverts expectations by 
changing the graphical interface. 

### Game within a game
Perhaps I will do this by having nearly every path the player takes lead to certain death, 
and, after the player's death, the *actual* player takes off their VR headset, and is in a technology-filled world. 
I believe this will be popular with both younger generations and older ones, as the drastic change of scenery can be 
interpreted by the younger generation, as a breath of fresh air, as a relatable and ironic shift, almost mocking the 
generations that once lived without technology, and by the older generations as an apt abstraction of the sudden
technological advancements that they couldn't necessarily keep up with.

So, following this, every time that the (real) player dies in the game's VR world, they will be able to play a short 
duration in the real world, before trying again in the VR world. This is not only meta-gameplay, but it is also like 
playing two games at once. Outside the VR world, the player will be able to interact with items all the same, 
but they will be modern items (e.g. a flashlight instead of a lantern). The ability and almost *need* to try again and 
again to beat the VR game will be reflected in the many, **many** ways to die in the VR game. 

### Checkpoints and Saving
Once the actual player has gotten past the beginning of the game a few times, it'd get repetitive, so, on top of being 
able to save the full game's state, I'd like to have a checkpoint system for the VR game, where codes are given based 
on the player's progression that can be entered upon the start of a new game to skip ahead. 

### Graphical Interface
Optimally, I'd like to have a terminal-like graphical interface for the text (VR) game, which will be intelliJ's console
at first, and a separate graphical interface for the *real* world. I was thinking an interface along the lines of a 
point-and-click like game. However, due to my limited knowledge in arts, it'd take me a long time to draw a scene and 
object for everything in the world. So, I may incorporate pointing and clicking on a basic floor plan of 
the meta player's house while still using a terminal for descriptions, objects and most interactions. However, I may
also just design a terminal-like GUI that simply looks different from the VR game's interface.

*note that this is just a preliminary description and may be subject to drastic change*

## User Stories
- As a user, I want to be able to put items in my character's inventory.
- As a user, I want to be able to take items out of my character's inventory.
- As a user, I want to be able to use items on various places within the game.
- As a user, I want to be able to enter a directional input and (n, e, s, w) and change in-game locations 
based on that input.
- As a user, I want to be able to save the game state when I'm in most game states (e.g. not already within a command)
- As a user, I want the option to load from a save file when I start the game


## Instructions for Grader
### Interacting with Items
- Items can be interacted with dynamically (i.e. without typing) using the two buttons called "drop" and "grab"
- These buttons can be accessed by clicking the "cheats: off" button. This turns the cheats on,
- and changes the gif of stars to the cheat menu, where the following can be done:
  - The grab button will either 
    - Open a new frame with a list of items you can grab, which you can click on to add the item to your inventory
    - Or, print to the GUI that you have no items in range that you can grab
  - The drop button will either
    - Open a new frame with a list of items you can drop, which.... to drop the item on the place you're currently at
    - Or, print a message to the GUI that you have no items in your inventory
- The items in the inventory can be found in the "inventory" tab at the top of the GUI

### Visual Component
- The visual component is the lil' dude 
- His mood changes based on different actions the player takes
  - for example, if the player looks for items, the lil' dude will look around

### Saving and Loading
- The user saves the state of the application by clicking on the "save" button at the top of the GUI
- The user loads the state of the application by clicking on the "load" button at the top of the GUI
- _Note that saving is only enabled once a name has been entered. Loading, however, is always enabled_

### Miscellaneous
- Other than this, it is the same interaction as with the console. I.e. commands can be typed in in the text field on the bottom right of the screen
- write "help" for help! Good luck. 


## Phase 4: Task 2
_representative sample of logging events_
World Generated. ||||| Wed Nov 23 16:12:58 PST 2022
Moving player from 00001(street) to 00003(house porch) ||||| Wed Nov 23 16:13:05 PST 2022
Moving player from 00003(house porch) to 00001(street) ||||| Wed Nov 23 16:13:09 PST 2022
Item with code 2 and name silver key moved from inventory 00001 to 99999 ||||| Wed Nov 23 16:13:15 PST 2022
World saved. Number of places saved: 5. Number of items: 1. ||||| Wed Nov 23 16:13:18 PST 2022
World reading begins. Number of places: 5. Number of items: 1. ||||| Wed Nov 23 16:13:24 PST 2022
Item with code 2 and name silver key moved from inventory 99999 to 99999 ||||| Wed Nov 23 16:13:24 PST 2022
World reading ends. ||||| Wed Nov 23 16:13:24 PST 2022
Moving player from 00001(street) to 00003(house porch) ||||| Wed Nov 23 16:13:31 PST 2022
Player unlocked 00002(house) with item 2(silver key). ||||| Wed Nov 23 16:13:33 PST 2022
Moving player from 00003(house porch) to 00002(house) ||||| Wed Nov 23 16:13:35 PST 2022
Item with code 2 and name silver key moved from inventory 99999 to 00002 ||||| Wed Nov 23 16:13:37 PST 2022
World saved. Number of places saved: 5. Number of items: 1. ||||| Wed Nov 23 16:13:41 PST 2022


## Phase 4: Task 3
To start, I think that my world class is wholly unnecessary, but I can't see a way to refactor it. Unnecessary may
be a strong term to use here, but it repeats so much code that it has become a glorified getter class. However,
it's the backbone for saving, event logging, and so much more interaction, as it provides a structure to interact
with all elements of a _world_ at once. To fix the repetition of code, I'd refactor the world class to simply 
return immutable copies of the objects we want values from. The immutability is important as the world class acts as a
'middle man' between GameDemo and the other model classes, and it should be the sole editor of said classes.

Another thing I'd refactor is the "jsonCode" field for inventories. This was initially simply supposed to be a way
of storing the locations of items based on a unique code for every given location, but quickly turned into a way to
identify locations by code. So, I'd either rename it, or create an entirely new code that isn't related to saving and 
loading.

If the Single Responsibility Principle was sentient, it'd despise my GameDemo class. It was originally just supposed
to setup the world, with all of its elements, and that's it. However, it became the longest single file of code I've 
ever written. This is because, instead of splitting off its different helper methods (which are basically just
helper classes), I just packed them all into the one class. The one exception to this is TextFileParser, back in the
early days when I still had hope for GameDemo. So, to clarify, to refactor GameDemo, I'd split up the graphical 
interface into one class, the command parsing into another, and finally the world building into a final class. This way,
the game demo class would simply run the game, using the necessary classes and the methods therein, instead of having 
all the game's functionality in one class. Also, this would allow me to make an actual game, not only a game demo,
since I could just re-use the pre-existing classes.

There's also some duplication in TextFileParser that I could turn into another method in that class. I would _not_ turn
the Player class Singleton, since there would possibly be two playable players further down the line.

Finally, the UML diagram is confusing and associations can be cut down and turned into dependencies. For example, I
could remove the GameDemo associations to player, world, etc. and turn those associations into dependencies that are
fed into the world.






