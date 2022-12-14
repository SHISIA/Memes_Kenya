package com.memesKenya.meme.entities;

public enum AccountStatus {
    BLOCKED,//permanent user account revocation
    ACTIVE,//active ,usable account
    ON_HOLD,//held by admin for various reasons mostly punishment for violation on platform rules
    UNDER_INVESTIGATION,//investigated/under investigation for suspicious behaviour
    AWAITING_CONFIRMATION //account awaits activation / usability confirmation
}
