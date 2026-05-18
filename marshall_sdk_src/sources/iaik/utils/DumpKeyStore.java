package iaik.utils;

import com.felhr.usbserial.UsbSerialDebugger;
import iaik.asn1.ObjectID;
import iaik.asn1.structures.Name;
import iaik.pkcs.PKCS7CertList;
import iaik.pkcs.PKCSException;
import iaik.pkcs.pkcs12.CertificateBag;
import iaik.pkcs.pkcs12.KeyBag;
import iaik.pkcs.pkcs12.PKCS12;
import iaik.security.provider.IAIK;
import iaik.x509.X509Certificate;
import iaik.x509.extensions.SubjectKeyIdentifier;
import java.io.ByteArrayOutputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.PrintWriter;
import java.io.UnsupportedEncodingException;
import java.security.PrivateKey;
import java.security.Provider;
import java.security.Security;
import java.util.Random;

/* JADX INFO: loaded from: classes2.dex */
public class DumpKeyStore {
    private static String a = System.getProperty("file.separator");

    private static String a(X509Certificate x509Certificate) {
        Name name = (Name) x509Certificate.getSubjectDN();
        if (name == null) {
            return null;
        }
        String rdn = name.getRDN(ObjectID.commonName);
        return rdn == null ? name.toString() : rdn;
    }

    private static final void a() {
        System.out.println("Usage:\n");
        System.out.println("java DumpKeyStore <KeyStore File> <Out Dir> <password> [<KeyStore Type>]\n");
        System.out.println("e.g.:");
        System.out.println("java DumpKeyStore keystore.ks certs/ks password IAIKKeyStore\n");
    }

    private static final void a(String str) {
        System.err.println(str);
        Util.waitKey();
        System.exit(-1);
    }

    private static byte[] a(X509Certificate x509Certificate, String str) {
        byte[] fingerprint = null;
        try {
            SubjectKeyIdentifier subjectKeyIdentifier = (SubjectKeyIdentifier) x509Certificate.getExtension(SubjectKeyIdentifier.oid);
            if (subjectKeyIdentifier != null) {
                fingerprint = subjectKeyIdentifier.get();
            }
        } catch (Exception unused) {
        }
        if (fingerprint == null) {
            try {
                fingerprint = new SubjectKeyIdentifier(x509Certificate.getPublicKey()).get();
            } catch (Exception unused2) {
            }
        }
        if (fingerprint == null) {
            try {
                fingerprint = x509Certificate.getFingerprint();
            } catch (Exception unused3) {
            }
        }
        if (fingerprint == null && str != null) {
            try {
                try {
                    fingerprint = str.getBytes(UsbSerialDebugger.ENCODING);
                } catch (UnsupportedEncodingException unused4) {
                    fingerprint = str.getBytes("UTF8");
                }
            } catch (UnsupportedEncodingException unused5) {
                fingerprint = str.getBytes();
            }
        }
        if (fingerprint != null) {
            return fingerprint;
        }
        byte[] bArr = new byte[8];
        new Random().nextBytes(bArr);
        return bArr;
    }

    public static void main(String[] strArr) throws Throwable {
        IAIK.addAsProvider();
        try {
            Security.insertProviderAt((Provider) Class.forName("iaik.security.ecc.provider.ECCProvider").newInstance(), 2);
        } catch (Exception unused) {
        }
        if (strArr.length < 3 || strArr.length > 4) {
            a();
            System.exit(-1);
        }
        new DumpKeyStore().dump(strArr[0], strArr[1], strArr[2].toCharArray(), strArr.length == 4 ? strArr[3] : "IAIKKeyStore");
    }

    public static void saveCertificateChain(X509Certificate[] x509CertificateArr, String str, boolean z, boolean z2, boolean z3) throws Throwable {
        FileOutputStream fileOutputStream = null;
        PrintWriter printWriter = null;
        PrintWriter printWriter2 = null;
        try {
            FileOutputStream fileOutputStream2 = new FileOutputStream(str);
            int i = 0;
            try {
                if (!z2) {
                    int length = z3 ? x509CertificateArr.length : 1;
                    if (z) {
                        while (i < length) {
                            x509CertificateArr[i].writeTo(fileOutputStream2);
                            i++;
                        }
                    } else {
                        try {
                            PrintWriter printWriter3 = new PrintWriter(fileOutputStream2);
                            while (i < length) {
                                try {
                                    printWriter3.println("-----BEGIN CERTIFICATE-----");
                                    printWriter3.println(new String(Util.Base64Encode(x509CertificateArr[i].toByteArray())));
                                    printWriter3.println("-----END CERTIFICATE-----");
                                    i++;
                                } catch (Throwable th) {
                                    th = th;
                                    printWriter2 = printWriter3;
                                    if (printWriter2 != null) {
                                        try {
                                            printWriter2.close();
                                        } catch (Exception unused) {
                                        }
                                    }
                                    throw th;
                                }
                            }
                            try {
                                printWriter3.close();
                            } catch (Exception unused2) {
                            }
                        } catch (Throwable th2) {
                            th = th2;
                        }
                    }
                    try {
                        fileOutputStream2.flush();
                        fileOutputStream2.close();
                        return;
                    } catch (IOException unused3) {
                        return;
                    }
                }
                PKCS7CertList pKCS7CertList = new PKCS7CertList();
                if (z3) {
                    pKCS7CertList.setCertificateList(x509CertificateArr);
                } else {
                    pKCS7CertList.setCertificateList(new X509Certificate[]{x509CertificateArr[0]});
                }
                if (z) {
                    pKCS7CertList.writeTo(fileOutputStream2);
                } else {
                    try {
                        PrintWriter printWriter4 = new PrintWriter(fileOutputStream2);
                        try {
                            printWriter4.println("-----BEGIN PKCS7-----");
                            printWriter4.println(new String(Util.Base64Encode(pKCS7CertList.toByteArray())));
                            printWriter4.println("-----END PKCS7-----");
                            try {
                                printWriter4.close();
                            } catch (Throwable unused4) {
                            }
                        } catch (Throwable th3) {
                            th = th3;
                            printWriter = printWriter4;
                            if (printWriter != null) {
                                try {
                                    printWriter.close();
                                } catch (Throwable unused5) {
                                }
                            }
                            try {
                                throw th;
                            } catch (PKCSException e) {
                                throw new IOException(e.getMessage());
                            }
                        }
                    } catch (Throwable th4) {
                        th = th4;
                    }
                }
                try {
                    fileOutputStream2.flush();
                    fileOutputStream2.close();
                } catch (IOException unused6) {
                }
            } catch (Throwable th5) {
                th = th5;
                fileOutputStream = fileOutputStream2;
                if (fileOutputStream != null) {
                    try {
                        fileOutputStream.flush();
                        fileOutputStream.close();
                    } catch (IOException unused7) {
                    }
                }
                throw th;
            }
        } catch (Throwable th6) {
            th = th6;
        }
    }

    public static void saveToPKCS12(String str, X509Certificate[] x509CertificateArr, PrivateKey privateKey, String str2, boolean z, char[] cArr, boolean z2) throws IOException {
        FileOutputStream fileOutputStream = new FileOutputStream(str2);
        try {
            if (str == null) {
                try {
                    str = a(x509CertificateArr[0]);
                } catch (PKCSException e) {
                    throw new IOException(e.getMessage());
                }
            }
            byte[] bArrA = a(x509CertificateArr[0], str);
            int length = x509CertificateArr.length;
            if (!z2) {
                length = 1;
            }
            CertificateBag[] certificateBagArr = new CertificateBag[length];
            int i = length - 1;
            certificateBagArr[i] = new CertificateBag(x509CertificateArr[0]);
            certificateBagArr[i].setFriendlyName(str);
            certificateBagArr[i].setLocalKeyID(bArrA);
            if (z2) {
                for (int i2 = 1; i2 < length; i2++) {
                    int i3 = i - i2;
                    certificateBagArr[i3] = new CertificateBag(x509CertificateArr[i2]);
                    try {
                        String strA = a(x509CertificateArr[i2]);
                        if (strA != null) {
                            certificateBagArr[i3].setFriendlyName(strA);
                        }
                    } catch (Exception unused) {
                    }
                }
            }
            PKCS12 pkcs12 = new PKCS12(new KeyBag(privateKey, str, bArrA), certificateBagArr, true);
            pkcs12.encrypt(cArr);
            if (z) {
                pkcs12.writeTo(fileOutputStream);
            } else {
                ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
                pkcs12.writeTo(byteArrayOutputStream);
                PrintWriter printWriter = new PrintWriter(fileOutputStream);
                printWriter.println("-----BEGIN PKCS12-----");
                printWriter.println(new String(Util.Base64Encode(byteArrayOutputStream.toByteArray())));
                printWriter.println("-----END PKCS12-----");
                printWriter.close();
            }
        } finally {
            try {
                fileOutputStream.close();
            } catch (IOException unused2) {
            }
        }
    }

    /* JADX WARN: Removed duplicated region for block: B:135:0x035e A[EXC_TOP_SPLITTER, SYNTHETIC] */
    /* JADX WARN: Removed duplicated region for block: B:33:0x00e5  */
    /* JADX WARN: Removed duplicated region for block: B:44:0x0113 A[Catch: KeyStoreException -> 0x0340, TRY_LEAVE, TryCatch #5 {KeyStoreException -> 0x0340, blocks: (B:41:0x0107, B:42:0x010d, B:44:0x0113, B:47:0x0141, B:51:0x0163, B:54:0x016b, B:59:0x018a, B:61:0x01df, B:66:0x01fd, B:68:0x0227, B:73:0x0245, B:75:0x026c, B:79:0x0273, B:72:0x022d, B:65:0x01e5, B:58:0x0173, B:80:0x028b, B:82:0x0293, B:84:0x0299, B:89:0x02b7, B:91:0x02f9, B:93:0x02fc, B:99:0x0306, B:88:0x02a0, B:50:0x014a, B:102:0x0324), top: B:118:0x0107, inners: #2, #4, #6, #7, #9, #16 }] */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct add '--show-bad-code' argument
    */
    public void dump(java.lang.String r19, java.lang.String r20, char[] r21, java.lang.String r22) throws java.lang.Throwable {
        /*
            Method dump skipped, instruction units count: 866
            To view this dump add '--comments-level debug' option
        */
        throw new UnsupportedOperationException("Method not decompiled: iaik.utils.DumpKeyStore.dump(java.lang.String, java.lang.String, char[], java.lang.String):void");
    }
}
