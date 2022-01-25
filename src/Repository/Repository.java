package Repository;

import Exceptions.ExceptionFileHandling;
import Exceptions.ExceptionValueNotFound;
import Model.ProgramState.PrgState;
import Model.Statements.Statement;
import Model.Value.*;
import Utilities.*;

import java.io.*;
import java.util.ArrayList;
import java.util.List;

public class Repository implements IRepository{
    List<PrgState> programStateList = new ArrayList<>();
    String logFilePath;

    public Repository(PrgState p, String l) {
        this.programStateList.add(p);
        this.logFilePath = l;
    }

    @Override
    public void addProgramState(PrgState prgState) {
        this.programStateList.add(prgState);
    }

    @Override
    public void logPrgStateExec(PrgState prgState) throws ExceptionFileHandling {
        PrintWriter logFile = null;
        try{
            logFile = new PrintWriter(new BufferedWriter(new FileWriter(this.logFilePath,true)));

            IExeStack<Statement> stack = prgState.getStack();
            IDictionary<String, IValue> dictionary = prgState.getDictionary();
            IList<IValue> out = prgState.getList();
            IDictionary<StringValue, BufferedReader> fileTable = prgState.getFileTable();
            IHeap<IValue> heap = prgState.getHeap();

            logFile.println("\nExeStack: " + prgState.getId());
            for(Statement statement: stack.getAll())
                logFile.println(statement);


            logFile.println("\nSymTable:\n");
            for(String key: dictionary.getAll())
                logFile.println(key + " --> " + dictionary.getValue(key) + "\n");

            logFile.println("\nOut:\n");
            for(IValue value: out.getAll())
                logFile.println(value + "\n" );

            logFile.println("\nFileTable:\n");
            for(StringValue i: fileTable.getAll())
                logFile.println(i + " " + fileTable.getValue(i) + "\n");

            logFile.println("\nHeap:\n");
            for(int i : heap.getContent().keySet())
                logFile.println(i + " --> " + heap.get(i) + "\n");

            logFile.println("\n");
        }
        catch (FileNotFoundException e) {
            throw new ExceptionFileHandling("The file used for PrintWriter was not found");
        }
        catch (IOException e) {
            throw new ExceptionFileHandling("IO Exception");
        }
        catch (ExceptionValueNotFound exceptionValueNotFound) {
            throw new ExceptionFileHandling("Value not found");
        }

        logFile.close();
    }

    @Override
    public List<PrgState> getPrgList() {
        return this.programStateList;
    }

    @Override
    public void setPrgList(List<PrgState> list) {
        this.programStateList = list;
    }
}
