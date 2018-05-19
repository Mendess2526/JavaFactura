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
    public float deduction(float value, boolean interior, int aggregateSize, float coefEmpresa, float coefIndividual){
        float deduction = 0;
        if(aggregateSize > 1) deduction += 0.3;
        if(interior) deduction += deduction * 1.5;
        deduction += coefEmpresa + coefIndividual;
        return value * deduction;
    }
}
