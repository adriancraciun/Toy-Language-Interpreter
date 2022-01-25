package Model.Statements;

import Exceptions.ExceptionDivideByZero;
import Exceptions.ExceptionHeap;
import Exceptions.ExceptionIllegalOperation;
import Exceptions.ExceptionValueNotFound;
import Model.Expressions.IExpression;
import Model.ProgramState.PrgState;
import Model.Types.IType;
import Model.Value.IValue;
import Utilities.IDictionary;

public class AssignStmt implements Statement {
    private String id;
    private IExpression expression;

    public AssignStmt(String i, IExpression e){
        this.id = i;
        this.expression = e;
    }

    @Override
    public PrgState execute(PrgState state) throws ExceptionIllegalOperation, ExceptionDivideByZero, ExceptionValueNotFound, ExceptionHeap {
        IDictionary<String, IValue> dictionary = state.getDictionary();
        if (dictionary.containsKey(id))
        {
            IValue value = this.expression.evaluate(dictionary, state.getHeap());
            IType value_type = dictionary.getValue(id).getType();
            if (value_type.equals(value.getType()))
                dictionary.setValue(id, value);
            else
                throw new ExceptionIllegalOperation("The type of the variable does not match the right " +
                        "expression " + value.getType() + " not the same as " + value_type);
        }
        else
            throw new ExceptionIllegalOperation("Variable not declared in this scope");
        return null;
    }

    @Override
    public IDictionary<String, IType> typecheck(IDictionary<String, IType> typeEnv) throws ExceptionIllegalOperation, ExceptionValueNotFound {
        IType typevar = typeEnv.getValue(id);
        IType typexp = this.expression.typecheck(typeEnv);
        if (typevar.equals(typexp))
            return typeEnv;
        else throw new ExceptionIllegalOperation("Assignment: right hand side and left hand side have different types ");
    }

    @Override
    public String toString(){
        return id + "=" + expression + "; ";
    }
}
