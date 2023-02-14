package textures;

public class ModelTexture {

	private int textureID;
	
	private float shineDamper = 1;
	private float reflectivity = 0;
	
	private boolean hasTransparency = false;
	private boolean useFakeLighting = false;
	
	private int numberOfRows = 1;

	public ModelTexture(int id) {
		this.textureID = id;
	}
	
	public ModelTexture(int id, int numberOfRows) {
		this.textureID = id;
		this.numberOfRows = numberOfRows;
	}
	
	public ModelTexture(int id, boolean transparency, boolean useFakeLighting) {
		this.textureID = id;
		this.hasTransparency = transparency;
		this.useFakeLighting = useFakeLighting;
	}
	
	public ModelTexture(int id,  boolean transparency, boolean useFakeLighting, int numberOfRows) {
		this.textureID = id;
		this.hasTransparency = transparency;
		this.useFakeLighting = useFakeLighting;
		this.numberOfRows = numberOfRows;
	}
	
	public int getNumberOfRows() {
		return numberOfRows;
	}

	public void setNumberOfRows(int numberOfRows) {
		this.numberOfRows = numberOfRows;
	}

	public boolean isUseFakeLighting() {
		return useFakeLighting;
	}

	public void setUseFakeLighting(boolean useFakeLighting) {
		this.useFakeLighting = useFakeLighting;
	}

	public boolean isHasTransparency() {
		return hasTransparency;
	}

	public void setHasTransparency(boolean hasTransparency) {
		this.hasTransparency = hasTransparency;
	}

	public int getTextureID() {
		return this.textureID;
	}

	public float getShineDamper() {
		return shineDamper;
	}

	public void setShineDamper(float shineDamper) {
		this.shineDamper = shineDamper;
	}

	public float getReflectivity() {
		return reflectivity;
	}

	public void setReflectivity(float reflectivity) {
		this.reflectivity = reflectivity;
	}

	public void setTextureID(int textureID) {
		this.textureID = textureID;
	}
	
	
	
}
