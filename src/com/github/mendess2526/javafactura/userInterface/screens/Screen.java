package com.github.mendess2526.javafactura.userInterface.screens;

public interface Screen<T> {

    /**
     * Runs the screen and presents the screen
     */
    void execute();

    /**
     * Returns the results of executing the screen
     * @return The results of executing the screen
     */
    T getResult();
}
