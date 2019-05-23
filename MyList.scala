package exercises




 abstract class MyList[+A] {
  def head:A
  def tail:MyList[A]
  def isEmpty:Boolean
  def  add[B>:A](element:B):MyList[B]
  def printElements:String
  override def toString:String="["+ printElements+ "]"
  def map[B](transformer: (A) => B): MyList[B]
  def flatMap[B](transformer:(A) => MyList[B]):MyList[B]
  def ++[B>:A](list:MyList[B]):MyList[B]
  def filter(predicate: (A) => Boolean): MyList[A]
  
}
case object Empty  extends MyList[Nothing]{
  def head:Nothing= throw new NoSuchElementException
  def tail:MyList[Nothing]=throw new NoSuchElementException
  def isEmpty:Boolean=true
  def  add[B>:Nothing](element:B):MyList[B]=Cons(element,Empty)
  def printElements:String=""
  def map[B](transformer: Nothing => B):MyList[B]=Empty
  def flatMap[B](transformer: Nothing => MyList[B]):MyList[B]=Empty
  def filter(predicate: Nothing => Boolean):MyList[Nothing]=Empty
  def ++[B>:Nothing](list:MyList[B]):MyList[B]=list
}

case class Cons[+A](h:A,t:MyList[A]) extends MyList[A]{
    def head:A=h
    def tail:MyList[A]=t
    def isEmpty:Boolean=false
    def map[B](transformer: A => B):MyList[B]={
      
         Cons(transformer(h),t.map(transformer))  
      
    }
    
    def flatMap[B](transformer:A => MyList[B]):MyList[B]={
      transformer(h) ++ t.flatMap(transformer)
    }
    def ++[B>:A](list:MyList[B]):MyList[B]= Cons(h,t ++ list)
    def filter(predicate:A => Boolean):MyList[A]={
      if(predicate(h))  Cons(h,t.filter(predicate))
      else t.filter(predicate)
    }
    
    /*def filter[B>:A](x:B =>Boolean):MyList[B]={
     val newList:MyList[B]=Empty
     def filterHelper(newList:MyList[B]):MyList[B]={
       if(t.isEmpty) newList
       else
       {
         if(x(this.head)) filterHelper(newList.add(this.head))
         else
           filterHelper(newList)
       }
     }
     filterHelper(newList)
    }
    
    def map[B](x: A=>B):MyList[B]=
    {val newList:MyList[B]=Empty
      def mapHelper(newList:MyList[B]):MyList[B]={
      if(t.isEmpty) newList
      else
       mapHelper( newList.add(x(this.head)))
    }
      mapHelper(newList)
    }
    def flatMap(*/
    def add[B>:A](element:B):MyList[B]= Cons(element,this)
    def printElements:String={
      if( t.isEmpty) " "+h
      else
        h+" "+t.printElements
    }
}

  


/*trait MyPredicate[-T] {
  def test(v:T):Boolean
}

trait Transformer[-A,B] {
  def transform(v:A):B
}*/

/*class EvenPredicate extends MyPredicate[Int]
{
  override def test(v:Int):Boolean = v % 2 == 0
}

class StringToIntTransform extends Transformer[String,Int]
{
  def transform(v:String):Int=v.toInt
}*/
object ListTest extends App{
  

  
  val list= Cons(1,Cons(2, Cons(3,Cons(4,Empty))))
  //val ep=new EvenPredicate
  //val l=list.filter(ep.test)
  //println(l)
 
  println( 3 % 2 ==0)
  val newList=list.add(4)
  println(list.add(4).tail)
  println(newList.tail)
}