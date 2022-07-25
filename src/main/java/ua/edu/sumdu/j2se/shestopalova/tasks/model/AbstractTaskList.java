package ua.edu.sumdu.j2se.shestopalova.tasks.model;

import java.util.Iterator;
import java.util.stream.Stream;
import java.io.Serializable;

public abstract class AbstractTaskList implements Iterable<Task>,Cloneable,Serializable {

    public abstract void add(Task task);

    public abstract boolean remove(Task task);

    public abstract int size();

    public abstract Task getTask(int index);

    public abstract ListTypes.types getType();

    public abstract Iterator<Task> iterator();

    public abstract Stream<Task> getStream();

    @Override
    public boolean equals(Object obj) {
        return super.equals(obj);
    }

    @Override
    public int hashCode() {
        return super.hashCode();
    }

    @Override
    protected Object clone() throws CloneNotSupportedException {
        return super.clone();
    }

}
