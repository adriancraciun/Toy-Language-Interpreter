package Model.Statements;

import Exceptions.*;
import Model.ProgramState.PrgState;
import Model.Types.IType;
import Utilities.IDictionary;

public interface Statement {
    public PrgState execute (PrgState state) throws ExceptionIllegalOperation, ExceptionDivideByZero, ExceptionValueNotFound, ExceptionFileHandling, ExceptionHeap;
    public IDictionary<String, IType> typecheck(IDictionary<String,IType> typeEnv) throws ExceptionIllegalOperation, ExceptionValueNotFound;
}
