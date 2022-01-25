package Model.Types;

import Model.Value.IValue;
import Model.Value.IntValue;

public class IntType implements IType {

    public boolean equals(Object another) {
        return another instanceof IntType;
    }

    @Override
    public IValue defaultValue() {
        return new IntValue(0);
    }

    @Override
    public IType deepCopy() {
        return new IntType();
    }

    @Override
    public String toString(){
        return "int";
    }
}
