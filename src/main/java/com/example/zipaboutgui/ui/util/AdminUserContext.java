package com.example.zipaboutgui.ui.util;

import com.example.zipaboutgui.domain.user.User;

/**
 * Shared context used to pass a selected {@link User}
 * between admin-related screens.
 *
 * <p>This class avoids tight coupling between controllers
 * by providing a simple, static storage for the currently
 * selected user.</p>
 *
 * <p>It is primarily used when navigating from:
 * <ul>
 *   <li>AdminUsersController → AdminUserDetailsController</li>
 * </ul>
 * </p>
 *
 * <p>The context is cleared explicitly after use to avoid
 * stale or incorrect data.</p>
 */
public class AdminUserContext {

    /**
     * Holds the currently selected user.
     */
    private static User selectedUser;

    /**
     * Stores the selected user in the shared context.
     *
     * @param user the user selected in the admin users table
     */
    public static void setSelectedUser(User user) {
        selectedUser = user;
    }

    /**
     * Retrieves the currently selected user.
     *
     * @return the selected user, or {@code null} if none is set
     */
    public static User getSelectedUser() {
        return selectedUser;
    }

    /**
     * Clears the stored user from the context.
     *
     * <p>This should be called when leaving the
     * user details screen.</p>
     */
    public static void clear() {
        selectedUser = null;
    }
}
