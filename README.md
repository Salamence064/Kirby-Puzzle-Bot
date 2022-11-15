# **Kirby Puzzle Bot**
* ### About
* ### How it Works
* ### Levels
* ### Level Components
* ### Abilities
* ### Expansion

___

## About
A mini-puzzle game themed around Kirby. 
The player can choose abilities by reacting to the 
message or using the -give command. The player can also 
remove abilities by removing one's reaction or using 
the -remove command. The player starts the game using -start.

___

## How the Bot Works
This project relies on a GameManager class and a 
LevelManager class working in tandem to handle each 
aspect of the puzzle game. Both classes are static 
to be easily interacted with by the bot's listener 
and commands. The GameManager contains a static 
HashMap with GameManager objects and the userID as 
keys. This allows each user to have a unique instance 
of the game.

___

## Levels
Each level is an image obtained through its URL. 
Levels contain a variety of components referenced in 
the section below. The player must assess how each 
component will interact to solve the puzzle by choosing 
the right abilities. The functionality of each ability 
is referenced in a section below.

___

## Level Components
Descriptions of each component that could appear in a level.
It is recommended to read this before playing the game.
___
![Block Image](https://cdn.discordapp.com/attachments/1035631092373393418/1040290688841027605/block1.png)
<br> Blocks are solid, unbreakable, 
immovable, and cannot be passed through.
___
![Ice Block Image](https://cdn.discordapp.com/attachments/1035631092373393418/1040291361829695488/iceBlock1.png)
<br> Ice blocks are solid, immovable, and cannot 
be passed through. Ice blocks can be melted by fire.
___
![Stone Block Image](https://cdn.discordapp.com/attachments/1035631092373393418/1041105755597721641/stone1.png)
<br> Stone blocks are solid, immovable, and cannot be 
passed through. Stone blocks can be destroyed by bombs 
and wheels. They will fall to the ground if not held 
up by anything else.
___
![Star Block Image](https://cdn.discordapp.com/attachments/1035631092373393418/1041106352388460666/starBlock1.png)
<br> Star blocks are solid and cannot be passed through. 
Star blocks can be picked up by the throw ability and 
destroyed by bombs, swords, tornados, and wheels. Star 
blocks can be inhaled if Kirby has no ability.
___
![Dotted Block Image](https://cdn.discordapp.com/attachments/1035631092373393418/1041106328577392680/dottedBlock1.png)
<br> Dotted blocks are solid, immovable, and unbreakable. 
Dotted blocks can be passed through if Kirby has the
ninja ability.
___
![Wire Image](https://cdn.discordapp.com/attachments/1035631092373393418/1041107505708793866/wire1.png)
<br> Wires are solid, immovable, and cannot be passed through. 
Wires can be destroyed by cutter and can hold up other 
components. Wires can carry an electrical current and will 
drop what it's holding if it's carrying one.
___
![Reinforced Wire Image](https://cdn.discordapp.com/attachments/1035631092373393418/1041107529637302342/reinforcedWire1.png)
<br> Reinforced wires are solid, immovable, unbreakable, and 
cannot be passed through. Reinforced wires can carry a current 
and will drop any component it is carrying if it's carrying one.
___
![Rope Image](https://cdn.discordapp.com/attachments/1035631092373393418/1041107469092519966/rope1.png)
<br> Ropes are solid and immovable. They can be passed through 
and broken by cutter and sword. Ropes can hold up other components 
and will drop what it's holding when broken.
___
![Ability Discovery Image](https://cdn.discordapp.com/attachments/1035631092373393418/1041109045064499261/abilityDiscovery1.png)
<br> Ability discoveries allow Kirby to discover a new ability 
during the level. This ability does not have to be one 
of the start of the level choices. Kirby is forced to 
pick up an ability discovery if he is able to. It will 
override any current abilities.
___
![Door Image](https://cdn.discordapp.com/attachments/1035631092373393418/1041109045832069200/door1.png)
<br> Doors are the exits for each level. Reach the level's 
doors to complete it.
___
![Waddle Dee Image](https://cdn.discordapp.com/attachments/1035631092373393418/1041109046171795466/waddleDee1.png)
<br> Waddle Dees are basic enemies. If Kirby comes into 
contact with one, he will lose the level and have to 
restart. Waddle Dees can be killed by beams, ninjas, 
swords, and tornados.
___
![King Dedede Image](https://cdn.discordapp.com/attachments/1035631092373393418/1041109045387477143/dedede1.png)
<br> King Dedede is a boss. Boss fights require Kirby to hit 
the boss twice without getting hit once. Kirby will 
get to choose between 5 abilities at the start of each
turn. If Kirby fails to defeat the boss in 5 turns or 
fewer, he loses.
___

## Abilities
Descriptions of each ability that could appear in a level.
___

### Beam
Kirby sends out a line of beams which damages 
all enemies in its path.
___

### Bomb
Kirby can place bombs that will explode a 
couple seconds later. These bombs can blow up stone 
and star blocks.
___

### Bubble
Kirby can send out a bubble which puts anything in a bubble. 
The bubble will pop if it hits the ceiling, and it'll keep 
rising unless there is a component to hold it up.
___

### Cutter
Kirby can shoot a boomerang-esk projectile that can cut 
through wires and ropes. This will make them drop whatever 
they are holding.
___

### Electric
Kirby can release an electrical current. This current can 
be carried by wires and reinforced wires, forcing them to drop whatever 
items are held. The currents can also damage any enemies 
touching the wires.
___

### Fire
Kirby shoots fire from his mouth. This fire can 
melt ice.
___

### Ice
Kirby is able to shoot ice from his mouth. The 
player can freeze level components and put out fires.
___

### Ninja
Kirby becomes a ninja. Kirby is able to kill 
enemies with his ninja stars and sneak through dotted 
blocks. Kirby can also wall jump as a ninja.
___

### None
Kirby is able to inhale star blocks without an ability 
but can't do much else.
___

### Sleep
Kirby will fall asleep and be unable to move or 
fight enemies.
___

### Stone
Turns Kirby invulnerable while limiting movement 
and preventing him from jumping. Kirby cannot attack 
enemies as a stone.
___

### Sword
Kirby wields a sword. He's able to damage enemies 
and cut through ropes using it.
___

### Throw
Kirby gains the ability to throw star blocks and 
enemies the same size as him. Kirby can damage enemies 
using these star blocks and can throw blocks into 
other components to move them.
___

### Tornado
Kirby gets the ability to turn into a tornado. 
He's able to break through star blocks and damage 
enemies using it.
___

### Wheel
Kirby can turn into a wheel. In his wheel form 
Kirby is able to break through star blocks, stone blocks, 
and damage enemies. Kirby will be forced out of his wheel 
form if he hits a wall.
___

## Expansion
This project could be expanded by the addition of more 
levels. These new levels will introduce new mechanics 
such as combining abilities, greater environmental 
interaction, and chase-like boss fights which make 
use of ability discoveries. Further, mini-games could 
be added to add greater variety and an option for those 
uninterested in the base game.