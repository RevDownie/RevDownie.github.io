---
layout: post
title: Functionally Challenged - Dabbling with FP
---

For a while now I have been tempted by the dark art that is functional programming. It's high order functions and powerful one liners hold a certain allure for me and last year I even started playing around with Haskell.

I wasn't ready.

This year I've decided to give FP another crack and have even bought myself a book - ["Functional Programming in Scala"](http://www.amazon.co.uk/Functional-Programming-Scala-Paul-Chiusano/dp/1617290653) (this is an excellent intro to FP and although the examples are in Scala it focuses on teaching core FP principles) and took a Coursera course, [of the same title](https://www.coursera.org/course/progfun), which turned out to be a great accompaniment to the book. So that's how you know I'm serious.

I wanted to learn FP for a few reasons - firstly: I like learning random new things, secondly: everyone is banging on about it and thirdly: I wanted to see if it could improve my C++.

## The Good...
Here are some of my favourite aspects of FP:

 - Everything is immutable and pure (well almost otherwise it would useless).
 - State is explicitly modeled.
 - Almost all programs can be modeled using a selection of higher order functions such as map, fold, etc.
 - Functions are not understudies to classes and can be composed, chained, passed around - brilliant.
 - Generic programming is simple and achievable with little boiler plate code.
 - Pattern matching.
 - Lazy evaluation: No boilerplate for caching the results of expressions! Don't pay for what you don't use!

One of the most impressive aspects of Scala is that you can build almost every language construct from first principles - you can create your own booleans and ints and even create your own if, then, else statements. Completely useless but really shows the power of the language.

## The Bad...
Things I didn't like so much:

- Apparently FP programmers like single letter parameter names (because everything is so generic and based on maths notation). I couldn't bring myself to do this.
- And...that's pretty much it.

In truth because I was using Scala (which is a hybrid OOP/FP language) it was much easier to incorporate state at the edges of my program than it would have been using a pure FP language like Haskell (and state is pretty important - put it this way I'm glad my bank is interested in the state of my account).

One of the issues I had with Scala was trying to determine when to use FP and when to use OOP. Usually I ended up with case classes acting as simple structs holding a collection of data. My big dirty secret is that sometimes I would even use an imperative for loop (what's the harm in a wee ++i) in place of for comprehension on a range.

## And the C++
Unfortunately there is little scope for me using Scala in my day job so I had to salvage what I could and bring that across to C++ - this wasn't as much as I'd hoped. I've always thought of C++ as multi-paradigm (and it is) but even C++11 struggles to do proper FP - you have to pick and choose your battles.

One of the cornerstones of FP is immutability and Scala has a whole bunch of immutable data structures. Updating the contents of a data structure produces a copy of it, and because this happens all the time in Scala, and because the structures are immutable; sometimes a copy isn't even made. Seriously, if you want a substring of a longer string you basically get a reference to part of the original string - it's never going to change. The compiler is set up to perform these kind of optimisations - C++ isn't. If we made a copy of vector, array or map every time we updated an element then we'd be in trouble (imagine creating a new texture every time you updated a texel!).

C++11 has introduced anonymous lambda functions which really help with the functional style and several higher order functions such as std::transform(i.e. map), std::accumulate(i.e. reduce), etc. but could really do with some more convenience functions for, among other things, composing functions i.e.


`def compose[A, B, C](f : B => C, g : A => B) : A => C = {
    a => f(g(a))
}`


Also (and I'm by no means the first to mention this) C++ is in desperate need of a D-lang style "pure" keyword that prevents any non-local state from being modified.

That being said there's always learning to be had:
- **Stop making unnecessary setters!** I do it all the time - "Oh here is a getter without its partner. Must be lonely. Stick a wee setter in there. Job done. Now how do I make this thread safe...?".
- **Sometimes a copy is good.** Although it's usually too expensive to copy a data structure on every modification perhaps it makes sense to batch modifications together and then make a single copy updated with all changes. This pairs up nicely with the data oriented approach of "where there's one, there's many" and actually FP and DOD make good bedfellows.
- **Higher order functions are useful.** I should make more use of std::algorithm to make my code more generic and less verbose (although the lambda syntax in C++ is still a little verbose compared to Scala i.e ```data.map(x => x + 2)``` vs ```std::transform(data.begin(), data.end(), newData.begin(), [](auto x){return x + 2;});```)
- **Model state explicitly.** It's much easier to reason about data and state (not to mention multi-thread it) if it is visible and explicit and covered in warnings and alarms rather than encapsulated in an object that you have no idea is thread safe or not. Concurrency isn't that hard is access to state is carefully managed.
- **Void functions are (usually) stateful functions.** This is so blindingly obvious but I had never really considered it to be the case. Reducing the number of void methods or functions will reduce the amount of hidden state and side-effects in your application.

## Heading over to the dark side

I'd really like to take on a project using a FP language and hopefully I'll get a chance in the course of my work to experiment more with FP. If anyone else is interested in learning FP I'd really recommend approaching it without any OO prejudices and learning it from the ground up. Embrace its strange syntax, higher order functions and immutability - don't fight against it and try and enforce an imperative style. Accept that creating and managing state should be a chore - this will help you mimimise it. Don't be put off because it is different. Different isn't bad it's just different.

I've already started learning about functional reactive programming (again via Coursera) so there is a good chance my next blog post will be about that.

P.S.

I've also attached my first effort at a functional program and my revised effort based on the huge lambasting I got on ["CodeReview"](http://codereview.stackexchange.com/questions/69297/pure-functional-simple-scala-battle-game-simulation) (cheers guys).

- [Battle Original](/assets/BattleOriginal.scala)
- [Battle Updated](/assets/BattleUpdated.scala)