package Utilities;

public interface IList<E>{
    public void add(E element);
    int size();
    E get(int index);
    public Iterable<E> getAll();
}
