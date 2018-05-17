package javafactura.businessLogic.econSectors;

public final class Veterinario extends EconSector {

    private static final Veterinario instance = new Veterinario();
    private static final long serialVersionUID = 784409379615980803L;

    public static Veterinario getInstance(){
        return instance;
    }

    private Veterinario(){
    }

    protected Object readResolve(){
        return getInstance();
    }

}
