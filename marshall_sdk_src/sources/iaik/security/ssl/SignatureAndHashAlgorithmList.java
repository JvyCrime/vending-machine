package iaik.security.ssl;

import java.io.IOException;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Iterator;

/* JADX INFO: loaded from: classes.dex */
public class SignatureAndHashAlgorithmList implements Serializable, Cloneable {
    public static final int L_ALL = 3;
    public static final int L_DEFAULT = 2;
    public static final int L_NONE = 1;
    private ArrayList a;

    public static final SignatureAndHashAlgorithmList getDefault() {
        return new SignatureAndHashAlgorithmList(2);
    }

    public static final SignatureAndHashAlgorithmList getAll() {
        return new SignatureAndHashAlgorithmList(3);
    }

    public SignatureAndHashAlgorithmList() {
        this(1);
    }

    public SignatureAndHashAlgorithmList(int i) {
        if (i == 1) {
            this.a = new ArrayList();
            return;
        }
        if (i == 2) {
            this.a = new ArrayList(SignatureAndHashAlgorithm.b);
            ensureAvailable();
        } else if (i == 3) {
            this.a = new ArrayList(SignatureAndHashAlgorithm.a);
            ensureAvailable();
        } else {
            StringBuffer stringBuffer = new StringBuffer("Invalid parameter: ");
            stringBuffer.append(i);
            throw new IllegalArgumentException(stringBuffer.toString());
        }
    }

    public SignatureAndHashAlgorithmList(SignatureAndHashAlgorithm signatureAndHashAlgorithm) {
        this();
        add(signatureAndHashAlgorithm);
    }

    public SignatureAndHashAlgorithmList(SignatureAndHashAlgorithm[] signatureAndHashAlgorithmArr) {
        this();
        add(signatureAndHashAlgorithmArr);
    }

    public SignatureAndHashAlgorithmList(SignatureAndHashAlgorithmList signatureAndHashAlgorithmList) {
        this.a = (ArrayList) signatureAndHashAlgorithmList.a.clone();
    }

    public SignatureAndHashAlgorithmList(String[] strArr) throws IllegalArgumentException {
        this();
        for (int i = 0; i < strArr.length; i++) {
            SignatureAndHashAlgorithm signatureAndHashAlgorithmA = SignatureAndHashAlgorithm.a(strArr[i]);
            if (signatureAndHashAlgorithmA == null) {
                StringBuffer stringBuffer = new StringBuffer("Unknown SignatureAndHashAlgorithm: ");
                stringBuffer.append(strArr[i]);
                throw new IllegalArgumentException(stringBuffer.toString());
            }
            add(signatureAndHashAlgorithmA);
        }
    }

    SignatureAndHashAlgorithmList(ab abVar) throws IOException {
        a(abVar);
    }

    SignatureAndHashAlgorithmList(ab abVar, int i) throws IOException {
        a(abVar, i);
    }

    public void add(SignatureAndHashAlgorithm signatureAndHashAlgorithm) {
        if (signatureAndHashAlgorithm == null || this.a.contains(signatureAndHashAlgorithm)) {
            return;
        }
        this.a.add(signatureAndHashAlgorithm);
    }

    public void remove(SignatureAndHashAlgorithm signatureAndHashAlgorithm) {
        if (signatureAndHashAlgorithm == null) {
            return;
        }
        this.a.remove(signatureAndHashAlgorithm);
    }

    public boolean contains(SignatureAndHashAlgorithm signatureAndHashAlgorithm) {
        if (signatureAndHashAlgorithm == null) {
            return false;
        }
        return this.a.contains(signatureAndHashAlgorithm);
    }

    public void add(SignatureAndHashAlgorithm[] signatureAndHashAlgorithmArr) {
        if (signatureAndHashAlgorithmArr == null) {
            return;
        }
        for (SignatureAndHashAlgorithm signatureAndHashAlgorithm : signatureAndHashAlgorithmArr) {
            add(signatureAndHashAlgorithm);
        }
    }

    public void remove(SignatureAndHashAlgorithm[] signatureAndHashAlgorithmArr) {
        if (signatureAndHashAlgorithmArr == null) {
            return;
        }
        for (SignatureAndHashAlgorithm signatureAndHashAlgorithm : signatureAndHashAlgorithmArr) {
            remove(signatureAndHashAlgorithm);
        }
    }

    public void clear() {
        this.a.clear();
    }

    public synchronized SignatureAndHashAlgorithm[] toArray() {
        SignatureAndHashAlgorithm[] signatureAndHashAlgorithmArr;
        int size = this.a.size();
        signatureAndHashAlgorithmArr = new SignatureAndHashAlgorithm[size];
        for (int i = 0; i < size; i++) {
            signatureAndHashAlgorithmArr[i] = (SignatureAndHashAlgorithm) this.a.get(i);
        }
        return signatureAndHashAlgorithmArr;
    }

    public int size() {
        return this.a.size();
    }

    public SignatureAndHashAlgorithm get(int i) throws IndexOutOfBoundsException {
        return (SignatureAndHashAlgorithm) this.a.get(i);
    }

    SignatureAndHashAlgorithm a(int i) {
        for (SignatureAndHashAlgorithm signatureAndHashAlgorithm : this.a) {
            if ((signatureAndHashAlgorithm.getId() & 255) == i) {
                return signatureAndHashAlgorithm;
            }
        }
        return null;
    }

    void b(int i) {
        int size = size();
        while (size > 0) {
            size--;
            SignatureAndHashAlgorithm signatureAndHashAlgorithm = get(size);
            if ((signatureAndHashAlgorithm.getId() & 255) == i) {
                remove(signatureAndHashAlgorithm);
            }
        }
    }

    public Iterator iterator() {
        return this.a.iterator();
    }

    public void ensureAvailable() {
        int size = this.a.size();
        while (size > 0) {
            size--;
            if (!((SignatureAndHashAlgorithm) this.a.get(size)).b()) {
                this.a.remove(size);
            }
        }
    }

    public void intersectWith(SignatureAndHashAlgorithmList signatureAndHashAlgorithmList) {
        int size = size();
        while (size > 0) {
            size--;
            SignatureAndHashAlgorithm signatureAndHashAlgorithm = get(size);
            if (!signatureAndHashAlgorithmList.contains(signatureAndHashAlgorithm)) {
                remove(signatureAndHashAlgorithm);
            }
        }
    }

    void a(ak akVar, int i) throws IOException {
        if (i == 0) {
            throw new SSLException("SignatureAndHashAlgorithm list must not be empty!");
        }
        SignatureAndHashAlgorithm.a(this.a, i, akVar);
    }

    void a(ab abVar) throws IOException {
        a(abVar, -1);
    }

    void a(ab abVar, int i) throws IOException {
        this.a = SignatureAndHashAlgorithm.a(abVar, i);
    }

    public int hashCode() {
        Iterator it = this.a.iterator();
        int iHashCode = 1;
        while (it.hasNext()) {
            iHashCode += it.next().hashCode();
        }
        return iHashCode;
    }

    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj instanceof SignatureAndHashAlgorithmList) {
            return this.a.equals(((SignatureAndHashAlgorithmList) obj).a);
        }
        return false;
    }

    public Object clone() {
        try {
            SignatureAndHashAlgorithmList signatureAndHashAlgorithmList = (SignatureAndHashAlgorithmList) super.clone();
            try {
                signatureAndHashAlgorithmList.a = (ArrayList) this.a.clone();
                return signatureAndHashAlgorithmList;
            } catch (CloneNotSupportedException unused) {
                return signatureAndHashAlgorithmList;
            }
        } catch (CloneNotSupportedException unused2) {
            return null;
        }
    }

    public String toString() {
        StringBuffer stringBuffer = new StringBuffer();
        Iterator it = this.a.iterator();
        int i = 0;
        while (it.hasNext()) {
            int i2 = i + 1;
            if (i > 0) {
                stringBuffer.append(", ");
            }
            stringBuffer.append(it.next());
            i = i2;
        }
        return stringBuffer.toString();
    }
}
