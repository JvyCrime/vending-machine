package iaik.security.jsse.net;

/* JADX INFO: loaded from: classes.dex */
class b {
    private byte[] a;

    public b(byte[] bArr) {
        this.a = bArr;
    }

    public byte[] a() {
        return this.a;
    }

    public int hashCode() {
        byte[] bArr = this.a;
        return bArr[0] + bArr[1] + bArr[bArr.length - 1] + bArr[bArr.length - 2];
    }

    public boolean equals(Object obj) {
        if (!(obj instanceof b)) {
            return false;
        }
        byte[] bArr = ((b) obj).a;
        if (bArr.length != this.a.length) {
            return false;
        }
        boolean z = true;
        for (int i = 0; i < bArr.length; i++) {
            z &= bArr[i] == this.a[i];
        }
        return z;
    }
}
