package Model.Expressions;

import Exceptions.ExceptionDivideByZero;
import Exceptions.ExceptionIllegalOperation;
import Exceptions.ExceptionValueNotFound;
import Model.Value.IValue;
import Utilities.IDictionary;
import Model.Types.*;
import Model.Value.*;
import Exceptions.*;
import Utilities.IHeap;

public class RelationalExpression implements IExpression{
    private IExpression expression1, expression2;
    private String operator;
    public RelationalExpression(IExpression e1, IExpression e2, String o){
        this.expression1 = e1;
        this.expression2 = e2;
        this.operator = o;
    }

    @Override
    public IValue evaluate(IDictionary<String, IValue> dictionary, IHeap<IValue> heap) throws ExceptionDivideByZero, ExceptionIllegalOperation, ExceptionValueNotFound, ExceptionHeap {
        IValue value1, value2;
        value1 = expression1.evaluate(dictionary, heap);
        if (value1.getType().equals(new IntType()))
        {
            value2 = expression2.evaluate(dictionary, heap);
            if(value2.getType().equals(new IntType()))
            {
                IntValue intValue1 = (IntValue)value1;
                IntValue intValue2 = (IntValue)value2;
                int v1, v2;
                v1 = intValue1.getValue();
                v2= intValue2.getValue();
                return switch (operator) {
                    case ">=" -> new BoolValue(v1 >= v2);
                    case ">" -> new BoolValue(v1 > v2);
                    case "<=" -> new BoolValue(v1 <= v2);
                    case "<" -> new BoolValue(v1 < v2);
                    case "==" -> new BoolValue(v1 == v2);
                    case "!=" -> new BoolValue(v1 != v2);
                    default -> throw new ExceptionIllegalOperation("Illegal operator");
                };
            }
            else throw new ExceptionIllegalOperation("The second value is not an integer type");
        }
        else throw new ExceptionIllegalOperation("The first value is not an integer type");
    }

    @Override
    public IType typecheck(IDictionary<String, IType> typeEnv) throws ExceptionIllegalOperation, ExceptionValueNotFound {
        IType typ1, typ2;
        typ1 = this.expression1.typecheck(typeEnv);
        typ2 = this.expression2.typecheck(typeEnv);
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
    public String toString() {
        return this.expression1.toString() + this.operator + this.expression2.toString();
    }
}
