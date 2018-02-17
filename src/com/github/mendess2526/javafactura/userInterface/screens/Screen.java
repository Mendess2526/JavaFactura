package com.github.mendess2526.javafactura.userInterface.screens;

public interface Screen<T> {
    void execute();
    T getResult();
}
