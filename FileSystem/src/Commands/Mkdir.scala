package Commands
import scalaoops.FIleSystem.State
import Files._

class Mkdir(name:String) extends CreateEntry(name: String) {
   override def createSpecificEntry(state: State): Directory ={
     Directory.empty(state.wd.path, name)
   }
  
}