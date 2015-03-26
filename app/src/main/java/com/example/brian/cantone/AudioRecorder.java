package com.example.brian.cantone;

import android.media.AudioRecord;
import android.media.MediaPlayer;
import android.media.MediaRecorder;
import android.util.Log;

import java.io.IOException;

/**
 * Created by brian on 3/25/15.
 *
 * AudioRecorder uses the phone microphone to record input audio as well as provide methods to manipulate the audio.
 *
 * TODO: consider changing MediaRecorder to AudioRecord for on the fly sound analysis and visualization
 */
public class AudioRecorder {

    private static final String LOG_TAG = "AudioRecorder";

    private final String outputFile;

    private AudioRecord audio;
    private MediaRecorder recorder;
    private MediaPlayer player;

    public AudioRecorder(String outputFile) {
        recorder = new MediaRecorder();
        this.outputFile = outputFile;
    }

    /**
     * Records the audio. Calling startRecording() will override any previously stored recording.
     */
    public void startRecording() {
        // reinitialize MediaRecorder
        // see: http://developer.android.com/reference/android/media/MediaRecorder.html
        recorder = new MediaRecorder();
        recorder.setAudioSource(MediaRecorder.AudioSource.MIC);
        recorder.setOutputFormat(MediaRecorder.OutputFormat.THREE_GPP);
        recorder.setOutputFile("tmp/cantone_recording.3gp");
        recorder.setAudioEncoder(MediaRecorder.AudioEncoder.AMR_NB);

        // try to prepare recorder
        try {
            recorder.prepare();
        } catch (IOException e) {
            Log.e(LOG_TAG, "prepare() recorder failed");
        }

        // start recording
        recorder.start();
    }

    /**
     * Stops recording the audio.
     */
    public void stopRecording() {
        recorder.stop();
    }

    /**
     * Start playing the recorded audio. If audio does not exist, displays error message.
     */
    public void startPlaying() {
        player = new MediaPlayer();
        try {
            player.setDataSource(outputFile);
            player.prepare();
            player.start();
        } catch (IOException e) {
            Log.e(LOG_TAG, "prepare() playing audio failed");
        }
    }

    /**
     * Stops playing recorded audio and frees MediaPlayer resources.
     */
    public void stopPlaying() {
        player.stop();
        player.release();
        player = null;
    }

}
