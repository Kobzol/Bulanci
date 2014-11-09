package cz.kobzol.bulanci.game;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import cz.kobzol.bulanci.command.CommandFactory;
import cz.kobzol.bulanci.command.CommandInvoker;
import cz.kobzol.bulanci.connection.ConnectionSide;
import cz.kobzol.bulanci.connection.LocalCommandRouter;
import cz.kobzol.bulanci.input.PlayerInputHandler;
import cz.kobzol.bulanci.map.Level;
import cz.kobzol.bulanci.map.MapLoader;
import cz.kobzol.bulanci.model.IDrawable;
import cz.kobzol.bulanci.model.IGameObject;
import cz.kobzol.bulanci.player.Player;

public class GameController extends ApplicationAdapter {
	private SpriteBatch batch;
    private AssetManager assetManager;
    private OrthographicCamera camera;
    private LocalCommandRouter localCommandRouter;
    private CommandInvoker commandInvoker = new CommandInvoker();
    private PlayerInputHandler inputHandler;

    private ConnectionSide client;

    private Game game;

    public GameController(ConnectionSide client) {
        this.client = client;
    }

	@Override
	public void create() {
		this.batch = new SpriteBatch();
        this.assetManager = this.loadAssets();

        Level level = new MapLoader(this.assetManager).parseLevel(Gdx.files.internal("map_proposal.xml"));

        Player localPlayer = new Player(this.client.getID(), "Kobzol");
        localPlayer.setControlledObject(level.getObjectByKey("bulanek"));

        level.addPlayer(localPlayer);

        this.game = new Game(level);

        this.camera = this.createCamera();
        this.localCommandRouter = new LocalCommandRouter(new CommandFactory(game), this.commandInvoker);
        this.localCommandRouter.setClientId(this.client, this.client.getID());
        this.inputHandler = new PlayerInputHandler(this.localCommandRouter);
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
        this.game.update();

        Gdx.gl.glClearColor(0, 0, 0.5f, 0.5f);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        this.camera.update();
        this.inputHandler.checkInput(Gdx.input);

        batch.setProjectionMatrix(this.camera.combined);
		batch.begin();

        this.game.getMap().draw(batch);

        for (IGameObject object : this.game.getObjects()) {
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
