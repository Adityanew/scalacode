package lectures.part1advancedscala

object DarkSugars extends App{
  //syntactic sugar #1
  def sinlgeArgMethod(arg: Int): String = s"$arg little ducks"

  val description = sinlgeArgMethod{
    //some complex code
    42
  }
  //syntactic sugar #2 : Single abstract method
  trait action{
    def act(x: Int): Int
  }
val functype: action = (x:Int) => x + 1
  val aThread = new Thread(new Runnable {
    override def run(): Unit = println("hello, scala")
  })

  val aSweeterThread = new Thread( () => println("sweeter scala"))


  abstract class AbstarctType{
    def implemented: Int = 23
    def f(a: Int): Unit
  }

  val anAbstractInstance: AbstarctType = (a: Int) => println(a)

  //syntax sugar #3: the :: and #:: methods are special

  val prependedList = 2 :: List(3, 4)
  //2.::(List(3,4) -> does not happen
  // compiler writes it as List(3,4).::(2)
  // scala spec: last character decides associativity of a method
  // methods ending in ":" are right associative otherwise in all other cases it is left associative

  class MyStream[T] {
    def -->:(value: T):MyStream[T] = this //actual implementation


  }
  val myStream = 1 -->: 2 -->: 3 -->: new MyStream[Int]
//syntax sugar #4 : multi word method
  class TeenGirl(name: String) {
    def `and then said`(gossip: String): String = s"$name said $gossip"
  }
  val lilly = new TeenGirl("Lilly")
  lilly `and then said` "Scala is so sweet"
// syntax sugar #5: infix types
  class -->[A, B]
  val towards: Int --> String = ???

// syntax sugar #6: update() is very special, much like apply()
  val anArray = Array(1,2,3)
  anArray(2) = 7 //compiler rewrites it as anArray.update(2,7)
  // used in mutable collections

  //syntax sugar #7 : setters for mutable containers
  class Mutable{
    private var internalMember = 0
    def member: Int = internalMember
    def member_=(value: Int): Unit =
      internalMember = value
  }
  val aMutableContainer = new Mutable
  aMutableContainer.member = 42 // compiler rewrites as aMutableContainer.member_=(42) . this happens when we have a getter
                               // and setter called member
}


