/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package maincharacter;

import com.jme3.math.Vector3f;
import controllers.Controller;
import de.lessvoid.nifty.render.NiftyImage;
import guisystem.Gui;
import guisystem.PopupsManager;
import guisystem.StatsManager;
import levels.PathConstructor;
import levels.TrackManager;
import missions.Collectibles;
import mygame.GameState;
import mygame.GameStateManager;
import static mygame.GameStateManager.currentGameState;
import mygame.PlayState;
import mygame.PurchaseItems;
import obstaclesystem.HitInfo;

/**
 *
 * @author Miguel Martrinez
 */
public final class LifeManager {
    public static boolean active = false;
    static NiftyImage twoImage;
    static NiftyImage fiveImage;
    static NiftyImage sevenImage;
    static NiftyImage nineImage;
  private LifeManager(){
      
  }
  
  public static void init(){
      twoImage = Gui.nifty.createImage("Interface/two.png", true);
      fiveImage = Gui.nifty.createImage("Interface/five.png",true);
      sevenImage = Gui.nifty.createImage("Interface/seven.png", true);
      nineImage = Gui.nifty.createImage("Interface/nine.png", true);
  }
  
  public static void attemptContinueLife(){
      PopupsManager.closeGameOverPopup();
      if(continueLife(getChickensNeeded())){
          
      }
         
      else{
              PopupsManager.showBuyChickenspopup();
          }
      
  }
  
  public static boolean continueLife(int chickensNeeded){
      if(StatsManager.getChickenCount() >= chickensNeeded){
              GameState.RESUMEPLAY.setResumeLife(true);
              respawnFromHitType();
              active = true;
              StatsManager.chickenUsed(chickensNeeded);
              StatsManager.getCurrentRunStats().goToNextLife();
              
              PlayState.main.setReset(true,GameState.RESUMEPLAY);
              return true;
          }
      return false;
  }
  
  public static void buyFromGameOver(){
      StatsManager.chickenBought(15);
      continueLife(getChickensNeeded());
      
  }
  
  public static void buyFromStore(PurchaseItems item){
      StatsManager.chickenBought(item.getChickensBought());
  }
  
  //this method checks what life this is to determine how many chickens
  //are needed to respaen life again
  public static int getChickensNeeded(){
      if(StatsManager.getCurrentRunStats().getCurrentLife() == 0)
          return 2;
      else if(StatsManager.getCurrentRunStats().getCurrentLife() == 1)
          return 5;
      else if(StatsManager.getCurrentRunStats().getCurrentLife() == 2)
          return 7;
      else return 9;
  }
  
  public static int getPointsNeeded(){
     return StatsManager.getPoints(Collectibles.POINTS.name);
          
  }
  
  public static void respawnFromHitType(){
      PlayerListener.bikerControl.setEnabled(true);
            //check if obstacle or hay hit
      if(StatsManager.getCurrentRunStats().getHitInfo().getHitType().equals(HitInfo.HitType.HAY)){
          if(PathConstructor.getTurnDirection().equalsIgnoreCase("left")){
                     Controller.turnLeft();
                 }
          else{
                     Controller.turnRight();
                 }
            }
      TrackManager.getCurrentRunningAlg().spawnAhead(30f);
      CamController.movePlayer(30f);
            
        }
  
  public static void attemptMiniPackBuy(){
      if(StatsManager.getPoints(Collectibles.POINTS.name) >= 5000){
          StatsManager.chickenBought(10);
          StatsManager.pointsUsed(50);
      }
  }
  
  //retrieves the correct image according to current life
  public static NiftyImage getNumberImage(){
      int chickensNeeded = getChickensNeeded();
      if(chickensNeeded == 2)
          return twoImage;
      else if(chickensNeeded == 5)
          return fiveImage;
      else if(chickensNeeded == 7)
          return sevenImage;
      else
          return nineImage;
  }
}
