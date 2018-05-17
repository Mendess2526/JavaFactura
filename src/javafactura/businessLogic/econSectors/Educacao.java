package javafactura.businessLogic.econSectors;

public final class Educacao extends EconSector implements Deductible {

    private static final Educacao instance = new Educacao();
    private static final long serialVersionUID = -3203220403125757525L;

    public static Educacao getInstance(){
        return instance;
    }

    private Educacao(){
    }

    protected Object readResolve(){
        return getInstance();
    }

    @Override
    public float deduction(float value, boolean interior, int aggregateSize, float coefEmpresa, float coefIndividual){
        float deduction = 0;

        return deduction;
    }
}
