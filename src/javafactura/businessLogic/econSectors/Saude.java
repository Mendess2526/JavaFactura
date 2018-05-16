package javafactura.businessLogic.econSectors;

public final class Saude extends EconSector implements Deductible {

    private static final Saude instance = new Saude();

    public static Saude getInstance(){
        return instance;
    }

    private Saude(){
    }

    @Override
    public float deduction(float value){
        return 0;
    }
}
