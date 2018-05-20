package javafactura.businessLogic.econSectors;

/**
 * Cabeleireiro
 * {@inheritDoc}
 */
public final class Cabeleireiro extends EconSector {

    private static final long serialVersionUID = 2476011820086681534L;
    /**
     * The singleton instance of this sector
     */
    private static final Cabeleireiro instance = new Cabeleireiro();

    /**
     * Returns the singleton instance of this class
     * @return the singleton instance of this class
     */
    public static Cabeleireiro getInstance(){
        return instance;
    }

    private Cabeleireiro(){
    }

    /**
     * Method used during deserialization to maintain the singleton pattern
     * @return The singleton instance
     */
    protected Object readResolve(){
        return getInstance();
    }
}