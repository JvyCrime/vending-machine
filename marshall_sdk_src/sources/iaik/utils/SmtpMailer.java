package iaik.utils;

import androidx.core.os.EnvironmentCompat;
import androidx.recyclerview.widget.ItemTouchHelper;
import java.io.BufferedReader;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.Vector;

/* JADX INFO: loaded from: classes2.dex */
public class SmtpMailer {
    String a;
    a b;
    a c;
    a d;
    String e;
    String f;
    Vector g;
    Vector h;
    Vector i;
    Vector j;
    StringBuffer k;
    BufferedReader l;
    boolean m;
    boolean n;
    String o;

    public SmtpMailer() {
        this.a = "localhost";
        this.e = "";
        this.f = "SmtpMailer";
        this.g = new Vector();
        this.h = new Vector();
        this.i = new Vector();
        this.j = new Vector();
        this.k = new StringBuffer();
        this.m = true;
        this.n = false;
        this.b = new a(EnvironmentCompat.MEDIA_UNKNOWN, "unknown@localhost");
        this.o = System.getProperty("line.separator");
    }

    public SmtpMailer(String str) {
        this();
        this.a = str;
    }

    private void a(PrintWriter printWriter) {
        printWriter.print(this.o);
        printWriter.flush();
    }

    private void a(Object obj, PrintWriter printWriter) {
        printWriter.print(obj);
        printWriter.print(this.o);
        printWriter.flush();
    }

    private void a(String str) {
        if (this.n) {
            System.err.println(str);
        }
    }

    private void a(String str, PrintWriter printWriter) {
        printWriter.print(str);
        printWriter.print(this.o);
        printWriter.flush();
    }

    public void addAdditionalField(String str) {
        this.j.addElement(str);
    }

    public void addBcc(String str, String str2) {
        this.i.addElement(new a(str, str2));
    }

    public void addCc(String str, String str2) {
        this.h.addElement(new a(str, str2));
    }

    public void addText(String str) {
        this.k.append(str);
    }

    public void addTo(String str, String str2) {
        this.g.addElement(new a(str, str2));
    }

    protected boolean checkResponse(int i) throws SmtpException {
        String line;
        int i2;
        do {
            try {
                line = this.l.readLine();
                if (line == null) {
                    break;
                }
            } catch (IOException e) {
                StringBuffer stringBuffer = new StringBuffer();
                stringBuffer.append("IOException: ");
                stringBuffer.append(e.getMessage());
                throw new SmtpException(stringBuffer.toString());
            }
        } while (line.length() == 0);
        StringBuffer stringBuffer2 = new StringBuffer();
        stringBuffer2.append("Response: ");
        stringBuffer2.append(this.o);
        stringBuffer2.append(line);
        a(stringBuffer2.toString());
        if (line != null) {
            try {
                i2 = Integer.parseInt(line.substring(0, 3));
            } catch (NumberFormatException unused) {
                i2 = 0;
            }
        } else {
            i2 = 0;
        }
        if (i2 == i) {
            a("Response ok...");
            return true;
        }
        StringBuffer stringBuffer3 = new StringBuffer();
        stringBuffer3.append("Response not ok... (Should be ");
        stringBuffer3.append(line);
        stringBuffer3.append(" but is ");
        stringBuffer3.append(i2);
        a(stringBuffer3.toString());
        return false;
    }

    protected boolean send(String str, PrintWriter printWriter, int i) throws SmtpException {
        a(str, printWriter);
        if (!this.m) {
            return true;
        }
        StringBuffer stringBuffer = new StringBuffer();
        stringBuffer.append("Sent: ");
        stringBuffer.append(this.o);
        stringBuffer.append(str);
        a(stringBuffer.toString());
        return checkResponse(i);
    }

    protected boolean sendCommands(PrintWriter printWriter) throws SmtpException {
        StringBuffer stringBuffer = new StringBuffer();
        stringBuffer.append("HELO ");
        stringBuffer.append(this.f);
        boolean zSend = send(stringBuffer.toString(), printWriter, ItemTouchHelper.Callback.DEFAULT_SWIPE_ANIMATION_DURATION) & true;
        StringBuffer stringBuffer2 = new StringBuffer();
        stringBuffer2.append("MAIL FROM: <");
        stringBuffer2.append(this.b.b);
        stringBuffer2.append(">");
        boolean zSend2 = zSend & send(stringBuffer2.toString(), printWriter, ItemTouchHelper.Callback.DEFAULT_SWIPE_ANIMATION_DURATION);
        for (int i = 0; i < this.g.size(); i++) {
            StringBuffer stringBuffer3 = new StringBuffer();
            stringBuffer3.append("RCPT TO: <");
            stringBuffer3.append(((a) this.g.elementAt(i)).b);
            stringBuffer3.append(">");
            zSend2 &= send(stringBuffer3.toString(), printWriter, ItemTouchHelper.Callback.DEFAULT_SWIPE_ANIMATION_DURATION);
        }
        for (int i2 = 0; i2 < this.h.size(); i2++) {
            StringBuffer stringBuffer4 = new StringBuffer();
            stringBuffer4.append("RCPT TO: <");
            stringBuffer4.append(((a) this.h.elementAt(i2)).b);
            stringBuffer4.append(">");
            zSend2 &= send(stringBuffer4.toString(), printWriter, ItemTouchHelper.Callback.DEFAULT_SWIPE_ANIMATION_DURATION);
        }
        for (int i3 = 0; i3 < this.i.size(); i3++) {
            StringBuffer stringBuffer5 = new StringBuffer();
            stringBuffer5.append("RCPT TO: <");
            stringBuffer5.append(((a) this.i.elementAt(i3)).b);
            stringBuffer5.append(">");
            zSend2 &= send(stringBuffer5.toString(), printWriter, ItemTouchHelper.Callback.DEFAULT_SWIPE_ANIMATION_DURATION);
        }
        return zSend2;
    }

    public boolean sendMail() throws SmtpException {
        try {
            Socket socket = new Socket(this.a, 25);
            PrintWriter printWriter = new PrintWriter(socket.getOutputStream(), true);
            this.l = new BufferedReader(new InputStreamReader(socket.getInputStream()));
            boolean zCheckResponse = checkResponse(220) & true & sendCommands(printWriter) & send("DATA", printWriter, 354);
            a("Sending message body... ");
            writeHeaders(printWriter);
            writeBody(printWriter);
            a(printWriter);
            a(".", printWriter);
            boolean zCheckResponse2 = zCheckResponse & checkResponse(ItemTouchHelper.Callback.DEFAULT_SWIPE_ANIMATION_DURATION);
            try {
                send("QUIT", printWriter, 221);
                socket.close();
            } catch (IOException e) {
                StringBuffer stringBuffer = new StringBuffer();
                stringBuffer.append("IOException: ");
                stringBuffer.append(e.getMessage());
                a(stringBuffer.toString());
            }
            a("done.");
            return zCheckResponse2;
        } catch (IOException e2) {
            StringBuffer stringBuffer2 = new StringBuffer();
            stringBuffer2.append("IOException: ");
            stringBuffer2.append(e2.getMessage());
            throw new SmtpException(stringBuffer2.toString());
        }
    }

    public void setDebug(boolean z) {
        this.n = z;
    }

    public void setFrom(String str, String str2) {
        this.b = new a(str, str2);
    }

    public void setLineSeparator(String str) {
        if (str != null) {
            this.o = str;
        }
    }

    public void setReplyTo(String str, String str2) {
        this.d = new a(str, str2);
    }

    public void setSubject(String str) {
        this.e = str;
    }

    public void setText(String str) {
        StringBuffer stringBuffer = new StringBuffer();
        this.k = stringBuffer;
        stringBuffer.append(str);
    }

    public void setVerify(boolean z) {
        this.m = z;
    }

    public String toString() {
        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
        PrintWriter printWriter = new PrintWriter((OutputStream) byteArrayOutputStream, true);
        writeHeaders(printWriter);
        writeBody(printWriter);
        return Util.toASCIIString(byteArrayOutputStream.toByteArray());
    }

    protected void writeBody(PrintWriter printWriter) {
        a(printWriter);
        StringBuffer stringBuffer = new StringBuffer();
        stringBuffer.append((Object) this.k);
        stringBuffer.append(this.o);
        a(stringBuffer.toString(), printWriter);
    }

    protected void writeHeaders(PrintWriter printWriter) {
        if (this.b != null) {
            StringBuffer stringBuffer = new StringBuffer();
            stringBuffer.append("From: ");
            stringBuffer.append(this.b);
            a(stringBuffer.toString(), printWriter);
        }
        if (this.c != null) {
            StringBuffer stringBuffer2 = new StringBuffer();
            stringBuffer2.append("Sender: ");
            stringBuffer2.append(this.c);
            a(stringBuffer2.toString(), printWriter);
        }
        for (int i = 0; i < this.g.size(); i++) {
            StringBuffer stringBuffer3 = new StringBuffer();
            stringBuffer3.append("To: ");
            stringBuffer3.append(this.g.elementAt(i));
            a(stringBuffer3.toString(), printWriter);
        }
        if (this.d != null) {
            StringBuffer stringBuffer4 = new StringBuffer();
            stringBuffer4.append("Reply-To: ");
            stringBuffer4.append(this.d);
            a(stringBuffer4.toString(), printWriter);
        }
        for (int i2 = 0; i2 < this.h.size(); i2++) {
            StringBuffer stringBuffer5 = new StringBuffer();
            stringBuffer5.append("Cc: ");
            stringBuffer5.append(this.h.elementAt(i2));
            a(stringBuffer5.toString(), printWriter);
        }
        StringBuffer stringBuffer6 = new StringBuffer();
        stringBuffer6.append("Subject: ");
        stringBuffer6.append(this.e);
        a(stringBuffer6.toString(), printWriter);
        for (int i3 = 0; i3 < this.j.size(); i3++) {
            a(this.j.elementAt(i3), printWriter);
        }
    }
}
