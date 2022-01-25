package Controller;
import Exceptions.*;
import Model.ProgramState.PrgState;
import Model.Value.IValue;
import Model.Value.RefValue;
import Repository.*;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.stream.Collectors;


public class Controller {
    private IRepository repo;
    private ExecutorService executor;
    public Controller(IRepository r){
        this.repo = r;
    }

    public int noPrgStates(){
        return repo.getPrgList().size();
    }

    public PrgState getPrgStateByIndex(int index){
        return  repo.getPrgList().get(index);
    }

    public ObservableList<String> getPrgStatesID(){
        ObservableList<String> list = FXCollections.observableArrayList();
        for(PrgState i : repo.getPrgList())
            list.add( String.valueOf(i.getId()));

        return list;
    }

    public List<PrgState> removeCompletedPrg(List<PrgState> inPrgList){
        return inPrgList.stream().filter(p -> p.isNotCompleted())
                                 .collect(Collectors.toList());
    }

    void oneStepForAllPrg(List<PrgState> prgList) throws InterruptedException {
        List<Callable<PrgState>> callList = prgList.stream()
                .map((PrgState p) -> (Callable<PrgState>) (() -> {
                    return p.oneStep();
                }))
                .collect(Collectors.toList());

        List<PrgState> newPrgList = executor.invokeAll(callList).stream()
                .map(future ->
                {
                    try {
                        return future.get();
                    } catch (ExecutionException | InterruptedException e) {
                        throw new ExceptionController(e.getMessage());
                    }
                })
                .filter(p -> p != null)
                .collect(Collectors.toList());

        prgList.addAll(newPrgList);
        prgList.forEach(prg -> {
            try {
                this.repo.logPrgStateExec(prg);
            }
            catch (ExceptionFileHandling e) {
                e.printStackTrace();
            }
        });
        this.repo.setPrgList(prgList);
    }

    public void allStep() throws InterruptedException {
        this.executor = Executors.newFixedThreadPool(2);
        List<PrgState> prgList = removeCompletedPrg(this.repo.getPrgList());
        while(prgList.size() > 0){
            oneStepForAllPrg(prgList);
            prgList = removeCompletedPrg(this.repo.getPrgList());
        }
        this.executor.shutdownNow();
        this.repo.setPrgList(prgList);
    }

    public boolean oneStepGUI () throws InterruptedException {
        this.executor = Executors.newFixedThreadPool(2);
        List<PrgState> prgList = removeCompletedPrg(this.repo.getPrgList());

        if(prgList.size() > 0){
            oneStepForAllPrg(prgList);
            prgList = removeCompletedPrg(repo.getPrgList());
            return true;
        }
        else{
            executor.shutdownNow();
            repo.setPrgList(prgList);
            return false;
        }
    }

    public void addProgram(PrgState prgState){
        this.repo.addProgramState(prgState);
    }

    /*
    Map<Integer, IValue> unsafeGarbageCollector(List<Integer> symTableAddr, Map<Integer,IValue> heap)
    {
        return heap.entrySet().stream().filter(e->symTableAddr.contains(e.getKey()))
                .collect(Collectors.toMap(Map.Entry::getKey, Map.Entry::getValue));
    }

    List<Integer> getAddrFromSymTable(Collection<IValue> symTableValues, Collection<IValue> heap){
        return Stream.concat(
                        heap.stream().filter(v-> v instanceof RefValue)
                            .map(v-> {RefValue v1 = (RefValue)v; return v1.getAddress();}),
                        symTableValues.stream().filter(v-> v instanceof RefValue)
                            .map(v-> {RefValue v1 = (RefValue)v; return v1.getAddress();})
                ).collect(Collectors.toList());
    }
    */

    private List<Integer> get_used_addresses(Collection<IValue> symbols_table_values, Collection<IValue> heap_table_values)
    {
        List<Integer> symbols_table_addresses = symbols_table_values.stream()
                .filter(v -> v instanceof RefValue)
                .map(value->{RefValue value2 = (RefValue) value;
                    return value2.getAddress();})
                .collect(Collectors.toList());

        List<Integer> heap_table_addresses = heap_table_values.stream()
                .filter(v -> v instanceof RefValue)
                .map(value->{RefValue value2 = (RefValue) value;
                    return value2.getAddress();})
                .collect(Collectors.toList());

        symbols_table_addresses.addAll(heap_table_addresses);
        return symbols_table_addresses;
    }

    private Map<Integer, IValue> garbage_collector(List<Integer> used_addresses, Map<Integer, IValue> heap)
    {
        return heap.entrySet().stream()
                .filter(e -> used_addresses.contains(e.getKey()))
                .collect(Collectors.toMap(Map.Entry::getKey, Map.Entry::getValue));
    }
}
