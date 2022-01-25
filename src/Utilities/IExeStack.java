package Utilities;

public interface IExeStack<E>{
    public void push(E x);
    public E pop();
    public boolean isEmpty();
    public Iterable<E> getAll();
}
