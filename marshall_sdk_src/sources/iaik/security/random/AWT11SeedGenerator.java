package iaik.security.random;

import java.awt.AWTEvent;
import java.awt.Component;
import java.awt.event.KeyListener;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;
import java.util.Enumeration;
import java.util.Vector;

/* JADX INFO: loaded from: classes.dex */
public class AWT11SeedGenerator extends HashObjectSeedGenerator {
    private Vector g;
    private Object h;

    public AWT11SeedGenerator() {
        this(136);
    }

    public AWT11SeedGenerator(int i) {
        super(i);
        this.g = new Vector(4);
        setEventListener(null);
        b();
    }

    private void a(Component component) {
        Object obj = this.h;
        if (obj instanceof MouseMotionListener) {
            component.addMouseMotionListener((MouseMotionListener) obj);
        }
        Object obj2 = this.h;
        if (obj2 instanceof MouseListener) {
            component.addMouseListener((MouseListener) obj2);
        }
        Object obj3 = this.h;
        if (obj3 instanceof KeyListener) {
            component.addKeyListener((KeyListener) obj3);
        }
    }

    private void b() {
        c();
        Enumeration enumerationElements = this.g.elements();
        while (enumerationElements.hasMoreElements()) {
            a((Component) enumerationElements.nextElement());
        }
    }

    private void c() {
        Enumeration enumerationElements = this.g.elements();
        while (enumerationElements.hasMoreElements()) {
            Component component = (Component) enumerationElements.nextElement();
            Object obj = this.h;
            if (obj instanceof MouseMotionListener) {
                component.removeMouseMotionListener((MouseMotionListener) obj);
            }
            Object obj2 = this.h;
            if (obj2 instanceof MouseListener) {
                component.removeMouseListener((MouseListener) obj2);
            }
            Object obj3 = this.h;
            if (obj3 instanceof KeyListener) {
                component.removeKeyListener((KeyListener) obj3);
            }
        }
    }

    public boolean addEvent(AWTEvent aWTEvent) {
        return addSeedObject(aWTEvent);
    }

    public void addEventSource(Component component) {
        this.g.addElement(component);
        a(component);
    }

    @Override // iaik.security.random.HashObjectSeedGenerator
    protected int extractSeedData(Object obj) throws IllegalArgumentException {
        if (!(obj instanceof AWTEvent)) {
            throw new RandomException("Object not an instance of java.awt.event.AWTEvent!");
        }
        updateHash(System.currentTimeMillis());
        updateHash(obj.toString());
        updateHash(System.identityHashCode(obj));
        return 3;
    }

    public void setEventListener(Object obj) {
        if (this.h != null) {
            c();
        }
        if (obj == null) {
            obj = new a(this);
        }
        this.h = obj;
        b();
    }
}
