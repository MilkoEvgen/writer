package org.example.model;

public enum PostStatus {
    ACTIVE(1), UNDER_REVIEW(2), DELETED(3);

    private final int value;

    PostStatus(int value) {
        this.value = value;
    }

    public int getValue() {
        return value;
    }

    public static PostStatus fromValue(int value) {
        for (PostStatus status : PostStatus.values()) {
            if (status.value == value) {
                return status;
            }
        }
        throw new IllegalArgumentException("Invalid PostStatus value: " + value);
    }
}
