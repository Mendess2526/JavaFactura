package javafactura.businessLogic.comparators;

import javafactura.businessLogic.Contribuinte;
import javafactura.businessLogic.Factura;

import java.io.Serializable;
import java.util.Comparator;

/**
 * Comparator class to compare 2 {@link Contribuinte}s
 * instances based on the number of {@link Factura}s they each have.
 */
public class ContribuinteFacturaCountComparator implements Comparator<Contribuinte>,
                                                           Serializable {

    private static final long serialVersionUID = -2082190761448389509L;

    /** {@inheritDoc} */
    @Override
    public int compare(Contribuinte o1, Contribuinte o2){
        return Integer.compare(o1.getFacturas().size(), o2.getFacturas().size());
    }
}
