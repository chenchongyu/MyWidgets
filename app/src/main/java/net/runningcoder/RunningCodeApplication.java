
package net.runningcoder;

import android.app.ActivityManager;
import android.app.ActivityManager.RunningAppProcessInfo;
import android.app.Application;
import android.content.Context;
import android.os.Process;
import android.text.TextUtils;

import net.runningcoder.util.DateUtil;
import net.runningcoder.util.PathUtil;

import java.io.FileOutputStream;
import java.io.PrintStream;
import java.util.List;


public class RunningCodeApplication extends Application {
    private static String CRASH_LOG = "";
    private static RunningCodeApplication sInstance;
    private boolean mRunInServiceProcess = false;
    public static RunningCodeApplication getInstance() {

        if (sInstance == null) {
            throw new RuntimeException(
                    "Not support calling this, before create app or after terminate app.");
        }
        return sInstance;
    }

    @Override
    protected void attachBaseContext(Context base) {
        super.attachBaseContext(base);
        // ContextUtils.initApplication(this);
        checkRunningProcess();
        sInstance = this;
    }

    private void checkRunningProcess() {
        ActivityManager activityManager = (ActivityManager) getSystemService(Context.ACTIVITY_SERVICE);
        List<RunningAppProcessInfo> infos = activityManager
                .getRunningAppProcesses();
        int pid = Process.myPid();
        RunningAppProcessInfo myInfo = null;
        for (RunningAppProcessInfo info : infos) {
            if (info.pid == pid) {
                myInfo = info;
                break;
            }
        }
        if (myInfo != null) {
            mRunInServiceProcess = !TextUtils.equals(myInfo.processName,
                    getPackageName());
        }
    }

    @Override
    public void onCreate() {
        super.onCreate();
        PathUtil.initial(this);
        CRASH_LOG = PathUtil.getInstance().getCacheRootPath("log") + "/rc_crash.log";
//        RcExceptionHandler handler = new RcExceptionHandler();
//        Thread.setDefaultUncaughtExceptionHandler(handler);
//        Thread.currentThread().setUncaughtExceptionHandler(handler);
    }


    @Override
    public void onTerminate() {
        super.onTerminate();
    }

    private class RcExceptionHandler implements Thread.UncaughtExceptionHandler {

        @Override
        public void uncaughtException(Thread thread, Throwable ex) {
            PrintStream printer = null;
            try {
                FileOutputStream out = new FileOutputStream(CRASH_LOG, true);
                printer = new PrintStream(out, false);
                printer.print("#====== ");
                printer.print(DateUtil.getCurDateStr());
                printer.println(" ======");

                printer.print("UncaughtException:");
                if (thread != null) {
                    printer.print(thread.getName());
                    printer.print("(" + thread.getId() + ")");
                }
                printer.println();

                if (ex != null) {
                    ex.printStackTrace();
                    ex.printStackTrace(printer);

                    Throwable t = ex.getCause();
                    if (t != null) {
                        printer.println("Case trace:");
                        t.printStackTrace(printer);
                    }
                }
            } catch (Throwable t) {
                t.printStackTrace();
            } finally {
                if (printer != null) {
                    try {
                        printer.close();
                    } catch (Throwable t) {
                        // ignore
                    }
                }
            }

        }
    }

}
