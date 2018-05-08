package javafactura.businessLogic.econSectors;

public final class Reparacoes extends EconSector implements Deductible {

    private static Reparacoes instancias = new Reparacoes();

    public static Reparacoes getInstance(){
        return instancias;
    }

    private Reparacoes(){}

    @Override
    public float deduction(float value){
        return 0;
    }
}
