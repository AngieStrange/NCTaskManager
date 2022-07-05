package ua.edu.sumdu.j2se.shestopalova.tasks;

import com.google.gson.Gson;
import java.io.*;
import java.time.LocalDateTime;
import java.time.ZoneOffset;


public class TaskIO {

    public static void write(AbstractTaskList tasks, OutputStream out) {
        try (DataOutputStream outStr = new DataOutputStream(out)) {
            outStr.writeInt(tasks.size());
            for (int i = 0; i < tasks.size(); i++) {
                outStr.writeInt(tasks.getTask(i).getTitle().length());
                outStr.writeUTF(tasks.getTask(i).getTitle());
                outStr.writeBoolean(tasks.getTask(i).isActive());
                outStr.writeInt(tasks.getTask(i).getRepeatInterval());

                if (tasks.getTask(i).isRepeated()) {
                    outStr.writeInt(tasks.getTask(i).getStartTime().getNano());
                    outStr.writeInt(tasks.getTask(i).getEndTime().getNano());
                } else {
                    outStr.writeInt(tasks.getTask(i).getTime().getNano());
                }
            }
        } catch (IOException e) {
            e.printStackTrace();

        }
    }

    public static void read(AbstractTaskList tasks, InputStream in) {
        try (DataInputStream inStr = new DataInputStream(in)) {
            int size = inStr.readInt();
            for (int i = 0; i < size; i++) {
                inStr.readInt();
                String title = inStr.readUTF();
                boolean isActive = inStr.readBoolean();
                int interval = inStr.readInt();

                if (interval > 0) {
                    LocalDateTime start = LocalDateTime.ofEpochSecond(inStr.readInt(), 0, ZoneOffset.UTC);
                    LocalDateTime end = LocalDateTime.ofEpochSecond(inStr.readInt(), 0, ZoneOffset.UTC);

                    Task repeatTask = new Task(title, start, end, interval);
                    repeatTask.setActive(isActive);
                    tasks.add(repeatTask);
                } else {
                    LocalDateTime time = LocalDateTime.ofEpochSecond(inStr.readInt(), 0, ZoneOffset.UTC);

                    Task nonRepeatTask = new Task(title, time);
                    nonRepeatTask.setActive(isActive);
                    tasks.add(nonRepeatTask);
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void write(AbstractTaskList tasks, Writer out)  throws IOException  {
        Gson writeJson = new Gson();
        writeJson.toJson(tasks, out);
        out.flush();
    }

    public static void read(AbstractTaskList tasks, Reader in) {
        Gson readJson = new Gson();
        AbstractTaskList taskList;
        taskList = readJson.fromJson(in, tasks.getClass());
        for (Task task : taskList) {
            tasks.add(task);
        }
    }

    public static void writeBinary(AbstractTaskList tasks, File file) {
        try (BufferedWriter buffWriter = new BufferedWriter(new FileWriter(file))) {
            write(tasks, buffWriter);

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void readBinary(AbstractTaskList tasks, File file) {
        try (BufferedReader buffReader = new BufferedReader(new FileReader(file))) {
            read(tasks, buffReader);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void writeText(AbstractTaskList tasks, File file) {
        try (FileOutputStream f = new FileOutputStream(file)) {
            write(tasks, f);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void readText(AbstractTaskList tasks, File file) {
        try (FileInputStream f = new FileInputStream(file)) {
            read(tasks, f);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}

