/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package guisystem;

import de.lessvoid.nifty.Nifty;
import de.lessvoid.nifty.effects.EffectImpl;
import de.lessvoid.nifty.effects.EffectProperties;
import de.lessvoid.nifty.effects.Falloff;
import de.lessvoid.nifty.elements.Element;
import de.lessvoid.nifty.render.NiftyRenderEngine;

/**
 *
 * @author Miguel Martrinez
 */
public class FadeEffect implements EffectImpl{

    @Override
    public void activate(Nifty nifty, Element element, EffectProperties parameter) {
        
    }

    @Override
    public void execute(Element element, float effectTime, Falloff falloff, NiftyRenderEngine r) {
        
        r.setColorAlpha(effectTime);
    }

    @Override
    public void deactivate() {
        
    }
    
}
