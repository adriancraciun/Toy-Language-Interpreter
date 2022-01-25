package Model.Expressions;
import Exceptions.ExceptionDivideByZero;
import Exceptions.ExceptionHeap;
import Exceptions.ExceptionIllegalOperation;
import Exceptions.ExceptionValueNotFound;
import Model.Types.IType;
import Model.Value.IValue;
import Model.Value.IntValue;
import Utilities.*;

public interface IExpression {
    public IValue evaluate(IDictionary<String, IValue> dictionary, IHeap<IValue> heap)
            throws ExceptionDivideByZero, ExceptionIllegalOperation, ExceptionValueNotFound, ExceptionHeap;

    public IType typecheck(IDictionary<String, IType> typeEnv) throws ExceptionIllegalOperation, ExceptionValueNotFound;
}
