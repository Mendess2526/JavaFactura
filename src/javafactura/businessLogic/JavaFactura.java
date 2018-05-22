package javafactura.businessLogic;

import javafactura.businessLogic.collections.Pair;
import javafactura.businessLogic.comparators.ContribuinteFacturaCountComparator;
import javafactura.businessLogic.comparators.ContribuinteSpendingComparator;
import javafactura.businessLogic.econSectors.EconSector;
import javafactura.businessLogic.econSectors.Pendente;
import javafactura.businessLogic.exceptions.*;

import java.io.*;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.*;
import java.util.stream.Collectors;

public class JavaFactura implements Serializable {

    private static final long serialVersionUID = -1056267908102555293L;
    /**
     * Name of the save state file
     */
    private static final String SAVE_STATE_FILE = "javaFactura.dat";
    /**
     * Map of all registered {@link Contribuinte}s
     */
    private final Map<String,Contribuinte> contribuintes;
    /**
     * The admin account
     */
    private final Admin admin;
    /**
     * The currently logged in user
     */
    private User loggedInUser;

    /**
     * The application interface constructor
     */
    private JavaFactura(){
        this.contribuintes = new HashMap<>();
        generateContribuintes();
        generateFacturas();
        this.loggedInUser = null;
        this.admin = new Admin();
    }

    /**
     * Attempts to load a previous state of the application
     * @return An instance of the application interface
     */
    public static JavaFactura loadState(){
        JavaFactura javaFactura;
        try(ObjectInputStream is = new ObjectInputStream(new FileInputStream(SAVE_STATE_FILE))){
            javaFactura = (JavaFactura) is.readObject();
            javaFactura.loggedInUser = null;
        }catch(FileNotFoundException e){
            javaFactura = new JavaFactura();
        }catch(IOException | ClassNotFoundException e){
            File file = new File(SAVE_STATE_FILE);
            if(file.exists() && file.delete()){
                System.out.println("Deleted data");
                javaFactura = new JavaFactura();
            }else{
                System.out.println("\033[31mCould not delete \033[0m" + SAVE_STATE_FILE);
                javaFactura = new JavaFactura();
            }
        }
        return javaFactura;
    }

    /**
     * Returns the currently logged in user
     * @return The currently logged in user
     */
    public User getLoggedUser(){
        if(this.loggedInUser == null) return null;
        return this.loggedInUser.clone();
    }

    /**
     * Returns the type of logged in user
     * @return The type of logged in user
     */
    public Class<? extends User> getLoggedUserType(){
        if(this.loggedInUser == null) return null;
        return this.loggedInUser.getClass();
    }

    /**
     * @return The currently logged in user NIF
     */
    public String getLoggedUserNif(){
        if(this.loggedInUser == null) return null;
        return this.loggedInUser.getNif();
    }

    /**
     * Attempts to log in
     * If the nif is "admin" an admin login will be attempted
     * @param nif      The nif
     * @param password The password
     * @throws InvalidCredentialsException if the log in fails
     */
    public void login(String nif, String password) throws InvalidCredentialsException{
        if(nif.equals("admin") && password.equals(this.admin.getPassword())){
            this.loggedInUser = this.admin;
        }else{
            Contribuinte user = this.contribuintes.get(nif);
            if(user == null || !user.getPassword().equals(password))
                throw new InvalidCredentialsException();
            this.loggedInUser = user;
        }
    }

    /**
     * Logs out
     */
    public void logout(){
        this.loggedInUser = null;
    }

    /**
     * Registers a new {@link ContribuinteIndividual} in the system
     * @param nif               The nif
     * @param email             The email
     * @param nome              The name
     * @param address           The address
     * @param password          The password
     * @param fiscalCoefficient The fiscal coefficient
     * @param familyAggregate   The NIFs of the family aggregate
     * @param numDependants     The number of dependants
     * @param econSectors       The economic sectors this individual is allowed to deduct from
     * @throws InvalidNumberOfDependantsException if the number of dependants is higher then the size
     *                                            of the family aggregate
     * @throws ContribuinteAlreadyExistsException if a {@link Contribuinte} with given {@code nif} already
     *                                            exists in the system
     */
    public void registarIndividual(String nif, String email, String nome, String address, String password,
                                   float fiscalCoefficient, Collection<String> familyAggregate, int numDependants,
                                   Set<EconSector> econSectors) throws
                                                                InvalidNumberOfDependantsException,
                                                                ContribuinteAlreadyExistsException{
        if(this.contribuintes.containsKey(nif)) throw new ContribuinteAlreadyExistsException();
        this.contribuintes.put(nif, new ContribuinteIndividual(
                nif,
                email,
                nome,
                address,
                password,
                numDependants, familyAggregate,
                fiscalCoefficient,
                econSectors));
    }

    /**
     * Registers a new {@link ContribuinteEmpresarial} in the system
     * @param nif               The nif
     * @param email             The email
     * @param nome              The name
     * @param address           The address
     * @param password          The password
     * @param fiscalCoefficient The fiscal coefficient
     * @param econSectors       The economic sectors this company operates on
     * @param conselho          The conselho the {@link ContribuinteEmpresarial} is located in
     * @throws ContribuinteAlreadyExistsException if a {@link Contribuinte} with given {@code nif} already
     *                                            exists in the system
     */
    public void registarEmpresarial(String nif, String email, String nome,
                                    String address, String password, float fiscalCoefficient,
                                    Set<EconSector> econSectors, Conselho conselho) throws
                                                                                    ContribuinteAlreadyExistsException{
        if(this.contribuintes.containsKey(nif)) throw new ContribuinteAlreadyExistsException();
        this.contribuintes.put(nif, new ContribuinteEmpresarial(
                nif,
                email,
                nome,
                address,
                password,
                fiscalCoefficient,
                econSectors,
                conselho));
    }

    /**
     * Returns a set of all {@link EconSector}s
     * @return A set of all {@link EconSector}s
     */
    public Set<EconSector> getAllSectors(){
        Set<EconSector> allSectors = EconSector.getAllSectors();
        allSectors.remove(Pendente.getInstance());
        return allSectors;
    }

    /**
     * Returns the instance of a economic sector from a string
     * @param name The name of the Sector
     * @return The corresponding economic sector
     */
    public EconSector getSectorFromString(String name){
        return EconSector.getFromString(name);
    }

    /**
     * Changes the logged user's email
     * @param newEmail The new email
     */
    public void changeEmail(String newEmail){
        if(this.loggedInUser instanceof Contribuinte)
            ((Contribuinte) this.loggedInUser).setEmail(newEmail);
    }

    /**
     * Changes the logged user's password
     * @param newPassword The new password
     */
    public void changePassword(String newPassword){
        this.loggedInUser.setPassword(newPassword);
    }

    /**
     * Changes the logged user's address
     * @param newAddress The new address
     */
    public void changeAddress(String newAddress){
        if(this.loggedInUser instanceof Contribuinte)
            ((Contribuinte) this.loggedInUser).setAddress(newAddress);
    }

    /**
     * Issues a new {@link Factura}
     * @param clientNif   The client
     * @param value       The value of the purchase
     * @param description The description of the purchase
     * @return A copy of the issued {@link Factura}
     *
     * @throws NotEmpresaException       if the logged user is not a {@link ContribuinteEmpresarial}
     * @throws NoSuchIndividualException if the {@code clientNif} is not the nif is not from a registered
     *                                   {@link ContribuinteIndividual}
     * @throws NotIndividualException    if the {@code clientNif} does not correspond to
     *                                   a {@link ContribuinteIndividual}
     */
    public Factura emitirFactura(String clientNif, float value, String description) throws
                                                                                    NotEmpresaException,
                                                                                    NoSuchIndividualException,
                                                                                    NotIndividualException{
        if(!(this.loggedInUser instanceof ContribuinteEmpresarial)) throw new NotEmpresaException();
        Contribuinte client = this.contribuintes.get(clientNif);
        if(client == null) throw new NoSuchIndividualException();
        if(!(client instanceof ContribuinteIndividual)) throw new NotIndividualException();
        return ((ContribuinteEmpresarial) this.loggedInUser)
                .issueFactura((ContribuinteIndividual) client, description, value);
    }

    /**
     * Changes the {@link EconSector} of the given {@link Factura}
     * @param factura    The factura to change
     * @param econSector The sector to set
     * @return A copy of the changed {@link Factura}
     *
     * @throws NotIndividualException     if the logged in user is not a {@link ContribuinteIndividual}
     * @throws InvalidEconSectorException if the {@code econSector} is not allowed for this factura
     *                                    {@link Factura#getPossibleEconSectors() getPossibleEconSectors}
     */
    public Factura changeFactura(Factura factura, EconSector econSector) throws NotIndividualException,
                                                                                InvalidEconSectorException{
        if(!(this.loggedInUser instanceof ContribuinteIndividual)) throw new NotIndividualException();
        return ((ContribuinteIndividual) this.loggedInUser).changeFatura(factura, econSector);
    }

    /**
     * Returns the {@link Factura}s of the logged user
     * @return The {@link Factura}s of the logged user
     *
     * @throws NotContribuinteException if the logged user is not a {@link Contribuinte}
     */
    public List<Factura> getLoggedUserFacturas() throws NotContribuinteException{
        if(!(this.loggedInUser instanceof Contribuinte)) throw new NotContribuinteException();
        return new ArrayList<>(((Contribuinte) this.loggedInUser).getFacturas());
    }

    /**
     * Returns a sorted list of  {@link Factura}s of the logged user
     * @param c The comparator used for sorting
     * @return A list of {@link Factura}
     *
     * @throws NotContribuinteException if the logged user is not a {@link Contribuinte}
     */
    public List<Factura> getLoggedUserFacturas(Comparator<Factura> c) throws NotContribuinteException{
        if(!(this.loggedInUser instanceof Contribuinte)) throw new NotContribuinteException();
        return ((Contribuinte) this.loggedInUser).getFacturas(c);
    }

    /**
     * Returns a list of  {@link Factura}s of the logged user between 2 dates
     * @param from The begin date
     * @param to   The end date
     * @return A list of  {@link Factura}s of the logged user between 2 dates
     *
     * @throws NotContribuinteException if the logged user is not a {@link Contribuinte}
     */
    public List<Factura> getLoggedUserFacturas(LocalDate from, LocalDate to) throws NotContribuinteException{
        if(!(this.loggedInUser instanceof Contribuinte)) throw new NotContribuinteException();
        return ((Contribuinte) this.loggedInUser).getFacturas(from.atStartOfDay(), to.atTime(LocalTime.MAX));
    }

    /**
     * Returns a sorted list of  {@link Factura}s of the logged user between 2 dates
     * @param c    The comparator used for sorting
     * @param from The begin date
     * @param to   The end date
     * @return A sorted list of  {@link Factura}s of the logged user between 2 dates
     *
     * @throws NotContribuinteException if the logged user is not a {@link Contribuinte}
     */
    public List<Factura> getLoggedUserFacturas(Comparator<Factura> c, LocalDate from, LocalDate to)
            throws NotContribuinteException{
        if(!(this.loggedInUser instanceof Contribuinte)) throw new NotContribuinteException();
        return ((Contribuinte) this.loggedInUser).getFacturas(c, from.atStartOfDay(), to.atTime(LocalTime.MAX));
    }

    /**
     * Returns the list of {@link Factura}s of the logged in company that were issued to a given individual in a
     * given time frame
     * @param client The client
     * @param from   The begin date
     * @param to     The end date
     * @return A list of {@link Factura}
     *
     * @throws NotEmpresaException if the logged in user is not a {@link ContribuinteEmpresarial}
     */
    public List<Factura> getFaturasOfIndividual(ContribuinteIndividual client, LocalDate from, LocalDate to) throws
                                                                                                             NotEmpresaException{
        if(!(this.loggedInUser instanceof ContribuinteEmpresarial)) throw new NotEmpresaException();
        return ((ContribuinteEmpresarial) this.loggedInUser).getFacturasOfClient(client, from.atStartOfDay(),
                                                                                 to.atTime(LocalTime.MAX));
    }

    /**
     * Returns the accumulated deduction for the logged in user
     * @return The accumulated deduction for the logged in user
     *
     * @throws NotIndividualException if the logged in user is not a {@link ContribuinteIndividual}
     */
    public double getAccumulatedDeduction() throws NotIndividualException{
        if(!(this.loggedInUser instanceof ContribuinteIndividual)) throw new NotIndividualException();
        return ((ContribuinteIndividual) this.loggedInUser).getAccumulatedDeduction();
    }

    /**
     * Returns the accumulated deduction for the logged in user's family aggregate
     * @return The accumulated deduction for the logged in user's family aggregate
     *
     * @throws NotIndividualException if the logged in user is not a {@link ContribuinteIndividual}
     */
    public double getAccumulatedDeductionFamilyAggregate() throws NotIndividualException{
        if(!(this.loggedInUser instanceof ContribuinteIndividual)) throw new NotIndividualException();
        Set<String> nifList = ((ContribuinteIndividual) this.loggedInUser).getFamilyAggregate();
        double total = 0.0;
        for(String nif : nifList){
            try{
                total += ((ContribuinteIndividual) this.contribuintes.get(nif)).getAccumulatedDeduction();
            }catch(ClassCastException ignored){
            }
        }
        return total;
    }

    /**
     * Returns the list of {@link ContribuinteIndividual}s the logged user has issued {@link Factura}s to
     * @return The list of {@link ContribuinteIndividual}s the logged user has issued {@link Factura}s to
     *
     * @throws NotEmpresaException if the logged in user is not a {@link ContribuinteEmpresarial}
     */
    public Set<ContribuinteIndividual> getClients() throws NotEmpresaException{
        if(!(this.loggedInUser instanceof ContribuinteEmpresarial)) throw new NotEmpresaException();
        return ((ContribuinteEmpresarial) this.loggedInUser).getClientNIFs()
                                                            .stream()
                                                            .map(this.contribuintes::get)
                                                            .filter(ContribuinteIndividual.class::isInstance)
                                                            .map(ContribuinteIndividual.class::cast)
                                                            .map(ContribuinteIndividual::clone)
                                                            .collect(Collectors.toSet());
    }

    /**
     * Returns the total amount of money made by the logged user
     * @return The total amount of money made by the logged user
     *
     * @throws NotEmpresaException if the logged in user is not a {@link ContribuinteEmpresarial}
     */
    public double totalFaturado() throws NotEmpresaException{
        if(!(this.loggedInUser instanceof ContribuinteEmpresarial)) throw new NotEmpresaException();
        return ((ContribuinteEmpresarial) this.loggedInUser).totalFacturado();
    }

    /**
     * Returns the total amount of money made between 2 dates by the logged user
     * @param from The begin date
     * @param to   The end date
     * @return The total amount of money made between 2 dates by the logged user
     *
     * @throws NotEmpresaException if the logged in user is not a {@link ContribuinteEmpresarial}
     */
    public double totalFaturado(LocalDate from, LocalDate to) throws NotEmpresaException{
        if(!(this.loggedInUser instanceof ContribuinteEmpresarial)) throw new NotEmpresaException();
        return ((ContribuinteEmpresarial) this.loggedInUser).totalFacturado(from, to);
    }

    /**
     * Returns a sorted list of the 10 {@link ContribuinteIndividual} that have spent more money
     * @return A sorted list of the 10 {@link ContribuinteIndividual} that have spent more money
     *
     * @throws NotAdminException if the logged in user is not an {@link Admin}
     */
    public List<ContribuinteIndividual> getTop10Contrib() throws NotAdminException{
        if(!(this.loggedInUser instanceof Admin)){
            throw new NotAdminException();
        }
        PriorityQueue<ContribuinteIndividual> top10
                = new PriorityQueue<>(10, new ContribuinteSpendingComparator().reversed());
        this.contribuintes.values()
                          .stream()
                          .filter(ContribuinteIndividual.class::isInstance)
                          .forEach(c -> top10.add((ContribuinteIndividual) c));
        return top10.stream().limit(10).map(ContribuinteIndividual::clone).collect(Collectors.toList());
    }

    /**
     * Return the top X {@link ContribuinteEmpresarial} that have emitted more {@link Factura}s
     * each of them associated with the amount of money made by them
     * @param x The size of the list
     * @return The top X {@link ContribuinteEmpresarial}
     *
     * @throws NotAdminException if the logged in user is not an {@link Admin}
     */
    public List<Pair<ContribuinteEmpresarial,Double>> getTopXEmpresas(int x) throws NotAdminException{
        if(!(this.loggedInUser instanceof Admin)) throw new NotAdminException();

        PriorityQueue<ContribuinteEmpresarial> topX =
                new PriorityQueue<>(x, new ContribuinteFacturaCountComparator().reversed());

        this.contribuintes.values().stream()
                          .filter(ContribuinteEmpresarial.class::isInstance)
                          .forEach(c -> topX.add((ContribuinteEmpresarial) c));

        return topX.stream()
                   .limit(x)
                   .map(c -> new Pair<>(c.clone(), c.totalFacturado()))
                   .collect(Collectors.toList());
    }

    /**
     * Saves the current state of the application to a file
     * @throws IOException if an I/O error occurs while writing to file
     */
    public void saveState() throws IOException{
        ObjectOutputStream os = new ObjectOutputStream(new FileOutputStream(SAVE_STATE_FILE));
        os.writeObject(this);
        os.close(); //ObjectOutputStream::close ja faz flush [ObjectOutputStream.java:740]
    }

    /**
     * Generate dummy {@link Contribuinte}s
     */
    private void generateContribuintes(){
        String[] names = new String[]{
                "Zero", "Um", "Dois", "Três", "Quatro", "Cinco", "Seis", "Sete", "Oito", "Nove",
                };
        Random r = new Random();
        Set<EconSector> allSectors = getAllSectors();
        Iterator<EconSector> it = allSectors.iterator();
        for(int i = 0; i < 100; i++){ //Change this to change the amount of people
            if(!it.hasNext()) it = allSectors.iterator();
            int j = i;
            StringBuilder nameBuilder = new StringBuilder();
            do{
                nameBuilder.insert(0, names[j % 10] + " ");
                j /= 10;
            }
            while(j > 0);
            String name = nameBuilder.toString();
            String nif = String.valueOf(i);
            //noinspection SpellCheckingInspection
            String email = String.format("%s%s%s", i % 2 == 1 ? "indiv." : "empr.",
                                         name.toLowerCase().replace(" ", ""), "@email.com");
            String address = "Rua " + name;
            String pass = "pass";
            float fiscalCoefficient = r.nextFloat() % 0.3f;
            // Escolhe setores aleatoriamente
            Set<EconSector> econSectors = new HashSet<>();
            j = i;
            for(EconSector allSector : allSectors)
                if(r.nextInt(10) > ((i % 2 == 1) ? 5 : j++)) econSectors.add(allSector);
            if(econSectors.isEmpty()) econSectors.add(it.next());
            if(i % 2 == 1){
                try{
                    List<String> aggregate = new ArrayList<>();
                    //Adiciona todos os individuais anteriores ao agregado
                    for(j = i - 2; j > 0; j -= 2) aggregate.add(String.valueOf(j));
                    int numDependants = aggregate.size() > 0 ? r.nextInt(aggregate.size()) : 0;
                    registarIndividual(nif, email, name, address, pass, fiscalCoefficient, aggregate, numDependants,
                                       econSectors);
                }catch(InvalidNumberOfDependantsException | ContribuinteAlreadyExistsException e){
                    e.printStackTrace();
                }
            }else{
                try{
                    registarEmpresarial(nif, email, name, address, pass, fiscalCoefficient, econSectors,
                                        Conselho.values()[r.nextInt(Conselho.values().length)]);
                }catch(ContribuinteAlreadyExistsException e){
                    e.printStackTrace();
                }
            }
        }
    }

    /**
     * Generate dummy {@link Factura}s
     */
    private void generateFacturas(){
        User u = this.loggedInUser;
        List<String> issuers = this.contribuintes.values()
                                                 .stream()
                                                 .filter(ContribuinteEmpresarial.class::isInstance)
                                                 .map(Contribuinte::getNif)
                                                 .collect(Collectors.toList());
        List<String> clients = this.contribuintes.values()
                                                 .stream()
                                                 .filter(ContribuinteIndividual.class::isInstance)
                                                 .map(Contribuinte::getNif)
                                                 .collect(Collectors.toList());

        Random r = new Random();
        for(int i = 0; i < 40; i++){
            String issuerNif = issuers.get(new Random().nextInt(issuers.size()));
            String clientNif = clients.get(new Random().nextInt(clients.size()));
            String description = issuerNif + " -> " + clientNif;
            float value = r.nextFloat() * 500;

            this.loggedInUser = this.contribuintes.get(issuerNif);
            try{
                Factura f = emitirFactura(clientNif, value, description);
                if(value % 3 == 0) changeFactura(f, (EconSector) f.getPossibleEconSectors().toArray()[0]);
            }catch(NotContribuinteException | NoSuchIndividualException | InvalidEconSectorException e){
                e.printStackTrace();
            }
        }
        this.loggedInUser = u;
    }
}
