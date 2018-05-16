package javafactura.businessLogic.comparators;

import javafactura.businessLogic.Factura;

import java.util.Comparator;

public class FacturaValorComparator implements Comparator<Factura> {

    @Override
    public int compare(Factura o1, Factura o2){
        return Double.compare(o1.getValue(), o2.getValue());
    }
}
