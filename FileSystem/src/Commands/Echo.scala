package Commands
import scalaoops.FIleSystem._
import Files._
import scala.annotation.tailrec

class Echo(args: Array[String]) extends Command {
   
  def apply(state: State): State = {
    /*
     * if no args, state
     * else if just one arg, print to console
     * 	else if multiple args
     * {
     * if >
     * echo to a file ( may create a new file if not there)
     * if >>
     * append to file
     * else just echo everything to console
     */
    
    
    if (args.isEmpty) state
    else if ( args.tail.isEmpty ) state.setMessage(args(0))
    else {
      val operator = args(args.length - 2)
      val filename = args(args.length - 1)
      val contents = createContents(args, args.length - 2)
      if(">>".equals(operator))
        doEcho(state, contents, filename, append = true)
      else if(">".equals(operator))
        doEcho(state, contents, filename, append = false)
      else
        state.setMessage(createContents(args, args.length))
    }
  }
  
  def getRootAfterEcho(currentDirectory: Directory, path: List[String], contents: String, append: Boolean): Directory = {
    /* 
     * if path in empty then fail
     * else if no more things to explore
     *  find the file to create/add content to
     *  if file not found , create file
     *  else if the entry is a directory, then fail
     *  else
     *  	replace or append content to the file
     *  	replace the entry with the filename with the new file
     * else
     *  find next directory to navigate
     *  call gRAE on that
     *  if recursive call failed fail
     *  else replace entry with new directory after the recursive call	
     */
    if(path.isEmpty) currentDirectory
    else if(path.tail.isEmpty){
      val dirEntry = currentDirectory.findEntry(path.head)
      if(dirEntry == null)
        currentDirectory.addEntry(new File(currentDirectory.path, path.head, contents))
      else if(dirEntry.isDirectory) currentDirectory
        else 
          if (append) currentDirectory.replaceEntry(path.head, dirEntry.asFile.appendContents(contents))
          else currentDirectory.replaceEntry(path.head, dirEntry.asFile.setContents(contents))
    }
    else{
      val nextDirectory = currentDirectory.findEntry(path.head).asDirectory
      val nextNewDirectory = getRootAfterEcho(nextDirectory, path.tail, contents, append)
      if (nextDirectory == nextNewDirectory) currentDirectory
      else currentDirectory.replaceEntry(path.head, nextNewDirectory)
    }
    
  }
  
  def doEcho(state: State, contents: String, filename: String, append: Boolean): State = {
    if(filename.contains(Directory.SEPARATOR))
        state.setMessage("Echo: filename must not contain separators")
    else{
       val newRoot: Directory = getRootAfterEcho(state.root, state.wd.getAllFoldersInPath :+ filename, contents, append) 
       if(newRoot == state.root)
         state.setMessage(filename+": no such file")
       else
         State(newRoot, newRoot.findDescendant(state.wd.getAllFoldersInPath)) 
       }
    
  }
  
  def createContents(args: Array[String], topIndex: Int): String = {
    @tailrec
   def createContentHelper(currentIndex: Int, accumulator: String): String = {
      if(currentIndex >= topIndex) accumulator
      else createContentHelper(currentIndex + 1, accumulator+ " "+args(currentIndex))
    }
   createContentHelper(0, "")
  }
  
}