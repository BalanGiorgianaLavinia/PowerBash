import java.io.*;
import java.util.ArrayList;

public class Main {
    //variabila care retine numarul comenzii curente in executia programului
    public static int commandNumber = 0;

    //lista de argumente curente pentru fiecare comanda executata la un anumit timp
    public static ArrayList<String> newArguments = new ArrayList<String>();

    public static void main(String[] args) throws IOException {
        BufferedReader inputFile = null;
        BufferedWriter outputFile = null;
        BufferedWriter errorFile = null;

        //incerc deschiderea celor 3 fisiere de input, output si erori
        try{
            inputFile = new BufferedReader(new FileReader(args[0]));
        } catch(IOException ex){
            System.exit(1);
        }

        try{
            outputFile = new BufferedWriter(new FileWriter(args[1]));
        } catch(IOException ex){
            System.exit(2);
        }

        try{
            errorFile = new BufferedWriter(new FileWriter(args[2]));
        } catch(IOException ex1){
            try{
                inputFile.close();
            } catch(Exception ex2){
                System.exit(4);
            } finally{
                try{
                    outputFile.close();
                } catch(Exception ex3){
                    System.exit(5);
                }
            }

            System.exit(3);
        }

        //mut fisierele de output si erori pentru a avea acces la ele de oriunde din program
        OutputFiles outputFiles = OutputFiles.getInstance();
        outputFiles.setOutput(outputFile, errorFile);

        //parsez inputul
        String line = null;
        String[] parseVec = null;
        String command = null;

        //creez un sistem de fisiere unic
        FileSystem fileSystem = FileSystem.getInstance();

        //creez un controller unic pentru comenzi
        CommandController controller = CommandController.getInstance();

        Command cmdToExecute = null;

        //parsez comenzile
        while((line = inputFile.readLine()) != null){
            parseVec = line.split(" ");

            //resetez vechile argumente si le salvez pe cele curente
            resetArguments();
            setNewArguments(parseVec);

            //setez controllerul sa execute comanda curenta din input
            cmdToExecute = CommandFactory.getCommand(Main.Commands.valueOf(parseVec[0]));
            controller.set(cmdToExecute);

            //incrementez numarul comenzii curente si il afisez in cele doua fisiere
            int number = ++Main.commandNumber;
            outputFile.write(number + "\n");
            errorFile.write(number + "\n");

            //rulez comanda
            controller.run();
        }


        //inchid fisierele
        inputFile.close();
        outputFile.close();
        errorFile.close();
    }

    private static void resetArguments() {
        newArguments.clear();
    }

    private static void setNewArguments(String[] parseVec) {
        for(int i = 1; i < parseVec.length; i++)
            newArguments.add(parseVec[i]);
    }

    public static String getFirstArgument(){
        return newArguments.get(0);
    }

    public static String getSecondArgument(){
        return newArguments.get(1);
    }

    public static int getNumberOfArguments(){
        return newArguments.size();
    }

    //folosit pentru crearea comenzilor in CommandFactory
    public static enum Commands{
        cd, cp, ls, mkdir, mv, pwd, rm, touch
    }
}
