public class Cp implements Command {
    public Cp() {}

    @Override
    public void run(){
        //Iau primul argument si despart calea de nume
        String sourceFullPath = Main.getFirstArgument();
        String sourcePath = FileSystem.extractParentPathFrom(sourceFullPath);
        String sourceName = FileSystem.getFileName(sourceFullPath);

        //Caut directorul sursa
        Directory sourceDir = FileSystem.getDirectoryFrom(sourcePath);
        if(sourceDir == null){
            //Daca unul din directoarele din path sau chiar nodul de copiat nu exista se va afisa mesajul:
            String message = "cp: cannot copy " + sourceFullPath + ": No such file or directory\n";
            OutputFiles.writeError(message);

            return;
        }

        //Caut frunza de copiat
        Leaf originalLeaf = sourceDir.get(sourceName);
        if(sourceName.equals(".")){
            originalLeaf = FileSystem.getCurrentDir();
        }
        if(originalLeaf == null){
            String message = "cp: cannot copy " + sourceFullPath + ": No such file or directory\n";
            OutputFiles.writeError(message);
            return;
        }

        //Iau al doilea argument pentru destinatie
        String destPath = Main.getSecondArgument();

        //Caut directorul destinatiei
        Directory destDir = FileSystem.getDirectoryFrom(destPath);

        //Verific daca exista
        if(destDir == null){
            //Daca unul din directoarele din path-ul destinatiei nu exista se va afisa urmatorul mesaj:
            String message = "cp: cannot copy into " + destPath + ": No such directory\n";
            OutputFiles.writeError(message);

            return;
        }

        //Clonez frunza gasita in sursa.
        Leaf copyLeaf = originalLeaf.clone();

        //Setez noul parinte
        copyLeaf.setParent(destDir);
        if(copyLeaf instanceof Directory){
            //Resetez path-urile si parintii daca nodul este director
            ((Directory)copyLeaf).resetPathAndParents();
        }else{
            //Resetez doar path-ul deoarece este File
            copyLeaf.setPath(destDir.getFullPath());
            copyLeaf.resetFullPath();
        }

        if(destDir.add(copyLeaf) == -1){
            //Fisierul exista deja in directorul destinatie
            //Doar se va afisa mesajul urmator:
            String message = "cp: cannot copy " + sourceFullPath + ": Node exists at destination\n";
            OutputFiles.writeError(message);

            return;
        }
    }
}
