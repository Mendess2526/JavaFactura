package javafactura.businessLogic.econSectors;

/**
 * Habitação
 * {@inheritDoc}
 */
public final class Habitacao extends EconSector implements Deductible {

    private static final long serialVersionUID = -3972026525416104514L;
    /**
     * The singleton instance of this sector
     */
    private static final Habitacao instance = new Habitacao();

    /** Private constructor to force singleton pattern */
    private Habitacao(){
    }

    /**
     * Returns the singleton instance of this class
     * @return the singleton instance of this class
     */
    public static Habitacao getInstance(){
        return instance;
    }

    /**
     * Method used during deserialization to maintain the singleton pattern
     * @return The singleton instance
     */
    protected Object readResolve(){
        return getInstance();
    }

    /** {@inheritDoc} */
    @Override
    public float deduction(float value, boolean interior, int aggregateSize, float coeffEmpresa, float coeffIndividual){
        float deduction = 0;
        if(aggregateSize > 6) deduction += 0.5;
        if(interior) deduction -= 0.3;
        deduction += coeffEmpresa + coeffIndividual;
        return value * deduction;
    }
}
