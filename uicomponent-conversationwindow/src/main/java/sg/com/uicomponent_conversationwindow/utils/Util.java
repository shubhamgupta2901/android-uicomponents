package sg.com.uicomponent_conversationwindow.utils;

import android.os.Handler;
import android.os.Looper;
import android.support.annotation.NonNull;

import java.util.concurrent.CountDownLatch;

/**
 * Created by Shubham on 23/08/17.
 */

public class Util {
    private static final String TAG = Util.class.getSimpleName();

    public static Handler handler = new Handler(Looper.getMainLooper());

    public static int clamp(int value, int min, int max) {
        return Math.min(Math.max(value, min), max);
    }

    public static float clamp(float value, float min, float max) {
        return Math.min(Math.max(value, min), max);
    }

    public static boolean isMainThread() {
        return Looper.myLooper() == Looper.getMainLooper();
    }

    public static void assertMainThread() {
        if (!isMainThread()) {
            throw new AssertionError("Main-thread assertion failed.");
        }
    }
    public static void runOnMain(final @NonNull Runnable runnable) {
        if (isMainThread()) runnable.run();
        else                handler.post(runnable);
    }

    public static void runOnMainSync(final @NonNull Runnable runnable) {
        if (isMainThread()) {
            runnable.run();
        } else {
            final CountDownLatch sync = new CountDownLatch(1);
            runOnMain(new Runnable() {
                @Override public void run() {
                    try {
                        runnable.run();
                    } finally {
                        sync.countDown();
                    }
                }
            });
            try {
                sync.await();
            } catch (InterruptedException ie) {
                throw new AssertionError(ie);
            }
        }
    }

    public static void wait(Object lock, long timeout) {
        try {
            lock.wait(timeout);
        } catch (InterruptedException ie) {
            throw new AssertionError(ie);
        }
    }
}
