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
public class DuckInfo {
    static   Vector3f duckLocation = Vector3f.ZERO;
    public static void registerDuck(Vector3f location){
        duckLocation = location;
    }
    
    public static Vector3f getDuckLocation(){
        return duckLocation;
    }
}
