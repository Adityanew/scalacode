package scalaoops.FIleSystem
import Files.Directory
import java.util.Scanner
import Commands._

object FileSystem extends App{
  val root=Directory.ROOT
  
  io.Source.stdin.getLines().foldLeft(State(root,root))((currentState, newLine) => {
    currentState.show
    Command.from(newLine).apply(currentState)
  })
  
  /*
   * List(1,2,3,4).foldLeft(0)((x,y) => x+y))
   * 0+1 => 1
   * 1+2 => 3
   * 3+3 => 6
   * 6+4 => 10
   */
  /*var state=State(root,root)
  val scanner=new Scanner(System.in)
  while(true){
    state.show
    val input=scanner.nextLine()
    state=Command.from(input).apply(state)*/
}
