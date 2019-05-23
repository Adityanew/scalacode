package Commands
import scalaoops.FIleSystem.State
import Files.Direntry


class Ls extends Command{
  
  def apply(state: State): State= {
    val contents=state.wd.contents
    val niceOutput=createNiceOutput(contents)
      state.setMessage(niceOutput)
      
  null
  }
  
  def createNiceOutput(contents: List[Direntry]): String =
  {
    if(contents.isEmpty) ""
    else {
      val entry = contents.head
      entry.name + "[" + entry.getType + "]" +  "\n" +  createNiceOutput(contents.tail)
    }
  }
}