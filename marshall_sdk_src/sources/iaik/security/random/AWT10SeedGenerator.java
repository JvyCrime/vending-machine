package iaik.security.random;

import java.awt.Event;

/* JADX INFO: loaded from: classes.dex */
public class AWT10SeedGenerator extends HashObjectSeedGenerator {
    public AWT10SeedGenerator() {
        this(136);
    }

    public AWT10SeedGenerator(int i) {
        super(i);
    }

    public boolean addEvent(Event event) {
        return addSeedObject(event);
    }

    @Override // iaik.security.random.HashObjectSeedGenerator
    protected int extractSeedData(Object obj) throws IllegalArgumentException {
        if (!(obj instanceof Event)) {
            throw new RandomException("Object not an instance of java.awt.Event!");
        }
        updateHash(System.currentTimeMillis());
        updateHash(obj.toString());
        updateHash(System.identityHashCode(obj));
        return 3;
    }
}
