package friedChicken;

import java.util.ArrayList;

import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;

public class BlockGrid {
	private Block[][] grid;

	public BlockGrid() {
		grid = new Block[9][9];
		for(int x=0;x<grid.length;x++)
			for(int y = 0; y<grid[x].length;y++) {
				grid[x][y]=new Block();
			}

	}

	public void update() {
		for(int x =0;x<grid.length;x++) {
			for(int y =0;y<grid[x].length;y++) {
				Block temp= grid[x][y];
				if(!temp.hasValue()) {//check others
					for(int x2=0;x2<grid.length;x2++) {
						for(int y2=0;y2<grid[x].length;y2++) {
							Block temp2= grid[x2][y2];
							if(temp2.hasValue()) {
								if(x==x2||y==y2) {//same row or column
									temp.remove(temp2);
								}
								if(x/3==x2/3&&y/3==y2/3) {//same block
									temp.remove(temp2);
								}
							}
						}
					}
				}
				boolean lose=true;
				for(int i=1;i<=9;i++) {
					if(temp.couldBe(i))
						lose=false;
				}
				if(lose) {
					//TODO
				}

			}

		}
		for(int n=1;n<=9;n++) {
			ArrayList<Block> temp = getBigSquare(n);
			for(int i =1;i<=9;i++) {
				getNum(temp,i);
			}
		}
	}

	/**
	 * so this basically assigns it the value if it's the only one in there that could be it
	 * @param arr
	 * @param num
	 */
	public void getNum(ArrayList<Block> arr,int num) {
		if(getSumNum(arr,num)==1) {
			for(Block b: arr) {
				if(b.couldBe(num)) {
					b.set(num);
				}
			}
		}
	}

	public int getSumNum(ArrayList<Block> arr,int num) {
		int sum =0;
		for(Block b:arr) {
			if(b.couldBe(num))
				sum++;
		}
		return sum;
	}

	public void setRandom() {
		Block b;
		do {
			b=grid[(int)(Math.random()*9)][(int)(Math.random()*9)];
		}while(b.hasValue());
		b.setRandom();
	}
	public void set(int value, int x, int y) {
		if(grid[x][y]==null)
			grid[x][y]=new Block();
		grid[x][y].set(value);
	}
	
	public void highLightBigSquare(GraphicsContext gc, int width, int height,int[] pos) {
		gc.save();
		gc.setFill(Color.LIGHTGREEN);
		gc.fillRect(pos[0]*width/9, pos[1]*height/9, width/9, height/9);
		gc.restore();
	}
	
	public void draw(GraphicsContext gc, int width, int height) {
		//main lines
		double lineWidth = gc.getLineWidth();
		gc.setLineWidth(lineWidth+2);
		gc.setStroke(Color.BLACK);
		gc.strokeLine(width/3.0, 0, width/3.0, height);
		gc.strokeLine(2*width/3.0, 0, 2*width/3.0, height);
		gc.strokeLine(0, height/3.0, width, height/3.0);
		gc.strokeLine(0, 2*height/3.0, width, 2*height/3.0);

		gc.setLineWidth(lineWidth);
		//other vertical lines
		gc.setStroke(Color.GRAY);
		gc.strokeLine(width/9.0,0,width/9.0,height);
		gc.strokeLine(2*width/9.0,0,2*width/9.0,height);
		gc.strokeLine(4*width/9.0,0,4*width/9.0,height);
		gc.strokeLine(5*width/9.0,0,5*width/9.0,height);
		gc.strokeLine(7*width/9.0,0,7*width/9.0,height);
		gc.strokeLine(8*width/9.0,0,8*width/9.0,height);
		//other horizontal lines
		gc.strokeLine(0,height/9.0,width,height/9.0);
		gc.strokeLine(0,2*height/9.0,width,2*height/9.0);
		gc.strokeLine(0,4*height/9.0,width,4*height/9.0);
		gc.strokeLine(0,5*height/9.0,width,5*height/9.0);
		gc.strokeLine(0,7*height/9.0,width,7*height/9.0);
		gc.strokeLine(0,8*height/9.0,width,8*height/9.0);
		//boxes
		gc.setLineWidth(lineWidth/2);
		for(int x=0;x<grid.length;x++)
			for(int y = 0; y<grid[x].length;y++) {
				if(grid[x][y]==null)
					grid[x][y]=new Block();
				grid[x][y].draw(gc, width, height, x, y);
			}
		gc.setLineWidth(lineWidth);
	}

	public ArrayList<Block> getBigSquare(int num) {
		ArrayList<Block> arr = new ArrayList<Block>();
		for(int x=(num-1)%3*3;x<=(num-1)%3*3+2;x++) {
			for(int y=(num-1)/3*3;y<=(num-1)/3*3+2;y++) {
				arr.add(grid[x][y]);
			}
		}
		return arr;
	}
}
