package javafactura.businessLogic.econSectors;

/**
 * Educa&ccedil;&atilde;o.
 * {@inheritDoc}
 */
public final class Educacao extends EconSector implements Deductible {

    private static final long serialVersionUID = -3203220403125757525L;
    /**
     * The singleton instance of this sector
     */
    private static final Educacao instance = new Educacao();

    /** Private constructor to force singleton pattern */
    private Educacao(){
    }

    /**
     * Returns the singleton instance of this class
     * @return the singleton instance of this class
     */
    public static Educacao getInstance(){
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
        if(numDependants > 1)
            deduction += 0.2;
        if(interior)
            deduction += 0.4;
        deduction += coeffEmpresa + coeffIndividual;
        return value * deduction;
    }
}
