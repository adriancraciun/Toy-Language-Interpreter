package Model.Statements;

public class GeneratorForkId {
    private static int counter = 1;

    public static int getId(){
        return counter++;
    }
}
