package game;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import org.lwjgl.input.Mouse;
import org.lwjgl.opengl.Display;
import org.lwjgl.util.vector.Vector2f;
import org.lwjgl.util.vector.Vector3f;
import entities.Camera;
import entities.Entity;
import entities.Light;
import entities.Player;
import guis.GuiTexture;
import models.RawModel;
import models.TexturedModel;
import objConverter.OBJFileLoader;
import renderEngine.DisplayManager;
import renderEngine.Loader;
import renderEngine.MasterRenderer;
import renderEngine.OBJLoader;
import terrains.Terrain;
import terrains.TerrainTexture;
import terrains.TerrainTexturePack;
import textures.ModelTexture;

public class MainGameLoop {

	public static void main(String[] args) {
		
		DisplayManager.createDisplay();
		Loader loader = new Loader();
		MasterRenderer renderer = new MasterRenderer(loader);
		Random random = new Random();
		
		List<Entity> entities = new ArrayList<Entity>();
		List<Light> lights = new ArrayList<Light>();
		List<GuiTexture> guis = new ArrayList<GuiTexture>();
		
		//**********TERRAIN TEXTURE**********//
		
		TerrainTexture backgroundTexture = new TerrainTexture(loader.loadTexture("grassy"));
		TerrainTexture rTexture = new TerrainTexture(loader.loadTexture("mud"));
		TerrainTexture gTexture = new TerrainTexture(loader.loadTexture("grassFlowers"));
		TerrainTexture bTexture = new TerrainTexture(loader.loadTexture("stone"));
		
		TerrainTexturePack texturePack = new TerrainTexturePack(backgroundTexture, rTexture, gTexture, bTexture);
		
		TerrainTexture blendMap = new TerrainTexture(loader.loadTexture("blendMap"));
		Terrain terrain = new Terrain(0, -1, loader, texturePack, blendMap, "heightmap");
		
		//***********************************//
		
				
		TexturedModel treeModel = new TexturedModel(OBJLoader.loadObjModel("tree", loader), new ModelTexture(loader.loadTexture("tree")));
				
		TexturedModel fernModel = new TexturedModel(OBJLoader.loadObjModel("fern", loader), new ModelTexture(loader.loadTexture("fernatlas"), true, false, 2));
		
		TexturedModel lampModel = new TexturedModel(OBJLoader.loadObjModel("lamp", loader), new ModelTexture(loader.loadTexture("lamp"), false, true));
		
		GuiTexture logo = new GuiTexture(loader.loadGuiTexture("test"), new Vector2f(0.5f, 0.5f), new Vector2f(0.25f, 0.25f));
		//guis.add(logo);
		
		
		
		Light sun = new Light(new Vector3f(1000, 1000, 2000), new Vector3f(0.3f, 0.3f, 0.3f));
		lights.add(sun);
		
		for(int i = 0; i < 3; i++){
        	float x = random.nextFloat() * 800;
        	float z = random.nextFloat() * -800;
        	float y = terrain.getHeightOfTerrain(x, z);
        	
        	Entity lamp = new Entity(lampModel, new Vector3f(x, y, z), 0, 0, 0, 1);
        	Light light = new Light(new Vector3f(x, y + 13, z), new Vector3f(2, 2, 0), new Vector3f(0.5f, 0.01f, 0.002f));
            entities.add(lamp);
            lights.add(light);
            
		}

		
		TexturedModel playerModel = new TexturedModel(OBJLoader.loadObjModel("player", loader), new ModelTexture(loader.loadTexture("player")));
		
		Player player = new Player(playerModel, new Vector3f(370, 0, -323), 0, 0, 0, 0.6f);
		Camera camera = new Camera(player);
		
        entities.add(new Entity(lampModel, new Vector3f(370, 0, -323), 0, 0, 0, 1));
        lights.add(new Light(new Vector3f(370, terrain.getHeightOfTerrain(370, -323) + 13, -323), new Vector3f(2, 2, 0), new Vector3f(0.5f, 0.01f, 0.002f)));
		
        
        for(int i = 0; i < 1000; i++){
        	float x = random.nextFloat() * 800;
        	float z = random.nextFloat() * -800;
        	float y = terrain.getHeightOfTerrain(x, z);
        	
            entities.add(new Entity(treeModel, new Vector3f(x, y, z), 0, 0, 0, 5));
            
        }
        
        for(int i = 0; i < 1000; i++){
        	float x = random.nextFloat() * 800;
        	float z = random.nextFloat() * -800;
        	float y = terrain.getHeightOfTerrain(x, z);
        	
            entities.add(new Entity(fernModel, random.nextInt(4), new Vector3f(x, y, z), 0, 0, 0, 1));
            
        }
		
		Mouse.setGrabbed(true);
		while(!Display.isCloseRequested()){
			player.move(terrain);
			camera.move();
			
			
			renderer.processEntity(player);
			renderer.processTerrain(terrain);
			for(Entity entity : entities){
                renderer.processEntity(entity);
            }
			for(GuiTexture gui : guis){
				renderer.processGui(gui);
            }
			
			renderer.render(lights, camera);
			
			DisplayManager.updateDisplay();
		}
		
		renderer.cleanUp();
		loader.cleanUp();
		DisplayManager.closeDisplay();
		
	}

}
