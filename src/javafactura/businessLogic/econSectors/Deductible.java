package javafactura.businessLogic.econSectors;

/**
 * Interface that define the economic sectors that can deduct
 */
public interface Deductible {

    /**
     * Method that calculates the amount deducted based on the passed values
     * @param value           The value to deduct from
     * @param interior        If the company is to be benefited
     * @param aggregateSize   The size of the family aggregate
     * @param coeffEmpresa    The fiscal coefficient of the company
     * @param coeffIndividual The fiscal coefficient of the individual
     * @return The deducted value
     */
    float deduction(float value, boolean interior, int aggregateSize, float coeffEmpresa, float coeffIndividual);
}
