public class CommandFactory {
    //Returneaza o instanta a comenzii care trebuie executata
    public static Command getCommand(Main.Commands command){
        if(command == Main.Commands.cd) return new Cd();
        if(command == Main.Commands.cp) return new Cp();
        if(command == Main.Commands.ls) return new Ls();
        if(command == Main.Commands.mkdir) return new Mkdir();
        if(command == Main.Commands.mv) return new Mv();
        if(command == Main.Commands.pwd) return new Pwd();
        if(command == Main.Commands.rm) return new Rm();
        if(command == Main.Commands.touch) return new Touch();

        return null;
    }
}
