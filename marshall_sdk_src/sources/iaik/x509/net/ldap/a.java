package iaik.x509.net.ldap;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Objects;
import javax.naming.NamingEnumeration;
import javax.naming.SizeLimitExceededException;
import javax.naming.directory.Attribute;
import javax.naming.directory.Attributes;
import javax.naming.directory.SearchResult;

/* JADX INFO: loaded from: classes2.dex */
class a extends InputStream {
    byte[] a;
    ByteArrayOutputStream b;
    int c;
    int d;
    LdapURLConnection e;
    NamingEnumeration f;
    private boolean g;

    a(LdapURLConnection ldapURLConnection, NamingEnumeration namingEnumeration) {
        Objects.requireNonNull(namingEnumeration, "Query NamingEnumeration must not be null!");
        this.e = ldapURLConnection;
        this.f = namingEnumeration;
        this.g = false;
        this.b = new ByteArrayOutputStream(2048);
    }

    private int a() throws IOException {
        if (this.g) {
            return -1;
        }
        this.b.reset();
        if (this.f != null) {
            while (this.b.size() == 0 && this.f.hasMore()) {
                try {
                    Attributes attributes = ((SearchResult) this.f.nextElement()).getAttributes();
                    if (attributes != null) {
                        NamingEnumeration all = attributes.getAll();
                        while (all.hasMoreElements()) {
                            NamingEnumeration all2 = ((Attribute) all.nextElement()).getAll();
                            while (all2.hasMoreElements()) {
                                Object objNextElement = all2.nextElement();
                                if (objNextElement instanceof byte[]) {
                                    this.b.write((byte[]) objNextElement);
                                }
                            }
                        }
                    }
                } catch (IOException e) {
                    throw e;
                } catch (SizeLimitExceededException unused) {
                } catch (Throwable th) {
                    close();
                    final String string = th.toString();
                    throw new IOException(this, string, th) { // from class: iaik.x509.net.ldap.LdapInputStream$1
                        private static final long serialVersionUID = 7305486914017622983L;
                        private final Throwable a;
                        private final a b;

                        {
                            this.b = this;
                            this.a = th;
                        }

                        @Override // java.lang.Throwable
                        public Throwable getCause() {
                            return this.a;
                        }
                    };
                }
            }
        }
        byte[] byteArray = this.b.toByteArray();
        this.a = byteArray;
        if (byteArray.length == 0) {
            this.g = true;
            return -1;
        }
        int length = byteArray.length;
        this.d = length;
        this.c = 0;
        return length;
    }

    @Override // java.io.InputStream
    public int available() throws IOException {
        if (this.d == 0) {
            a();
        }
        return this.d;
    }

    @Override // java.io.InputStream, java.io.Closeable, java.lang.AutoCloseable
    public void close() throws IOException {
        LdapURLConnection ldapURLConnection = this.e;
        if (ldapURLConnection != null) {
            ldapURLConnection.disconnect();
        }
    }

    @Override // java.io.InputStream
    public int read() throws IOException {
        do {
            int i = this.d;
            if (i > 0) {
                this.d = i - 1;
                byte[] bArr = this.a;
                int i2 = this.c;
                this.c = i2 + 1;
                return bArr[i2] & 255;
            }
        } while (a() != -1);
        return -1;
    }

    @Override // java.io.InputStream
    public int read(byte[] bArr, int i, int i2) throws IOException {
        int i3 = 0;
        do {
            int i4 = this.d;
            if (i4 >= i2) {
                System.arraycopy(this.a, this.c, bArr, i + i3, i2);
                this.d -= i2;
                this.c += i2;
                return i3 + i2;
            }
            if (i4 > 0) {
                System.arraycopy(this.a, this.c, bArr, i + i3, i4);
                int i5 = this.d;
                i2 -= i5;
                i3 += i5;
                this.d = 0;
            }
        } while (a() != -1);
        if (i3 == 0) {
            return -1;
        }
        return i3;
    }

    @Override // java.io.InputStream
    public long skip(long j) throws IOException {
        return read(new byte[(int) j]);
    }
}
