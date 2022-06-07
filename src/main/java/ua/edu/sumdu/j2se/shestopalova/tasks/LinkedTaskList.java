package ua.edu.sumdu.j2se.shestopalova.tasks;


public class LinkedTaskList extends AbstractTaskList {
    private int listSize = 0;
    private Node headNode;

    private static class Node {
        private Node prev;
        private Node next;
        private Task task;

        public Node(Task task) throws IllegalArgumentException {
            if (task == null) {
                throw new IllegalArgumentException();
            }
            this.task = task;
            Node prev = null;
            Node next = null;
        }
    }

    public void add(Task task) throws NullPointerException {
        if (task == null) {
            throw new NullPointerException("task must not be null");
        }

        Node newNode = new Node(task);
        if (headNode != null) {
            newNode.next = headNode;
            headNode.prev = newNode;
        }
        headNode = newNode;
        listSize++;

    }

    public boolean remove(Task task) throws NullPointerException {
        if (task == null) {
            throw new NullPointerException("The task cannot be null");
        }
        Node current = headNode;
        Node previous = null;
        while (current != null) {
            if (current.task.equals(task)) {
                if (current.equals(headNode)) {
                    headNode = current.next;
                    listSize--;
                    return true;
                }
                previous.next.prev = current.prev;
                previous.next = current.next;
                listSize--;
                return true;
            }
            previous = current;
            current = current.next;

        }
        return false;
    }

    public int size() {
        return listSize;
    }
    public Task getTask(int index) throws IndexOutOfBoundsException,IllegalArgumentException{
        if ((index >= listSize || index < 0)) {
            throw new IndexOutOfBoundsException("There is no task with such index");
        }
        if (headNode == null) {
            throw new IllegalArgumentException("List is empty");
        }
        Node current = headNode;
        int counter = 0;

        while (current != null) {
            if (counter == index) {
                return current.task;
            }
            current = current.next;
            counter++;
        }
        return null;

    }
    public ListTypes.types getType() {
        return ListTypes.types.LINKED;
    }

    public LinkedTaskList incoming(int from, int to){
        LinkedTaskList incominTasks = new LinkedTaskList();

        for (int i=0; i < listSize; i++) {
            if (getTask(i) == null) {
                continue;
            }
            getTask(i).nextTimeAfter(from);

            if (from < getTask(i).nextTimeAfter(from) && getTask(i).nextTimeAfter(from) < to) {
                incominTasks.add(getTask(i));
            }
        }
        return incominTasks;
    }

}
