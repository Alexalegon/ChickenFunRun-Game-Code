/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controllers;

import com.jme3.math.Vector3f;

/**
 *
 * @author Miguel Martrinez
 */
public class JumpInfo {
  static   Vector3f jumpLocation = Vector3f.ZERO;
    public static void registerJump(Vector3f location){
        jumpLocation = location;
    }
    
    public static Vector3f getJumpLocation(){
        return jumpLocation;
    }
}
