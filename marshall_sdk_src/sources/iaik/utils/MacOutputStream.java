package iaik.utils;

import java.io.FilterOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.util.Objects;
import javax.crypto.Mac;

/* JADX INFO: loaded from: classes2.dex */
public class MacOutputStream extends FilterOutputStream {
    protected Mac macEngine_;
    protected boolean macingActive_;

    public MacOutputStream(OutputStream outputStream, Mac mac) {
        super(outputStream);
        Objects.requireNonNull(outputStream, "Argument \"stream\" must not be null.");
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

    @Override // java.io.FilterOutputStream, java.io.OutputStream
    public void write(int i) throws IOException {
        if (this.macingActive_) {
            this.macEngine_.update((byte) i);
        }
        this.out.write(i);
    }

    @Override // java.io.FilterOutputStream, java.io.OutputStream
    public void write(byte[] bArr, int i, int i2) throws IOException {
        if (this.macingActive_) {
            this.macEngine_.update(bArr, i, i2);
        }
        this.out.write(bArr, i, i2);
    }
}
