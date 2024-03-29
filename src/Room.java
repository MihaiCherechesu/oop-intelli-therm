/**
 * Room class: stores the surface, name
 * and the a reference address to the proprietary device.
 */
public class Room {
    private final String name;
    private final Device device;
    private final int surface;

    public Room (String name, String id, int surface){
        this.name = name;
        this.device = new Device(id);
        this.surface = surface;
    }

    public String getName() {
        return name;
    }

    public Device getDevice() {
        return device;
    }

    public int getSurface() {
        return surface;
    }
}
