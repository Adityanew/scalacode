package Files
import scalaoops.FIleSystem._
class Directory(override val parentPath:String, override val name:String, val contents:List[Direntry]) extends Direntry( parentPath:String,  name:String){
  
  def hasEntry(name:String):Boolean= findEntry(name)!= null 

  def asFile = throw new FilesystemException("A directory cannot be converted to a file")
  
  def getAllFoldersInPath:List[String] = path.substring(1).split(Directory.SEPARATOR).toList.filter(x => ! x.isEmpty())
  
  def findDescendant(path: List[String]): Directory = {
    if (path.isEmpty) this
    else findEntry(path.head).asDirectory.findDescendant(path.tail)
  }
  
  def findDescendant(path:String): Directory = {
    if( path.isEmpty()) this
    else findDescendant(path.split(Directory.SEPARATOR).toList)  
  }
  def isRoot: Boolean = parentPath.isEmpty()
  
  def addEntry(newEntry: Direntry): Directory =
    new Directory(parentPath,name, contents :+ newEntry)
  
  def removeEntry(entryName :String): Directory = {
    if(!hasEntry(entryName)) this
    else new Directory(parentPath, name, contents.filter(x => ! x.name.equals(entryName)))
  }
  
  def findEntry(entryName: String): Direntry = {
    def findEntryHelper(name: String, contentList: List[Direntry]): Direntry = {
      if(contentList.isEmpty) null
      else if(contentList.head.name.equals(name)) contentList.head
      else findEntryHelper(name, contentList.tail)
      
    }
    findEntryHelper(name, contents)
  }
  override def asDirectory=this
  
  override def getType:String="Directory"
  
  override def isDirectory: Boolean = true
  
  override def isFile: Boolean = false
  
  def replaceEntry(entryName: String, newEntry: Direntry):Directory=
    new Directory(parentPath,name,contents.filter(e => !e.name.equals(entryName)) :+ newEntry)
}

object Directory{
  val SEPARATOR = "/"
  val ROOT_PATH="/"
  
  def ROOT: Directory =Directory.empty("", "")
  
  def empty(parentPath:String,name:String):Directory={
    new Directory(parentPath,name,List()) 
  }
}

