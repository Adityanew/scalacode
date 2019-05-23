package Commands
import Files._
import scalaoops.FIleSystem._
class Touch(name: String) extends CreateEntry(name){
  override def createSpecificEntry(state: State): File =
    File.empty(state.wd.path, name)
  
}