package cz.kobzol.bulanci.map;

import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.files.FileHandle;
import com.badlogic.gdx.graphics.Texture;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import java.awt.*;
import java.io.ByteArrayInputStream;
import java.io.InputStream;

/**
 * Loads maps and objects from XML files.
 */
public class MapLoader {
    private final AssetManager assetManager;

    public MapLoader(AssetManager assetManager) {
        this.assetManager = assetManager;
    }

    public Level parseLevel(FileHandle levelXML) {
        return this.parseLevel(levelXML.read());
    }

    public Level parseLevel(String levelXML) {
        return this.parseLevel(new ByteArrayInputStream(levelXML.getBytes()));
    }

    public Level parseLevel(InputStream inputXML) {
        Level level = new Level();
        Document dom = this.createDOM(inputXML);

        NodeList nodes = dom.getDocumentElement().getChildNodes();

        for (int i = 0; i < nodes.getLength(); i++) {
            Node node = nodes.item(i);

            if (node instanceof Element) {
                Element element = (Element) node;

                if (element.getTagName().equals("properties")) {
                    this.parseMap(level, element);
                }
            }
        }

        return level;
    }

    /**
     * Parses map from DOM element. Expects child nodes name, background, dimension.
     * @param level level
     * @param dom DOM
     */
    private void parseMap(Level level, Element elementProperties) {
        String name = "";
        Texture background = null;
        Dimension dimension = new Dimension(0, 0);

        for (int i = 0; i < elementProperties.getChildNodes().getLength(); i++)
        {
            Node node = elementProperties.getChildNodes().item(i);

            if (node instanceof Element) {
                Element element = (Element) node;

                if (element.getTagName().equals("name")) {
                    name = element.getAttribute("value");
                }
                else if (element.getTagName().equals("background")) {
                    background = assetManager.get(element.getAttribute("value"), Texture.class);
                }
                else if (element.getTagName().equals("dimension")) {
                    dimension = this.parseDimension(element);
                }
            }
        }

        level.setMap(new Map(name, background, dimension));
    }

    /**
     * Parses Dimension from DOM element. Expects double attributes width and height.
     * @param element DOM element
     * @return dimension
     */
    private Dimension parseDimension(Element element) {
        Dimension dimension = new Dimension();

        dimension.setSize(Double.parseDouble(element.getAttribute("width")), Double.parseDouble(element.getAttribute("height")));

        return dimension;
    }

    /**
     * Creates DOM from the given string.
     * @param input input string
     * @return DOM created from the string or null if the DOM build failed
     */
    private Document createDOM(InputStream input) {
        try {
            DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
            DocumentBuilder db = dbf.newDocumentBuilder();

            Document dom = db.parse(input);
            dom.normalizeDocument();

            return dom;
        }
        catch (Exception e) {
            return null;
        }
    }
}
