---
layout: post
title: Playdate Zig - Part 2 Build to device from Windows
---

So my Playdate arrived and I thought I'd switch context from developing my little game (which I'll cover in another post) to actually getting it deployed on device.

The following linked templates did all the heavy lifting for me and I can't thank them enough. But as with the simulator, there were a few stumbling blocks that caught me out and I thought were worth sharing.

* [Me The Flea - Zig Playdate Template](https://github.com/MeTheFlea/zig-playdate)
* [MitchellH - Zig Playdate Template](https://gist.github.com/mitchellh/0d25e47ed2c4731f076b637904410b87)

Worth noting that I am not an expert in anyway at build systems, I copy pasted and hacked away until I had something working. I don't fully understand the Zig build system nor the artefacts required by the Playdate! 

[Commit of a functioning build.zig](https://github.com/RevDownie/playdate-next/commit/9b8ac0f33f5818b41d87d76f6876ee214717ec25)

## Zig version
So this really caught me out. Originally I was working using Zig 0.9.0 but the references seemed to be using 0.10.0 (beta versions mostly). I upgraded, which involved some minor changes to the existing code. However, when I implemented build.zig for the device I was getting an unhelpful error on @cImport for pd_api.h. The error gave absolutely no information in Powershell (which was baffling as I could see online other people were getting helpful errors). Eventually, out of pure desperation, I rolled back to Zig 0.9.0 and on building I got actual errors relating to how I had installed the ARM GNU Toolchain (I had used the zip rather than the exe and hadn't run the bat file to complete the setup that puts the includes into the correct locations).

Once I fixed the ARM GNU Toolchain issue I managed to get passed the cImport error but then encountered another error to do with linking and lld memory sections. Couldn't figure this one out either - but again out of desperation I upgraded back to Zig 0.10.0 and those errors went away! Phew!

## Linking Lib C
In my desperation to get the ELF compiling I had added in a call to linkLibC - don't do this for device builds (and neither of the templates do). It causes linker errors. Removing this allowed me to progress (but keep it for the simulator build steps).

## Linking library into ELF
As I say I'm definetely not an expert on these things but on my local machine the library compilation step was generating a file "libname.so.o" but the template code seemed to be expecting "name.o" - so I made the following change to the ELF build step:
```
const game_elf = b.addExecutable("pdex.elf", null);
game_elf.addObjectFile(b.pathJoin(&.{ lib.output_dir.?, b.fmt("lib{s}.so.o", .{lib.name}) }));
```

Also added the following to the library build step. The template missed these out and I was getting errors but noticed they were then being coded into the @cImport in the template - so I added them into build.zig instead as I did with the simulator build, like this
```
lib.defineCMacro("TARGET_SIMULATOR", null);
lib.defineCMacro("TARGET_EXTENSION", null);
```

## Running but not displaying
One of the weirder, and more frustrating issues to solve, was once I had it up an running on the device. I was being presented with a blank screen but weirdly if I opened the menu, a frame of my game was rendered. I could close the menu, apply some input and open the menu again and see the frame had updated. Couldn't find anything online about this but after some trial and error I noticed that if I called graphics.display() directly from my update loop then the game rendered correctly - but no-one else was calling this function explicitly.

So turns out that on simulator you can return any return value you like from your update loop but on device you have to return TRUE (1) to inform Playdate that you need it to update the display. The template was doing this but I hadn't bothered looking at the game code because I already had a working version on simulator that returned 0. Just needed to change it to return 1.


## Linker warning
I still get the following linker warning but something to worry about for another day.
```
LLD Link... warning(link): unexpected LLD stderr:
ld.lld: warning: cannot find entry symbol _start; not setting start address
```

## Next Steps

* I'll need to cleanup Build.zig. Currently it builds a "fat" .pdx that runs on simulator and on device and I'll want to be able to skip either depending on what I'm testing. 
* The main reason I wanted to deploy to device was to profile sprites vs bitmap drawing to inform the next steps of my game. So likely will post about the results