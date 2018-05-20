package javafactura.businessLogic.comparators;

import javafactura.businessLogic.Contribuinte;
import javafactura.businessLogic.Factura;

import java.io.Serializable;
import java.util.Comparator;

/**
 * Comparator class to compare 2 {@link Contribuinte}s
 * instances based on the amount of money spent by each of them.
 */
public class ContribuinteSpendingComparator implements Comparator<Contribuinte>,
                                                       Serializable {

    private static final long serialVersionUID = -5984517538533832715L;


    /**
     * Calculates the total money spent a {@link Contribuinte}
     * @param c The {@link Contribuinte}
     * @return The total money spent
     */
    private static double getTotal(Contribuinte c){
        return c.getFacturas()
                .stream()
                .mapToDouble(Factura::getValue)
                .sum();
    }

    /** {@inheritDoc} */
    @Override
    public int compare(Contribuinte o1, Contribuinte o2){
        return Double.compare(getTotal(o1), getTotal(o2));
    }
}
