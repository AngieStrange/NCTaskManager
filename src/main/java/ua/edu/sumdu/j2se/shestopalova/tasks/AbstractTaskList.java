package ua.edu.sumdu.j2se.shestopalova.tasks;
import java.util.Iterator;
import java.util.Objects;
import java.util.stream.Stream;

public abstract class AbstractTaskList implements Iterable<Task>,Cloneable {

    private int elemQuantity;

    public abstract void add(Task task);

    public abstract boolean remove(Task task);

    public abstract int size();

    public abstract Task getTask(int index);

    public abstract ListTypes.types getType();

    public abstract Iterator<Task> iterator();

    public abstract Stream<Task> getStream();


    public AbstractTaskList incoming(int from, int to) {
        AbstractTaskList incominTasks =
                TaskListFactory.createTaskList(getType());
        {
            for (int i = 0; i < size(); i++) {
                if (getTask(i) == null) {
                    continue;
                }
                getTask(i).nextTimeAfter(from);

                if (from < getTask(i).nextTimeAfter(from) && getTask(i).nextTimeAfter(from) < to) {
                    incominTasks.add(getTask(i));
                }
            }
        }
        return incominTasks;
    }
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        AbstractTaskList that = (AbstractTaskList) o;
        return elemQuantity == that.elemQuantity;
    }
    @Override
    public int hashCode() {
        return Objects.hash(elemQuantity);
    }
    @Override
    public Object clone(){
        AbstractTaskList taskListClone = TaskListFactory.createTaskList(getType());
        for (Task task : this) {
            taskListClone.add(task);
        }
        return taskListClone;
    }
}
