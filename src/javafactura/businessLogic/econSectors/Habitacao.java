package javafactura.businessLogic.econSectors;

public final class Habitacao extends EconSector implements Deductible {

    private static final Habitacao instance = new Habitacao();
    private static final long serialVersionUID = -3972026525416104514L;

    public static Habitacao getInstance(){
        return instance;
    }

    private Habitacao(){
    }

    protected Object readResolve(){
        return getInstance();
    }

    @Override
    public float deduction(float value, boolean interior, int aggregateSize, float coeffEmpresa, float coeffIndividual){
        float deduction = 0;
        if(aggregateSize > 6) deduction += 0.5;
        if(interior) deduction -= 0.3;
        deduction += coeffEmpresa + coeffIndividual;
        return value * deduction;
    }
}
