package javafactura.businessLogic.econSectors;

public final class Lares extends EconSector implements Deductible {

    private static Lares instance = new Lares();

    public static Lares getInstance(){
        return instance;
    }

    private Lares(){}

    @Override
    public float deduction(float value){
        return 0;
    }
}
