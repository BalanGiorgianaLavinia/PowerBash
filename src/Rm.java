public class Rm implements Command {
    public Rm() {}

    @Override
    public void run(){
        //Iau primul argument si il despart in cale si nume
        String fullPath = Main.getFirstArgument();
        String path = FileSystem.extractParentPathFrom(fullPath);
        String name = FileSystem.getFileName(fullPath);

        //Caut directorul sursa
        Directory temp = FileSystem.getDirectoryFrom(path);

        //Verific daca exista
        if(temp == null){
            //Daca unul din folderele din path nu exista afisez mesajul urmator:
            String message = "rm: cannot remove " + fullPath + ": No such file or directory\n";
            OutputFiles.writeError(message);

            return;
        }

        //Caut fisierul in director
        Leaf leaf = temp.get(name);

        if(name.equals("..")){
            leaf = temp.getParent();
        }

        if(name.equals(".")){
            leaf = temp;
            temp = temp.getParent();

            if(temp == null){
                String message = "rm: cannot remove " + fullPath + ": No such file or directory\n";
                OutputFiles.writeError(message);

                return;
            }
        }

        if(leaf == null){
            //Daca nu exista afisez mesajul urmator:
            String message = "rm: cannot remove " + fullPath + ": No such file or directory\n";
            OutputFiles.writeError(message);

            return;
        }

        //Verific daca este fisier sau folder
        if(leaf instanceof File){
            //Daca e fisier il sterg
            temp.remove(leaf);
        }else{
            //Daca e director verific daca acesta contine directorul curent
            if(((Directory)leaf).hasCurrentDir()){
                //Daca acest director contine directorul curent nu se intampla nimic
                return;
            }else{
                //altfel il sterg
                temp.remove(leaf);
            }
        }
    }
}
