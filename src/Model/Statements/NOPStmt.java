package Model.Statements;

import Exceptions.ExceptionDivideByZero;
import Exceptions.ExceptionIllegalOperation;
import Exceptions.ExceptionValueNotFound;
import Model.ProgramState.*;
import Model.Types.IType;
import Utilities.IDictionary;

public class NOPStmt implements Statement {
    @Override
    public String toString(){
        return "No operation";
    }

    @Override
    public PrgState execute(PrgState state) throws ExceptionIllegalOperation, ExceptionDivideByZero, ExceptionValueNotFound {
        return null;
    }

    @Override
    public IDictionary<String, IType> typecheck(IDictionary<String, IType> typeEnv) throws ExceptionIllegalOperation, ExceptionValueNotFound {
        return null;
    }
}
