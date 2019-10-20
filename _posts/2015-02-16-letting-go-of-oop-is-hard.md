---
layout: post
title: Letting Go of OOP is Hard
---

![Let Go!](/assets/TugOfWar.jpg)

I read a lot of blog posts these days about the death of OOP, with flocks of programmers abandoning their objects in favour of DOD, reactive or FP (and probably other acronyms I've never heard of). I've been dabbling quite a lot recently with both DOD and FP and it's easy to see why many people have embraced that style of programming, but when it comes to doing my day job I still always think in objects; I can't help it and I also know I'm not alone.

Now, I still believe that object-orientation has a part to play in system design; as do all the other acronyms. There are no silver bullets in programming and all approaches have their own strengths and weaknesses; it's about picking the best solution for the problem at hand.

The problem is, that despite all the recent bluster, I still don't think many people know that there are *any* alternatives to OOP, let alone what their respective strengths are. I watched Mike Acton give a presentation on DOD at a C++ conference and the audience questions at the end led me to believe they all thought he was an absolute nut job. One of the questions that I ask graduates during interviews is what programming methodologies they like and what the strengths and weaknesses are and 90% of graduates answer OOP, strengths: encapsulation, abstraction, etc and weaknesses: O_o. Seriously, most of them have never even considered that there are any other types of programming, let alone weaknesses of OOP; to them OOP is programming. When I prompt graduates about the difficulties of multi-threading OOP applications they shrug "threading is hard".

Even when you know and have used other programming methodologies it is *still* hard not to default to OOP. The worst thing is most of us have twisted OOP so far it doesn't even look like OOP anymore - flat inheritance trees, composition, immutable objects, etc.

I like OOP but I think I, and others, like it too much and a large part of the blame falls on universities (and like so many of the worlds ills - Java). Most uni courses that I've seen (including the one I took) have 6 months of imperative training, maybe a little assembly and then blam! straight onto the objects for 4 years. All the course examples are OOP, frameworks are OOP, even marking guidelines deduct points for lack of OOP. Instead of all this brainwashing, teach a variety of techniques and allow students to work out what they like and don't like for themselves. Ultimately it's about picking the most appropriate techniques for getting the job done and getting home in time for tea - we can call it objective-oriented programming.

Now if you don't mind I've got to go and send a empty message to this bin, it's leaking its encapsulations all over the floor...