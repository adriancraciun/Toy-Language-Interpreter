package Utilities;

import java.util.Collection;
import java.util.Map;
import java.util.Set;

public interface IHeap<T> {
    int allocate(T val);
    T get(int addr);
    void put(int addr, T val);
    T deallocate(int addr);
    Map<Integer,T> getContent();
    void setContent(Map<Integer,T> content);
    boolean isDefined(int key);
}
