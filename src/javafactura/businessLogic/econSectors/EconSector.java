package javafactura.businessLogic.econSectors;

import java.io.Serializable;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

public abstract class EconSector implements Serializable {

    public static Set<EconSector> getAllSectors(){
        return new HashSet<>(SectorWrapper.sectors);
    }

    public static EconSector getFromString(String name){
        return SectorWrapper.STRING_ECON_SECTOR_MAP.get(name);
    }

    private static final class SectorWrapper {

        private static final Set<EconSector> sectors = new HashSet<>();

        static{
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

        private static final Map<String,EconSector> STRING_ECON_SECTOR_MAP = new HashMap<>();

        static{
            sectors.forEach(e -> STRING_ECON_SECTOR_MAP.put(e.toString(), e));
        }
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
