/*
Aluna: Patrícia Tanimoto Tanaka
com edição das classes em maiusculas e minusculas
 */
package arvoreb1;

import java.util.Comparator;
import javax.swing.JFrame;
import javax.swing.JOptionPane;

public class Arvoreb1 {

    No raiz;
    int ordem;
    int altura;

    //metodos get e set
    public void setRaiz(No raiz) {
        this.raiz = raiz;
    }

    public void setOrdem(int ordem) {
        this.ordem = ordem;
    }

    public void setAltura(int altura) {
        this.altura = altura;
    }

    public void insere(int valorNovo) {
        //variável utilizada No laço while
        boolean sair = false;
        //essa função irá buscar a folha na qual o valorNovo deverá ser inserido
        //e retornará o endereço dessa folha encontrada na variável folha.
        //caso não encontre, retornará null.
        No folha = buscaFolha(valorNovo);
        //se folha for igual a null, significa que não encontrou uma folha para
        //inserir o valorNovo. Isso quer dizer que a árvore ainda não foi criada,
        //ou seja, ainda não tem raiz. 
        //Então é necessário que essa raiz seja criada.
        if (folha == null) {
            //criará um novo no, que será a raiz e também a folha atual.
            //Essa folha é onde o valorNovo será inserido.
            //No construtor do objeto do tipo nó, um arraylist de Chaves é
            //criado e também o atributo booleaNo folha é setado como true.
            raiz = folha = new No();
            //como agora temos um no, devemos atualizar a altura da árvore, que
            //será 1.
            this.altura = 1;
        }
        //ch é o objeto do tipo Chave que guardará o valorNovo. Depois o ch irá
        //para o No folha(objeto)
        //aqui é criado um novo objeto Chave, inserido o valorNovo e depois atribuído
        //ao objeto ch.
        Chave ch = new Chave(valorNovo);
        //esse laço serve para tratar a inserção em um nó(folha). Pois pode
        //acontecer de fazer uma inserção e ocorrer um estouro do nó.
        //Ficará preso neste laço enquanto sair for false.
        while (!sair) {
            //aqui vai ser feita a inserção de ch na folha.
            this.insereNaFolha(ch, folha);
            //se entrar neste if, significa que estourou a quantidade de Chaves
            //portanto deve-se organizar o nó.
            if (folha.qtdeChaves == ordem) {
                //essa variável será utilizada quando ocorrer um estouro de nó.
                //a folha será dividida e a metade dos elementos irá para o novoNo
                //caso a folha tenha ordem ímpar, a metade menor vai para o novoNo
                No novoNo = folha.divideNo(ordem);
                //se entrar neste if significa que a raiz deve ser atualizada,
                //pois houve uma divisão da raiz atual
                if (folha == raiz) {
                    //ajustaRaiz irá atualizar a raiz atual.
                    raiz = folha.ajustaRaiz(novoNo);
                    //como criou uma nova raiz, deve aumentar a altura da árvore
                    this.altura++;
                    //agora está tudo ajustado. Então pode sair.
                    sair = true;
                } else {
                    ch = folha.getChaves().remove(folha.qtdeChaves - 1);
                    folha.setQtdeChaves();
                    ch.filho = novoNo;
                    novoNo.pai = folha.pai;
                    //recurso para colocar a Chave ch na folha, que é feito na linha 70
                    //está atualizando a folha
                    folha = folha.pai;
                }

            } else {
                sair = true;
            }
        }

    }

    //essa função irá pesquisar na árvore a folha certa que o valorNovo deverá
    //ser inserido.
    private No buscaFolha(int valorNovo) {
        //na busca, deve começar pela raiz.
        No aux = raiz;
        //ficará No while enquanto aux for diferente de null e aux NÃO for uma folha
        while (aux != null && !aux.folha) {
            //esse if é para decidir se irá descer a árvore pela esquerda ou pelo
            //filho direito
            if (valorNovo < aux.getChaves().get(0).getValorChave()) {
                aux = aux.esquerda;
            } else {
                int i = 1;
                //esse while serve para testar qual o filho de qual Chave que 
                //será escolhido para o próximo teste.
                while (i < aux.qtdeChaves
                        && valorNovo > aux.getChaves().get(i).getValorChave()) {
                    i++;
                }
                aux = aux.getChaves().get(i - 1).getFilho();
            }
        }
        return aux;
    }

    private void insereNaFolha(Chave ch, No folha) {
        //aqui vai adicionar a Chave ch No nó folha.
        folha.getChaves().add(ch);
        //aqui está pedindo para ordenar as Chaves do nó folha pelo campo
        //valorChave.
        folha.getChaves().sort(Comparator.comparingInt(Chave::getValorChave));
        //aqui está alterando a quantidade de Chaves que tem No nó.
        //incrementando de 1 unidade.
        folha.setQtdeChaves();
    }

    @Override
    public String toString() {
        int nivel = 0;
        String texto = "";

        while (nivel < this.altura) {
            nivel++;
            texto += (percursoNivel(raiz, new String(), nivel) + "\n");
        }
        return texto;
    }

    public String percursoNivel(No raiz, String texto, int nivel) {
        if (raiz != null) {
            if (nivel == 1) {
                texto = texto.concat(raiz.toString() + ' ');
            } else if (nivel > 1) {
                texto = percursoNivel(raiz.esquerda, texto, nivel - 1);

                for (Chave aux : raiz.getChaves()) {
                    texto = percursoNivel(aux.filho, texto, nivel - 1);

                }
            }
        }
        return texto;
    }

    public int valorChaveMaisEsquerda() {

        No aux = raiz; //estou chamando o atributo raix do objeto arvore
        while (aux.esquerda != null) {
            aux = aux.esquerda;
        }

        return aux.getChaves().get(0).valorChave;
    }

    public int valorChaveMaisDireita() {

        No aux = raiz;
        while (!aux.folha) {
            aux = aux.getChaves().get(aux.qtdeChaves - 1).filho;
        }

        return aux.getChaves().get(aux.qtdeChaves - 1).valorChave;
    }

    public int primeiroAbaixoDoMaior() {

        No aux = raiz;
        while (!aux.folha) {
            aux = aux.getChaves().get(aux.qtdeChaves - 1).filho;
        }

        //validação no caso de receber uma raiz somente com um nó, já na interface
        if (aux.qtdeChaves > 1) { //Caso de aux ser uma folha e tiver mais de dois elementos
            return aux.getChaves().get(aux.qtdeChaves - 2).valorChave;
        }
        //Caso aux ser uma folha e tiver só UM elemento
        aux = aux.pai; //subi a altura            
        return aux.getChaves().get(aux.qtdeChaves - 1).valorChave;//retorno a "maior" chave

    }

    public No retornaIrmaoComMenosChaves(No aux, int valorBuscado) {
        int i = 0;
        int p, q;

        if (valorBuscado < aux.getChaves().get(0).valorChave) {
            aux = aux.getChaves().get(0).filho;
            return aux;
        } else if (valorBuscado > aux.getChaves().get(aux.qtdeChaves - 1).valorChave) {
            if (aux.qtdeChaves == 1) {
                aux = aux.esquerda;
                return aux;
            } else {
                aux = aux.getChaves().get(aux.qtdeChaves - 2).filho;
                return aux;
            }
        } else {
            while (i < aux.qtdeChaves - 1) {
                if (valorBuscado < (aux.getChaves().get(i + 1).valorChave)) {
                    if (i == 0) { //filho index 0
                        p = aux.esquerda.getChaves().size();
                        q = aux.getChaves().get(i + 1).filho.getChaves().size();
                        if (p < q) {
                            aux = aux.esquerda;
                            return aux;
                        } else if (q < p) {
                            aux = aux.getChaves().get(i + 1).filho;
                            return aux;
                        } else {
                            aux = aux.esquerda;
                            return aux;
                        }
                    } else {//emqualquer posição
                        p = aux.getChaves().get(i - 1).filho.getChaves().size();
                        q = aux.getChaves().get(i + 1).filho.getChaves().size();
                        if (p < q) {
                            aux = aux.getChaves().get(i - 1).filho;
                            return aux;
                        } else if (q < p) {
                            aux = aux.getChaves().get(i + 1).filho;
                            return aux;
                        } else {
                            aux = aux.getChaves().get(i - 1).filho;
                            return aux;
                        }
                    }
                }//fim do if
                ++i;
            }//fim do laço while
        }
        return aux;
    }

    public No buscaMelhorVizinho(int valorBuscado) {
        //aqui estou só percoreendo a arvore e verificando se ela tem o valorBuscado        
        No aux = raiz;
        boolean encontradoNoNo = false;

        for (int i = 0; i < raiz.qtdeChaves - 1; ++i) {
            if (valorBuscado == raiz.getChaves().get(i).valorChave) {
                return null;
            }
        }

        while (!encontradoNoNo) {

            if (valorBuscado < aux.getChaves().get(0).valorChave) {
                aux = aux.esquerda;
            } else if (valorBuscado > aux.getChaves().get(aux.qtdeChaves - 1).valorChave) {
                aux = aux.getChaves().get(aux.qtdeChaves - 1).filho;
            } else {
                for (int i = 0; i < aux.qtdeChaves - 1; ++i) {
                    if (valorBuscado > (aux.getChaves().get(i).valorChave)
                            && valorBuscado < (aux.getChaves().get(i + 1).valorChave)) {
                        aux = aux.getChaves().get(i).filho;
                        break;
                    }
                }
            }

            for (int i = 0; i < aux.getChaves().size(); ++i) {
                if (aux.getChaves().get(i).valorChave == valorBuscado) {
                    encontradoNoNo = true;
                    //aqui eu já faço ela retornar o nó pai do nó que contém a chave buscada
                    //caso queira alterar quem vai para comparar os irmãos alterar aqui
                    aux = retornaIrmaoComMenosChaves(aux.pai, valorBuscado);
                }
            }
        }
        return aux;
    }

    //////////////////////////////////////////////////////////////////////////
    //////////////////codigo novo/////////////////////////////////////////////
    //aqui será feita a remoção da chave, que será recebida no parâmetro #valor#
    //O parâmetro frame é simplesmente para ser usado no JOptionPane, e poderá
    //ser excluído após a implementação completa da função. Aqui ele é usado 
    //para testes.
    public void remover(int valor, JFrame frame) {
        Chave ch;
        //a função que segue (encontraValornoNoh) irá buscar o nó no qual foi solicitada a remoção
        //do elemento #valor#, e retornará o endereço desse nó encontrado na variável folha.
        //caso não encontre, retornará null.
        No folha = encontraValornoNoh(valor);
        if (folha == null) {
            JOptionPane.showMessageDialog(frame, "Esta chave não existe na árvore. Favor entrar com outro valor!", "B-Tree", JOptionPane.ERROR_MESSAGE);
        } else {
            //se chegar aqui, significa que o valor solicitado para ser removido existe 
            //na árvore. Agora devemos descobrir se o valor está em um nó folha
            //É AQUI QUE INICIA O ALGORITMO QUE FOI ENTREGUE. PÁG. 279 DO DROZDEK
            if (folha.folha) {
                //SE ENTROU AQUI, ENTÃO É REMOÇÃO DE UMA CHAVE QUE ESTÁ EM UM NÓ FOLHA
                JOptionPane.showMessageDialog(frame, "O valor está em um nó folha!", "B-Tree", JOptionPane.INFORMATION_MESSAGE);
                //está em um nó folha. Então vamos proceder com a remoção da
                //chave que está na folha.

                //INICIO TRECHO 1.1 DO ALGORITMO
                //primeiro temos que descobrir qual o índice do nó que está a chave 
                //a ser removida.
                int indice = folha.buscarIndicedaChavenoNoh(valor);
                //agora é feita a remoção.                 
                folha.getChaves().remove(folha.getChaves().get(indice));
                //depois da chave removida, deve-se ordenar as chaves restantes do nó
                folha.getChaves().sort(Comparator.comparingInt(Chave::getValorChave));
                //deve-se atualizar a quantidade de chaves do nó.
                folha.setQtdeChaves();
                //FIM DO TRECHO 1.1 DO ALGORITMO

                //INICIO TRECHO 1.2 DO ALGORITMO
                //agora vamos verificar se o nó está subutilizado.
                //Na verdade a verificação se o nó está pelo menos metade cheio, ou seja,
                //ainda não está subutilizado, pertence ao trecho 1.1 do algoritmo.
                //Se estiver, então entrará no IF e deverá ser feito um tratamento para o nó subutilizado.
                if (folha.subUtilizada(this.ordem)) {
                    JOptionPane.showMessageDialog(frame, "A folha está subutilizada!!!", "B-Tree", JOptionPane.INFORMATION_MESSAGE);
                    //AQUI DEVE SER IMPLEMENTADO OS TRECHOS 1.2.1, 1.2.2 E 1.2.2.1
                    //INÍCIO TRECHO 1.2.1
                    //aqui será feita a procura de um irmão da #folha# que tenha chaves excedendo 
                    //a quantidade mínima. Caso não haja, será retornado null no campo #irmao#
                    NoIrmaos estruturanoh = this.procuraIrmaoDisponivel(folha);
                    if (estruturanoh.irmao != null) {
                        //entrou aqui, então deverá ser feita uma redistribuição de chaves entre a #folha#
                        //e seu #irmao#
                        //para fazer a redistribuição de forma correta, 3 casos serão trabalhados: 
                        //1 - o nó que receberá a chave está na #esquerda#
                        //2 - o nó que receberá a chave está no indice 0 do pai - aí tem a análise 1 e análise 2, de 
                        //acordo com o que vimos em sala de aula.
                        //3 - o nó que receberá a chave está no indice 1 em diante 
                        //caso 1
                        if (estruturanoh.indiceprin == -1) {
                            //atualizando os ponteiros das chaves que serão deslocadas.
                            //aqui aponta pra ele mesmo pq depois essa chave sobe para o pai e ai ele já esta apontando para seu no filho
                            estruturanoh.irmao.getChaves().get(0).filho = estruturanoh.principal.pai.getChaves().get(0).filho;
                            estruturanoh.principal.pai.getChaves().get(0).filho = null;

                            //neste método, será removido a chave que está no índice 0 de
                            //#estruturanoh.principal# e retornará esta chave removida para 
                            //a variável ch.
                            ch = estruturanoh.principal.pai.removerChaveEatualizarNo(0);

                            //colocando ch na estruturanoh.principal
                            insereNaFolha(ch, estruturanoh.principal);

                            //neste método, será removido a chave que está no índice 0 de
                            //#estruturanoh.irmao# e retornará esta chave removida para 
                            //a variável ch.
                            ch = estruturanoh.irmao.removerChaveEatualizarNo(0);

                            //inserindo ch no indice 0 do pai
                            insereNaFolha(ch, estruturanoh.principal.pai);

                            //caso 1.2.1 - análise 1 - pega o irmão da esquerda
                        } else if ((estruturanoh.indiceprin == 0) && (estruturanoh.quelado == -1)) {
                            //atualizando os ponteiros das chaves que serão deslocadas.
                            //pego a ultima chave do irmão (-1) e coloco o filho apontando para o no que vai receber a chave
                            estruturanoh.irmao.getChaves().get(estruturanoh.irmao.qtdeChaves - 1).filho = estruturanoh.principal.pai.esquerda;
                            estruturanoh.principal.pai.esquerda = null;

                            ch = estruturanoh.principal.pai.removerChaveEatualizarNo(0);
                            //tirando a chave indice 0 do pai e colocando na #folha#
                            insereNaFolha(ch, estruturanoh.principal);

                            ch = estruturanoh.irmao.removerChaveEatualizarNo(estruturanoh.irmao.qtdeChaves - 1);
                            //tirando a chave no último indice do irmão e colocando no indice 0 do pai
                            insereNaFolha(ch, estruturanoh.principal.pai);

                            //caso 1.2.1 - análise 2 - pega do irmão à direita
                        } else if ((estruturanoh.indiceprin == 0) && (estruturanoh.quelado == 1)) {
                            ////to be done....
                            //pego a chave que subir para o pai e aponto para a chave que vai receber a chave
                            //quem vai subir é a chave 0 do nó irmão
                            estruturanoh.irmao.getChaves().get(0).filho = estruturanoh.principal.pai.getChaves().get(estruturanoh.indiceprin).filho;
                            estruturanoh.principal.pai.getChaves().get(estruturanoh.indiceprin).filho = null;

                            ch = estruturanoh.principal.pai.removerChaveEatualizarNo(1);
                            //tirando a chave indice 0 do pai e colocando na #folha#
                            insereNaFolha(ch, estruturanoh.principal);

                            ch = estruturanoh.irmao.removerChaveEatualizarNo(0);
                            //tirando a chave no último indice do irmão e colocando no indice 0 do pai
                            insereNaFolha(ch, estruturanoh.principal.pai);

                            //caso 1.2.1 - o indice da chave no nó pai é maior igual que 1 - irmão esquerda
                        } else if ((estruturanoh.indiceprin > 0) && (estruturanoh.quelado == -1)) {
                            estruturanoh.irmao.getChaves().get(estruturanoh.irmao.qtdeChaves - 1).filho = estruturanoh.principal.pai.getChaves().get(estruturanoh.indiceprin).filho;
                            estruturanoh.principal.pai.getChaves().get(estruturanoh.indiceprin).filho = null;

                            ch = estruturanoh.principal.pai.removerChaveEatualizarNo(estruturanoh.indiceprin);
                            //tirando a chave indice 0 do pai e colocando na #folha#
                            insereNaFolha(ch, estruturanoh.principal);

                            ch = estruturanoh.irmao.removerChaveEatualizarNo(estruturanoh.irmao.qtdeChaves - 1);
                            //tirando a chave no último indice do irmão e colocando no indice 0 do pai
                            insereNaFolha(ch, estruturanoh.principal.pai);
                            //caso 1.2.1 - o indice da chave no nó pai é maior igual que 1 - irmão direita
                        } else { //(estruturanoh.indiceprin > 0)&&(estruturanoh.quelado == 1)
                            estruturanoh.irmao.getChaves().get(0).filho = estruturanoh.principal.pai.getChaves().get(estruturanoh.indiceprin).filho;
                            estruturanoh.principal.pai.getChaves().get(estruturanoh.indiceprin).filho = null;

                            ch = estruturanoh.principal.pai.removerChaveEatualizarNo(estruturanoh.indiceprin);
                            //tirando a chave indice 0 do pai e colocando na #folha#
                            insereNaFolha(ch, estruturanoh.principal);

                            ch = estruturanoh.irmao.removerChaveEatualizarNo(0);
                            //tirando a chave no último indice do irmão e colocando no indice 0 do pai
                            insereNaFolha(ch, estruturanoh.principal.pai);
                        }

                    } else {
                        ////////////////////////////////PROVA////////////////////////////////
                        //entrou aqui, então deverá ser implementado o TRECHO 1.2.2
                        //irmão tem que receber null para não entrar na primeira condição que é redistribuir as chaves
                        //vou escolher fundir com o da esquerda     
                        if (estruturanoh.principal.pai.esquerda == estruturanoh.principal) {
                            estruturanoh.irmao = estruturanoh.principal.pai.getChaves().get(0).filho;

                            ch = estruturanoh.principal.pai.removerChaveEatualizarNo(0);
                            estruturanoh.principal.pai.getChaves().sort(Comparator.comparingInt(Chave::getValorChave));
                            //tirando a chave indice 0 do pai e colocando na #folha#
                            insereNaFolha(ch, estruturanoh.principal);

                            for (int i = estruturanoh.irmao.getChaves().size() - 1; i >= 0; --i) {
                                ch = estruturanoh.irmao.removerChaveEatualizarNo(i);
                                //tirando a chave no último indice do irmão e colocando no indice 0 do pai
                                insereNaFolha(ch, estruturanoh.principal);
                            }

                            //indice maior que 0 && uma condição especial é se chegar na última chave
                            //se fosse pegar sempre o da direita nao teria a condição de indice 0 e sim a do principal.pai.getChaves().size()-1
                        } else if (estruturanoh.principal.pai.getChaves().size() - 1 == estruturanoh.indiceprin) {
                            estruturanoh.irmao = estruturanoh.principal.pai.getChaves().get(estruturanoh.indiceprin - 1).filho;
                            estruturanoh.principal.pai.getChaves().get(estruturanoh.indiceprin - 1).filho = estruturanoh.principal.pai.getChaves().get(estruturanoh.indiceprin).filho;

                            ch = estruturanoh.principal.pai.removerChaveEatualizarNo(estruturanoh.indiceprin);
                            estruturanoh.principal.pai.getChaves().sort(Comparator.comparingInt(Chave::getValorChave));
                            //tirando a chave indice 0 do pai e colocando na #folha#
                            insereNaFolha(ch, estruturanoh.principal);

                            for (int i = estruturanoh.irmao.getChaves().size() - 1; i >= 0; --i) {
                                ch = estruturanoh.irmao.removerChaveEatualizarNo(i);
                                //tirando a chave no último indice do irmão e colocando no indice 0 do pai
                                insereNaFolha(ch, estruturanoh.principal);
                            }
                        } else if (estruturanoh.principal.pai.getChaves().get(0).filho == estruturanoh.principal) {
                            estruturanoh.irmao = estruturanoh.principal.pai.esquerda;
                            estruturanoh.principal.pai.esquerda = estruturanoh.principal.pai.getChaves().get(0).filho;

                            ch = estruturanoh.principal.pai.removerChaveEatualizarNo(estruturanoh.indiceprin);
                            estruturanoh.principal.pai.getChaves().sort(Comparator.comparingInt(Chave::getValorChave));
                            //tirando a chave indice 0 do pai e colocando na #folha#
                            insereNaFolha(ch, estruturanoh.principal);

                            for (int i = estruturanoh.irmao.getChaves().size() - 1; i >= 0; --i) {
                                ch = estruturanoh.irmao.removerChaveEatualizarNo(i);
                                //tirando a chave no último indice do irmão e colocando no indice 0 do pai
                                insereNaFolha(ch, estruturanoh.principal);
                            }
                        } else {//aqui é indiceprin -1 pq escolhi pegar default o da esquerda
                            estruturanoh.irmao = estruturanoh.principal.pai.getChaves().get(estruturanoh.indiceprin - 1).filho;
                            //como vou descatar o irmão, o que era a ligação com o irmão aponta para o principal
                            estruturanoh.principal.pai.getChaves().get(estruturanoh.indiceprin - 1).filho = estruturanoh.principal.pai.getChaves().get(estruturanoh.indiceprin).filho;

                            ch = estruturanoh.principal.pai.removerChaveEatualizarNo(estruturanoh.indiceprin);
                            estruturanoh.principal.pai.getChaves().sort(Comparator.comparingInt(Chave::getValorChave));
                            //tirando a chave indice 0 do pai e colocando na #folha#
                            insereNaFolha(ch, estruturanoh.principal);

                            for (int i = estruturanoh.irmao.getChaves().size() - 1; i >= 0; --i) {
                                ch = estruturanoh.irmao.removerChaveEatualizarNo(i);
                                //tirando a chave no último indice do irmão e colocando no indice 0 do pai
                                insereNaFolha(ch, estruturanoh.principal);
                            }
                        }

                    }
                    //FIM TRECHO 1.2.1
                } else { // aqui acaba a folha subutilizada
                    JOptionPane.showMessageDialog(frame, "A folha não está subutilizada!", "B-Tree", JOptionPane.INFORMATION_MESSAGE);
                    //ESTE ELSE É SIMPLESMENTE INFORMATIVO. NÃO PRECISA IMPLEMENTAR AQUI
                }
                //FIM TRECHO 1.2 DO ALGORITMO

            } else {
                JOptionPane.showMessageDialog(frame, "O valor não está em um nó folha!", "B-Tree", JOptionPane.INFORMATION_MESSAGE);
                // está em um nó NÃO folha. Então vamos proceder com a remoção.
                //INICIO TRECHO 2 DO ALGORITMO

                //IMPLEMENTAÇÃO A SER FEITA!!!
                //FIM TRECHO 2 DO ALGORITMO
            }

        }

    }

    //////////////////////////////////////////////////////////////////////////
    //////////////////codigo novo/////////////////////////////////////////////
    public NoIrmaos procuraIrmaoDisponivel(No noh) {
        NoIrmaos aux = new NoIrmaos();
        aux.principal = noh;

        if (noh.pai.esquerda == noh) {
            aux.indiceprin = -1;
            if (noh.pai.getChaves().get(0).filho.excedendoMinimo(this.ordem) == true) {
                aux.irmao = noh.pai.getChaves().get(0).filho;
                aux.quelado = 1;
            }
        } else if (noh.pai.getChaves().get(noh.pai.qtdeChaves - 1).filho == noh) {
            aux.indiceprin = noh.pai.qtdeChaves - 1;
            if (noh.pai.getChaves().get(noh.pai.qtdeChaves - 2).filho.excedendoMinimo(this.ordem) == true) {
                aux.irmao = noh.pai.getChaves().get(noh.pai.qtdeChaves - 2).filho;
                aux.quelado = -1;
            }
        } else { //quer dizer que está no meio
            if (noh == noh.pai.getChaves().get(0).filho) {
                aux.indiceprin = 0;
                if (noh.pai.esquerda.excedendoMinimo(this.ordem) == true) {
                    aux.irmao = noh.pai.esquerda;
                    aux.quelado = -1;
                } else if (noh.pai.getChaves().get(1).filho.excedendoMinimo(this.ordem) == true) {
                    aux.irmao = noh.pai.getChaves().get(1).filho;
                    aux.quelado = 1;
                }
            } else {
                int i = 1;
                while (noh != noh.pai.getChaves().get(i).filho) {
                    i++;
                }
                aux.indiceprin = i;

                if (noh.pai.getChaves().get(i - 1).filho.excedendoMinimo(this.ordem) == true) {
                    aux.irmao = noh.pai.getChaves().get(i - 1).filho;
                    aux.quelado = -1;
                    //i+1 > aux.indiceprin
                } else if ((i + 1 < noh.pai.qtdeChaves) && (noh.pai.getChaves().get(i + 1).filho.excedendoMinimo(this.ordem) == true)) {
                    aux.irmao = noh.pai.getChaves().get(i + 1).filho;
                    aux.quelado = 1;
                } else {
                    aux.irmao = null;
                    System.out.println("Não tem irmão para ceder");
                }
            }
        }
        //se chegar até aqui, então não há irmãos disponíveis (que tenha chaves 
        //excedendo o mínimo).
        return aux;
    }
    //////////////////fim codigo novo/////////////////////////////////////////////
    //////////////////////////////////////////////////////////////////////////

    //////////////////////////////////////////////////////////////////////////
    //////////////////codigo novo/////////////////////////////////////////////
    //pesquisa o valorProcurado na árvore. Caso encontrar, retornará o nó.
    //se não encontrar, retornará null
    public No encontraValornoNoh(int valorProcurado) {
        //na busca, deve começar pela raiz.
        No aux = raiz;
        while (aux != null) {// && !aux.folha) {

            /*se achar a chave, já pode sair.*/
            for (int i = 0; i < raiz.qtdeChaves - 1; ++i) {
                if (valorProcurado == raiz.getChaves().get(i).valorChave) {
                    return aux;
                }
            }

            //esse if é para decidir se irá descer a árvore pela esquerda ou pelo
            //filho direito
            if (valorProcurado < aux.getChaves().get(0).getValorChave()) {
                aux = aux.esquerda;
            } else {
                int i = 0;
                //esse while serve para testar qual o filho de qual chave que 
                //irá procurar a chave.
                while (i < aux.qtdeChaves
                        && valorProcurado >= aux.getChaves().get(i).getValorChave()) {
                    //ALTERAÇÃO
                    /*esse if é para caso encontrar a chave sem ser nas folhas.*/
                    if (valorProcurado == aux.getChaves().get(i).getValorChave()) {
                        return aux;
                    }
                    i++;
                }
                aux = aux.getChaves().get(i - 1).getFilho();
            }

        }
        return aux;
    }

}
