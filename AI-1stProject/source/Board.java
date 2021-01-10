//import java.lang.IllegalArgumentException;
import java.util.ArrayList;
public class Board{
	public tile lastPlayer = tile.WHITE;//Stores who played last
	// Initialize a Reversi 8x8 board
    public tile[][] tiles = new tile[8][8];
    public Move lastMove;


	// Variables for the board values
	public enum tile{
		WHITE(-1), 
		EMPTY(0), 
		BLACK(1);
		
		private final int value;
		
		tile(int value){
			this.value = value;
		}
		
		public int getValue() {
			return value;
		}
	}
	
	public static tile other(tile t) throws IllegalArgumentException{
		if(!(t == tile.BLACK || t == tile.WHITE))throw new IllegalArgumentException();
		return(t == tile.WHITE) ? tile.BLACK : tile.WHITE;
	}
	//this class represents a tile position
    public class Move{
	    private byte x, y;
	    public Move(byte x, byte y){
		    this.x =x;this.y =y;}
	    public byte getX(){return x;}
	    public byte getY(){return y;}
    }
	// Construct an empty Reversi board
	public Board(){
		for(byte a=0; a<8; a++){
		for(byte b=0; b<8; b++){
			tiles[a][b] = tile.EMPTY;
		}}
		// Place the initial 4 tiles in the center of the board
		tiles[3][4]=tiles[4][3]=tile.BLACK;
		tiles[3][3]=tiles[4][4]=tile.WHITE;
	}
	
	// Copy constructor of a Reversi board
	public Board(Board board)
	{
		for(byte a=0; a<8; a++)
		{
			for(byte b=0; b<8; b++)
			{
				tiles[a][b] = board.tiles[a][b];
			}
		}
		this.lastPlayer = board.lastPlayer;//Stores who played last
		// Initialize a Reversi 8x8 board
		this.lastMove = board.lastMove;
	}
	
	// Check if a tile is inside rows and columns 1-8
	public boolean isInBounds(byte X, byte Y){
		X--;Y--;
		return ((X>=0 && X<8 && Y>=0 && Y<8));
	}
	
	// Check if a position on the board contains a tile
	public boolean isEmpty(byte X,byte Y){
		if(!(isInBounds(X, Y)))return true;
		return tile.EMPTY == tiles[X][Y];
	}
	
	//
	public int evaluate()
	{
		int sum = 0;
		sum = Heureticals.h1(this) + Heureticals.h2(this);
		return sum*((-1)*lastPlayer.getValue());//This makes sure the heuretical function returns a result making sense for the next player.
	}
	
	// Draw the board with its tiles
	public String toString(){
		
		String s = "";
		s += "   ";
		
		for(byte a = 0; a<8; a++){
			s += (a+1)+" ";
		}
		
		s += "\n";
		s += "  ";
		
		for(byte a = 0; a<8; a++){
			s += "==";
		}
		
		s += "=";
		s += "\n";
		int letter = 65;
		for(byte a = 0; a<8; a++){
			s += (char)(letter+a)+"| ";
			for(byte b = 0; b<8; b++){
				switch(tiles[a][b]){
					case WHITE:
						s+="W ";
						break;
					case BLACK:
						s+="B ";
						break;
					default:
						s+=". ";
						break;
				}
			}
			s+="|\n";
		}
		
		s += "  ";
		
		for(byte a = 0; a<8; a++){
			s += "==";
		}
		
		s += "=";
		s += "\n";
		
		return s;
	}
	
	/**
	 * Place a tile on the board
	 * @param color the color of the tile (BLACK/WHITE) depending on whose turn it is
	 * @param X the row of the position to play, counting from 1 to 8
	 * @param Y the column of the position to play, counting from 1 to 8
	 * @param commit QQ
	 * @return
	 */
	public boolean play(tile color, final byte X, final byte Y, boolean commit){
		return play(X, Y, commit);
	}
	public boolean play(/*tile color,*/ final byte X, final byte Y, boolean commit)
	{
		
		class nonestsinJava
		{
			/**
			 * Check all directions (horizontally, vertically and diagonally) for tiles
			 * @param i the horizontal direction, represented by columns
			 * @param j the vertical direction, represented by rows
			 * @param toBe the color of the tile currently playing
			 * @param play false to only check around, true to make a play
			 * @return
			 */
			private boolean checkDirection(/*byte X, byte Y,*/ byte i, byte j, tile toBe, boolean play)
			{
				// Convert the coordinates of the position from 1-8 to 0-7
				byte cX = (byte)(X-1); byte cY = (byte)(Y-1);
				// Remember when a tile of the same color is found
				boolean foundSister = false;
					
				cX += i; cY += j;
				
				if(isInBounds((byte)(cX+i+1), (byte)(cY+j+1)) && tiles[cX][cY] != other(toBe))return false;


				while(!foundSister && isInBounds((byte)(cX+i+1), (byte)(cY+j+1)))
				{
					cX += i; cY += j;
					foundSister = toBe == tiles[cX][cY] ;
					if(tiles[cX][cY] == tile.EMPTY)break;
				}

				//System.out.println("i: "+i+" j: "+j+" cX:"+cX+" cY:"+cY+" sister: "+foundSister+" play: "+play);//DEBUG

				if(!foundSister)return false;
				if(play){
					while(cX != X-1 | cY != Y-1){
						cX -= i; cY -= j;
						tiles[cX][cY] = toBe;
					}
				}
				return true;
			}
		}
		// Cannot make a play if the coordinates are out of bounds or
		// they already contain a tile or
		// the tile to be placed is not black or white
		tile color = other(lastPlayer);
		if(!(isInBounds(X, Y) && tiles[X-1][Y-1] == tile.EMPTY && color != tile.EMPTY))return false;
		
		nonestsinJava n = new nonestsinJava();
		
		if( n.checkDirection((byte)1, (byte)0, color, false)  || // Check down position
			n.checkDirection((byte)1, (byte)1, color, false)  || // Check down-right position
			n.checkDirection((byte)1, (byte)-1, color, false) || // Check down-left position
			n.checkDirection((byte)0, (byte)1, color, false)  || // Check right position
			n.checkDirection((byte)0, (byte)-1, color, false) || // Check left position
			n.checkDirection((byte)-1, (byte)0, color, false) || // Check up-right position
			n.checkDirection((byte)-1, (byte)1, color, false) || // Check up-left position
			n.checkDirection((byte)-1, (byte)-1, color, false))  // Check up position
		{
			if(commit)
			{
				n.checkDirection((byte)1,  (byte)0, color, true); 
				n.checkDirection((byte)1,  (byte)1, color, true);  
				n.checkDirection((byte)1, (byte)-1,  color, true); 
				n.checkDirection((byte)0,  (byte)1, color, true);  
				n.checkDirection((byte)0, (byte)-1,  color, true); 
				n.checkDirection((byte)-1, (byte)0, color, true); 
				n.checkDirection((byte)-1, (byte)1, color, true);  
				n.checkDirection((byte)-1, (byte)-1, color, true);
				this.lastMove = new Move((byte)(X-1), (byte)(Y-1));
				this.lastPlayer = color;
			}
			return true;
		}
		else
		{
			return false;
		}
	}
	
	public tile[][] getBoard()
    {
        return tiles;
    }
	
	//returns an ArrayList of all the Board-states reachable from current Board with a legal move from the player with the parameter color
    public ArrayList<Board> getChildren(){
	    return getChildren(other(lastPlayer));
    }
	
    public ArrayList<Board> getChildren(tile color){
	    ArrayList<Board> r = new ArrayList<Board>();
	    for(byte x = 1; x<=8; x++){for(byte y = 1; y<=8; y++){
		    Board copy = new Board(this);
		    if(copy.play(color, x, y, true))
					//System.out.println(""+copy.lastPlayer );////
					r.add(new Board(/*new Move(x,y),*/copy));
	    }}
		return r;
    }
	
	public boolean isGameOver(){
	    return getChildren().size()==0;
    }
}
