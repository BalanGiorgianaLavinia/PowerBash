public class CommandController implements Command{
    //Aceasta clasa modeleaza design pattern-ul de singleton deoarece
    //am nevoie doar de un controller de comenzi

    private static CommandController singleInstance = null;
    private CommandController(){}

    // Crearea unei singure instante
    public static CommandController getInstance() {
        if(singleInstance == null) {
            singleInstance = new CommandController();
        }
        return singleInstance;
    }


    // Functionalitatea controller-ului de comenzi
    Command cmdToExecute = null;

    public void set(Command cmdToExecute){
        this.cmdToExecute = cmdToExecute;
    }

    @Override
    public void run(){
        if(cmdToExecute == null){
            System.out.println("Comanda nu a fost initializata.");

            return;
        }

        cmdToExecute.run();
    }
}
