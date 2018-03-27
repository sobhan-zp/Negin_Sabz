package ir.loper.neginsabz.Service;

import android.app.Service;
import android.content.Intent;
import android.media.MediaPlayer;
import android.os.IBinder;

import ir.loper.neginsabz.R;


public class BackgroundSoundService extends Service {
    private static final String TAG = null;

    public static final String CMD_NAME = "CMD_NAME";
    public static final String ACTION_CMD = "ACTION_CMD";
    public static final String CMD_PAUSE = "CMD_PAUSE";
    public static final String CMD_PLAY = "CMD_PLAY";
    public static final String CMD_STOP = "CMD_STOP";

    MediaPlayer player;
    public IBinder onBind(Intent arg0) {

        return null;
    }
    @Override
    public void onCreate() {
        super.onCreate();
        player = MediaPlayer.create(this, R.raw.music);
        player.setLooping(true); // Set looping
        player.setVolume(35,35);

    }
    public int onStartCommand(Intent startIntent, int flags, int startId) {

        if (startIntent != null) {
            String command = startIntent.getStringExtra(CMD_NAME);

            if (CMD_PAUSE.equals(command)) {
                if (player != null && player.isPlaying()) {
                    //pause
                    player.pause();
                }
            }else if (CMD_PLAY.equals(command)) {
                //play
                player.start();
            }else if (CMD_STOP.equals(command)) {
                if (player != null && player.isPlaying()) {
                    //stop
                    player.stop();
                }
            }

        }

        return START_STICKY;

        /*player.start();
        return 1;*/
    }

    public void onStart(Intent intent, int startId) {
        // TO DO
    }
    public IBinder onUnBind(Intent arg0) {
        // TO DO Auto-generated method
        return null;
    }

    public void onStop() {

    }
    public void onPause() {

    }
    @Override
    public void onDestroy() {
        player.stop();
        player.release();
    }

    @Override
    public void onLowMemory() {

    }
}