package Model.Expressions;

import Exceptions.ExceptionDivideByZero;
import Exceptions.ExceptionIllegalOperation;
import Exceptions.ExceptionValueNotFound;
import Model.Types.*;
import Model.Value.IValue;
import Model.Value.IntValue;
import Utilities.IDictionary;
import Utilities.IHeap;

public class ValueExp implements IExpression{
    IValue value;
    public ValueExp(IValue v){
        this.value = v;
    }

    @Override
    public String toString(){
        return "" + value.toString();
    }

    @Override
    public IValue evaluate(IDictionary<String, IValue> dictionary, IHeap<IValue> heap) throws ExceptionDivideByZero, ExceptionIllegalOperation, ExceptionValueNotFound {
        if(this.value.getType().equals(new IntType()) || this.value.getType().equals(new BoolType()) || this.value.getType().equals(new StringType()))
            return this.value;
        throw new ExceptionIllegalOperation("Unknown data type\n");
    }

    @Override
    public IType typecheck(IDictionary<String, IType> typeEnv){
        return this.value.getType();
    }
}
