package ua.edu.sumdu.j2se.shestopalova.tasks;

public abstract class AbstractTaskList {

    public abstract void add(Task task);

    public abstract boolean remove(Task task);

    public abstract int size();

    public abstract Task getTask(int index);

    public abstract ListTypes.types getType();

    public AbstractTaskList incoming(int from, int to) {
        AbstractTaskList incominTasks =
                TaskListFactory.createTaskList(this.getType());
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
}
