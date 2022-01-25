package Model.FileHandling;

import Exceptions.*;
import Model.Expressions.IExpression;
import Model.ProgramState.PrgState;
import Model.Statements.Statement;
import Model.Types.IType;
import Model.Types.IntType;
import Model.Types.StringType;
import Model.Value.IValue;
import Model.Value.IntValue;
import Model.Value.StringValue;
import Utilities.*;
import java.io.*;

public class ReadFile implements Statement {
    IExpression expression;
    String variableName;
    public ReadFile(IExpression e, String s){
        this.expression = e;
        this.variableName = s;
    }

    public IExpression getExpression() {
        return expression;
    }

    public String getVariableName() {
        return variableName;
    }

    @Override
    public PrgState execute(PrgState state) throws ExceptionFileHandling, ExceptionValueNotFound, ExceptionIllegalOperation, ExceptionDivideByZero, ExceptionHeap {
        IDictionary<String, IValue> symTable = state.getDictionary();
        if (symTable.containsKey(this.variableName))
        {
            IValue val = symTable.getValue(this.variableName);
            if (val.getType().equals(new IntType())) {
                IValue pathVal = this.expression.evaluate(symTable, state.getHeap());
                if (pathVal.getType().equals(new StringType()))
                {
                    if (state.getFileTable().containsKey((StringValue) pathVal))
                    {
                        BufferedReader b = state.getFileTable().getValue((StringValue) pathVal);
                        try
                        {
                            String line = b.readLine();
                            if (line == null)
                                state.getDictionary().setValue(this.variableName, new IntValue(0));
                            else
                                state.getDictionary().setValue(this.variableName, new IntValue(Integer.parseInt(line)));
                            state.getFileTable().setValue((StringValue) pathVal, b);
                        }
                        catch (IOException e)
                        {
                            throw new ExceptionFileHandling("IO Exception");
                        }
                    }
                    else throw new ExceptionFileHandling("There is no file in fileTable with that name");
                }
                else throw new ExceptionFileHandling("Path variable is not a string type");
            }
            else throw new ExceptionFileHandling("Variable is not an integer");
        }
        else throw new ExceptionFileHandling("Variable is not declared");

        return null;
    }

    @Override
    public IDictionary<String, IType> typecheck(IDictionary<String, IType> typeEnv) throws ExceptionIllegalOperation, ExceptionValueNotFound {
        IType typeVar = typeEnv.getValue(this.variableName);
        IType typeExp = this.expression.typecheck(typeEnv);

        if(typeVar.equals(new IntType()))
        {
            if(typeExp.equals(new StringType()))
                return typeEnv;
            else throw new ExceptionIllegalOperation("Variable is not StringType");
        }
        else throw new ExceptionIllegalOperation("Variable not IntType");
    }

    @Override
    public String toString(){
        return "read(" + this.expression + ", " + this.variableName + "); ";
    }
}
