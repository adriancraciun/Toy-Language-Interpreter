package Model.Value;

import Model.Types.IType;
import Model.Types.StringType;

public class StringValue implements IValue{
    String string;
    public StringValue(String s){
        this.string = s;
    }

    @Override
    public IType getType() {
        return new StringType();
    }

    public String getString(){
        return this.string;
    }

    @Override
    public String toString(){
        return "" + this.string;
    }

    @Override
    public boolean equals(Object ob){
        return ob instanceof StringValue && ((StringValue) ob).string == this.string;
    }
}
