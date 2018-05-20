package javafactura.businessLogic.econSectors;

/**
 * Pendente.
 * {@inheritDoc}
 */
public final class Pendente extends EconSector {

    private static final long serialVersionUID = -2701674423006137578L;
    /**
     * The singleton instance of this sector
     */
    private static final Pendente instance = new Pendente();

    /** Private constructor to force singleton pattern */
    private Pendente(){
    }

    /**
     * Returns the singleton instance of this class
     * @return the singleton instance of this class
     */
    public static Pendente getInstance(){
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
