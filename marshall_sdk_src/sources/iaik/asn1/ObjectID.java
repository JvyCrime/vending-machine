package iaik.asn1;

import com.ftdi.j2xx.ft4222.FT_4222_Defines;
import java.io.IOException;
import java.io.OutputStream;
import java.math.BigInteger;
import java.util.HashMap;
import java.util.Objects;
import java.util.StringTokenizer;
import kotlin.jvm.internal.LongCompanionObject;

/* JADX INFO: loaded from: classes.dex */
public class ObjectID extends ASN1Object {
    public static final ObjectID accessIdentity;
    public static final ObjectID anyPolicy;
    public static final ObjectID attrCertEncAttrs;
    public static final ObjectID attrCertExt_AcceptableCertPolicies;
    public static final ObjectID attrCertExt_AuditIdentity;
    public static final ObjectID attrCertExt_BasicAttConstraints;
    public static final ObjectID attrCertExt_NoRevAvail;
    public static final ObjectID attrCertExt_ProxyInfo;
    public static final ObjectID attrCertExt_TargetInformation;
    public static final ObjectID authenticationInfo;
    public static final ObjectID caIssuers;
    public static final ObjectID caRepository;
    public static final ObjectID certExt_AuthorityInfoAccess;
    public static final ObjectID certExt_AuthorityKeyIdentifier;
    public static final ObjectID certExt_BiometricInfo;
    public static final ObjectID certExt_ExtendedKeyUsage;
    public static final ObjectID certExt_FreshestCRL;
    public static final ObjectID certExt_InhibitAnyPolicy;
    public static final ObjectID certExt_NetscapeBaseUrl;
    public static final ObjectID certExt_NetscapeCaPolicyUrl;
    public static final ObjectID certExt_NetscapeCaRevocationUrl;
    public static final ObjectID certExt_NetscapeCertRenewalUrl;
    public static final ObjectID certExt_NetscapeCertType;
    public static final ObjectID certExt_NetscapeComment;
    public static final ObjectID certExt_NetscapeRevocationUrl;
    public static final ObjectID certExt_NetscapeSSLServerName;
    public static final ObjectID certExt_NoCheck;
    public static final ObjectID certExt_PolicyConstraints;
    public static final ObjectID certExt_PublicAuthorityIdentifier;
    public static final ObjectID certExt_PublicServiceProvider;
    public static final ObjectID certExt_QcStatements;
    public static final ObjectID certExt_SubjectInfoAccess;
    public static final ObjectID certTypes;
    public static final ObjectID challengePassword;
    public static final ObjectID chargingIdentity;
    public static final ObjectID clearance;
    public static final ObjectID cms_authData;
    public static final ObjectID cms_authEnvelopedData;
    public static final ObjectID cms_compressedData;
    public static final ObjectID cms_contentInfo;
    public static final ObjectID cms_data;
    public static final ObjectID cms_digestedData;
    public static final ObjectID cms_encryptedData;
    public static final ObjectID cms_envelopedData;
    public static final ObjectID cms_signedData;
    public static final ObjectID contentHint;
    public static final ObjectID contentIdentifier;
    public static final ObjectID contentReference;
    public static final ObjectID contentType;
    public static final ObjectID countersignature;
    public static final ObjectID crlExt_AuthorityKeyIdentifier;
    public static final ObjectID crlExt_CertificateIssuer;
    public static final ObjectID crlExt_CrlNumber;
    public static final ObjectID crlExt_DeltaCRLIndicator;
    public static final ObjectID crlExt_ExpiredCertsOnCRL;
    public static final ObjectID crlExt_HoldInstructionCode;
    public static final ObjectID crlExt_InvalidityDate;
    public static final ObjectID crlExt_IssuingDistributionPoint;
    public static final ObjectID crlExt_ReasonCode;
    public static final ObjectID crlTypes;
    public static final ObjectID emailAddress;
    public static final ObjectID encrypKeyPref;
    public static final ObjectID equivalentLabels;
    public static final ObjectID extendedCertificateAttributes;
    public static final ObjectID extensionRequest;
    private static HashMap f;
    public static final ObjectID friendlyName;
    private static HashMap g;
    public static final ObjectID group;

    /* JADX INFO: renamed from: iaik, reason: collision with root package name */
    public static final ObjectID f3iaik;
    public static final ObjectID iaikKeyStoreV3;
    public static final ObjectID id_pkix_cps;
    public static final ObjectID id_pkix_unotice;
    public static final ObjectID localKeyID;
    public static final ObjectID messageDigest;
    public static final ObjectID mlExpandHistory;
    public static final ObjectID msgSigDigest;
    public static final ObjectID msoeEncrypKeyPref;
    public static final ObjectID netscape;
    public static final ObjectID netscapeCertSequence;
    public static final ObjectID ocsp;
    public static final ObjectID ocspExt_AcceptableResponses;
    public static final ObjectID ocspExt_ArchiveCutoff;
    public static final ObjectID ocspExt_CertHash;
    public static final ObjectID ocspExt_CrlID;
    public static final ObjectID ocspExt_ExtendedRevoked;
    public static final ObjectID ocspExt_Nonce;
    public static final ObjectID ocspExt_PreferredSignatureAlgorithms;
    public static final ObjectID ocspExt_ServiceLocator;
    public static final ObjectID pkcs12_CertBagIds;
    public static final ObjectID pkcs12_SDSICertBagId;
    public static final ObjectID pkcs12_Version1;
    public static final ObjectID pkcs12_Version1_BagIds;
    public static final ObjectID pkcs12_certBag;
    public static final ObjectID pkcs12_crlBag;
    public static final ObjectID pkcs12_keyBag;
    public static final ObjectID pkcs12_pkcs8ShroudedKeyBag;
    public static final ObjectID pkcs12_safeContentsBag;
    public static final ObjectID pkcs12_secretBag;
    public static final ObjectID pkcs12_x509CertCRLBagId;
    public static final ObjectID pkcs7;
    public static final ObjectID pkcs7_data;
    public static final ObjectID pkcs7_digestedData;
    public static final ObjectID pkcs7_encryptedData;
    public static final ObjectID pkcs7_envelopedData;
    public static final ObjectID pkcs7_signedAndEnvelopedData;
    public static final ObjectID pkcs7_signedData;
    public static final ObjectID qcEuCompliance;
    public static final ObjectID qcEuLimitValue;
    public static final ObjectID qcEuPDS;
    public static final ObjectID qcEuRetentionPeriod;
    public static final ObjectID qcEuSSCD;
    public static final ObjectID qcSyntaxV1;
    public static final ObjectID qcSyntaxV2;
    public static final ObjectID receipt;
    public static final ObjectID receiptRequest;
    public static final ObjectID role;
    public static final ObjectID sdsiCertificate;
    public static final ObjectID securityLabel;
    public static final ObjectID signingCertificate;
    public static final ObjectID signingCertificateV2;
    public static final ObjectID signingDescription;
    public static final ObjectID signingTime;
    public static final ObjectID smimeCapabilities;
    public static final ObjectID symmetricCapabilities;
    public static final ObjectID timeStampToken;
    public static final ObjectID timeStamping;
    public static final ObjectID tstInfo;
    public static final ObjectID unstructuredAddress;
    public static final ObjectID unstructuredName;
    public static final ObjectID x509Certificate;
    public static final ObjectID x509Crl;
    private String h;
    private static final int a = String.valueOf(LongCompanionObject.MAX_VALUE).length() - 1;
    private static final BigInteger b = BigInteger.valueOf(0);
    private static final BigInteger c = BigInteger.valueOf(2);
    private static final BigInteger d = BigInteger.valueOf(40);
    private static boolean e = true;
    public static final ObjectID commonName = new ObjectID("2.5.4.3", "commonName", "CN", false);
    public static final ObjectID serialNumber = new ObjectID("2.5.4.5", "serialNumber", null, false);
    public static final ObjectID country = new ObjectID("2.5.4.6", "countryName", "C", false);
    public static final ObjectID locality = new ObjectID("2.5.4.7", "localityName", "L", false);
    public static final ObjectID stateOrProvince = new ObjectID("2.5.4.8", "stateOrProvinceName", "ST", false);
    public static final ObjectID streetAddress = new ObjectID("2.5.4.9", "streetAddress", "STREET", false);
    public static final ObjectID organization = new ObjectID("2.5.4.10", "organizationName", "O", false);
    public static final ObjectID organizationalUnit = new ObjectID("2.5.4.11", "organizationalUnitName", "OU", false);
    public static final ObjectID title = new ObjectID("2.5.4.12", "title", "T", false);
    public static final ObjectID personalTitle = new ObjectID("0.9.2342.19200300.100.1.40", "personalTitle", null, false);
    public static final ObjectID description = new ObjectID("2.5.4.13", "description", null, false);
    public static final ObjectID businessCategory = new ObjectID("2.5.4.15", "businessCategory", null, false);
    public static final ObjectID postalAddress = new ObjectID("2.5.4.16", "postalAddress", null, false);
    public static final ObjectID postalCode = new ObjectID("2.5.4.17", "postalCode", null, false);
    public static final ObjectID telephoneNumber = new ObjectID("2.5.4.20", "telephoneNumber", null, false);
    public static final ObjectID telexNumber = new ObjectID("2.5.4.21", "telexNumber", null, false);
    public static final ObjectID surName = new ObjectID("2.5.4.4", "surname", "SN", false);
    public static final ObjectID givenName = new ObjectID("2.5.4.42", "givenName", null, false);
    public static final ObjectID initials = new ObjectID("2.5.4.43", "initials", null, false);
    public static final ObjectID generationQualifier = new ObjectID("2.5.4.44", "generationQualifier", null, false);
    public static final ObjectID uniqueIdentifier = new ObjectID("2.5.4.45", "uniqueIdentifier", null, false);
    public static final ObjectID dnQualifier = new ObjectID("2.5.4.46", "dnQualifier", null, false);
    public static final ObjectID pseudonym = new ObjectID("2.5.4.65", "pseudonym", null, false);
    public static final ObjectID domainComponent = new ObjectID("0.9.2342.19200300.100.1.25", "domainComponent", "DC", false);
    public static final ObjectID userid = new ObjectID("0.9.2342.19200300.100.1.1", "userid", "UID", false);
    public static final ObjectID dateOfBirth = new ObjectID("1.3.6.1.5.5.7.9.1", "dateOfBirth", null, false);
    public static final ObjectID placeOfBirth = new ObjectID("1.3.6.1.5.5.7.9.2", "placeOfBirth", null, false);
    public static final ObjectID gender = new ObjectID("1.3.6.1.5.5.7.9.3", "gender", null, false);
    public static final ObjectID countryOfCitizenship = new ObjectID("1.3.6.1.5.5.7.9.4", "countryOfCitizenship", null, false);
    public static final ObjectID countryOfResidence = new ObjectID("1.3.6.1.5.5.7.9.5", "countryOfResidence", null, false);
    public static final ObjectID basicOcspResponse = new ObjectID("1.3.6.1.5.5.7.48.1.1", "id-pkix-ocsp-basic", null, false);
    public static final ObjectID certExt_SubjectDirectoryAttributes = new ObjectID("2.5.29.9", "SubjectDirectoryAttributes", null, false);
    public static final ObjectID certExt_SubjectKeyIdentifier = new ObjectID("2.5.29.14", "SubjectKeyIdentifier", null, false);
    public static final ObjectID certExt_KeyUsage = new ObjectID("2.5.29.15", "KeyUsage", null, false);
    public static final ObjectID certExt_PrivateKeyUsagePeriod = new ObjectID("2.5.29.16", "PrivateKeyUsagePeriod", null, false);
    public static final ObjectID certExt_SubjectAltName = new ObjectID("2.5.29.17", "SubjectAltName", null, false);
    public static final ObjectID certExt_IssuerAltName = new ObjectID("2.5.29.18", "IssuerAltName", null, false);
    public static final ObjectID certExt_BasicConstraints = new ObjectID("2.5.29.19", "BasicConstraints", null, false);
    public static final ObjectID certExt_NameConstraints = new ObjectID("2.5.29.30", "NameConstraints", null, false);
    public static final ObjectID certExt_CrlDistributionPoints = new ObjectID("2.5.29.31", "CRLDistributionPoints", null, false);
    public static final ObjectID certExt_CertificatePolicies = new ObjectID("2.5.29.32", "CertificatePolicies", null, false);
    public static final ObjectID certExt_PolicyMappings = new ObjectID("2.5.29.33", "PolicyMappings", null, false);

    static {
        f = null;
        g = null;
        f = new HashMap(FT_4222_Defines.CHIPTOP_CMD.CHIPTOP_GET_SFR);
        g = new HashMap(20);
        ObjectID objectID = new ObjectID("2.5.29.35", "AuthorityKeyIdentifier", null, false);
        certExt_AuthorityKeyIdentifier = objectID;
        crlExt_AuthorityKeyIdentifier = objectID;
        certExt_PolicyConstraints = new ObjectID("2.5.29.36", "PolicyConstraints", null, false);
        certExt_ExtendedKeyUsage = new ObjectID("2.5.29.37", "ExtendedKeyUsage", null, false);
        certExt_FreshestCRL = new ObjectID("2.5.29.46", "FreshestCRL", null, false);
        certExt_InhibitAnyPolicy = new ObjectID("2.5.29.54", "InhibitAnyPolicy", null, false);
        certExt_AuthorityInfoAccess = new ObjectID("1.3.6.1.5.5.7.1.1", "AuthorityInfoAccess", null, false);
        certExt_SubjectInfoAccess = new ObjectID("1.3.6.1.5.5.7.1.11", "SubjectInfoAccess", null, false);
        crlExt_CrlNumber = new ObjectID("2.5.29.20", "CRLNumber", null, false);
        crlExt_ReasonCode = new ObjectID("2.5.29.21", "ReasonCode", null, false);
        crlExt_HoldInstructionCode = new ObjectID("2.5.29.23", "HoldInstructionCode", null, false);
        crlExt_InvalidityDate = new ObjectID("2.5.29.24", "InvalidityDate", null, false);
        crlExt_DeltaCRLIndicator = new ObjectID("2.5.29.27", "DeltaCRLIndicator", null, false);
        crlExt_IssuingDistributionPoint = new ObjectID("2.5.29.28", "IssuingDistributionPoint", null, false);
        crlExt_CertificateIssuer = new ObjectID("2.5.29.29", "CertificateIssuer", null, false);
        crlExt_ExpiredCertsOnCRL = new ObjectID("2.5.29.60", "ExpiredCertsOnCRL", null, false);
        certExt_NetscapeCertType = new ObjectID("2.16.840.1.113730.1.1", "NetscapeCertType", null, false);
        certExt_NetscapeBaseUrl = new ObjectID("2.16.840.1.113730.1.2", "NetscapeBaseUrl", null, false);
        certExt_NetscapeRevocationUrl = new ObjectID("2.16.840.1.113730.1.3", "NetscapeRevocationUrl", null, false);
        certExt_NetscapeCaRevocationUrl = new ObjectID("2.16.840.1.113730.1.4", "NetscapeCaRevocationUrl", null, false);
        certExt_NetscapeCaPolicyUrl = new ObjectID("2.16.840.1.113730.1.8", "NetscapeCaPolicyUrl", null, false);
        certExt_NetscapeCertRenewalUrl = new ObjectID("2.16.840.1.113730.1.7", "NetscapeCertRenewalUrl", null, false);
        certExt_NetscapeSSLServerName = new ObjectID("2.16.840.1.113730.1.12", "NetscapeSSLServerName", null, false);
        certExt_NetscapeComment = new ObjectID("2.16.840.1.113730.1.13", "NetscapeComment", null, false);
        certExt_BiometricInfo = new ObjectID("1.3.6.1.5.5.7.1.2", "BiometricInfo", null, false);
        certExt_QcStatements = new ObjectID("1.3.6.1.5.5.7.1.3", "QCStatements", null, false);
        certExt_NoCheck = new ObjectID("1.3.6.1.5.5.7.48.1.5", "NoCheck", null, false);
        certExt_PublicAuthorityIdentifier = new ObjectID("1.2.40.0.10.1.1.1", "PublicAuthorityIdentifier", null, false);
        certExt_PublicServiceProvider = new ObjectID("1.2.40.0.10.1.1.2", "PublicServiceProvider", null, false);
        attrCertExt_AuditIdentity = new ObjectID("1.3.6.1.5.5.7.1.4", "AuditIdentity", null, false);
        attrCertExt_BasicAttConstraints = new ObjectID("2.5.29.41", "BasicAttConstraints", null, false);
        attrCertExt_AcceptableCertPolicies = new ObjectID("2.5.29.52", "AcceptableCertPolicies", null, false);
        attrCertExt_TargetInformation = new ObjectID("2.5.29.55", "TargetInformation", null, false);
        attrCertExt_NoRevAvail = new ObjectID("2.5.29.56", "NoRevAvail", null, false);
        attrCertExt_ProxyInfo = new ObjectID("1.3.6.1.5.5.7.1.10", "ProxyInfo", null, false);
        ocspExt_Nonce = new ObjectID("1.3.6.1.5.5.7.48.1.2", "Nonce", null, false);
        ocspExt_CrlID = new ObjectID("1.3.6.1.5.5.7.48.1.3", "CrlID", null, false);
        ocspExt_AcceptableResponses = new ObjectID("1.3.6.1.5.5.7.48.1.4", "AcceptableResponses", null, false);
        ocspExt_ArchiveCutoff = new ObjectID("1.3.6.1.5.5.7.48.1.6", "ArchiveCutoff", null, false);
        ocspExt_ServiceLocator = new ObjectID("1.3.6.1.5.5.7.48.1.7", "ServiceLocator", null, false);
        ocspExt_PreferredSignatureAlgorithms = new ObjectID("1.3.6.1.5.5.7.48.1.8", "PreferredSignatureAlgorithms", null, false);
        ocspExt_ExtendedRevoked = new ObjectID("1.3.6.1.5.5.7.48.1.9", "ExtendedRevoked", null, false);
        ocspExt_CertHash = new ObjectID("1.3.36.8.3.13", "CertHash", null, false);
        ocsp = new ObjectID("1.3.6.1.5.5.7.48.1", "ocsp", null, false);
        caIssuers = new ObjectID("1.3.6.1.5.5.7.48.2", "caIssuers", null, false);
        caRepository = new ObjectID("1.3.6.1.5.5.7.48.5", "caRepository", null, false);
        timeStamping = new ObjectID("1.3.6.1.5.5.7.48.3", "timeStamping", null, false);
        timeStampToken = new ObjectID("1.2.840.113549.1.9.16.2.14", "timeStampToken", null, false);
        anyPolicy = new ObjectID("2.5.29.32.0", "anyPolicy", null, false);
        authenticationInfo = new ObjectID("1.3.6.1.5.5.7.10.1", "authenticationInfo", null, false);
        accessIdentity = new ObjectID("1.3.6.1.5.5.7.10.2", "accessIdentity", null, false);
        chargingIdentity = new ObjectID("1.3.6.1.5.5.7.10.3", "chargingIdentity", null, false);
        group = new ObjectID("1.3.6.1.5.5.7.10.4", "group", null, false);
        role = new ObjectID("2.5.4.72", "role", null, false);
        clearance = new ObjectID("2.5.1.5.55", "clearance", null, false);
        attrCertEncAttrs = new ObjectID("1.2.840.113549.1.9.16.1.14", "attrCertEncAttrs", null, false);
        qcSyntaxV1 = new ObjectID("1.3.6.1.5.5.7.11.1", "QCSyntaxV1", null, false);
        qcSyntaxV2 = new ObjectID("1.3.6.1.5.5.7.11.2", "QCSyntaxV2", null, false);
        qcEuCompliance = new ObjectID("0.4.0.1862.1.1", "QcEuCompliance", null, false);
        qcEuLimitValue = new ObjectID("0.4.0.1862.1.2", "QcEuLimitValue", null, false);
        qcEuRetentionPeriod = new ObjectID("0.4.0.1862.1.3", "QcEuRetentionPeriod", null, false);
        qcEuSSCD = new ObjectID("0.4.0.1862.1.4", "QcEuSSCD", null, false);
        qcEuPDS = new ObjectID("0.4.0.1862.1.5", "QcEuPDS", null, false);
        pkcs7 = new ObjectID("1.2.840.113549.1.7", "PKCS#7", null, false);
        pkcs7_data = new ObjectID("1.2.840.113549.1.7.1", "PKCS#7 data", null, false);
        pkcs7_signedData = new ObjectID("1.2.840.113549.1.7.2", "PKCS#7 signedData", null, false);
        pkcs7_envelopedData = new ObjectID("1.2.840.113549.1.7.3", "PKCS#7 envelopedData", null, false);
        pkcs7_signedAndEnvelopedData = new ObjectID("1.2.840.113549.1.7.4", "PKCS#7 signedAndEnvelopedData", null, false);
        pkcs7_digestedData = new ObjectID("1.2.840.113549.1.7.5", "PKCS#7 digestedData", null, false);
        pkcs7_encryptedData = new ObjectID("1.2.840.113549.1.7.6", "PKCS#7 encryptedData", null, false);
        tstInfo = new ObjectID("1.2.840.113549.1.9.16.1.4", "id-ct-TSTInfo", null, false);
        emailAddress = new ObjectID("1.2.840.113549.1.9.1", "emailAddress", "EMAIL", false);
        unstructuredName = new ObjectID("1.2.840.113549.1.9.2", "unstructuredName", null, false);
        contentType = new ObjectID("1.2.840.113549.1.9.3", "contentType", null, false);
        messageDigest = new ObjectID("1.2.840.113549.1.9.4", "messageDigest", null, false);
        signingTime = new ObjectID("1.2.840.113549.1.9.5", "signingTime", null, false);
        countersignature = new ObjectID("1.2.840.113549.1.9.6", "countersignature", null, false);
        challengePassword = new ObjectID("1.2.840.113549.1.9.7", "challengePassword", null, false);
        unstructuredAddress = new ObjectID("1.2.840.113549.1.9.8", "unstructuredAddress", null, false);
        extendedCertificateAttributes = new ObjectID("1.2.840.113549.1.9.9", "extendedCertificateAttributes", null, false);
        signingDescription = new ObjectID("1.2.840.113549.1.9.13", "signingDescription", null, false);
        extensionRequest = new ObjectID("1.2.840.113549.1.9.14", "extensionRequest", null, false);
        ObjectID objectID2 = new ObjectID("1.2.840.113549.1.9.15", "symmetricCapabilities", null, false);
        symmetricCapabilities = objectID2;
        friendlyName = new ObjectID("1.2.840.113549.1.9.20", "friendlyName", null, false);
        localKeyID = new ObjectID("1.2.840.113549.1.9.21", "localKeyID", null, false);
        certTypes = new ObjectID("1.2.840.113549.1.9.22", "certTypes", null, false);
        x509Certificate = new ObjectID("1.2.840.113549.1.9.22.1", "x509Certificate", null, false);
        sdsiCertificate = new ObjectID("1.2.840.113549.1.9.22.2", "sdsiCertificate", null, false);
        crlTypes = new ObjectID("1.2.840.113549.1.9.23", "crlTypes", null, false);
        x509Crl = new ObjectID("1.2.840.113549.1.9.23.1", "x509Crl", null, false);
        receipt = new ObjectID("1.2.840.113549.1.9.16.1.1", "receipt", null, false);
        receiptRequest = new ObjectID("1.2.840.113549.1.9.16.2.1", "receiptRequest", null, false);
        securityLabel = new ObjectID("1.2.840.113549.1.9.16.2.2", "securityLabel", null, false);
        mlExpandHistory = new ObjectID("1.2.840.113549.1.9.16.2.3", "mlExpandHistory", null, false);
        contentHint = new ObjectID("1.2.840.113549.1.9.16.2.4", "contentHint", null, false);
        msgSigDigest = new ObjectID("1.2.840.113549.1.9.16.2.5", "msgSigDigest", null, false);
        contentIdentifier = new ObjectID("1.2.840.113549.1.9.16.2.7", "contentIdentifier", null, false);
        equivalentLabels = new ObjectID("1.2.840.113549.1.9.16.2.9", "equivalentLabels", null, false);
        contentReference = new ObjectID("1.2.840.113549.1.9.16.2.10", "contentReference", null, false);
        signingCertificate = new ObjectID("1.2.840.113549.1.9.16.2.12", "signingCertificate", null, false);
        signingCertificateV2 = new ObjectID("1.2.840.113549.1.9.16.2.47", "signingCertificateV2", null, false);
        pkcs12_CertBagIds = new ObjectID("1.2.840.113549.1.12.4", "PKCS#12 CertBagIds", null, false);
        pkcs12_x509CertCRLBagId = new ObjectID("1.2.840.113549.1.12.4.1", "PKCS#12 x509CertCRLBagId", null, false);
        pkcs12_SDSICertBagId = new ObjectID("1.2.840.113549.1.12.4.2", "PKCS#12 SDSICertBagId", null, false);
        pkcs12_Version1 = new ObjectID("1.2.840.113549.1.12.10", "PKCS#12 Version1", null, false);
        pkcs12_Version1_BagIds = new ObjectID("1.2.840.113549.1.12.10.1", "PKCS#12 Version1 BagIds", null, false);
        pkcs12_keyBag = new ObjectID("1.2.840.113549.1.12.10.1.1", "PKCS#12 keyBag", null, false);
        pkcs12_pkcs8ShroudedKeyBag = new ObjectID("1.2.840.113549.1.12.10.1.2", "PKCS#12 pkcs8ShroudedKeyBag", null, false);
        pkcs12_certBag = new ObjectID("1.2.840.113549.1.12.10.1.3", "PKCS#12 certBag", null, false);
        pkcs12_crlBag = new ObjectID("1.2.840.113549.1.12.10.1.4", "PKCS#12 crlBag", null, false);
        pkcs12_secretBag = new ObjectID("1.2.840.113549.1.12.10.1.5", "PKCS#12 secretBag", null, false);
        pkcs12_safeContentsBag = new ObjectID("1.2.840.113549.1.12.10.1.6", "PKCS#12 safeContentsBag", null, false);
        id_pkix_cps = new ObjectID("1.3.6.1.5.5.7.2.1", "id-pkix-cps", null, false);
        id_pkix_unotice = new ObjectID("1.3.6.1.5.5.7.2.2", "id-pkix-unotice", null, false);
        netscape = new ObjectID("2.16.840.1.113730", "netscape", null, false);
        netscapeCertSequence = new ObjectID("2.16.840.1.113730.2.5", "netscapeCertSequence", null, false);
        cms_data = pkcs7_data;
        cms_signedData = pkcs7_signedData;
        cms_envelopedData = pkcs7_envelopedData;
        cms_digestedData = pkcs7_digestedData;
        cms_encryptedData = pkcs7_encryptedData;
        cms_authData = new ObjectID("1.2.840.113549.1.9.16.1.2", "CMS authenticatedData", null, false);
        cms_authEnvelopedData = new ObjectID("1.2.840.113549.1.9.16.1.23", "CMS authEnvelopedData", null, false);
        cms_compressedData = new ObjectID("1.2.840.113549.1.9.16.1.9", "CMS compressedData", null, false);
        cms_contentInfo = new ObjectID("1.2.840.113549.1.9.16.1.6", "CMS contentInfo", null, false);
        smimeCapabilities = objectID2;
        encrypKeyPref = new ObjectID("1.2.840.113549.1.9.16.2.11", "encryptionKeyPreference", null, false);
        msoeEncrypKeyPref = new ObjectID("1.3.6.1.4.1.311.16.4", "MSOE Encryption CertID", null, false);
        f3iaik = new ObjectID("1.3.6.1.4.1.2706", "IAIK", null, false);
        iaikKeyStoreV3 = new ObjectID("1.3.6.1.4.1.2706.2.1", "iaikKeyStoreV3", null, false);
    }

    protected ObjectID() {
        this.h = null;
        this.asnType = ASN.ObjectID;
    }

    public ObjectID(String str) {
        this();
        a(str, e);
    }

    public ObjectID(String str, String str2) {
        this();
        a(str, e);
        if (str2 != null) {
            f.put(str, str2);
        } else {
            f.remove(str);
        }
        g.remove(str);
    }

    public ObjectID(String str, String str2, String str3) {
        this(str, str2, str3, e);
    }

    public ObjectID(String str, String str2, String str3, boolean z) {
        this();
        a(str, z);
        if (str2 != null) {
            f.put(str, str2);
        } else {
            f.remove(str);
        }
        HashMap map = g;
        if (str3 != null) {
            map.put(str, str3);
        } else {
            map.remove(str);
        }
    }

    private long a(String[] strArr, int i) {
        if (i >= strArr.length) {
            return 0L;
        }
        return Long.parseLong(strArr[i]);
    }

    private void a(long j, OutputStream outputStream) throws IOException {
        outputStream.write((int) (127 & j));
        while (true) {
            j >>>= 7;
            if (j <= 0) {
                return;
            } else {
                outputStream.write((int) (128 | j));
            }
        }
    }

    private void a(String str, boolean z) {
        int i;
        if (z) {
            Objects.requireNonNull(str, "oid must not be null!");
            if (str.length() < 3) {
                throw new IllegalArgumentException("Invalid oid string. Too short!");
            }
            char cCharAt = str.charAt(0);
            if (cCharAt < '0' || cCharAt > '2') {
                StringBuffer stringBuffer = new StringBuffer();
                stringBuffer.append("Invalid oid string (");
                stringBuffer.append(str);
                stringBuffer.append("). First subid must be 0, 1, or 2!");
                throw new IllegalArgumentException(stringBuffer.toString());
            }
            char cCharAt2 = str.charAt(1);
            if (cCharAt2 != '.') {
                StringBuffer stringBuffer2 = new StringBuffer();
                stringBuffer2.append("Invalid oid string (");
                stringBuffer2.append(str);
                stringBuffer2.append("). Illegal character '");
                stringBuffer2.append(cCharAt2);
                stringBuffer2.append("' at position 1. Must be '.'!");
                throw new IllegalArgumentException(stringBuffer2.toString());
            }
            int i2 = 2;
            char cCharAt3 = str.charAt(2);
            if ((cCharAt == '0' || cCharAt == '1') && str.length() > 3 && str.charAt(3) != '.' && (cCharAt3 > '3' || (str.length() > 4 && str.charAt(4) != '.'))) {
                StringBuffer stringBuffer3 = new StringBuffer();
                stringBuffer3.append("Invalid oid string (");
                stringBuffer3.append(str);
                stringBuffer3.append("). Second subid to large; must be between 0 .. 39");
                throw new IllegalArgumentException(stringBuffer3.toString());
            }
            boolean z2 = true;
            while (i2 < str.length()) {
                char cCharAt4 = str.charAt(i2);
                boolean z3 = cCharAt4 == '.';
                if (z3) {
                    if (z2) {
                        StringBuffer stringBuffer4 = new StringBuffer();
                        stringBuffer4.append("Invalid oid string (");
                        stringBuffer4.append(str);
                        stringBuffer4.append("). Illegal '.' at position ");
                        stringBuffer4.append(i2);
                        stringBuffer4.append("!");
                        throw new IllegalArgumentException(stringBuffer4.toString());
                    }
                } else {
                    if (cCharAt4 < '0' || cCharAt4 > '9') {
                        StringBuffer stringBuffer5 = new StringBuffer();
                        stringBuffer5.append("Invalid oid string (");
                        stringBuffer5.append(str);
                        stringBuffer5.append("). Illegal character '");
                        stringBuffer5.append(cCharAt4);
                        stringBuffer5.append("' at position ");
                        stringBuffer5.append(i2);
                        stringBuffer5.append("!");
                        throw new IllegalArgumentException(stringBuffer5.toString());
                    }
                    if (cCharAt4 == '0' && z2 && str.length() > (i = i2 + 1) && str.charAt(i) != '.') {
                        StringBuffer stringBuffer6 = new StringBuffer();
                        stringBuffer6.append("Invalid oid string (");
                        stringBuffer6.append(str);
                        stringBuffer6.append("). Illegal leading zero in subid (oid position ");
                        stringBuffer6.append(i2);
                        stringBuffer6.append(")!");
                        throw new IllegalArgumentException(stringBuffer6.toString());
                    }
                }
                i2++;
                z2 = z3;
            }
        }
        this.h = str;
    }

    private void a(BigInteger bigInteger, OutputStream outputStream) throws IOException {
        outputStream.write(bigInteger.intValue() & 127);
        while (true) {
            bigInteger = bigInteger.shiftRight(7);
            if (bigInteger.compareTo(b) <= 0) {
                return;
            } else {
                outputStream.write(bigInteger.intValue() | 128);
            }
        }
    }

    private BigInteger b(String[] strArr, int i) {
        return i >= strArr.length ? BigInteger.valueOf(0L) : new BigInteger(strArr[i]);
    }

    public static void checkOID(boolean z) {
        e = z;
    }

    public static ObjectID getObjectID(String str) {
        return new ObjectID(str);
    }

    public static String getRegisteredName(String str) {
        return (String) f.get(str);
    }

    public static String getRegisteredShortName(String str) {
        return (String) g.get(str);
    }

    public static boolean hasRegisteredName(String str) {
        String str2 = (String) f.get(str);
        return str2 != null && str2.length() > 0;
    }

    /* JADX WARN: Code restructure failed: missing block: B:32:0x0065, code lost:
    
        if (r4 != null) goto L58;
     */
    /* JADX WARN: Code restructure failed: missing block: B:33:0x0067, code lost:
    
        if (r3 == false) goto L56;
     */
    /* JADX WARN: Code restructure failed: missing block: B:35:0x006d, code lost:
    
        if (r9 >= 40) goto L37;
     */
    /* JADX WARN: Code restructure failed: missing block: B:36:0x006f, code lost:
    
        r6 = 0;
     */
    /* JADX WARN: Code restructure failed: missing block: B:38:0x0075, code lost:
    
        if (r9 >= 80) goto L40;
     */
    /* JADX WARN: Code restructure failed: missing block: B:39:0x0077, code lost:
    
        r6 = 1;
     */
    /* JADX WARN: Code restructure failed: missing block: B:40:0x0079, code lost:
    
        r6 = 2;
     */
    /* JADX WARN: Code restructure failed: missing block: B:41:0x007a, code lost:
    
        r6 = r6;
        r0.append(r6);
        r0.append(".");
        r0.append(r9 - (40 * r6));
     */
    /* JADX WARN: Code restructure failed: missing block: B:42:0x0088, code lost:
    
        r0.append(r9);
     */
    /* JADX WARN: Code restructure failed: missing block: B:44:0x008f, code lost:
    
        if (r3 == false) goto L57;
     */
    /* JADX WARN: Code restructure failed: missing block: B:45:0x0091, code lost:
    
        r3 = r4.subtract(iaik.asn1.ObjectID.c.multiply(iaik.asn1.ObjectID.d));
        r0.append("2.");
        r0.append(r3.toString(10));
     */
    /* JADX WARN: Code restructure failed: missing block: B:46:0x00a9, code lost:
    
        r3 = false;
     */
    /* JADX WARN: Code restructure failed: missing block: B:47:0x00ac, code lost:
    
        r0.append(r4.toString(10));
     */
    @Override // iaik.asn1.ASN1Object
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct add '--show-bad-code' argument
    */
    protected void decode(int r16, java.io.InputStream r17) throws java.io.IOException {
        /*
            r15 = this;
            java.lang.StringBuffer r0 = new java.lang.StringBuffer
            r0.<init>()
            r1 = 1
            r2 = r16
            r3 = 1
        L9:
            if (r2 <= 0) goto Lbb
            r4 = 0
            int r5 = r17.read()
            r6 = -1
            if (r5 == r6) goto Lb5
            r7 = 128(0x80, float:1.8E-43)
            if (r5 != r7) goto L24
            boolean r8 = iaik.asn1.ObjectID.e
            if (r8 != 0) goto L1c
            goto L24
        L1c:
            java.io.IOException r0 = new java.io.IOException
            java.lang.String r1 = "Invalid OID encoding: found leading 0x80 in subid."
            r0.<init>(r1)
            throw r0
        L24:
            java.lang.String r8 = "."
            if (r3 != 0) goto L2b
            r0.append(r8)
        L2b:
            r9 = 0
        L2d:
            r11 = r5 & 127(0x7f, float:1.78E-43)
            long r11 = (long) r11
            long r9 = r9 | r11
        L31:
            int r2 = r2 + r6
            if (r2 <= 0) goto L64
            if (r5 < r7) goto L64
            int r5 = r17.read()
            if (r5 == r6) goto L5e
            r11 = 72057594037927935(0xffffffffffffff, double:7.291122019556397E-304)
            r13 = 7
            int r14 = (r9 > r11 ? 1 : (r9 == r11 ? 0 : -1))
            if (r14 >= 0) goto L48
            long r9 = r9 << r13
            goto L2d
        L48:
            if (r4 != 0) goto L4e
            java.math.BigInteger r4 = java.math.BigInteger.valueOf(r9)
        L4e:
            java.math.BigInteger r4 = r4.shiftLeft(r13)
            r11 = r5 & 127(0x7f, float:1.78E-43)
            long r11 = (long) r11
            java.math.BigInteger r11 = java.math.BigInteger.valueOf(r11)
            java.math.BigInteger r4 = r4.or(r11)
            goto L31
        L5e:
            java.io.EOFException r0 = new java.io.EOFException
            r0.<init>()
            throw r0
        L64:
            r5 = 0
            if (r4 != 0) goto L8d
            if (r3 == 0) goto L88
            r3 = 40
            int r6 = (r9 > r3 ? 1 : (r9 == r3 ? 0 : -1))
            if (r6 >= 0) goto L71
            r6 = 0
            goto L7a
        L71:
            r6 = 80
            int r11 = (r9 > r6 ? 1 : (r9 == r6 ? 0 : -1))
            if (r11 >= 0) goto L79
            r6 = 1
            goto L7a
        L79:
            r6 = 2
        L7a:
            long r6 = (long) r6
            long r3 = r3 * r6
            long r9 = r9 - r3
            r0.append(r6)
            r0.append(r8)
            r0.append(r9)
            goto La9
        L88:
            r0.append(r9)
            goto L9
        L8d:
            r6 = 10
            if (r3 == 0) goto Lac
            java.math.BigInteger r3 = iaik.asn1.ObjectID.c
            java.math.BigInteger r7 = iaik.asn1.ObjectID.d
            java.math.BigInteger r3 = r3.multiply(r7)
            java.math.BigInteger r3 = r4.subtract(r3)
            java.lang.String r4 = "2."
            r0.append(r4)
            java.lang.String r3 = r3.toString(r6)
            r0.append(r3)
        La9:
            r3 = 0
            goto L9
        Lac:
            java.lang.String r4 = r4.toString(r6)
            r0.append(r4)
            goto L9
        Lb5:
            java.io.EOFException r0 = new java.io.EOFException
            r0.<init>()
            throw r0
        Lbb:
            java.lang.String r1 = new java.lang.String
            r1.<init>(r0)
            r0 = r15
            r0.h = r1
            return
        */
        throw new UnsupportedOperationException("Method not decompiled: iaik.asn1.ObjectID.decode(int, java.io.InputStream):void");
    }

    @Override // iaik.asn1.ASN1Object
    protected void encode(OutputStream outputStream) throws IOException {
        StringTokenizer stringTokenizer = new StringTokenizer(this.h, ".");
        int iCountTokens = stringTokenizer.countTokens();
        String[] strArr = new String[iCountTokens];
        for (int i = 0; i < iCountTokens; i++) {
            strArr[i] = stringTokenizer.nextToken();
        }
        for (int i2 = iCountTokens - 1; i2 > 1; i2--) {
            if (strArr[i2].length() < a) {
                a(a(strArr, i2), outputStream);
            } else {
                a(b(strArr, i2), outputStream);
            }
        }
        if (iCountTokens > 1) {
            long jA = a(strArr, 0) * 40;
            if (strArr[1].length() < a) {
                a(jA + a(strArr, 1), outputStream);
            } else {
                a(BigInteger.valueOf(jA).add(b(strArr, 1)), outputStream);
            }
        }
    }

    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj instanceof ObjectID) {
            return this.h.equals(((ObjectID) obj).h);
        }
        return false;
    }

    public String getID() {
        return this.h;
    }

    public String getName() {
        String str = (String) f.get(this.h);
        return (str == null || str.length() == 0) ? this.h : str;
    }

    public String getNameAndID() {
        String string;
        StringBuffer stringBuffer = new StringBuffer();
        String str = (String) f.get(this.h);
        if (str == null || str.length() <= 0) {
            string = this.h;
        } else {
            stringBuffer.append(str);
            StringBuffer stringBuffer2 = new StringBuffer();
            stringBuffer2.append(" (");
            stringBuffer2.append(this.h);
            stringBuffer2.append(")");
            string = stringBuffer2.toString();
        }
        stringBuffer.append(string);
        return stringBuffer.toString();
    }

    public String getShortName() {
        String str = (String) g.get(this.h);
        if (str == null || str.length() == 0) {
            str = (String) f.get(this.h);
        }
        return (str == null || str.length() == 0) ? this.h : str;
    }

    @Override // iaik.asn1.ASN1Object
    public Object getValue() {
        return this.h;
    }

    public int hashCode() {
        return this.h.hashCode();
    }

    @Override // iaik.asn1.ASN1Object
    public void setValue(Object obj) {
        a((String) obj, e);
    }

    @Override // iaik.asn1.ASN1Object
    public String toString() {
        String string;
        String str = (String) f.get(this.h);
        if (str == null) {
            string = this.h;
        } else {
            StringBuffer stringBuffer = new StringBuffer(str);
            stringBuffer.append(" (");
            stringBuffer.append(this.h);
            stringBuffer.append(")");
            string = stringBuffer.toString();
        }
        if (this.asnType == null) {
            return string;
        }
        StringBuffer stringBuffer2 = new StringBuffer();
        stringBuffer2.append(super.toString());
        stringBuffer2.append(string);
        return stringBuffer2.toString();
    }
}
