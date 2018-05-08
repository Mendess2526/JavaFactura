package javafactura.businessLogic.econSectors;

import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;

public abstract class EconSector implements Serializable {

    private static final Set<EconSector> sectors = new HashSet<>();
    static {
        sectors.add(AlojamentoRestauracao.getInstance());
        sectors.add(Cabeleireiro.getInstance());
        sectors.add(Educacao.getInstance());
        sectors.add(Familia.getInstance());
        sectors.add(Habitacao.getInstance());
        sectors.add(Lares.getInstance());
        sectors.add(Pendente.getInstance());
        sectors.add(Reparacoes.getInstance());
        sectors.add(Saude.getInstance());
        sectors.add(Veterinario.getInstance());
    }

    public static Set<EconSector> getAllSectors(){
        return new HashSet<>(sectors);
    }

    @Override
    public boolean equals(Object obj){
        return obj != null && this.getClass() == obj.getClass();
    }

    @Override
    public String toString(){
        return this.getClass().getSimpleName();
    }
}
