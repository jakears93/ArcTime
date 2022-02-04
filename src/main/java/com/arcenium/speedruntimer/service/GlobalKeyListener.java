package com.arcenium.speedruntimer.service;
import com.arcenium.speedruntimer.controller.FrontController;
import com.arcenium.speedruntimer.config.KeyMap;
import com.arcenium.speedruntimer.utility.SettingsManager;
import javafx.application.Platform;
import org.jnativehook.GlobalScreen;
import org.jnativehook.NativeHookException;
import org.jnativehook.keyboard.NativeKeyEvent;
import org.jnativehook.keyboard.NativeKeyListener;
import java.util.logging.Logger;
import java.util.logging.LogManager;
import java.util.logging.Level;

public class GlobalKeyListener implements NativeKeyListener {
    /******************** Fields ********************/
    private final FrontController frontController;
    private final KeyMap keymap;

    /******************** Constructor/Initializer ********************/
    public GlobalKeyListener(FrontController frontController){
        this.frontController = frontController;
        this.keymap = SettingsManager.getINSTANCE().getSettings().getKeyMap();
        try{
            GlobalScreen.registerNativeHook();
        } catch (NativeHookException e){
            e.printStackTrace();
        }
        enable();

        if(!SettingsManager.getINSTANCE().getSettings().isShowKeyEventLogging()){
            disableNativeEventLogging();
        }
    }

    /******************** Enable/Disable ********************/
    public void enable(){
        GlobalScreen.addNativeKeyListener(this);
    }

    public void disable(){
        GlobalScreen.removeNativeKeyListener(this);
    }

    private void disableNativeEventLogging() {
        LogManager.getLogManager().reset();
        Logger logger = Logger.getLogger(GlobalScreen.class.getPackage().getName());
        logger.setLevel(Level.OFF);
    }

    /******************** Handlers ********************/
    @Override
    public void nativeKeyPressed(NativeKeyEvent nativeKeyEvent) {
        if(SettingsManager.getINSTANCE().getSettings().isModifyingSettings() || frontController.isPaused())   return;

        if(nativeKeyEvent.getKeyCode() == keymap.getStartKey()){
            Platform.runLater(frontController::startStopHandler);
        }
        else if(nativeKeyEvent.getKeyCode() == keymap.getPauseKey()){
            Platform.runLater(frontController::togglePauseHandler);
        }
        else if(nativeKeyEvent.getKeyCode() == keymap.getResetKey()){
            Platform.runLater(frontController::resetHandler);
        }
        else if(nativeKeyEvent.getKeyCode() == keymap.getPreviousKey()){
            Platform.runLater(frontController::previousSegmentHandler);
        }
        else if(nativeKeyEvent.getKeyCode() == keymap.getSkipKey()){
            Platform.runLater(frontController::skipSegmentHandler);
        }

        //TODO find way to re-enable when not listening, disabled for now
//        else if(nativeKeyEvent.getKeyCode() == keymap.getToggleGlobalHotkeysKey()){
//            if(isGlobalHotkeysActive)   disable();
//            else    enable();
//        }
    }
    @Override
    public void nativeKeyTyped(NativeKeyEvent nativeKeyEvent) {
    }

    @Override
    public void nativeKeyReleased(NativeKeyEvent nativeKeyEvent) {
    }
}//End of GlobalKeyListener Class
