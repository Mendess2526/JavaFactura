package javafactura.businessLogic.collections;

/**
 * Simple class to represent a Pair of any 2 types
 * @param <A> Type A
 * @param <B> Type B
 */
public class Pair<A, B> {

    /**
     * First element of the pair
     */
    private final A fst;
    /**
     * Second element of the pair
     */
    private final B snd;

    /**
     * Pair constructor
     * @param fst The first element
     * @param snd The second element
     */
    public Pair(A fst, B snd){
        this.fst = fst;
        this.snd = snd;
    }

    /**
     * Get the first element of the pair
     * @return the first element
     */
    public A fst(){
        return fst;
    }

    /**
     * Get the second element of the pair
     * @return the second element
     */
    public B snd(){
        return snd;
    }

    /** {@inheritDoc} */
    @Override
    public boolean equals(Object obj){
        if(this == obj) return true;

        if(obj == null || getClass() != obj.getClass()) return false;

        Pair<?,?> pair = (Pair<?,?>) obj;

        return this.fst.equals(pair.fst()) &&
               this.snd.equals(pair.snd());
    }

    /** {@inheritDoc} */
    @Override
    public String toString(){
        return "Pair{" +
               "fst=" + fst + '\''
               + ", snd=" + snd + '\''
               + '}';
    }
}
