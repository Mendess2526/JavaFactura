package javafactura.businessLogic.econSectors;

/**
 * Alojamento e Restauração
 * {@inheritDoc}
 */
public final class AlojamentoRestauracao extends EconSector {

    private static final long serialVersionUID = 2324835756438756007L;
    /**
     * The singleton instance of this sector
     */
    private static final AlojamentoRestauracao instance = new AlojamentoRestauracao();

    /** Private constructor to maintain singleton pattern */
    private AlojamentoRestauracao(){
    }

    /**
     * Returns the singleton instance of this class
     * @return the singleton instance of this class
     */
    public static AlojamentoRestauracao getInstance(){
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
