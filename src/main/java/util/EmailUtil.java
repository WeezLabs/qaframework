package util;

import javax.mail.*;
import javax.mail.search.FlagTerm;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.Properties;

/**
 * Connect, read, clean and parse emails
 */
public class EmailUtil {

    public enum Scope {
        ALL(null, false),
        UNSEEN(Flags.Flag.SEEN, false),
        SEEN(Flags.Flag.SEEN, true);

        private Flags.Flag flag;
        private boolean value;

        Scope(Flags.Flag f, boolean v) {
            flag = f;
            value = v;
        }

        public Flags.Flag getFlag() {
            return flag;
        }

        public boolean getValue() {
            return value;
        }
    }

    private static Map<String, Store> storeMap = new HashMap<>();

    private static Map<String, Folder> folderMap = new HashMap<>();

    private static Properties props = new Properties();

    static {
        props.put("mail.store.protocol", "imaps");
        props.put("mail.imaps.ssl.trust", "*");
    }

    public static Folder getInbox(String fullEmail, String password, String folderBox) {
        if (fullEmail == null) return null;
        String host = "imap.gmail.com";
        String emailAddress = fullEmail.replaceAll("(?s)(.*?)\\+([\\w\\d-]+)(@.*)", "$1$3");
        String folderName = (folderBox != null && !folderBox.isEmpty()) ? folderBox : "INBOX";

        Store store = null;

        Session session = Session.getInstance(props, null);
        try {
            store = session.getStore();
            store.connect(host, emailAddress, password);
            storeMap.put(emailAddress, store);
        } catch (NoSuchProviderException e) {
            e.printStackTrace();
        } catch (MessagingException e) {
            e.printStackTrace();
        }
        if (store == null) return null;
        Folder folder = null;
        try {
            folder = store.getFolder(folderName);
            if (folder.isOpen())
                folder.close(true);
            folder.open(Folder.READ_WRITE);
            folderMap.put(fullEmail, folder);
        } catch (MessagingException e) {
            e.printStackTrace();
        } catch (IllegalStateException e1) {
            e1.printStackTrace();
        }
        return folder;

    }

    public static Message[] getMessages(Folder folder, Scope scope) throws MessagingException {
        Message[] retMsgs;
        folder.getMessageCount();
        if (scope.getFlag() == null) {
            retMsgs = folder.getMessages();
            scope = Scope.UNSEEN;
        } else {
            Flags flags = new Flags(scope.getFlag());
            FlagTerm flagTerm = new FlagTerm(flags, scope.getValue());
            retMsgs = folder.search(flagTerm);
        }

        for (Message msg : retMsgs) {
            msg.setFlag(scope.getFlag(), !scope.getValue());
        }
        return retMsgs;
    }

    public static String getMessageAsString(Message message) throws IOException, MessagingException {
        Object content = message.getContent();
        StringBuilder result = new StringBuilder();
        if (content instanceof String) {
            result.append(content);
        } else if (content instanceof Multipart) {
            Multipart parts = (Multipart) content;
            for (int i = 0; i < parts.getCount(); i++) {
                BodyPart part = parts.getBodyPart(i);
                if (part.getContent() instanceof String) {
                    result.append(part.getContent());
                }
            }
        }
        return result.toString();
    }

    public static void clearFolder(Folder folder) throws MessagingException {
        if (!folder.isOpen()) {
            try {
                folder.open(Folder.READ_WRITE);
            } catch (MessagingException e) {
                e.printStackTrace();
            }

        }
        folder.getMessageCount();
        Message[] msgs = folder.getMessages();
        try {
            for (Message msg : msgs) {
                msg.setFlag(Flags.Flag.SEEN, true);
                msg.setFlag(Flags.Flag.DELETED, true);
            }
        } catch (MessagingException e) {}
        folder.getMessageCount();
        folder.expunge();
    }

    public static void closeFolder(Folder folder) {
        if (folder != null && folder.isOpen())
            try {
                folder.close(true);
            } catch (MessagingException e) {
                e.printStackTrace();
            }
    }

    // just a sample of some methods for email parsing. Replace it with something useful
    public static String getAttendToken(String invitationEmail) {
        return invitationEmail == null ? "" : invitationEmail.substring(invitationEmail
                .indexOf("/participations/accept/") + 23, invitationEmail.indexOf("/participations/accept/") + 194);
    }

    public static String getDeclineToken(String invitationEmail) {
        return invitationEmail == null ? "" : invitationEmail.substring(invitationEmail
                .indexOf("/participations/decline/") + 24, invitationEmail.indexOf("/participations/decline/") + 195);
    }
}
