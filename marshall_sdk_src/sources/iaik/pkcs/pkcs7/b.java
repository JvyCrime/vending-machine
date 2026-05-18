package iaik.pkcs.pkcs7;

import iaik.utils.InternalErrorException;
import iaik.utils.ObjectFactory;

/* JADX INFO: loaded from: classes.dex */
final class b extends ObjectFactory {
    protected b() {
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
