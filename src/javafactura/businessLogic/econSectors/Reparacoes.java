package javafactura.businessLogic.econSectors;

public final class Reparacoes extends EconSector implements Deductible {

    private static final Reparacoes instance = new Reparacoes();

    public static Reparacoes getInstance(){
        return instance;
    }

    private Reparacoes(){}

    @Override
    public float deduction(float value){
        return 0;
    }
}
