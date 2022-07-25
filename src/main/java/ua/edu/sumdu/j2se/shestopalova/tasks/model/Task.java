package ua.edu.sumdu.j2se.shestopalova.tasks.model;

import java.util.Objects;
import java.time.LocalDateTime;
import java.io.Serializable;
public class Task implements Cloneable,Serializable {
    private LocalDateTime time;
    private String title;
    private boolean active;
    private boolean repeated;
    private LocalDateTime start_time;
    private LocalDateTime end_time;
    private int interval;

    /* Конструктор, що конструює неактивну задачу, яка
     * виконується у заданий час без повторення із заданою назвою.
     * @param time час задачі
     * @param title назва задачі
     * */
    public Task(String title, LocalDateTime time) throws IllegalArgumentException  {
        this.time = time;
        this.title = title;
        repeated = false;
        active = false;
        if (time == null ) throw new IllegalArgumentException("Time can not be null");
    }

    /* Конструктор, що конструює
     * неактивну задачу, яка виконується у заданому проміжку часу (і початок і кінець включно) із
     * заданим інтервалом і має задану назву
     * @param title назва
     * @param start стартова дата виконання задачі
     * @param end кінцева дата задачі
     * @param interval інтервал між задачами
     * */
    public Task(String title, LocalDateTime start, LocalDateTime end, int interval) throws IllegalArgumentException {
        this.title = title;
        this.start_time = start;
        if(start_time == null ) throw new IllegalArgumentException("Start time can not be null");
        this.end_time = end;
        this.interval = interval;
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

    public LocalDateTime getTime() {
        return repeated ? start_time : time;
    }
    /* void setTime(int time), у разі, якщо задача повторювалась, вона має стати такою,
    * що не повторюється
    * @param time встановлюємий час
    * */
    public void setTime(LocalDateTime time) {
        this.time = time;
        if (repeated) {
            repeated = false;
        }
        if (time == null){
            throw new IllegalArgumentException("Time can not be null");
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

    public LocalDateTime getStartTime() {
        return repeated ? start_time : time;
    }

    public LocalDateTime getEndTime() {
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
    public void setTime(LocalDateTime start, LocalDateTime end, int interval) {
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

    public LocalDateTime nextTimeAfter(LocalDateTime current) {
        if(isActive() && !isRepeated()) {
            return (current.isAfter(getTime())) || current.isEqual(getTime()) ? null : getTime();
        }
        if(isActive() & isRepeated()) {
            if(current.isBefore(getTime())) {
                return getTime();
            }
            else {
                LocalDateTime time = getStartTime();
                while (current.isAfter(time) || current.isEqual(time)) {
                    time = time.plusSeconds(getRepeatInterval());
                }
                if(time.isAfter(getEndTime())) {
                    return null;
                }
                return time;
            }
        }
        return null;
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

    @Override
    public Task clone() throws CloneNotSupportedException {
        return (Task) super.clone();
    }

    @Override
    public String toString() {
        return "Task{" +
                "title:'" + title + '\'' +
                ", time:" + time +
                ", start time:" + start_time +
                ", end time:" + end_time +
                ", interval:" + interval +
                ", active:" + active +
                ", repeated:" + repeated +
                '}';
    }
}
