package javafactura.businessLogic.econSectors;

/**
 * Lares
 * {@inheritDoc}
 */
public final class Lares extends EconSector {

    private static final long serialVersionUID = 208516646587382748L;
    /**
     * The singleton instance of this sector
     */
    private static final Lares instance = new Lares();

    /** Private constructor to force singleton pattern */
    private Lares(){
    }

    /**
     * Returns the singleton instance of this class
     * @return the singleton instance of this class
     */
    public static Lares getInstance(){
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
