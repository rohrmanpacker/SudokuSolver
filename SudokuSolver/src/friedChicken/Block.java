package friedChicken;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.function.Function;
import java.util.stream.IntStream;

import javafx.geometry.Rectangle2D;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.text.Font;

public class Block{
	private boolean[] possibleValues = {true,true,true,true,true,true,true,true,true};
	private int value;
	public Block() {
		value = 0;
	}
	public static ArrayList<Rectangle2D> get( int width, int height){
		ArrayList<Rectangle2D> arr = new ArrayList<Rectangle2D>();


		for(int x = 0; x<9;x++) {
			for(int y=0;y<9;y++) {
				arr.add(new Rectangle2D(x*width/9, y*height/9, width/9, height/9));
			}
		}
		return arr;
	}
	public Block(boolean[] possibleValues) {
		if(possibleValues.length==this.possibleValues.length)
			this.possibleValues=possibleValues.clone();
		value=0;
	}

	public boolean hasValue() {
		return value!=0;
	}
	public boolean couldBe(int val) {
		return possibleValues[val-1];
	}

	public void remove(Block other) {
		remove(other.value);
	}

	public void remove(int notPossible) {
		if(notPossible>=1&&notPossible<=possibleValues.length)
			possibleValues[notPossible-1]=false;
	}

	public void add(int possible) {
		possibleValues[possible-1]=true;
	}

	public void set(int value) {
		if(possibleValues[value-1]) {
			for(int i = 0;i<possibleValues.length;i++)
				possibleValues[i]=false;
			this.value=value;
		}
	}

	public void setRandom() {
		ArrayList<Integer> arr = new ArrayList<Integer>();
		for(int i=0;i<possibleValues.length;i++) {
			if(possibleValues[i]) {
				arr.add(i+1);
			}
		}
		set(arr.get((int)(Math.random()*arr.size())));
	}

	public void draw(GraphicsContext gc, int width, int height, int xPos, int yPos) {
		double x = xPos/9.0*width;
		double y = yPos/9.0*height;

		for(int i =0;i<9;i++) {
			if(value==0) {
				gc.setFont(new Font(13));
				if(possibleValues[i])
					gc.strokeText((i+1)+"", x+(1+i%3)*width/36, y+(1+i/3)*height/36);
			}else {
				gc.setFont(new Font(69));
				gc.strokeText(value+"", x+width/36, y+height/9-height/36);
			}
		}

		if (toReplace()&&!hasValue()) {
			set(toReplaceValue());
		}
	}

	private boolean toReplace() {
		return numTrues()==1;
	}

	private int numTrues() {
		int sum= 0;
		for(boolean b : possibleValues) {
			if (b) {
				sum++;
			}
		}
		return sum;
	}

	private int toReplaceValue() {
		if(toReplace()) {
			for(int i=0;i<possibleValues.length;i++)
				if(possibleValues[i])
					return i+1;
		}
		return 0;
	}
	public void click() {

	}
}
