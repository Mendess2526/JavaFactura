package javafactura.businessLogic.econSectors;

/**
 * Saude.
 * {@inheritDoc}
 */
public final class Saude extends EconSector implements Deductible {

    private static final long serialVersionUID = 4423279153899593830L;
    /**
     * The singleton instance of this sector
     */
    private static final Saude instance = new Saude();

    /** Private constructor to force singleton pattern */
    private Saude(){
    }

    /**
     * Returns the singleton instance of this class
     * @return the singleton instance of this class
     */
    public static Saude getInstance(){
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
        if(numDependants > 1) deduction += 0.3;
        if(interior) deduction += deduction * 1.5;
        deduction += coeffEmpresa + coeffIndividual;
        return value * deduction;
    }
}
