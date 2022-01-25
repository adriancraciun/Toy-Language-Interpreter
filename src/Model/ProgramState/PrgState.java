package Model.ProgramState;
import Exceptions.*;
import Model.Statements.Statement;
import Model.Value.*;
import Utilities.*;

import java.io.*;

public class PrgState {
    private IExeStack<Statement> stack;
    private IDictionary<String, IValue> dictionary;
    private IList<IValue> list;
    private Statement root;
    private IDictionary<StringValue, BufferedReader> fileTable;
    private IHeap<IValue> heap;
    private int id;

    /*
    public ProgramState(IStack<IStatement> s, IDictionary<String, IValue> d, ICustomList<IValue> l, IStatement r,
                        IFileTable<IValue, FileData> f){
        this.stack = s;
        this.dictionary = d;
        this.list = l;
        this.root = r;
        this.fileTable = f;

        this.stack.push(this.root);
    }
    */


    public PrgState(IExeStack<Statement> s, IDictionary<String, IValue> d, IList<IValue> l, Statement r,
                    IDictionary<StringValue, BufferedReader> fT, IHeap<IValue> h, int i) {
        this.stack = s;
        this.dictionary = d;
        this.list = l;
        this.root = r;
        this.fileTable = fT;
        this.heap = h;
        this.id = i;

        this.stack.push(this.root);
    }

    public IDictionary<StringValue, BufferedReader> getFileTable() {
        return fileTable;
    }
    public IExeStack<Statement> getStack(){
        return this.stack;
    }
    public IDictionary<String, IValue> getDictionary(){
        return this.dictionary;
    }
    public IList<IValue> getList(){
        return this.list;
    }
    public Statement getRoot(){
        return this.root;
    }
    public IHeap<IValue> getHeap() {
        return heap;
    }
    public int getId() {
        return id;
    }

    public void setStack(IExeStack<Statement> s){
        this.stack = s;
    }
    public void setDictionary(IDictionary<String, IValue> d){
        this.dictionary = d;
    }
    public void setList(IList<IValue> l){
        this.list = l;
    }
    public void setRoot(Statement r){
        this.root = r;
    }
    public void setFileTable(IDictionary<StringValue, BufferedReader> fileTable) {
        this.fileTable = fileTable;
    }
    public void setHeap(IHeap<IValue> heap) {
        this.heap = heap;
    }
    public void setId(int id) {
        this.id = id;
    }

    public Boolean isNotCompleted(){
        return !this.stack.isEmpty();
    }

    public PrgState oneStep() throws ExceptionEmptyStack, ExceptionIllegalOperation, ExceptionDivideByZero, ExceptionValueNotFound, ExceptionFileHandling, ExceptionHeap {
        if (this.stack.isEmpty())
            throw new ExceptionEmptyStack("Attempted oneStep with empty stack.");

        Statement statement = stack.pop();
        return statement.execute(this);
    }

    @Override
    public String toString() {

        StringBuilder str = new StringBuilder();

        str.append("\nExeStack: ").append(this.id);
        for(Statement statement: stack.getAll())
            str.append(statement);

        str.append("\nSymTable:\n");
        for(String key: this.dictionary.getAll()) {
            try {
                str.append(key).append(" --> ").append(this.dictionary.getValue(key)).append("\n");
            }
            catch (ExceptionValueNotFound e) {
                e.printStackTrace();
            }
        }

        str.append("\nOut:\n");
        for(IValue value: this.list.getAll())
            str.append(value).append("\n");

        str.append("\nFileTable:\n");
        for(StringValue i: fileTable.getAll()) {
            try {
                str.append(i).append(" ").append(fileTable.getValue(i)).append("\n");
            } catch (ExceptionValueNotFound e) {
                e.printStackTrace();
            }
        }

        str.append("\nHeap:\n");
        for(int i : heap.getContent().keySet())
            str.append(i).append(" --> ").append(heap.get(i)).append("\n");
        str.append("\n");

        return str.toString();
    }
}

