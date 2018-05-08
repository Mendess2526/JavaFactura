package javafactura.businessLogic.econSectors;

public final class Cabeleireiro extends EconSector implements Deductible {

    private static Cabeleireiro instance = new Cabeleireiro();

    public static Cabeleireiro getInstance(){
        return instance;
    }

    private Cabeleireiro(){}

    @Override
    public float deduction(float value){
        return 0;
    }
}
