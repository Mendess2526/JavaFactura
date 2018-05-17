package javafactura.businessLogic.econSectors;

public final class Cabeleireiro extends EconSector {

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
}