package com.github.mendess2526.javafactura.efactura.collections;

public class Pair<A, B> {

    private A fst;
    private B snd;

    public Pair(A fst, B snd){
        this.fst = fst;
        this.snd = snd;
    }

    public Pair(Pair<A,B> pair){
        this.fst = pair.fst();
        this.snd = pair.snd();
    }

    public A fst(){
        return fst;
    }

    public void fst(A fst){
        this.fst = fst;
    }

    public B snd(){
        return snd;
    }

    public void snd(B snd){
        this.snd = snd;
    }

    @Override
    public boolean equals(Object o){
        if(this == o) return true;

        if(o == null || getClass() != o.getClass()) return false;

        Pair<?,?> pair = (Pair<?,?>) o;

        return this.fst.equals(pair.fst()) &&
               this.snd.equals(pair.snd());
    }

    @Override
    public String toString(){
        return "Pair{" +
               "fst=" + fst
               + ", snd=" + snd
               + '}';
    }

    public Pair<A,B> clone(){
        return new Pair<>(this);
    }
}
