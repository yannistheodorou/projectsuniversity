import java.io.PrintStream;
import java.util.NoSuchElementException;
public class CharDoubleEndedQueueImpl implements CharDoubleEndedQueue{
    private DNode head;
    private DNode tail;
    private int n=0;
    public CharDoubleEndedQueueImpl(){
        head=null;
        tail=null;        
    } 
    public boolean isEmpty(){
        if (n==0)
            return true; 
        else
            return false;
    }
    public void addFirst(char item){
        DNode node = new DNode(item,null,null);
        if (isEmpty()){
            tail=node;
        }
        else{
            head.setPrevious(node);
            node.setNext(head);
        }
        head=node;
        n++;           
    }
    public char removeFirst() throws NoSuchElementException{
        DNode temphead;
        if (isEmpty() ) 
            throw new NoSuchElementException("This queue is empty.");
        temphead=head;
        head=head.getNext();
        temphead.setNext(null);
        n--;
        if (isEmpty())
            tail=null;
        return temphead.getItem();
    }
    public void addLast(char item){
        DNode node = new DNode(item,null,null);
        if (isEmpty())  
            head=node;
        else{
            node.setPrevious(tail);
            tail.setNext(node);
        }
        tail=node;
        n++;      
    }
    public char removeLast() throws NoSuchElementException{
        DNode temptail;
        if (isEmpty() ) 
            throw new NoSuchElementException("This queue is empty.");
        temptail=tail;
        tail=tail.getPrevious();
        temptail.setPrevious(null);
        n--;
        if (isEmpty())
            tail=null;
        return temptail.getItem();
    }
    public char getFirst() throws NoSuchElementException{
        if (isEmpty() ) 
            throw new NoSuchElementException("This queue is empty.");
            return head.getItem();
    }
    public char getLast() throws NoSuchElementException{
        if (isEmpty() ) 
            throw new NoSuchElementException("This queue is empty.");
            return tail.getItem();
    }
    public void printQueue (PrintStream stream){
        DNode temphead;
        temphead=head;
        for(int i=0;i<n;i++){
            stream.println(temphead.getItem() +"\n" );
            temphead= temphead.getNext();
        }
    }
    public int size(){
        return n;
    }
}