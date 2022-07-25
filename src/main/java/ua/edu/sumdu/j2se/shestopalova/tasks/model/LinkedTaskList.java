package ua.edu.sumdu.j2se.shestopalova.tasks.model;

import java.util.Iterator;
import java.util.NoSuchElementException;
import java.util.ConcurrentModificationException;
import java.util.stream.Stream;
import java.util.Objects;

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


    @Override
    public Iterator<Task> iterator() {
        return new Iterator<Task>() {

            private int index = 0;
            private int previousLoc = -1;
            private Node node = headNode;

            @Override
            public boolean hasNext() {
                return node != null;
            }

            @Override
            public Task next() {
                try {
                    int i = index;
                    Task next = getTask(i);
                    node = node.next;
                    previousLoc = i;
                    index = i + 1;
                    return next;
                } catch (IndexOutOfBoundsException e) {
                    throw new NoSuchElementException();
                }
            }

            @Override
            public void remove() {
                if(previousLoc < 0) {
                    throw new IllegalStateException("Can't remove!");
                }

                try {
                    LinkedTaskList.this.remove(getTask(previousLoc));
                    if (previousLoc < index)
                        index--;
                    previousLoc = -1;
                } catch (IndexOutOfBoundsException e) {
                    throw new ConcurrentModificationException();
                }
            }

        };
    }

    @Override
    public String toString() {
        Iterator<Task> taskIterator = this.iterator();
        StringBuilder string = new StringBuilder("LinkedTaskList{");
        while (taskIterator.hasNext()) {
            string.append(taskIterator.next().toString()).append(", ");
        }
        return string.append("size=").append(this.size()).append('}').toString();
    }

    public Stream<Task> getStream(){
        Task[] tasks = new Task[this.size()];
        for (int i = 0; i < this.size(); i++) {
            tasks[i] = this.getTask(i);
        }
        return Stream.of(tasks);
    }
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (getClass() != o.getClass()) return false;
        LinkedTaskList otherList = (LinkedTaskList) o;
        if (otherList.headNode == null && headNode == null) return true;
        while (headNode.next != null) {
            if (!(otherList.headNode.task.equals(headNode.task))) {
                return false;
            }
            headNode = headNode.next;
        }
        return headNode.task.equals(otherList.headNode.task);
    }
    @Override
    public int hashCode() {
        return Objects.hash(this.size(), headNode.task.getTitle());
    }

@Override
    public LinkedTaskList clone() throws CloneNotSupportedException {
        return (LinkedTaskList) super.clone();
    }
}
