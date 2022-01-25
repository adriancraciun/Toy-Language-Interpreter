package Model.FileHandling;

import Exceptions.*;
import Model.Expressions.IExpression;
import Model.ProgramState.PrgState;
import Model.Statements.Statement;
import Model.Value.IValue;
import Utilities.*;
import java.io.*;
import Model.Value.*;
import Model.Types.*;

public class CloseRFile implements Statement {
    private IExpression expression;

    public CloseRFile(IExpression e) {
        this.expression = e;
    }


    @Override
    public PrgState execute(PrgState state) throws ExceptionIllegalOperation, ExceptionDivideByZero, ExceptionValueNotFound, ExceptionFileHandling, ExceptionHeap {
        IValue value = this.expression.evaluate(state.getDictionary(), state.getHeap());
        if (value.getType().equals(new StringType()))
        {
            StringValue stringValue = (StringValue) value;
            IDictionary<StringValue, BufferedReader> fileTable = state.getFileTable();
            if (!fileTable.containsKey(stringValue))
                throw new ExceptionFileHandling("There are no files opened with that name");
            else
            {
                BufferedReader bufferedReader = fileTable.getValue(stringValue);
                try
                {
                    bufferedReader.close();
                }
                catch (IOException e)
                {
                    throw new ExceptionFileHandling("IO Exception");
                }
                fileTable.delete(stringValue);
            }
        }
        else throw new ExceptionFileHandling("The given expression needs to be StringType");

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
    public String toString() {
        return "close(" + this.expression.toString() + "); ";
    }
}
