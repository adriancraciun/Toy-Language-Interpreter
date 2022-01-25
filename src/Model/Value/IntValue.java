package Model.Value;

import Model.Types.IType;
import Model.Types.IntType;

public class IntValue implements IValue {
    int val;
    public IntValue(int v){
        this.val = v;
    }
    int getVal(){
        return this.val;
    }

    public void setVal(int val) {
        this.val = val;
    }

    @Override
    public String toString(){
        return "" + this.val;
    }
    @Override
    public IType getType() {
        return new IntType();
    }

    public int getValue(){
        return this.val;
    }

    @Override
    public boolean equals(Object ob){
        return ob instanceof IntValue && ((IntValue) ob).val == this.val;
    }
}
