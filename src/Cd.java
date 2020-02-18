public class Cd implements Command {
    public Cd() {}

    @Override
    public void run(){
        String path = Main.getFirstArgument();
        Directory newCurrentDir = FileSystem.getDirectoryFrom(path);

        if(newCurrentDir != null){
            //Comanda s-a realizat cu succes
            FileSystem.setCurrentDir(newCurrentDir);
        }else{
            String message = "cd: " + path + ": No such directory\n";
            OutputFiles.writeError(message);
        }
    }
}
