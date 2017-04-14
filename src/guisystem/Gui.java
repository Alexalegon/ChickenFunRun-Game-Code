/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package guisystem;

import tutorial.Tutorial;
import audiosystem.AudioManager;
import com.jme3.asset.AssetManager;
import com.jme3.audio.AudioRenderer;
import com.jme3.input.InputManager;
import com.jme3.math.Vector3f;
import com.jme3.niftygui.NiftyJmeDisplay;
import com.jme3.renderer.ViewPort;
import com.jme3.scene.Spatial;
import de.lessvoid.nifty.Nifty;
import de.lessvoid.nifty.screen.Screen;
import de.lessvoid.nifty.screen.ScreenController;
import controllers.Controller;
import de.lessvoid.nifty.EndNotify;
import de.lessvoid.nifty.effects.Effect;
import de.lessvoid.nifty.effects.EffectEventId;
import de.lessvoid.nifty.effects.EffectImpl;
import de.lessvoid.nifty.elements.Element;
import de.lessvoid.nifty.elements.render.ImageRenderer;
import de.lessvoid.nifty.render.NiftyImage;
import static guisystem.Hud.clearPoints;
import levels.PrefabPool;
import listenersystem.ListenerHandler;
import mygame.GameState;
import mygame.GameStateManager;
import mygame.PlayState;
import maincharacter.PlayerListener;
import maincharacter.CamController;
import maincharacter.LifeManager;
import mygame.Main;
import static mygame.PlayState.chaseCam;
import mygame.PurchaseItems;

/**
 *
 * @author Miguel Martrinez
 */
public class Gui implements ScreenController,NiftyCommunicator {
public static Nifty nifty;
public static Hud hud;
public static Pause pause;
GameOver gameOver;
private int totalPoints = 8;
public static NiftyJmeDisplay niftyDisplay;
public static boolean firstBoot =  true;
public static GameState gameState;
public static Vector3f introCamGoal;
public static Element introElement;
public static NiftyImage[] introImages = new NiftyImage[3];
public String missionAccomplishedX;
public String missionAccomplishedY;
public Main main;

    public Gui(AssetManager assetManager, InputManager inputManager, AudioRenderer audioRenderer, ViewPort guiViewPort,Main main) {
          niftyDisplay= new NiftyJmeDisplay(
                assetManager, inputManager, audioRenderer, guiViewPort);

        nifty = niftyDisplay.getNifty();
       //homeXml();
        guiViewPort.addProcessor(niftyDisplay);  
       this.main = main;
    }

    Gui() {
        
    }
    
    public void homeXml(){
        setElementLocationX();
        setElementLocationY();
        Tutorial tut = new Tutorial();
        nifty.fromXml("Interface/start.xml", "blank", new ScreenController[]{this,tut});
        tut.init(nifty);
        nifty.setIgnoreMouseEvents(true);
    }
    
    public void loadGui(){
        hud = new Hud(nifty);
        pause = new Pause(nifty);
        gameOver = new GameOver(nifty);
       introElement = nifty.getScreen("intro").findElementById("ready");
       introImages[0] = nifty.createImage("Interface/ready.png", true);
       introImages[1] = nifty.createImage("Interface/set.png", true);
       introImages[2] = nifty.createImage("Interface/go.png", true);
       PopupsManager.init();
       StatsManager.load();
    }
    
    public static void goToScreen(){
        nifty.gotoScreen("intro");
        
        //nifty.exit();
        //nifty.fromXml("Interface/start.xml", "hud", hud);
        //nifty.unregisterScreenController(this);
        //nifty.registerScreenController(hud);
        //PlayState.setStart(true);
        if(!firstBoot){
            //stats.clearStats();
            hud.restart();
        }
        else{
            //gameState = GameState.RUN;
            //gameState.setIntro(true);
            introCamGoal = chaseCam.getNormalCamLoc();
            GameStateManager.setState(GameState.INTRO);
            Controller.restartPlay();
            firstBoot = false;
        }
        AudioManager.buttonClicked.playInstance();
    }
    
    public void pointsMethod(){
        
    }
    
    public Hud getHud(){
        return hud;
    }
    
    public Pause getPauseScreen(){
        return pause;
    }
    
    public GameOver getGameOverScreen(){
        return gameOver;
    }
    
    public static void restart(){
        
        hud.restart();
    }
    
@Override
    public  void playTutorial(String isSettings){
        nifty.gotoScreen("intro");
        if(isSettings.equalsIgnoreCase("true"))
           PopupsManager.closeSettingsPopup("true");
        
         HomeScreen.showHome("tutorial");
         GameStateManager.initTutorial();
    }
    
@Override
    public void pause(String ad){
        boolean showAd = false;
        if(ad.equalsIgnoreCase("true")){
            showAd = true;
            AudioManager.buttonClicked.playInstance();
        }
       hud.pause(showAd);
       
    }
    
@Override
    public void gameOver(){
        hud.gameOver();
    }
    
@Override
    public void resumePlay(){
        nifty.closePopup(PopupsManager.pausePopup.getId());
        if("tutorialScreen".equalsIgnoreCase(nifty.getCurrentScreen().getScreenId()))
            GameStateManager.setState(GameState.RESUMETUTORIAL);
        else{
            GameState.RESUMEPLAY.setResumeLife(false);
            GameStateManager.setState(GameState.RESUMEPLAY);
        }
        AudioManager.buttonClicked.playInstance();
    }
    
@Override
    public void goToHomeScreen(String type){
        if(type.equalsIgnoreCase("pause"))
            nifty.closePopup(PopupsManager.pausePopup.getId());
        else if(type.equalsIgnoreCase("gameOver"))
            nifty.closePopup(PopupsManager.gameOverPopup.getId());
        else if(type.equalsIgnoreCase("tutorialOver"))
            nifty.closePopup(PopupsManager.tutorialOverPopup.getId());
        //nifty.gotoScreen("start");
        GameStateManager.setState(GameState.HOME);
        AudioManager.buttonClicked.playInstance();
    }
    
@Override
    public void goToStore(String source){
        PopupsManager.showStorePopup(source);
        AudioManager.buttonClicked.playInstance();
    }
@Override
    public String getPrice(String string){
        return PurchaseItems.getItemPriceThroughName(string);
    }
 @Override
 public void continueLife(){
     //GameStateManager.setState(GameState.HOME);
     //PlayState.node.detachChild(PlayerListener.character);
     LifeManager.attemptContinueLife();
     AudioManager.buttonClicked.playInstance();
 }
    
@Override
    public void buyItem(String item){
        nifty.getCurrentScreen().findElementById(item).startEffect(EffectEventId.onShow);
        if(PurchaseItems.getItemThroughName(item).equals(PurchaseItems.MINIPACK)){
            LifeManager.attemptMiniPackBuy();
            return;//we return from method because this is not a cash purchase, rather we use in game points
        }
        
        Main.communication.buyItem(PurchaseItems.getItemThroughName(item));
    }

    @Override
    public void bind(Nifty nifty, Screen screen) {
       
    }

    @Override
    public void onStartScreen() {
      
    }

    @Override
    public void onEndScreen() {
    
    }
    
@Override
    public void showPopup(String type){
        if(type.equalsIgnoreCase("settings"))
           PopupsManager.showSettingsPopup("false");
        else if(type.equalsIgnoreCase("missions"))
            PopupsManager.showMissionsPopup();
        AudioManager.buttonClicked.playInstance();
    }
    
@Override
    public void closePopup(String type){
        if(type.equalsIgnoreCase("settings"))
            PopupsManager.closeSettingsPopup("false");
        else if(type.equalsIgnoreCase("missions"))
            PopupsManager.closeMissionspopup();
        else if(type.equalsIgnoreCase("store"))
            PopupsManager.closeStorePopup();
        else if(type.equalsIgnoreCase("buyChickens"))
            PopupsManager.closeBuyChickensPopup("true");
        AudioManager.buttonClicked.playInstance();
    }
    
@Override
    public void toggleAudio(final String audioName){
        
        EndNotify notify = new EndNotify() {
            
            @Override
            public void perform() {
                nifty.getCurrentScreen().findElementById(audioName).getRenderer(ImageRenderer.class)
                        .setImage(Settings.getCurrentAudioImage(audioName));
                
            }
          
        };
        nifty.getCurrentScreen().findElementById(audioName).startEffect(EffectEventId.onShow,notify);
        //nifty.findPopupByName(PopupsManager.settingsPopup.getId()).startEffect(EffectEventId.onShow, notify);
        Settings.toggleAudio(audioName);
        AudioManager.buttonClicked.playInstance();
    }
  
    
@Override
    public String getFont(){
        if(Main.screenDensity < 2f)
            return "Interface/Fonts/FiraSansHeavysmall.fnt";
        else
            return "Interface/Fonts/FiraSansHeavy.fnt";
    }
@Override
    public String getXLFont(){
        if(Main.screenDensity < 2f)
            return "Interface/Fonts/FiraSansHeavyMid.fnt";
        //else if(Main.screenDensity < 3.5f)
            //return "Interface/FontsFiraSansHeavy.fnt";
        else
            return "Interface/Fonts/FiraSansHeavyXL.fnt";
    }
    
   // public String setElementLocation(String percent,String coordinate){
        //if(coordinate.equals("x"))
            //return getX(percent);
        //return getY(percent);
        
    //}
@Override
    public void setElementLocationX(){
       String percent = "20";
        float width = main.getContext().getSettings().getWidth();
        int x = (int) (width *.2);
        int mid = (int) width/2;
       String s = Integer.toString(mid-x/2);
       missionAccomplishedX = s;
        //return String.valueOf((int)(width/2-(width * (100/Integer.getInteger(percent)))/2));
      
    }
@Override
    public void setElementLocationY(){
        String percent = "10";
        float height = main.getContext().getSettings().getHeight();
        int y = (int) -(height *.1);
        missionAccomplishedY = Integer.toString(y);
        //return "-" + String.valueOf((int)(height / (100/Integer.getInteger(percent))));
        
    }
    
@Override
    public String getMissionAX(){
        return missionAccomplishedX;
    }
@Override
    public String getMissionAY(){
        return missionAccomplishedY;
    }

    @Override
    public void goToWebsite() {
        Main.communication.goToWebsite();
    }
}
