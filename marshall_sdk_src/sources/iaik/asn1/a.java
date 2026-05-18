package iaik.asn1;

import iaik.utils.InternalErrorException;
import iaik.utils.ObjectFactory;

/* JADX INFO: loaded from: classes.dex */
final class a extends ObjectFactory {
    protected a(Class cls) {
        super(30, cls);
    }

    @Override // iaik.utils.ObjectFactory
    public Object create(Class cls) {
        try {
            return cls.newInstance();
        } catch (IllegalAccessException e) {
            throw new InternalErrorException("Error creating local implementation!", e);
        } catch (InstantiationException e2) {
            throw new InternalErrorException("Error creating local implementation!", e2);
        }
    }
}
