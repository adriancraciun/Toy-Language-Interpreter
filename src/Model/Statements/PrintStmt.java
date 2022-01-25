package Model.Statements;
import Exceptions.ExceptionDivideByZero;
import Exceptions.ExceptionHeap;
import Exceptions.ExceptionIllegalOperation;
import Exceptions.ExceptionValueNotFound;
import Model.Expressions.IExpression;
import Model.ProgramState.PrgState;
import Model.Types.IType;
import Model.Value.IValue;
import Utilities.*;

public class PrintStmt implements Statement {
    private IExpression expression;
    public PrintStmt(IExpression e)
    {
        this.expression = e;
    }

    @Override
    public PrgState execute(PrgState state) throws ExceptionIllegalOperation, ExceptionDivideByZero, ExceptionValueNotFound, ExceptionHeap {
        IList<IValue> list = state.getList();
        IDictionary<String, IValue> dictionary = state.getDictionary();

        IValue value = this.expression.evaluate(dictionary, state.getHeap());
        list.add(value);
        return null;
    }

    @Override
    public IDictionary<String, IType> typecheck(IDictionary<String, IType> typeEnv) throws ExceptionIllegalOperation, ExceptionValueNotFound {
        this.expression.typecheck(typeEnv);
        return typeEnv;
    }

    @Override
    public String toString(){ return "Print(" + expression.toString() + "); " ;}
}
