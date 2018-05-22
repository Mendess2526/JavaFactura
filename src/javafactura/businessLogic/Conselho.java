package javafactura.businessLogic;

@SuppressWarnings("SpellCheckingInspection")
public enum Conselho {
    BRAGA(false),
    LISBOA(false),
    PORTIMAO(false),
    PORTO(false),
    FARO(false),
    VILA_REAL(true),
    OVIBEJA(true),
    VISEU(true),
    GUARDA(true),
    BEJA(true);

    private boolean interior;

    Conselho(boolean interior){
        this.interior = interior;
    }

    public boolean isInterior(){
        return this.interior;
    }
}
