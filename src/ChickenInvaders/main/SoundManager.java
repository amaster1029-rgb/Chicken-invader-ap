package ChickenInvaders.main;

import javax.sound.sampled.*;
import java.io.File;

public class SoundManager {

    public static boolean isMusicEnabled = true;
    public static boolean isShotEnabled = true;
    public static boolean isExplosionEnabled = true;
    public static boolean isGameOverEnabled = true;

    private static Clip bgmClip;
    private static String currentBGMPath = "";

    public static void playBGM(String filePath){
        currentBGMPath = filePath;

        if(!isMusicEnabled)
            return;
        try {
            if(bgmClip != null && bgmClip.isRunning())
                return;

            File file = new File(filePath);
            if(file.exists()){
                if(bgmClip != null)
                    bgmClip.close();

                AudioInputStream audioStream = AudioSystem.getAudioInputStream(file);
                bgmClip = AudioSystem.getClip();
                bgmClip.open(audioStream);
                bgmClip.loop(Clip.LOOP_CONTINUOUSLY);
            }

        }catch (Exception e){
            System.out.println("Error playing BGM: " + e.getMessage());
        }
    }

    public static void stopBGM(){
        if(bgmClip != null && bgmClip.isRunning())
            bgmClip.stop();
    }

    public static void playEffect(String filePath){
        try {
            File file = new File(filePath);
            if(file.exists()){
                AudioInputStream audioStream = AudioSystem.getAudioInputStream(file);
                Clip clip = AudioSystem.getClip();
                clip.open(audioStream);
                clip.start();
            }
        }catch (Exception e){
            System.out.println("Error playing sound Effect: " + e.getMessage());
        }
    }

    public static void playShotSound(String filePath){
        if(isShotEnabled)
            playEffect(filePath);
    }

    public static void playExplosionSound(String filePath){
        if(isExplosionEnabled)
            playEffect(filePath);
    }

    public static void playGameOverSound(String filePath){
        if(isGameOverEnabled)
            playEffect(filePath);
    }

    public static void resumeBGM(){
        if(isMusicEnabled){
            if(bgmClip != null){
                if(!bgmClip.isRunning()){
                    bgmClip.start();
                    bgmClip.loop(Clip.LOOP_CONTINUOUSLY);
                }
            }
            else if(currentBGMPath != null && !currentBGMPath.isEmpty())
                playBGM(currentBGMPath);
        }
    }
}
