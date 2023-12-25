package org.example.model;

public enum PostStatus {
    ACTIVE(1), UNDER_REVIEW(2), DELETED(3);

    private final Integer value;

    PostStatus(Integer value) {
        this.value = value;
    }

    public Integer getValue() {
        return value;
    }

    public static PostStatus fromValue(Integer value) {
        for (PostStatus status : PostStatus.values()) {
            if (status.value == value) {
                return status;
            }
        }
        throw new IllegalArgumentException("Invalid PostStatus value: " + value);
    }
}
