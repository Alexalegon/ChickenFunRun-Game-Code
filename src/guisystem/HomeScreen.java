/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package guisystem;

import aicharacters.ChickenListener;
import cameras.CamAnimationsManager;
import com.jme3.animation.AnimChannel;
import com.jme3.animation.AnimEventListener;
import com.jme3.export.JmeExporter;
import com.jme3.export.JmeImporter;
import com.jme3.math.Vector3f;
import com.jme3.renderer.RenderManager;
import com.jme3.renderer.ViewPort;
import com.jme3.scene.Node;
import com.jme3.scene.Spatial;
import com.jme3.scene.control.Control;
import controllers.Controller;
import de.lessvoid.nifty.elements.render.TextRenderer;
import static guisystem.Gui.nifty;
import java.io.IOException;
import levels.PrefabPool;
import levels.RotationQuats;
import levels.TrackManager;
import listenersystem.ListenerHandler;
import mygame.PlayState;
import maincharacter.PlayerListener;
import pointsSystem.PointObjectSettings;
import pointsSystem.PointObjectsFactory;

/**
 *
 * @author Miguel Martrinez
 */
public class HomeScreen {
    public static Node homeScene = PointObjectsFactory.getHomeScene();
    public static AnimEventListener animListener;
    public static AnimChannel animChannel;
    public static void showHome(String type){
        if(type.equalsIgnoreCase("tutorial"))
            PlayerListener.animChannel.setAnim("runBottom");
        else
            PlayerListener.animChannel.setAnim("sleep");
        
        
        showHighScore();
        hideWall();
        Controller.positionAtRoadChanged = new Vector3f(0,0,-56f);
        ChickenListener.chicken.setLocalTranslation(4f, 2.3f, -106f);
        PlayState.rootNode.attachChild(ChickenListener.chicken);//cicken loc set
         
         PlayerListener.bikerControl.setEnabled(false);
        //PlayerListener.bikerControl.setPhysicsLocation(new Vector3f(0,11f,0));
         PlayerListener.character.setLocalTranslation(0,2f,-108f);
         PlayerListener.character.setLocalRotation(RotationQuats.ROTATIONQUAT90);
         PlayerListener.animControl.setEnabled(true);
         
         ListenerHandler.touchListener.setEnabled(false);
        PlayState.chaseCam.setEnabled(false);
        PlayState.chaseCam.reset();
        PlayState.cam.setLocation(new Vector3f(12f,3f,-112f));
        PlayState.cam.lookAt(PlayerListener.character.getLocalTranslation(), Vector3f.UNIT_Y);
        PlayState.rootNode.attachChild(homeScene);
        /*CamAnimationsManager.homeThenRun.setStartingLocation(PlayState.cam.getLocation());
        CamAnimationsManager.homeThenRun.setDistance(new Vector3f(100f,0,0));
        CamAnimationsManager.homeThenRun.setSpeed(.999f);
        CamAnimationsManager.homeThenRun.setContinous(true);
        PlayState.chaseCam.setAnimation(CamAnimationsManager.homeThenRun);
        PlayState.chaseCam.setIsAnimated(true);*/
        //Node node = PointObjectsFactory.addQuicksand().getNode();node.setLocalTranslation(0, 2f, -112f);
        //PlayState.rootNode.attachChild(node);
    }
    
    public static void hideWall(){
         if(PrefabPool.wallNode != null){
            PrefabPool.wallNode.setLocalTranslation(0, 0,100f);
            PlayState.rootNode.detachChild(PrefabPool.wallNode);
        }
    }

    public static void detach(){
        PlayState.rootNode.detachChild(homeScene);
    }
    
    public static void showHighScore(){
    Gui.nifty.getScreen("start").findElementById("distanceHighText").getRenderer(TextRenderer.class)
            .setText(String.valueOf(StatsManager.distanceAllTimeHigh));
}
}
