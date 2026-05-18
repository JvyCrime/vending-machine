package iaik.utils;

import java.io.FilterInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Objects;
import javax.crypto.Mac;

/* JADX INFO: loaded from: classes2.dex */
public class MacInputStream extends FilterInputStream {
    protected Mac macEngine_;
    protected boolean macingActive_;

    public MacInputStream(InputStream inputStream, Mac mac) {
        super(inputStream);
        Objects.requireNonNull(inputStream, "Argument \"stream\" must not be null.");
        Objects.requireNonNull(mac, "Argument \"macEngine\" must not be null.");
        this.macingActive_ = true;
        setMac(mac);
    }

    public Mac getMac() {
        return this.macEngine_;
    }

    public void on(boolean z) {
        this.macingActive_ = z;
    }

    @Override // java.io.FilterInputStream, java.io.InputStream
    public int read() throws IOException {
        int i = this.in.read();
        if (this.macingActive_ && i != -1) {
            this.macEngine_.update((byte) i);
        }
        return i;
    }

    @Override // java.io.FilterInputStream, java.io.InputStream
    public int read(byte[] bArr, int i, int i2) throws IOException {
        int i3 = this.in.read(bArr, i, i2);
        if (this.macingActive_ && i3 != -1) {
            this.macEngine_.update(bArr, i, i3);
        }
        return i3;
    }

    public void setMac(Mac mac) {
        Objects.requireNonNull(mac, "Argument \"macEngine\" must not be null.");
        this.macEngine_ = mac;
    }

    public String toString() {
        StringBuffer stringBuffer = new StringBuffer();
        stringBuffer.append("MAC Input Stream (");
        stringBuffer.append(this.macingActive_ ? "MACing active" : "MACing inactive");
        stringBuffer.append(") using ");
        stringBuffer.append(this.macEngine_.toString());
        return stringBuffer.toString();
    }
}
