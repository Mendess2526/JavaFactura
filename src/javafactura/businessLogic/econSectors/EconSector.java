package javafactura.businessLogic.econSectors;

import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;

public abstract class EconSector implements Serializable {

    public static final String T_CODE_PENDENTE = "E00";
    public static final String T_CODE_ALOJ_REST = "E01";
    public static final String T_CODE_CABELEIREIRO = "E02";
    public static final String T_CODE_EDUCACAO = "E03";
    public static final String T_CODE_FAMILIA = "E04";
    public static final String T_CODE_HABITACAO = "E05";
    public static final String T_CODE_LARES = "E06";
    public static final String T_CODE_REPARACOES = "E07";
    public static final String T_CODE_SAUDE = "E08";
    public static final String T_CODE_VETERINARIO = "E09";

    private interface MakeEconSector {

        EconSector make();
    }

    private static final Map<String,MakeEconSector> STRING_FUNCTION_MAP = new HashMap<>();

    static{
        STRING_FUNCTION_MAP.put(T_CODE_PENDENTE, Pendente::new);
        STRING_FUNCTION_MAP.put(T_CODE_ALOJ_REST, AlojamentoRestauracao::new);
        STRING_FUNCTION_MAP.put(T_CODE_CABELEIREIRO, Cabeleireiro::new);
        STRING_FUNCTION_MAP.put(T_CODE_EDUCACAO, Educacao::new);
        STRING_FUNCTION_MAP.put(T_CODE_FAMILIA, Familia::new);
        STRING_FUNCTION_MAP.put(T_CODE_HABITACAO, Habitacao::new);
        STRING_FUNCTION_MAP.put(T_CODE_LARES, Lares::new);
        STRING_FUNCTION_MAP.put(T_CODE_REPARACOES, Reparacoes::new);
        STRING_FUNCTION_MAP.put(T_CODE_SAUDE, Saude::new);
        STRING_FUNCTION_MAP.put(T_CODE_VETERINARIO, Veterinario::new);
    }

    public static EconSector factory(String typeCode){
        MakeEconSector makeEconSector = STRING_FUNCTION_MAP.get(typeCode);
        if(makeEconSector == null) return new Pendente();
        else return makeEconSector.make();
    }

    public abstract String getTypeCode();

    public int hashCode(){
        return Integer.parseInt(getTypeCode().substring(1));
    }

    @Override
    public boolean equals(Object obj){
        return obj != null
                && this.getClass() == obj.getClass();
    }

    @Override
    public String toString(){
        return this.getClass().getSimpleName();
    }
}
