package javafactura.businessLogic.econSectors;

public final class Educacao extends EconSector implements Deductible {

    private static final Educacao instance = new Educacao();

    public static Educacao getInstance(){
        return instance;
    }

    private Educacao(){}

    @Override
    public float deduction(float value){
        return 0;
    }
}
