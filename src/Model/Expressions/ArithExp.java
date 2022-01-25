package Model.Expressions;

import Exceptions.ExceptionHeap;
import Exceptions.ExceptionIllegalOperation;
import Exceptions.ExceptionValueNotFound;
import Model.Types.IType;
import Model.Types.IntType;
import Model.Value.IValue;
import Model.Value.IntValue;
import Utilities.IDictionary;
import Exceptions.ExceptionDivideByZero;
import Utilities.IHeap;

public class ArithExp implements IExpression{
    private char operation;
    private IExpression left;
    private IExpression right;

    public ArithExp(char o, IExpression l, IExpression r){
        this.operation = o;
        this.left = l;
        this.right = r;
    }

    @Override
    public IValue evaluate(IDictionary<String, IValue> dictionary, IHeap<IValue> heap) throws ExceptionDivideByZero, ExceptionIllegalOperation, ExceptionValueNotFound, ExceptionHeap {
        IValue leftValue = left.evaluate(dictionary, heap);
        IValue rightValue = right.evaluate(dictionary, heap);
        IntValue v1 = (IntValue)leftValue;
        IntValue v2 = (IntValue)rightValue;
        int n1 = v1.getValue();
        int n2 = v2.getValue();

        switch (operation){
            case '+':
                return new IntValue(n1 + n2);
            case '-':
                return new IntValue(n1 - n2);
            case '*':
                return new IntValue(n1 * n2);
            case '/':
                if (n2 == 0)
                    throw new ExceptionDivideByZero("Attempted division by 0");
                return new IntValue(n1 / n2);
        }

        throw new ExceptionIllegalOperation("Attempted illegal operation");
    }

    @Override
    public IType typecheck(IDictionary<String, IType> typeEnv) throws ExceptionIllegalOperation, ExceptionValueNotFound {
        IType typ1, typ2;
        typ1 = this.left.typecheck(typeEnv);
        typ2 = this.right.typecheck(typeEnv);
        if (typ1.equals(new IntType()))
        {
            if (typ2.equals(new IntType()))
                return new IntType();
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
