/*
package exercises

trait Myset[A] extends (A => Boolean) {
  def apply(elem: A): Boolean = contains(elem)
  def contains(elem: A): Boolean
  def +[A](elem: A): Myset[A]
  def ++[A](anotherSet: Myset[A]): Myset[A]
  def map[B](f: A => B): Myset[B]
  def flatMap[B](f: A => Myset[B]): Myset[B]
  def filter(predicate: A => Boolean): Myset[A]
  def foreach(f: A => Unit): Unit
  def isEmpty : Boolean
  def -(elem: A): Myset[A]
  def &(anotherSet: Myset[A]): Myset[A]
  def --(anotherSet: Myset[A]): Myset[A]
  def unary_! : Myset[A]
}

 class Empty[A] extends Myset[A] {
  override def contains(elem: A): Boolean = false

   override def --(anotherSet: Myset[A]): Myset[A] = this
   override def &(anotherSet: Myset[A]): Myset[A] = this
   override def -(elem: A): Myset[A] = this
  override def +[A](elem: A): Myset[A] = new Cons[A](elem, this)
  override def ++[A](anotherSet: Myset[A]):Myset[A] = anotherSet

  def map[B](transformer: A => B): Myset[B] = new Empty[B]
  def  flatMap[B](f:  A => _root_.exercises.Myset[B]): Myset[B] = new Empty[B]

  override def filter(predicate: A => Boolean): Myset[A] = this

  override def foreach(f: A => Unit): Unit = ()
  def isEmpty = true

   override def unary_! : Myset[A] = new PropertyBasedSet[A](_ => true)
}
class PropertyBasedSet[A](property: A => Boolean) extends Myset[A] {


  def contains(elem: A): Boolean = property(elem)
  // { x in A | property(x)} + element = {x in A | property(x)|| x == elem}
  def +[A](elem: A): Myset[A] = new PropertyBasedSet[A](x => property(x) || x == elem)
  def ++[A](anotherSet: Myset[A]): Myset[A] = new PropertyBasedSet[A](x => property(x) || anotherSet(x))
  def map[B](f: A => B): Myset[B] = politelyFail
  def flatMap[B](f: A => Myset[B]): Myset[B] = politelyFail
  def filter(predicate: A => Boolean): Myset[A] = new PropertyBasedSet[A](x => property(x) && predicate(x))
  def foreach(f: A => Unit): Unit = politelyFail
  def isEmpty : Boolean = false
  def -(elem: A): Myset[A] = filter(x => x != elem)
  def &(anotherSet: Myset[A]): Myset[A] = filter(anotherSet)
  def --(anotherSet: Myset[A]): Myset[A] = filter(!anotherSet)
  def unary_! : Myset[A] = new PropertyBasedSet[A](x => !property(x))

  def politelyFail = throw new IllegalArgumentException("very deep rabbit hole")
}

 class Cons[A]( head: A, tail: Myset[A]) extends Myset[A] {

  override def ++[A](anotherSet: Myset[A]): Myset[A] = tail ++ anotherSet + head

   override def --(anotherSet: Myset[A]): Myset[A] = filter( x => !anotherSet(x))// because of apply method defined
                                                                                 // we can define like this also
   override def &(anotherSet: Myset[A]): Myset[A] = filter(x => anotherSet(x)) // same as filter(anotherSet)
   override def -(elem: A): Myset[A] = {
   if(head == elem) tail
     else
     tail.-(elem) + head
   }
  override def isEmpty: Boolean = false
  override def +[A](elem: A): Myset[A] =
    if(this contains  elem) this
    else new Cons[A](elem, this)

  override def contains(elem: A): Boolean = {if (elem == head) true
  else if (this.tail.isEmpty) false
    else
    tail.contains(elem)
  }



  override def filter(predicate: A => Boolean): Myset[A] = {
    val filteredTail = tail filter predicate
    if(predicate(head)) filteredTail + head
    else
      filteredTail
  }
  override def flatMap[B](f: A => Myset[B]): Myset[B] =(tail flatMap f) ++ f(head)

  override def foreach(f : A => Unit): Unit = {
    f(head)
  tail.foreach(f)}

  override def map[B](f: A => B): Myset[B] = (tail map f) + f(head)

   override def unary_! : Myset[A] = new PropertyBasedSet[A](x => !this.contains(x))
}

object Myset {

  // val s = MySet(1,2,3) = buildSet(seq(1,2,3), [])
  def apply[A](values: A*): Myset[A] = {
    def buildSet(valSeq: Seq[A], acc: Myset[A]): Myset[A] ={
      if(valSeq.isEmpty) acc
      else buildSet(valSeq.tail, acc + valSeq.head)
    }

    buildSet(values.toSeq, new Empty[A])
  }
}


*/
