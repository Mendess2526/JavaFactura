package com.github.mendess2526.javafactura.efactura;

public enum EconActivity {
    HEALTH          ("Health"),
    FAMILY          ("Family"),
    EDUCATION       ("Education"),
    CAR_REPAIRS     ("Car Repairs"),
    HOME            ("Home"),
    BIKE_REPAIRS    ("Bike Repairs"),
    FOOD_HOUSING    ("Food and Housing"),
    HAIRCUTS        ("Haircuts"),
    VETERINARY      ("Veterinary"),
    TRANSPORT_PASS  ("Transport pass") //TODO get better name
    ;

    private String econActivity;

    EconActivity(String econActivity){

        this.econActivity = econActivity;
    }

    public String getEconActivity(){
        return this.econActivity;
    }
}
