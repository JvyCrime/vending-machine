package iaik.pkcs.pkcs12;

import iaik.utils.InternalErrorException;
import iaik.utils.ObjectFactory;

/* JADX INFO: loaded from: classes.dex */
class a extends ObjectFactory {
    a() {
    }

    @Override // iaik.utils.ObjectFactory
    public Object create(Class cls) throws InstantiationException {
        try {
            return cls.newInstance();
        } catch (IllegalAccessException e) {
            throw new InternalErrorException("Error creating local implementation!", e);
        } catch (InstantiationException e2) {
            throw new InternalErrorException("Error creating local implementation!", e2);
        }
    }
}
