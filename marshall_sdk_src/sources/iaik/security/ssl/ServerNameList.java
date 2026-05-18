package iaik.security.ssl;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.util.Vector;

/* JADX INFO: loaded from: classes.dex */
public class ServerNameList extends Extension implements Cloneable {
    public static final ExtensionType TYPE = new ExtensionType(0, "server_name");
    private static boolean b = false;
    private ServerName[] c;

    public static void setAllowMoreThanOneServerNamesOfSameType(boolean z) {
        b = z;
    }

    static boolean e() {
        return b;
    }

    public ServerNameList() {
        super(TYPE);
    }

    public ServerNameList(ServerName[] serverNameArr) {
        this();
        a(serverNameArr);
    }

    public ServerName[] getServerNames() {
        return this.c;
    }

    void a(ServerName[] serverNameArr) {
        if (!b && serverNameArr != null) {
            if (serverNameArr.length > 1) {
                int[] iArr = new int[serverNameArr.length];
                iArr[0] = serverNameArr[0].getType();
                for (int i = 1; i < serverNameArr.length; i++) {
                    int type = serverNameArr[i].getType();
                    for (int i2 = 0; i2 < i; i2++) {
                        if (type == iArr[i2]) {
                            StringBuffer stringBuffer = new StringBuffer("Multiple ServerNames of same type (");
                            stringBuffer.append(type);
                            stringBuffer.append(") are not allowed!");
                            throw new IllegalArgumentException(stringBuffer.toString());
                        }
                    }
                    iArr[i] = type;
                }
            }
        }
        this.c = serverNameArr;
    }

    void a(String str) throws UnsupportedEncodingException {
        if (str != null) {
            a(new ServerName[]{new ServerName(str)});
        }
    }

    public int hashCode() {
        int type = getType();
        ServerName[] serverNameArr = this.c;
        if (serverNameArr != null && serverNameArr.length > 0) {
            int i = 0;
            while (true) {
                ServerName[] serverNameArr2 = this.c;
                if (i >= serverNameArr2.length) {
                    break;
                }
                type += serverNameArr2[i].hashCode();
                i++;
            }
        }
        return type;
    }

    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj instanceof ServerNameList) {
            ServerName[] serverNameArr = ((ServerNameList) obj).c;
            ServerName[] serverNameArr2 = this.c;
            if (serverNameArr2 == null || serverNameArr == null) {
                if (serverNameArr2 == null && serverNameArr == null) {
                    return true;
                }
            } else if (serverNameArr2.length == serverNameArr.length) {
                int i = 0;
                while (true) {
                    ServerName[] serverNameArr3 = this.c;
                    if (i >= serverNameArr3.length) {
                        return true;
                    }
                    if (!serverNameArr3[i].equals(serverNameArr[i])) {
                        break;
                    }
                    i++;
                }
            }
        }
        return false;
    }

    @Override // iaik.security.ssl.Extension
    public Object clone() {
        ServerNameList serverNameList = (ServerNameList) super.clone();
        ServerName[] serverNameArr = this.c;
        if (serverNameArr != null) {
            serverNameList.c = new ServerName[serverNameArr.length];
            int i = 0;
            while (true) {
                ServerName[] serverNameArr2 = this.c;
                if (i >= serverNameArr2.length) {
                    break;
                }
                serverNameList.c[i] = (ServerName) serverNameArr2[i].clone();
                i++;
            }
        }
        return serverNameList;
    }

    @Override // iaik.security.ssl.Extension
    public String toString() {
        StringBuffer stringBuffer = new StringBuffer();
        ServerName[] serverNameArr = this.c;
        if (serverNameArr != null && serverNameArr.length > 0) {
            stringBuffer.append(" Server names: ");
            for (int i = 0; i < this.c.length; i++) {
                if (i > 0) {
                    stringBuffer.append(", ");
                }
                stringBuffer.append(this.c[i]);
            }
        } else {
            stringBuffer.append("empty");
        }
        return stringBuffer.toString();
    }

    @Override // iaik.security.ssl.Extension
    int a(ab abVar) throws IOException {
        boolean zIsTypeSupported;
        SecurityProvider securityProvider = SecurityProvider.getSecurityProvider();
        Vector vector = new Vector(5);
        int iF = abVar.f();
        if (iF > 0) {
            int iF2 = abVar.f();
            if (iF2 != iF - 2) {
                throw new SSLException("Invalid length of ServerNameList extension!", 2, 50, false);
            }
            zIsTypeSupported = true;
            while (iF2 > 0) {
                int iK = abVar.k();
                int i = iF2 - 1;
                byte[] bArrG = abVar.g();
                ServerName tLSServerName = securityProvider.getTLSServerName(iK, bArrG);
                if (zIsTypeSupported) {
                    zIsTypeSupported = tLSServerName.isTypeSupported();
                }
                vector.addElement(tLSServerName);
                iF2 = i - (bArrG.length + 2);
            }
            if (iF2 != 0) {
                throw new SSLException("ServerNameList size does not match to length field!", 2, 50, false);
            }
        } else {
            zIsTypeSupported = true;
        }
        ServerName[] serverNameArr = new ServerName[vector.size()];
        vector.copyInto(serverNameArr);
        try {
            a(serverNameArr);
            if (!zIsTypeSupported && !d()) {
                this.a = new SSLException("ServerNameList contains unsupported name type(s)!", c() == 0 ? 2 : 1, 112, false);
            }
            return iF + 2;
        } catch (IllegalArgumentException e) {
            throw new SSLException(e.getMessage(), 2, 47, false);
        }
    }

    @Override // iaik.security.ssl.Extension
    void a(v vVar) throws IOException {
        ServerName[] serverNameArr;
        int i = 0;
        if (!d() || (serverNameArr = this.c) == null || serverNameArr.length == 0) {
            vVar.a(0);
            return;
        }
        int size = vVar.size();
        vVar.write(v.a);
        vVar.write(v.a);
        while (true) {
            ServerName[] serverNameArr2 = this.c;
            if (i < serverNameArr2.length) {
                byte[] encodedName = serverNameArr2[i].getEncodedName();
                vVar.d(this.c[i].getType());
                vVar.a(encodedName);
                i++;
            } else {
                int size2 = (vVar.size() - size) - 2;
                vVar.b(size2, size);
                vVar.b(size2 - 2, size + 2);
                return;
            }
        }
    }

    @Override // iaik.security.ssl.Extension
    Extension a(Extension extension) throws SSLException {
        return d() ? this : extension;
    }
}
