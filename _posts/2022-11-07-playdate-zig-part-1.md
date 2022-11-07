---
layout: post
title: Playdate Zig - Part 1: Window's Simulator
---

As I'm still waiting on the arrival of my actual Playdate I figured I'd get up and running on the Window's simulator. I wanted to use Zig as I haven't had a chance to do any game dev with it and it has great interoperability with C - so figured it would be ideal for Playdate.

There are a couple of guides online for getting setup but they glossed over some things that gave me a little trouble (or only worked on Mac) so thought I'd post about them.

Here is a link to the commit that has the most basic working code for building and running on the simulator - just shows a blank screen.
[Basic Zig Windows Simulator](https://github.com/RevDownie/playdate-next/commit/f4744f094cb327dd8a3d28599e9fb3b1dd425b61)


The main steps (and gotchas) I encountered are as follows:

## Create a DLL
Essentially you just create a DLL with event handler exported

```
const simulator = b.addSharedLibrary("pdex", "src/main.zig", .unversioned);
```

## Include the C libraries
```
///Path to Playdate SDK C_API folder
simulator.addIncludeDir(c_sdk_path);
```

```
simulator.linkLibC();
```

## Add the neccessary defines
```
simulator.defineCMacro("TARGET_SIMULATOR", null);
simulator.defineCMacro("TARGET_EXTENSION", null);
simulator.defineCMacro("_WINDLL", null);
```

## Barebones main.zig
You need to expose both the eventHandler and also regiater the update callback. If you don't register the update callback then the simulator will fail to load with no error! Also ensure you 'export' the eventHandler and use the C call convention. This allows the simulator to call the function from the DLL

I decided to use Zig's cImport functionality, which translates the c file as it imports, providing access to the Playdate C API in Zig.

```
const pd = @cImport({
    @cInclude("pd_api.h");
});

pub export fn eventHandler(playdate: [*c]pd.PlaydateAPI, event: pd.PDSystemEvent, _: c_ulong) callconv(.C) c_int {
    switch (event) {
        pd.kEventInit => playdate.*.system.*.setUpdateCallback.?(update, null),
        else => {},
    }
    return 0;
}

fn update(_: ?*anyopaque) callconv(.C) c_int {
    return 0;
}
```

## Building a .pdx
This part turned out to be very straightforward in the end but was pretty confusing. I saw a lot of mixed posts talking about shared libraries and bin files, etc. The .pdx package requires a bin but I couldn't figure out how to build it. Turns out it actually only uses the dll but requires that a bin file be present. So you can literally just create an empty 'pdex.bin' file in the same folder as the 'pdex.dll' and pass this folder to pdc.

This is the Powershell command I then use to build the .pdx package that I then dragged and dropped onto the simulator. Note I set a different output path (/Source) from the default in Zig build ```simulator.setOutputDir(output_path);``` and you need pdc in your path

```
zig build && echo $null >> .\zig-out\Source\pdex.bin && pdc -v -k .\zig-out\Source Test.pdx
```

## References
* [Official Playdate C Guide](https://sdk.play.date/1.12.3/Inside%20Playdate%20with%20C.html#_about_the_c_api)
* [DanB91's GitHub starting point](https://gist.github.com/DanB91/4236e82025bb21f2a0d7d72482e391d8#file-main-zig)
* [Me the Flea's GitHub starting template](https://github.com/MeTheFlea/zig-playdate)