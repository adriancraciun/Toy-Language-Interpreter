package Utilities;

import java.util.HashMap;
import java.util.Map;

public class Heap<T> implements IHeap<T> {
    private HashMap<Integer, T> map;
    private int memory;

    public Heap() {
        this.map = new HashMap<Integer,T>();
        this.memory = 0;
    }

    @Override
    public synchronized int allocate(Object val) {
        this.memory++;
        this.map.put(this.memory, (T)val);
        return this.memory;
    }

    @Override
    public T get(int address) {
        return this.map.get(address);
    }

    @Override
    public synchronized void put(int address, Object val) {
        this.map.put(address, (T)val);
    }

    @Override
    public synchronized T deallocate(int address) {
        return this.map.remove(address);
    }

    @Override
    public Map<Integer, T> getContent() {
        return map;
    }

    @Override
    public void setContent(Map<Integer, T> content) {
        this.map = (HashMap<Integer, T>)content;
    }

    @Override
    public String toString() {
        StringBuilder stringBuilder = new StringBuilder("{");
        for(HashMap.Entry<Integer,T> element : this.map.entrySet())
            stringBuilder.append(element.getKey().toString()).append("->").append(element.getValue().toString()).append("\n");

        stringBuilder.append("}");
        return stringBuilder.toString();
    }

    @Override
    public boolean isDefined(int key){
        return this.map.containsKey(key);
    }
}
