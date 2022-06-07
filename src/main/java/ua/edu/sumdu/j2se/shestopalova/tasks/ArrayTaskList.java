package ua.edu.sumdu.j2se.shestopalova.tasks;

public class ArrayTaskList extends AbstractTaskList {

    private Task[] taskArray = new Task[10];

    private int size = 0;


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

    public int size(){
        return size;
    }

   public Task getTask(int index) throws IndexOutOfBoundsException{
       if ((index >= size) && (index < 0)) {
           throw new IndexOutOfBoundsException("There is no task with such index");
       }
       return taskArray[index];

    }

    public ListTypes.types getType() {
        return ListTypes.types.ARRAY;
    }

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
   }

}
