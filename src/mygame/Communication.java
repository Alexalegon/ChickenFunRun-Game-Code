/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package mygame;

import guisystem.Storage;

/**
 *
 * @author Miguel Martrinez
 */
public interface Communication {
    
    public void requestNewInterstitial();
    public void showInterstitial();
    public void buyItem(PurchaseItems item);
    public Storage loadSavedSettings();
    public void goToWebsite();
}
