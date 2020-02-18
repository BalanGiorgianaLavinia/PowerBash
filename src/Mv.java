public class Mv implements Command {
    public Mv() {}

    @Override
    public void run(){
        //Iau primul argument si il despart in cale si nume
        String sourceFullPath = Main.getFirstArgument();
        String sourcePath = FileSystem.extractParentPathFrom(sourceFullPath);
        String sourceName = FileSystem.getFileName(sourceFullPath);

        //Caut directorul sursa
        Directory sourceDir = FileSystem.getDirectoryFrom(sourcePath);

        //Verific daca exista
        if(sourceDir == null){
            //Daca unul din directoarele din path sau chiar nodul de copiat nu exista se va afisa mesajul:
            String message = "mv: cannot move " + sourceFullPath + ": No such file or directory\n";
            OutputFiles.writeError(message);
            return;
        }

        //Caut frunza de mutat
        Leaf originalLeaf = sourceDir.get(sourceName);
        if(sourceName.equals(".")){
            originalLeaf = FileSystem.getCurrentDir();
            sourceDir = originalLeaf.getParent();
        }
        if(originalLeaf == null){
            String message = "mv: cannot move " + sourceFullPath + ": No such file or directory\n";
            OutputFiles.writeError(message);
            return;
        }

        //Iau al doilea argument
        String destPath = Main.getSecondArgument();

        //Caut directorul destinatie
        Directory destDir = FileSystem.getDirectoryFrom(destPath);

        //Verific daca exista
        if(destDir == null){
            //Daca unul din directoarele din path-ul destinatiei nu exista se va afisa urmatorul mesaj:
            String message = "mv: cannot move into " + destPath + ": No such directory\n";
            OutputFiles.writeError(message);
            return;
        }

        Leaf moveLeaf = originalLeaf;

        //Verific daca se insereaza sau nu
        if(destDir.add(moveLeaf) == -1){
            //Fisierul exista deja in directorul destinatie
            //Doar se va afisa mesajul urmator:
            String message = "mv: cannot move " + sourceFullPath + ": Node exists at destination\n";
            OutputFiles.writeError(message);

            return;
        }

        //Setez noul parinte
        moveLeaf.setParent(destDir);
        if(moveLeaf instanceof Directory){
            //Resetez path-urile si parintii daca nodul este director
            ((Directory)moveLeaf).resetPathAndParents();

            //Resetarea parintelui si cailor referintei initiale se aplica doar asupra acestei instante de director
            //Asta inseamna ca si directorul curent va fi modificat (deoarece el contine doar referinta catre acest director)
        }else{
            //Resetez doar path-ul deoarece este File
            moveLeaf.setPath(destDir.getFullPath());
            moveLeaf.resetFullPath();
        }

        //Sterg frunza din sursa
        sourceDir.remove(originalLeaf);
    }
}
