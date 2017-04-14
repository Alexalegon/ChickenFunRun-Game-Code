/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package audiosystem;

import com.jme3.audio.AudioNode;
import java.util.concurrent.Callable;
import mygame.Main;
import mygame.PlayState;

/**
 *
 * @author Miguel Martrinez
 */
public class AudioManager {
    public static AudioNode gameMusic;
    public static AudioNode gameOverSound;
    public static AudioNode pointsSound;
   public static AudioNode buttonClicked;
    public static AudioNode chickenHitSound;
    
    
    
    public static AudioNode jumpSound;
    public static AudioNode buttonPressedSound;
    public static float volume= 1f;
    
    public static void initSounds(){
        gameMusic = new AudioNode(PlayState.assetManager,"Sounds/runnerGameMusic11.wav",false);
        gameMusic.setTimeOffset(1f);
        gameMusic.setPositional(false);
        gameMusic.setLooping(true);
        gameMusic.play();
        
        jumpSound = new AudioNode(PlayState.assetManager,"Sounds/Jump.wav",false);
        jumpSound.setPositional(false);
        
        pointsSound = new AudioNode(PlayState.assetManager,"Sounds/PickupPoints.wav",false);
        pointsSound.setPositional(false);
        
        chickenHitSound = new AudioNode(PlayState.assetManager,"Sounds/Chicken.wav",false);
        chickenHitSound.setPositional(false);
        
        gameOverSound = new AudioNode(PlayState.assetManager,"Sounds/GameOver.wav",false);
        gameOverSound.setPositional(false);
        
        buttonClicked = new AudioNode(PlayState.assetManager,"Sounds/buttonClicked.wav",false);
        buttonClicked.setPositional(false);
    }
    
    public static void turnOnsound(AudioNode node,boolean looping,boolean positional){
        node.play();
        node.setPositional(positional);
        node.setLooping(looping);
    }
    
    public static void turnOffSound(AudioNode node){
        node.stop();
    }
    
    public static void setVolume(float volume, String audioName){
        if(audioName.equalsIgnoreCase("musicAudio"))
            gameMusic.setVolume(volume);
        else{
            jumpSound.setVolume(volume*.3f);
            chickenHitSound.setVolume(volume*.3f);
            gameOverSound.setVolume(volume);
            pointsSound.setVolume(volume*.3f);
        }
    }
    
    public static void pauseGameMusic(){
        if(gameMusic!=null)
            PlayState.main.enqueue(new Callable(){

            @Override
            public Object call() throws Exception {
               gameMusic.stop();
                return null;
            }
            });
        
    }
    
    public static void resumeGameMusic(){
        if(gameMusic!=null)
            PlayState.main.enqueue(new Callable(){

            @Override
            public Object call() throws Exception {
               gameMusic.play(); 
                return null;
            }
            });
        
    }
}
