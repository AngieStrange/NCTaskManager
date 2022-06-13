package ua.edu.sumdu.j2se.shestopalova.tasks;

import java.util.Objects;
public class Task implements Cloneable {
    private int time;
    private String title;
    private boolean active;
    private boolean repeated;
    private int start_time;
    private int end_time;
    private int interval;

    /* Конструктор, що конструює неактивну задачу, яка
     * виконується у заданий час без повторення із заданою назвою.
     * @param time час задачі
     * @param title назва задачі
     * */
    public Task(String title, int time) throws IllegalArgumentException  {
        this.time = time;
        this.title = title;
        repeated = false;
        active = false;
        if (time < 0 ) throw new IllegalArgumentException("Time can not be negative");
    }

    /* Конструктор, що конструює
     * неактивну задачу, яка виконується у заданому проміжку часу (і початок і кінець включно) із
     * заданим інтервалом і має задану назву
     * @param title назва
     * @param start стартова дата виконання задачі
     * @param end кінцева дата задачі
     * @param interval інтервал між задачами
     * */
    public Task(String title, int start, int end, int interval) throws IllegalArgumentException {
        this.title = title;
        this.start_time = start;
        if(start_time < 0 ) throw new IllegalArgumentException("Start time can not be negative");
        this.end_time = end;
        if(end_time<start_time) throw new IllegalArgumentException("Start time can not be less than end time");
        this.interval = interval;
        if (interval <= 0 ) throw new IllegalArgumentException("Interval must be greater than zero");
        active = false;
        repeated = true;

    }

    /* Метод для зчитування та встановлення назви задачі
    * */

    public String getTitle() {
        return title;
    }

    /* Метод що задає назву задачі
     * @param title назва
    * */

    public void setTitle(String title) {
        this.title = title;
    }

    /*Методи для зчитування та встановлення стану задачі*/

    public boolean isActive() {

        return active;
    }

    public void setActive(boolean active) {
        this.active = active;
    }

    /* Методи для зчитування та зміни часу виконання для задач, що не повторюються:
     * getTime, у разі, якщо задача повторюється метод має повертати час початку
     * повторення;
     * */

    public int getTime() {
        return repeated ? start_time : time;
    }
    /* void setTime(int time), у разі, якщо задача повторювалась, вона має стати такою,
    * що не повторюється
    * @param time встановлюємий час
    * */
    public void setTime(int time) {
        this.time = time;
        if (repeated) {
            repeated = false;
        }
    }

    /*
    * Методи для зчитування та зміни часу виконання для задач, що повторюються:
    * int getStartTime(), у разі, якщо задача не повторюється метод має повертати час
      виконання задачі;
    * int getEndTime(), у разі, якщо задача не повторюється метод має повертати час
      виконання задачі;
    * int getRepeatInterval(), у разі, якщо задача не повторюється метод має
      повертати 0;
    * */

    public int getStartTime() {
        return repeated ? start_time : time;
    }

    public int getEndTime() {
        return repeated ? end_time : time;
    }

    public int getRepeatInterval() {
        return repeated ? interval : 0;
    }
    /* void setTime(int start, int end, int interval), у разі, якщо задача не
     * повторювалася метод має стати такою, що повторюється
     * @param start встановлює початок
     * @param end встановлює кінець
     * @param interval втсановлює інтервал
    * */
    public void setTime(int start, int end, int interval) {
        this.start_time = start;
        this.end_time = end;
        this.interval = interval;
        repeated = true;
    }

    /*Метод для перевірки повторюваності задачі boolean*/

    public boolean isRepeated() {
        return repeated;
    }
    /* Метод int nextTimeAfter, що повертає час наступного
     * виконання задачі після вказаного часу current,
     * якщо після вказаного часу задача не виконується, то
     * метод має повертати -1.
     * @param current поточний час
     * */

    public int nextTimeAfter(int current) {

        if (!active) {
              return -1;
        }

        if (!repeated) {
              return current >= time ? -1 : time;
        }

        if (current < start_time) {
              return start_time;
        }

        if (current > end_time) {
              return -1;
        }
        else {
            int i;
            for (i = start_time; i <= end_time; i += interval) {
                if (i > current) {
                    return i;
                }
            }
        }
        return -1;
    }

    @Override
    public int hashCode() {
        return Objects.hash(getTitle(), isActive(), getTime(), getRepeatInterval(), isRepeated(), getStartTime(), getEndTime());
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Task)) return false;
        Task task = (Task) o;
        return isActive() == task.isActive() &&
                getTime() == task.getTime() &&
                getRepeatInterval() == task.getRepeatInterval() &&
                isRepeated() == task.isRepeated() &&
                getStartTime() == task.getStartTime() &&
                getEndTime() == task.getEndTime() &&
                Objects.equals(getTitle(), task.getTitle());
    }

    public Object clone(){
        try {
            Task task_Clone = (Task) super.clone();
            return task_Clone;
        } catch (CloneNotSupportedException e) {
            return null;
        }
    }

    @Override
    public String toString() {
        return "Task{" +
                "title='" + title + '\'' +
                ", time=" + time +
                ", start=" + start_time +
                ", end=" + end_time +
                ", interval=" + interval +
                ", active=" + active +
                ", repeated=" + repeated +
                '}';
    }
}
