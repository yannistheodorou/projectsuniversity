public class DNode{
    private char item;
    private DNode nextNode;
    private DNode previousNode;
    DNode(char item ){
        this( item, null, null );
    } 
    DNode( char item,DNode previousNode, DNode nextNode){
        this.item=item;
        this.nextNode=nextNode;
        this.previousNode=previousNode;
    }
    char getItem(){
        return item; 
    }
    DNode getPrevious(){
        return previousNode;
    }    
    DNode setPrevious(DNode newPrevious){
        return previousNode=newPrevious;
    }
    DNode getNext(){
        return nextNode;
    }    
    DNode setNext(DNode newNext){
        return nextNode=newNext;
    }
} 
