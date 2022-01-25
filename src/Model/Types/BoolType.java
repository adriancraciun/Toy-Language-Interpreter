package Model.Types;

import Model.Value.BoolValue;
import Model.Value.IValue;

public class BoolType implements IType{

    public boolean equals(Object another) {
        return another instanceof BoolType;
    }

    @Override
    public IValue defaultValue() {
        return new BoolValue(false);
    }

    @Override
    public IType deepCopy() {
        return new BoolType();
    }

    @Override
    public String toString(){
        return "bool";
    }
}
