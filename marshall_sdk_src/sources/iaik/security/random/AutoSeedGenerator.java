package iaik.security.random;

import java.io.ByteArrayInputStream;
import java.io.PrintStream;
import org.ksoap2.transport.ServiceConnection;

/* JADX INFO: loaded from: classes.dex */
public class AutoSeedGenerator extends VarLengthSeedGenerator {
    private static int h = 8;
    private byte[] g;

    public AutoSeedGenerator() {
        this(256);
    }

    public AutoSeedGenerator(int i) {
        super(i);
    }

    static void a(int i) {
        System.exit(i);
    }

    private static byte[] a(SeedGenerator seedGenerator) {
        PrintStream printStream = System.out;
        StringBuffer stringBuffer = new StringBuffer();
        stringBuffer.append("Testing ");
        stringBuffer.append(seedGenerator.getClass().getName());
        stringBuffer.append("...");
        printStream.println(stringBuffer.toString());
        System.out.println("calling getseed...");
        long jCurrentTimeMillis = System.currentTimeMillis();
        byte[] seed = seedGenerator.getSeed();
        long jCurrentTimeMillis2 = System.currentTimeMillis();
        PrintStream printStream2 = System.out;
        StringBuffer stringBuffer2 = new StringBuffer();
        stringBuffer2.append("seed.length ");
        stringBuffer2.append(seed.length);
        printStream2.println(stringBuffer2.toString());
        int i = seedGenerator.getStatus()[1];
        float f = jCurrentTimeMillis2 - jCurrentTimeMillis;
        PrintStream printStream3 = System.out;
        StringBuffer stringBuffer3 = new StringBuffer();
        stringBuffer3.append("it took ");
        stringBuffer3.append(f / 1000.0f);
        stringBuffer3.append(" seconds, ");
        stringBuffer3.append((i / f) * 1000.0f);
        stringBuffer3.append(" bits per second");
        printStream3.println(stringBuffer3.toString());
        return seed;
    }

    private static synchronized int b() {
        int i;
        int i2 = 0;
        int i3 = 0;
        for (int i4 = 0; i4 < 16; i4++) {
            int i5 = 0;
            while (System.currentTimeMillis() <= System.currentTimeMillis() + ((long) h)) {
                i5++;
            }
            i3 <<= 1;
            if ((i5 & 1) != 0) {
                i3++;
            }
            i2 += i5;
            try {
                Thread.sleep(i5 % 5);
            } catch (InterruptedException unused) {
            }
        }
        int i6 = i2 / 16;
        if (i6 < 4096) {
            h <<= 1;
            return b();
        }
        if (i6 > 10240 && (i = h) >= 4) {
            h = i >>> 1;
        }
        return i3;
    }

    private static void c() {
        long jCurrentTimeMillis = System.currentTimeMillis();
        while (true) {
            long jCurrentTimeMillis2 = System.currentTimeMillis();
            if (jCurrentTimeMillis != jCurrentTimeMillis2) {
                PrintStream printStream = System.out;
                StringBuffer stringBuffer = new StringBuffer();
                stringBuffer.append(jCurrentTimeMillis2 - jCurrentTimeMillis);
                stringBuffer.append("  ");
                printStream.print(stringBuffer.toString());
                jCurrentTimeMillis = jCurrentTimeMillis2;
            }
        }
    }

    private static void d() {
        System.out.println("Configuration: MIN_AVERAGE: 4096, MAX_AVERAGE: 10240, SLEEP_MODULUS: 5");
    }

    private static void e() {
        byte[] bArrA = a(new AutoSeedGenerator(ServiceConnection.DEFAULT_TIMEOUT));
        d();
        FIPS140Test fIPS140Test = new FIPS140Test(new ByteArrayInputStream(bArrA));
        fIPS140Test.setDebugStream(System.out);
        PrintStream printStream = System.out;
        StringBuffer stringBuffer = new StringBuffer();
        stringBuffer.append("result: ");
        stringBuffer.append(fIPS140Test.startTests(true));
        printStream.println(stringBuffer.toString());
    }

    public static void main(String[] strArr) {
        if (strArr.length == 0) {
            e();
        } else if (strArr[0].equalsIgnoreCase("clock")) {
            c();
        } else {
            a(new AutoSeedGenerator(160));
            a(new AutoSeedGenerator(160));
            a(new AutoSeedGenerator(160));
            a(new JDKSeedGenerator(160));
            a(new JDKSeedGenerator(160));
            a(new JDKSeedGenerator(160));
        }
        a(0);
    }

    @Override // iaik.security.random.SeedGenerator
    public byte[] getSeed() {
        if (this.g == null || hasSeedLengthChanged()) {
            b();
            this.g = new byte[this.e];
            for (int i = 0; i < this.e; i += 2) {
                int iB = b();
                byte[] bArr = this.g;
                bArr[i] = (byte) (iB >>> 8);
                bArr[i + 1] = (byte) iB;
            }
        }
        return (byte[]) this.g.clone();
    }

    @Override // iaik.security.random.SeedGenerator
    public int[] getStatus() {
        return new int[]{this.d, this.d};
    }

    @Override // iaik.security.random.VarLengthSeedGenerator
    public void reinit(int i) {
        this.d = i;
        this.e = ((i - 1) >> 3) + 1;
        if ((this.e & 1) != 0) {
            this.e++;
        }
    }
}
