package com.github.mendess2526.javafactura.userInterface.screens;

import java.util.Map;
import java.util.HashMap;
import java.util.Scanner;

public class Form implements Screen<Map<Integer,String>>{
    private String name;
    private Map<Integer,String> fields;
    private Map<Integer,String> answers;

    public Form(String name, Map<Integer,String> fields){
        this.name = name;
        this.fields = fields;
        this.answers = new HashMap<>();
        for(Integer key : fields.keySet()){
            this.answers.put(key, null);
        }
    }

    public void execute(){
        System.out.println("**** "+this.name+" ****");
        for(Integer key: fields.keySet()){
            System.out.println(this.fields.get(key));
            this.answers.replace(key,readField());
        }
    }

    private String readField(){
        try(Scanner is = new Scanner(System.in)){
            return is.nextLine();
        }
    }

    @Override
    public Map<Integer,String> getResult(){
        return new HashMap<>(this.answers);
    }
}
