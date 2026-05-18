package iaik.utils;

/* JADX INFO: loaded from: classes2.dex */
class a {
    String a;
    String b;

    public a(String str, String str2) {
        this.a = str;
        this.b = str2;
    }

    public String toString() {
        if (this.a == null) {
            return this.b;
        }
        StringBuffer stringBuffer = new StringBuffer();
        stringBuffer.append(this.a);
        stringBuffer.append(" <");
        stringBuffer.append(this.b);
        stringBuffer.append(">");
        return stringBuffer.toString();
    }
}
