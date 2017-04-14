/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package guisystem;

import com.jme3.math.FastMath;
import controllers.ControllerMath;
import de.lessvoid.nifty.Nifty;
import de.lessvoid.nifty.effects.EffectImpl;
import de.lessvoid.nifty.effects.EffectProperties;
import de.lessvoid.nifty.effects.Falloff;
import de.lessvoid.nifty.elements.Element;
import de.lessvoid.nifty.elements.render.ImageRenderer;
import de.lessvoid.nifty.render.NiftyRenderEngine;
import mygame.Main;

/**
 *
 * @author Miguel Martrinez
 */
public class ShakeEffect implements EffectImpl{
float distance = 40;
float lastDistanceX= 0;
float lastDistanceY= 0;
float duration = 1f;
float time;float x=0;float y=0;
    @Override
    public void activate(Nifty nifty, Element element, EffectProperties parameter) {
      
    }

    @Override
    public void execute(Element element, float effectTime, Falloff falloff, NiftyRenderEngine r) {
        //float x = -distance + FastMath.rand.nextFloat()*2f * distance;
        //float y = -distance + FastMath.rand.nextFloat()*2f * distance;
        float X,Y;
        if(time > 1f){
            x = 0f;
            y = -40f;
            time = 0;
        }
        time+=Main.tpf;
        float alpha = calculateAlpha(time,duration);
        X = ControllerMath.lerp(lastDistanceX, x, alpha);
        Y = ControllerMath.lerp(lastDistanceY, y, alpha);
           r.moveTo(X,Y);
        
        
     
    }
    
    public float calculateAlpha(float current, float goal){
        if(time > duration)
            return 1f;
         return time/duration;
     }
    @Override
    public void deactivate() {
        
    }
    
}
