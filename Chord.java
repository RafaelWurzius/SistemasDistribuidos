package chord;

import java.util.HashMap;
import java.util.Map;

class Node {
    String data;
    Node next;
    Node prev;
    Node nextAtivo; //próximo nó ativo
    Node prevAtivo; //anterior ao nó ativo
    Map<Integer, Node> associados;
    boolean ativo;

    Node(String data) {
        this.data = data;
        this.next = null;
        this.prev = null;
        this.ativo = false;
    }
}

class LinkedList {
    Node head;
    int size = 0;

    LinkedList() {
        this.head = null;
    }
    
    //Ativa ou desativa o nodo
    public void ativacaoNodo(int nodeIndex) {
    	
        Node current = head;
        int currentPosition = 0;
        
      //while (currentPosition < nodeIndex - 1 && current != null) {
        while (currentPosition < nodeIndex) {
            current = current.next;
            currentPosition++;
        }
        
        if(!current.ativo) {
            // Ativando um nó
        	current.ativo = true;
        	 //Percorre de trás pra frente salvando os associados até encontrar um ativo
            Node currentAtivo = current;
            
            while (!current.prev.ativo) {
    			currentAtivo.associados.put(hash(current.data), current);
    			current = current.prev;
    		}
            
            currentAtivo.associados.put(hash(current.data), current);
            current = current.prev;
            
            current.nextAtivo = currentAtivo;
            currentAtivo.prevAtivo = current;
            
            //Percorre para a frente até encontrar um nó ativo, linka ele ao nó em questão e revê os associados 
            current = currentAtivo;
            while (!current.next.ativo) {
    			current = current.next;
    		}
            current = current.next;
            
            //Ligado os ponteitos ativos
            current.prevAtivo = currentAtivo;
            currentAtivo.nextAtivo = current;
            
            //Revendo os associados (dava pra otimizar isso aqui)
            currentAtivo = current;

            current.associados.clear();
            while (!current.prev.ativo) {
            	
    			currentAtivo.associados.put(hash(current.data), current);
    			current = current.prev;
    		}
            currentAtivo.associados.put(hash(current.data), current);
            current = current.prev;

            current.nextAtivo = currentAtivo;
            currentAtivo.prevAtivo = current;
        }else {
        	// Desativando um nó
        	current.ativo = false;
        	Node nextNode = current;
        	
            while (!nextNode.next.ativo) {
            	nextNode = nextNode.next;
    		}
            nextNode = nextNode.next;
        	
            // Passando os itens do hashmap do item desativado para o próximo nó ativo
            nextNode.associados.putAll(current.associados);
            current.associados.clear();
        }     
    }
    
    // Método para adicionar um novo nó no final da lista
    public void append(String data) {
        Node newNode = new Node(data);
        
        newNode.associados = new HashMap<>();
        if (head == null) {
            head = newNode;
            size++;
            return;
        }
        
        //gambiarra pra eu fazer o tail.next ser o head
        if (head.next == null) {
            head.next = newNode;
            head.prev = newNode;
            newNode.next = head;
            newNode.prev = head;
            size++;
            return;
        }

        Node last = head.next;
        while (last.next != head) {
            last = last.next;
        }

        last.next = newNode;
        newNode.prev = last;
//      newNode.next = head;
        last.next.next = head;
        head.prev = last.next;
        
        size++;
    }
    
    public int hash(String data) {
    	int hashValue = 0;
    	
    	for (int i = 0; i < data.length(); i++) {
    		hashValue += data.charAt(i);
		}
    	
    	return hashValue % 16; //Valor fixo 16 aqui
    }
    

    // Método para inserir um recurso em uma posição definida pelo hash
    public void insertByHash(String data) {
    	 	
    	int position = hash(data);
    			
        if (position < 0) {
            System.out.println("Posição inválida");
            return;
        }

        Node current = head;
        int currentPosition = 0;
        while (currentPosition < position) {
            current = current.next;
            currentPosition++;
        }

        if (current == null) {
            System.out.println("Posição inválida");
            return;
        }

        current.data = data;
        
    }
    
    public Node buscar(String data) {
    	Node current = head;
    	if(!current.ativo) {
    		while (!current.ativo) {
    			current = current.next;
    		}
    	}
    	
    	int hashValue = hash(data);

    	for (int i = 0; i < size; i++) {
    		
			if(current.associados.containsKey(hashValue)) {
				return current.associados.get(hashValue);
			}else {
				current = current.nextAtivo;
			}
		}
    	System.err.println("Recurso não encontrado no Chord!");
    	return null;
    	
    }
    
    // Método para imprimir a lista encadeada (anel)
    public void printAnel() {
        Node current = head;
        System.out.print(current.data + " ");
        
        if(head.next != null) {
            current = head.next;

            while (current != head) {
                System.out.print(current.data + " ");
                current = current.next;
            }
            System.out.println();
        }
    }
    
    public void printAnelHT() {
        Node current = head;
        System.out.print(current.data + " ");
        
        if(head.prev != null) {
            current = head.prev;
            while (current != head) {
                System.out.print(current.data + " ");
                current = current.prev;
            }
            System.out.println();
        }
    }
    
    public void printAssoc() {
    	Node current = head;
        System.out.println(current.data + ":\t" + "Status ativo: " + current.ativo + "\tAssociados: " + current.associados);
        
        if(head.next != null) {
            current = head.next;
            while (current != head) {
            	System.out.println(current.data + ":\t" + "Status ativo: " + current.ativo + "\tAssociados: " + current.associados);
                current = current.next;
            }
            System.out.println();
        }
    }
}

public class Chord {
    public static void main(String[] args) {
    	LinkedList chord = new LinkedList();
        
        //criei 16 nós pq sim
        for (int i = 0; i < 16; i++) {
        	chord.append(" ");
		}
 
        chord.insertByHash(" ");
        chord.insertByHash("a");
        chord.insertByHash("b");
        chord.insertByHash("c");
        chord.insertByHash("d");
        chord.insertByHash("e");
        chord.insertByHash("f");
        chord.insertByHash("g");
        chord.insertByHash("h");
        chord.insertByHash("i");
        chord.insertByHash("j");
        chord.insertByHash("k");
        chord.insertByHash("l");
        chord.insertByHash("m");
        chord.insertByHash("n");
        chord.insertByHash("o");
        
        // Imprimindo a lista
        System.out.println("Lista encadeada:");
        chord.printAnel();
        chord.printAnelHT();
        
        
        chord.ativacaoNodo(1);
        chord.ativacaoNodo(6);    
        chord.ativacaoNodo(11);
        chord.ativacaoNodo(13);

        chord.printAssoc();
        
        chord.ativacaoNodo(8);
        chord.printAssoc();
        
        chord.ativacaoNodo(8);
        chord.printAssoc();    
        
        System.out.println("Endereço do nó: " + chord.buscar("n"));
        System.out.println("Endereço do nó: " + chord.buscar("a"));
    }
}
