package iaik.security.cipher;

import iaik.utils.CriticalObject;
import iaik.utils.Util;
import java.security.InvalidKeyException;

/* JADX INFO: renamed from: iaik.security.cipher.l, reason: case insensitive filesystem */
/* JADX INFO: loaded from: classes.dex */
final class C0031l extends AbstractC0030k {
    private static final int[] p = Util.decodeIntArray("JD9qiIWjCNMTGYouA3BzRKQJOCIpnzHQCC76mOxObIlFKCHmONATd75UZs806QxswKwpt8l8UN0/hNW1tUcJF5IW1dmJefsb");
    private static final int[] q = Util.decodeIntArray("0TELppjftawv/XLb0Brft7jhr+1qJn6WunyQRfEsf5kkoZlHs5Fs9wgB8uKFjvwWY2kg2HFXTmmkWP6j9JM9fg2VdI9yjrZYcYvNWIIVSu57VKQdwlpZtZww1Tkq8mATxdGwIyhghfDKQXkYuNs474553LBgOhgObJ4Oi7Aeij7XFXfBvTFLJ3ivL9pVYFxg5lUl86pVq5RXSJhiY+gUQFXKOWoqqxC2tMxcNBFB6M6hVIavfHLpk7PuFBFjb7wqK6nFXXQYMfbOXD4Wm4eTHq/WujNsJM9cejJTgSiVhnc7j0iYa0u5r8S/6BtmKCGTYdgJzPshqZFIfKxgXeyAMu+EXV3phXWx3CYjAutlG4gjiT6B05asxQ9tb/OD9EI5LgtEgqSEIARpyPBKnh+bXiHGaEL26WyaZwycYavTiPBqUaDS2FQvaJYPpyirUTOjbu8LbBN6O+S6O/BQfvsqmKHxZR05rwF2ZspZPoJDDoiM7oYZRW+ftH2EpcM7i16+4G912IXBIHNAGkSfVsFqpk7TqmI2P3cGG/7fckKbAj030Nck0AoSSNsP6tNJ8cCbB1NyyYCZG3sl1HnY9uje9+P+UBq2eUw7l2zgvQTABrrBqU+2QJ9gxF5cnsIZaiRjaPtvrz5sU7UTObLrO1Lsb238UR+bMJUszIFFRK9evQm+49AE3jNK/WYPKAcZLkuzwMuoV0XIdA/SC185udP721V5wL0aYDIK1qEAxkAscnlnnyX++x+jzI6l6fjbMiL4PHUW3/1haxUvUB7IrQVSqzI9tfr9I4dgUzF7SD4A34KeXFe7ym+MoBqHVi7fF2nb1UKo9ih+/8OsZzLGjE9Vc2lbJ7C7yljI4f+jXbjwEaAQ+j2Y/SGDuEr8tWwt0dNbmlPkebb4RWXSjkm8S/uXkOHd8tqky34zYvsTQc7kxujvIMraNndMAdB+nv4r8R+0ldvaTa6QkZjqrY5xa5PVoNCO0dCvxyXgjjxbL451lLeP9uL78hIrZIiIuBKQDfAcT61eoGiPwxzRz/GRs6jBrS8vIhi+Dhd36nUt/osCH6HloMwPtW906Bis89bOieKZtKhP4P0T4Ld8xDuB0q2o2RZfomaAlXcFk8xzFCEaFHfmrSBld7X6hsdUQvX7nTXP682vDHs+iaDWQRvTrh5+SQAlDi0gcbNeImgAu1e44K8kZDab8Am5HlVjkR1Z36aqeMFDidlaU38gfVuiAuW5xYMmA3Zilc+pEcgZaE5zSkGzRy3KexSpShtRAFKaUykV1g9XP7ybxuQrYKR2geZ0AAi6b7VXG+kf8pbsayoN2RW2Y2Uh57n5tv80BS7FhVZkU7AtXamfj6EIukeZboUHag==");
    private static final int[] r = Util.decodeIntArray("S3pw6bWzKUTbdQkuxBkmI61uprBJp999nO5guI/tsmbsqoxxaZoX/1ZkUmzCsZ7hGTYCpXUJTCmgWRNA5Bg6Pj9UmJpbQp1la4/k1pn3P9ah0pwH7+gw9U0tOObwJV3BTN0ghoRw6yZjgunGAh7MXgloaz8+uu/JPJcYFGtqcKFofzWEUqDihrecUwWqUAc3PgeEHH/erlyOfUTsVxbyuLA62jfwUAwN8BwfBAIAs/+uDPUaPLV0siWDeljcCSG90ZET+XypL/aUMkdzIvVHATrl5YE3wtrcyLV2NJrz3aepRGFGD9ADDuzIxz6kdR5B4jjNmTvqDi8ygLuhGD6zMU5UizhPbbkIb0INA/YKBL8suBKQJJd8eVZ5sHK8r4mv3pp3H9mTCBCzi64S3M8/LlUSch8ua3EkUBrd5p+EzYd6WEcYdAjaF7yfmrzpS32M7HrsOtuFHfpjCUNmxGTD0u8cGEcyFdkI3UM7NyTCuhYSoU1DKmXEUVCUAAITOuTdcd/4nhAxTlWBrHfWXxEZmwQ1VvHXo8drPBEYO1kkpQnyj+btl/H7+p66vyweFTxuhuNFcOrpb7GGDl4KWj4qs3cf5xxOPQb6KWXcuZnnHQ+APonWUmbIJS5MyXicELNqxhUOupTi6nil/DxTHgot9PL3Tqc2HSs9GTkmDxnCeWBSI6cI9xMStuut/m7qwx9m47xFlaZ7yIOxfzfRAYz/KMMy3e++bFqlZVghhWirmALuzqUP2y+VOyrvfa1bbi+EFSG2KCkHYXDs3Ud1YZ8VEBPMqDDrYb2WAzT+HqoDY8+1c1yQTHCiOdWengvLqt4U7syGvGBiLKecq1yrsvOEbmSLHq8ZvfDKoCNpuWVau1BAaFoyPCq0szGe6dXAIbj3m1QLGYdfoJmV95l+Yj19qPg3iJqX4y13Ee2TXxZoEoEONYgpx+Yf1pbe36F4WLqZV/WEpRsicmObg8P/GsJGls2zCutTLjBUj9lI5G28MShY6/LvNMb/6v4o7WHufDxzXUoU2ehkt+NCEF0UID4T4EXu4rajqqvq22xPFfrLT9DHQvRC72q7tWVPOx1BzSEF2B55noaFTcfkS0dqPYFiUM9iofJbjSZG/IiDoMHHtqN/FSTDact0kkeEigtWkrKFCVu/AK0ZSJ0UYrF0I4IOAFhCjSoMVfXqHa30PiM/cGEzcvCSjZN+QdZf7PFsIjvbfN43WcvudGBAhfKnzncybqYHgIQZ+FCe6O/YVWHZlzWpaaeqxQwGwloEq/yAC8rcnkR6LsNFNIT91WcFDh6eydtz29MQVYjNZ1/aeeNnQ0DFxDRlcT442D0o+J7xbf8gFT4h54+wPUrm458r24Ot9w==");
    private static final int[] s = Util.decodeIntArray("6T1aaJSBQPf2TCYclGkpNEEVIPd2AtT3vPRrLtSiAGjUCCRxMyD0akO31LdQAGGvHjn2LpckRUYUIU90v4uIQE2V/B2WtZGvcPTd02agL0W/vAnsA72XhX+sbdAxy4UElusns1X9OUHaJUfmq8oKmihQeCVTBCn0CiyG2um2bfto3BRi10hpAGgOwKQnoY3uTz/+ouiHrYy1jOAGevTWtqrOHnzTN1/sznijmUBrKkIg/p412fOFue4516s7Ek6LHcn690ttGFYmo2Yx6uOXsjpu+nTdW0MyaEHn98p4IPv7CvVO2P6zl0VAVqy6SJUnVVM6OiCDjYf+a6m30JaVS1WoZ7yhFZpYzKkpY5nh2zOmKkpWPzEl+V70fhyQKTF8/fjoAgQnL3CAuxVcBSgs45XBFUjkxm0iSMETP8cPhtwH+cnuQQQfD0BHeaRdiG4XMl9R69WbwNHyvMGPQRE1ZCV7eDRgKpxg3/joox9jbBsOErTCAuEynq9mT9HK0YEVayOV4DM+kuE7JAti7r65IoWyog7mug2Z3nIMjC2i9yjQEnhFlbeU/WR9CGLnzPXwVEmjb4d9SPrDnf0n8z6NHgpHY0GZLv90Om9uq/T4/TeoEtxgoevd+Jkb4UzbbmsNxntVEG1nLDcnZdQ73NDoBPEpDcfMAP+jtTkPkmkP7Qtme5/7ztt9nKCRzwvZFV6juxMviFFbrSR7lHm/djvW6zc5LrPMEVl5gCbil/QuMS1oQq2nxmorOxJ1TMx4LvEcahJCN7eSUecGobvmS/tjUBprEBgRyu36PSW92OLhw8lEQhZZChIThtkM7G7Vq+oqZK9nTtqGqF++v+mIZOTD/p28gFfw98CGYHh7+GADYE3R/YNG9jgfsHdFrgTXNvzMg0JrM/Aeq3GwgEGHPABeX3egV7696K4kVUZCmb9YLmFOWPSP8t39ovR07ziHib3CU2b5w8izjnS0dfJVRvzZuXrrJmGLHd+EhGoOeZFfleJGblmOILRXcIzVVZHJAt5MuQus4buCBdARqGJIdXSpnrd/GbbgqdwJZi0JocQyRjPoWh8CCfC+jEqZoCUdbv4QGrk9HQulpN+hhvIPKGjxady32oNXOQb+oeLOm0/Nf1JQEV4BpwaD+qACtcQN5tAnmviMJ3c/hkHDYEwGYagGtfAXeijA9YbgAGBYqjDcfWIR5p7XIzjqY1PC3ZTCwhY0u8vuVpC8tt7r/H2hzlkddm8F5AlLfAGIOXIKPXySfCSG43Jfck2duRrBW7TTnrj87VRVeAj8pbXYPXzTTa0PxB5Q716xYeb4ooUU2WxREzxv1cfnVuFOxDYqv87dxsg315oyNJJjghJnDvqOQGAA4A==");
    private static final int[] t = Util.decodeIntArray("OjnON9P69c+rwnc3WsUtG1ywZ55PozdC04InQJm8m77VEY6dvw9zFdYtHH7HAMR7t4wbayGhkEWybrG+ajZutFdIqy+8lG55xqN20mVJwshTD/juRo3efdVzCh1M0E3GKTm726m6RlCslSbovl7jBKH61fBqLVGaY++M4pqG7iLAicK4QyQu9qUeA6qc8tCkg8Bhupvpak2P5RVQumRb1igmovmnOjrhS6mVhu9VYunHL+/T91L32j8Eb2l3+gpZgOSpFYewhgGbCeatOz7lk+mQ/VqeNNeXLPC32QIri1GW1aw6AX2mfdHPPtZ8fS0oH58lz63yuJta1rRyWoj1TOAprHHgGaXmR7Cs/e2T+pvo08SNKDtXzPjVZil5Ey4oeF8Bke11YFX3lg5E49NejBUFbdSI9G26A6FhJQVk8L3D654VPJBXopcnGuypOgcqGz9tmx5jIfX1nGb7JtzzGXUz2SixVf31A1Y0goq6PLsoUXcRwgrZ+KvMUWfMrZJfTegXUTgw3I43nVhikyD5kep6kML7PnvOUSHOZHdPvjKotuN+wyk9RkjeU2lkE+aAoq4IEN1tsiRphS39CQchZrOaRgpkRcDdWGzezxwgyK5bvvfdG1iNQMzSAX9rtOO73aJqfjpZ/0U+NQpEvLTN1XLqzqj6ZIS7jWYSrr88b0fSm+RjVC9dnq7Cdxv2TmNwdA4NjedbE1f4chZxr1N9XUBAywhOtOLMNNJGagEVr4ThsAQolZg6HQa4n7TObqBIbz87gjUgq4IBGh1LJ3In+GEVYLHnkz/cuzp5KzRFJb2giDnhUc55Sy8yybegH7rJ4BzIfrzH0fbPARHDoeiqxxqQh0nUT72a0Nrey9UK2jgDOcMqxpE2Z435MXzgsStP955Zt0P1uzry1Rn/J9lFnL+XIiwV5vwqD5H8cZuUFSX65ZNhzrac68KoZFkSuqjRtsEHXuMFagwQ0lBlywOkQuDsbg4WmNs7TJigvjJ46WSfH5Uy4NOS39OgNCuJcfIeGwp0QUujNIzFvnEgw3Yy2N81n42bmS8u5gtvRw/j8R3lTNpUHtrYkc5iec/NPn5vFhixZv0sHQWEj9LF9vsimfUj81emMnYjk6g1MVbMzQKs8IFiWnXrtW4WNpeI0nPM3pZikoG5SdBMUJAbccZWFObGx70yehQKReHQBsPye5rJqlP9YqgPALslv+I1vdL2cRJpBbIEAiK2y898zXacK1MRPsAWQOPTOKu9YCVHrfC6OCCc90bOdnevocUgdWBghcv+Torojdh6qvmwTPmqfhlIwlwC+4qMAcNq5Nbr4fmQ1PhpplzeoD8JJS3CCOaft05hMs534ltXj9/jOsNy5g==");
    private final int[] b;
    private final int[] c;
    private final int[] m;
    private final int[] n;
    private final int[] o;

    C0031l() {
        super("Blowfish", 1);
        this.b = new int[256];
        this.c = new int[256];
        this.m = new int[256];
        this.n = new int[256];
        this.o = new int[18];
    }

    @Override // iaik.security.cipher.AbstractC0030k
    void a(int i, byte[] bArr) throws InvalidKeyException {
        if (bArr.length < 5 || bArr.length > 56) {
            throw new InvalidKeyException("Key must be between 40 and 448 bits long!");
        }
        int[] iArr = q;
        System.arraycopy(iArr, 0, this.b, 0, iArr.length);
        int[] iArr2 = r;
        System.arraycopy(iArr2, 0, this.c, 0, iArr2.length);
        int[] iArr3 = s;
        System.arraycopy(iArr3, 0, this.m, 0, iArr3.length);
        int[] iArr4 = t;
        System.arraycopy(iArr4, 0, this.n, 0, iArr4.length);
        int[] iArr5 = p;
        System.arraycopy(iArr5, 0, this.o, 0, iArr5.length);
        int length = bArr.length;
        int i2 = 0;
        for (int i3 = 0; i3 < 18; i3++) {
            int i4 = 0;
            for (int i5 = 0; i5 < 4; i5++) {
                i4 = (i4 << 8) | (bArr[i2] & 255);
                i2++;
                if (i2 >= length) {
                    i2 = 0;
                }
            }
            int[] iArr6 = this.o;
            iArr6[i3] = i4 ^ iArr6[i3];
        }
        int[] iArr7 = this.a;
        iArr7[0] = 0;
        iArr7[1] = 0;
        int i6 = 0;
        while (i6 < 18) {
            b();
            int[] iArr8 = this.o;
            int i7 = i6 + 1;
            iArr8[i6] = iArr7[0];
            i6 = i7 + 1;
            iArr8[i7] = iArr7[1];
        }
        int i8 = 0;
        while (i8 < 256) {
            b();
            int[] iArr9 = this.b;
            int i9 = i8 + 1;
            iArr9[i8] = iArr7[0];
            i8 = i9 + 1;
            iArr9[i9] = iArr7[1];
        }
        int i10 = 0;
        while (i10 < 256) {
            b();
            int[] iArr10 = this.c;
            int i11 = i10 + 1;
            iArr10[i10] = iArr7[0];
            i10 = i11 + 1;
            iArr10[i11] = iArr7[1];
        }
        int i12 = 0;
        while (i12 < 256) {
            b();
            int[] iArr11 = this.m;
            int i13 = i12 + 1;
            iArr11[i12] = iArr7[0];
            i12 = i13 + 1;
            iArr11[i13] = iArr7[1];
        }
        int i14 = 0;
        while (i14 < 256) {
            b();
            int[] iArr12 = this.n;
            int i15 = i14 + 1;
            iArr12[i14] = iArr7[0];
            i14 = i15 + 1;
            iArr12[i15] = iArr7[1];
        }
    }

    @Override // iaik.security.cipher.AbstractC0030k
    void b() {
        int i = this.a[0];
        int i2 = this.a[1];
        int i3 = i ^ this.o[0];
        int i4 = 1;
        while (i4 < 16) {
            int[] iArr = this.b;
            int i5 = iArr[i3 >>> 24];
            int[] iArr2 = this.c;
            int i6 = i5 + iArr2[(i3 >> 16) & 255];
            int[] iArr3 = this.m;
            int i7 = i6 ^ iArr3[(i3 >> 8) & 255];
            int[] iArr4 = this.n;
            int i8 = i7 + iArr4[i3 & 255];
            int[] iArr5 = this.o;
            int i9 = i4 + 1;
            i2 ^= iArr5[i4] ^ i8;
            i3 ^= (((iArr[i2 >>> 24] + iArr2[(i2 >> 16) & 255]) ^ iArr3[(i2 >> 8) & 255]) + iArr4[i2 & 255]) ^ iArr5[i9];
            i4 = i9 + 1;
        }
        this.a[0] = i2 ^ this.o[17];
        this.a[1] = i3;
    }

    @Override // iaik.security.cipher.AbstractC0030k
    void c() {
        int i = this.a[1];
        int i2 = this.a[0] ^ this.o[17];
        int i3 = 16;
        while (i3 > 0) {
            int[] iArr = this.b;
            int i4 = iArr[i2 >>> 24];
            int[] iArr2 = this.c;
            int i5 = i4 + iArr2[(i2 >> 16) & 255];
            int[] iArr3 = this.m;
            int i6 = i5 ^ iArr3[(i2 >> 8) & 255];
            int[] iArr4 = this.n;
            int i7 = i6 + iArr4[i2 & 255];
            int[] iArr5 = this.o;
            int i8 = i3 - 1;
            i ^= iArr5[i3] ^ i7;
            i2 ^= (((iArr[i >>> 24] + iArr2[(i >> 16) & 255]) ^ iArr3[(i >> 8) & 255]) + iArr4[i & 255]) ^ iArr5[i8];
            i3 = i8 - 1;
        }
        this.a[0] = i ^ this.o[0];
        this.a[1] = i2;
    }

    @Override // iaik.security.cipher.AbstractC0030k
    public void d() {
        super.d();
        CriticalObject.destroy(this.o);
        CriticalObject.destroy(this.b);
        CriticalObject.destroy(this.c);
        CriticalObject.destroy(this.m);
        CriticalObject.destroy(this.n);
    }
}
