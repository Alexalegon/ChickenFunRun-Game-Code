/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package aicharacters;


import com.jme3.animation.AnimChannel;
import com.jme3.animation.AnimControl;
import com.jme3.animation.AnimEventListener;
import com.jme3.math.Vector3f;
import com.jme3.scene.Node;
import mygame.PlayState;

/**
 *
 * @author Miguel Martrinez
 */
public class ChickenListener {
    public static Node chicken;
    public static AnimEventListener animListener;
    public static AnimChannel animChannel;
    public static AnimControl animControl;
    public static float walkDistance = 0;
    public static SimpleControl simpleControl;
    
    public static void init(){
        chicken = (Node) PlayState.assetManager.loadModel("Models/chicken/chicken.j3o");
        chicken.scale(.5f);
        animControl = chicken.getChild("Sphere").getControl(AnimControl.class);
        animChannel = animControl.createChannel();
        setupAnimListener();
        animChannel.setAnim("walk");
        simpleControl = new SimpleControl();
        simpleControl.setFloor(2f);
        chicken.addControl(simpleControl);
        animControl.addListener(animListener);
    }
    
    public static void setupAnimListener(){
        animListener = new AnimEventListener() {

            @Override
            public void onAnimCycleDone(AnimControl control, AnimChannel channel, String animName) {
                if(animName.equalsIgnoreCase("caught")){
                    channel.setAnim("walk");
                    PlayState.rootNode.detachChild(ChickenListener.chicken);
                }
            }

            @Override
            public void onAnimChange(AnimControl control, AnimChannel channel, String animName) {
                
            }
        };
    }
    
    public static void setEnabled(boolean enabled){
        simpleControl.setEnabled(enabled);
        animControl.setEnabled(enabled);
    }
    
    public static float walk(float speed){
        float distance = PlayState.tpf * speed;
        ChickenListener.walkDistance += distance; 
        return ChickenListener.walkDistance;
    }
}
