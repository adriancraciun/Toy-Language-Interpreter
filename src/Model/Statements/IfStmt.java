package Model.Statements;
import Exceptions.ExceptionDivideByZero;
import Exceptions.ExceptionHeap;
import Exceptions.ExceptionIllegalOperation;
import Exceptions.ExceptionValueNotFound;
import Model.Expressions.IExpression;
import Model.ProgramState.PrgState;
import Model.Types.BoolType;
import Model.Types.IType;
import Model.Value.BoolValue;
import Model.Value.IValue;
import Utilities.IDictionary;
import Utilities.IExeStack;

public class IfStmt implements Statement {
    private IExpression expression;
    private Statement thenS;
    private Statement elseS;

    public IfStmt(IExpression e, Statement tST, Statement eST){
        this.expression = e;
        this.thenS = tST;
        this.elseS = eST;
    }

    @Override
    public PrgState execute(PrgState state) throws ExceptionIllegalOperation, ExceptionDivideByZero, ExceptionValueNotFound, ExceptionHeap {
        IDictionary <String, IValue> dictionary = state.getDictionary();
        IExeStack<Statement> stack = state.getStack();
        IValue iValue = expression.evaluate(dictionary, state.getHeap());
        BoolValue boolValue = (BoolValue) iValue;
        boolean val = boolValue.getValue();

        if (val)
            stack.push(thenS);
        else
            stack.push(elseS);

        return null;
    }

    @Override
    public IDictionary<String, IType> typecheck(IDictionary<String, IType> typeEnv) throws ExceptionIllegalOperation, ExceptionValueNotFound {
        IType typexp = this.expression.typecheck(typeEnv);
        if (typexp.equals(new BoolType()))
        {
            thenS.typecheck(typeEnv.copy());
            elseS.typecheck(typeEnv.copy());
            return typeEnv;
        }
        else throw new ExceptionIllegalOperation("The condition of IF has not the type bool");
    }

    @Override
    public String toString(){
        return "(If " + expression.toString()+" Then " + thenS.toString()  +"Else " + elseS.toString() + ") ";
    }
}
