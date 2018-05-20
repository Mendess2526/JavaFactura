package javafactura.businessLogic.econSectors;

import java.io.Serializable;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

/**
 * A representation of an economic sector.
 */
public abstract class EconSector implements Serializable {

    private static final long serialVersionUID = 3640154128824268337L;

    /**
     * Returns a Set of all the economic sectors
     * @return a Set of all the economic sectors
     */
    public static Set<EconSector> getAllSectors(){
        return new HashSet<>(SectorWrapper.sectors);
    }

    /**
     * Returns the instance of a economic sector from a string
     * @param name The name of the Sector
     * @return The corresponding economic sector
     */
    public static EconSector getFromString(String name){
        return SectorWrapper.STRING_ECON_SECTOR_MAP.get(name);
    }

    /** {@inheritDoc} */
    @Override
    public boolean equals(Object obj){
        return obj != null && this.getClass() == obj.getClass();
    }

    /** {@inheritDoc} */
    @Override
    public String toString(){
        return this.getClass().getSimpleName();
    }

    /**
     * Wrapper class that stores all economic sectors
     */
    private static final class SectorWrapper {

        /**
         * The Set of all sectors
         */
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

        /**
         * The HashMap that associates a String to each Economic Sector
         */
        private static final Map<String,EconSector> STRING_ECON_SECTOR_MAP = new HashMap<>();

        static{
            sectors.forEach(e -> STRING_ECON_SECTOR_MAP.put(e.toString(), e));
        }
    }
}
