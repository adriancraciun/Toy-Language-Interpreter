package Utilities;

import java.util.Deque;
import java.util.ArrayDeque;

public class ExeStack<E> implements IExeStack<E> {

    private Deque<E> stack = new ArrayDeque<E>();

    @Override
    public void push(E x) {
        stack.push(x);
    }

    @Override
    public E pop() {
        return stack.pop();
    }

    @Override
    public boolean isEmpty() {
        return stack.isEmpty();
    }

    @Override
    public Iterable<E> getAll() {
        return stack;
    }

    @Override
    public String toString(){
        StringBuilder str_builder = new StringBuilder();
        for (E element : stack)
            str_builder.append(element);
        return str_builder.toString();
    }

}
