/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package guisystem;

import de.lessvoid.nifty.EndNotify;
import de.lessvoid.nifty.effects.EffectEventId;
import java.util.concurrent.Callable;
import java.util.concurrent.Future;
import java.util.concurrent.ScheduledThreadPoolExecutor;
import mygame.Main;
import mygame.PlayState;

/**
 *
 * @author Miguel Martrinez
 */
public class LoadScreen {
    Future futureLoad;
    ScheduledThreadPoolExecutor executor = new ScheduledThreadPoolExecutor(1);
    Main main;
    boolean isAtHome = false;
    boolean isFinished = false;
    
    public LoadScreen(Main main) {
        this.main = main;
    }
    
    public void initLoadingScreen(){
         Callable<Void> callable = new Callable<Void>() {

            @Override
            public Void call() {
                
     
                main.loadGame();

                return null;

            }
        ;
        };
      futureLoad = executor.submit(callable);
    }
    
    public Future getFuture() {
        return futureLoad;
    }
    
    public void setIsAtHome(boolean home){
        isAtHome = home;
    }
    //for activating nifty mouse 
    public boolean isAtHomeScreen(){
        return isAtHome;
    }
    
    public void finish(){
        isFinished = true;
    }
    
    public boolean isFinished(){
        return isFinished;
    }
}
