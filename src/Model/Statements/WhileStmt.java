package Model.Statements;

import Exceptions.*;
import Model.Expressions.IExpression;
import Model.ProgramState.PrgState;
import Model.Types.BoolType;
import Model.Types.IType;
import Model.Value.BoolValue;
import Model.Value.IValue;
import Utilities.IDictionary;
import Utilities.IHeap;
import Utilities.IExeStack;

public class WhileStmt implements Statement {
    IExpression expression;
    Statement statement;

    public WhileStmt(IExpression e, Statement s) {
        this.expression = e;
        this.statement = s;
    }


    @Override
    public PrgState execute(PrgState state) throws ExceptionIllegalOperation, ExceptionDivideByZero, ExceptionValueNotFound, ExceptionFileHandling, ExceptionHeap {
        IExeStack<Statement> stack = state.getStack();
        IHeap<IValue> heap = state.getHeap();
        IDictionary<String, IValue> symTable = state.getDictionary();

        IValue expressionValue = this.expression.evaluate(symTable, heap);
        BoolValue expressionBoolValue = (BoolValue) expressionValue;
        boolean whileValue = expressionBoolValue.getValue();

        if(whileValue) {
            stack.push(new WhileStmt(this.expression, this.statement));
            this.statement.execute(state);
        }

        return null;
    }

    @Override
    public IDictionary<String, IType> typecheck(IDictionary<String, IType> typeEnv) throws ExceptionIllegalOperation, ExceptionValueNotFound {
        IType typeExp = this.expression.typecheck(typeEnv);
        if(typeExp.equals(new BoolType()))
            return typeEnv;
        else throw new ExceptionIllegalOperation("condition expression is not a boolean");
    }

    @Override
    public String toString(){
        return "while(" + this.expression + "){" + this.statement + "}";
    }
}
