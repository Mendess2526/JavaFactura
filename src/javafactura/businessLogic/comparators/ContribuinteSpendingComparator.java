package javafactura.businessLogic.comparators;

import javafactura.businessLogic.Contribuinte;
import javafactura.businessLogic.Factura;

import java.io.Serializable;
import java.util.Comparator;

public class ContribuinteSpendingComparator implements Comparator<Contribuinte>,
                                                       Serializable {

    private static final long serialVersionUID = -5984517538533832715L;

    private static double getTotal(Contribuinte c){
        return c.getFacturas()
                .stream()
                .mapToDouble(Factura::getValue)
                .sum();
    }

    @Override
    public int compare(Contribuinte c1, Contribuinte c2){
        return Double.compare(getTotal(c1), getTotal(c2));
    }
}
