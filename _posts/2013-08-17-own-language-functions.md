---
layout: post
title: Creating my Own Language - Functions
---

In my spare time I have been working on creating a new lightweight, general purpose programming/scripting language. I have no real intention of doing anything with the language beyond experimenting but I find that trying to create something can often help you understand why things are the way they are. I really wanted to create a syntax that embraced modern programming features (such as type inference, lambdas and concurrency) as part of the language's foundation and not as boilerplate additions.

Originally the syntax for my functions looked a lot like a hybrid between Python and Actionscript (The language is optionally type inferred so the explicit types are purely there to fully illustrate the syntax formatting):

```
func Add(const x : s32, const y : s32) : s32
	const result : s32 = x + y
	return result
```

I was pretty happy with the syntax as I like the enforced coding style of Python with the syntax of Actionscript and I felt there was consistency between the variable/const declarations and the function declaration. However, once I started thinking about lambda functions (i.e. anonymous functions) I felt that the consistency between functions and variables started to break down. In most languages (C++ and ObjC in particular) the syntax for lambda functions is horrendous and I wanted to avoid the discord between function assignment and variable assignment. So I started to think of functions more like variables, constants or expressions (which are frequently passed anonymously). That train of though lead me to this syntax:

```
Add : func = (const x : s32, const y : s32 -> s32)
	const result : s32 = x + y
	return result
```

The above syntax is very similar to creating a variable or constant and it should be very clear how to use anonymous functions or how to assign functions:

```
Add : func = (const x : s32, const y : s32 -> s32)
	const result : s32 = x + y
	return result

SetDelegate(Add)
```

or

```
SetDelegate((const x : s32, const y : s32 -> s32)
	const result : s32 = x + y
	return result)
```

By encapsulating the entire function signature inside the parenthesis it is now very easy to add support for multiple return types:

```
GetAuthDetails : func = (-> string, string)
	return "username", "password"

const username, const password = GetAuthDetails()
```

My next big task is to address concurrency and multithreading.