public class Touch implements Command {
    public Touch() {}

    @Override
    public void run(){
        //Iau primul argument si despart calea de nume
        String path = Main.getFirstArgument();
        String fileName = FileSystem.getFileName(path);
        String parentPath = FileSystem.extractParentPathFrom(path);

        //Caut directorul sursa
        Directory tempDirectory = FileSystem.getDirectoryFrom(parentPath);

        //Verific daca exista
        if(tempDirectory != null){
            //Directorul s-a gasit
            File newFile = new File(fileName, tempDirectory);
            int returnCode = tempDirectory.add(newFile);

            if(returnCode == -1){
                Leaf existingLeaf = tempDirectory.get(fileName);
                String message = "touch: cannot create file " + existingLeaf.getFullPath() + ": Node exists\n";
                OutputFiles.writeError(message);

                return;
            }
        }else {
            //Unul din directoare nu exista
            String message = "touch: " + parentPath + ": No such directory\n";
            OutputFiles.writeError(message);
        }
    }
}
