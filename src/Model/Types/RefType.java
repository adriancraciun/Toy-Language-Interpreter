package Model.Types;

import Model.Value.IValue;
import Model.Value.IntValue;
import Model.Value.RefValue;

public class RefType implements IType{
    IType inner;
    public RefType(IType i){
        this.inner = i;
    }

    public IType getInner(){
        return this.inner;
    }

    @Override
    public IValue defaultValue() {
        return new RefValue(0, inner);
    }

    @Override
    public boolean equals(Object another){
        if (another instanceof  RefType)
            return inner.equals(((RefType) another).getInner());
        else
            return false;
    }

    @Override
    public IType deepCopy() {
        return new RefType(inner.deepCopy());
    }

    @Override
    public String toString(){
        return "Ref(" + inner.toString() + ")";
    }
}
