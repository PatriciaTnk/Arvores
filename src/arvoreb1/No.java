package arvoreb1;

import java.util.ArrayList;

public class No {
    ArrayList<Chave> Chaves;
    No esquerda;
    No pai;    
    int qtdeChaves;
    boolean folha;
    
    public ArrayList<Chave> getChaves() {
        return Chaves;
    }

    public void setPai(No pai) {
        this.pai = pai;
    }

    public void setQtdeChaves() {
        this.qtdeChaves = this.Chaves.size();
    }
    
//    construtor, onde será criado o obj arraylist do tipo Chave
//    e também setará a variável folha como true
    public No() {
        this.Chaves = new ArrayList();
        this.folha = true;
    }
        
    //aqui é feita a divisão de um nó(estouro do nó)
    public No divideNo(int ordem) {
        No novo = new No();
        novo.pai = this.pai;

        int meio = (int) Math.ceil(ordem / 2.0);
        int tam = this.getChaves().size();

        //é nesse for que será feito o transbordo dos elementos do nó antigo
        //para o nó novo.
        for (; tam > meio; tam--) {
            //entra neste if se a Chave na posição (meio) do nó tiver um filho
            if (this.getChaves().get(meio).filho != null) {
                this.getChaves().get(meio).filho.pai = novo;
            }
            //remove o elemento do nó que sofreu a divisão e coloca o elemento
            //removido No novo nó.
            
            novo.getChaves().add(this.getChaves().remove(meio));
        }
        //atualizando a quantidade de Chaves do novo nó
        novo.qtdeChaves = novo.getChaves().size();
        //e também do nó que sofreu a divisão
        this.qtdeChaves = this.getChaves().size();
        //só entra neste if se não for uma folha.
        if (!this.folha) {
            novo.esquerda = this.getChaves().get(this.qtdeChaves - 1).filho;
            novo.esquerda.pai = novo;//verificar se houver erro de null pointer
        }

        novo.folha = this.folha;

        return novo;
    }
    
    //método que irá atualizar a nova raiz.
    public No ajustaRaiz(No novo) {

        //criando um novo nó que será a nova raiz.
        No novaRaiz = new No();
        //removendoo o maior elemento da folha e passando para a raiz
        novaRaiz.getChaves().add(this.getChaves().remove(this.qtdeChaves - 1));
        (this.qtdeChaves)--;
        novaRaiz.esquerda = this;
        novaRaiz.getChaves().get(0).filho = novo;

        this.pai = novaRaiz;
        novo.pai = novaRaiz;

        novaRaiz.folha = false;
        novaRaiz.qtdeChaves = 1;

        return novaRaiz;
    }
    
    @Override
    public String toString() {
        String texto = "{";
        if (this.pai != null) {
            texto += " noPai:" + this.pai.getChaves().get(0).valorChave + " ##";
        } else {
            texto += " noPai:null ##";
        }
        
        if (this.esquerda != null) {
            texto += " noEsquerdaComeçaCom:" + this.esquerda.getChaves().get(0).valorChave + " ##";
        } else {
            texto += " noEsquerdaComeçaCom:null ##";
        }

        texto += " Chaves [";
        for (Chave aux : this.getChaves()) {
            texto += (aux.toString());
        }

        texto += "]}    ";
        return texto;
    }
    
    //////////////////////////////////////////////////////////////////////////
    //////////////////codigo novo/////////////////////////////////////////////
    //esse método é para retornar o índice da chave a ser removida do nó 
    public int buscarIndicedaChavenoNoh(int valor){
        int i = 0;
        while (valor != this.getChaves().get(i).getValorChave()) {            
            i++;
        }
        return i;
    }
    
    //////////////////////////////////////////////////////////////////////////
    //////////////////codigo novo/////////////////////////////////////////////
    //método para verificar se um nó está subutilizado.
    public boolean subUtilizada(int ordem){
        double qtdeminima;
        qtdeminima = Math.ceil((float)ordem/2) - 1;
        
        return this.qtdeChaves < qtdeminima;
        
    }
    
    //método para verificar se um nó está excedendo a quantidade mínima de chaves.
    public boolean excedendoMinimo(int ordem){
        double qtdeminima;
        qtdeminima = Math.ceil((float)ordem/2) - 1;
        
        return this.qtdeChaves > qtdeminima;
        
    }
    
    public Chave removerChaveEatualizarNo(int ind){
        Chave ch = this.getChaves().remove(ind);
        //atualizando a quantidade de chaves da folha
        this.setQtdeChaves();
        return ch;        
    }
    
    ////////////////////////////////////////////////////////////////////////// 
    //////////////////codigo novo/////////////////////////////////////////////
    
}
