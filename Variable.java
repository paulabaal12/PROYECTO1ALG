//Paula Barillas, Ana Paula Navas, Nicolle Gordillo
public class Variable<T> {
    private T content;
    public String name;
    
    public Variable(T content, String name){
        this.content = content;
        this.name = name;
    }
    /*
    regresa el tipo del valor contenido
    */
    public Class ContentType(){
        return content.getClass();
    
    }
    public T getValue(){
        return content;
    }
    
}

