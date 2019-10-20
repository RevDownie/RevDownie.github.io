---
layout: post
title: Lifting the Limitations with Open-Source
---

*Originally posted on [Gamasutra](http://gamasutra.com/blogs/ScottDownie/20150217/236551/Lifting_the_limitations_with_opensource.php)*


Last month, Tag Games launched our game development engine, at Pocket Gamer Connects in London. The engine is called ChilliSource and is available under the MIT license as a free, open-source solution for creating games.

At Tag, we have long understood the benefits of using and contributing to open-source software, but I was surprised that many developers I speak to see open-source solutions as a risky choice, or know little about the advantages of using open-source software. Having been involved in the ChilliSource venture from day one, it feels like a good opportunity to share some of the reasons Tag use open-source solutions (focusing on game engines) and ultimately what lead us to open up our internal technology in this manner.

## Background: The trouble with closed-source…
To date, ChilliSource has been used by Tag Games to create eleven titles on a range of platforms. However, prior to ChilliSource, we used a popular closed-source engine to develop most of our games. We didn’t really have any major issues with the engine itself, but its closed-source nature provided some concerns.

So what do I mean by ‘closed-source’ and ‘open-source’? Really the difference between closed and open source is in the licensing approach; and there are lots of different licenses! For example, it’s possible to have software where you can see the code but not edit it, or edit it but not re-distribute it, or do what ever you like with it. In the context of this blog I’ll make the distinction that you can view, edit and update the code of an open-source project (i.e. MIT license) but cannot do the same on a closed-source one.

With closed-source software you are basically at the mercy of the software vendor. Often this is fine because it is in the vendor’s interests to keep the customer happy with new features and bug fixes; but the problem arises around the issues of time and priority. The features and fixes that the vendor will focus on are those requested by the majority of the community (that’s where the money is right?); this could mean the bug fix you require is at the bottom of the pile. This might be, at best, a minor inconvenience or…it could be you have a release deadline in 2 days, a whole bunch of advertising booked and absolutely no way of fixing your bug in order to meet that deadline. Worst of all the third party company could have their software acquired, potentially stopping support or limiting deployment platforms for your future games.

We’ve had a few issues like this at Tag, where the support team of a third party technology couldn’t possibly work to the timescale we required, and particularly in the case of an engine that’s enabling all of your games  (and possibly all of your company’s revenue), this can be a scary prospect. This became such a concern for the company that the team were tasked with finding an engine that would allow us to address these issues ourselves. Unfortunately at that time smartphone gaming was still in its infancy and there were no open-source solutions available that met the requirement of iOS and Android support, plus 2D and 3D rendering. As a result ChilliSource was born.

So what are the main benefits of open source?

## Reason one: Taking control…
Control is the main reason that Tag moved away from a closed-source engine and I believe, above all else, that control is the main benefit of open-source software.

Handing control to your programming team means that you are no longer dependent on third parties for new features and bug fixes. If iOS has a new killer framework your team want to take advantage of to wangle a front-page feature on the App Store, then you can. If a bug is found in your live app and it needs to be fixed and updated ASAP, then there is no need to waste time to-ing and fro-ing with remote customer support. Open-source enables you to work to your own timelines and your own priorities

Control also has more far reaching implications than just bug fixes, it can allow you to add new platforms and open up untapped markets. Niche markets can potentially be quite lucrative for small, indie studios but are maybe not recognised by large vendors. Ultimately you get a say in the direction of the engine that you use to make all your games – that’s got to be a good thing.

ChilliSource is written in C++ and one of the main benefits of using a global language, in combination with open-source, is the wealth of available libraries and modules that can be plugged-in. You don’t have to be tied to a particular physics engine or networking library, for example; you can use tools that you are already familiar and comfortable with.

With open-source solutions no one can seize this control away from you. The source does not belong to an individual or company, it cannot be acquired; it belongs to you and the community.

## Reason two: Seeing is believing…
Being able to edit and update the source is really powerful but just being able to *see* it also has huge benefits.

A common misconception is that open-source software is of poor quality. I’m sure there are many poor quality open-source projects out there but I’m equally sure many poor quality closed-source projects also exist. At least with open-source software you can audit it, perform your own code reviews and judge the quality for yourself.  Just because a project is open-source does not mean that any old code can be contributed. Most open-source projects have strict guidelines and review processes that must be met before code can make its way into the main branch (however you can always fork the source and manage the code using your own guidelines and process if you wish).

Having the ability to examine the code means that, for example, if you want to know what type of encryption is used in the data store or what networking protocols are used in the multiplayer system, then you are not dependent on the documentation (which can often be non-existent or worse out of date); just open up the source file and see for yourself.

## Reason three: Did I mention it was free?
Open-source software is free. I could have lead with this reason and that might have been enough to convince many of the merits of open-source (sometimes there are costs for services such as support but the software is free to use). One of the biggest expenses at Tag is kitting out new employees with expensively licensed software; this can be a huge barrier for smaller, indie companies often preventing them from growing and expanding. Switching to suitable free, open-source alternatives can save money and be hugely rewarding if you’re one of the passionate and dedicated developers contributing their expertise and time to free, open-source projects.

## Reason four: Sharing and improving…
One of the main reasons for releasing ChilliSource to the wider industry is that we wanted to build a community. Opening your technology to the community allows you to engage with developers with different experiences, specialisms, needs and ideas and can drive your project forward in ways that you would never have thought of had you kept it in-house. It also gives you the opportunity to hire people who are already familiar with your tools and technology, rather than spending time and money training them.

Knowledge sharing is invaluable – whether you are a student looking to get your hands on source code for learning, a developer looking to make use of a product that takes advantage of an experienced and active community or a contributor looking to give back to the community and get feedback on their code. This is the power of an open-source community where knowledge, features and bug fixes are shared and everyone benefits.

## Conclusion: Final thoughts
The games industry is hugely diverse with many talented and dedicated individuals, but it is also constantly evolving and changing. It is important for a company like Tag Games to stay agile and be able to adapt to changes. This need led us to take control of our own middleware by creating ChilliSource, but in order to tap in to the experience and diversity of the game development community we needed to open up this technology and share it with the community. We now have the ability to listen and act upon the ideas and work created by other developers using ChilliSource which will prove beneficial to us all.

If, like us, you want to have a say in the tools that you use to make your products (and ultimately your revenue) and you want to benefit from the experience, knowledge and dedication of a community; then I would really recommend investigating open-source as a viable alternative to licensed products.

Scott Downie, Lead Technology Programmer, Tag Games