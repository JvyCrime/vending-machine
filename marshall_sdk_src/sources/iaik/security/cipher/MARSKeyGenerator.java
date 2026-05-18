package iaik.security.cipher;

/* JADX INFO: loaded from: classes.dex */
public class MARSKeyGenerator extends VarLengthKeyGenerator {
    public MARSKeyGenerator() {
        super("MARS", 128, 448, 128, 32);
    }
}
