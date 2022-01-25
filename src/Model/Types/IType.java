package Model.Types;
import Model.Value.IValue;

public interface IType {
    IValue defaultValue();
    IType deepCopy();
    String toString();
}
