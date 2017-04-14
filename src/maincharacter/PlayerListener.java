/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package maincharacter;

import com.jme3.animation.AnimChannel;
import com.jme3.animation.AnimControl;
import com.jme3.animation.AnimEventListener;
import com.jme3.bullet.collision.shapes.CapsuleCollisionShape;
import com.jme3.material.Material;
import com.jme3.math.ColorRGBA;
import com.jme3.math.Vector3f;
import com.jme3.scene.Node;
import com.jme3.texture.Texture;
import controllers.Controller;
import static controllers.Controller.gravity;
import levels.RotationQuats;
import levels.RunningAlgsFacility;
import levels.TrackManager;
import mygame.PlayState;
import static mygame.PlayState.assetManager;
import static mygame.PlayState.bulletAppState;
import static mygame.PlayState.node;
import pointsSystem.PointObjectSettings.Direction;

/**
 *
 * @author Miguel Martrinez
 */
public final class PlayerListener {
   public static AnimControl animControl;
   public static AnimChannel animChannel;
   public static AnimChannel animChannel2;
   public static AnimEventListener animListener;
   public static BikerCharacterControl bikerControl;
   public static Node character,clone;
   public static CustomAnimationControl customAnimControl = new CustomAnimationControl();
   static CapsuleCollisionShape capsule = new CapsuleCollisionShape(3.5f*.2f,14f*.2f,1);
   static CapsuleCollisionShape duckingCapsule = new CapsuleCollisionShape(3.5f*.2f,14f*.025f,1);
   static boolean running = true;
   
   public static void initCharacter(){
       
    character = (Node) assetManager.loadModel("Models/cowboy/cowboy.j3o");
    //clone = (Node) assetManager.loadModel("Models/cowboy/cowboy.j3o");node.attachChild(clone);clone.scale(.35f);
    //character = (Node) assetManager.loadModel("Models/bikerCharacter/bikerCharacter.j3o");
    character.scale(.35f);
        character.setLocalTranslation(0,2f,-22f);
        node.attachChild(character);
        //Material mat = new Material(assetManager,"Common/MatDefs/Light/Lighting.j3md");
       // Texture tex = assetManager.loadTexture("Models/bikerCharacter/bikerNormals.png");
       // mat.setTexture("NormalMap", tex);
        //mat.setColor("Ambient", ColorRGBA.Blue);
        //mat.setColor("Diffuse", ColorRGBA.Brown);
        //mat.setBoolean("UseMaterialColors", true);
        //character.setMaterial(mat);
        //character.scale(.1f);
        
        bikerControl = new BikerCharacterControl(capsule,0.05f);
        bikerControl.setPhysicsLocation(new Vector3f(0,0,-56));
        bikerControl.setGravity(0f);//5 
        bikerControl.setJumpSpeed(25f);//15
        bikerControl.setFallSpeed(1000f);
        character.addControl(bikerControl);
        bulletAppState.getPhysicsSpace().add(bikerControl);
       // bulletAppState.setDebugEnabled(true);
        animControl = character.getChild("cowboy").getControl(AnimControl.class);
        //animControl = character.getChild("Cube").getControl(AnimControl.class);
        animChannel = animControl.createChannel();
        animChannel2 = animControl.createChannel();
        animChannel.setAnim("runBottom");
       // animChannel2.setAnim("runTop2");
        animChannel.setSpeed(3f);
        setAnimListener();
        animControl.addListener(animListener);
        customAnimControl.setEnabled(false);
        PlayerListener.character.addControl(PlayerListener.customAnimControl);

   }
   
   public static void setAnimListener(){
        
       animListener = new AnimEventListener(){

           @Override
           public void onAnimCycleDone(AnimControl control, AnimChannel channel, String animName) {
               if(animName.equalsIgnoreCase("jump") || animName.equalsIgnoreCase("catchChicken")){
                   channel.setAnim("runBottom");
                   channel.setSpeed(3f);
                   //PlayerListener.animChannel2.setAnim("runTop");
               }
               else if(animName.equalsIgnoreCase("duck")){
                  bikerControl.scaleBounds(1f, 1f, 1f);
                  PlayState.chaseCam.setCameraHeight(-3f);
                  channel.setAnim("runBottom");
                  channel.setSpeed(3f);
                  //PlayerListener.animChannel2.setAnim("runTop");
               }
               else if(animName.equalsIgnoreCase("runBottom")){
                  
               }
           }

           @Override
           public void onAnimChange(AnimControl control, AnimChannel channel, String animName) {
               if(animName.equalsIgnoreCase("jump")){
                   channel.setAnim("runBottom");
                   channel.setSpeed(3f);
                   PlayState.chaseCam.setCameraHeight(-3f);
                  // PlayerListener.animChannel2.setAnim("runTop");
               }
               else if(animName.equalsIgnoreCase("duck")){
                  bikerControl.scaleBounds(1f, 1f, 1f);
                  channel.setAnim("runBottom");
                  channel.setSpeed(3f);
                  //PlayerListener.animChannel2.setAnim("runTop");
               }
           }
           
       };
    }
   
   public static void setRunning(boolean running){
       PlayerListener.running = running;
   }
   
   public static void run(){
       if(running){
       Vector3f location = PlayerListener.bikerControl.getPhysicsLocation();
        RunningAlgsFacility.getAlgByDirection(TrackManager.direction)
                    .setPlayerLocTroughGravity(location, gravity, Controller.gravity, 2f);
       }
   }
   
   public static void ensureCorrectRotation(Direction direction){
       bikerControl.setViewDirection(direction.vector);
   }
}
