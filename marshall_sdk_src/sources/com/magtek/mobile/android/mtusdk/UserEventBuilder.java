package com.magtek.mobile.android.mtusdk;

/* JADX INFO: loaded from: classes.dex */
public class UserEventBuilder {
    public static String CARD_SEATED = "card_seated";
    public static String CARD_SWIPED = "card_swiped";
    public static String CARD_UNSEATED = "card_unseated";
    public static String CONTACTLESS_CARD_PRESENTED = "contactless_card_presented";
    public static String CONTACTLESS_CARD_REMOVED = "contactless_card_removed";
    public static String TOUCH_PRESENTED = "touch_presented";
    public static String TOUCH_REMOVED = "touch_removed";

    public static UserEvent GetValue(String str) {
        UserEvent userEvent;
        UserEvent userEvent2 = UserEvent.None;
        if (str == null) {
            return userEvent2;
        }
        try {
            if (str.equalsIgnoreCase(CONTACTLESS_CARD_PRESENTED)) {
                userEvent = UserEvent.ContactlessCardPresented;
            } else if (str.equalsIgnoreCase(CONTACTLESS_CARD_REMOVED)) {
                userEvent = UserEvent.ContactlessCardRemoved;
            } else if (str.equalsIgnoreCase(CARD_SEATED)) {
                userEvent = UserEvent.CardSeated;
            } else if (str.equalsIgnoreCase(CARD_UNSEATED)) {
                userEvent = UserEvent.CardUnseated;
            } else if (str.equalsIgnoreCase(CARD_SWIPED)) {
                userEvent = UserEvent.CardSwiped;
            } else if (str.equalsIgnoreCase(TOUCH_PRESENTED)) {
                userEvent = UserEvent.TouchPresented;
            } else {
                if (!str.equalsIgnoreCase(TOUCH_REMOVED)) {
                    return userEvent2;
                }
                userEvent = UserEvent.TouchRemoved;
            }
            return userEvent;
        } catch (Exception unused) {
            return userEvent2;
        }
    }

    /* JADX INFO: renamed from: com.magtek.mobile.android.mtusdk.UserEventBuilder$1, reason: invalid class name */
    static /* synthetic */ class AnonymousClass1 {
        static final /* synthetic */ int[] $SwitchMap$com$magtek$mobile$android$mtusdk$UserEvent;

        static {
            int[] iArr = new int[UserEvent.values().length];
            $SwitchMap$com$magtek$mobile$android$mtusdk$UserEvent = iArr;
            try {
                iArr[UserEvent.ContactlessCardPresented.ordinal()] = 1;
            } catch (NoSuchFieldError unused) {
            }
            try {
                $SwitchMap$com$magtek$mobile$android$mtusdk$UserEvent[UserEvent.ContactlessCardRemoved.ordinal()] = 2;
            } catch (NoSuchFieldError unused2) {
            }
            try {
                $SwitchMap$com$magtek$mobile$android$mtusdk$UserEvent[UserEvent.CardSeated.ordinal()] = 3;
            } catch (NoSuchFieldError unused3) {
            }
            try {
                $SwitchMap$com$magtek$mobile$android$mtusdk$UserEvent[UserEvent.CardUnseated.ordinal()] = 4;
            } catch (NoSuchFieldError unused4) {
            }
            try {
                $SwitchMap$com$magtek$mobile$android$mtusdk$UserEvent[UserEvent.CardSwiped.ordinal()] = 5;
            } catch (NoSuchFieldError unused5) {
            }
            try {
                $SwitchMap$com$magtek$mobile$android$mtusdk$UserEvent[UserEvent.TouchPresented.ordinal()] = 6;
            } catch (NoSuchFieldError unused6) {
            }
            try {
                $SwitchMap$com$magtek$mobile$android$mtusdk$UserEvent[UserEvent.TouchRemoved.ordinal()] = 7;
            } catch (NoSuchFieldError unused7) {
            }
        }
    }

    public static String GetString(UserEvent userEvent) {
        switch (AnonymousClass1.$SwitchMap$com$magtek$mobile$android$mtusdk$UserEvent[userEvent.ordinal()]) {
            case 1:
                return CONTACTLESS_CARD_PRESENTED;
            case 2:
                return CONTACTLESS_CARD_REMOVED;
            case 3:
                return CARD_SEATED;
            case 4:
                return CARD_UNSEATED;
            case 5:
                return CARD_SWIPED;
            case 6:
                return TOUCH_PRESENTED;
            case 7:
                return TOUCH_REMOVED;
            default:
                return "";
        }
    }
}
