/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package levels;

import com.jme3.math.Vector3f;
import com.jme3.scene.Node;
import java.util.ArrayList;
import mygame.CustomNode;
import pointsSystem.PointObjectSettings.Direction;

/**
 *
 * @author Miguel Martrinez
 */
public final class SplineFactory {
    public static Curve curve = new Curve(90f);
    public static class Curve{
        public float degrees;

        public Curve(float degrees) {
            this.degrees = degrees;
        }
        
        public void arrangeObjectsFromFirst(Node[] nodes, float distance){
            float degreesPerObject = degrees/(nodes.length-1);
            
        }
        public void arrangeObjectsFromFirst(ArrayList<Node> nodeList){
            
        }
        
        public <T extends CustomNode> void  arrangeObjectsFromMid(ArrayList<T> customNodes, float distance, float height, Vector3f midLocation,Direction direction){
            float degreesPerObject = degrees/(customNodes.size()-1);
            float distanceBetweenObject = distance/(customNodes.size()-1);
            float heightPerObject = height/((customNodes.size()-1)/2);
            
            if(direction.equals(Direction.ZPOSITIVE))
                midLocation.z = midLocation.z - (distance/2);
            else if(direction.equals(Direction.XPOSITIVE))
                midLocation.x = midLocation.x - (distance/2);
            else if(direction.equals(Direction.XNEGATIVE))
                midLocation.x = midLocation.x + (distance/2);
            else if(direction.equals(Direction.ZNEGATIVE))
                midLocation.z = midLocation.z + (distance/2);
            
            float currentDegree = 0;
            float newHeight = 0;
            float newHorizontal = 0;
            
                for(CustomNode node: customNodes){
                    if(currentDegree <= degrees/2f){
                        
                        if(direction.equals(Direction.ZPOSITIVE))
                            node.setLocalTranslation(midLocation.x, midLocation.y + newHeight, midLocation.z + newHorizontal);
                        else if(direction.equals(Direction.XPOSITIVE))
                            node.setLocalTranslation(midLocation.x + newHorizontal,midLocation.y + newHeight,midLocation.z);
                        else if(direction.equals(Direction.XNEGATIVE))
                            node.setLocalTranslation(midLocation.x - newHorizontal,midLocation.y + newHeight,midLocation.z);
                        else
                            node.setLocalTranslation(midLocation.x, midLocation.y + newHeight,midLocation.z - newHorizontal);
                        
                        currentDegree += degreesPerObject;
                        if(currentDegree <= degrees/2f){
                                newHeight += heightPerObject;
                                newHorizontal += distanceBetweenObject;
                        }
                    }
                    else{
                        newHeight -= heightPerObject;
                        newHorizontal +=  distanceBetweenObject;
                        if(direction.equals(Direction.ZPOSITIVE))
                            node.setLocalTranslation(midLocation.x, midLocation.y + newHeight, midLocation.z + newHorizontal);
                        else if(direction.equals(Direction.XPOSITIVE))
                            node.setLocalTranslation(midLocation.x + newHorizontal,midLocation.y + newHeight,midLocation.z);
                        else if(direction.equals(Direction.XNEGATIVE))
                            node.setLocalTranslation(midLocation.x - newHorizontal,midLocation.y + newHeight,midLocation.z);
                        else
                            node.setLocalTranslation(midLocation.x, midLocation.y + newHeight,midLocation.z - newHorizontal);
                        
                    }
                }
        }
        
    }
    
    public static float cubicInterpolation(float t){
        return -2*t*t*t + 3 *t*t;
    }
}
