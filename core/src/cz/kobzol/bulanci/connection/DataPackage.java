package cz.kobzol.bulanci.connection;

/**
 * INTERNAL! DO NOT USE, it is public for Kryonet library registration class
 */
public class DataPackage {


    protected boolean isResponse;

    protected String packageId;

    protected Object data;

    public DataPackage() {
    }

    /**
     * Data package which is response or request
     */
    public DataPackage(Object data, String packageId, boolean isResponse) {
        this.data = data;
        this.packageId = packageId;
        this.isResponse = isResponse;
    }


    /**
     * Data package without waiting for response
     * @param data
     */
    public DataPackage(Object data) {
        this.data = data;
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
        return !isResponse;
    }

    public boolean isResponse()
    {
        return isResponse;
    }

    /**
     * Waiting for response
     */
    public boolean isResponsible() {
        return packageId != null && isRequest();
    }
}
