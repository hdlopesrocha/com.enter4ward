package hidrogine.webserver;

import java.io.File;


public class FileController extends Controller {

    @Override
    public Response process() {
        final String filename = getRequest().getUrl().substring(1);
        final File file = new File(filename);
        return ok(file);
        
    }


}
