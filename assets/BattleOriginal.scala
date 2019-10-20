object Main
{
  case class Battler(name : String, health : Int, damage : Int, mentality : Mentality)
  {
    def decideAction(battlers : Seq[Battler]) : Action = mentality.decideAction(this, battlers)
  }

  abstract class Mentality(healthBeforeHeal : Int, attackStrategy : (Battler, Seq[Battler]) => Battler)
  {
    def decideAction(us : Battler, battlers : Seq[Battler]) : Action =
    {
      (us, battlers) match
      {
        case (Battler(_, ourHealth, _, _), _) if ourHealth < healthBeforeHeal => HealAction(us.name)
        case (_, Nil) => HealAction(us.name)
        case (_, xs) => AttackAction(us.name, attackStrategy(us, xs).name)
      }
    }
  }

  object Mentality
  {
    def getUnhealthiestNotUs(us : Battler, battlers : Seq[Battler]) : Battler =
      battlers.filterNot(_.name == us.name).min(Ordering.by((x : Battler) => x.health))

    def getHealthiestNotUs(us : Battler, battlers : Seq[Battler]) : Battler =
      battlers.filterNot(_.name == us.name).max(Ordering.by((x : Battler) => x.health))

    def getStrongestNotUs(us : Battler, battlers : Seq[Battler]) : Battler =
      battlers.filterNot(_.name == us.name).max(Ordering.by((x : Battler) => x.damage))
  }

  case class AttackingMentality extends Mentality(10, Mentality.getStrongestNotUs)
  case class DefendingMentality extends Mentality(50, Mentality.getUnhealthiestNotUs)

  sealed trait Action
  {
    def apply(battlers : Seq[Battler]) : Seq[Battler]
  }

  object Action
  {
    def perform(battlers : Seq[Battler], actions : Seq[Action]) : Seq[Battler] =
    {
      actions match
      {
        case h :: t => perform(h(battlers), t)
        case Nil => battlers
      }
    }
  }

  case class AttackAction(attackName : String, defendName : String) extends Action
  {
    def apply(battlers : Seq[Battler]) : Seq[Battler] =
    {
      battlers.find(_.name == attackName) match
      {
        case Some(a) => battlers.map(d => if (d.name == defendName) attack(a, d) else d)
        case _ => battlers
      }
    }

    override def toString : String = attackName + " attacks " + defendName

    private def attack(attacker : Battler, defender : Battler) : Battler =
      Battler(defender.name, defender.health - attacker.damage, defender.damage, defender.mentality)
  }

  case class HealAction(name : String) extends Action
  {
    def apply(battlers : Seq[Battler]) : Seq[Battler] = battlers.map(b => if (b.name == name) heal(b) else b)

    override def toString : String = name + " heals themself"

    private def heal(b : Battler) : Battler = Battler(b.name, b.health + 10, b.damage, b.mentality)
  }

  type Round = (Seq[Battler], Int) => (Seq[Action], Int)

  def nextRound : Round =
  {
    (battlers, roundNum) =>
    {
      val actions = for(battler <- battlers) yield(battler.decideAction(battlers))
      (actions, roundNum + 1)
    }
  }

  def fight(battlers: Seq[Battler]) : Unit =
  {
    println("Begin Fight")
    println("Fighters: " + battlers.map(x => x.name + " Health: " + x.health.toString).mkString(", "))

    def loop(battlers: Seq[Battler], roundNum : Int) : Seq[Battler] =
    {
      battlers match
      {
        case Nil => battlers
        case h :: Nil => battlers
        case _ =>
        {
          val (actions, newRoundNum) = nextRound(battlers, roundNum)

          println("Begin Round: " + newRoundNum)
          println(actions.mkString("\n"))
          println("End Round")

          val stillAlive = Action.perform(battlers, actions).filter(_.health > 0)
          println("Still Alive: " + stillAlive.map(x => x.name + " Health: " + x.health.toString).mkString(", "))

          loop(stillAlive, newRoundNum)
        }
      }
    }

    val winners = loop(battlers, 0)

    println("End Fight")
    println("Winner: " + winners.map(x => x.name + " Health: " + x.health.toString).mkString(", "))
  }

  def main(args : Array[String]) : Unit =
  {
    val battlers = List(
      Battler(name = "Wizard", health = 60, damage = 60, mentality = DefendingMentality()),
      Battler(name = "Bot", health = 90, damage = 20, mentality = AttackingMentality()),
      Battler(name = "Boxer", health = 120, damage = 9, mentality = AttackingMentality())
    )

    fight(battlers)
  }
}
