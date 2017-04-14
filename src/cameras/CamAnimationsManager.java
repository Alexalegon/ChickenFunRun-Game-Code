/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cameras;

import com.jme3.math.Vector3f;
import de.lessvoid.nifty.effects.EffectEventId;
import guisystem.Gui;
import levels.PathConstructor;
import levels.TrackManager;
import maincharacter.PlayerListener;
import mygame.GameState;
import mygame.GameStateManager;
import mygame.PlayState;
import static mygame.PlayState.chaseCam;
import pointsSystem.PointObjectSettings;
import pointsSystem.PointObjectSettings.Direction;

/**
 *
 * @author Miguel Martrinez
 */
public class CamAnimationsManager {
  public static  CamAnimation homeThenRun = new CamAnimation("home","cam");
  public static  CamAnimation playerTakeOff = new CamAnimation("playerTakeOff", "player");  
  public static GameState callBackState;
  
  public static void initAnimations(String animation){
        if(animation.equalsIgnoreCase("home"))
            CamAnimationsManager.initHomeThenRun();
        else if(animation.equalsIgnoreCase("playerTakeOff"))
            CamAnimationsManager.initPlayerTakeOff();
    }
    
    public static void animationEnded(String name){
        if(name.equalsIgnoreCase("home")){
            PlayState.chaseCam.setIsAnimated(false);
            CamAnimationsManager.homeThenRun.setEnabled(false);
            //GameStateManager.setState(GameState.RUN);
            
        }
        if(name.equalsIgnoreCase("playerTakeOff")){
            //Gui.introElement.stopEffect(EffectEventId.onHide);
        PlayerListener.customAnimControl.setEnabled(false);
            GameStateManager.setState(callBackState);
            //CamAnimationsManager.playerTakeOff.time=0;
            //CamAnimationsManager.playerTakeOff.endReached=true;
        }
    }
    
    public static void initHomeThenRun(){TrackManager.setDirection(PointObjectSettings.Direction.ZPOSITIVE);
        CamAnimationsManager.homeThenRun.reset();
        CamAnimationsManager.homeThenRun.setStartingLocation(new Vector3f(12f,3f,-112f));
        CamAnimationsManager.homeThenRun.setEndingLocation(chaseCam.getNormalCamLoc());
        CamAnimationsManager.homeThenRun.setDuration(3f);
        CamAnimationsManager.homeThenRun.setContinous(false);
        CamAnimationsManager.homeThenRun.setEnabled(true);
        PlayState.chaseCam.setEnabled(true);
        PlayState.chaseCam.setAnimation(CamAnimationsManager.homeThenRun);
        PlayState.chaseCam.setIsAnimated(true);
    }
    
    public static void initPlayerTakeOff(){
        
        CamAnimationsManager.playerTakeOff.reset();
        CamAnimationsManager.playerTakeOff.setStartingLocation(PlayerListener.character.getLocalTranslation());
        CamAnimationsManager.playerTakeOff.setEndingLocation(new Vector3f(0,2f,-56f));
        CamAnimationsManager.playerTakeOff.setDuration(3f);
        CamAnimationsManager.playerTakeOff.setContinous(false);
        
        
        PlayerListener.customAnimControl.setAnimation(playerTakeOff);
        PlayerListener.customAnimControl.setEnabled(true);
        
        PlayerListener.animChannel.setAnim("runBottom");
        
        
    }
    
    public static void resetAnimations(){
        PlayState.chaseCam.setIsAnimated(false);CamAnimationsManager.homeThenRun.setEnabled(false);
        PlayerListener.customAnimControl.setEnabled(false);
    }
    
    public static void setCallBackState(GameState state){
        callBackState = state;
    }
}
//PathConstructor.getCurrentRoad().fields.firstTileLoc.
                //subtract(new Vector3f(0,0,PathConstructor.getCurrentRoad().fields.tileDimensionsZ))