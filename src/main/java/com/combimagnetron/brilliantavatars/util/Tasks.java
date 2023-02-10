package com.combimagnetron.brilliantavatars.util;

import org.jetbrains.annotations.NotNull;

import java.util.concurrent.*;

public class Tasks {
    private static final ScheduledThreadPoolExecutor SCHEDULED_THREAD_POOL_EXECUTOR;
    static {
        SCHEDULED_THREAD_POOL_EXECUTOR = new ScheduledThreadPoolExecutor(2);
    }

    public static @NotNull ScheduledFuture<?> runRepeatingAsync(Runnable task, long delay, long interval, TimeUnit timeUnit) {
        return SCHEDULED_THREAD_POOL_EXECUTOR.scheduleAtFixedRate(task, delay, interval, timeUnit);
    }

    public static void submitAsync(Runnable task, long delay, TimeUnit timeUnit) {
        SCHEDULED_THREAD_POOL_EXECUTOR.schedule(task, delay, timeUnit);
    }

    public static void executeAsync(Runnable task) {
        SCHEDULED_THREAD_POOL_EXECUTOR.execute(task);
    }

    public static ScheduledExecutorService getExecutor() {
        return SCHEDULED_THREAD_POOL_EXECUTOR;
    }

}
