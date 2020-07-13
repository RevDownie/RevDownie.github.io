---
layout: post
title: Tom's Data Onion - A language a layer
---

I stumbled across a new programming puzzle on *r/programming* called "Tom's Data Onion" (https://www.tomdalling.com/toms-data-onion/). The puzzle (well actually set of puzzles) is built in layers such that solving one layer generates the instructions for the next layer. I'm not going to cover how I solved the puzzles (my solutions are on GitHub if anyone is interested https://github.com/RevDownie/TomsDataOnion) but instead talk a little bit about the languages I used to solve the puzzles. 

I do quite a lot of coding challenges (CodinGame, Reddit Daily Programmer, Advent of Code, etc) mostly because I enjoy them but partly because I see them as a good opportunity to practice langauges I've not worked in for a while and also as a way to learn and test out new languages. I thought it would be fun to try and solve each layer of the Onion in a different language (BTW before this I'd never written an ascii85 decoder and now I have about 4 in various states of completeness).

I wanted to use a mixture of languages that had various styles, track records and different sized standard libraries. I ended up using the following:

- **Zig** - Touted as a genuine replacement for C, I've been messing around with Zig recently and really enjoying its simplicity
- **Rust** - Potential game dev language of the future. I've not used it in about a year and wanted to make sure I don't forget how
- **Python** - For these types of puzzles you often can't beat Python for implementation time efficiency. I use Python quite a lot and puzzles can be a good way to probe new areas of the language
- **Go** - Like with Rust, I haven't used Go in a couple of years and wanted to refresh my memory
- **C++** - My bread and butter but I've been in C# land alot recently and wanted to brush off any cobwebs
- **Kotlin** - Occasionally at work we have to interface with Android in Java. I wanted to explore Kotlin as an alternative to Java and hadn't had a chance to look at it until now
- **Swift** - Similar to Kotlin in that I wanted to evaluate Swift as an alternative to Obj-C and again hadn't yet had a chance to mess around with it

Sort of in-keeping with what I felt the spirit of the challenge was (i.e. often the puzzle pointed to RFC docs) I was determined not to use any 3rd party libraries but to write the code myself. This also had the benefit of allowing anyone else to run my solutions without external dependencies - however I did allow myself to use any modules from the standard libraries. I know languages like Rust, Go and Python really shine with their support for external modules but I'd made my mind up.

An interesting side effect of using a language per layer when each layer required many of the same techniques (aside from writing about 4 different ascii85 decoders) is that I got a chance to see the relative strengths and weaknesses of the languages for solving these types of problems (mostly file reading/writing and bit manipulation). I thought I'd note down some of my observations about how each language faired for this particular puzzle.

## Zig

I've only started using Zig recently but the more I use it the more I enjoy it. It's probably the least well known of the languages on the list so will spend a bit more time outlining why I like it. Part of its ethos is that there is only one way to do something so you don't get that analysis paralysis that you find with other languages (like Rust and C++). Despite it not having a large standard library, and therefore no off the shelf base85 decoder, (which is fine - I like the lean approach) I found I was able to implement the solution to layer 0 pretty quickly. I think this is largely because you don't have to think too in-depth about what approach to take. 

Even though it has quite a minimal standard library the file reading/writing support was good and as easy to use as any other language. While it is a "safe" language, and doesn't allow implicit casting willy-nilly, I didn't find doing low level bit manipulation too cumbersome (@truncate to the rescue). Another thing I discovered about Zig is how easy it is to write and run unit tests with its built in test framework - this was a nice surprise! Also it has Zig fmt so no squabbling over style (which I appreciate more and more as I get older). Plus like most modern lanaguages there's no need for complex external build programs it comes with its own build toolchain. Something I'd never seen before in a language is that Zig supports arbitrary size integers; this can make bit packing super simple (for example in base64 encoding when you pack to 24 bits you could use a u24). One thing I actually really like about Zig is that it strongly encourages you to use pre-defined stack memory by default. For programs like my puzzle solver I can make very accurate, if not perfect, estimates of how much memory I need because the data is fixed - however I'm not sure how easy it is to use the allocators if the memory requirements weren't as well defined (but TBH you usually have a good idea of the max memory requirements).

Zig's documentation is pretty good but not complete and it doesn't have a large community so not much of a Stack Overflow presence. Despite that I've been able to get up and running with it pretty easily; thanks to the existing documentation and the Zig GitHub which has tests and examples to reference. I think my only real gripe with Zig is that it doesn't have any standard 'for' loop syntax. Range based looping is great (and with slices is probably sufficient in 99% percent of cases) but for this type of puzzle work you often need the index of the loop and the ability to increment in multiples of 1 and it feels cumbersome to use a while loop to do that.

## Rust

It's probably impossible not to have heard of Rust. I'm surprised they don't have cold callers that rock up at your house and tell you to "rewrite all your software in Rust". But evangelism aside, it really is a powerful language with a great toolchain. As a newbie getting into Rust the documentation is second to none and clippy is a great mechanism for learning Rust idioms on the job (except I will die on the hill that ```if x == false``` is more readable and less easily missed than ```if !x```). Despite the evolution of the language, whenever I search for how to do something I seem to always find the most up to date way - this is great when I need to search how to write a file or convert a u32 to a u8 (I'm looking at you Swift!).

As you would expect from a beast like Rust there is a lot of great built-in module support. Particularly for puzzles like this there are useful convenience functions for endianess handling and for packing/unpacking bytes, etc. that can take a lot of the boilerplate away from the developer. Like with Zig the built in testing framework is really great (and useful in puzzles like this to test that my ascii85 decoder worked ok). Curiously, despite me seeing Rust as a C++ alternative, I tend to write my Rust a bit more 'functionally' and find myself reaching for iterators more often than not (so my Rust programs look nothing like my C++ one). For puzzles like this which are often very explicitly about manipulating blocks of homogenous data there is nothing quite as satisfying as a functional one liner. I find that writing functionally in Rust is almost as easy as say Python or Scala (sometimes you do have to fight the borrow checker but for a non-GC language the functional support is excellent). Plus having 128-bit integers is great for bit manipulation puzzles!

I think because Rust is multi-paradigm and has a lot of functionality I find that I probably solve puzzles slower in Rust than other languages because I'm trying to find the 'best' or most idiomatic way to solve the problem. Despite being very similar to Zig when it comes to type safety I found the explicit truncation casting in Rust much noisier than Zig (which is weird because ```val as u8``` is less text than ```@truncate(u8, val)``` - perhaps the infix operator is what makes it seem noisy ¯\\_(ツ)_/¯.

## Python

Nothing really beats Python for programming challenges in terms of implementation speed (can't always say the same for run-time performance). It has a seemingly never ending library of hex functions, base85 and base64 encoders, etc. Despite not being a 'systems' language the bit manipulation and packing (using struct) is pretty good - especially if you need to be explicit about endianess.

I think my main concerns with using Python for the Onion (and other similar challenges) is that you are never quite sure how big your int actually is or whether it has been implicitly converted to a float. Sometimes I want a 16bit int and I don't want it to expand but TBH the variability of the number type never gave me any problems for layer 2. I think writing solutions of this size are about as large as I'd ever want to write with dynamically typed languages.

## Go

Go shares that same great trait as Zig in that there is no debating about how to go about implementing something - you really only have loops, conditionals and functions. I was also pleasantly surprised by how good the std lib was with encoding, decoding and file reading/writing functions (was particularly surprised that it had base85 decoding)

Similarly to Zig I was able to get up and running really quickly and again no real hesitation about the best way to tackle the problem. The documentation was really helpful and, like with Rust docs, having an API doc as well as tutorials and examples is great for finding out what functions are available.

Rust, Zig and Go have error handling approaches that really appeal to me (i.e. error returns). However with Go the error handling can get quite cumbersome and generate a lot of boilerplate code. Quite often (in fact almost always) when I'm writing puzzle solving code I don't expect to gracefully handle any errors because I control the whole pipeline so usually an error is my error and I just want to terminate. My Go script is littered with ```if err panic``` and I feel like Go could do with a force unwrap (like Rust, Zig or Swift).

## C++

C++ is C++. For a number of years I was lost in the wilderness of "modern C++" but now I tend to use more vanilla (non standard library) C++ and dip into std when there is something that I need (like threads or file loading). You really can't beat C++ for certain types of puzzles (knowing how the types work and being able to easily and implicitly cast between them can make the code much easier to read). I actually picked the order of the languages before I saw the puzzles so it was quite fortuitous that the wheel landed on C++ for this one because C++ has the great ability of being able to easily view data through different lenses (where other languages tie the memory too tightly to types). In this particular layer the same block of memory had to be parsed into a header struct (containing length, etc) and also iterated in 2 byte chunks in order to calculate a checksum. This is pretty trivial in C++ just by changing the pointer type and iterating - no need to wrap or copy.

Of course all this convenience comes at a cost and when I ported my C++ ascii85 encoder to Kotlin it caught a bunch of overflow errors! A couple of things that annoyed me with C++ for this particular puzzle was a) there isn't a standard way to prevent struct padding so you can't safely reinterpret memory as a struct (which would have been great for reading the header) and b) There isn't any built in endianess handling so I just had to assume little endian and byte swap the network order data. I think being able to reinterpret memory safely as a struct and at the same time swap endianess if needed would make these puzzle solutions much simpler. The other thing about C++ when compared to all the other languages here is the lack of default toolchain - sticking with clang/LLVM always seems the best approach for me.

## Kotlin

Ideally I would have used Kotlin Native but the particular layer I used Kotlin for required some AES decryption and I had to leverage the weight of the JVM libraries.

There isn't too much to say about Kotlin that hasn't already been covered by the other languages. It's documentation was good and I really liked the iterator support and despite my worries about how much of a PITA bit manipulation would be, because of how strict Java is, it wasn't too bad (obviously there was a lot of explicit casting and I had to keep correcting to the non-standard bit operation syntax shr, shl, etc). I'd really like to give Native a try in future.

One of the little things that caught me out with Kotlin - why is ```0..N``` an inclusive range when in every other language that is exclusive? ```0 until N``` is more cumbersome and is by far the more common case right?

## Swift

Initially the puzzle only had 5 layers so I had a toss up between Kotlin and Swift to see which one I would use. I chose Kotlin because I was worried about having to introduce a dependency on the Xcode toolchain and I wanted to keep the solutions as standalone simple scripts. Thankfully it is actually easy to build and run Swift scripts independently from the command line.

Swift has a lot of the same plus points as the other modern languages I've listed - strong typing, nice error handling, etc. However of all the languages I used (bearing in mind I used Zig which has a small community and Rust that has had several breaking evolutions) I didn't expect Swift was going to the hardest language to pick up - but it was by far! For some reason it seems to be really difficult to find up to date documentation for Swift. I kept finding no longer supported solutions for previous versions. The first party tutorial site was pretty good for grasping the basics but that was it. I found it really hard to find how to read/write files or how to convert bytes to strings (ASCII or UTF-8). I think part of the problem is because Swift and Apple are so linked and there is toll-free bridging with Obj-C libraries it can often be hard to figure out where Swift stops and Cocoa/Foundation/etc. start. FileManager is a carbon copy of the one for Obj-C and has the same PITA hoops regarding URL, document directories, etc to jump through just to read or write a file (feels like there should be a lower level File API implemented in Swift that FileManager wraps).

A couple of things that really killed me with Swift: 1) Why do slices keep the same indices as the underlying array? When is that ever useful? 2) When running from command line without the power of Xcode any crash logs were unsymbolised (and didn't point to a line number) this wasn't the case for any of the other languages.

## Conclusion

There isn't really anything profound in this doc. I picked the order of the languages at random so I think that shows you could really make any of them work for puzzles like this. It was just interesting to get a sense of the frustration points for each language on a mini-project. It was nice to try our Kotlin and Swift but I don't think I'd use them out of anything but necessity - simply because I'd probably use Rust instead. For quick single script puzzles in particular having to symbolicate the crash logs is a bit of a pain with Swift. Kotlin still seems to be in the process of separating from the JVM so perhaps I'll try again in a couple of years. It's nice to see some of the same patterns emerging across the modern languages - built-in toolchains, auto-formatters/linters, built-in test frameworks, immutable by default, return based error handling, etc. If I find time perhaps I'll go back through the layers and thread anything that can can be done so trivially to test out the relative threading capabilities and I'm definitely going to keep messing around with Zig.

If you haven't had a bash at Tom's Data Onion I'd encourage you to do so - but perhaps just re-use the same ascii85 decoder.


