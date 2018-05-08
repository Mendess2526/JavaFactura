package javafactura.businessLogic.econSectors;

public final class Habitacao extends EconSector implements Deductible {

    private static Habitacao instance = new Habitacao();

    public static Habitacao getInstance(){
        return instance;
    }

    private Habitacao(){}

    @Override
    public float deduction(float value){
        return 0;
    }
}
