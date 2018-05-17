package javafactura.businessLogic.comparators;

import javafactura.businessLogic.Contribuinte;

import java.io.Serializable;
import java.util.Comparator;

public class ContribuinteFacturaCountComparator implements Comparator<Contribuinte>,
                                                           Serializable {

    private static final long serialVersionUID = -2082190761448389509L;

    @Override
    public int compare(Contribuinte c1, Contribuinte c2){
        return Integer.compare(c1.getFacturas().size(), c2.getFacturas().size());
    }
}
