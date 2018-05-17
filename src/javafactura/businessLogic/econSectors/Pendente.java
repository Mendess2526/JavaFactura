package javafactura.businessLogic.econSectors;

public final class Pendente extends EconSector {

    private static final Pendente instance = new Pendente();
    private static final long serialVersionUID = -2701674423006137578L;

    public static Pendente getInstance(){
        return instance;
    }

    protected Object readResolve(){
        return getInstance();
    }

    private Pendente(){
    }

}
