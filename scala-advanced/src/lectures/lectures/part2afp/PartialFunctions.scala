package lectures.lectures.part2afp

object PartialFunctions extends App{

  val aFunction = (x:Int) => x+1 // Function1[Int, Int]

  val aFussyFunction = (x: Int) =>
    {
      if (x==1) 42
      else if (x==2) 56
      else if (x==5) 78
      else throw new FunctionNotApplicableException


    }
class FunctionNotApplicableException extends RuntimeException
  val aNicerFussyFunction = (x:Int) => x match {
    case 1 => 23
    case 2 => 45
    case 5 => 67
  }
 val aPartialFunction: PartialFunction[Int, Int] = {
   case 1 => 34
   case 3 => 45
   case 5 => 56
 }// partial function values
  println(aPartialFunction.isDefinedAt(45)) // returns false
  val lifted = aPartialFunction.lift // converts this into total function Int => Option[Int]
  println(lifted(2))// returns Some(2)
  println(lifted(98))// returns None

  val pfChain = aPartialFunction.orElse[Int, Int] {
    case 45 => 67
  }

  println(pfChain(2))// goes to first partial function which contains case for 2
  println(pfChain(45)) // goes to orElse partial function which contains case 45 definition

  // pf extend normal functions
  val aTotalFunction: Int => Int = {
    case 1 => 99
  }

// HOFs accept partial functions  as well
  val aMappedList = List(1, 2, 3).map {
    case 1 => 34
    case 2 => 35
    case 3 => 36
  }//partial function sent to map. It will fail for values other than defined in the cases

  /*
  Pfs can only have 1 parameter
   */
  //constructing pf instance
  val aManualFussyFunction = new PartialFunction[Int, Int] {
    override def apply(v1: Int): Int = v1 match {
      case 1 => 42
      case 3 => 65
      case 2 => 999
    }

    override def isDefinedAt(x: Int): Boolean =
      x==1 || x==2 || x==3
  }// creating your own partial function requires us to override these methods

  val chatBot: PartialFunction[String, String] = {
    case "hello" => "Hi, my name is HAl9000"
    case "good bye" => "once you say hi, there is no return"
    case "call mom" => "needvyour credit card info to process this message"

  }
scala.io.Source.stdin.getLines().foreach(line => println("chat bot says: "+ chatBot(line)))
}
