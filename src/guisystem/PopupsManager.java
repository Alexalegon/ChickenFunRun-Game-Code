/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package guisystem;

import de.lessvoid.nifty.controls.NiftyControl;
import de.lessvoid.nifty.elements.Element;
import de.lessvoid.nifty.elements.render.ImageRenderer;
import de.lessvoid.nifty.elements.render.TextRenderer;
import de.lessvoid.nifty.render.NiftyImage;
import maincharacter.LifeManager;
import missions.MissionIndex;
import missions.MissionManager;
import mygame.GameState;
import mygame.GameStateManager;
import mygame.PlayState;

/**
 *
 * @author Miguel Martrinez
 */
public class PopupsManager {
    public static Element settingsPopup;
    public static Element missionsPopup;
    public static Element pausePopup;
    public static Element gameOverPopup;
    public static Element tutorialOverPopup;
    public static Element storePopup;
    public static Element buyChickensPopup;
    public static String[] missions = new String[3];
    public static String[] missionTexts = new String[2];
    public static NiftyImage missionBackground;
    public static NiftyImage missionCompletedBackground;
    public static boolean isTherePopupOnBottom = false;
    public static String storePopupSource;
    
    static {
        missions[0] = "mission1";
        missions[1] = "mission2";
        missions[2] = "mission3";
        missionTexts[0] = "missionText";
        missionTexts[1] = "progressText";
    }
    
   
    public static void init(){
        missionBackground = Gui.nifty.createImage("Interface/missionButtonBackground.png", true);
        missionCompletedBackground = Gui.nifty.createImage("Interface/missionCompletedBackground.png", true);
        LifeManager.init();
       
        
        
        
    }
    
    public static void showStorePopup(String source){
        
        if(!source.equalsIgnoreCase("home")){
            closeSettingsPopup("true");//here we passed in true to not show pause popup
            storePopupSource = source;
        }
        else 
            storePopupSource = source;
        storePopup = Gui.nifty.createPopup("storePopup");
        Gui.nifty.showPopup(Gui.nifty.getCurrentScreen(), storePopup.getId(), null);
    }
    
    public static void showPausePopup(){
         PopupsManager.pausePopup = Gui.nifty.createPopup("pausePopup");
        Gui.nifty.showPopup(Gui.nifty.getCurrentScreen(), PopupsManager.pausePopup.getId(), null);
        isTherePopupOnBottom = true;
    }
    
    public static void showSettingsPopup(String store){//string store signifies if call came from back button on store screen
        if(GameStateManager.getCurrentState().equals(GameState.PAUSE)
                && !store.equalsIgnoreCase("true")){
            Gui.nifty.closePopup(pausePopup.getId());
            
        }
        
        settingsPopup = Gui.nifty.createPopup("settingsPopup");
        Gui.nifty.showPopup(Gui.nifty.getCurrentScreen(), settingsPopup.getId(), null);
        Gui.nifty.findPopupByName(settingsPopup.getId()).findElementById("musicAudio").getRenderer(ImageRenderer.class)
                .setImage(Settings.getCurrentAudioImage("musicAudio"));
        Gui.nifty.findPopupByName(settingsPopup.getId()).findElementById("sfxAudio").getRenderer(ImageRenderer.class)
                .setImage(Settings.getCurrentAudioImage("sfxAudio"));
        
    }
    
    public static void showMissionsPopup(){
        if(GameStateManager.getCurrentState().equals(GameState.PAUSE)){
            Gui.nifty.closePopup(pausePopup.getId());
            
        }
        
        missionsPopup = Gui.nifty.createPopup("missionsPopup");
        Gui.nifty.showPopup(Gui.nifty.getCurrentScreen(), missionsPopup.getId(), null);
        for(MissionIndex missionIndex: MissionIndex.values()){
            for(String missionProgress: missionTexts)
        missionsPopup.findElementById(missionIndex.name).findElementById(missionProgress).getRenderer(TextRenderer.class)
                        .setText(MissionManager.updateMissionScreen(missionIndex,missionProgress));
            NiftyImage image;
            if(MissionManager.isMissionComplete(missionIndex))
                image = missionCompletedBackground;
            else
                image = missionBackground;
                missionsPopup.findElementById(missionIndex.name).findElementById("background")
                        .getRenderer(ImageRenderer.class).setImage(image);
        }
    }
    
    public static void showTutorialOverPopup(){
        tutorialOverPopup = Gui.nifty.createPopup("tutorialOverPopup");
        Gui.nifty.showPopup(Gui.nifty.getCurrentScreen(), tutorialOverPopup.getId(), null);
    }
    
    public static void showGameOverPopup(){
        gameOverPopup = Gui.nifty.createPopup("gameOverPopup");
        gameOverPopup.findElementById("number").getRenderer(ImageRenderer.class)
                .setImage(LifeManager.getNumberImage());
        Gui.nifty.showPopup(Gui.nifty.getCurrentScreen(), gameOverPopup.getId(), null);
    }
    
    public static void showBuyChickenspopup(){
        buyChickensPopup = Gui.nifty.createPopup("buyChickensPopup");
        Gui.nifty.showPopup(Gui.nifty.getCurrentScreen(), buyChickensPopup.getId(), null);
    }
    
    public static void closeSettingsPopup(String tutorial){
        Gui.nifty.closePopup(settingsPopup.getId());
        if(GameStateManager.getCurrentState().equals(GameState.PAUSE)
                && tutorial.equalsIgnoreCase("false")){
            showPausePopup();
            
        }
    }
    
    public static void closeMissionspopup(){
        Gui.nifty.closePopup(missionsPopup.getId());
        if(GameStateManager.getCurrentState().equals(GameState.PAUSE)){
            showPausePopup();
            
        }
    }
    
    public static void closeStorePopup(){
        Gui.nifty.closePopup(storePopup.getId());
        if(!storePopupSource.equalsIgnoreCase("home"))
        showSettingsPopup("true");
    }
    
    public static void closeGameOverPopup(){
        Gui.nifty.closePopup(gameOverPopup.getId());
    }
    
    public static void closeBuyChickensPopup(String showGameOverPopup){
        Gui.nifty.closePopup(buyChickensPopup.getId());
        if(showGameOverPopup.equalsIgnoreCase("true"))
            showGameOverPopup();
    }

   

}
