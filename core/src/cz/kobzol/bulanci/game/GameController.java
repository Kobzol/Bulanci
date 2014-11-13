package cz.kobzol.bulanci.game;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import cz.kobzol.bulanci.command.util.CommandFactory;
import cz.kobzol.bulanci.command.util.CommandInvoker;
import cz.kobzol.bulanci.connection.ConnectionSide;
import cz.kobzol.bulanci.connection.DummyConnectionSide;
import cz.kobzol.bulanci.connection.LocalCommandRouter;
import cz.kobzol.bulanci.input.PlayerInputHandler;
import cz.kobzol.bulanci.map.Level;
import cz.kobzol.bulanci.map.MapLoader;
import cz.kobzol.bulanci.model.IDrawable;
import cz.kobzol.bulanci.model.IGameObject;
import cz.kobzol.bulanci.model.SpriteObject;
import cz.kobzol.bulanci.util.Files;

public class GameController extends ApplicationAdapter {
    private AssetManager assetManager;
    private OrthographicCamera camera;
    private SpriteBatch batch;
    private ShapeRenderer shapeRenderer;

    private PlayerInputHandler inputHandler;
    private LocalCommandRouter localCommandRouter;
    private CommandInvoker commandInvoker = new CommandInvoker();
    private GameClient client;

    private Game game;

    public GameController(ConnectionSide client) {
        this.client = new GameClient(client, this);
    }

    public AssetManager getAssetManager() {
        return this.assetManager;
    }

	@Override
	public void create() {
		this.batch = new SpriteBatch();
        this.shapeRenderer = new ShapeRenderer();
        this.assetManager = this.loadAssets();

        Level level = new MapLoader(this).parseLevel(Gdx.files.internal("map_proposal.xml"));
        this.game = new Game(level);
        this.game.createPlayer(this.client.getID());

        this.camera = this.createCamera();
        this.localCommandRouter = new LocalCommandRouter(new CommandFactory(game), this.commandInvoker);
        this.localCommandRouter.setClientId(this.client.getConnection(), this.client.getID());
        this.inputHandler = new PlayerInputHandler(this.localCommandRouter);

        this.client.setReady();

        if (this.client.getConnection() instanceof DummyConnectionSide) {
            this.startGame();
        }
	}

    /**
     * Creates an orthographic camera.
     * @return orthographic camera
     */
    private OrthographicCamera createCamera() {
        OrthographicCamera camera = new OrthographicCamera();
        camera.setToOrtho(false, 1920, 1080);

        return camera;
    }

    /**
     * Loads the game's assets and returns them in an assetmanager.
     * @return assetmanager with game assets
     */
    private AssetManager loadAssets() {
        AssetManager assetManager = new AssetManager();

        for (String file : Files.getFilesWithExtension("", "jpg,jpeg,png")) {
            assetManager.load(file, Texture.class);
        }

        assetManager.finishLoading();

        return assetManager;
    }

    public void startGame() {
        this.game.start();
    }

	@Override
	public void render()  {
        if (this.game.isRunning())
        {
            this.game.update();

            Gdx.gl.glClearColor(0, 0, 0.5f, 0.5f);
            Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

            this.camera.update();
            this.inputHandler.checkInput(Gdx.input);

            batch.setProjectionMatrix(this.camera.combined);
            batch.begin();

            this.game.getMap().draw(batch);

            for (IGameObject object : this.game.getLevel().getObjects()) {
                if (object instanceof IDrawable) {
                    ((IDrawable) object).draw(batch);
                }
            }

            batch.end();

            this.shapeRenderer.setProjectionMatrix(this.camera.combined);
            this.shapeRenderer.begin(ShapeRenderer.ShapeType.Line);
            for (IGameObject object : this.game.getLevel().getObjects()) {
                if (object instanceof SpriteObject) {
                    ((SpriteObject) object).drawShape(this.shapeRenderer);
                }
            }
            this.shapeRenderer.end();
        }
	}

    @Override
    public void dispose() {
        this.assetManager.dispose();
    }
}
