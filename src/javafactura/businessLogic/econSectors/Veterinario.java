package javafactura.businessLogic.econSectors;

public final class Veterinario extends EconSector implements Deductible {

    private static final Veterinario instance = new Veterinario();

    public static Veterinario getInstance(){
        return instance;
    }

    private Veterinario(){
    }

    @Override
    public float deduction(float value){
        return 0;
    }
}
