package org.kobjects.pim;

import java.io.IOException;
import java.io.Writer;
import java.util.Enumeration;

/* JADX INFO: loaded from: classes2.dex */
public class PimWriter {
    Writer writer;

    public PimWriter(Writer writer) {
        this.writer = writer;
    }

    public void writeEntry(PimItem pimItem) throws IOException {
        this.writer.write("begin:");
        this.writer.write(pimItem.getType());
        this.writer.write("\r\n");
        Enumeration enumerationFieldNames = pimItem.fieldNames();
        while (enumerationFieldNames.hasMoreElements()) {
            String str = (String) enumerationFieldNames.nextElement();
            for (int i = 0; i < pimItem.getFieldCount(str); i++) {
                PimField field = pimItem.getField(str, i);
                this.writer.write(str);
                this.writer.write(58);
                this.writer.write(field.getValue().toString());
                this.writer.write("\r\n");
            }
        }
        this.writer.write("end:");
        this.writer.write(pimItem.getType());
        this.writer.write("\r\n\r\n");
    }
}
