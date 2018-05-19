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
    public float deduction(float value, boolean interior, int aggregateSize, float coeffEmpresa, float coeffIndividual){
        float deduction = 0;
        deduction += 0.1 * aggregateSize;
        deduction = deduction > 0.5 ? 0.5f : deduction;
        deduction += interior ? 0 : 0;
        deduction += coeffEmpresa + coeffIndividual;
        return value * deduction;
    }
}
