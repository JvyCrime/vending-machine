package iaik.security.random;

import androidx.constraintlayout.core.motion.utils.TypedValues;
import com.bitmick.marshall.vmc.marshall_t;
import com.felhr.usbserial.FTDISerialDevice;
import java.io.IOException;
import java.io.InputStream;
import java.io.PrintStream;
import java.io.PrintWriter;
import java.util.Random;

/* JADX INFO: loaded from: classes.dex */
public class FIPS140Test {
    static int[] c;
    private static final int[] e = {0, 2267, 1079, TypedValues.Position.TYPE_DRAWPATH, 223, 90, 90};
    private static final int[] f = {0, 2733, 1421, 748, TypedValues.Cycle.TYPE_VISIBILITY, 223, 223};
    byte[] a;
    InputStream b;
    PrintWriter d;

    public FIPS140Test(InputStream inputStream) {
        this.b = inputStream;
        this.a = new byte[FTDISerialDevice.FTDI_BAUDRATE_1200];
    }

    public FIPS140Test(Random random) {
        this(new RandomInputStream(random));
    }

    private int a(int i) {
        return ((this.a[i >> 3] & 255) >>> (7 - (i & 7))) & 1;
    }

    private static void a() {
        if (c != null) {
            return;
        }
        c = new int[256];
        for (int i = 0; i < 256; i++) {
            int i2 = 0;
            for (int i3 = i; i3 != 0; i3 >>= 1) {
                if ((i3 & 1) != 0) {
                    i2++;
                }
            }
            c[i] = i2;
        }
    }

    private void a(Object obj) {
        PrintWriter printWriter = this.d;
        if (printWriter != null) {
            StringBuffer stringBuffer = new StringBuffer();
            stringBuffer.append("FIPS140Test: ");
            stringBuffer.append(obj);
            printWriter.println(stringBuffer.toString());
            this.d.flush();
        }
    }

    private boolean a(int i, int i2, int i3) {
        return i > i2 && i < i3;
    }

    public void initTests() throws RandomException {
        try {
            if (this.b.read(this.a) == this.a.length) {
            } else {
                throw new IOException();
            }
        } catch (IOException unused) {
            throw new RandomException("Error reading random data!");
        }
    }

    public boolean longRunsTest() {
        a("running long runs test");
        int iA = a(0);
        int i = 0;
        int i2 = 0;
        for (int i3 = 0; i3 < 20000; i3++) {
            if (iA == a(i3)) {
                i2++;
            } else {
                if (i2 >= 34) {
                    StringBuffer stringBuffer = new StringBuffer();
                    stringBuffer.append("long run of length ");
                    stringBuffer.append(i2);
                    stringBuffer.append(" detected");
                    a(stringBuffer.toString());
                    a("long runs test FAILED");
                    return false;
                }
                if (i2 > i) {
                    i = i2;
                }
                iA ^= 1;
                i2 = 0;
            }
        }
        StringBuffer stringBuffer2 = new StringBuffer();
        stringBuffer2.append("longest run: ");
        stringBuffer2.append(i);
        a(stringBuffer2.toString());
        a("long runs test passed");
        return true;
    }

    public boolean monoBitTest() {
        a("running monobit test...");
        a();
        int i = 0;
        for (int i2 = 0; i2 < 2500; i2++) {
            i += c[this.a[i2] & 255];
        }
        StringBuffer stringBuffer = new StringBuffer();
        stringBuffer.append("number of one bits: 9654 < ");
        stringBuffer.append(i);
        stringBuffer.append(" < ");
        stringBuffer.append(10346);
        a(stringBuffer.toString());
        if (a(i, 9654, 10346)) {
            a("monobit test passed");
            return true;
        }
        a("monobit test FAILED!");
        return false;
    }

    public boolean pokerTest() {
        a("running poker test");
        int[] iArr = new int[16];
        boolean z = false;
        for (int i = 0; i < 2500; i++) {
            byte[] bArr = this.a;
            int i2 = (bArr[i] & marshall_t.marshall_packet_option_rfu_mask) >> 4;
            iArr[i2] = iArr[i2] + 1;
            int i3 = bArr[i] & 15;
            iArr[i3] = iArr[i3] + 1;
        }
        long j = 0;
        for (int i4 = 0; i4 <= 15; i4++) {
            j += (long) (iArr[i4] * iArr[i4]);
        }
        float f2 = (j * 0.0032f) - 5000.0f;
        StringBuffer stringBuffer = new StringBuffer();
        stringBuffer.append("poker test result: 1.03 < ");
        stringBuffer.append(f2);
        stringBuffer.append(" < ");
        stringBuffer.append(57.4f);
        a(stringBuffer.toString());
        if (1.03f < f2 && f2 < 57.4f) {
            z = true;
        }
        StringBuffer stringBuffer2 = new StringBuffer();
        stringBuffer2.append("poker test ");
        stringBuffer2.append(z ? "passed" : "FAILED!");
        a(stringBuffer2.toString());
        return z;
    }

    public boolean runTests() {
        return startTests();
    }

    public boolean runsTest() {
        a("running runs test");
        int[] iArr = new int[7];
        int[] iArr2 = new int[7];
        int[][] iArr3 = {iArr, iArr2};
        int iA = a(0);
        int i = 0;
        for (int i2 = 0; i2 < 20000; i2++) {
            if (iA == a(i2)) {
                i++;
            } else {
                if (i > 6) {
                    i = 6;
                }
                int[] iArr4 = iArr3[iA];
                iArr4[i] = iArr4[i] + 1;
                iA ^= 1;
                i = 0;
            }
        }
        for (int i3 = 1; i3 <= 6; i3++) {
            StringBuffer stringBuffer = new StringBuffer();
            stringBuffer.append("blocks/gaps of length ");
            stringBuffer.append(i3);
            stringBuffer.append(": ");
            stringBuffer.append(iArr2[i3]);
            stringBuffer.append("/");
            stringBuffer.append(iArr[i3]);
            a(stringBuffer.toString());
        }
        for (int i4 = 1; i4 <= 6; i4++) {
            int i5 = iArr[i4] + iArr2[i4];
            int[] iArr5 = e;
            int i6 = iArr5[i4];
            int[] iArr6 = f;
            if (!a(i5, i6, iArr6[i4])) {
                StringBuffer stringBuffer2 = new StringBuffer();
                stringBuffer2.append("runs of length ");
                stringBuffer2.append(i4);
                stringBuffer2.append(" failed test: not ");
                stringBuffer2.append(iArr5[i4]);
                stringBuffer2.append(" < ");
                stringBuffer2.append(iArr[i4] + iArr2[i4]);
                stringBuffer2.append(" < ");
                stringBuffer2.append(iArr6[i4]);
                a(stringBuffer2.toString());
                a("runs test FAILED");
                return false;
            }
        }
        a("runs test passed");
        return true;
    }

    public void setDebugStream(PrintStream printStream) {
        if (printStream == null) {
            this.d = null;
        } else {
            this.d = new PrintWriter(printStream);
        }
    }

    public void setDebugStream(PrintWriter printWriter) {
        this.d = printWriter;
    }

    public boolean startTests() {
        return startTests(false);
    }

    public boolean startTests(boolean z) {
        a("initializing...");
        try {
            initTests();
            boolean zMonoBitTest = monoBitTest() & true;
            if (!z && !zMonoBitTest) {
                return false;
            }
            boolean zPokerTest = zMonoBitTest & pokerTest();
            if (!z && !zPokerTest) {
                return false;
            }
            boolean zRunsTest = zPokerTest & runsTest();
            if (!z && !zRunsTest) {
                return false;
            }
            boolean zLongRunsTest = zRunsTest & longRunsTest();
            if (!z && !zLongRunsTest) {
                return false;
            }
            if (zLongRunsTest) {
                a("all tests passed");
            }
            return zLongRunsTest;
        } catch (RandomException e2) {
            a(e2.getMessage());
            return false;
        }
    }
}
