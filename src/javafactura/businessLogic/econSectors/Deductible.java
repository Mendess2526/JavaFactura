package javafactura.businessLogic.econSectors;

public interface Deductible {

    float deduction(float value, boolean interior, int aggregateSize, double coefEmpresa, double coefIndividual);
}
