package iaik.asn1;

import java.io.ByteArrayOutputStream;

/* JADX INFO: loaded from: classes.dex */
class c extends ByteArrayOutputStream {
    public c(int i) {
        super(i);
    }

    private byte[] a(byte[] bArr) {
        int length = bArr.length - 1;
        for (int i = 0; i < length; i++) {
            byte b = bArr[i];
            bArr[i] = bArr[length];
            bArr[length] = b;
            length--;
        }
        return bArr;
    }

    @Override // java.io.ByteArrayOutputStream
    public synchronized byte[] toByteArray() {
        return a(super.toByteArray());
    }

    @Override // java.io.ByteArrayOutputStream, java.io.OutputStream
    public void write(int i) {
        super.write(i);
    }

    @Override // java.io.OutputStream
    public void write(byte[] bArr) {
        byte[] bArr2 = (byte[]) bArr.clone();
        super.write(a(bArr2), 0, bArr2.length);
    }

    @Override // java.io.ByteArrayOutputStream, java.io.OutputStream
    public void write(byte[] bArr, int i, int i2) {
        byte[] bArr2 = new byte[i2];
        System.arraycopy(bArr, i, bArr2, 0, i2);
        super.write(a(bArr2), 0, i2);
    }
}
