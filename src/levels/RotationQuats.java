/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package levels;

import com.jme3.math.FastMath;
import com.jme3.math.Quaternion;
import com.jme3.math.Vector3f;
import com.jme3.terrain.geomipmap.picking.BresenhamYUpGridTracer;
import pointsSystem.PointObjectSettings;
import pointsSystem.PointObjectSettings.Direction;


/**
 *
 * @author Miguel Martrinez
 */
public final class RotationQuats {
public static final Quaternion ROTATIONQUAT90 = new Quaternion().fromAngleAxis(FastMath.DEG_TO_RAD*90f,Vector3f.UNIT_Y);
public static final Quaternion ROTATONQUAT180 = new Quaternion().fromAngleAxis(FastMath.DEG_TO_RAD*180f, Vector3f.UNIT_Y);
public static final Quaternion ROTATIONQUAT270 = new Quaternion().fromAngleAxis(FastMath.DEG_TO_RAD*270f,Vector3f.UNIT_Y);
public static final Quaternion ROTATIONQUAT360 = new Quaternion().fromAngleAxis(FastMath.DEG_TO_RAD*360f,Vector3f.UNIT_Y);

public static final Quaternion X90 = new Quaternion().fromAngleAxis(FastMath.DEG_TO_RAD*90f,Vector3f.UNIT_X);
public static final Quaternion X180 = new Quaternion().fromAngleAxis(FastMath.DEG_TO_RAD*180f, Vector3f.UNIT_X);
public static final Quaternion X270 = new Quaternion().fromAngleAxis(FastMath.DEG_TO_RAD*270f,Vector3f.UNIT_X);
public static final Quaternion X360 = new Quaternion().fromAngleAxis(FastMath.DEG_TO_RAD*360f,Vector3f.UNIT_X);
    public RotationQuats() {
    }
    
    public static Quaternion getRotationQuatTroughDirection(Vector3f direction){
        if(direction.equals(Direction.ZPOSITIVE.vector))
            return ROTATIONQUAT360;
        else if(direction.equals(Direction.ZNEGATIVE.vector))
            return  ROTATONQUAT180;
        else if(direction.equals(Direction.XPOSITIVE.vector))
            return  ROTATIONQUAT90;
        else
            return  ROTATIONQUAT270;
    }
    
    
        
}
