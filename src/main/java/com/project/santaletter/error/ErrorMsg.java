package com.project.santaletter.error;

public class ErrorMsg {

    public static String userNotFoundMsg(Long userId) {
        return String.format("User not found for userId = %s", userId);
    }

    public static String letterNotFound(Long id) {
        return String.format("Letter not found for id = %s", id.toString());

    }

    public static String letterAccessDenied(Long userId, Long letterId) {
        return String.format("User does not have access to letter for username = %s, letterId = %s", userId, letterId);

    }

    public static String usernameClaimNotFound() {
        return String.format("Invalid Token - username claim not found");
    }
}
