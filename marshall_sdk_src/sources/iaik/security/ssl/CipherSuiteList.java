package iaik.security.ssl;

import java.io.IOException;
import java.io.Serializable;
import java.util.Enumeration;
import java.util.Vector;

/* JADX INFO: loaded from: classes.dex */
public class CipherSuiteList implements Serializable, Cloneable {
    public static final int L_ALL = 4;
    public static final int L_DEFAULT = 2;
    public static final int L_IMPLEMENTED = 3;
    public static final int L_NONE = 1;
    private Vector a;

    public CipherSuiteList() {
        this(1);
    }

    public CipherSuiteList(int i) {
        if (i == 1) {
            this.a = new Vector();
            return;
        }
        if (i == 2) {
            this.a = (Vector) CipherSuite.b.a.clone();
            ensureAvailable();
        } else if (i == 3) {
            this.a = (Vector) CipherSuite.c.a.clone();
            ensureAvailable();
        } else if (i == 4) {
            this.a = (Vector) CipherSuite.a.a.clone();
        } else {
            StringBuffer stringBuffer = new StringBuffer("Invalid parameter: ");
            stringBuffer.append(i);
            throw new IllegalArgumentException(stringBuffer.toString());
        }
    }

    public CipherSuiteList(CipherSuite cipherSuite) {
        this();
        add(cipherSuite);
    }

    public CipherSuiteList(CipherSuite[] cipherSuiteArr) {
        this();
        add(cipherSuiteArr);
    }

    public CipherSuiteList(String[] strArr) throws IllegalArgumentException {
        this();
        for (int i = 0; i < strArr.length; i++) {
            CipherSuite byName = CipherSuite.getByName(strArr[i]);
            if (byName == null) {
                StringBuffer stringBuffer = new StringBuffer("Unsupported cipher suite: ");
                stringBuffer.append(strArr[i]);
                throw new IllegalArgumentException(stringBuffer.toString());
            }
            add(byName);
        }
    }

    public CipherSuiteList(CipherSuiteList cipherSuiteList) {
        this.a = (Vector) cipherSuiteList.a.clone();
    }

    CipherSuiteList(ab abVar) throws IOException {
        this();
        a(abVar);
    }

    CipherSuiteList(ab abVar, int i, boolean z) throws IOException {
        this();
        a(abVar, i, z);
    }

    public synchronized void add(CipherSuite cipherSuite) {
        if (cipherSuite == null) {
            return;
        }
        if (!this.a.contains(cipherSuite)) {
            this.a.addElement(cipherSuite);
        }
    }

    public synchronized void remove(CipherSuite cipherSuite) {
        if (cipherSuite == null) {
            return;
        }
        this.a.removeElement(cipherSuite);
    }

    public boolean contains(CipherSuite cipherSuite) {
        if (cipherSuite == null) {
            return false;
        }
        return this.a.contains(cipherSuite);
    }

    public int indexOf(CipherSuite cipherSuite) {
        if (cipherSuite == null) {
            return -1;
        }
        return this.a.indexOf(cipherSuite);
    }

    boolean a() {
        Enumeration enumerationElements = elements();
        while (enumerationElements.hasMoreElements()) {
            if (((CipherSuite) enumerationElements.nextElement()).getKeyExchangeAlgorithm().indexOf("anon") == -1) {
                return true;
            }
        }
        return false;
    }

    public synchronized void add(CipherSuite[] cipherSuiteArr) {
        if (cipherSuiteArr == null) {
            return;
        }
        for (CipherSuite cipherSuite : cipherSuiteArr) {
            add(cipherSuite);
        }
    }

    public synchronized void remove(CipherSuite[] cipherSuiteArr) {
        if (cipherSuiteArr == null) {
            return;
        }
        for (CipherSuite cipherSuite : cipherSuiteArr) {
            remove(cipherSuite);
        }
    }

    public synchronized void clear() {
        this.a.removeAllElements();
    }

    public synchronized CipherSuite[] toArray() {
        CipherSuite[] cipherSuiteArr;
        int size = this.a.size();
        cipherSuiteArr = new CipherSuite[size];
        for (int i = 0; i < size; i++) {
            cipherSuiteArr[i] = (CipherSuite) this.a.elementAt(i);
        }
        return cipherSuiteArr;
    }

    public int size() {
        return this.a.size();
    }

    public CipherSuite elementAt(int i) throws ArrayIndexOutOfBoundsException {
        return (CipherSuite) this.a.elementAt(i);
    }

    public Enumeration elements() {
        return this.a.elements();
    }

    public synchronized void insertSorted(CipherSuite cipherSuite) {
        if (cipherSuite == null) {
            return;
        }
        if (contains(cipherSuite)) {
            return;
        }
        int size = this.a.size();
        for (int i = 0; i < size; i++) {
            if (cipherSuite.compareTo((CipherSuite) this.a.elementAt(i)) >= 0) {
                this.a.insertElementAt(cipherSuite, i);
                return;
            }
        }
        this.a.addElement(cipherSuite);
    }

    public synchronized void insertSorted(CipherSuite[] cipherSuiteArr) {
        if (cipherSuiteArr == null) {
            return;
        }
        for (CipherSuite cipherSuite : cipherSuiteArr) {
            insertSorted(cipherSuite);
        }
    }

    public synchronized void sort() {
        CipherSuiteList cipherSuiteList = new CipherSuiteList();
        Enumeration enumerationElements = this.a.elements();
        while (enumerationElements.hasMoreElements()) {
            cipherSuiteList.insertSorted((CipherSuite) enumerationElements.nextElement());
        }
        this.a = cipherSuiteList.a;
    }

    public synchronized void ensureAvailable() {
        a(0, SSLContext.VERSION_TLS12, (SignatureAndHashAlgorithmList) null);
    }

    synchronized void a(int i, int i2, SignatureAndHashAlgorithmList signatureAndHashAlgorithmList) {
        int size = this.a.size();
        while (size > 0) {
            size--;
            if (!((CipherSuite) this.a.elementAt(size)).a(i, i2, signatureAndHashAlgorithmList)) {
                this.a.removeElementAt(size);
            }
        }
    }

    public synchronized void intersectWith(CipherSuiteList cipherSuiteList) {
        int size = size();
        while (size > 0) {
            size--;
            CipherSuite cipherSuiteElementAt = elementAt(size);
            if (!cipherSuiteList.contains(cipherSuiteElementAt)) {
                remove(cipherSuiteElementAt);
            }
        }
    }

    void a(ag agVar, boolean z) throws IOException {
        int i = 2;
        byte[] bArr = new byte[(this.a.size() + (z ? 1 : 0)) * 2];
        if (z) {
            int id = CipherSuite.d.getID();
            bArr[0] = (byte) (id >> 8);
            bArr[1] = (byte) (id & 255);
        } else {
            i = 0;
        }
        Enumeration enumerationElements = this.a.elements();
        while (enumerationElements.hasMoreElements()) {
            int id2 = ((CipherSuite) enumerationElements.nextElement()).getID();
            int i2 = i + 1;
            bArr[i] = (byte) (id2 >> 8);
            i = i2 + 1;
            bArr[i2] = (byte) (id2 & 255);
        }
        agVar.a(bArr);
    }

    void a(ag agVar) throws IOException {
        Enumeration enumerationElements = this.a.elements();
        while (enumerationElements.hasMoreElements()) {
            agVar.e(((CipherSuite) enumerationElements.nextElement()).getID());
        }
    }

    void a(ab abVar) throws IOException {
        byte[] bArrG = abVar.g();
        int length = bArrG.length;
        if ((length & 1) != 0) {
            throw new SSLException("Invalid ciphersuite length");
        }
        this.a.ensureCapacity(length >> 1);
        for (int i = 0; i < length; i += 2) {
            this.a.addElement(CipherSuite.a(((bArrG[i] & 255) << 8) | (bArrG[i + 1] & 255)));
        }
    }

    void a(ab abVar, int i, boolean z) throws IOException {
        if (i % 3 != 0) {
            StringBuffer stringBuffer = new StringBuffer("Invalid ciphersuite length identifier: ");
            stringBuffer.append(i);
            throw new SSLException(stringBuffer.toString());
        }
        while (i > 0) {
            int iH = abVar.h();
            if (z || ((-65536) & iH) == 0) {
                this.a.addElement(CipherSuite.a(iH));
            }
            i -= 3;
        }
    }

    CipherSuiteList b() {
        CipherSuiteList cipherSuiteList = new CipherSuiteList();
        Enumeration enumerationElements = this.a.elements();
        while (enumerationElements.hasMoreElements()) {
            CipherSuite cipherSuiteB = ((CipherSuite) enumerationElements.nextElement()).b();
            if (cipherSuiteB != null) {
                cipherSuiteList.a.addElement(cipherSuiteB);
            }
        }
        return cipherSuiteList;
    }

    public Object clone() {
        try {
            CipherSuiteList cipherSuiteList = (CipherSuiteList) super.clone();
            try {
                cipherSuiteList.a = (Vector) this.a.clone();
                return cipherSuiteList;
            } catch (CloneNotSupportedException unused) {
                return cipherSuiteList;
            }
        } catch (CloneNotSupportedException unused2) {
            return null;
        }
    }

    public String toString() {
        return a("");
    }

    String a(String str) {
        StringBuffer stringBuffer = new StringBuffer(size() * 40);
        Enumeration enumerationElements = this.a.elements();
        while (enumerationElements.hasMoreElements()) {
            stringBuffer.append(str);
            stringBuffer.append(enumerationElements.nextElement());
            stringBuffer.append('\n');
        }
        return stringBuffer.toString();
    }
}
