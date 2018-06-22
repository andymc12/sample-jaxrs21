package io.openliberty.sample.ssechat;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.concurrent.atomic.AtomicLong;

public class ChatMessage {

    private static final AtomicLong idGenerator = new AtomicLong();
    private static final SimpleDateFormat dateFormat = new SimpleDateFormat("HH:mm:ss");

    private final long msgID;
    private final String timestamp;
    private final String user;
    private final String message;

    ChatMessage(String user, String message) {
        this.user = user;
        this.message = message;
        this.msgID = idGenerator.incrementAndGet();
        this.timestamp = dateFormat.format(new Date());
    }

    public long getMsgID() {
        return msgID;
    }

    public String getTimestamp() {
        return timestamp;
    }

    public String getUser() {
        return user;
    }

    public String getMessage() {
        return message;
    }
}
