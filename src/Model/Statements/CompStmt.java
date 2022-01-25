package Model.Statements;

import Exceptions.ExceptionIllegalOperation;
import Exceptions.ExceptionValueNotFound;
import Model.ProgramState.PrgState;
import Model.Types.IType;
import Utilities.IDictionary;
import Utilities.IExeStack;

public class CompStmt implements Statement {
    private Statement first;
    private Statement second;
    public CompStmt(Statement f, Statement s){
        this.first = f;
        this.second = s;
    }

    @Override
    public PrgState execute(PrgState state){
        IExeStack<Statement> stack = state.getStack();
        stack.push(second);
        stack.push(first);
        return null;
    }

    @Override
    public IDictionary<String, IType> typecheck(IDictionary<String, IType> typeEnv) throws ExceptionIllegalOperation, ExceptionValueNotFound {
        return this.second.typecheck(first.typecheck(typeEnv));
    }

    public String toString(){
        return first.toString() + second.toString();
    }
}
