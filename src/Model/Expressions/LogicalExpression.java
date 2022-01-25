package Model.Expressions;

import Exceptions.ExceptionDivideByZero;
import Exceptions.ExceptionHeap;
import Exceptions.ExceptionIllegalOperation;
import Exceptions.ExceptionValueNotFound;
import Model.Types.BoolType;
import Model.Types.IType;
import Model.Types.IntType;
import Model.Value.BoolValue;
import Model.Value.IValue;
import Model.Value.IntValue;
import Utilities.IDictionary;
import Utilities.IHeap;

public class LogicalExpression implements IExpression{
    private String operation;
    private IExpression left;
    private IExpression right;

    public LogicalExpression(String o, IExpression l, IExpression r){
        this.operation = o;
        this.left = l;
        this.right = r;
    }

    @Override
    public IValue evaluate(IDictionary<String, IValue> dictionary, IHeap<IValue> heap) throws ExceptionDivideByZero, ExceptionIllegalOperation, ExceptionValueNotFound, ExceptionHeap {
        IValue leftValue = left.evaluate(dictionary, heap);
        IValue rightValue = right.evaluate(dictionary, heap);
        BoolValue v1 = (BoolValue) leftValue;
        BoolValue v2 = (BoolValue) rightValue;
        boolean n1 = v1.getValue();
        boolean n2 = v2.getValue();

        switch (operation){
            case "and":
                return new BoolValue(n1 && n2);
            case "or":
                return new BoolValue(n1 || n2);
        }

        throw new ExceptionIllegalOperation("Attempted illegal operation");
    }

    @Override
    public IType typecheck(IDictionary<String, IType> typeEnv) throws ExceptionIllegalOperation, ExceptionValueNotFound {
        IType typ1, typ2;
        typ1 = this.left.typecheck(typeEnv);
        typ2 = this.right.typecheck(typeEnv);
        if (typ1.equals(new BoolType()))
        {
            if (typ2.equals(new BoolType()))
                return new BoolType();
            else
                throw new ExceptionIllegalOperation("second operand is not an integer");
        }
        else throw new ExceptionIllegalOperation("first operand is not an integer");
    }

    @Override
    public String toString(){
        return "" + left + operation + right;
    }
}
