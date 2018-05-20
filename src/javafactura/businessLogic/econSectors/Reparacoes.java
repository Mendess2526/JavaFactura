package javafactura.businessLogic.econSectors;

/**
 * Repara&ccedil;&otilde;es.
 * {@inheritDoc}
 */
public final class Reparacoes extends EconSector implements Deductible {

    private static final long serialVersionUID = -866084890821433731L;
    /**
     * The singleton instance of this sector
     */
    private static final Reparacoes instance = new Reparacoes();

    /** Private constructor to force singleton pattern */
    private Reparacoes(){
    }

    /**
     * Returns the singleton instance of this class
     * @return the singleton instance of this class
     */
    public static Reparacoes getInstance(){
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
        if(numDependants < 2) deduction += 0.3;
        if(interior) deduction += 0.01;
        deduction += coeffEmpresa + coeffIndividual;
        return value * deduction;
    }
}
