package UtilitiesTables;

import Model.Value.IValue;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;

public class SymTableView {
    private SimpleStringProperty varName;
    private SimpleStringProperty value;

    public SymTableView(String varName, IValue value) {
        this.varName = new SimpleStringProperty(varName);
        this.value = new SimpleStringProperty(value.toString());
    }

    public String getVarName() {
        return varName.get();
    }

    public SimpleStringProperty varNameProperty() {
        return varName;
    }

    public String getValue() {
        return value.get();
    }

    public SimpleStringProperty valueProperty() {
        return value;
    }

    public void setVarName(String varName) {
        this.varName.set(varName);
    }

    public void setValue(String value) {
        this.value.set(value);
    }
}