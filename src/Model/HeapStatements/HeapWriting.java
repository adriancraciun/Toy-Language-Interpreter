package Model.HeapStatements;

import Exceptions.*;
import Model.ProgramState.PrgState;
import Model.Statements.Statement;
import Model.Expressions.*;
import Model.Types.IType;
import Model.Types.RefType;
import Model.Value.IValue;
import Model.Value.RefValue;
import Utilities.IDictionary;

public class HeapWriting implements Statement {
    String variableName;
    IExpression expression;
    public HeapWriting(String vN, IExpression e) {
        this.variableName = vN;
        this.expression = e;
    }

    @Override
    public PrgState execute(PrgState state) throws ExceptionIllegalOperation, ExceptionDivideByZero, ExceptionValueNotFound, ExceptionFileHandling, ExceptionHeap {
        if (!state.getDictionary().containsKey(this.variableName))
            throw new ExceptionHeap("The variable does not exist in the symTable");

        IValue val = state.getDictionary().getValue(this.variableName);
        if (!(val instanceof RefValue))
            throw new ExceptionHeap("The value is not a reference");

        int variableAddress = ((RefValue)val).getAddress();
        if (state.getHeap().get(variableAddress) == null)
            throw new ExceptionHeap("The address is null");

        IValue expressionValue = this.expression.evaluate(state.getDictionary(),state.getHeap());
        if (!expressionValue.getType().equals(((RefValue) val).getLocationType()))
            throw new ExceptionHeap("The types of the values are incompatible");

        state.getHeap().put(variableAddress, expressionValue);
        return null;
    }

    @Override
    public IDictionary<String, IType> typecheck(IDictionary<String, IType> typeEnv) throws ExceptionIllegalOperation, ExceptionValueNotFound {
        IType typeVar = typeEnv.getValue(this.variableName);
        IType typeExp=this.expression.typecheck(typeEnv);
        if(typeVar.equals(new RefType(typeExp)))
            return typeEnv;
        else throw new ExceptionIllegalOperation("The type of the variable is not reference type");
    }

    @Override
    public String toString(){
        return "wH(" + this.variableName + "," + this.expression +")";
    }
}
