package Model.Expressions;

import Exceptions.ExceptionDivideByZero;
import Exceptions.ExceptionIllegalOperation;
import Exceptions.ExceptionValueNotFound;
import Model.Types.IType;
import Model.Value.IValue;
import Model.Value.IntValue;
import Utilities.IDictionary;
import Utilities.IHeap;

public class VarExp implements IExpression{
    String id;
    public VarExp(String i){
        this.id = i;
    }

    @Override
    public String toString(){
        return "" + id;
    }

    @Override
    public IValue evaluate(IDictionary<String, IValue> dictionary, IHeap<IValue> heap) throws ExceptionDivideByZero, ExceptionIllegalOperation, ExceptionValueNotFound {

        return dictionary.getValue(id);
    }

    @Override
    public IType typecheck(IDictionary<String, IType> typeEnv) throws ExceptionValueNotFound {
        return typeEnv.getValue(id);
    }
}
