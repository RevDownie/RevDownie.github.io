object Main
{
  type BattlersHealth = Map[Battler, Int]
  type TargetStrategy = (Battler, BattlersHealth) => Battler
  type HealthChange = (Battler, Int)
  type Mentality = (Iterable[ConditionalActionStrategy], ActionStrategy)
  type ActionStrategy = (Battler, Int, BattlersHealth) => Action
  type ConditionalActionStrategy = (Battler, Int, BattlersHealth) => Option[Action]

  case class Battler(name : String, damage : Int, heal : Int, mentality : Mentality)
  {
    def decideAction(battlers : BattlersHealth) : Action =
    {
      val health = battlers.getOrElse(this, 0)
      mentality._1.map(_(this, health, battlers)).filterNot(_ == None).head.getOrElse(mentality._2(this, health, battlers))
    }
  }

  object Mentality
  {
    val attackingShrewd = (Vector(AttackStrategy(TargettingStrategy.getUnhealthiest), HealStrategy(20, TargettingStrategy.getSelf)),
                          FallbackStrategy())

    val attackingAlpha = (Vector(AttackStrategy(TargettingStrategy.getStrongest), HealStrategy(10, TargettingStrategy.getSelf)),
                          FallbackStrategy())

    val defensive = (Vector(HealStrategy(80, TargettingStrategy.getSelf), AttackStrategy(TargettingStrategy.getUnhealthiest)),
                     FallbackStrategy())
  }

  object TargettingStrategy
  {
    def getSelf(us : Battler, battlers : BattlersHealth): Battler = us

    def getUnhealthiest(us : Battler, battlers : BattlersHealth): Battler = battlers.filterKeys(_ != us).minBy(_._2)._1

    def getHealthiest(us : Battler, battlers : BattlersHealth): Battler = battlers.filterKeys(_ != us).minBy(_._2)._1

    def getStrongest(us : Battler, battlers : BattlersHealth): Battler = battlers.filterKeys(_ != us).maxBy(_._1.damage)._1
  }

  case class HealStrategy(healthBeforeHeal : Int, targetStrategy : TargetStrategy) extends ConditionalActionStrategy
  {
    def apply(us : Battler, health : Int, battlers : BattlersHealth) : Option[Action] =
      if (health < healthBeforeHeal) Some(new Action(us, targetStrategy(us, battlers), "heals", Action.heal))
      else None
  }

  case class AttackStrategy(targetStrategy : TargetStrategy) extends ConditionalActionStrategy
  {
    def apply(us : Battler, health : Int, battlers : BattlersHealth) : Option[Action] =
      Some(new Action(us, targetStrategy(us, battlers), "attacks", Action.attack))
  }

  case class FallbackStrategy() extends ActionStrategy
  {
    def apply(us : Battler, health : Int, battlers : BattlersHealth) : Action =
      new Action(us, us, "heals", Action.heal)
  }

  class Action(val actor : Battler, val target : Battler, val desc : String, f : (Battler, Battler) => HealthChange)
  {
    val change : HealthChange = f(actor, target)
  }

  object Action
  {
    def buildActions(battlers : BattlersHealth) : Iterable[Action] =
      battlers.keys.map(_.decideAction(battlers))

    def performActions(actions : Iterable[Action], battlers : BattlersHealth) : BattlersHealth =
    {
      val aggregatedChanges = actions.map(_.change).groupBy(_._1).mapValues(_.map(_._2).sum).toList

      def loop(changes : Iterable[HealthChange], battlers : BattlersHealth) : BattlersHealth =
      {
        changes match
        {
          case h :: t =>
          {
            val health = battlers.getOrElse(h._1, 0)
            val updatedState = battlers.updated(h._1, health + h._2)
            loop(t, updatedState)
          }
          case Nil => battlers
        }
      }

      loop(aggregatedChanges, battlers).filter(_._2 > 0)
    }

    def heal(healer : Battler, target : Battler) : HealthChange = (target, healer.heal)

    def attack(attacker : Battler, target : Battler) : HealthChange = (target, -attacker.damage)

    def toString(action : Action) : String =
    {
      "%s %s %s".format(action.actor.name, action.desc, action.target.name)
    }
  }

  def simulate() : Unit =
  {
    val battlers = Map(
      Battler(name = "Wizard", damage = 20, heal = 10, Mentality.defensive) -> 60,
      Battler(name = "Bot", damage = 25, heal = 5, Mentality.attackingShrewd) -> 70,
      Battler(name = "Boxer", damage = 10, heal = 5, Mentality.attackingAlpha) -> 100
    )

    def battlersToString(battlers : BattlersHealth) : String =
      battlers.map(kv => "%s (health %d)".format(kv._1.name, kv._2)).mkString(", ")

    println("Battlers: " + battlersToString(battlers))
    println("Begin Fight")

    def loop(battlers : BattlersHealth) : BattlersHealth =
    {
      if(battlers.size > 1)
      {
        println("Begin Round")
        val actions = Action.buildActions(battlers)
        println(actions.map(Action.toString).mkString("\n"))
        val survivors = Action.performActions(actions, battlers)
        println("End Round")
        println("Survivors: " + battlersToString(survivors))
        loop(survivors)
      }
      else
      {
        battlers
      }
    }

    loop(battlers)

    println("Fight Over")
  }

  def main(args : Array[String]) : Unit =
  {
    simulate()
  }
}
