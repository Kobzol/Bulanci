package cz.kobzol.bulanci;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import cz.kobzol.bulanci.command.CommandFactory;
import cz.kobzol.bulanci.command.CommandInvoker;
import cz.kobzol.bulanci.input.PlayerInputHandler;
import cz.kobzol.bulanci.map.Level;
import cz.kobzol.bulanci.map.MapLoader;
import cz.kobzol.bulanci.model.IDrawable;
import cz.kobzol.bulanci.model.IGameObject;
import cz.kobzol.bulanci.player.Player;

public class BulanciGame extends ApplicationAdapter {
	private SpriteBatch batch;
    private Level level;
    private AssetManager assetManager;
    private OrthographicCamera camera;
    private CommandFactory commandFactory;
    private CommandInvoker commandInvoker = new CommandInvoker();
    private PlayerInputHandler inputHandler;
	
	@Override
	public void create() {
		this.batch = new SpriteBatch();
        this.assetManager = this.loadAssets();

        this.level = new MapLoader(this.assetManager).parseLevel(Gdx.files.internal("map_proposal.xml"));

        Player localPlayer = new Player(1, "Kobzol");
        localPlayer.setControlledObject(this.level.getObjectByKey("bulanek"));

        this.level.addPlayer(localPlayer);

        this.camera = this.createCamera();
        this.commandFactory = new CommandFactory(this.level);
        this.inputHandler = new PlayerInputHandler(this.commandFactory, this.commandInvoker);
	}

    /**
     * Creates an orthographic camera.
     * @return orthographic camera
     */
    private OrthographicCamera createCamera() {
        OrthographicCamera camera = new OrthographicCamera();
        camera.setToOrtho(false, 800, 600);

        return camera;
    }

    /**
     * Loads the game's assets and returns them in an assetmanager.
     * @return assetmanager with game assets
     */
    private AssetManager loadAssets() {
        AssetManager assetManager = new AssetManager();
        assetManager.load("circle.png", Texture.class);
        assetManager.finishLoading();

        return assetManager;
    }

	@Override
	public void render()  {
        Gdx.gl.glClearColor(0, 0, 0.5f, 0.5f);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        this.camera.update();
        this.inputHandler.checkInput(Gdx.input);

        batch.setProjectionMatrix(this.camera.combined);
		batch.begin();

        this.level.getMap().draw(batch);

        for (IGameObject object : this.level.getObjects()) {
            if (object instanceof IDrawable) {
                ((IDrawable) object).draw(batch);
            }
        }

		batch.end();
	}

    @Override
    public void dispose() {
        this.assetManager.dispose();
    }
}
