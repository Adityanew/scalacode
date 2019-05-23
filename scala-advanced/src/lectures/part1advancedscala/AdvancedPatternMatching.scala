package lectures.part1advancedscala

import lectures.part1advancedscala.AdvancedPatternMatching.MyList

object AdvancedPatternMatching extends App{


  class Person(val name: String, val age: Int)

  object Person {
    def unapply(arg: Person): Option[(String, Int)] = Some((arg.name, arg.age)) // when class is not a case class unapply
    //can be used to deconstruct class for pattern matching
    // the name of object and case is the same when pattern matching is done.

    def unapply(age: Int): Option[String] = Some(if (age < 21) "minor" else "major")
  }

  val bob = new Person("bob", 25)
  val greetings = bob match {
    case Person(n, a) => s" hi my name is $n and i'm $a years old"
  }
 val LegalStatus = bob.age match {
   case Person(status) => s" My legal status is $status" // the status here is the returned value of second uapply method
 }

  object even{
    def unapply(arg: Int): Boolean =
      arg % 2 == 0

  }

  object singleDigit {
    def unapply(arg: Int): Boolean =
      arg < 10


  }

  val n: Int = 34
  val mathProperty = 34 match {
    case singleDigit() => "single digit"
    case even() => "even"
    case _ => " no property"
  }


  //infix patterns

  case class  Or[A, B] (a: A, b:B)// infix pattern only works when we have 2 things .
  val either = Or(2, "two")
  val humanDescription = either match {
    case Or(number, string) => s" $number is written as $string"

  }

  abstract class MyList[+A] {
    def head: A = ???
    def tail: MyList[A] = ???
  }
case object Empty extends MyList[Nothing]
case class Cons[+A](override val head: A, override  val tail: MyList[A]) extends MyList[A]

  object MyList{
    def unapplySeq[A](list: MyList[A]): Option[Seq[A]] ={
      if(list== Empty) Some(Seq.empty)
      else
        unapplySeq(list.tail).map( list.head +: _)
    }
  }

  val myList: MyList[Int] = Cons(1,Cons(2,Cons(3,Empty)))
  val decomposed = myList match {
    case MyList(1 ,2, _*) =>  "starting with 1 and 2"// the compiler looks for the object with name in this case pattern and expects an unapplySeq there as we are
                                                      // using "_*" which a Seq priviledge
    case _ => "something else"
  }

  abstract class Wrapper[T] {
    def isEmpty: Boolean
    def get: T

  }

  object PersonWrapper{
    def unapply(person: Person): Wrapper[String] = new Wrapper[String] {
      override def isEmpty: Boolean = false

      override def get: String =  person.name
    }

    bob match {
      case PersonWrapper(n) => s"$n"
    }
  }
}
