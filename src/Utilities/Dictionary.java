package Utilities;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.HashMap;
import Exceptions.ExceptionValueNotFound;

public class Dictionary<K, V> implements IDictionary<K, V>{
    private Map<K, V> map = new HashMap<>();
    @Override
    public boolean containsKey(K key) {
        return this.map.containsKey(key);
    }

    @Override
    public V getValue(K key) throws ExceptionValueNotFound {
        if (!containsKey(key))
            throw new ExceptionValueNotFound("Given Key is not part of the Dictionary");
        return map.get(key);
    }

    @Override
    public void setValue(K key, V new_Value) {
        this.map.put(key, new_Value);
    }

    @Override
    public Iterable<K> getAll() {
        return this.map.keySet();
    }

    @Override
    public void delete(K key) {
        this.map.remove(key);
    }

    @Override
    public List<V> getAllValues() throws ExceptionValueNotFound {
        List<V> list = new ArrayList<>();
        for(K key: this.getAll())
            list.add(this.getValue(key));

        return list;
    }

    @Override
    public IDictionary<K, V> copy() {
        IDictionary<K, V> symTable = new Dictionary<>();
        for(Map.Entry<K, V> e: this.map.entrySet())
            symTable.setValue(e.getKey(), e.getValue());

        return symTable;
    }

    @Override
    public String toString(){
        StringBuilder str_build = new StringBuilder();
        for (Map.Entry<K, V> element : map.entrySet())
            str_build.append("<").append(element.getKey()).append(", ").append(element.getValue()).append(">; ");
            //str_build.append(element);
        return str_build.toString();
    }
}
