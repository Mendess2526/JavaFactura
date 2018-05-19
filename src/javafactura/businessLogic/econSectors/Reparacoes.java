package javafactura.businessLogic.econSectors;

public final class Reparacoes extends EconSector implements Deductible {

    private static final Reparacoes instance = new Reparacoes();
    private static final long serialVersionUID = -866084890821433731L;

    public static Reparacoes getInstance(){
        return instance;
    }

    private Reparacoes(){
    }

    protected Object readResolve(){
        return getInstance();
    }

    @Override
    public float deduction(float value, boolean interior, int aggregateSize, float coeffEmpresa, float coeffIndividual){
        float deduction = 0;
        if(aggregateSize < 2) deduction += 0.3;
        if(interior) deduction += 0.01;
        deduction += coeffEmpresa + coeffIndividual;
        return value * deduction;
    }
}
