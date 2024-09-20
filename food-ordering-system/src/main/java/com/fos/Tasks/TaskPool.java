package com.fos.Tasks;

import java.util.HashMap;
import java.util.Map;

public class TaskPool {
    public static Map<String, Task> taskMap = new HashMap<>();

    public TaskPool() {
        taskMap.put("main", new MainTask(0, 1));
    }

    public static Task getTask(String taskName) {
        return taskMap.get(taskName);
    }

    public static void stopAllTasks() {
        for (Task task : taskMap.values()) {
            task.stop();
        }
    }
}
