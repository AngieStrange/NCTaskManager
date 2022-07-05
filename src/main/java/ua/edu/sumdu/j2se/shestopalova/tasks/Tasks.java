package ua.edu.sumdu.j2se.shestopalova.tasks;

import java.time.LocalDateTime;
import java.util.*;
import java.util.stream.Stream;


public class Tasks {

    public static SortedMap<LocalDateTime, Set<Task>> calendar(Iterable<Task>  tasks, LocalDateTime  start, LocalDateTime end) {
        SortedMap<LocalDateTime, Set<Task>> calendar = new TreeMap<>();

        for (Task task : tasks) {
            if (task.isActive()){
                LocalDateTime temp = task.nextTimeAfter(start);
                while (temp != null && !temp.isAfter(end)) {
                    if (calendar.containsKey(temp)) {
                        calendar.get(temp).add(task);
                    } else {
                        Set<Task> taskSet = new HashSet<>();
                        taskSet.add(task);
                        calendar.put(temp, taskSet);
                    }
                    temp = task.nextTimeAfter(temp);
                }
            }
        }
        return calendar;
    }

    public static  Iterable<Task> incoming(Iterable<Task> tasks, LocalDateTime start, LocalDateTime end)  {
        AbstractTaskList tempList = new ArrayTaskList();
        tasks.forEach(tempList::add);
        Stream<Task> taskStream = tempList.getStream();
        Set<Task> incomTask = new LinkedHashSet<Task>();

            taskStream.filter(s -> s != null && s.nextTimeAfter(start) != null &&
                                        s.nextTimeAfter(start).isAfter(start.minusSeconds(1)) &&
                    s.nextTimeAfter(start).isBefore(end.plusSeconds(1)) && s.isActive()).forEach(incomTask::add);
            return incomTask;
        }
    }

