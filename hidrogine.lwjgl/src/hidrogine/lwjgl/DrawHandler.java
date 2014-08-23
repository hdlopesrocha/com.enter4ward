package hidrogine.lwjgl;

public interface DrawHandler {

    public void beforeDraw(Group group, Material material);
    
    public void afterDraw(Group group, Material material);
    
}
