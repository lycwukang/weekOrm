package wuk.week.orm;

public class Pair<T, F> {

    private T t;
    private F f;

    public Pair(T t, F f) {
        this.t = t;
        this.f = f;
    }

    public T getT() {
        return t;
    }

    public void setT(T t) {
        this.t = t;
    }

    public F getF() {
        return f;
    }

    public void setF(F f) {
        this.f = f;
    }
}
