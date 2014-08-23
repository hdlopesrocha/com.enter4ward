package hidrogine.lwjgl;

import java.util.ArrayList;
import java.util.List;

public class Group {
    public List<BufferObject> subGroups = new ArrayList<BufferObject>();
    public float maxY = Float.MIN_VALUE;

    private String name;
    
    public Group(String n) {
        name=n;
    }

    public String getName() {
        return name;
    }
}
