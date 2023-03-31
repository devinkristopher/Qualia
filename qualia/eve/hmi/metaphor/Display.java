import javax.swing.JPanel;
import java.awt.Graphics2D;

public abstract class Display extends JPanel {

    public void getScreenSpace() {
    }

    void fillBlade() {
        
    }

    void fillVolume() {
        
    }

    void drawFrame() {
        
    }

    void cleanUp(Graphics2D g) {
        g.dispose();
    }

    
}
