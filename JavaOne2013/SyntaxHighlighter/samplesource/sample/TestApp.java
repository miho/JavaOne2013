package sample;

import java.io.File;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.stage.Stage;
import at.bestsolution.javafx.ide.editor.SourceEditor;
import at.bestsolution.javafx.ide.editor.document.FileDocument;

public class TestApp extends Application {

	@Override
	public void start(Stage primaryStage) throws Exception {
		SourceEditor editor = new SourceEditor();
		editor.setDocument(new FileDocument(new File("/Users/tomschindl/git/org.eclipse.e4.ui/examples/org.eclipse.e4.demo.simpleide/src/org/eclipse/e4/demo/simpleide/internal/DefaultProjectService.java")));
		primaryStage.setScene(new Scene(editor, 800, 600));
		primaryStage.setTitle("FX-IDE");
		primaryStage.show();
	}
	
	public static void main(String[] args) {
		launch(args);
	}
}
