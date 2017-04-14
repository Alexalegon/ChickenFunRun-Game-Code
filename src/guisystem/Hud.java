/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package guisystem;

import aicharacters.ChickenListener;
import audiosystem.AudioManager;
import com.jme3.math.Vector3f;
import com.jme3.niftygui.RenderFontJme;
import de.lessvoid.nifty.Nifty;
import de.lessvoid.nifty.elements.Element;
import de.lessvoid.nifty.elements.render.TextRenderer;
import de.lessvoid.nifty.screen.Screen;
import de.lessvoid.nifty.screen.ScreenController;
import de.lessvoid.nifty.spi.render.RenderFont;
import controllers.Controller;
import de.lessvoid.nifty.effects.Effect;
import de.lessvoid.nifty.effects.EffectEventId;
import de.lessvoid.nifty.effects.EffectImpl;
import de.lessvoid.nifty.tools.SizeValue;
import static guisystem.Gui.nifty;
import mygame.GameState;
import listenersystem.ListenerHandler;
import mygame.Main;
import mygame.PlayState;
import maincharacter.PlayerListener;
import mygame.GameStateManager;

/**
 *
 * @author Miguel Martrinez
 */
public class Hud implements ScreenController{
Nifty nifty;
Screen screen;
static Element bronzeElement;
static Element chickenElement;


static TextRenderer bronzeTextRend;
static TextRenderer chickenTextRend;
static TextRenderer distanceTextRend;
private static int totalPoints = 0;

public static RenderFontJme font;
    public Hud(Nifty nifty) {
        this.nifty = nifty;
        init();
    }
    
    public void init(){
        screen = nifty.getScreen("hud");
        bronzeElement = screen.findElementById("bronzeText");
        bronzeTextRend = bronzeElement.getRenderer(TextRenderer.class);
        chickenElement = screen.findElementById("chickenText");
        chickenTextRend = chickenElement.getRenderer(TextRenderer.class);
        distanceTextRend = screen.findElementById("distanceText").getRenderer(TextRenderer.class);
        
        RenderFontJme font;
        if(Main.screenDensity < 2f)
            font = new RenderFontJme("Interface/Fonts/FiraSansHeavysmall.fnt",Gui.niftyDisplay);
         else
            font = new RenderFontJme( "Interface/Fonts/FiraSansHeavy.fnt",Gui.niftyDisplay);
        bronzeTextRend.setFont(font);
        chickenTextRend.setFont(font);
        
        
        Element missionAccomplished = nifty.getScreen("hud").findElementById("missionAccomplished");
        //Effect slideEffect = missionAccomplished.getEffects(EffectEventId.onShow, EffectImpl.class).get(0);//.getEffectImpl(SlideEffect.class);
        //setSlideEffectSettings(missionAccomplished);
        //slideEffect.setSettings(missionAccomplished);
    }
    
    public void setSlideEffectSettings(Element element){
        float height = PlayState.main.getContext().getSettings().getHeight();
        //distance = height/7;
        String y = "-" + String.valueOf(element.getHeight());
        String x = String.valueOf(PlayState.main.getContext().getSettings().getWidth()/2-element.getWidth()/2);
       element.setConstraintY(new SizeValue("0"));
       element.setConstraintX(new SizeValue(x));
       element.layoutElements();
       
    }
    
    public void addPoints(int points){
        totalPoints+=points;
    }
    
  //This is what actually shows the score
    public static void showBronzePoints(){
        bronzeTextRend.setText(String.valueOf(StatsManager.lowPointsConsumed));
    }
    //this actually shows chicken count
    public static void showChickenCount(){
        chickenTextRend.setText(String.valueOf(StatsManager.chickenCount));
    }
    
    public static void showDistance(){
        distanceTextRend.setText(String.valueOf((int)StatsManager.currentRunStats.distanceTraveled));
    }
    
    public static void clearPoints(){
        
        showBronzePoints();
        showChickenCount();
        showDistance();
    }
    
    public void showScore(){
        bronzeTextRend.setText(String.valueOf(totalPoints));
    }
    
  
    
     public void restart(){
        
        Controller.restartPlay();
        clearPoints();
        PlayState.bulletAppState.getPhysicsSpace().add(PlayerListener.bikerControl);
        PlayerListener.bikerControl.setPhysicsLocation(new Vector3f(0,5f,0f));
        PlayerListener.bikerControl.graduallyRotate = false;
        //PlayerListener.character.addControl(PlayerListener.bikerControl);
        //PlayerListener.bikerControl.setReset(false);
        
        //throw new NullPointerException("ClickedButton!");
        //if(PlayState.bulletAppState.getPhysicsSpace().getCharacterList().isEmpty())
            //throw new NullPointerException("CHaractet control is null!");
        
        //PlayerListener.bikerControl.setWalkDirection(Vector3f.ZERO);
        
        //if(!PlayerListener.bikerControl.getWalkDirection().equals(Vector3f.UNIT_Z))
            //throw new NullPointerException("Walk direction is not posZ");
        
       
        //PlayState.main.playState.setStart(true);
        ListenerHandler.registerListeners();
       PlayState.main.setReset(true,GameState.INTRO);
    }
    
    public void pause(boolean showAd){
        if(showAd && !Main.getIsAdsDisabled())
            Main.communication.showInterstitial();
        //PlayState.main.setReset(true, GameState.PAUSE);
        ChickenListener.setEnabled(false);
        GameStateManager.setState(GameState.PAUSE);
        ListenerHandler.unregisterListeners();
        PopupsManager.showPausePopup();
    }
    
    public void gameOver(){
        AudioManager.gameOverSound.playInstance();
        if(!Main.getIsAdsDisabled())
            Main.communication.showInterstitial();
        PlayState.main.setReset(true, GameState.GAMEOVER);
        ListenerHandler.unregisterListeners();
        PopupsManager.showGameOverPopup();
        
    }
    
    public void closePausePopup(){
        nifty.closePopup(PopupsManager.pausePopup.getId());
    }
    public void closeGameOverPopup(){
        nifty.closePopup(PopupsManager.gameOverPopup.getId());
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
}
