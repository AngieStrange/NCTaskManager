package ua.edu.sumdu.j2se.shestopalova.tasks;

public class ArrayTaskList {

    private Task[] taskArray = new Task[10];

    private int size = 0;


   public void add(Task task){

        if (size >= taskArray.length) {
            Task[] tempArr = new Task[taskArray.length+5];
            System.arraycopy(taskArray, 0, tempArr, 0, taskArray.length);
            taskArray=tempArr;
        }
        taskArray[size] = task;
        size++;
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

   public Task getTask(int index){
       if ((index >= size) && (index < 0)) {
           return null;                             //тут краще зробити ексепшн
       }
       return taskArray[index];
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
