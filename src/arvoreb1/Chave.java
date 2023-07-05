package arvoreb1;

/**
 * default visible to the package
 * private visible only to the class
 * public visible to the world
 * protected visible to the class and its subclasses.
 * @author hugo
 */
public class Chave {
    int valorChave;
    No filho;

    public Chave(int valorChave) {
        this.valorChave = valorChave;
    }

    public int getValorChave() {
        return valorChave;
    }

    public void setValorChave(int valorChave) {
        this.valorChave = valorChave;
    }

    public No getFilho() {
        return filho;
    }

    public void setFilho(No filho) {
        this.filho = filho;
    }
    
    @Override
    public String toString()
    {
        return " "+String.valueOf(this.valorChave)+" ";
    }
    
    
    
    
    
}
