package mygame;

import controllers.Controller;
import com.jme3.app.BasicProfiler;
import com.jme3.app.SimpleApplication;
import com.jme3.app.StatsAppState;
import com.jme3.material.Material;
import com.jme3.math.ColorRGBA;
import com.jme3.math.FastMath;
import com.jme3.math.Vector3f;
import com.jme3.profile.AppProfiler;
import com.jme3.profile.AppStep;
import com.jme3.profile.VpStep;
import com.jme3.renderer.RenderManager;
import com.jme3.renderer.ViewPort;
import com.jme3.renderer.queue.RenderQueue;
import com.jme3.scene.Geometry;
import com.jme3.scene.shape.Box;
import com.jme3.ui.Picture;
import de.lessvoid.nifty.EndNotify;
import de.lessvoid.nifty.effects.EffectEventId;
import de.lessvoid.nifty.effects.EffectImpl;
import guisystem.Gui;
import guisystem.HomeScreen;
import guisystem.LoadScreen;
import java.util.concurrent.Callable;
import levels.TrackManager;
import static maincharacter.PlayerListener.bikerControl;

/**
 * test
 * @author normenhansen
 */
public class Main extends SimpleApplication {
Geometry geom;
Vector3f ve;
float h = 0;
public  Gui gui;
public static float tpf;
boolean isResetting = false;
public PlayState playState;
public GameState gameStatePassedToManager;
public static float screenDensity = 0;
public static Communication communication;
public static boolean isApplicationActive = false;
public LoadScreen loadScreen;
public static boolean loading = true;
private static boolean isAdsDisabled;
    public static void main(String[] args) {
        Main app = new Main();
        app.showSettings = false;
      
        app.setDisplayStatView(false);
        app.setDisplayFps(false);
        app.start();
        app.getGuiNode().detachAllChildren();
        
      
        //app.settings.setResizable(true);
    }

    @Override
    public void simpleInitApp() {
        //Picture pic = new Picture("");
        //pic.setImage(assetManager, "Interface/darkfigLogo.png", false);
        //pic.setWidth(this.settings.getWidth());
        //pic.setHeight(this.settings.getHeight()/6);
        //pic.setPosition(200,500);
       StatsAppState s = this.getStateManager().getState(StatsAppState.class);
        if(s!=null)
            getStateManager().detach(s);
      if(isApplicationActive)
          return;
      loadScreen = new LoadScreen(this);
      gui = new Gui(assetManager, inputManager, audioRenderer, guiViewPort,this);
      gui.homeXml();
    }
    
    public void loadGame(){
        
      
        playState = new PlayState(this);
        playState.initialize(stateManager, this);
        playState.setRootNode(rootNode);
        
        
        isApplicationActive = true;
    }

    @Override
    public void simpleUpdate(float tpf) {
        
        this.tpf = tpf;
        if(loadScreen.isAtHomeScreen() && !loadScreen.isFinished()){
            Gui.nifty.setIgnoreMouseEvents(false);
            loadScreen.finish();
        }
            
        if(loading)
            if(loadScreen.getFuture() == null)
                loadScreen.initLoadingScreen();
            else if(loadScreen.getFuture().isDone() && !loadScreen.isAtHomeScreen()){
                
                loadScreen.setIsAtHome(true);
                gui.loadGui();
                stateManager.attach(playState);
                //HomeScreen.showHome("");
                setReset(true,GameState.HOME);
                loading = false;
                
            }
                   
        if(isResetting){
            //PlayState.setStart(true);
            
            GameStateManager.setState(gameStatePassedToManager);
            isResetting = false;
            }
            
       
        //TODO: add update code
            //h+=.001f;
            //geom.setLocalTranslation(ve.x,h,ve.z);
    }

    @Override
    public void simpleRender(RenderManager rm) {
        //TODO: add render code
    }

    @Override
    public void reshape(int w, int h) {
        super.reshape(w, h); //To change body of generated methods, choose Tools | Templates.
    }
    
    public void setReset(boolean reset,GameState gameState){
        isResetting = reset;
        gameStatePassedToManager = gameState;
    }
    
    public void setCommunication(Communication communication){
        Main.communication = communication;
    }
    
    public void setScreenDensity(float density){
        screenDensity = density;
    }
    
    
    public void setIsAdsDisabled(boolean isAdsDisabled){
        Main.isAdsDisabled = isAdsDisabled;
    }
    
    public static boolean getIsAdsDisabled(){
        return isAdsDisabled;
    }
    public void handleAccelerometer(float[] values){
        Controller.handleAccelerometer(values);
    }
    
    //keep this for reference although its not used
    public void task(final EndNotify notify){
        enqueue(new Callable(){

            @Override
            public Object call() throws Exception {
                 Gui.nifty.getCurrentScreen().findElementById("loadingText").startEffect(EffectEventId.onHide,
              notify);
                return null;
                
            }
            
        });
    }
}
