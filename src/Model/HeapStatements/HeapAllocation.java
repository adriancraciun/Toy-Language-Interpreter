package Model.HeapStatements;

import Exceptions.ExceptionDivideByZero;
import Exceptions.ExceptionFileHandling;
import Exceptions.ExceptionIllegalOperation;
import Exceptions.ExceptionValueNotFound;
import Exceptions.ExceptionHeap;
import Model.Expressions.IExpression;
import Model.ProgramState.PrgState;
import Model.Statements.Statement;
import Model.Types.IType;
import Model.Types.RefType;
import Model.Value.IValue;
import Model.Value.RefValue;
import Utilities.*;


public class HeapAllocation implements Statement {
    String variableName;
    IExpression expression;

    public HeapAllocation(String vN, IExpression e) {
        this.variableName = vN;
        this.expression = e;
    }

    @Override
    public PrgState execute(PrgState state) throws ExceptionIllegalOperation, ExceptionDivideByZero, ExceptionValueNotFound, ExceptionFileHandling, ExceptionHeap {
        IDictionary<String, IValue> symTable = state.getDictionary();
        IHeap<IValue> heap = state.getHeap();

        if (!symTable.containsKey(variableName))
            throw new ExceptionHeap("The variable is not part of the sym table");

        IValue tableVal = symTable.getValue(this.variableName);
        if (!(tableVal.getType() instanceof RefType))
            throw new ExceptionHeap("The variable is not a reference type");

        IValue expressionValue = this.expression.evaluate(symTable, heap);

        if (!expressionValue.getType().equals(((RefValue) tableVal).getLocationType()))
        throw new ExceptionHeap("The type of the variable does not match the location type");

        int variablAddress = state.getHeap().allocate(expressionValue);
        symTable.setValue(this.variableName, new RefValue(variablAddress, expressionValue.getType()));
        return null;
    }

    @Override
    public IDictionary<String, IType> typecheck(IDictionary<String, IType> typeEnv) throws ExceptionIllegalOperation, ExceptionValueNotFound {
        IType typeVar = typeEnv.getValue(this.variableName);
        IType typeExp = expression.typecheck(typeEnv);
        if(typeVar.equals(new RefType(typeExp)))
            return typeEnv;
        else throw new ExceptionIllegalOperation("The type of the variable is not reference type");
    }

    @Override
    public String toString(){
        return "newH(" + this.variableName + ", " + this.expression + ")";
    }
}
