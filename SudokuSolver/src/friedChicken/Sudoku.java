package friedChicken;


import javafx.animation.AnimationTimer;
import javafx.application.*;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.layout.GridPane;
import javafx.stage.Stage;


public class Sudoku extends Application{

	public static void main(String[] args) {
		Application.launch(args);
	}
	@Override
	public void start(Stage primaryStage) throws Exception {
		int width = 720, height = 720;
		GridPane root = new GridPane();
		Scene scene = new Scene(root,width,height);

		Canvas canvas = new Canvas(width,height);//background
		root.getChildren().add(canvas);
		GraphicsContext gc = canvas.getGraphicsContext2D();//this is the background


		BlockGrid grid = new BlockGrid();

		/*ArrayList<Rectangle2D> boxes = Block.get(width, height);
		for(Rectangle2D rect: boxes) {

		}*/
		int[] pos = {0,0};
		scene.setOnMouseClicked(e->{
			pos[0]=(int)(e.getX()/width*9);
			pos[1]=(int)(e.getY()/height*9);
		});
		
		
		scene.setOnKeyPressed(e->{
			int val=0;
			try{
				val=Integer.parseInt(e.getCode().toString().substring(e.getCode().toString().length()-1));
			}catch( NumberFormatException ex){
				//ex.printStackTrace();
			}
			grid.set(val, pos[0], pos[1]);

		});

		new AnimationTimer()//this is like the game timer
		{
			public void handle(long currentNanoTime){
				gc.clearRect(0, 0, width, height);
				Block.get(width, height);
				grid.update();
				grid.draw(gc, width, height);


			}
		}.start();

		primaryStage.setScene(scene);
		primaryStage.setTitle("Sudoku Solver");
		primaryStage.show();
	}

}
