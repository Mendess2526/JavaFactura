package javafactura.businessLogic;

public enum Conselho {
    BRAGA(false),
    BEJA(true);

    private boolean interior;

    Conselho(boolean interior){
        this.interior = interior;
    }

    public boolean isInterior(){
        return this.interior;
    }
}
