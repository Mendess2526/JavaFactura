package javafactura.businessLogic.econSectors;

/**
 * Fam&iacute;lia.
 * {@inheritDoc}
 */
public final class Familia extends EconSector implements Deductible {

    private static final long serialVersionUID = -3832979720877574853L;
    /**
     * The singleton instance of this sector
     */
    private static final Familia instance = new Familia();

    /** Private constructor to force singleton pattern */
    private Familia(){
    }

    /**
     * Returns the singleton instance of this class
     * @return the singleton instance of this class
     */
    public static Familia getInstance(){
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
    public float deduction(float value, boolean interior, int numDependants, float coeffEmpresa, float coeffIndividual){
        float deduction = 0;
        deduction += 0.1 * numDependants;
        deduction = deduction > 0.5 ? 0.5f : deduction;
        deduction += interior ? 0 : 0;
        deduction += coeffEmpresa + coeffIndividual;
        return value * deduction;
    }
}
