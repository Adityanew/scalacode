package sAdvancd.src.exercises

import scala.annotation.tailrec


abstract class MyStream[+A]
{
  def isEmpty: Boolean
  def head: A
  def tail: MyStream[A]
  def #:: [B >:A] (element: B): MyStream[B]
  def ++ [B >: A](anotherStream: => MyStream[B]): MyStream[B]// as another Stream is a parameter here .. it needs to be evaluated
  // lazily because as it will lead to the parameter in flatmap( tail.flatmap) to be evaluated first and thus causing runtime exception.


  def foreach(f: A => Unit): Unit
  def map[B](f : A => B): MyStream[B]
  def flatMap[B](f: A => MyStream[B]): MyStream[B]
  def filter(predicate: A => Boolean): MyStream[A]

  def take(n: Int): MyStream[A] // take the first n elements out of the stream

  @tailrec
  final  def toList[B >: A](acc: List[B] = Nil): List[B] =
    { println(" to list opp "+acc)
      if (isEmpty) acc.reverse
      else
        tail.toList(head :: acc)
    }

  def takeAsList(n: Int): List[A] = take(n).toList()

}



object Empty extends MyStream[Nothing] {


  def isEmpty: Boolean = true
  def head: Nothing = throw new NoSuchElementException
  def tail: MyStream[Nothing] = throw new NoSuchElementException
  def #:: [B >:Nothing] (element: B): MyStream[B] = new Cons(element, this)
  def ++ [B >: Nothing](anotherStream: => MyStream[B]): MyStream[B] = anotherStream


  def foreach(f: Nothing => Unit): Unit = ()
  def map[B](f : Nothing => B): MyStream[B] = this
  def flatMap[B](f: Nothing => MyStream[B]): MyStream[B] = this
  def filter(predicate: Nothing => Boolean): MyStream[Nothing] = this

  def take(n: Int): MyStream[Nothing] = this// take the first n elements out of the stream



}


class Cons[+A]( hd: A, tl: => MyStream[A]) extends MyStream[A] {

  def isEmpty: Boolean = false
  override val  head: A = hd
  override lazy val tail: MyStream[A] = tl
  def #:: [B >:A] (element: B): MyStream[B] = new Cons[B](element, this)
  def ++ [B >: A](anotherStream: => MyStream[B]): MyStream[B] = new Cons[B](head, tail ++ anotherStream) // here lazy evaluation is still
                                 //preserved as in each recursive call a new cos is created with tail as by name parameter and tail is
                                 // is overridden as a lazy val thus preserving lazy evaluation.


  def foreach(f: A => Unit): Unit = {
    f(head)
    tail.foreach(f)  // forcing the the tail to be evaluated as we are using the foreach on all the elements of the stream.
  }



  /*
  val s = Cons(1,?)
  mapped = s.map(_ + 1) = new Cons (2, s.tail.map(_ +1)
  the s.tail part will not be evaluated until and unless the mapped.tail is used in further expressions or instances.

  by name parameter and lazy evaluation go hand in hand to preserve the evaluation of tail .
   */
  def map[B](f : A => B): MyStream[B] = new Cons( f(head), tail.map(f))// lazy evaluation is preserved here as we are creating a new
                                                                      // Cons on each recursive call which will have the tail as a by name param and
                                                                      // and overridden as a lazy val. evaluation of tail will be by need.
  def flatMap[B](f: A => MyStream[B]): MyStream[B] = f(head) ++ tail.flatMap(f) // preserves lazy val evaluation
  def filter(predicate: A => Boolean): MyStream[A] = {
    println("filter doing "+head )
    if( predicate(head))  new Cons(head, tail.filter(predicate))
    else
      tail.filter(predicate)
  }

  def take(n: Int): MyStream[A] = {
    println(" n = "+n+" value taken "+head)
    if (n <= 0) Empty
    else if ( n ==1) new Cons(head, Empty)
    else

      new Cons(head, tail.take(n - 1))
  } // take the first n elements out of the stream


}

object MyStream {
  def from[A] (start: A)(generator: A => A) : MyStream[A] = new Cons(start,MyStream.from(generator(start))(generator))
}



object StreamsPlayground extends App{
val naturals = MyStream.from(1)(_ + 1)
  //println(naturals.head)
  //println(naturals.tail.head)
  //println(naturals.tail.tail.head)

  val startFrom0 = 0 #:: naturals // methods ending with colon are right associative

  //startFrom0.take(10000).foreach(println)
  //startFrom0.filter(_ < 5).foreach(println)
  println(startFrom0.filter(_ < 5).take(5).toList()) //.take(5).toList())



  def fibonacci(first: BigInt, second: BigInt): MyStream[BigInt] =
    new Cons(first, fibonacci(second, first + second))

  //println(fibonacci(1,1).take(100).toList())

  def eratosthenes(numbers: MyStream[Int]): MyStream[Int] =
    if (numbers.isEmpty) numbers
    else new Cons(numbers.head,eratosthenes(numbers.tail.filter( _ % numbers.head != 0)))
}
