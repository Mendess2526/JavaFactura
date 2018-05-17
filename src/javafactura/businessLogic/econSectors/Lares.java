package javafactura.businessLogic.econSectors;

public final class Lares extends EconSector {

    private static final Lares instance = new Lares();
    private static final long serialVersionUID = 208516646587382748L;

    public static Lares getInstance(){
        return instance;
    }

    private Lares(){
    }

    protected Object readResolve(){
        return getInstance();
    }
}
