//Paula Barillas, Ana Paula Navas, Nicolle Gordillo
public class Pair {
    private final Object car;
    private final Object cdr;

    public Pair(Object car, Object cdr) {
        this.car = car;
        this.cdr = cdr;
    }

    
    /** 
     * @return Object
     */
    public Object car() {
        return car;
    }

    
    /** 
     * @return Object
     */
    public Object cdr() {
        return cdr;
    }
}