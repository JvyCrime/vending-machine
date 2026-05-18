package iaik.security.random;

import java.awt.AWTEvent;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;

/* JADX INFO: loaded from: classes.dex */
class a implements KeyListener, MouseListener, MouseMotionListener {
    AWT11SeedGenerator a;

    a(AWT11SeedGenerator aWT11SeedGenerator) {
        this.a = aWT11SeedGenerator;
    }

    private void a(AWTEvent aWTEvent) {
        this.a.addEvent(aWTEvent);
    }

    public void keyPressed(KeyEvent keyEvent) {
    }

    public void keyReleased(KeyEvent keyEvent) {
    }

    public void keyTyped(KeyEvent keyEvent) {
        a(keyEvent);
    }

    public void mouseClicked(MouseEvent mouseEvent) {
    }

    public void mouseDragged(MouseEvent mouseEvent) {
        a(mouseEvent);
    }

    public void mouseEntered(MouseEvent mouseEvent) {
    }

    public void mouseExited(MouseEvent mouseEvent) {
    }

    public void mouseMoved(MouseEvent mouseEvent) {
        a(mouseEvent);
    }

    public void mousePressed(MouseEvent mouseEvent) {
        a(mouseEvent);
    }

    public void mouseReleased(MouseEvent mouseEvent) {
    }
}
