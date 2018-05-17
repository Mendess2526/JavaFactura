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
    public float deduction(float value, boolean interior, int aggregateSize, float coefEmpresa, float coefIndividual){
        return 0;
    }
}
