package cz.kobzol.bulanci.connection;

public class DataPackage {

    enum Type {
        REQUEST, RESPONSE
    }

    protected Type type;

    protected String packageId;

    protected Object data;


    /**
     * Data package which is response or request
     */
    public DataPackage(Object data, String packageId, Type type) {
        this.data = data;
        this.packageId = packageId;
        this.type = type;
    }


    /**
     * Data package without waiting for response
     * @param data
     */
    public DataPackage(Object data) {
        this.data = data;
    }

    public Type getType() {
        return type;
    }

    /**
     * UUID for this package
     */
    public String getPackageId() {
        return packageId;
    }

    public Object getData() {
        return data;
    }

    public boolean isRequest()
    {
        return type == Type.RESPONSE;
    }

    public boolean isResponse()
    {
        return type == Type.RESPONSE;
    }

    /**
     * Waiting for response
     */
    public boolean isResponsible() {
        return packageId != null && type == Type.REQUEST;
    }
}
