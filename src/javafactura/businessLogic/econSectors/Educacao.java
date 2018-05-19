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
    public float deduction(float value, boolean interior, int aggregateSize, float coeffEmpresa, float coeffIndividual){
        float deduction = 0;
        if(aggregateSize > 1)
            deduction += 0.2;
        if(interior)
            deduction += 0.4;
        deduction += coeffEmpresa + coeffIndividual;
        return value * deduction;
    }
}
