package Model.Value;

import Model.Types.IType;
import Model.Types.RefType;

public class RefValue implements IValue{
    int address;
    IType locationType;

    public RefValue(int a, IType lT){
        this.address = a;
        this.locationType = lT;
    }

    public int getAddress() {
        return address;
    }
    public IType getLocationType() {
        return locationType;
    }

    @Override
    public IType getType() {
        return new RefType(locationType);
    }

    @Override
    public String toString() {
        return "("+ address + "," + locationType.toString() +  ")";
    }
}
