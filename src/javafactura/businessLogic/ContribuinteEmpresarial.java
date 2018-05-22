package javafactura.businessLogic;

import javafactura.businessLogic.comparators.FacturaValorComparator;
import javafactura.businessLogic.econSectors.EconSector;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

/**
 * The class that represents a company in the application.
 */
public class ContribuinteEmpresarial extends Contribuinte {

    private static final long serialVersionUID = -8963955761706471101L;

    /**
     * The company's district
     */
    private final Conselho conselho;

    /**
     * Parameterized constructor
     * @param nif               The NIF
     * @param email             The email
     * @param name              The Name
     * @param address           The Address
     * @param password          The Password
     * @param fiscalCoefficient The fiscal coefficient
     * @param econActivities    The economic activities
     */
    public ContribuinteEmpresarial(String nif, String email, String name,
                                   String address, String password, float fiscalCoefficient,
                                   Collection<EconSector> econActivities, Conselho conselho){
        super(nif, email, name, address, password, fiscalCoefficient, econActivities);
        this.conselho = conselho;
    }

    /**
     * The copy constructor
     * @param contribuinteEmpresarial The object to clone
     */
    private ContribuinteEmpresarial(ContribuinteEmpresarial contribuinteEmpresarial){
        super(contribuinteEmpresarial);
        this.conselho = contribuinteEmpresarial.getConselho();
    }

    /**
     * Returns the company's district
     * @return The company's district
     */
    public Conselho getConselho(){
        return this.conselho;
    }

    /**
     * Issues a {@link Factura}
     * @param buyer       The buyer
     * @param description The description of the purchase
     * @param value       The value of the purchase
     */
    Factura issueFactura(ContribuinteIndividual buyer, String description, float value){
        Factura factura = new Factura(this, buyer, description, value);
        this.associateFactura(factura);
        buyer.associateFactura(factura);
        return factura.clone();
    }

    /**
     * Return a list of NIFs of the clients
     * @return a list of NIFs of the clients
     */
    public List<String> getClientNIFs(){
        return this.facturas.stream().map(Factura::getClientNif).collect(Collectors.toList());
    }

    /**
     * Returns a list of {@link Factura}s of a given {@link ContribuinteIndividual} emitted between 2 dates
     * @param ci   The client
     * @param from The begin date
     * @param to   The end date
     * @return A list of {@link Factura}
     */
    public List<Factura> getFacturasOfClient(ContribuinteIndividual ci, LocalDateTime from, LocalDateTime to){
        return this.facturas.stream()
                            .filter(f -> f.getCreationDate().isAfter(from))
                            .filter(f -> f.getCreationDate().isBefore(to))
                            .filter(f -> f.getClientNif().equals(ci.getNif()))
                            .map(Factura::clone)
                            .sorted(new FacturaValorComparator().reversed()).collect(Collectors.toList());
    }

    /**
     * Returns the total amount of money made
     * @return The total amount of money made
     */
    public double totalFacturado(){
        return this.facturas.stream().mapToDouble(Factura::getValue).sum();
    }

    /**
     * Returns the total amount of money made between 2 dates
     * @param from The begin date
     * @param to   The end date
     * @return The total amount of money made
     */
    public double totalFacturado(LocalDate from, LocalDate to){
        return this.facturas.stream()
                            .filter(f -> f.getCreationDate().isAfter(from.atStartOfDay()))
                            .filter(f -> f.getCreationDate().isBefore(to.atTime(LocalTime.MAX)))
                            .mapToDouble(Factura::getValue)
                            .sum();
    }

    /**
     * Creates a deep copy of the instance
     * @return a deep copy of the instance
     */
    @Override
    public ContribuinteEmpresarial clone(){
        return new ContribuinteEmpresarial(this);
    }

    /** {@inheritDoc} */
    @Override
    public String toString(){
        return "ContribuinteEmpresarial{}" + super.toString();
    }
}
