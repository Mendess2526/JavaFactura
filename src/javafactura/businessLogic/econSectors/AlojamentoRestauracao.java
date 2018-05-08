package javafactura.businessLogic.econSectors;

public final class AlojamentoRestauracao extends EconSector implements Deductible {

    private static final AlojamentoRestauracao instance = new AlojamentoRestauracao();

    public static AlojamentoRestauracao getInstance(){
        return instance;
    }

    private AlojamentoRestauracao(){
    }

    @Override
    public float deduction(float value){
        return 0;
    }
}
