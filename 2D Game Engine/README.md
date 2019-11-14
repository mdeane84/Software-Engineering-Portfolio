# 2D Game Engine

This is a game engine built using only default Java libraries. It has interconnected item, entity, crafting, and inventory systems. I wrote all the Java code for this project, but the art assets were supplied courtesy of my friend, Jack. You can view the source code in the 'src' file tree, or run it using the 'launcher.jar' executable java archive file. Assets are saved in the 'res' file tree.

### Credit
* **Art Assets** - *Jack A Wilson*

## Controls

The inventory and crafting systems can be manipulated using the mouse cursor. The game uses WASD movement and entities in the world can be destroyed by "attacking" them. Attacking is directional, meaning the character will attack in the direction of the mouse. Trees can only be broken using an axe, rocks can only be broken using a pickaxe, and a knife will kill the "frizard" faster than fists. flowers can be harvested without a tool. Tools and other materials can all be crafted.

### Movement

* **up** - W
* **Left** - A
* **Down** - S
* **Right** - D

### Interaction

* **Attack** - Left Mouse Button (*a box appears for a single frame indicating the hitbox*)
* **equip** - Right Mouse Button (*alternatively, an item can be drag-n-dropped in the appropriate slot*)
* **pick up** - Spacebar
* **crafting** - c
* **inventory** - e (*a storage item must be equipped - i.e. the bulb pouch*)
