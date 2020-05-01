---
layout: post
title: Keep world abstractions out of the type system
---

Something that I see farily frequently, particularly in OOP codebases, is programmers bringing real-world or design-world abstractions into the type system. Type systems in statically typed languages are useful but very inflexible and introducing the wrong types can make the type-system work against you. I find that often people work around this with interfaces or inheritance but I believe these are sticking plasters that introduce additional complexity at the cost of comprehension (and flexibility) and that the problem is best solved at the root.

## Example

Here is a paraphrased example that I came across recently:

The game had a central character that the player could upgrade by unlocking new items (upgrades) and applying them.

```
class Upgrade
{
	int GetHealthUpgradeAmount()
	int GetAttackUpgradeAmount()
	int GetDefenceUpgradeAmount()
}

class CharacterStats
{
	void ApplyUpgrade(Upgrade upgrade)

	Upgrades[] m_upgrades

	int m_baseHealth
	int m_baseAttack
	int m_baseDefence
}

```

There was also a feature that triggered events that had time limited impacts on the character stats - the designers referred to these as Mod Events.

```
class ModEvent : Upgrade
{
	float GetDurationSecs()
}

class CharacterStats
{
	void ApplyUpgrade(Upgrade upgrade)
	void ApplyModEvent(ModEvent modEvent)

	void RemoveExpiredModEvents()

	Upgrade[] m_upgrades
	ModEvent[] m_modEvents

	int m_baseHealth
	int m_baseAttack
	int m_baseDefence
}

```

Finally there was a 'trait' feature where the character stats were increased or decreased based on who they were pitted against.

```
class Trait : Upgrade
{
	//Had stuff specific to traits like names but they aren't important for this example
}

class CharacterStats
{
	void ApplyUpgrade(Upgrade upgrade)
	void ApplyModEvent(ModEvent modEvent)
	void ApplyTrait(Trait trait)

	void RemoveExpiredModEvents()

	Upgrade[] m_upgrades //Including traits
	ModEvent[] m_modEvents

	int m_baseHealth
	int m_baseAttack
	int m_baseDefence
}

```

The approach above perhaps doesn't seem too bad, and in this case the actual impact on the codebase was fairly minimal (I'll cover it in a second), but I think when we reach for inheritance (and I don't want to get into an explanation here of why inheritance is a bit of a code smell) it is usually because we've made sub-optimal decisions earlier on.

## Splitting along data usage boundaries

I think root of the problem is conflating actual differences in usage with conceptual differences influenced by the world (in this case the game world). ***Where*** something comes from doesn't necessarily fundamentally change ***what*** it is. If we look at ```Upgrades```, ```ModEvents``` and ```Traits``` the only actual difference between them (besides where they are applied) is that one of them is time-limited. You could argue that the time-limitation isn't actually a property of the type but of the context and if you follow that logic and think about what the data is, where it is stored, read and written (i.e. how it is actually used) and split along those lines (and in keeping with the original structure) you would probably end up with something like this:

```
struct StatMod
{
	int m_id
	int m_healthMod
	int m_attackMod
	int m_defenceMod
}

struct TimeRemaining
{
	int m_id
	float m_timeRemaining
}

class CharacterStats
{
	void ApplyStatMod(StatMod statMod)
	void ApplyTimeLimitedStatMod(StatMod statMod, float durationSecs)

	void RemoveExpiredStatMods()

	StatMod[] m_statMods
	TimeRemaining[] m_statModTimers

	int m_baseHealth
	int m_baseAttack
	int m_baseDefence
}

```

Having a single type now gives you the flexibility to group or categorise to best meet the usage and not by some arbitrary conceptual difference. For example if you need to display the total affect of all mods to the player (which is exactly what the game needed to do: e.g. ```"Attack 7 (+7)"```) you can store all the ```StatMods``` in one array and sum them. If instead the design changed so the complete breakdown is displayed to the player (e.g. ```"Attack 7 (Upgrade +6) (Trait -2) (Power-Up +3)"```) you could go back to storing each conceptual type in a different container. Essentially the name of the variable can be used to represent the context.

By splitting the data along usage boundaries it is trivial to evlove the codebase as the design evolves. We can easily make certain upgrades time-limited or we can support the ability for upgrades to have names (just by associating them with an id) without having fundamentally change how mods are applied.

## Think about what types are being introduced

If you think about the most successful types they aren't influenced by their context. An integer doesn't have to change type based on whether it represents an age or a number of sandwiches (```int m_age, int m_numSandwiches```). What's the difference between a stool and table - functionally nothing, they are both raised surfaces and making a distiniction between them makes it difficult to treat them the same whenit is best to do so (what if I want to sit on the table). Introducing new types into the program should be thought out carefully and always along the lines of how the data is used and not based on real world naming which, if English is anything to go by, is messy, inconsistent and often not helpful. 

