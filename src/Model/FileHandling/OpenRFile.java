package Model.FileHandling;

import Exceptions.*;
import Model.Expressions.IExpression;
import Model.ProgramState.PrgState;
import Model.Statements.*;
import Model.Value.IValue;
import Model.Value.*;
import Utilities.*;
import java.io.*;
import Model.Types.*;

import java.io.FileNotFoundException;
import java.io.FileReader;

public class OpenRFile implements Statement {
    IExpression expression;

    public OpenRFile(IExpression e){
        this.expression = e;
    }

    @Override
    public PrgState execute(PrgState state) throws ExceptionIllegalOperation, ExceptionDivideByZero, ExceptionValueNotFound, ExceptionFileHandling, ExceptionHeap {
        IValue value = this.expression.evaluate(state.getDictionary(), state.getHeap());
        if (value.getType().equals(new StringType()))
        {
            String path = ((StringValue) value).getString();
            try
            {
                BufferedReader b = new BufferedReader(new FileReader(path));
                IDictionary<StringValue, BufferedReader> fileTable = state.getFileTable();
                if (fileTable.containsKey((StringValue) value))
                    throw new ExceptionFileHandling("The file is already open");
                fileTable.setValue((StringValue) value, b);
            }
            catch (FileNotFoundException e) {
                throw new ExceptionFileHandling("Path not found\n");
            }
        }
        else
            throw new ExceptionFileHandling("The given expression is not a string");

        return null;
    }

    @Override
    public IDictionary<String, IType> typecheck(IDictionary<String, IType> typeEnv) throws ExceptionIllegalOperation, ExceptionValueNotFound {
        IType typeExp = this.expression.typecheck(typeEnv);
        if(typeExp.equals(new StringType()))
            return typeEnv;
        else throw new ExceptionIllegalOperation("Expression not of type string.");
    }

    @Override
    public String toString(){
        return "open(" + this.expression + "); ";
    }
}
