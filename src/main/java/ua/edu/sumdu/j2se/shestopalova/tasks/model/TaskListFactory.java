package ua.edu.sumdu.j2se.shestopalova.tasks.model;

import ua.edu.sumdu.j2se.shestopalova.tasks.model.AbstractTaskList;
import ua.edu.sumdu.j2se.shestopalova.tasks.model.ArrayTaskList;
import ua.edu.sumdu.j2se.shestopalova.tasks.model.LinkedTaskList;
import ua.edu.sumdu.j2se.shestopalova.tasks.model.ListTypes;

public class TaskListFactory {

    public static AbstractTaskList createTaskList(ListTypes.types types) {
        switch (types) {
            case LINKED:
                return new LinkedTaskList();
            case ARRAY:
            default:
                return new ArrayTaskList();
        }

    }
}