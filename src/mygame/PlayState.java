/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package mygame;

import audiosystem.AudioManager;
import listenersystem.ListenerHandler;
import cameras.MyCamera;
import maincharacter.PlayerListener;
import controllers.Controller;
import levels.LevelManager;
import com.jme3.animation.AnimChannel;
import com.jme3.animation.AnimControl;
import com.jme3.animation.AnimEventListener;
import com.jme3.app.Application;
import com.jme3.app.SimpleApplication;
import com.jme3.app.state.AbstractAppState;
import com.jme3.app.state.AppStateManager;
import com.jme3.asset.AssetManager;
import com.jme3.bounding.BoundingBox;
import com.jme3.bounding.BoundingSphere;
import com.jme3.bullet.BulletAppState;
import com.jme3.bullet.collision.shapes.CapsuleCollisionShape;
import com.jme3.bullet.collision.shapes.CollisionShape;
import com.jme3.bullet.control.CharacterControl;
import com.jme3.bullet.control.RigidBodyControl;
import com.jme3.bullet.util.CollisionShapeFactory;
import com.jme3.input.ChaseCamera;
import com.jme3.input.FlyByCamera;
import com.jme3.input.InputManager;
import com.jme3.input.KeyInput;
import com.jme3.input.TouchInput;
import com.jme3.input.controls.ActionListener;
import com.jme3.input.controls.AnalogListener;
import com.jme3.input.controls.KeyTrigger;
import com.jme3.input.controls.TouchListener;
import com.jme3.input.controls.TouchTrigger;
import com.jme3.input.event.TouchEvent;
import com.jme3.light.AmbientLight;
import com.jme3.light.DirectionalLight;
import com.jme3.material.Material;
import com.jme3.math.ColorRGBA;
import com.jme3.math.FastMath;
import com.jme3.math.Plane;
import com.jme3.math.Quaternion;
import com.jme3.math.Vector2f;
import com.jme3.math.Vector3f;
import com.jme3.post.Filter;
import com.jme3.post.FilterPostProcessor;
import com.jme3.post.filters.FogFilter;
import com.jme3.renderer.Camera;
import com.jme3.renderer.ViewPort;
import com.jme3.renderer.queue.RenderQueue;
import com.jme3.renderer.queue.RenderQueue.ShadowMode;
import com.jme3.scene.Geometry;
import com.jme3.scene.Node;
import com.jme3.scene.Spatial;
import com.jme3.scene.shape.Box;
import com.jme3.scene.shape.Quad;
import com.jme3.scene.shape.Sphere;
import com.jme3.texture.Image;
import com.jme3.texture.Texture;
import com.jme3.texture.TextureCubeMap;
import com.jme3.util.SkyFactory;
import com.jme3.util.SkyFactory.EnvMapType;
import com.jme3.water.SimpleWaterProcessor;
import com.jme3.water.WaterFilter;
import guisystem.Gui;
import java.util.Calendar;
import levels.PathConstructor;
import levels.Road;
import levels.TrackManager;
import pointsSystem.PointObjectSettings.Direction;
import pointsSystem.PointObjectsFactory;

/**
 *
 * @author Miguel Martrinez
 */
public class PlayState extends AbstractAppState {
    
   public static BulletAppState bulletAppState;
   public AppStateManager stateManager;
   public static Main main;
   public LevelManager levelManager;
   public Geometry geom,water;
   public static Node node = new Node("node"),rootNode,cube,tRex,floor,star,mazePath;
   public static boolean start=false, myCam=false;
    
  
   public static float tpf = 0;
   public static Camera cam;
   public FlyByCamera flyCam;
   public static MyCamera chaseCam;
   public static InputManager inputManager;
   public static AssetManager assetManager;
   public Calendar cal;
   public int h=0,rotationIndex=0;
   public Material mat; 
   public AmbientLight ambient;
   public DirectionalLight dLight;
   public String camDirection = "Z";
   public Node shadowNode = new Node();
   
   public static float screenWidth;
   public static float screenHeight;
   public Spatial skySpatial;
   public static GameState gameState;
   
    public PlayState(SimpleApplication app){
        main = (Main) app;
       
        levelManager = new LevelManager(main,this);
        flyCam = main.getFlyByCamera();
        flyCam.setEnabled(false);
        main.getCamera().setLocation(new Vector3f(0f,10f,30f));
        cal = Calendar.getInstance();
       cam = app.getCamera();
       assetManager = app.getAssetManager();
       stateManager = app.getStateManager();
       inputManager = app.getInputManager();
       screenWidth = main.getContext().getSettings().getWidth();
       screenHeight = main.getContext().getSettings().getHeight();
       main.getViewPort().setBackgroundColor(ColorRGBA.LightGray);
       bulletAppState = new BulletAppState();
       stateManager.attach(bulletAppState);
       
         /** A white ambient light source. */ 
    ambient = new AmbientLight();
    ambient.setColor(ColorRGBA.White);
    
  
        PlayerListener.initCharacter();
        ListenerHandler.initListeners();
        
      
        thirdPersonCam();
         
    // cam.setLocation(new Vector3f(0,10f,-10f));cam.lookAtDirection(viewDirection, Vector3f.UNIT_Y);
        final Vector3f normalScale = new Vector3f(-1, 1, 1);
      // westTex,
                //eastTex,
                //northTex,
                //southTex,
                //upTex,
                //downTex,
        Texture tex = assetManager.loadTexture("Textures/sky/skyMap.png");
        Texture texUp = assetManager.loadTexture("Textures/skyUp.jpg");
        Image image = tex.getImage();
        Image imageUp = texUp.getImage();
        image.addData(image.getData(0));
        image.addData(image.getData(0));
        image.addData(image.getData(0));
        image.addData(image.getData(0));
        image.addData(image.getData(0));
         
        Texture cubeTex = new TextureCubeMap();
        cubeTex.setImage(image);
        final Sphere sphereMesh = new Sphere(3, 3, 3f, false, true);
        Box box = new Box(10f,10f,10f);
        Geometry sky = new Geometry("Sky", sphereMesh);//sky.setLocalTranslation(0,0,50f);
        sky.setQueueBucket(RenderQueue.Bucket.Sky);
        sky.setCullHint(Spatial.CullHint.Never);
        sky.setModelBound(new BoundingSphere(Float.POSITIVE_INFINITY,Vector3f.ZERO));//(new BoundingBox(Vector3f.ZERO,100f,100f,100f)
//Float.POSITIVE_INFINITY  BoundingSphere
       // Material skyMat = new Material(assetManager, "Common/MatDefs/Misc/Unshaded.j3md");
         Material skyMat = new Material(assetManager, "MatDefs/SkyBox.j3md");
        skyMat.setVector3("NormalScale", normalScale);
        
       // cubeTex.setMagFilter(Texture.MagFilter.Bilinear);
        //cubeTex.setMinFilter(Texture.MinFilter.BilinearNoMipMaps);
        //cubeTex.setAnisotropicFilter(0);
        //cubeTex.setWrap(Texture.WrapMode.EdgeClamp);
       // skyMat.setTexture("Texture", cubeTex);
        skyMat.setTexture("Texture", cubeTex);
        skyMat.setColor("Color", MaterialHandler.FOG_COLOR);
        sky.setMaterial(skyMat);
        skySpatial = sky;//SkyFactory.createSky(assetManager, tex, false);//createSky(assetManager, tex, EnvMapType.CubeMap);
        main.getRootNode().setCullHint(Spatial.CullHint.Never);
       
    }
 
  @Override
    public void initialize(AppStateManager stateManager, Application app) {
        super.initialize(stateManager, app); //To change body of generated methods, choose Tools | Templates.
        
    }

    public void setRootNode(Node rootNode){
        this.rootNode = rootNode;
        this.rootNode.setCullHint(Spatial.CullHint.Never);
    }
    
    @Override
    public void stateAttached(AppStateManager stateManager) {
       super.stateAttached(stateManager); //To change body of generated methods, choose Tools | Templates.
       
       rootNode.addLight(ambient);
       //rootNode.addLight(dLight);
       //levelManager.initiateLevel1();
       levelManager.initPrefabs();
       rootNode.attachChild(node);
      rootNode.attachChild(skySpatial);
      AudioManager.initSounds();
      ListenerHandler.registerListeners();
      //PointObjectsFactory factory = new PointObjectsFactory(assetManager);
      //for(int i = 0; i < 1; i++)
          //rootNode.attachChild(factory.getTileNode1().clone());
    }

    @Override
    public void setEnabled(boolean enabled) {
        super.setEnabled(enabled); //To change body of generated methods, choose Tools | Templates.
       
        //PlayerListener.bikerControl.setEnabled(enabled);
        //setStart(enabled);
    }
   
    @Override
    public void update(float tpf) {this.tpf =tpf;
       super.update(tpf); //To change body of generated methods, choose Tools | Templates.
       if(PlayerListener.bikerControl!=null)
           Controller.trueBool = true;
      
       if(start){
        
          levelManager.run();
            if (myCam){
       }
    
       }
    }
    public static int number = 0;
    public static void setStart(boolean enabled){
        start = enabled;
        /*PlayState.gameState = gameState;
        if(!start){System.out.println("start set to false:   " + start + number);number++;
            
            PlayState.bulletAppState.getPhysicsSpace().remove(PlayerListener.bikerControl);
            //PlayerListener.character.removeControl(PlayerListener.bikerControl);
            //PlayerListener.bikerControl.setPhysicsLocation(new Vector3f(0,0,-60f));
           
            //if(PlayState.bulletAppState.getPhysicsSpace().getCharacterList().isEmpty())
            //throw new NullPointerException("Chartacter control is nul!");
            //PlayerListener.character.setLocalTranslation(0, 20, -60f);
        }
        else{
            PlayState.bulletAppState.getPhysicsSpace().add(PlayerListener.bikerControl);
            //PlayerListener.bikerControl.setWalkDirection(Vector3f.UNIT_Z.mult(1.3f));
            PlayerListener.bikerControl.setViewDirection(Vector3f.UNIT_Z);
            PlayerListener.bikerControl.setPhysicsLocation(new Vector3f(0,PlayerListener.bikerControl.getPhysicsLocation().y,0));
            CamController.resetRunningAlg();
            Controller.posZSettings();
            PlayState.chaseCam.setGradualRotation(Vector3f.UNIT_Z);
            ListenerHandler.changeRoadFlag = false;
            PlayerListener.bikerControl.changingRoad = false;
            chaseCam.resetAutomaticDuck();
            for(Road road: PathConstructor.roadArray)
                road.turnIndex=0;
            Controller.rotationIndex=0;
        }*/
       
    }
    
    public boolean getStart(){
        return start;
    }
    
    
   

    
    
  public void thirdPersonCam(){
      //cam.setRotation(new Quaternion().fromAngleAxis(FastMath.DEG_TO_RAD*90f, Vector3f.UNIT_Y));
      chaseCam = new MyCamera(cam, PlayerListener.character);//chaseCam.setDragToRotate(false);
      //character.setLocalRotation(cam.getRotation());
      //chaseCam.setTrailingEnabled(true);
      chaseCam.setDefaultHorizontalRotation(FastMath.DEG_TO_RAD*-90f);
      chaseCam.setDefaultVerticalRotation(FastMath.DEG_TO_RAD*45f);
      chaseCam.setDefaultDistance(19f);
      chaseCam.setLookAtAddition(5f);
      chaseCam.setCameraHeight(-3f);
      chaseCam.setTargetHeight(4f);
      chaseCam.setVRotation(0);
      //chaseCam.setSmoothMotion(true);
      ViewPort v = main.getViewPort();
      v.setBackgroundColor(ColorRGBA.Orange);
      //float near, float far, float left, float right,
            //float top, float bottom
     
      cam.setFrustum(1.0f, 2.0f,cam.getFrustumLeft(),cam.getFrustumRight(),
              cam.getFrustumTop(),cam.getFrustumBottom());
      //Must call setFrPerspective after resetting the frustrum values!!!
      cam.setFrustumPerspective(45f, (float)cam.getWidth() / cam.getHeight(), 1f, 200f);
  }
  

    
}
