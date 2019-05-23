package Commands
import scalaoops.FIleSystem.State

class UnkownCommand extends Command{
  
  override def apply(state:State):State={
    state.setMessage("command not found!")
  }
}