/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package pointsSystem;

import com.jme3.asset.AssetManager;
import com.jme3.bounding.BoundingBox;
import com.jme3.bullet.collision.shapes.CollisionShape;
import com.jme3.bullet.control.RigidBodyControl;
import com.jme3.bullet.util.CollisionShapeFactory;
import com.jme3.effect.ParticleEmitter;
import com.jme3.effect.ParticleMesh;
import com.jme3.effect.ParticleMesh.Type;
import com.jme3.effect.shapes.EmitterShape;
import com.jme3.effect.shapes.EmitterSphereShape;
import com.jme3.export.JmeExporter;
import com.jme3.export.JmeImporter;
import com.jme3.material.Material;
import com.jme3.material.RenderState;
import com.jme3.math.ColorRGBA;
import com.jme3.math.FastMath;
import com.jme3.math.Quaternion;
import com.jme3.math.Vector3f;
import com.jme3.renderer.RenderManager;
import com.jme3.renderer.ViewPort;
import com.jme3.renderer.queue.RenderQueue;
import com.jme3.scene.Geometry;
import com.jme3.scene.Node;
import com.jme3.scene.Spatial;
import com.jme3.scene.VertexBuffer;
import com.jme3.scene.control.AbstractControl;
import com.jme3.scene.control.Control;
import com.jme3.scene.shape.Quad;
import java.io.IOException;
import java.util.List;
import levels.RotationQuats;
import mygame.MaterialHandler;
import mygame.PlayState;
import obstaclesystem.ObstacleDescription;
import obstaclesystem.ObstacleNode;
import obstaclesystem.ObstacleSettings;
import pointsSystem.PointObjectSettings.Direction;

/**
 *
 * @author Miguel Martrinez
 */
public class PointObjectsFactory {
public static AssetManager assetManager;
    public PointObjectsFactory(AssetManager assetManager) {
        this.assetManager = assetManager;
    }
    
    public static Node getHomeScene(){
        Node tile = (Node) assetManager.loadModel("Scenes/homeMenu.j3o");
        tile.setLocalTranslation(0,1f,-110f);
        
        Node standingRock = getStandingRock();
        standingRock.setLocalTranslation(-35f, 5f, 20f);
        standingRock.setLocalRotation(RotationQuats.ROTATIONQUAT90);
        tile.attachChild(standingRock);
        
        ObstacleNode obs = addFirePlace();
        obs.setLocalTranslation(-2f, 0, -2f);
        obs.getNode().scale(.6f);
        obs.getNode().getChild(0).setLocalTranslation(0,3f,0f);
        tile.attachChild(obs.getNode());
        
        ObstacleNode fire2 = addFirePlace();
        fire2.getNode().setLocalTranslation(-15f, 0, -3f);
        fire2.getNode().getChild(0).setLocalTranslation(0,1f,0);
        //tile.attachChild(fire2.getNode());
        
        ObstacleNode o = addQuicksand();
        o.setLocalTranslation(-22f,0f,12f);
        tile.attachChild(o.getNode());
        
        Node tumbleweed = addTumbleweed();
        tumbleweed.setLocalTranslation(-17f,1f,0);
        tile.attachChild(tumbleweed);
        RotationControl control = new RotationControl(true);
        control.setPathOrigin(0,1f,-18f);
        control.setPathDistance(36f);
        tumbleweed.addControl(control);
        //control.setSpatial(tumbleweed);
        return tile;
    }
    
    public Node getTileNode(){
        Node tile = (Node) assetManager.loadModel("Models/Scenes/newScene1.j3o");
       
        
        setFogColor(tile);
        return tile;
    }
    
    public Node getSaloonTile(){
        //Node tile = (Node) assetManager.loadModel("Models/splineEx/splineEx.j3o");
       // Node tile = (Node) assetManager.loadModel("Models/tile1a/tile1a.j3o");"Scenes/newScene1.j3o"
        Node tile = (Node) assetManager.loadModel("Scenes/saloon.j3o");
        setFogColor(tile);
        return tile; 
    }
    
    
    public Node getOasisTile(){
        Node tile = (Node) assetManager.loadModel("Scenes/oasis.j3o");
        Node n = (Node) tile.getChild("Models/oasisTile/oasisTile.j3o");
        if(n == null)
            throw new AssertionError("Fucked up node");
        Node s = (Node) n.getChild("Scene");
        if(s == null)
            throw new AssertionError("Fucked up node");
       Node j = (Node) n.getChild("water");
       if(j == null)
            throw new AssertionError("Fucked up node");
        
        Material mat = new Material(assetManager,"MatDefs/cartoonWater.j3md");
        mat.setTexture("ColorMap", assetManager.loadTexture("Textures/waterInk3.png"));
         mat.setFloat("ScrollSpeed", -.22f);
         //mat.setFloat("Alpha", .5f);
        j.getChild("waterPlane1").setMaterial(mat);
        
        setFogColor(tile);
        return tile;
    }
    
    public static Node getStandingRock(){
        Node standing = (Node) assetManager.loadModel("Models/standingRock/standingRock.j3o");
        return standing;
    }
    
    public Node getRocksTile(){
        Node tile = (Node) assetManager.loadModel("Scenes/rocks.j3o");
        setFogColor(tile);
        return tile;
    }
    
    public Node getTreesTile(){
        Node tile = (Node)assetManager.loadModel("Scenes/trees.j3o");
        
        //tree.setMaterial(mat);
        setFogColor(tile);
        return tile;
    }
    
    public Node getCactusTile(){
        Node tile = (Node) assetManager.loadModel("Scenes/cactus.j3o");
        setFogColor(tile);
        return tile;
    }
    
    public Node getArcsTile(){
        Node tile = (Node) assetManager.loadModel("Scenes/arcs.j3o");
        setFogColor(tile);
        return tile;
    }
    
    public Node getTransitionNode(){
        //Node tile = (Node) assetManager.loadModel("Models/transition/transition.j3o");
        Node tile = (Node) assetManager.loadModel("Models/transitionTile/transitionTile.j3o");
        setFogColor(tile);
        return tile;
    }
    
    public static Node getWall(){
        Node tile = (Node) assetManager.loadModel("Models/Wall/Wall.j3o");
        setFogColor(tile);
        return tile;
    }
    
    public static Node getTransitionWall(){
        Node tile = (Node) assetManager.loadModel("Models/transitionWall/transitionWall.j3o");
        //setTwoFacesVisible(tile);
        CollisionShape shape = CollisionShapeFactory.createMeshShape(tile);
        RigidBodyControl body = new RigidBodyControl(shape,0);
        tile.addControl(body);
       
        Node hay = (Node) assetManager.loadModel("Models/hay/hay.j3o");
        hay.setName("hay");
        Node clone = hay.clone(false);
        clone.setLocalTranslation(0,3f,0);hay.attachChild(clone);
        CollisionShape shape2 = CollisionShapeFactory.createMeshShape(hay);
        RigidBodyControl body2 = new RigidBodyControl(shape2,0);
        hay.addControl(body2);
        tile.attachChild(hay);
        
        setFogColor(tile);
        return tile;
    }
    
    public ObstacleNode addNopalObstacle(float pointValue){
        Node node = (Node) assetManager.loadModel("Models/pricklypear/pricklypear.j3o");
        node.scale(1f,1f,3f);
        ObstacleNode obstacle = new ObstacleNode(pointValue,node,ObstacleSettings.ObstacleTypes.Jumping,ObstacleDescription.NOPAL);
        setFogColor(node);
        return obstacle;
    }
    
    public ObstacleNode addRockObstacle(float pointValue){
        Node node = (Node) assetManager.loadModel("Models/rockObstacle/rockObstacle.j3o");
        node.scale(.3f,.8f,.5f);
        ObstacleNode obstacle = new ObstacleNode(pointValue,node,ObstacleSettings.ObstacleTypes.Jumping,ObstacleDescription.JUMPROCK);
        setFogColor(node);
        return obstacle;
    }
   
    public ObstacleNode addDuckRock(float pointValue){
        Node node = (Node) assetManager.loadModel("Models/duckRock/duckRock.j3o");
        node.scale(1f,1.7f,1f);
        
        ObstacleNode obstacle = new ObstacleNode(87f, node,ObstacleSettings.ObstacleTypes.Ducking,ObstacleDescription.DUCKROCK);
        setFogColor(node);
        return obstacle;
    }
    
    public ObstacleNode addWagon(){
         Node wagon = (Node) assetManager.loadModel("Models/wagon/wagon.j3o");
         wagon.scale(3f);
         setTwoFacesVisible(wagon);
         ObstacleNode obstacle = new ObstacleNode(5f,wagon,ObstacleSettings.ObstacleTypes.Ducking,ObstacleDescription.WAGON);
         return obstacle;
     }
    
    public static ObstacleNode addFirePlace(){
         Node node = new Node();
         Node fireplace = (Node) assetManager.loadModel("Scenes/fireplace.j3o");
         fireplace.setName("wood");
         fireplace.scale(.5f);
         fireplace.setLocalTranslation(fireplace.getLocalTranslation().add(new Vector3f(0f,0f,0f)));
         ParticleEmitter fire = getParticleEmitter();
         fire.setLocalTranslation(fireplace.getLocalTranslation().add(new Vector3f(-1.0f,0,0)));
        // fire.setLocalRotation(RotationQuats.ROTATIONQUAT90);
         //fireplace.attachChild();
         node.attachChild(fire);
         node .attachChild(fireplace);
         ObstacleNode obstacle = new ObstacleNode(5f,node,ObstacleSettings.ObstacleTypes.Jumping,ObstacleDescription.FIREPLACE);
         return obstacle;
     }
     
     public static ObstacleNode addQuicksand(){
         Node quicksand = (Node) assetManager.loadModel("Models/quicksand/quicksand.j3o");
         quicksand.scale(1.6f,5f,1f);
         quicksand.setLocalTranslation(0,1f,0f);
         //BoundingBox original = (BoundingBox) quicksand.getWorldBound();
         //BoundingBox box = new BoundingBox(quicksand.getWorldBound().getCenter(),4f,4f,4f);
         //quicksand.setModelBound(box);
         //quicksand.updateGeometricState();
         Material mat = new Material(assetManager, "MatDefs/cartoonWater.j3md");
         mat.setTexture("ColorMap", assetManager.loadTexture("Models/quicksand/quicksand.png"));
         mat.setBoolean("Quicksand", true);
         //mat.setFloat("ScrollSpeed", -.1f);
         quicksand.setMaterial(mat);
         ObstacleNode obstacle = new ObstacleNode(5f,quicksand,ObstacleSettings.ObstacleTypes.Jumping,ObstacleDescription.QUICKSAND);
         obstacle.setLocationOffset(quicksand.getLocalTranslation().clone());
         return obstacle;
     }
    
    public static void setTwoFacesVisible(Node node){
         for(Spatial spatial: node.getChildren()){
             if(spatial instanceof Node)
                 setTwoFacesVisible((Node) spatial);
             else{
                 Geometry geom = (Geometry) spatial;
                 Material mat = geom.getMaterial();
                 mat.getAdditionalRenderState().setFaceCullMode(RenderState.FaceCullMode.Off);
             }
}
    }
  
    
  

    
    public PointsNode addPinata(){
        Node pinata = (Node) assetManager.loadModel("Models/pinata/pinata.j3o");
        pinata.scale(.5f);
        BoundingBox box = new 
            BoundingBox(pinata.getWorldBound().getCenter(),4f,4f,4f);
        pinata.setModelBound(box);
        pinata.updateGeometricState();
        
        PointsNode point = new PointsNode(pinata,1);
        setFogColor(pinata);
        RotationControl control = new RotationControl(false);
        pinata.addControl(control);
        return point;
    }
    
   
   
    
     
  
     
     public void scaleBoundingBox(Node node, float scale){
         BoundingBox box  = (BoundingBox)node.getWorldBound();
         box.setZExtent(box.getZExtent()* scale);
         box.setXExtent(box.getXExtent()* scale);
         
     }
     
 
     
     public static Node addTumbleweed(){
         Node tumbleweed = (Node) assetManager.loadModel("Models/tumbleweed/tumbleweed.j3o");
         setTwoFacesVisible(tumbleweed);
         return tumbleweed;
     }
     
     
     
     public Node addWaterPlane(){
         Quad quad = new Quad(50f,50f);
         Geometry geom = new Geometry("",quad);
         geom.setLocalTranslation(new Vector3f(-25f,2f,100));
         Material mat = new Material(assetManager, "MatDefs/cartoonWater.j3md");
         mat.setTexture("ColorMap", assetManager.loadTexture("Textures/waterInk.png"));
         mat.setFloat("ScrollSpeed", -.2f);
         mat.setFloat("Alpha", .5f);
         mat.getAdditionalRenderState().setBlendMode(RenderState.BlendMode.Alpha);
         geom.setMaterial(mat);
         geom.setLocalRotation(RotationQuats.X270);
         geom.setQueueBucket(RenderQueue.Bucket.Transparent);
         
         Quad quad2 = new Quad(50f,50f);
         Geometry geom2 = new Geometry("",quad2);
         geom2.setLocalTranslation(new Vector3f(-25f,2f,100));
         Material mat2 = new Material(assetManager, "MatDefs/cartoonWater.j3md");
         mat2.setTexture("ColorMap", assetManager.loadTexture("Textures/waterInk3.png"));
         mat2.setFloat("ScrollSpeed", -.1f);
         mat2.setFloat("Alpha", 1.0f);
         geom2.setMaterial(mat2);
         geom2.setLocalRotation(RotationQuats.X270);
         
         Node node = new Node();
         node.attachChild(geom);
         node.attachChild(geom2);
         return node;
     }
     
     public static ParticleEmitter getParticleEmitter(){
         ParticleEmitter fire = new ParticleEmitter("fire",Type.Triangle,100);
         Material mat = new Material(assetManager,"MatDefs/MyParticles.j3md");
         mat.setTexture("Texture", assetManager.loadTexture("Textures/fireO.png"));
         
         fire.setMaterial(mat);
         fire.setLocalTranslation(0,3f,0f);
         
         fire.setImagesX(1); fire.setImagesY(1); // 2x2 texture animation
    //fire.setEndColor(  new ColorRGBA(1f, 0f, 0f, 1f));   // red
    //fire.setStartColor(new ColorRGBA(1f, 1f, 0f, 0.5f)); // yellow
        fire.getParticleInfluencer().setInitialVelocity(new Vector3f(0f,1f,0f));
        fire.setStartSize(1.5f);
        fire.setEndSize(0.1f);
        fire.setGravity(0,0,0);
        fire.setLowLife(.5f);//0.5f
        fire.setHighLife(4f);//3f
        fire.getParticleInfluencer().setVelocityVariation(0f);
        //fire.setShape(new EmitterSphereShape(Vector3f.ZERO,2f));
        //fire.setRandomAngle(true);
        fire.getMaterial().getAdditionalRenderState().setFaceCullMode(RenderState.FaceCullMode.Off);
        fire.setQueueBucket(RenderQueue.Bucket.Transparent);
        //fire.setCullHint(Spatial.CullHint.Never);
       
         return fire;
}
     
     public Geometry getTestQuad(){
         Quad quad = new Quad(20f,20f);
         Geometry g = new Geometry("", quad);
         g.setLocalTranslation(0, 2f, 20f);
         Material mat = new Material(assetManager,"Common/MatDefs/Misc/Unshaded.j3md");
         mat.setTexture("ColorMap", assetManager.loadTexture("Textures/fireO.png"));
         mat.getAdditionalRenderState().setBlendMode(RenderState.BlendMode.Alpha);
         mat.setFloat("AlphaDiscardThreshold", .01f);
         g.setMaterial(mat);
         g.setQueueBucket(RenderQueue.Bucket.Transparent);
         g.setLocalRotation(RotationQuats.ROTATONQUAT180);
         return g;
     }
     
     public Geometry getQuad(float w, float h){
         Quad quad = new Quad(w,h);
         Geometry g = new Geometry("", quad);
         g.setLocalRotation(RotationQuats.ROTATONQUAT180);
         return g;
     }
     
     public void setRotation(Node node, String direction){
         if(direction.equalsIgnoreCase("+Z")|| direction.equalsIgnoreCase("-Z"))
             node.setLocalRotation(RotationQuats.ROTATIONQUAT360);
         else
             node.setLocalRotation(RotationQuats.ROTATIONQUAT90);
     }
     
     public static void setFogColor(Spatial nodeS){
         Node node = (Node) nodeS;
         for(Spatial spatial: node.getChildren()){
             if(spatial instanceof Node)
                 setFogColor(spatial);
             else{
                Geometry geom = (Geometry) spatial;
                Material mat = geom.getMaterial();
                if(!mat.getMaterialDef().getName().equalsIgnoreCase("unshaded"))
                    return;
                mat.setColor("FogColor", MaterialHandler.FOG_COLOR);
                mat.setFloat("FogDensity", MaterialHandler.FOG_DENSITY);
                mat.setBoolean("UseInstancing", false);
                //mat.getAdditionalRenderState().setBlendMode(RenderState.BlendMode.Alpha);
                //geom.setQueueBucket(RenderQueue.Bucket.Transparent);
             }
         }
         
         
     }
     public static class RotationControl extends AbstractControl{
         public float rotationSpeed = 1f;
         public float degrees =0;
         public float distance=0;
         public Vector3f origin =  new Vector3f(0,0,0);
         public Direction direction = Direction.ZPOSITIVE;
         public float newLocation = 0;
         public boolean movementAnim =  false;

        public RotationControl(boolean movement) {
            movementAnim = movement;
        }

        @Override
        public Control cloneForSpatial(Spatial spatial) {
            RotationControl c = new RotationControl(movementAnim);
            spatial.addControl(c);
            return c;
        }
         
         
         public void setPathDistance(float distance){
             this.distance=distance;
         }
         
         public void setPathOrigin(float x, float y, float z){
             origin.x = x;
             origin.y = y;
             origin.z = z;
         }
         
         public void setDirection(Direction direction){
             this.direction= direction;
         }
         
        @Override
        protected void controlUpdate(float tpf) {
            degrees+=70*tpf;
            Quaternion q = new Quaternion().fromAngleAxis(FastMath.DEG_TO_RAD * degrees, Vector3f.UNIT_X);
            spatial.setLocalRotation(q);
            //spatial.setLocalTranslation(Vector3f.UNIT_X);
           checkMovement(tpf);
        }
        
        public void checkMovement(float tpf){
            if(movementAnim){
                 if(direction.equals(Direction.ZPOSITIVE)){
                newLocation+=(4*tpf);
                spatial.getLocalTranslation().z=newLocation;//newLocation;System.out.println("Spatial location     "+spatial.getLocalTranslation().z);
                if(spatial.getLocalTranslation().z > origin.z+distance){
                    //spatial.getLocalTranslation().z = -10f;System.out.println("Origin is    " + origin.z);
                    newLocation = origin.z;
                }
            }
            }
        }

        @Override
        protected void controlRender(RenderManager rm, ViewPort vp) {
          
        }
         
        // public Spatial spatial;
        

       

      

        
    
}
}
