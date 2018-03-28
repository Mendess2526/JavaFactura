package com.github.mendess2526.javafactura.userInterface.screens;

import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;

public class Form implements Screen<Map<Integer,String>>{
    private final String name;
    private final Map<Integer,String> fields;
    private final Map<Integer,String> answers;

    public Form(String name, Map<Integer,String> fields){
        this.name = name;
        this.fields = fields;
        this.answers = new HashMap<>();
        for(Integer key : fields.keySet()){
            this.answers.put(key, null);
        }
    }

    @Override
    public void execute(){
        System.out.println("**** " + this.name + " ****");
        try(Scanner is = new Scanner(System.in)){
            for(Integer key : fields.keySet()){
                System.out.println(this.fields.get(key));
                this.answers.replace(key, is.nextLine());
            }
        }
    }

    @Override
    public Map<Integer,String> getResult(){
        return new HashMap<>(this.answers);
    }
}
