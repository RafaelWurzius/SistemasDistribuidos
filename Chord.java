package chord;

import java.util.HashMap;
import java.util.Map;

//ligar o head e tail X
// hashmap em cada nó pq quando tiver ativo ele tem q reconhece-los 


//TODO list:
//	implementar o prev X
//	implementar a ativação e X
// 	implementar a associação aos nós ativos X
//	implementar a busca X
//	implementar a desativação
//	Ajustes




class Node {
    String data;
    Node next;
    Node prev;
    Node nextAtivo;//próximo nó ativo
    Node prevAtivo;//anterior nó ativo
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
        	current.ativo = true;
        	 //percorre de trás pra frente salvando os associados até encontrar um ativo
            Node currentAtivo = current;
            
 
            
            while (!current.prev.ativo) {
            	
    			currentAtivo.associados.put(hash(current.data), current);
    			current = current.prev;
    		}
            currentAtivo.associados.put(hash(current.data), current);
            current = current.prev;
            
            current.nextAtivo = currentAtivo;
            currentAtivo.prevAtivo = current;
            
            //percorre para a frente até encontrar um nó ativo, linka ele ao nó em questão e revê os associados 
            current = currentAtivo;
            while (!current.next.ativo) {
    			current = current.next;
    		}
            current = current.next;
            //ligado os ponteitos ativos (Testar isso aqui, não sei se tá certo)
            current.prevAtivo = currentAtivo;
            currentAtivo.nextAtivo = current;
            
            //Revendo os associados(dava pra otimizar isso aqui)
            currentAtivo = current;
            

            
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
    	
    	return hashValue % 16;//Valor fixo 16 aqui! Talvez mudar
    }
    

    // Método para inserir um recurso em uma posição definida pelo hash
    public void insertByHash(String data) {
    	
//    	int hashValue = 0;
//    	
//    	for (int i = 0; i < data.length(); i++) {
//    		hashValue += data.charAt(i);
//		}
//    	
    	int position = hash(data);
    			
        if (position < 0) {
            System.out.println("Posição inválida");
            return;
        }
        
//        if (position == 0) {
//            Node newNode = new Node(data);
////            newNode.next = head;
//            head = newNode;
//            return;
//        }

        Node current = head;
        int currentPosition = 0;
//        while (currentPosition < position - 1 && current != null) {
        while (currentPosition < position) {
            current = current.next;
            currentPosition++;
        }

        if (current == null) {
            System.out.println("Posição inválida");
            return;
        }

        current.data = data;
//        Node newNode = new Node(data);
//        
//        newNode.next = current.next;
//        newNode.prev = current;
//        current.next.prev = newNode;
//        current.next = newNode;
        
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
    
    // Método para imprimir a lista encadeada
    public void printList() {
        Node current = head;
        System.out.print(current.data + " ");
        
        if(head.next != null) {
            current = head.next;
            //se for head da prblema se for null funciona
            while (current != head) {
                System.out.print(current.data + " ");
                current = current.next;
            }
            System.out.println();
        }
        

    }
    
    public void printListHT() {
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
        chord.append(" ");
        chord.append("a");
        chord.append("b");
        chord.append("c");
        chord.append("d");
        chord.append("e");
        chord.append("f");
        chord.append("g");
        chord.append("h");
        chord.append("i");
        chord.append("j");
        chord.append("k");
        chord.append("l");
        chord.append("m");
        chord.append("n");
        chord.append("o");
 

        chord.insertByHash("bb");
        chord.insertByHash("bbb");
        chord.insertByHash("c");
        chord.insertByHash("j");
        

        // Imprimindo a lista
        System.out.println("Lista encadeada:");
        chord.printList();
        chord.printListHT();
        
        System.out.println("Nodo 2(indice 1): " + chord.head.next.data);
        System.out.println("Nodo 2(indice 1) ativado: " + chord.head.next.ativo);
        chord.ativacaoNodo(1);
        chord.ativacaoNodo(6);
        chord.ativacaoNodo(8);
        chord.ativacaoNodo(11);
        chord.ativacaoNodo(13);
        System.out.println("Nodo 2(indice 1) ativado: " + chord.head.next.ativo);
        
        chord.printAssoc();
        
        chord.ativacaoNodo(8);
        
        chord.printAssoc();
//       System.out.println("Endereço do nó: " + chord.buscar("n"));
    }
}
