package javafactura.businessLogic;

@SuppressWarnings("SpellCheckingInspection")
public enum Concelho {
    BRAGA(false, "Braga"),
    LISBOA(false, "Lisboa"),
    PORTIMAO(false, "Portimão"),
    PORTO(false, "Porto"),
    FARO(false, "Faro"),
    VILA_REAL(true, "Vila Real"),
    MONCAO(true, "Monção"),
    VISEU(true, "Viseu"),
    GUARDA(true, "Guarda"),
    BEJA(true, "Beja");

    private final String name;
    private boolean interior;

    Concelho(boolean interior, String name){
        this.interior = interior;
        this.name = name;
    }

    public boolean isInterior(){
        return this.interior;
    }

    @Override
    public String toString(){
        return this.name;
    }
}
