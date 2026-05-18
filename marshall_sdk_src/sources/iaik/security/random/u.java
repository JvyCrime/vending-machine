package iaik.security.random;

import iaik.security.provider.IAIK;
import iaik.utils.Util;
import java.util.Random;

/* JADX INFO: loaded from: classes.dex */
abstract class u extends v {
    private static final Random b = new Random();
    private volatile boolean a;
    long f;
    final int g;
    final int h;
    final int i;
    final int j;
    final int k;
    final long l;
    final int m;
    final int n;
    final int o;
    boolean p;
    final boolean q;

    /* JADX WARN: Removed duplicated region for block: B:4:0x000d A[PHI: r0
  0x000d: PHI (r0v4 int) = (r0v2 int), (r0v3 int) binds: [B:3:0x000b, B:13:0x0021] A[DONT_GENERATE, DONT_INLINE]] */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct add '--show-bad-code' argument
    */
    u(int r4, int r5, int r6, int r7, int r8, int r9, int r10, int r11, long r12, boolean r14) {
        /*
            r3 = this;
            r0 = 1
            r3.<init>(r0)
            r0 = 0
            r3.a = r0
            r3.p = r0
            r0 = 14
            if (r4 >= r0) goto L10
        Ld:
            r3.h = r0
            goto L2d
        L10:
            r1 = 32
            if (r4 > r1) goto L40
            r2 = 16
            if (r4 <= r0) goto L1d
            if (r4 >= r2) goto L1d
            r3.h = r2
            goto L2d
        L1d:
            r0 = 24
            if (r4 <= r2) goto L24
            if (r4 >= r0) goto L24
            goto Ld
        L24:
            if (r4 <= r0) goto L2b
            if (r4 >= r1) goto L2b
            r3.h = r1
            goto L2d
        L2b:
            r3.h = r4
        L2d:
            r3.q = r14
            r3.g = r5
            r3.o = r6
            r3.i = r11
            r3.j = r7
            r3.k = r8
            r3.l = r12
            r3.m = r9
            r3.n = r10
            return
        L40:
            java.lang.IllegalArgumentException r4 = new java.lang.IllegalArgumentException
            java.lang.String r5 = "Invalid security strength!"
            r4.<init>(r5)
            throw r4
        */
        throw new UnsupportedOperationException("Method not decompiled: iaik.security.random.u.<init>(int, int, int, int, int, int, int, int, long, boolean):void");
    }

    static int a(byte[] bArr, byte[] bArr2, int i) {
        if (bArr == null || bArr.length <= 0) {
            return i;
        }
        System.arraycopy(bArr, 0, bArr2, i, bArr.length);
        return i + bArr.length;
    }

    private void b(byte[] bArr) {
        if (bArr != null && bArr.length > this.n) {
            throw new IllegalArgumentException("Illegal number of seed bytes!");
        }
        a(a(this.j, this.k), bArr);
    }

    private byte[] b(int i) {
        if (i > this.i) {
            throw new IllegalArgumentException("Too many bits requested!");
        }
        this.p = false;
        byte[] bArrA = a(i);
        if (!this.p) {
            return bArrA;
        }
        b((byte[]) null);
        this.p = false;
        return a(i);
    }

    abstract void a();

    void a(byte[] bArr) throws IllegalArgumentException {
        byte[] bArrA;
        byte[] bArrA2;
        if (bArr != null && bArr.length > this.m) {
            throw new IllegalArgumentException("Invalid personalization string length!");
        }
        if (this.q) {
            int i = this.h;
            int i2 = i >> 1;
            bArrA = a(i + i2, this.k);
            bArrA2 = a(i2, this.k >> 1);
        } else {
            bArrA = a(this.j, this.k);
            bArrA2 = null;
        }
        a(bArrA, bArrA2, bArr);
    }

    abstract void a(byte[] bArr, byte[] bArr2);

    abstract void a(byte[] bArr, byte[] bArr2, byte[] bArr3);

    abstract byte[] a(int i);

    byte[] a(int i, int i2) {
        int i3 = i2 - i;
        return new JDKSeedGenerator(((i3 > 0 ? Math.abs(b.nextInt() % i3) : 0) + i) << 3).getSeed();
    }

    @Override // iaik.security.random.v
    void b() {
        a();
    }

    byte[] c() {
        byte[] bytes;
        byte[] bytes2;
        byte[] bytes3;
        byte[] bytes4;
        byte[] bytes5;
        byte[] bytes6;
        byte[] bytes7;
        byte[] bytes8;
        byte[] bytes9;
        byte[] byteArray = Util.toByteArray(System.currentTimeMillis());
        int length = byteArray.length;
        byte[] bytes10 = null;
        if (length < this.m) {
            try {
                bytes = IAIK.getVersionInfo().getBytes();
                try {
                    length += bytes.length;
                } catch (Exception unused) {
                }
            } catch (Exception unused2) {
                bytes = null;
            }
        } else {
            bytes = null;
        }
        if (length < this.m) {
            try {
                bytes2 = System.getProperty("java.version").getBytes();
                try {
                    length += bytes2.length;
                } catch (Exception unused3) {
                }
            } catch (Exception unused4) {
                bytes2 = null;
            }
        } else {
            bytes2 = null;
        }
        if (length < this.m) {
            try {
                bytes3 = System.getProperty("os.arch").getBytes();
                try {
                    length += bytes3.length;
                } catch (Exception unused5) {
                }
            } catch (Exception unused6) {
                bytes3 = null;
            }
        } else {
            bytes3 = null;
        }
        if (length < this.m) {
            try {
                bytes4 = System.getProperty("os.name").getBytes();
                try {
                    length += bytes4.length;
                } catch (Exception unused7) {
                }
            } catch (Exception unused8) {
                bytes4 = null;
            }
        } else {
            bytes4 = null;
        }
        if (length < this.m) {
            try {
                bytes5 = System.getProperty("os.version").getBytes();
                try {
                    length += bytes5.length;
                } catch (Exception unused9) {
                }
            } catch (Exception unused10) {
                bytes5 = null;
            }
        } else {
            bytes5 = null;
        }
        if (length < this.m) {
            try {
                bytes6 = System.getProperty("sun.os.patch.level").getBytes();
                try {
                    length += bytes6.length;
                } catch (Exception unused11) {
                }
            } catch (Exception unused12) {
                bytes6 = null;
            }
        } else {
            bytes6 = null;
        }
        if (length < this.m) {
            try {
                bytes7 = System.getProperty("user.name").getBytes();
                try {
                    length += bytes7.length;
                } catch (Exception unused13) {
                }
            } catch (Exception unused14) {
                bytes7 = null;
            }
        } else {
            bytes7 = null;
        }
        if (length < this.m) {
            try {
                bytes8 = System.getProperty("user.timezone").getBytes();
                try {
                    length += bytes8.length;
                } catch (Exception unused15) {
                }
            } catch (Exception unused16) {
                bytes8 = null;
            }
        } else {
            bytes8 = null;
        }
        if (length < this.m) {
            try {
                bytes9 = System.getProperty("user.language").getBytes();
                try {
                    length += bytes9.length;
                } catch (Exception unused17) {
                }
            } catch (Exception unused18) {
                bytes9 = null;
            }
        } else {
            bytes9 = null;
        }
        if (length < this.m) {
            try {
                bytes10 = System.getProperty("user.country").getBytes();
                length += bytes10.length;
            } catch (Exception unused19) {
            }
        }
        byte[] bArr = new byte[length];
        a(bytes10, bArr, a(bytes9, bArr, a(bytes8, bArr, a(bytes7, bArr, a(bytes6, bArr, a(bytes5, bArr, a(bytes4, bArr, a(bytes3, bArr, a(bytes2, bArr, a(bytes, bArr, a(byteArray, bArr, 0)))))))))));
        int i = this.m;
        return length > i ? Util.resizeArray(bArr, i) : bArr;
    }

    @Override // iaik.security.random.v
    protected void engineNextBytes(byte[] bArr) {
        if (bArr == null) {
            throw new IllegalArgumentException();
        }
        if (!this.a) {
            a(c());
            this.a = true;
        }
        int length = bArr.length;
        System.arraycopy(b(length), 0, bArr, 0, length);
    }

    @Override // iaik.security.random.v
    protected void engineSetSeed(byte[] bArr) {
        if (!this.a) {
            a(c());
            this.a = true;
        }
        b(bArr);
    }

    public int getSecurityStrength() {
        return this.h;
    }
}
