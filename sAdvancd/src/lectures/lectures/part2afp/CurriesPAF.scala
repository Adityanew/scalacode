package sAdvancd.src.lectures.lectures.part2afp



object CurriesPAF extends App{

  //curried functions
  val superAdder: Int => Int => Int = x => y => x + y

  val add = superAdder(3)
  println(add(4))
  println(superAdder(2)(4))

  def curriedAdder(x: Int)(y: Int) :Int = x+y// curried function

  val add4: Int => Int = curriedAdder(4)  // the type needs to mentioned when expecting a function from a curried method. if not there
                                          // there would be a run time error


  val simpleAddFunction = (x: Int, y:Int) => x + y
  def simpleAddMethod(x: Int, y:Int) = x + y
  def curriedAddMethod(x: Int)(y: Int) = x + y


  val add7 = (x:Int) => simpleAddFunction(x, 7)// simplest solution

  val add7_2 = simpleAddFunction.curried(7)

  val add7_3 = curriedAddMethod(7) _ //PAF.  Lifting at work here.. converting the method into a function


  //underscores are powerful
  def concatenator(a: String, b: String, c: String) = a + b + c
  val insertName = concatenator("hello. I'm ",  _: String, "! how are you?")// the underscore tells the compiler to change this into a function value
  //from a method
  println(insertName("aditya"))
  val fillInTheBlanks = concatenator("hello, ", _: String, _: String) // expanded to (x,y) => concatenator("hello ",x ,y)



  def curriedFormatter(s : String)(number: Double): String = s.format(number)
  val nmbers = List(Math.PI, Math.E, 1, 9.8, 1.3e-12)

  val simpleFormat = curriedFormatter("%4.2f") _ //lift  ..ETA expansion
  val seriousFormat = curriedFormatter("%8.6f") _
  val preciseFormatter = curriedFormatter("%14.12f") _

  println(nmbers.map(seriousFormat))

  //compiler does not do eta exp for accessor methods but rather for complete methods so if we send an accessor method to a function accepting
  // function values it will show an error.
  //methods can't be passed to higher order functions as it a limitation of jvm as methods are a part of classes or objects so they need
  //to converted to a function value which done by the compiler and this is known as lifting or ETA expansion.

}
