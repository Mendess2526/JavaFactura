package javafactura.businessLogic.econSectors;

public final class Saude extends EconSector implements Deductible {

    private static final Saude instance = new Saude();
    private static final long serialVersionUID = 4423279153899593830L;

    public static Saude getInstance(){
        return instance;
    }

    private Saude(){
    }

    protected Object readResolve(){
        return getInstance();
    }

    @Override
    public float deduction(float value){
        return 0;
    }
}
