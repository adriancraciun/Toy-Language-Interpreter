package Model.HeapStatements;

import Exceptions.*;
import Model.Expressions.IExpression;
import Model.Types.IType;
import Model.Types.RefType;
import Model.Value.IValue;
import Model.Value.RefValue;
import Utilities.IDictionary;
import Utilities.IHeap;
import Exceptions.ExceptionHeap;

public class HeapReading implements IExpression {
    private IExpression expression;

    public HeapReading(IExpression e) {
        this.expression = e;
    }

    @Override
    public String toString() {
        return "rH("  + expression.toString() + ")";
    }


    @Override
    public IValue evaluate(IDictionary<String, IValue> dictionary, IHeap<IValue> heap) throws ExceptionDivideByZero, ExceptionIllegalOperation, ExceptionValueNotFound, ExceptionHeap {
        IValue refValue = this.expression.evaluate(dictionary, heap);

        if (!(refValue instanceof RefValue))
            throw new ExceptionHeap("The value needs to be a reference");

        int variableAddress = ((RefValue) refValue).getAddress();
        if (heap.get(variableAddress) == null)
            throw new ExceptionHeap("The address is invalid");

        return heap.get(variableAddress);
    }

    @Override
    public IType typecheck(IDictionary<String, IType> typeEnv) throws ExceptionIllegalOperation, ExceptionValueNotFound {
        IType typ = this.expression.typecheck(typeEnv);
        if (typ instanceof RefType)
        {
            RefType reft =(RefType) typ;
            return reft.getInner();
        }
        else
            throw new ExceptionIllegalOperation("the rH argument is not a Ref Type");
    }
}