package Model.Statements;

import Exceptions.ExceptionDivideByZero;
import Exceptions.ExceptionIllegalOperation;
import Exceptions.ExceptionValueNotFound;
import Model.ProgramState.PrgState;
import Model.Types.*;
import Model.Value.IValue;
import Utilities.IDictionary;

public class VariableDeclarationStatement implements Statement {
    private String name;
    private IType type;

    public VariableDeclarationStatement(String n, IType t){
        this.name = n;
        this.type = t;
    }

    @Override
    public PrgState execute(PrgState state) throws ExceptionIllegalOperation, ExceptionDivideByZero, ExceptionValueNotFound {
        IDictionary<String, IValue> dictionary = state.getDictionary();
        if(dictionary.containsKey(this.name))
            throw new ExceptionIllegalOperation("Variable was declared before in this scope");
        else
        {
            IValue value = this.type.defaultValue();
            dictionary.setValue(this.name, value);
        }
        return null;
    }

    @Override
    public IDictionary<String, IType> typecheck(IDictionary<String, IType> typeEnv) throws ExceptionIllegalOperation, ExceptionValueNotFound {
        typeEnv.setValue(this.name, this.type);
        return typeEnv;
    }

    @Override
    public String toString(){
        return "" + type + ' ' + name + "; ";
    }
}
