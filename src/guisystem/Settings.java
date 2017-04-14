/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package guisystem;

import audiosystem.AudioManager;
import de.lessvoid.nifty.render.NiftyImage;

/**
 *
 * @author Miguel Martrinez
 */
public class Settings {
    public static boolean musicAudio = true;
    public static boolean sfxAudio = true;
    public static NiftyImage audioOnImage = Gui.nifty.createImage("Interface/audioButton.png",true);
    public static NiftyImage audioOffImage = Gui.nifty.createImage("Interface/audioOffButton.png", true);
    public static void toggleAudio(String audioName){
        boolean holder;
        if(audioName.equalsIgnoreCase("musicAudio"))
           holder = musicAudio = !musicAudio;
        else
            holder = sfxAudio = !sfxAudio;
        if(holder)
            AudioManager.setVolume(1f,audioName);
        else
            AudioManager.setVolume(0f,audioName);
    }
    
    public static NiftyImage getCurrentAudioImage(String audioName){
        boolean holder;
        if(audioName.equalsIgnoreCase("musicAudio"))
            holder = musicAudio;
        else 
            holder = sfxAudio;
        if(holder)
            return audioOnImage;
        return audioOffImage;
    }
}
