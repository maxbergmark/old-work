import java.io.File;
import java.io.IOException;

import javax.sound.sampled.AudioFormat;
import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.DataLine;
import javax.sound.sampled.LineUnavailableException;
import javax.sound.sampled.SourceDataLine;

public class SoundMixer implements Runnable{

    //private final int BUFFER_SIZE = 128000;
    private final int BUFFER_SIZE = 1000;
    private File soundFile;
    private AudioInputStream audioStream;
    private AudioFormat audioFormat;
    private SourceDataLine sourceLine;
    private String strFilename;

    private volatile boolean running = true;

    /**
     * @param filename the name of the file that is going to be played
     */

    public SoundMixer(String filename) {
        strFilename = filename;
    }

    public void terminate() {
        running = false;
    }

    public void run(){

        while (running) {
            try {
                soundFile = new File(strFilename);
            } catch (Exception e) {
                e.printStackTrace();
                System.exit(1);
            }

            try {
                audioStream = AudioSystem.getAudioInputStream(soundFile);
            } catch (Exception e){
                e.printStackTrace();
                System.exit(1);
            }

            AudioFormat audioFormat2 = audioStream.getFormat();
            audioFormat = new AudioFormat(audioFormat2.getSampleRate()*1.2F, audioFormat2.getSampleSizeInBits(), 
                                            audioFormat2.getChannels(), false, audioFormat2.isBigEndian());

            DataLine.Info info = new DataLine.Info(SourceDataLine.class, audioFormat);
            try {
                sourceLine = (SourceDataLine) AudioSystem.getLine(info);
                sourceLine.open(audioFormat);
            } catch (LineUnavailableException e) {
                e.printStackTrace();
                System.exit(1);
            } catch (Exception e) {
                e.printStackTrace();
                System.exit(1);
            }

            sourceLine.start();

            int nBytesRead = 0;
            byte[] abData = new byte[BUFFER_SIZE];
            while (nBytesRead != -1 && running) {
                try {
                    nBytesRead = audioStream.read(abData, 0, abData.length);
                } catch (IOException e) {
                    e.printStackTrace();
                }
                if (nBytesRead >= 0) {
                    @SuppressWarnings("unused")
                    int nBytesWritten = sourceLine.write(abData, 0, nBytesRead);
                }
            }

            sourceLine.drain();
            sourceLine.close();
        }
    }
}