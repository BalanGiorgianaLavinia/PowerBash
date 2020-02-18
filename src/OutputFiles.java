import java.io.BufferedWriter;
import java.io.IOException;

public class OutputFiles {
    //Singleton
    private static OutputFiles instance = null;
    private static BufferedWriter output;
    private static BufferedWriter errors;

    private OutputFiles() {}

    public static OutputFiles getInstance(){
        if(instance == null)
            instance = new OutputFiles();

        return instance;
    }

    //Setter pentru cele doua fisiere de iesire: output, respectiv errors
    public void setOutput(BufferedWriter outputFile, BufferedWriter errorFile) {
        this.output = outputFile;
        this.errors = errorFile;
    }

    //Cele doua metode usureaza munca de scriere in cele doua fisiere
    public static void writeOutput(String message){
        try {
            output.write(message);
        }catch (IOException e){
            System.out.println("Nu s-a putut scrie mesajul " + message + " in output");
        }
    }

    public static void writeError(String message){
        try {
            errors.write(message);
        }catch (IOException e){
            System.out.println("Nu s-a putut scrie mesajul " + message + " in errors");
        }
    }
}
