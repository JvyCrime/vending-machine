package iaik.x509.qualified;

import iaik.asn1.ObjectID;
import iaik.asn1.structures.PolicyInformation;
import iaik.utils.InternalErrorException;
import iaik.x509.V3Extension;
import iaik.x509.X509Certificate;
import iaik.x509.X509ExtensionException;
import iaik.x509.X509ExtensionInitException;
import iaik.x509.extensions.CertificatePolicies;
import iaik.x509.extensions.qualified.BiometricInfo;
import iaik.x509.extensions.qualified.QCStatements;
import iaik.x509.extensions.qualified.structures.QCStatement;
import java.io.IOException;
import java.io.InputStream;
import java.security.cert.CertificateException;
import java.util.Enumeration;
import java.util.Vector;

/* JADX INFO: loaded from: classes2.dex */
public class QualifiedCertificate extends X509Certificate {
    static Vector a = new Vector();
    static Vector b = new Vector();
    private transient QCStatement[] c;
    private transient PolicyInformation[] d;

    public QualifiedCertificate() {
    }

    public QualifiedCertificate(InputStream inputStream) throws QualifiedCertificateException, IOException, CertificateException {
        super(inputStream);
        if (!a()) {
            throw new QualifiedCertificateException("Initialization failed! No qualified cert!");
        }
    }

    public QualifiedCertificate(byte[] bArr) throws QualifiedCertificateException, CertificateException {
        super(bArr);
        if (!a()) {
            throw new QualifiedCertificateException("Initialization failed! No qualified cert!");
        }
    }

    private boolean a() {
        try {
            QCStatements qCStatements = getQCStatements();
            if (qCStatements != null) {
                this.c = containsQualifiedQCStatements(qCStatements);
            }
        } catch (Exception unused) {
        }
        try {
            CertificatePolicies certificatePolicies = (CertificatePolicies) getExtension(CertificatePolicies.oid);
            if (certificatePolicies != null) {
                this.d = containsQualifiedPolicyInformations(certificatePolicies);
            }
        } catch (Exception unused2) {
        }
        return (this.d == null && this.c == null) ? false : true;
    }

    public static void clearRegisteredQualifiedPolicyIDs() {
        a.removeAllElements();
    }

    public static void clearRegisteredQualifiedQCStatementIDs() {
        b.removeAllElements();
    }

    public static PolicyInformation[] containsQualifiedPolicyInformations(CertificatePolicies certificatePolicies) {
        Vector vector = new Vector();
        for (PolicyInformation policyInformation : certificatePolicies.getPolicyInformation()) {
            if (isQualifiedPolicyID(policyInformation.getPolicyIdentifier())) {
                vector.addElement(policyInformation);
            }
        }
        int size = vector.size();
        if (size <= 0) {
            return null;
        }
        PolicyInformation[] policyInformationArr = new PolicyInformation[size];
        vector.copyInto(policyInformationArr);
        return policyInformationArr;
    }

    public static QCStatement[] containsQualifiedQCStatements(QCStatements qCStatements) {
        Vector vector = new Vector();
        for (QCStatement qCStatement : qCStatements.getQCStatements()) {
            if (isQualifiedQCStatementID(qCStatement.getStatementID())) {
                vector.addElement(qCStatement);
            }
        }
        int size = vector.size();
        if (size <= 0) {
            return null;
        }
        QCStatement[] qCStatementArr = new QCStatement[size];
        vector.copyInto(qCStatementArr);
        return qCStatementArr;
    }

    public static ObjectID[] getRegisteredQualifiedPolicyIDs() {
        ObjectID[] objectIDArr = new ObjectID[a.size()];
        a.copyInto(objectIDArr);
        return objectIDArr;
    }

    public static ObjectID[] getRegisteredQualifiedQCStatementIDs() {
        ObjectID[] objectIDArr = new ObjectID[b.size()];
        b.copyInto(objectIDArr);
        return objectIDArr;
    }

    public static QualifiedCertificate isQualifedCertificate(X509Certificate x509Certificate) throws QualifiedCertificateException {
        try {
            return new QualifiedCertificate(x509Certificate.getEncoded());
        } catch (CertificateException e) {
            StringBuffer stringBuffer = new StringBuffer();
            stringBuffer.append("Error in reading certificate: ");
            stringBuffer.append(e.getMessage());
            throw new InternalErrorException(stringBuffer.toString(), e);
        }
    }

    public static boolean isQualifiedPolicyID(ObjectID objectID) {
        Enumeration enumerationElements = a.elements();
        while (enumerationElements.hasMoreElements()) {
            if (objectID.equals(enumerationElements.nextElement())) {
                return true;
            }
        }
        return false;
    }

    public static boolean isQualifiedQCStatementID(ObjectID objectID) {
        Enumeration enumerationElements = b.elements();
        while (enumerationElements.hasMoreElements()) {
            if (objectID.equals(enumerationElements.nextElement())) {
                return true;
            }
        }
        return false;
    }

    public static boolean registerQualifiedPolicyID(ObjectID objectID) {
        Enumeration enumerationElements = a.elements();
        while (enumerationElements.hasMoreElements()) {
            if (objectID.equals(enumerationElements.nextElement())) {
                return false;
            }
        }
        a.addElement(objectID);
        return true;
    }

    public static void registerQualifiedPolicyIDs(ObjectID[] objectIDArr) {
        a.removeAllElements();
        for (ObjectID objectID : objectIDArr) {
            a.addElement(objectID);
        }
    }

    public static boolean registerQualifiedQCStatementID(ObjectID objectID) {
        Enumeration enumerationElements = b.elements();
        while (enumerationElements.hasMoreElements()) {
            if (objectID.equals(enumerationElements.nextElement())) {
                return false;
            }
        }
        b.addElement(objectID);
        return true;
    }

    public static void registerQualifiedQCStatementIDs(ObjectID[] objectIDArr) {
        b.removeAllElements();
        for (ObjectID objectID : objectIDArr) {
            b.addElement(objectID);
        }
    }

    public static boolean removeRegisteredQualifiedPolicyID(ObjectID objectID) {
        return a.removeElement(objectID);
    }

    public static boolean removeRegisteredQualifiedQCStatementID(ObjectID objectID) {
        return b.removeElement(objectID);
    }

    @Override // iaik.x509.X509Certificate
    public void addExtension(V3Extension v3Extension) throws X509ExtensionException {
        super.addExtension(v3Extension);
        if (v3Extension.getObjectID().equals(CertificatePolicies.oid)) {
            this.d = containsQualifiedPolicyInformations((CertificatePolicies) v3Extension);
        } else if (v3Extension.getObjectID().equals(QCStatements.oid)) {
            this.c = containsQualifiedQCStatements((QCStatements) v3Extension);
        }
    }

    public BiometricInfo getBiometricInfo() throws X509ExtensionInitException {
        return (BiometricInfo) getExtension(BiometricInfo.oid);
    }

    public CertificatePolicies getCertificatePolicies() throws X509ExtensionInitException {
        return (CertificatePolicies) getExtension(CertificatePolicies.oid);
    }

    public QCStatements getQCStatements() throws X509ExtensionInitException {
        return (QCStatements) getExtension(QCStatements.oid);
    }

    public PolicyInformation[] getQualifiedPolicyInformations() {
        return this.d;
    }

    public QCStatement[] getQualifiedQCStatements() {
        return this.c;
    }

    @Override // iaik.x509.X509Certificate
    public void removeAllExtensions() {
        super.removeAllExtensions();
        this.d = null;
        this.c = null;
    }

    @Override // iaik.x509.X509Certificate
    public boolean removeExtension(ObjectID objectID) {
        boolean zRemoveExtension = super.removeExtension(objectID);
        if (zRemoveExtension) {
            if (objectID.equals(CertificatePolicies.oid)) {
                this.d = null;
            } else if (objectID.equals(QCStatements.oid)) {
                this.c = null;
            }
        }
        return zRemoveExtension;
    }

    public void setBiometricInfo(BiometricInfo biometricInfo) throws X509ExtensionException {
        addExtension(biometricInfo);
    }

    public void setCertificatePolicies(CertificatePolicies certificatePolicies) throws X509ExtensionException {
        addExtension(certificatePolicies);
        this.d = containsQualifiedPolicyInformations(certificatePolicies);
    }

    public void setQCStatements(QCStatements qCStatements) throws X509ExtensionException {
        addExtension(qCStatements);
        this.c = containsQualifiedQCStatements(qCStatements);
    }
}
