package javafactura.businessLogic.comparators;

import javafactura.businessLogic.Factura;

import java.io.Serializable;
import java.util.Comparator;

public class FacturaValorComparator implements Comparator<Factura>,
                                               Serializable {

    private static final long serialVersionUID = -5107287785591654861L;

    @Override
    public int compare(Factura o1, Factura o2){
        return Double.compare(o1.getValue(), o2.getValue());
    }
}
