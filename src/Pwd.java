public class Pwd implements Command {

    public Pwd() {}

    @Override
    public void run(){
        //Iau directorul curent
        Directory currentDir = FileSystem.getCurrentDir();

        //Obtin calea absoluta
        String message = currentDir.getFullPath() + "\n";

        //Afisez calea in output
        OutputFiles.writeOutput(message);
    }
}
