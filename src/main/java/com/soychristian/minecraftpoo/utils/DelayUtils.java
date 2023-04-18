package com.soychristian.minecraftpoo.utils;

import java.util.Timer;
import java.util.TimerTask;

public class DelayUtils {
    public static void executeDelayed(Runnable task, long delay) {
        Timer timer = new Timer();
        timer.schedule(new TimerTask() {
            @Override
            public void run() {
                task.run();
            }
        }, delay);
    }
}
