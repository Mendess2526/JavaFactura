package javafactura.businessLogic.econSectors;

public final class Familia extends EconSector implements Deductible {

    private static Familia instance = new Familia();

    public static Familia getInstance(){
        return instance;
    }

    private Familia(){}

    @Override
    public float deduction(float value){
        return 0;
    }
}
