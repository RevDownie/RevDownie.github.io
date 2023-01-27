---
layout: post
title: Playdate Zig - Part 3 Game intro
---

I've been making slow and not that steady progress with my Playdate game in Zig. So thought I'd write a bit of a status update of where I am with it, what I'm trying to achieve and some of the challenges I have faced or are likely to face. As much to get my own thoughts straight as anything else.

For anyone interested in the WIP - the repo is open sourced here: [Playdate-Next Repo](https://github.com/RevDownie/playdate-next). When the game is done I might release it through Itch.io or something.

## Premise
The game is heavily influenced by one of my favourite mobile games *Minigore*; which is a fast paced twin-stick shooter in which you fight off hordes of monsters/zombies/etc. I remember playing *Minigore* for the longest time, dying and retrying over and over. They really nailed the gameplay experience with the balance between the weighty feel of the enemies (they took multiple hits and were pushed back) but also were lightweight enough that you could take on a horde and win. The chaos and carnage of having 100 or so enemies on a small mobile screen was great fun.

The thought of having swarms of enemies surrounding you and mowing them down with a little Playdate crank-powered gatling gun just really appealed to me. Because of the input differences, the game I'm making will be similar to the "auto-aim" mode in *Minigore*. So the player is focused on shooting, moving to avoid enemies and moving to gather resources and the game will auto-pick the targets to aim at.

The aim of the game will be to beat your highscore for any given level but also to collect unique artefacts that will appear per level.

![Minigore](/assets/Minigore.jpg)

## Vision

### Player experience
..."One more shot", the player mutters after being overcome by a horde of enemies. Quickly they jump back into the game. A new level boots up, it's one they haven't played before. The enemies starts to appear, a few at first, moving towards the player's location. The player cranks and the enemies are blown away. The player realises they are in open space and vulnerable from all sides, they start to move. More and more enemies are piling in. The gatling gun is keeping them at bay, pushing them back and allowing the player to keep on the move. "Uh-oh", out of bullets. Player moves around in search of ammo. There are dozens of enemies now. The player is jinking in and out, narrowly avoiding being hit. The player reaches the ammo crate and unleashes a spray of bullets, wiping out a group of enemies. Their score is ranking up fast, combo streak is getting bigger. They are firing and dodging, constantly moving, trying to group the horde together on one side. They find themselves in open ground again and a group of enemies surround the player getting some hits in before the player has a chance to take them out. Health is low, the player needs to go and find more, but ammo is running out too. The player stops firing, conserving bullets and focuses on moving; using a few bullets here and there to clear a path or push an enemy back. Suddenly the player spots an artefact for this level - who knows when they will get a chance to grab it again. The player abandons their search for health and starts moving towards the artefact instead. The horde is 100 strong now, swarming in from all sides. 

"Click click" no bullets left...

![Current screenshot](/assets/PDNextScreen1.png)

### Game is hitting the mark if...
* Tries are short and sweet
* Player is retrying lots - it's addictive - one more try to beat the level / find an artefact / beat the highscore
* Dying and retrying isn't frustrating but super quick
* During a play session (multiple tries) players should experience wins. Beating their highscore or collecting a new unique artefacts

* Players feel powerful mowing down enemies with the gatling gun
* Firing feels visceral
* Enemies feel like a horde, there are lots of them and they swarm the player

* Controls feel intuitive and players find a firing scheme that works for them
* Movement is responsive, allowing the player to get out of tight situations
* Movement is integral to gameplay - where and when to move is part of the strategy

* Players have to make strategic choices about whether to get more ammo or health or other items
* Player should feel smart at times

* Player should feel moments of joy
* Players should experience "oh shit" moments - low health, empty gun, surrounded by swarms

### Theme & style
* TBD - I'm no artist so will likely purchase a model pack. Something like monsters / zombies / ghosts / etc. Erring on the side of cuter characters that are really readable in 1-bit on a small screen
* Cartoon violence
* "Until there are none" - double meaning. Player is trying to take all the horde out but inevitably will become one of them - no humans left
* Environments - TBD (will depend on what can be sourced/bought)


## Game breakdown
### Levels
* Level selection is auto randomised on play. Inspired by Forrest Byrnes. Reduces friction, provides more flavour, plays into the 1-more go to see what's next
* Levels have no dead-ends but have open areas, funnels/chokepoints and obstacles to navigate
* Levels are bounded/enclosed - so there is a finite movement space
* Different levels feel distinct

### Movement
* Player is controlled with the d-pad
* Movement is snappy (very fast acceleration, can move and stop on a dime)
* Player faces towards targetted enemy. If no targetted enemy, player faces the direction of travel
* Basic movement animation (bob / simple walk cycle)
* Camera follows the player (player is mostly centered - probably slightly offset to show more in direction of movement)

### Firing
* Gatling gun (primarily controlled by crank but also hold A/B if preferred)
* Bullets are actual projectiles
* Target enemy is auto-selected based on some criteria (closest for now)
* Gun has finite number of bullets and will stop firing when runs out

### Enemies
* Enemies are knocked back on hit
* Enemies die with a quick ceremony when their health reaches zero
* Enemies move towards the player (while trying not to end up all on top of each other)
* When enemies reach the player they start dealing damage per second
* When the player is killed by an enemy - there is quick ceremony and new level is picked
* Enemies are spawned in around the player to try and surround them - sometimes offscreen, sometimes on

### Pick-ups
* Pick-ups are spawned based on some criteria (need + randomness)
* Health pick-ups give the player *N* health
* Ammo pick-ups give the player *N* bullets
* Artefacts contribute to the players "trophy" screen

### Objectives
* Beat the highscore for the level (highscore is displayed on screen and celebrated if beaten at the end)
* Scoring is based on enemy kills with combo that is broken on hit
* Gather *N* artefacts for each level. Appear based on some criteria (+ randomness). Artefacts are displayed in a "trophy" screen

### Audio
* Unobtrusive music track
* Layer in low ammo and low health backing
* Enemy hit, death
* Player death
* Gun fire, empty
* Reload SFX
* Health up SFX
* Menu SFX

### Misc
* "Trophy" screen showing artefacts collected and highscore per level
* Win/Lose version of "trophy" screen showing new artefacts collected in that run and whether the highscore was beaten
* Restart menu item
* Launcher cards
* Button instructions
* Cartoon violence warning

### Unknowns / Future
* Do levels end? If so is it survive for a period of time? Kill so many enemies?
* Are enemies able to pass through obstacles - are they ghosts? Might be determined by performance
* What will the auto-target criteria be to provide the best experience?
* Will we have more than one type of enemy? Maybe just little and large
* Different players?
* Different guns?

## Status

![Current screenshot](/assets/PDNextScreen1.png)

* Single level
* Isometric (ish) character that you can move around using the d-pad
* Enemies that spawn and swarm towards the player (up to 100 enemies at a time for now)
* 360 degree rendered models (they turn around)
* No obstacles (full open space - unbounded)
* Use the crank (or hold A/B) to fire
* Bullets push the enemies back to give the player some breathing space, hit them enough and the enemies will die (and disappear without any fanfare)
* Let the enemies get on top of you and they will drain your health, if you die the level resets
* Track score for killing enemies with a combo chain for the more enemies you kill without being hit
* Running out of bullets requires a short time to auto-reload where the player is vulnerable
* Menu item to manually reset the level
* Placeholder launcher card

## Optimisations
* Sparse grouping of enemies for bullet v enemy collision detection
* Need to profile whether broader phase render culling for offscreen items has a benefit

## Challenges
* Pathfinding. Potentially having to pathfind / obstacle avoidance for 100+ enemies
* Sorting. As an isometric(ish) game ensuring that the sorting looks appropriate while maintaining good performance
* Mirror. I've heard that hooking up Mirror can really impact performance. Need to test and see
* Gameplay feel/balance. Balancing the number of enemies, how they surround, how many hits to kill, magazine capacity, etc.
* Gun VFX and bullet spawn locations for isometric characters
* Shadows? Don't know if I'll have them

## Potential future topics for blogs
* Art pipeline from 3D models to 1-bit sprites
* Architecuture of game
* Approach for a 1-person title and how it differs from my day job
* Sprites vs bitmaps
* Culling or not
* Audio using the Playdate C SDK
* Collision detection
* Performance optimisations in general
* Importing level layouts
