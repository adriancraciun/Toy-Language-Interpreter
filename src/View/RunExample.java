package View;
import Controller.*;
import Exceptions.*;

public class RunExample extends Command{
    private Controller controller;
    public RunExample(String k, String d, Controller c){
        super(k, d);
        this.controller = c;
    }

    @Override
    public void execute() {
        try {
            controller.allStep();
        }
        catch (InterruptedException exceptionEmptyStack) {
            exceptionEmptyStack.printStackTrace();
        }

    }
}
