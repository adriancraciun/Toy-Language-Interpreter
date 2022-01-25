package Utilities;

import Exceptions.ExceptionValueNotFound;

import java.util.List;

public interface IDictionary <K, V>{
    public boolean containsKey(K key);
    public V getValue(K key) throws ExceptionValueNotFound;
    public void setValue(K key, V new_Value);
    public Iterable<K> getAll();
    public void delete(K key);
    public List<V> getAllValues() throws ExceptionValueNotFound;
    public IDictionary<K,V> copy();
}
