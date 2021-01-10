class ListNode{
    private char item;
    private ListNode nextNode;
    ListNode(char item ){
        this( item, null );
    } 
    ListNode( char item, ListNode nextNode){
        this.item=item;
        this.nextNode=nextNode;
    }
    char getItem(){
        return item; 
    }
    ListNode getNext(){
        return nextNode;
    }
    ListNode setNext(ListNode newNext){
        return nextNode=newNext;
    }
} 