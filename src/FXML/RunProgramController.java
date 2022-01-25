package FXML;

import Exceptions.ExceptionValueNotFound;
import Model.Value.IValue;
import Model.Value.StringValue;
import UtilitiesTables.*;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;
import Model.ProgramState.PrgState;
import Model.Statements.*;
import Repository.*;
import Utilities.*;
import Controller.*;

import java.io.BufferedReader;

public class RunProgramController {
    //HEAP TABLE
    @FXML private TableView<HeapTableView> heapTable;
    @FXML private TableColumn<HeapTableView, Integer> heapAddressColumn;
    @FXML private TableColumn<HeapTableView, Integer> heapValueColumn;

    //FILE TABLE
    @FXML private ListView<String> fileTable;

    //SYM TABLE
    @FXML private TableView<SymTableView> symTable;
    @FXML private TableColumn<SymTableView, String> symTableVarName;
    @FXML private TableColumn<SymTableView, Integer> symTableValue;

    @FXML private ListView<String> outList;
    @FXML private ListView<String> exeStack;
    @FXML private ListView<String> prgStateIdentifiers;
    @FXML private Button oneStepBTN;
    @FXML private TextField noPrgStateTextField;

    private Controller ctrl;

    @FXML
    public void initialize(){

        IExeStack<Statement> stack = new ExeStack<>();
        IDictionary<String, IValue> dictionary = new Dictionary<>();
        IList<IValue> list = new MyList<>();
        IDictionary<StringValue, BufferedReader> fileTable = new Dictionary<>();
        IHeap<IValue> heap = new Heap<>();

        PrgState prgState = new PrgState(stack, dictionary, list, SelectProgramController.statement,
                fileTable, heap, GeneratorForkId.getId());

        IRepository repository = new Repository(prgState, "fork.txt");
        ctrl = new Controller(repository);


        this.heapAddressColumn.setCellValueFactory(new PropertyValueFactory<>("address"));
        this.heapValueColumn.setCellValueFactory(new PropertyValueFactory<>("value"));
        this.symTableVarName.setCellValueFactory(new PropertyValueFactory<>("varName"));
        this.symTableValue.setCellValueFactory(new PropertyValueFactory<>("value"));


        setNoPrgState();
        setPrgStateIdentifiers();
        this.prgStateIdentifiers.getSelectionModel().select(0);
        setExeStack();
    }



    private void setNoPrgState(){
        Integer nr = this.ctrl.noPrgStates();
        this.noPrgStateTextField.setText(String.valueOf(nr));
    }

    private void setPrgStateIdentifiers(){
        this.prgStateIdentifiers.setItems(ctrl.getPrgStatesID());
    }

    PrgState getCurrentProgramState(){
        int index = this.prgStateIdentifiers.getSelectionModel().getSelectedIndex();
        if(index == -1)
            index = 0;
        return ctrl.getPrgStateByIndex(index);
    }

    private void setExeStack(){

        ObservableList<String> list = FXCollections.observableArrayList();
        PrgState programState = getCurrentProgramState();

        for(Statement i : programState.getStack().getAll())
            list.add("" + i);

        this.exeStack.setItems(list);
    }

    private void setHeapTable(){
        ObservableList<HeapTableView> list = FXCollections.observableArrayList();
        PrgState programState = getCurrentProgramState(); // here we don't need to get current because heap is shared by all
        // but i used to don't make another function to get one programState

        for(Integer key: programState.getHeap().getContent().keySet())
            list.add(new HeapTableView(key, programState.getHeap().get(key)));

        this.heapTable.setItems(list);
    }

    private void setFileTable() throws ExceptionValueNotFound {

        ObservableList<String> list = FXCollections.observableArrayList();
        PrgState programState = getCurrentProgramState(); // here we don't need to get current because fileTable is shared by all
        // but i used to don't make another function to get one programState

        for(StringValue key: programState.getFileTable().getAll())
            list.add("" + key + " --> " + programState.getFileTable().getValue(key));

        this.fileTable.setItems(list);
    }

    private void setSymTable() throws ExceptionValueNotFound {
        ObservableList<SymTableView> list = FXCollections.observableArrayList();
        PrgState programState = getCurrentProgramState();

        for(String key: programState.getDictionary().getAll())
            list.add(new SymTableView(key, programState.getDictionary().getValue(key)));

        symTable.setItems(list);
    }

    private void setOutList(){
        ObservableList<String> list = FXCollections.observableArrayList();
        PrgState programState = getCurrentProgramState();   // they all share the same outList

        for(IValue i: programState.getList().getAll())
            list.add(""+i);

        outList.setItems(list);
    }


    private void setAll() throws ExceptionValueNotFound {
        setNoPrgState();
        setPrgStateIdentifiers();
        setExeStack();
        setHeapTable();
        setFileTable();
        setSymTable();
        setOutList();
    }



    public void executeOneStep(ActionEvent ae){

        try {
            if (this.ctrl.oneStepGUI()) {
                setAll();
            }
            else {
                setNoPrgState();
                setPrgStateIdentifiers();
            }
        }
        catch (RuntimeException | InterruptedException | ExceptionValueNotFound e){
            Node source = (Node) ae.getSource();
            Stage theStage = (Stage) source.getScene().getWindow();
            Alert a = new Alert(Alert.AlertType.ERROR, e.getMessage());
            a.show();
            theStage.close();
        }
    }

    public void mouseClickPrgStateIdentifiers() throws ExceptionValueNotFound {

        if(ctrl.noPrgStates() > 0)
            setAll();
    }
}
