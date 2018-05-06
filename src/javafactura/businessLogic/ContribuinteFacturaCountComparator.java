package javafactura.businessLogic;

import java.util.Comparator;

public class ContribuinteFacturaCountComparator implements Comparator<Contribuinte> {

    @Override
    public int compare(Contribuinte c1, Contribuinte c2){
        return Integer.compare(c1.getFacturas().size(), c2.getFacturas().size());
    }
}
