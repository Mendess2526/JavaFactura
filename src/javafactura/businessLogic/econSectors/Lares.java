package javafactura.businessLogic.econSectors;

public final class Lares extends EconSector implements Deductible {

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

    @Override
    public float deduction(float value){
        return 0;
    }
}
