package com.library.util;

import com.fasterxml.jackson.annotation.JsonValue;

public enum LibarayRole {
    READER("READER"), STUDENT("STUDENT"), TEACHER("TEACHER"), LIBRARIAN("LIBRARIAN"), GUEST("GUEST"), DMIN("DMIN"),
    ADMIN("ADMIN"), SUPER_ADMIN("SUPER_ADMIN");

    private String value;

    private LibarayRole(String value) {
        this.value = value;
    }

    public static boolean isValidRole(String role) {
        try {
            LibarayRole.valueOf(role.toUpperCase());
            return true;
        } catch (IllegalArgumentException e) {
            return false;
        }
    }

    @JsonValue
    public String getValue() {
        return this.value;
    }

    public String toString() {
        return String.valueOf(this.value);
    }

    public LibarayRole fromValue(String value) {
        for (LibarayRole b : LibarayRole.values()) {
            if (b.value.equals(value)) {
                return b;
            }
        }
        return null;
    }

}
