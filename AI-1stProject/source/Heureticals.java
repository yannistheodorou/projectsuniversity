public class Heureticals{
	//h1 returns the difference between the score
	public static int h1(Board b){
		int sum = 0;
		for(byte x = 0; x < 8; x++){for(byte y = 0; y < 8; y++){
			sum += b.tiles[x][y].getValue();
		}}
		return sum;
	}
	//h2 returns a number based on the domination on the sides of the board. Corners have double the weight
	public static int h2(Board b){
		int sum = 0;
		final int W = 10;
		for(byte i = 0; i < 8; i++){
			sum += b.tiles[0][i].getValue() * W;
			sum += b.tiles[7][i].getValue() * W;
			sum += b.tiles[i][0].getValue() * W;
			sum += b.tiles[i][7].getValue() * W;
		}
		return sum;
	}
	//h3 returns the number of possible moves from state b
	public static int h3(Board b){
		int sum = 0;
		if(!b.isGameOver()){
			sum = b.getChildren().size();
		}
		return sum;
	}
}
