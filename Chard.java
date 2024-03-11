package chord;

//ligar o head e tail
// hashmap em cada nó pq auando tiver ativo ele tem q reconhece-los 
//next e prev
class Node {
    String data;
    Node next;
    boolean ativo;

    Node(String data) {
        this.data = data;
        this.next = null;
        this.ativo = false;
    }
}

class LinkedList {
    Node head;
    int size = 0;

    LinkedList() {
        this.head = null;
    }
    
    public void hash() {
    	
		
	}
    
    //Ativa ou desativa o nodo
    public void ativacaoNodo(int nodeIndex) {
    	
        Node current = head;
        int currentPosition = 0;
        
      //while (currentPosition < nodeIndex - 1 && current != null) {
        while (currentPosition < nodeIndex - 1) {
            current = current.next;
            currentPosition++;
        }
        
        current.ativo = !current.ativo;
    }
    
    // Método para adicionar um novo nó no final da lista
    public void append(String data) {
        Node newNode = new Node(data);

        if (head == null) {
            head = newNode;
            size++;
            return;
        }
        
        //gambiarra pra eu fazer o tail.next se o head
        if (head.next == null) {
            head.next = newNode;
            newNode.next = head;
            size++;
            return;
        }

        Node last = head.next;
        while (last.next != head) {
            last = last.next;
        }

        last.next = newNode;
        
//      newNode.next = head;
        last.next.next = head;
        
        size++;
    }

    // Método para inserir um recurso em uma posição definida pelo hash
    public void insertByHash(String data) {
    	
    	int hashValue = 0;
    	
    	for (int i = 0; i < data.length(); i++) {
    		hashValue += data.charAt(i);
		}
    	
    	int position = hashValue % 16;//Valor fixo 16 aqui! Talvez mudar
    			
        if (position < 0) {
            System.out.println("Posição inválida");
            return;
        }

        if (position == 0) {
            Node newNode = new Node(data);
            newNode.next = head;
            head = newNode;
            return;
        }

        Node current = head;
        int currentPosition = 0;
//        while (currentPosition < position - 1 && current != null) {
        while (currentPosition < position - 1) {
            current = current.next;
            currentPosition++;
        }

        if (current == null) {
            System.out.println("Posição inválida");
            return;
        }

        Node newNode = new Node(data);
        newNode.next = current.next;
        current.next = newNode;
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
}



public class Chord {
    public static void main(String[] args) {
        LinkedList list = new LinkedList();

        
        //criei 16 nós pq sim
        

        list.append("a");
        list.append("a");
        list.append("a");
        list.append("a");
        list.append("a");
        list.append("a");
        list.append("a");
        list.append("a");
        list.append("a");
        list.append("a");
        list.append("a");
        list.append("a");
        list.append("a");
        list.append("a");
        list.append("a");
        list.append("a");
        list.append("a");
        list.append("a");
        
//        for (int i = 0; i < 16; i++) {
//        	list.append("a");
//		}
        
        //list.insertByHash("Filme");
        

        // Imprimindo a lista
        System.out.println("Lista encadeada:");
        list.printList();
        

    }
}
