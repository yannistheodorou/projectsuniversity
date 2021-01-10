import java.io.PrintStream;
import java.util.NoSuchElementException;
public class CharQueueImpl implements CharQueue{
    private ListNode head;
    private ListNode tail;
    private int r=0;
    public CharQueueImpl(){
        head=null;
        tail=null;
    } 
    public boolean isEmpty(){
        if (r==0)
            return true; 
        else
            return false;
    }
    public void put(char item){
        ListNode node = new ListNode(item,null);
        if (isEmpty())  
            head=node;
        else
            tail.setNext(node);
        tail=node;
        r++;            
    }    
    public char get() throws NoSuchElementException{
        ListNode temphead;
        if (isEmpty() ) 
            throw new NoSuchElementException("This queue is empty.");
        temphead=head;
        head=head.getNext();
        temphead.setNext(null);
        r--;
        if (isEmpty())
            tail=null;
        return temphead.getItem();
    }
    public char peek() throws NoSuchElementException{
        if (isEmpty() ) 
            throw new NoSuchElementException("This queue is empty.");
            return head.getItem();
    }
    public void printQueue (PrintStream stream){
        ListNode temphead;
        temphead=head;
        for(int i=0;i<r;i++){
            stream.println(temphead.getItem() +"\n" );
            temphead= temphead.getNext();
        }
    } 
    public int size(){
        return r;
    } 
}