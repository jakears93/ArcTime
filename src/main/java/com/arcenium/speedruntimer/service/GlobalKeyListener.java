package com.arcenium.speedruntimer.service;
import com.arcenium.speedruntimer.controller.FrontController;
import com.arcenium.speedruntimer.config.KeyMap;
import com.arcenium.speedruntimer.utility.SettingsManager;
import javafx.application.Platform;
import org.jnativehook.GlobalScreen;
import org.jnativehook.NativeHookException;
import org.jnativehook.keyboard.NativeKeyEvent;
import org.jnativehook.keyboard.NativeKeyListener;

public class GlobalKeyListener implements NativeKeyListener {

    private final FrontController frontController;
    private final KeyMap keymap;
    private boolean isGlobalHotkeysActive = false;



    public GlobalKeyListener(FrontController frontController){
        this.frontController = frontController;
        this.keymap = SettingsManager.getINSTANCE().getSettings().getKeyMap();
        try{
            GlobalScreen.registerNativeHook();
        } catch (NativeHookException e){
            e.printStackTrace();
        }
        enable();
    }
    @Override
    public void nativeKeyTyped(NativeKeyEvent nativeKeyEvent) {
    }

    @Override
    public void nativeKeyPressed(NativeKeyEvent nativeKeyEvent) {
        if(nativeKeyEvent.getKeyCode() == keymap.getStartKey()){
            Platform.runLater(frontController::startStopHandler);
        }
        else if(nativeKeyEvent.getKeyCode() == keymap.getToggleGlobalHotkeysKey()){
            if(isGlobalHotkeysActive)   disable();
            else    enable();
            //TODO figure out why all key presses no longer registered after disabling
        }
    }

    @Override
    public void nativeKeyReleased(NativeKeyEvent nativeKeyEvent) {
    }

    public void enable(){
        GlobalScreen.addNativeKeyListener(this);
        this.isGlobalHotkeysActive = true;
    }

    public void disable(){
        GlobalScreen.removeNativeKeyListener(this);
        this.isGlobalHotkeysActive = false;
    }

}//End of GlobalKeyListener Class
