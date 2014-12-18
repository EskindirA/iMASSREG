/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.lift.massreg.util;

/**
 *
 * @author Yoseph
 */
public class Logger {

    public void logError(String err) {
        System.err.println("MASSREG ERROR:" + err);
    }

    public void logMessage(String msg) {
        System.err.println("MASSREG ERROR:" + msg);
    }
}
