package javafactura.businessLogic.econSectors;

public final class Familia extends EconSector implements Deductible {

    private static final Familia instance = new Familia();
    private static final long serialVersionUID = -3832979720877574853L;

    public static Familia getInstance(){
        return instance;
    }

    private Familia(){
    }

    protected Object readResolve(){
        return getInstance();
    }

    @Override
    public float deduction(float value, boolean interior, int aggregateSize, double coefEmpresa, double coefIndividual){
        return 0;
    }
}
