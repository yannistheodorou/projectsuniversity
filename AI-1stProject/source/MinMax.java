
import java.util.ArrayList;
public class MinMax{
	private int depth;
	private Board.tile mycolor;
	private Board cb;
	public MinMax(Board.tile ccolor, int depth, Board current){
		if(ccolor != Board.tile.EMPTY && depth >= 0){this.depth = depth;mycolor = ccolor;cb = current;}
		else{throw new IllegalArgumentException();}
	}
	public Board getMove(/*Board cb*/){
		if(cb.isGameOver())return cb;
		ArrayList<Board> children = cb.getChildren(this.mycolor);
		
		if(cb.lastPlayer == mycolor){
			System.out.println("Not my turn!");
		}
		
		if(depth == 0){
			Board best = children.get(0);
			int bestValue = best.evaluate();
			
			for( Board s : children){
				if(s.evaluate() > bestValue){
					best = s;
					bestValue = s.evaluate();
				}
			}
			return best;
		}else{
			Board best = children.get(0);
			int bestValue = best.evaluate();
			for( Board s : children){
				Board nextlevel = new MinMax(Board.other(mycolor), depth-1, s).getMove();
				if(nextlevel.evaluate() > bestValue){
					best = s;
					bestValue = nextlevel.evaluate();
				}
			}
			return best;
		}
		
	}

}
