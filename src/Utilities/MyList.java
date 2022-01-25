package Utilities;

import java.util.List;
import java.util.ArrayList;

public class MyList<E> implements IList<E> {
    private List <E> list = new ArrayList<>();

    @Override
    public void add(E element) {
        this.list.add(element);
    }

    @Override
    public String toString() {
        StringBuilder str_build = new StringBuilder();
        for (E element : this.list)
            str_build.append(element).append(" ");
        return str_build.toString();
    }

    @Override
    public int size() {
        return this.list.size();
    }

    @Override
    public E get(int index) {
        return this.list.get(index);
    }

    @Override
    public Iterable<E> getAll() {
        return this.list;
    }
}
