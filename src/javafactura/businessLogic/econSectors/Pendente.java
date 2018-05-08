package javafactura.businessLogic.econSectors;

public final class Pendente extends EconSector {

    private static Pendente instance = new Pendente();

    public static Pendente getInstance(){
        return instance;
    }

    private Pendente(){}

}
