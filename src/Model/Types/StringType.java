package Model.Types;

import Model.Value.IValue;
import Model.Value.StringValue;

public class StringType implements IType{

    public boolean equals(Object another) {
        return another instanceof StringType;
    }

    @Override
    public IValue defaultValue() {
        return new StringValue("");
    }

    @Override
    public IType deepCopy() {
        return new StringType();
    }

    @Override
    public String toString(){
        return "String";
    }
}
