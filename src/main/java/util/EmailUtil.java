package util;

import javax.mail.*;
import javax.mail.search.FlagTerm;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.Properties;

/**
 * класс для работы с почтой
 */
public class EmailUtil {

    public enum Scope {
        ALL(null, false),
        UNSEEN(Flags.Flag.SEEN, false),
        SEEN(Flags.Flag.SEEN, true);

        private Flags.Flag flag;
        private boolean value;

        private Scope(Flags.Flag f, boolean v) {
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

    private static boolean textIsHtml = false;

    private static Map<String, Store> storeMap = new HashMap<>();

    private static Map<String, Folder> folderMap = new HashMap<>();

    private static Properties props = new Properties();

    static {
        props.put("mail.store.protocol", "imaps");
        props.put("mail.imaps.ssl.trust", "*");
    }

    public static Folder getInbox(String fullEmail, String password, String folderBox) {
        if (fullEmail == null) return null;
        String host = "imap.gmail.com";//fullEmail.replaceAll("(.+?@)(.+)","$2");
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
        Message[] retMsgs = null;
        folder.getMessageCount();
        if (scope.getFlag() == null) {
            retMsgs = folder.getMessages();
            scope = Scope.UNSEEN;
        } else {
            Flags flags = new Flags(scope.getFlag());
            FlagTerm flagTerm = new FlagTerm(flags, scope.getValue());
            retMsgs = folder.search(flagTerm);
        }
//  инвертируем флаг SEEN
        for (Message msg : retMsgs) {
            msg.setFlag(scope.getFlag(), !scope.getValue());
        }
        return retMsgs;
    }

    public static String getMessageAsString(Message message) throws IOException, MessagingException {
        // Multipart mp = (Multipart) message.getContent();
        // BodyPart bp = mp.getBodyPart(0);
        String retval = (String) message.getContent();//EmailUtil.getText(bp);
        return retval;
    }


    /**
     * Return the primary text content of the message.
     */
    public static String getText(Part p) throws
            MessagingException, IOException {
        if (p.isMimeType("text/*")) {
            String s = (String) p.getContent();
            textIsHtml = p.isMimeType("text/html");
            return s;
        }

        if (p.isMimeType("multipart/alternative")) {
            // prefer html text over plain text
            Multipart mp = (Multipart) p.getContent();
            String text = null;
            for (int i = 0; i < mp.getCount(); i++) {
                Part bp = mp.getBodyPart(i);
                if (bp.isMimeType("text/plain")) {
                    if (text == null)
                        text = getText(bp);
                    continue;
                } else if (bp.isMimeType("text/html")) {
                    String s = getText(bp);
                    if (s != null)
                        return s;
                } else {
                    return getText(bp);
                }
            }
            return text;
        } else if (p.isMimeType("multipart/*")) {
            Multipart mp = (Multipart) p.getContent();
            for (int i = 0; i < mp.getCount(); i++) {
                String s = getText(mp.getBodyPart(i));
                if (s != null)
                    return s;
            }
        }

        return null;
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
        } catch (MessagingException e) {
//            e.printStackTrace();
        }
        folder.getMessageCount();
    }

    public static void closeFolder(Folder folder) {
        if (folder != null && folder.isOpen())
            try {
                folder.close(true);
            } catch (MessagingException e) {
                e.printStackTrace();
            }
    }

    @Deprecated
    public static void clearFolder(String emailAddress, String password, String folder) throws MessagingException {
        String host = emailAddress.replaceAll("(.+?@)(.+)", "$2");
        String mainEmailAddress = emailAddress.replaceAll("([^\\\\+]+)[\\\\+][^@]+(@.*)", "$1$2");
        Properties props = System.getProperties();
        props.setProperty("mail.store.protocol", "imaps");
        props.put("mail.imaps.ssl.trust", "*");
        Session session = Session.getDefaultInstance(props, null);
        Store store = null;
//        try {
        store = session.getStore();
        store.connect(host, mainEmailAddress, password);
        Folder inbox = store.getFolder(folder);
        inbox.open(Folder.READ_WRITE);
        Message[] msg = inbox.getMessages();
        for (int i = 0; i < msg.length; i++) {
            msg[i].setFlag(Flags.Flag.DELETED, true);
        }
        inbox.close(true);
        store.close();
//        } catch (NoSuchProviderException e) {
//            e.printStackTrace();
//        } catch (MessagingException e) {
//            e.printStackTrace();
//        }
    }

    @Deprecated
    public static void clearInbox(String emailAddress, String password) throws MessagingException {
        String host = emailAddress.replaceAll("(.+?@)(.+)", "$2");
        Properties props = System.getProperties();
        props.setProperty("mail.store.protocol", "imaps");
        props.put("mail.imaps.ssl.trust", "*");
        Session session = Session.getDefaultInstance(props, null);
        Store store = null;
//        try {
        store = session.getStore();
        store.connect(host, emailAddress, password);
        Folder inbox = store.getFolder("INBOX");
        inbox.open(Folder.READ_WRITE);
        Message[] msg = inbox.getMessages();
        for (int i = 0; i < msg.length; i++) {
            msg[i].setFlag(Flags.Flag.DELETED, true);
        }
        inbox.close(true);
        store.close();
//        } catch (NoSuchProviderException e) {
//            e.printStackTrace();
//        } catch (MessagingException e) {
//            e.printStackTrace();
//        }
    }

    public static String getLastEmailAsString(String emailAddress, String password) throws IOException, MessagingException {
        return getLastEmailAsString(emailAddress, password, "INBOX");
    }

    public static String getLastEmailAsString(String emailAddress, String password, String folder) throws IOException, MessagingException {
        String host = emailAddress.replaceAll("(.+?@)(.+)", "$2");
        String mainEmailAddress = emailAddress.replaceAll("([^\\\\+]+)[\\\\+][^@]+(@.*)", "$1$2");
        Properties props = System.getProperties();
        props.setProperty("mail.store.protocol", "imaps");
        props.put("mail.imaps.ssl.trust", "*");
        Session session = Session.getDefaultInstance(props, null);
        Store store = null;
//        try {
        store = session.getStore();
        store.connect(host, mainEmailAddress, password);
        Folder inbox = store.getFolder(folder);
        inbox.open(Folder.READ_ONLY);
        if (inbox.getMessageCount() == 0) return null;
        Message msg = inbox.getMessage(inbox.getMessageCount());
        Address[] in = msg.getFrom();
//            for (Address address : in) {
//                System.out.println("FROM:" + address.toString());
//            }
        Multipart mp = (Multipart) msg.getContent();
//            MimeMultipart mmp = (MimeMultipart) msg.getContent();
        BodyPart bp = mp.getBodyPart(0);
//            System.out.println("SENT DATE:" + msg.getSentDate());
//            System.out.println("SUBJECT:" + msg.getSubject());
//            System.out.println("CONTENT:" + bp.getContent());
//            System.out.println("CONTENT:" + getText(bp));
        String retStr = getText(bp);
        inbox.close(true);
        store.close();
        return retStr;
//        } catch (NoSuchProviderException e) {
//            e.printStackTrace();
//        } catch (MessagingException e) {
//            e.printStackTrace();
//        } catch (IOException e) {
//            e.printStackTrace();
//        }
//        return null;
    }

    public static Message[] getAllEmail(String emailAddress, String password) throws MessagingException {
        String host = emailAddress.replaceAll("(.+?@)(.+)", "$2");
        Properties props = System.getProperties();
        props.setProperty("mail.store.protocol", "imaps");
        props.put("mail.imaps.ssl.trust", "*");
        Session session = Session.getDefaultInstance(props, null);
        Store store = null;
//        try {
        store = session.getStore();
        store.connect(host, emailAddress, password);
        Folder inbox = store.getFolder("INBOX");
        inbox.open(Folder.READ_ONLY);
        Message[] messages = inbox.getMessages();
        inbox.close(true);
        store.close();
        return messages;
//        } catch (NoSuchProviderException e) {
//            e.printStackTrace();
//        } catch (MessagingException e) {
//            e.printStackTrace();
//        }
//        return new Message[0];
    }

    //  activation email parsers:
    public static String getActivationKey(String activationEmail) {
        return activationEmail == null ? "" : activationEmail.substring(activationEmail.indexOf("/reset-password/") + 16, activationEmail.indexOf("'>here"));
    }

    public static String getActivationUrl(String activationEmail) {
        return activationEmail == null ? "" : activationEmail.substring(activationEmail.indexOf(":8101") + 5, activationEmail.indexOf("/reset-password/") + 16);
    }

    public static String getActivationLogin(String activationEmail) {
        return activationEmail == null ? "" : activationEmail.substring(activationEmail.indexOf("Use username ") + 13, activationEmail.indexOf(" and password"));
    }

    //  reset password email parser
    public static String getResetPasswordKey(String resetPswEmail) {
//        return resetPswEmail==null ? "" : resetPswEmail.replaceAll("(?s)(?:\\n*.*Код активации:\\s+)([\\w\\d]+)(?:\\n*.*)", "$1");
        return getActivationKey(resetPswEmail);
    }

    public static String getResetPasswordUrl(String resetPswEmail) {
        return resetPswEmail == null ? "" : resetPswEmail.replaceAll("(?s)(?:\\n*.*\\s+<a href=\")(http.+)(?:(\".*))", "$1");
    }

    public static String getResetPasswordName(String resetPswEmail) {
        return resetPswEmail == null ? "" : resetPswEmail.replaceAll("(?s)(?:.*\\nЗдравствуйте,\\s+)(.*)(?:(!<.*))", "$1");
    }

    //  deactivation email parser:
    public static String getDeactivatedName(String deactivationEmail) {
        return deactivationEmail == null ? "" : deactivationEmail.replaceAll("(?s)(?:.*\\nЗдравствуйте,\\s+)(.*)(?:(!<.*))", "$1").trim();
    }

    public static String getDeactivationInitiator(String deactivationEmail) {
        return deactivationEmail == null ? "" : deactivationEmail.replaceAll("(?s)(?:.*Автор операции:\\s+)([^<]*)(?:<.*)", "$1").trim();
    }

    public static String getDeactivationDate(String deactivationEmail) {
        return deactivationEmail == null ? "" : deactivationEmail.replaceAll("(?s)(?:.*Дата операции:\\s+)([^<]*)(?:<.*)", "$1").trim();
    }

    public static String getDeactivationTime(String deactivationEmail) {
        return deactivationEmail == null ? "" : deactivationEmail.replaceAll("(?s)(?:.*Время операции:\\s+)([^<]*)(?:<.*)", "$1").trim();
    }

    //  не актуально:
    public static String getActivationFio(String activationEmail) {
        return activationEmail == null ? "" : activationEmail.replaceAll("(?s)(?:\\n*.*,\\s+)(.+)(?:!(<br>\\n){2,}.*\\n*)", "$1");
    }

    public static String getActivationData(String activationEmail) {
        return activationEmail == null ? "" : activationEmail.replaceAll("(?s)(?:\\n*.*:\\s+)((\\d{2,4}\\.?){3})(?:\\n*.*)", "$1");
    }

    public static String getActivationTime(String activationEmail) {
        return activationEmail == null ? "" : activationEmail.replaceAll("(?s)(?:\\n*.*:\\s+)([\\d\\:]+)(?:\\n*.*)", "$1");
    }

    public static String getActivationInitiator(String activationEmail) {
        return activationEmail == null ? "" : activationEmail.replaceAll("(?s)(?:\\n*.*\\n{2}.+:\\s*)([^\\n<]*)(?:<br>\\n.*(\\d{2,4}\\.?){3}\\n*.*)", "$1");
    }

    public static void checkActivationEmail(Folder folder, String managerEmail, String baseUri, SoftAssert sa) throws IOException, MessagingException, InterruptedException {
        String activationMsg = "укажите пароль для успешной активации вашей учетной записи";
        String sd = "(step: check activation email)\n";
//  получение письма с кодом активации
        Thread.sleep(2000);
        Message[] msg = EmailUtil.getMessages(folder, EmailUtil.Scope.UNSEEN);
        sa.assertTrue(msg.length > 0, sd + "activation email not found");
        if (msg.length == 0) return;

        String emailStr = EmailUtil.getMessageAsString(msg[0]);
//  проверка содержания письма активации
        sa.assertTrue(emailStr.contains(activationMsg), sd +
                "activation email must contain: \"" + activationMsg + "\", but actual email body:" +
                "\n" + emailStr.replaceAll("(?s)(?:\\n*.*<body>\\n)(.+)(?:(</body>.*))", "$1"));
        String activationKey = EmailUtil.getActivationKey(emailStr);
        String activationUrl = EmailUtil.getActivationUrl(emailStr);
//  проверка наличия ключа активации в адресе ссылки
        sa.assertTrue(activationUrl.contains(activationKey), sd +
                "activation URL must contain activation key, but actual: " + activationUrl);
//  проверка адреса ссылки
        sa.assertTrue(activationUrl.contains(baseUri + "/#idm/activate?key="), sd +
                "activation URL must contain \"" + baseUri + "/#idm/activate?key=\"" + "\nactual activation URL: " + activationUrl);
        String activationLogin = EmailUtil.getActivationLogin(emailStr);
        sa.assertTrue(activationLogin.equals(managerEmail), sd +
                "expected activation login: " + managerEmail + ", but actual: " + activationLogin);
    }

}
