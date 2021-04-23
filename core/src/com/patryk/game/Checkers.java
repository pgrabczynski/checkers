package com.patryk.game;

import com.badlogic.gdx.ApplicationListener;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.PerspectiveCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g3d.Environment;
import com.badlogic.gdx.graphics.g3d.Model;
import com.badlogic.gdx.graphics.g3d.ModelBatch;
import com.badlogic.gdx.graphics.g3d.ModelInstance;
import com.badlogic.gdx.graphics.g3d.attributes.ColorAttribute;
import com.badlogic.gdx.graphics.g3d.environment.DirectionalLight;
import com.badlogic.gdx.graphics.g3d.utils.CameraInputController;
import com.badlogic.gdx.utils.Array;



public class Checkers implements ApplicationListener {
	public PerspectiveCamera cam;
	public CameraInputController camController;
	public ModelBatch modelBatch;
	public AssetManager assets;
	public Array<ModelInstance> instances = new Array<>();
	public Environment environment;
	public boolean loading;
	@Override
	public void create () {

	modelBatch = new ModelBatch();
	environment = new Environment();
	environment.set(new ColorAttribute(ColorAttribute.AmbientLight, 1.4f, 1.4f, 1.4f, 1f));
	environment.add(new DirectionalLight().set(0.8f,0.8f,0.8f, -1f, -0.8f, -0.2f));

	cam = new PerspectiveCamera(67, Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
	cam.position.set(1.0f,1.0f,1.0f);
	cam.lookAt(0,0,0);
	cam.near = 0.01f;
	cam.far = 3000f;
	cam.update();


	camController = new CameraInputController(cam);
	Gdx.input.setInputProcessor(camController);
	assets = new AssetManager();
	assets = new AssetManager();


	assets.load("data/1.obj", Model.class);
	//texture = new Texture(Gdx.files.internal("data/textureChessboard.jpg"));
	//loading = true;
	assets.load("data/chessboard.obj", Model.class);
	loading = true;
	assets.load("data/textureChessboard.jpg", Texture.class);


	}

	public void doneLoading(){
		//ModelBuilder chessBoard = new ModelBuilder();

		//chessBoardModel = chessBoard.createBox(1.5f,0.01f,1.5f, new Material( ColorAttribute.createDiffuse(Color.BROWN)), VertexAttributes.Usage.Position | VertexAttributes.Usage.Normal | VertexAttributes.Usage.TextureCoordinates);

		//chessBoardModelInstance = new ModelInstance(chessBoardModel);

		/*

		do zrobienia wczytywanie chessboard (szachownica)

		 */


		Model chessBoard = assets.get("data/chessboard.obj", Model.class);

		ModelInstance chessBoardInstance = new ModelInstance(chessBoard);
		chessBoardInstance.transform.setToScaling(4,1,4);
		instances.add(chessBoardInstance);



		Model checkers = assets.get("data/1.obj", Model.class);

		for (float x = -0.7f; x <= 0.7f; x += 0.2f) {
			for (float z = -0.7f; z <= 0.7f; z += 0.2f) {
				if(x!=0.1f) {
					ModelInstance checkerInstance = new ModelInstance(checkers);
					checkerInstance.transform.setToTranslation(x, 0, z);
					instances.add(checkerInstance);
				}
			}
		}

		//ModelInstance checkersInstance = new ModelInstance(checkers);
		//instances.add(checkersInstance);
		loading = false;

	}

	@Override
	public void render () {
		if(loading && assets.update()) {
			doneLoading();
		}
		camController.update();
		Gdx.gl.glViewport(0, 0, Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT | GL20.GL_DEPTH_BUFFER_BIT);

		modelBatch.begin(cam);
		modelBatch.render(instances, environment);
		modelBatch.end();
	}
	
	@Override
	public void dispose () {
		modelBatch.dispose();
		instances.clear();
		assets.dispose();
	}
	public void resume(){

	}
	public void resize(int width, int height){

	}
	public void pause(){

	}
}
