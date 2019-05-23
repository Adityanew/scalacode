package scalaoops.FIleSystem
 import Files.Directory

class State(val root:Directory, val wd:Directory,val output:String) {
   def show:Unit={
     println(output)
     print(State.SHELL_TOKEN)
   }
   def setMessage(message:String):State= //here we are maintaining immutability by always passing a new state instance instead
                                         //instead of mutating the parameter of the current state instance
      State(root,wd,message)
}

object State{
  val SHELL_TOKEN="$ "
  
  def apply(root:Directory, wd:Directory,output:String=""):State={
    new State(root,wd,output)
  }
}