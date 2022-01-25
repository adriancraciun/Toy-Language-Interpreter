package Model.Value;

import Model.Types.BoolType;
import Model.Types.IType;

public class BoolValue implements IValue{
    boolean val;

    public BoolValue(boolean v){
        this.val = v;
    }
    boolean getVal(){
        return this.val;
    }
    @Override
    public String toString(){
        return "" + this.val;
    }
    @Override
    public IType getType() {
        return new BoolType();
    }

    public boolean getValue(){
        return this.val;
    }

    @Override
    public boolean equals(Object ob){
        return ob instanceof BoolValue && ((BoolValue) ob).val == this.val;
    }
}
