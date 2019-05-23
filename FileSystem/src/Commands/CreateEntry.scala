package Commands
import scalaoops.FIleSystem._
import Files._

abstract class CreateEntry(val name: String) extends Command {
  override def apply(state:State):State={
     val wd=state.wd
     if(wd.hasEntry(name)){
       state.setMessage("Entry" +name+ "already exists!")
     }
     else if(name.contains(Directory.SEPARATOR))
       state.setMessage(name+" must not contain separators")
     else if (checkIllegal(name))
       state.setMessage((name+" must not contain '.'"))
       
     else
       doCreateEntry(state, name)
       
   }
   
   def checkIllegal(name:String):Boolean=
     name.contains(".")
     
   def doCreateEntry(state:State,name:String):State=
   { def updateStructure(currentDirectory: Directory, path: List[String], newEntry: Direntry): Directory = {
     if (path.isEmpty) currentDirectory.addEntry(newEntry)
     else{
       val oldEntry = currentDirectory.findEntry(path.head).asDirectory
       
       currentDirectory.replaceEntry(oldEntry.name, updateStructure(oldEntry, path.tail, newEntry))
     }
     /*
      * /a/b
      * (contents)
      * (new Entry)/e
      *  updateStructure(root,["a","b"],/e)
      *  => path.isEmpty? No
      *  => oldEntry=/a
      *  root.replaceEntry("a", updateStructure(/a,["b"],/e))
      *  	=>path.isEmpty? No
      *  	=>oldEntry=/b
      *  	/a.replaceEntry("b", updateStructure(/b,[],/e))
      *  		=>path.isEmpty?  Yes
      *  		=> /b.addEntry(/e) 
      */
     
   }
     val wd=state.wd
    
     //1. all the directories in the full path
     val allDirsInPath=wd.getAllFoldersInPath
     //2. create new directory  entry in wd
     // TODO : implement this
     val newEntry:Direntry=createSpecificEntry(state)
     //3.update the whole directory structure starting from  the root
     //(the directory structure is IMMUTABLE)
     val newRoot=updateStructure(state.root,allDirsInPath,newEntry)
     //4.find the new working directory  INSTANCE given wd's full path, in the new directory structure.
     val newWd=newRoot.findDescendant(allDirsInPath)
     State(newRoot,newWd)
     
   }
   def createSpecificEntry(state: State): Direntry
}