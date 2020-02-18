public class Ls implements Command {
    public Ls() {}

    @Override
    public void run(){
        //Verific numarul de argumente si execut comanda in functie
        //de acest numar
        if(Main.getNumberOfArguments() == 0){
            //Ls in directorul curent
            Directory temp = FileSystem.getCurrentDir();
            String text = temp.listFiles() + "\n";
            OutputFiles.writeOutput(text);
        }

        if(Main.getNumberOfArguments() == 1){
            String argument = Main.getFirstArgument();

            if(argument.equals("-R")) {
                //Ls recursiv in directorul curent
                Directory temp = FileSystem.getCurrentDir();
                String text = temp.listFilesRecursive() + "\n";
                OutputFiles.writeOutput(text);
            }else{
                //Ls in directorul din argument
                String path = argument;
                Directory temp = FileSystem.getDirectoryFrom(path);

                if(temp == null){
                    //Nu s-a gasit unul din directoarele din path
                    String message = "ls: " + path + ": No such directory\n";
                    OutputFiles.writeError(message);

                    return;
                }

                String text = temp.listFiles() + "\n";
                OutputFiles.writeOutput(text);
            }

        }

        if(Main.getNumberOfArguments() == 2){
            String argument1 = Main.getFirstArgument();
            String argument2 = Main.getSecondArgument();
            String path;

            //Caut care este calea
            if(argument1.equals("-R")){
                path = argument2;
            }else{
                path = argument1;
            }

            //Ls recursiv pe directorul din path
            Directory temp = FileSystem.getDirectoryFrom(path);

            if(temp == null){
                //Nu s-a gasit unul din directoarele din path
                String message = "ls: " + path + ": No such directory\n";
                OutputFiles.writeError(message);

                return;
            }

            String text = temp.listFilesRecursive() + "\n";
            OutputFiles.writeOutput(text);
        }

    }
}
