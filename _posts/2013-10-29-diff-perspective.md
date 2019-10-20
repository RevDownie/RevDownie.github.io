---
layout: post
title: Looking at Things from a Different Perspective
---

I often read that as a programmer you often have to approach problems from a different perspective in order to find the most elegant solution. Well I was made to look a right idiot the other day when I was arguing a new feature was going to increase the scope of the project; only to find out it wouldn't. Worst of all it was a designer that came up with the solution!

The game we are working on has many complex systems built from components. In the game components control the generation of resources, the trading and transporting of resources and the consumption of resources. One of the main system loops involves a farm growing crops over a period of time and employees are dispatched to fetch these resources periodically. Each farm is a field in which the crops are grown and people collect the crops from the farm. Effectively the crops are a single resource.

The designers wanted to add a system in which the player could grow and harvest trees instead of crops.

*Me:* "Brilliant; we already have a system for that. We will just create a new farm type that grows tree crops. Pop on a producer controller and a fetch controller - job's a good 'un".

*Designer:* "Yeah, yeah something like that. Except that trees can grow anywhere across the map and employees must visit each tree, in turn, to obtain the wood, and once the wood is obtained the tree takes time to regenerate, and the trees aren't really owned by the farm because employees can gather from any tree. But other than that it's just like a farm"

*Me:* "So...nothing like a farm. This will require a whole separate system in which field and farms are separated, in which employees work their way through every crop in a field. Each crop must have its own state to manage individual regrowth. Is it really worth all the extra time?"

*Designer:*  "Hmmmmm..."

*Designer:* "Why not just make each tree a farm?"

*Me:* "Excuse me a minute... *stabs pen through eye*"