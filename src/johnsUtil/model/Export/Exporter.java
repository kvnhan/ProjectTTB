package johnsUtil.model.Export;

import java.io.File;
import java.io.IOException;

/**
 * Created by John on 4/18/2017.
 */
public interface Exporter {
    public void open() throws IOException;
    public void close() throws IOException;
    public void fill() throws IOException;
    public File save() throws IOException;
    public void setSource(File source);
    public void setTarget(File target);
    public void setForm(Form form);
    public boolean isOpen();
}
