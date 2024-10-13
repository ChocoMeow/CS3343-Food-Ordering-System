package com.fos.Task;

import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

public abstract class Task {
    private final ScheduledExecutorService scheduler;
    private int initialDelay;
    private int period;

    public Task(int initialDelay, int period) {
        this.scheduler = Executors.newScheduledThreadPool(1);
        this.initialDelay = initialDelay;
        this.period = period;
    }

    public void start() {
        this.scheduler.scheduleAtFixedRate(this::execute, this.initialDelay, period, TimeUnit.SECONDS);
    }

    public void stop() {
        this.scheduler.shutdown();
    }

    public abstract void execute();
}
