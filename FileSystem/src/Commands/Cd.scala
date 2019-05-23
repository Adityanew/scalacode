package Commands
import scalaoops.FIleSystem._
import Files._
class Cd(dir: String) extends Command{
  
  override def apply(state: State): State = {
    //1. find root
    val root = state.root
    val wd = state.wd
    //2. find absolute path of the directory I want to cd to
    val absolutePath=
      if(dir.startsWith(Directory.SEPARATOR)) dir
      else if(wd.isRoot) wd.path+dir
      else wd.path+Directory.SEPARATOR+dir 
        
    //3. find the directory to cd to given path
      val destinationDirectory = doFindEntry(root,absolutePath)
    //4. change the state given new directory
      if(destinationDirectory==null || !destinationDirectory.isDirectory)
        state.setMessage(dir + ": no such directory")
      else
        State(root,destinationDirectory.asDirectory)
  }
  
  
  def doFindEntry(root: Directory, path: String): Direntry = {
    
    def findEntryHelper(currentDirectory: Directory, path: List[String]): Direntry = {
      if(path.isEmpty || path.head.isEmpty()) currentDirectory
      else if(path.tail.isEmpty) currentDirectory.findEntry(path.head)
      else {
        val nextDir = currentDirectory.findEntry(path.head).asDirectory
        if(nextDir == null || !nextDir.isDirectory ) null
        else
          findEntryHelper(nextDir,path.tail)
      } 
      
    }
    //1. tokens
    def collapseRelativeTokens(path: List[String], result: List[String]): List[String] = {
      if(path.isEmpty) result
      else if( ".".equals(path.head)) collapseRelativeTokens(path.tail, result)
      else if("..".equals(path.head)){
        if(result.isEmpty) null 
        else
          collapseRelativeTokens(path.tail, result.init)
      }
      else collapseRelativeTokens(path.tail, result :+ path.head)
    }
      val tokens: List[String] = path.substring(1).split(Directory.SEPARATOR).toList
    // eliminate or collapse relative tokens
      /*
       * [a,.] => [a]
       * [a,b,.,.] => [a,b]
       * /a/../ => [a, ..] => []
       * /a/b/.. => [a,b,..] => [a]
       */
      
      val newTokens=collapseRelativeTokens(tokens, List())
      
    //2. navigate to the correct entry
      if(newTokens==null) null
      else
      findEntryHelper(root,newTokens)
  }
}