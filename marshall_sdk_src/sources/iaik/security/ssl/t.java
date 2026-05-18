package iaik.security.ssl;

/* JADX INFO: loaded from: classes.dex */
class t extends ae {
    private String a;

    public t(ae aeVar, boolean z) {
        super(aeVar);
        this.a = z ? "client." : "server.";
    }

    public t() {
    }

    @Override // iaik.security.ssl.ae
    public String a(String str) {
        StringBuffer stringBuffer = new StringBuffer(String.valueOf(this.a));
        stringBuffer.append(str);
        String strA = super.a(stringBuffer.toString());
        return strA == null ? super.a(str) : strA;
    }

    @Override // iaik.security.ssl.ae
    public String[] a(String str, String[] strArr) {
        String[] strArrA = super.a(str, strArr);
        StringBuffer stringBuffer = new StringBuffer(String.valueOf(this.a));
        stringBuffer.append(str);
        return super.a(stringBuffer.toString(), strArrA);
    }

    @Override // iaik.security.ssl.ae
    public boolean a(String str, boolean z) {
        boolean zA = super.a(str, z);
        StringBuffer stringBuffer = new StringBuffer(String.valueOf(this.a));
        stringBuffer.append(str);
        return super.a(stringBuffer.toString(), zA);
    }

    @Override // iaik.security.ssl.ae
    public int a(String str, int i) {
        int iA = super.a(str, i);
        StringBuffer stringBuffer = new StringBuffer(String.valueOf(this.a));
        stringBuffer.append(str);
        return super.a(stringBuffer.toString(), iA);
    }
}
