package javafactura.businessLogic.econSectors;

public final class Cabeleireiro extends EconSector implements Deductible {

    private static final Cabeleireiro instance = new Cabeleireiro();
    private static final long serialVersionUID = 2476011820086681534L;

    public static Cabeleireiro getInstance(){
        return instance;
    }

    private Cabeleireiro(){
    }

    protected Object readResolve(){
        return getInstance();
    }

    @Override
    public float deduction(float value){
        return 0;
    }
}
