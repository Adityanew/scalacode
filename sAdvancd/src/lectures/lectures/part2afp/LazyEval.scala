package sAdvancd.src.lectures.lectures.part2afp

object LazyEval extends App{

  lazy val x: Int = throw new RuntimeException // lazy delays the evaluation of the value.  thus no error will thrown untill it is used for the first time

   lazy val y : Int ={
    println("hello")
    42
  }

  println(y) // since print(hello) is part of the evaluation, what gets printed to teh console is the hello and then 42 here as y is being
  //for the first time here
  println(y)// as y has already been evaluated what get printed to the console here is 42.  TODO not completely sure though. find out

  def sideEffectCondition: Boolean ={
    println("hi")
    true
  }

  def simpleCondition: Boolean = false

  lazy val lazyCondition = sideEffectCondition
  println(if( simpleCondition && lazyCondition) "yes" else "no") //here the compiler is smart enough to know that as simpleCondition is
  //false so the whole expression is false and thus lazyCondition is not evaluated at all.


  def byName(n : => Int): Int = n +n +n +1 // n is evaluated as many times as it is called as it is defined as by name param

  def retrieveMagicNumber= {
    println("waiting")
    Thread.sleep(1000)
    42
  }

  println(byName(retrieveMagicNumber))// waiting gets printed 3 times as n is evaluated 3 times
  // using lazy vals can prevent this
  def byNameLazyVal(n: => Int): Int = {
    lazy val t = n
    t + t + t + 1
  }

  println(byNameLazyVal(retrieveMagicNumber))// waiting only gets printed 1 time as when the first time it gets evaluated it is in lazy val and then not
  // reevaluated . Also known CALL BY NEED


  def lessThan30(i : Int): Boolean =
  {
    println(s" $i less than 30?")
    i < 30
  }

  def greaterThan20(i : Int): Boolean =
  {
    println(s" $i greater than 20?")
    i > 30
  }
  val list = List(1,25,40,5,3)
  val lt30 = list.filter(lessThan30)
  val gt20 = lt30.filter(greaterThan20)
  println(gt20)

  val lazylt30 = list.withFilter(lessThan30)
  val lazygt20 = lazylt30.withFilter(greaterThan20)
  println(lazygt20) // prints an object reference to a monadic filter for lazygt20 as lazy vals are being used under the
                    // hood thus until and unless the values are forced to be evaluated the side effects are not printed to the
                    // console.
  lazygt20.foreach(println) // this forces the evaluation of each value and thus filtering happens in an order different from normal filtering.
                            // .i.e the al the expressions are evaluated for each number one after the other.



  // STREAM : a collection in which the head is always available and the tail is lazily evaluated on demand.



}
