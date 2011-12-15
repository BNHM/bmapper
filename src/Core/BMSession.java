package Core;

import com.eaio.uuid.UUID;
import java.io.File;
import java.io.FileOutputStream;
import java.net.URL;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.io.IOException;
import java.nio.channels.Channels;
import java.nio.channels.ReadableByteChannel;

/**
 * Create a session by passing in a remote URL, copying file to local
 * server, and assigning a session variable.
 * 
 * Remote file expects certain fields to identify location, including:
 * Latitude,Longitude,ErrorRadiusInMeters,Datum
 * @author jdeck
 */
public class BMSession {

    private String session = null;
    final private String filesLocation = "/tmp/ms_tmp/";
    private File file = null;
    private File configFile = null;

    /**
     * Used for testing purposes only
     * @param args 
     */
    public static void main(String args[]) {
        try {
            int i = 0;
            //while (i < 1000) {
            // NOTE: encoding on darwin.berkeley.edu/amphibiaweb.txt is strange--
            // I spent 2 days researching this to no avail!!  It seems that this instance
            // has a strange encoding but could not find the reason.
            BMSession bm = new BMSession(new URL("http://berkeleymappertest.berkeley.edu/amphibiaweb.txt"));
            
            System.out.println(bm.getFile().getAbsoluteFile());
            //   i++;
            //}
        } catch (IOException ex) {
            Logger.getLogger(BMSession.class.getName()).log(Level.SEVERE, null, ex);
        }

    }

    public BMSession(URL url) throws IOException {
        UUID uuid = new UUID();
        this.session = String.valueOf(uuid);
        this.file = new File(filesLocation + session);
        this.configFile = new File(filesLocation + session + ".xml");
        String complete = url.getProtocol() + "://" + url.getHost() + ":" + url.getPort() + "/" + url.getFile();
            if (!copy(url, file)) {
                System.err.println("problem copying " + complete + " to local server to " + file.getAbsolutePath());
            } else {
                System.out.println("success copying " + complete + " to local server to " + file.getAbsolutePath());
            }
                  
    }

    public BMSession(String session) {
        this.session = session;
        this.file = new File(filesLocation + session);
    }

    public File getConfigFile() {
        return configFile;
    }

    public File getFile() {
        return file;
    }

    public String getFilesLocation() {
        return filesLocation;
    }

    public String getSessionString() {
        return session;
    }

    public static boolean copy(URL url, File file) {
        try {
            ReadableByteChannel rbc = Channels.newChannel(url.openStream());
            FileOutputStream fos = new FileOutputStream(file);
            fos.getChannel().transferFrom(rbc, 0, 1 << 24);
        } catch (IOException ex) {
            Logger.getLogger(BMSession.class.getName()).log(Level.SEVERE, null, ex);
            return false;
        }
        return true;
    }
    
}
