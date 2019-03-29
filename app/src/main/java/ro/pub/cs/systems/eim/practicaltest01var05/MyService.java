package ro.pub.cs.systems.eim.practicaltest01var05;

import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.os.IBinder;
import android.util.Log;

import java.util.Date;

public class MyService extends Service {
    public MyService() {
    }
    private ProcessingThread processingThread = null;

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        int score = intent.getIntExtra("score", -1);
        processingThread = new ProcessingThread(this, score);
        processingThread.start();
        return Service.START_REDELIVER_INTENT;
    }

    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    @Override
    public void onDestroy() {
        processingThread.stopThread();
    }

    class ProcessingThread extends Thread {
        private Context context = null;
        private int score;
        private boolean isRunning;

        public ProcessingThread(Context context, int score) {
            this.context = context;
            this.score = score;
        }

        @Override
        public void run() {
            isRunning = true;
            while (isRunning) {
                sleep();
                sendMessage();
            }
        }

        private void sendMessage() {
            Intent intent = new Intent();
//            intent.setAction(Constants.actionTypes[random.nextInt(Constants.actionTypes.length)]);
            intent.putExtra("date", new Date().toString());
            intent.setAction("VICTORY");
            intent.putExtra("score", score);
            Log.e("[MESSAGE]", "Sending message...");
            context.sendBroadcast(intent);
        }

        private void sleep() {
            try {
                Thread.sleep(2000);
            } catch (InterruptedException interruptedException) {
                interruptedException.printStackTrace();
            }
        }

        public void stopThread() {
            isRunning = false;
        }
    }
}
