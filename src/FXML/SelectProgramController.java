package FXML;

import Controller.Controller;
import Exceptions.ExceptionIllegalOperation;
import Exceptions.ExceptionValueNotFound;
import Model.ProgramState.PrgState;
import Model.Types.*;
import Model.Value.BoolValue;
import Model.Value.IValue;
import Model.Value.IntValue;
import Model.Value.StringValue;
import Repository.IRepository;
import Repository.Repository;
import Utilities.*;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ListView;
import javafx.stage.Modality;
import javafx.stage.Stage;
import Model.Expressions.*;
import Model.FileHandling.*;
import Model.HeapStatements.*;
import Model.Statements.*;

import java.io.BufferedReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;


public class SelectProgramController {

    @FXML
    private ListView<String> listView;

    @FXML
    private Button runProgramBTN;

    public static Statement statement;
    private List<Statement> StmtList = new ArrayList<Statement>();


    @FXML
    public void initialize() {

        Statement ex1 =
                new CompStmt(new VariableDeclarationStatement("v", new IntType()),
                        new CompStmt(new AssignStmt("v", new ValueExp(new BoolValue(true))),
                                new PrintStmt(new VarExp("v"))));

        Statement ex2 = new CompStmt(new VariableDeclarationStatement("a", new IntType()),
                new CompStmt(new VariableDeclarationStatement("b", new IntType()),
                        new CompStmt(new AssignStmt("a", new ArithExp('+', new ValueExp(new IntValue(2)),
                                new ArithExp('*', new ValueExp(new IntValue(3)), new ValueExp(new IntValue(5))))),
                                new CompStmt(new AssignStmt("b",
                                        new ArithExp('+', new VarExp("a"), new ValueExp(new IntValue(1)))),
                                        new PrintStmt(new VarExp("b"))))));

        Statement ex3 =
                new CompStmt(new VariableDeclarationStatement("a", new BoolType()),
                        new CompStmt(new VariableDeclarationStatement("v", new IntType()),
                                new CompStmt(new AssignStmt("a", new ValueExp(new BoolValue(true))),
                                        new CompStmt(new IfStmt(new VarExp("a"), new AssignStmt("v", new ValueExp(new IntValue(2))),
                                                new AssignStmt("v", new ValueExp(new IntValue(3)))), new PrintStmt(new VarExp("v"))))));

        Statement ex4 =
                new CompStmt( new VariableDeclarationStatement("varf",new StringType()),
                        new CompStmt(new AssignStmt("varf",new ValueExp(new StringValue("test.in"))),
                                new CompStmt(new OpenRFile(new VarExp("varf")),
                                        new CompStmt(new VariableDeclarationStatement("varc",new IntType()),
                                                new CompStmt(new ReadFile(new VarExp("varf"),"varc"),
                                                        new CompStmt(new PrintStmt(new VarExp("varc")),
                                                                new CompStmt(new ReadFile(new VarExp("varf"),"varc"),
                                                                        new CompStmt(new PrintStmt(new VarExp("varc")),
                                                                                new CloseRFile(new VarExp("varf"))))))))));


        // Ref int v;new(v,20);Ref Ref int a; new(a,v);print(v);print(a)

        Statement ex5 = new CompStmt(new VariableDeclarationStatement("v", new RefType(new IntType())),
                new CompStmt(new HeapAllocation("v", new ValueExp(new IntValue(20))),
                        new CompStmt(new VariableDeclarationStatement("a", new RefType(new RefType(new IntType()))),
                                new CompStmt(new HeapAllocation("a", new VarExp("v")),
                                        new CompStmt(new PrintStmt(new VarExp("v")),
                                                new PrintStmt(new VarExp("a")))))));


        // Ref int v;new(v,20);Ref Ref int a; new(a,v);print(rH(v));print(rH(rH(a))+5)
        Statement ex6 = new CompStmt(new VariableDeclarationStatement("v", new RefType(new IntType())),
                new CompStmt(new HeapAllocation("v", new ValueExp(new IntValue(20))),
                        new CompStmt(new VariableDeclarationStatement("a", new RefType(new RefType(new IntType()))),
                                new CompStmt(new HeapAllocation("a", new VarExp("v")),
                                        new CompStmt(new PrintStmt(new HeapReading(new VarExp("v"))),
                                                new PrintStmt(new ArithExp('+', new HeapReading(new HeapReading(new VarExp("a"))), new ValueExp(new IntValue(5)))))))));


        // Ref int v;new(v,20);print(rH(v)); wH(v,30);print(rH(v)+5);
        Statement ex7 = new CompStmt(new VariableDeclarationStatement("v", new RefType(new IntType())),
                new CompStmt(new HeapAllocation("v", new ValueExp(new IntValue(20))),
                        new CompStmt(new PrintStmt(new HeapReading(new VarExp("v"))),
                                new CompStmt(new HeapWriting("v", new ValueExp(new IntValue(30))),
                                        new PrintStmt(new ArithExp('+', new HeapReading(new VarExp("v")), new ValueExp(new IntValue(5))))))));


        // int v; v=4; (while (v>0) print(v);v=v-1);print(v)
        Statement ex8 = new CompStmt(new VariableDeclarationStatement("v", new IntType()),
                new CompStmt(new AssignStmt("v", new ValueExp(new IntValue(4))),
                        new CompStmt(new WhileStmt(new RelationalExpression(new VarExp("v"), new ValueExp(new IntValue(0)), ">"),
                                new CompStmt(
                                        new PrintStmt(new VarExp("v")),
                                        new AssignStmt("v", new ArithExp('-', new VarExp("v"), new ValueExp(new IntValue(1))))
                                )
                        ),
                                new PrintStmt(new VarExp("v")))));


        // Ref int v;new(v,20);Ref Ref int a; new(a,v); new(v,30);print(rH(rH(a)))
        Statement ex9 = new CompStmt(new VariableDeclarationStatement("v", new RefType(new IntType())),
                new CompStmt(new HeapAllocation("v", new ValueExp(new IntValue(20))),
                        new CompStmt(new VariableDeclarationStatement("a", new RefType(new RefType(new IntType()))),
                                new CompStmt(new HeapAllocation("a", new VarExp("v")),
                                        new CompStmt(new HeapAllocation("v", new ValueExp(new IntValue(30))),
                                                new PrintStmt(new HeapReading(new HeapReading(new VarExp("a")))))))));


        /*
            int v; Ref int a; v=10; new(a,22);
            fork( wH(a,30); v=32; print(v); print(rH(a)) );
            print(v); print(rH(a))
         */

        Statement a1 = new VariableDeclarationStatement("v", new IntType());
        Statement a2 = new VariableDeclarationStatement("a", new RefType(new IntType()));
        Statement a3 = new AssignStmt("v", new ValueExp(new IntValue(10)));
        Statement a4 = new HeapAllocation("a", new ValueExp(new IntValue(22)));
        Statement a = new CompStmt(a1, new CompStmt(a2, new CompStmt(a3, a4)));

        Statement f1 = new HeapWriting("a", new ValueExp(new IntValue(30)));
        Statement f2 = new AssignStmt("v", new ValueExp(new IntValue(32)));
        Statement f3 = new PrintStmt(new VarExp("v"));
        Statement f4 = new PrintStmt(new HeapReading(new VarExp("a")));

        Statement forkStatement = new ForkStmt(
                new CompStmt(f1, new CompStmt(f2, new CompStmt(f3, f4)))
        );

        Statement b1 = new PrintStmt(new VarExp("v"));
        Statement b2 = new PrintStmt(new HeapReading(new VarExp("a")));
        Statement b = new CompStmt(b1, b2);

        Statement ex = new CompStmt(a, new CompStmt(forkStatement, b));

        /// !!!!!!!!!!!!!!!!!!!!!!!!! statement.typecheck(typeEnv)
        IDictionary<String, IType> typeEnv1 = new Dictionary<String, IType>();

        try {
            ex.typecheck(typeEnv1);
            ex1.typecheck(typeEnv1);
            ex2.typecheck(typeEnv1);
            ex3.typecheck(typeEnv1);
            ex4.typecheck(typeEnv1);
            ex5.typecheck(typeEnv1);
            ex6.typecheck(typeEnv1);
            ex7.typecheck(typeEnv1);
            ex8.typecheck(typeEnv1);
            ex9.typecheck(typeEnv1);
        } catch (ExceptionIllegalOperation | ExceptionValueNotFound exceptionIllegalOperation) {
            System.out.println(exceptionIllegalOperation.getMessage());
        }

        StmtList.add(ex);
        StmtList.add(ex1);
        StmtList.add(ex2);
        StmtList.add(ex3);
        StmtList.add(ex4);
        StmtList.add(ex5);
        StmtList.add(ex6);
        StmtList.add(ex7);
        StmtList.add(ex8);
        StmtList.add(ex9);


        ObservableList<String> list = FXCollections.observableArrayList();
        for(Statement i : StmtList)
            list.add(""+i);

        listView.setItems(list);
        listView.getSelectionModel().selectIndices(0);
    }

    @FXML
    public void buttonRun() throws IOException {

        statement = StmtList.get(listView.getSelectionModel().getSelectedIndex());

        Stage stage = new Stage();
        stage.setTitle("Running Program");
        FXMLLoader fxmlLoader = new FXMLLoader(Main.class.getResource("runProgram.fxml"));
        stage.setScene(new Scene(fxmlLoader.load(), 800, 600));
        stage.initModality(Modality.APPLICATION_MODAL); // blocks other windows' events
        stage.show();
    }
}