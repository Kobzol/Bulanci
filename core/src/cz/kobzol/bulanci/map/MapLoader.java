package cz.kobzol.bulanci.map;

import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.files.FileHandle;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.math.Vector2;
import cz.kobzol.bulanci.model.Shape;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import java.awt.*;
import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.lang.reflect.Constructor;

/**
 * Loads maps and objects from XML files.
 */
public class MapLoader {
    private static String CLASS_NAMESPACE = "cz.kobzol.bulanci";

    private final AssetManager assetManager;
    private int objectCounters = 0;

    public MapLoader(AssetManager assetManager) {
        this.assetManager = assetManager;
    }

    public Level parseLevel(FileHandle levelXML) {
        return this.parseLevel(levelXML.read());
    }

    public Level parseLevel(String levelXML) {
        return this.parseLevel(new ByteArrayInputStream(levelXML.getBytes()));
    }

    /**
     * Parses level from the given XML.
     * @param inputXML inputstream with XML
     * @return parsed Level or null in case of failure
     */
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
                else if (element.getTagName().equals("objects")) {
                    this.parseObjects(level, element);
                }
            }
        }

        return level;
    }

    /**
     * Parses objects from DOM element. Expects child nodes with tag object.
     * @param level level
     * @param elementObjects DOM element
     */
    private void parseObjects(Level level, Element elementObjects) {
        for (int i = 0; i < elementObjects.getChildNodes().getLength(); i++)
        {
            Node node = elementObjects.getChildNodes().item(i);

            if (node instanceof Element) {
                Element element = (Element) node;

                if (element.getTagName().equals("object")) {
                    if (element.getAttribute("interface").equals("model.Shape")) {
                        level.addObject(this.parseShape(element));
                    }
                }
            }
        }
    }

    /**
     * Parses shape from DOM element. Expects attribute class and child nodes dimension and location.
     * @param elementObject DOM element
     */
    private Shape parseShape(Element elementObject) {
        try {
            Class<?> objectClass = Class.forName(MapLoader.CLASS_NAMESPACE + "." + elementObject.getAttribute("class"));
            Constructor<?> objectConstructor = objectClass.getConstructor(int.class);
            Shape shape = (Shape) objectConstructor.newInstance(this.objectCounters++);

            for (int i = 0; i < elementObject.getChildNodes().getLength(); i++)
            {
                Node node = elementObject.getChildNodes().item(i);

                if (node instanceof Element) {
                    Element element = (Element) node;

                    if (element.getTagName().equals("dimension")) {
                        shape.setDimension(this.parseDimension(element));
                    }
                    else if (element.getTagName().equals("location")) {
                        shape.setPosition(this.parsePosition(element));
                    }
                }
            }

            return shape;
        }
        catch (Exception e) {
            e.printStackTrace();

            return null;
        }
    }

    /**
     * Parses map from DOM element. Expects child nodes name, background, dimension.
     * @param level level
     * @param elementProperties DOM element
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
     * Parses Vector2 (position) from DOM element. Expects double attributes x and y.
     * @param element DOM element
     * @return vector2 (position)
     */
    private Vector2 parsePosition(Element element) {
        return new Vector2(Float.parseFloat(element.getAttribute("x")), Float.parseFloat(element.getAttribute("y")));
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
