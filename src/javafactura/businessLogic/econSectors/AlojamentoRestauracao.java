package javafactura.businessLogic.econSectors;

public final class AlojamentoRestauracao extends EconSector {

    private static final AlojamentoRestauracao instance = new AlojamentoRestauracao();
    private static final long serialVersionUID = 2324835756438756007L;

    public static AlojamentoRestauracao getInstance(){
        return instance;
    }

    private AlojamentoRestauracao(){
    }

    protected Object readResolve(){
        return getInstance();
    }

}
