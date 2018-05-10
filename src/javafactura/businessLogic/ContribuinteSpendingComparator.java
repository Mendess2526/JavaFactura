package javafactura.businessLogic;

import java.util.Comparator;

public class ContribuinteSpendingComparator implements Comparator<Contribuinte> {

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
