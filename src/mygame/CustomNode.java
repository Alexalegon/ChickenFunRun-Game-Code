/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package mygame;

import com.jme3.math.Quaternion;
import com.jme3.math.Vector3f;

/**
 *
 * @author Miguel Martrinez
 */
public interface CustomNode {
    public void setLocalTranslation(Vector3f location);
    public void setLocalTranslation(float x, float y, float z);
    public void setLocalRotation(Quaternion quaterninon);
}
