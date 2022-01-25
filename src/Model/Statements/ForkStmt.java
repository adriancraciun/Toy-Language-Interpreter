package Model.Statements;

import Exceptions.*;
import Model.ProgramState.PrgState;
import Model.Types.IType;
import Model.Value.IValue;
import Model.Value.StringValue;
import Utilities.*;

import java.io.BufferedReader;

public class ForkStmt implements Statement {
    Statement statement;

    public ForkStmt(Statement statement) {
        this.statement = statement;
    }

    @Override
    public PrgState execute(PrgState state) throws ExceptionIllegalOperation, ExceptionDivideByZero, ExceptionValueNotFound, ExceptionFileHandling, ExceptionHeap {
        IExeStack<Statement> stack2 = new ExeStack<Statement>();
        IDictionary<String, IValue> dictionary2 = state.getDictionary().copy();
        IList<IValue> out2 = state.getList();
        IDictionary<StringValue, BufferedReader> fileTable2 = state.getFileTable();
        IHeap<IValue> heap2 = state.getHeap();

        int ID = GeneratorForkId.getId();
        return new PrgState(stack2, dictionary2, out2, statement, fileTable2, heap2, ID);
    }

    @Override
    public IDictionary<String, IType> typecheck(IDictionary<String, IType> typeEnv) throws ExceptionIllegalOperation, ExceptionValueNotFound {
        return this.statement.typecheck(typeEnv);
    }

    @Override
    public String toString(){
        return "fork(" + this.statement + ")";
    }
}
