package ua.edu.sumdu.j2se.shestopalova.tasks;
import java.util.Iterator;
import java.util.Objects;
import java.util.stream.Stream;

public abstract class AbstractTaskList implements Iterable<Task>,Cloneable {



    public abstract void add(Task task);

    public abstract boolean remove(Task task);

    public abstract int size();

    public abstract Task getTask(int index);

    public abstract ListTypes.types getType();

    public abstract Iterator<Task> iterator();

    public abstract Stream<Task> getStream();


    public final AbstractTaskList incoming(int from, int to)
             throws IllegalArgumentException {
            if (from < 0 || to < 0) {
                throw new IllegalArgumentException("can not be negative");
            }
        AbstractTaskList incominTasks =
                TaskListFactory.createTaskList(getType());
        this.getStream().filter(t -> t != null && t.nextTimeAfter(from) != -1 && t.nextTimeAfter(from) < to)
                .forEach(incominTasks :: add);

           /* for (int i = 0; i < size(); i++) {
               if (getTask(i) == null) {
                   continue;
               }
               getTask(i).nextTimeAfter(from);

               if (from < getTask(i).nextTimeAfter(from) && getTask(i).nextTimeAfter(from) < to) {
                   incominTasks.add(getTask(i));*/

        return incominTasks;
    }
    @Override
    public boolean equals(Object obj) {
        return super.equals(obj);
    }

    @Override
    public int hashCode() {
        return super.hashCode();
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
