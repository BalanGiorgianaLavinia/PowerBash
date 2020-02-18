public class Mkdir implements Command{
    public Mkdir() {}

    @Override
    public void run(){
        //Iau argumentul si il despart in cale si nume
        String path = Main.getFirstArgument();
        String dirName = FileSystem.getFileName(path);
        String parentPath = FileSystem.extractParentPathFrom(path);

        //Caut directorul de la sursa
        Directory tempDirectory = FileSystem.getDirectoryFrom(parentPath);

        //Verific daca exista
        if(tempDirectory != null){
            //Directorul s-a gasit
            Directory newDir = new Directory(dirName, tempDirectory);
            int returnCode = tempDirectory.add(newDir);

            if(returnCode == -1){
                Leaf existingLeaf = tempDirectory.get(dirName);
                String message = "mkdir: cannot create directory " + existingLeaf.getFullPath() + ": Node exists\n";
                OutputFiles.writeError(message);

                return;
            }

        }else{
            //Unul din directoare nu exista
            String message = "mkdir: " + parentPath + ": No such directory\n";
            OutputFiles.writeError(message);
        }
    }
}
