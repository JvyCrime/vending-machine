package iaik.security.ssl;

import java.io.IOException;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.Hashtable;
import java.util.Objects;
import java.util.Vector;

/* JADX INFO: loaded from: classes.dex */
public class ExtensionList implements Cloneable {
    private static final HashMap a = new HashMap();
    private Hashtable b;
    private boolean c;
    private boolean d;

    static {
        a(ServerNameList.TYPE, "iaik.security.ssl.ServerNameList");
        a(MaxFragmentLength.TYPE, "iaik.security.ssl.MaxFragmentLength");
        a(ClientCertificateURL.TYPE, "iaik.security.ssl.ClientCertificateURL");
        a(TrustedAuthorities.TYPE, "iaik.security.ssl.TrustedAuthorities");
        a(TruncatedHMAC.TYPE, "iaik.security.ssl.TruncatedHMAC");
        a(CertificateStatusRequest.TYPE, "iaik.security.ssl.CertificateStatusRequest");
        a(SupportedEllipticCurves.TYPE, "iaik.security.ssl.SupportedEllipticCurves");
        a(SupportedPointFormats.TYPE, "iaik.security.ssl.SupportedPointFormats");
        a(SessionTicket.TYPE, "iaik.security.ssl.SessionTicket");
        a(RenegotiationInfo.b, "iaik.security.ssl.RenegotiationInfo");
        a(SignatureAlgorithms.TYPE, "iaik.security.ssl.SignatureAlgorithms");
        a(ExtendedMasterSecret.TYPE, "iaik.security.ssl.ExtendedMasterSecret");
        Utils.a();
    }

    private static final synchronized Extension a(ExtensionType extensionType) throws InstantiationException {
        String str;
        try {
            if (extensionType == null) {
                throw new NullPointerException("Extension type must not be null!");
            }
            str = (String) a.get(extensionType);
            if (str == null) {
                StringBuffer stringBuffer = new StringBuffer("No implementation for extension type + ");
                stringBuffer.append(extensionType);
                throw new InstantiationException(stringBuffer.toString());
            }
            try {
                try {
                    try {
                    } catch (ClassCastException unused) {
                        StringBuffer stringBuffer2 = new StringBuffer("Registered class (");
                        stringBuffer2.append(str);
                        stringBuffer2.append(") for extension type + ");
                        stringBuffer2.append(extensionType);
                        stringBuffer2.append("does not represent an Extension implementation!");
                        throw new InstantiationException(stringBuffer2.toString());
                    }
                } catch (Throwable th) {
                    StringBuffer stringBuffer3 = new StringBuffer("Cannot create instance for extension type + ");
                    stringBuffer3.append(extensionType);
                    stringBuffer3.append(":");
                    stringBuffer3.append(th.toString());
                    throw new InstantiationException(stringBuffer3.toString());
                }
            } catch (Throwable th2) {
                StringBuffer stringBuffer4 = new StringBuffer("No implementing class for extension type + ");
                stringBuffer4.append(extensionType);
                stringBuffer4.append(":");
                stringBuffer4.append(th2.toString());
                throw new InstantiationException(stringBuffer4.toString());
            }
        } catch (Throwable th3) {
            throw th3;
        }
        return (Extension) Class.forName(str).newInstance();
    }

    private static final synchronized void a(ExtensionType extensionType, String str) {
        if (extensionType == null) {
            throw new NullPointerException("Extension type must not be null!");
        }
        a.put(extensionType, str);
    }

    public ExtensionList() {
        this(false);
    }

    /* JADX WARN: 'this' call moved to the top of the method (can break code semantics) */
    public ExtensionList(Extension[] extensionArr) {
        this(false);
        if (extensionArr != null) {
            for (Extension extension : extensionArr) {
                this.b.put(extension.getExtensionType(), extension);
            }
        }
    }

    private ExtensionList(boolean z) {
        this.b = new Hashtable();
        this.c = z;
        this.d = true;
    }

    void a(boolean z) {
        this.d = z;
    }

    boolean a() {
        return this.d;
    }

    public boolean addExtension(Extension extension) {
        Objects.requireNonNull(extension, "Extension to be added must not be null!");
        return this.b.put(extension.getExtensionType(), extension) != null;
    }

    public boolean removeExtension(ExtensionType extensionType) {
        return this.b.remove(extensionType) != null;
    }

    public void removeAllExtensions() {
        this.b.clear();
    }

    public Enumeration listExtensions() {
        return this.b.elements();
    }

    public boolean hasExtensions() {
        return !this.b.isEmpty();
    }

    public int countExtensions() {
        return this.b.size();
    }

    public Enumeration listUnsupportedExtensions() {
        Vector vector = new Vector();
        Enumeration enumerationElements = this.b.elements();
        while (enumerationElements.hasMoreElements()) {
            Extension extension = (Extension) enumerationElements.nextElement();
            if (extension instanceof UnknownExtension) {
                vector.addElement(extension);
            }
        }
        return vector.elements();
    }

    public boolean hasUnsupportedExtensions() {
        return listUnsupportedExtensions().hasMoreElements();
    }

    Enumeration b() {
        Vector vector = new Vector();
        Enumeration enumerationElements = this.b.elements();
        while (enumerationElements.hasMoreElements()) {
            Extension extension = (Extension) enumerationElements.nextElement();
            if (extension.c() == 0) {
                vector.addElement(extension);
            }
        }
        return vector.elements();
    }

    public void setAllCritical(boolean z) {
        Enumeration enumerationElements = this.b.elements();
        while (enumerationElements.hasMoreElements()) {
            ((Extension) enumerationElements.nextElement()).setCritical(z);
        }
    }

    public Extension getExtension(ExtensionType extensionType) {
        return (Extension) this.b.get(extensionType);
    }

    public Enumeration getExtensionTypes() {
        return this.b.keys();
    }

    public Object clone() {
        try {
            ExtensionList extensionList = (ExtensionList) super.clone();
            try {
                extensionList.b = new Hashtable();
                Enumeration enumerationElements = this.b.elements();
                while (enumerationElements.hasMoreElements()) {
                    Extension extension = (Extension) enumerationElements.nextElement();
                    extensionList.b.put(extension.getExtensionType(), (Extension) extension.clone());
                }
                return extensionList;
            } catch (CloneNotSupportedException unused) {
                return extensionList;
            }
        } catch (CloneNotSupportedException unused2) {
            return null;
        }
    }

    ExtensionList a(Extension extension, boolean z) {
        ExtensionList extensionList = (!this.c || z) ? new ExtensionList(true) : this;
        Enumeration enumerationListExtensions = listExtensions();
        boolean z2 = false;
        while (enumerationListExtensions.hasMoreElements()) {
            Extension extension2 = (Extension) enumerationListExtensions.nextElement();
            if (extension != null && extension2.getType() == extension.getType()) {
                extensionList.addExtension(extension);
                z2 = true;
            } else {
                extensionList.addExtension(extension2);
            }
        }
        if (!z2 && extension != null) {
            extensionList.addExtension(extension);
        }
        return extensionList;
    }

    public String toString() {
        StringBuffer stringBuffer = new StringBuffer();
        Enumeration extensionTypes = getExtensionTypes();
        int i = 0;
        while (extensionTypes.hasMoreElements()) {
            int i2 = i + 1;
            if (i > 0) {
                stringBuffer.append(", ");
            }
            stringBuffer.append(extensionTypes.nextElement());
            i = i2;
        }
        return stringBuffer.toString();
    }

    public String toString(boolean z) {
        if (z) {
            StringBuffer stringBuffer = new StringBuffer();
            Enumeration enumerationListExtensions = listExtensions();
            int i = 1;
            while (enumerationListExtensions.hasMoreElements()) {
                Extension extension = (Extension) enumerationListExtensions.nextElement();
                StringBuffer stringBuffer2 = new StringBuffer("Extension [");
                stringBuffer2.append(i);
                stringBuffer2.append("] (");
                stringBuffer.append(stringBuffer2.toString());
                stringBuffer.append(extension.getExtensionType());
                stringBuffer.append("):\n");
                StringBuffer stringBuffer3 = new StringBuffer(String.valueOf(String.valueOf(extension)));
                stringBuffer3.append("\n\n");
                stringBuffer.append(stringBuffer3.toString());
                i++;
            }
            return stringBuffer.toString();
        }
        return toString();
    }

    int a(ab abVar, ExtensionList extensionList, SSLTransport sSLTransport) throws IOException {
        Extension unknownExtension;
        this.b.clear();
        int iF = abVar.f();
        boolean useClientMode = sSLTransport.getUseClientMode();
        int i = iF;
        while (i > 0) {
            ExtensionType extensionType = new ExtensionType(abVar);
            int i2 = i - 2;
            if (i2 <= 0) {
                StringBuffer stringBuffer = new StringBuffer("Extension ");
                stringBuffer.append(extensionType);
                stringBuffer.append(" does not contain data!");
                throw new SSLException(stringBuffer.toString(), 2, 50, false);
            }
            if (useClientMode && !extensionType.equals(RenegotiationInfo.b) && extensionList.getExtension(extensionType) == null) {
                StringBuffer stringBuffer2 = new StringBuffer("Server has sent unrequested extension: ");
                stringBuffer2.append(extensionType);
                throw new SSLException(stringBuffer2.toString(), 2, 110, false);
            }
            try {
                unknownExtension = a(extensionType);
                extensionType = unknownExtension.getExtensionType();
            } catch (InstantiationException unused) {
                unknownExtension = new UnknownExtension(extensionType);
            }
            unknownExtension.b(useClientMode);
            int iA = unknownExtension.a(abVar);
            this.b.put(extensionType, unknownExtension);
            i = i2 - iA;
            SSLException sSLExceptionB = unknownExtension.b();
            if (sSLExceptionB != null) {
                if (sSLExceptionB.getAlertLevel() == 1) {
                    if (sSLTransport.b != null) {
                        StringBuffer stringBuffer3 = new StringBuffer("Encountered problem during parsing ");
                        stringBuffer3.append(unknownExtension.getExtensionType());
                        stringBuffer3.append(" extension!");
                        sSLTransport.a(stringBuffer3.toString());
                    }
                    sSLTransport.c().a(sSLExceptionB.getAlertLevel(), sSLExceptionB.getAlertDescription());
                } else {
                    throw sSLExceptionB;
                }
            }
        }
        if (i == 0) {
            return iF + 2;
        }
        throw new SSLException("Extension list size does not match to length field!", 2, 50, false);
    }

    void a(v vVar) throws IOException {
        if (hasExtensions()) {
            int size = vVar.size();
            vVar.write(v.a);
            Enumeration enumerationElements = this.b.elements();
            while (enumerationElements.hasMoreElements()) {
                Extension extension = (Extension) enumerationElements.nextElement();
                extension.getExtensionType().a(vVar);
                extension.a(vVar);
            }
            vVar.b((vVar.size() - size) - 2, size);
        }
    }

    final ExtensionList a(ExtensionList extensionList, boolean z) throws SSLException {
        Extension extension;
        Extension extension2;
        Extension extension3;
        ExtensionList extensionList2 = new ExtensionList();
        if (extensionList == null) {
            Enumeration enumerationB = b();
            while (enumerationB.hasMoreElements()) {
                a((Extension) enumerationB.nextElement());
            }
        } else {
            Enumeration enumerationListExtensions = listExtensions();
            while (enumerationListExtensions.hasMoreElements()) {
                Extension extension4 = (Extension) enumerationListExtensions.nextElement();
                Extension extension5 = extensionList.getExtension(extension4.getExtensionType());
                if (extension5 == null) {
                    if (extension4.c() == 0) {
                        a(extension4);
                    }
                } else {
                    Extension extensionA = extension4.a(extension5);
                    if (extensionA != null) {
                        extensionList2.addExtension(extensionA);
                    }
                }
            }
            if (extensionList2.getExtension(SupportedEllipticCurves.TYPE) == null && (extension3 = extensionList.getExtension(SupportedEllipticCurves.TYPE)) != null && !z) {
                extensionList2.addExtension(extension3);
            }
            if (extensionList2.getExtension(SupportedPointFormats.TYPE) == null && (extension2 = extensionList.getExtension(SupportedPointFormats.TYPE)) != null) {
                extensionList2.addExtension(extension2);
            }
            if (extensionList2.getExtension(SignatureAlgorithms.TYPE) == null && (extension = extensionList.getExtension(SignatureAlgorithms.TYPE)) != null && !z) {
                extensionList2.addExtension(extension.a(new SignatureAlgorithms(SignatureAndHashAlgorithmList.getAll())));
            }
        }
        return extensionList2;
    }

    private void a(Extension extension) throws SSLException {
        int type = extension.getType();
        if (type == 10 || type == 13) {
            return;
        }
        StringBuffer stringBuffer = new StringBuffer("Peer did not send ");
        stringBuffer.append(extension.getExtensionType().getName());
        stringBuffer.append(" extension!");
        throw new SSLException(stringBuffer.toString(), 2, 80, false);
    }
}
