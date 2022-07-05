package ua.edu.sumdu.j2se.shestopalova.tasks;

import java.util.Iterator;
import java.util.stream.Stream;
import java.util.Objects;

public class ArrayTaskList extends AbstractTaskList  {

    private Task[] taskArray = new Task[10];

    private int size = 0;

@Override
   public void add(Task task) throws IllegalArgumentException{

        if (size >= taskArray.length) {
            Task[] tempArr = new Task[taskArray.length+5];
            System.arraycopy(taskArray, 0, tempArr, 0, taskArray.length);
            taskArray=tempArr;
        }
        taskArray[size] = task;
        size++;

       if(task == null) throw new IllegalArgumentException("Task can't be null");
    }
@Override
    public boolean remove(Task task) {

        if (task != null) {
            for (int i = 0; i < taskArray.length; i++) {
                if (taskArray[i].equals(task)) {
                    System.arraycopy(taskArray, i + 1, taskArray, i, taskArray.length - 1 - i);
                    size--;
                    return true;
                }
            }
        }
        return false;
   }
    @Override
    public int size(){
        return size;
    }
    @Override
    public Task getTask(int index) throws IndexOutOfBoundsException {
        if(index < 0 || index >= size) throw new IndexOutOfBoundsException("Index is out of range for the list");
        return taskArray[index];
    }

    public ListTypes.types getType() {
        return ListTypes.types.ARRAY;
    }
/* @Override
   public ArrayTaskList incoming(int from, int to){
       ArrayTaskList incomTasks = new ArrayTaskList();

       for (Task task : taskArray) {
           if (task == null) {
               continue;
           }
           task.nextTimeAfter(from);

           if (from < task.nextTimeAfter(from) && task.nextTimeAfter(from) < to) {
               incomTasks.add(task);
           }
       }
       return incomTasks;
   }*/

    @Override
    public Iterator<Task> iterator() {
        return new Iterator<Task>() {
            private int index = 0;

            @Override
            public boolean hasNext() {
                return index < size;
            }

            @Override
            public Task next() {
                return taskArray[index++];
            }

            @Override
            public void remove() {
                if(index == 0 ) throw new IllegalStateException();
                ArrayTaskList.this.remove(taskArray[--index]);
            }
        };
    }

    @Override
    public String toString() {
        Iterator<Task> taskIterator = this.iterator();
        StringBuilder string = new StringBuilder("ArrayTaskList{");
        while (taskIterator.hasNext()) {
            string.append(taskIterator.next().toString()).append(", ");
        }
        return string.append("size=").append(size).append('}').toString();
    }

    public Stream<Task> getStream(){
        return Stream.of(this.taskArray);
    }
    @Override
    public boolean equals(Object obj) {
        ArrayTaskList arr = (ArrayTaskList) obj;
        if (this == obj) return true;
        if ( obj == null || getClass() != obj.getClass()) return false;
        for (int i = 0; i< size; i++) {
            if (!arr.taskArray[i].equals(taskArray[i])){
                return  false;
            }
        }
        return this.getType() == arr.getType() && size == arr.size ;
    }
    @Override
    public int hashCode() {
        return Objects.hash(this.getType(),size);
    }
   @Override
    public ArrayTaskList clone() throws CloneNotSupportedException {
        ArrayTaskList cloned = (ArrayTaskList) super.clone();
        cloned.taskArray = taskArray.clone();
        return cloned;
    }

}
