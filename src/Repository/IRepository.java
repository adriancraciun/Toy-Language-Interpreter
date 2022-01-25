package Repository;
import Exceptions.ExceptionFileHandling;
import Model.ProgramState.PrgState;
import java.util.ArrayList;
import java.util.List;

public interface IRepository {
    public void addProgramState(PrgState prgState);
    public void logPrgStateExec(PrgState prgState) throws ExceptionFileHandling;
    public List<PrgState> getPrgList();
    public void setPrgList(List<PrgState> list);
}
