/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package guisystem;

import com.jme3.material.RenderState;
import de.lessvoid.nifty.Nifty;
import de.lessvoid.nifty.effects.Effect;
import de.lessvoid.nifty.effects.EffectEventId;
import de.lessvoid.nifty.effects.EffectImpl;
import de.lessvoid.nifty.effects.EffectProperties;
import de.lessvoid.nifty.effects.Falloff;
import de.lessvoid.nifty.elements.Element;
import de.lessvoid.nifty.elements.render.ImageRenderer;
import de.lessvoid.nifty.render.BlendMode;
import de.lessvoid.nifty.render.NiftyImage;
import de.lessvoid.nifty.render.NiftyRenderEngine;
import de.lessvoid.nifty.render.image.ImageMode;
import de.lessvoid.nifty.spi.time.TimeProvider;
import de.lessvoid.nifty.tools.Color;
import java.util.Collection;
import mygame.PlayState;


/**
 *
 * @author Miguel Martrinez
 */
public class MyPulsateEffect  implements EffectImpl{
NiftyImage cleanImage,usedImage;
Color color ;
String colorString;   
    public void setCleanImage(NiftyImage image){
        cleanImage = image;
    }
    @Override
    public void activate(Nifty nifty, Element element, EffectProperties parameter) {
        String name = parameter.getProperty("imageName");
        cleanImage = nifty.createImage(name, true);
        usedImage =nifty.createImage(name, true);
        color =  new de.lessvoid.nifty.tools.Color(parameter.getProperty("color","#ffff"));
    }

    @Override
    public void execute(Element element, float effectTime, Falloff falloff, NiftyRenderEngine r) {
       if((effectTime<.94f)){
        element.getRenderer(ImageRenderer.class).setImage(usedImage);
        usedImage.setColor(color);
       }
       else
           element.getRenderer(ImageRenderer.class).setImage(cleanImage);
       //else{
           //if(Settings.audio)
               //cleanImage = Settings.audioOnImage;
           //else
              //cleanImage = Settings.audioOffImage;
           //element.getRenderer(ImageRenderer.class).setImage(cleanImage);
       //}
       //System.out.println("effect time is   " + effectTime);
     //r.setBlendMode(BlendMode.MULIPLY);
    }

    @Override
    public void deactivate() {
        
    }
    
}
