package managers;

import java.util.ArrayList;

import interfaces.Scene;
import scenes.GameScene;

public class SceneManager {

	private static int ACTIVE_SCENE = 0;
	private ArrayList<Scene> scenes = new ArrayList<Scene>();
	
	public SceneManager() {
		scenes.add(new GameScene());
	}
	
	public void setScene(int scene) {
		
		getActiveScene().close();
		
		ACTIVE_SCENE = scene;
		
		getActiveScene().open();
	}
	
	public Scene getActiveScene() {
		return scenes.get(ACTIVE_SCENE);
	}
	
	public void render() {
		getActiveScene().render();
	}
}
