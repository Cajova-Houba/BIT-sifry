package org.valesz.crypt.controller;

/**
 * Thrown when the controller is accessed by get() method before initialization.
 *
 * Created by Zdenek Vales on 19.3.2017.
 */
public class ControllerNotInitializedException extends Exception {

    public ControllerNotInitializedException(String controllerName) {
        super(controllerName+" not initialized yet!");
    }
}
