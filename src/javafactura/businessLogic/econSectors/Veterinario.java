package javafactura.businessLogic.econSectors;

/**
 * Veterinario
 * {@inheritDoc}
 */
public final class Veterinario extends EconSector {

    private static final long serialVersionUID = 784409379615980803L;
    /**
     * The singleton instance of this sector
     */
    private static final Veterinario instance = new Veterinario();

    /** Private constructor to force singleton pattern */
    private Veterinario(){
    }

    /**
     * Returns the singleton instance of this class
     * @return the singleton instance of this class
     */
    public static Veterinario getInstance(){
        return instance;
    }

    /**
     * Method used during deserialization to maintain the singleton pattern
     * @return The singleton instance
     */
    protected Object readResolve(){
        return getInstance();
    }

}
