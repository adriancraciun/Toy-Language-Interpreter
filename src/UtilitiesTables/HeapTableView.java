package UtilitiesTables;

import Model.Value.IValue;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;

public class HeapTableView {
    private SimpleStringProperty address, value;

    public HeapTableView(Integer address, IValue value) {
        this.address = new SimpleStringProperty(address.toString());
        this.value = new SimpleStringProperty(value.toString());
    }

    public void setAddress(Integer address) {
        this.address.set(address.toString());
    }

    public void setValue(IValue value) {
        this.value.set(value.toString());
    }

    public SimpleStringProperty addressProperty() {
        return address;
    }

    public SimpleStringProperty getValue() {
        return value;
    }

    public SimpleStringProperty valueProperty() {
        return value;
    }
}