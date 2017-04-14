/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package guisystem;

import com.jme3.math.Vector2f;
import controllers.ControllerMath;
import de.lessvoid.nifty.Nifty;
import de.lessvoid.nifty.effects.EffectImpl;
import de.lessvoid.nifty.effects.EffectProperties;
import de.lessvoid.nifty.effects.Falloff;
import de.lessvoid.nifty.elements.Element;
import de.lessvoid.nifty.elements.render.ImageRenderer;
import de.lessvoid.nifty.render.NiftyRenderEngine;
import de.lessvoid.nifty.tools.SizeValue;
import mygame.PlayState;

/**
 *
 * @author Miguel Martrinez
 */
public class SlideEffect implements EffectImpl{
Vector2f position = new Vector2f(0,0);
float time;
float duration;
float height;
float distance;
boolean active = false;
    @Override
    public void activate(Nifty nifty, Element element, EffectProperties parameter) {
        //String string = parameter.getProperty("position");
        
        //element.getNifty().getRenderEngine().moveTo(0, -element.getHeight());
      
       setSettings(element);
       
        //position.x= string.indexOf(",");
    }
    
    public void setSettings(Element element){
        height = PlayState.main.getContext().getSettings().getHeight();
        distance = height/7;
        String y = "-" + String.valueOf(element.getHeight());
        String x = String.valueOf(PlayState.main.getContext().getSettings().getWidth()/2-element.getWidth()/2);
       element.setConstraintY(new SizeValue(y));
       element.setConstraintX(new SizeValue(x));
       element.layoutElements();
       active = true;
    }

    @Override
    public void execute(Element element, float effectTime, Falloff falloff, NiftyRenderEngine r) {
        if(!active)
            setSettings(element);
        position.y = effectTime* 2 * distance;
        if(position.y > distance){
            
            position.y = (1- effectTime) * 2 * distance;
            
        }
        
        r.moveTo(0, position.y);
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
