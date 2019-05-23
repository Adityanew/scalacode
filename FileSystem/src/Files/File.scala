package Files
import scalaoops.FIleSystem._
class File(override val parentPath: String, override  val name: String, val contents: String) extends Direntry(parentPath: String, name: String){
  
  def asDirectory:Directory = throw new FilesystemException("A file cannot be converted to a directory")
  def asFile: File = this
  def getType:String = "File"
  def setContents(newContent: String): File = new File(parentPath, name, newContent)
  def appendContents(newContent: String): File = setContents(contents + "\n"+newContent)
  override def isDirectory: Boolean = false
  override def isFile: Boolean = true
  
}

object File{
  def empty(parentPath: String, name:String): File ={
    new File(parentPath,name,"")
  }
}