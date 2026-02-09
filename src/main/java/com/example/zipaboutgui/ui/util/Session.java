package com.example.zipaboutgui.ui.util;

import com.example.zipaboutgui.domain.enums.Role;
import com.example.zipaboutgui.domain.user.User;

/**
 * Session utility class used to store application-wide login state.
 *
 * <p>This class keeps track of:
 * <ul>
 *   <li>The currently logged-in user</li>
 *   <li>The current role (USER or ADMIN)</li>
 * </ul>
 * </p>
 *
 * <p>It allows all controllers to access authentication
 * and authorization information without passing objects
 * between scenes.</p>
 *
 * <p>This is a GUI equivalent of the implicit session
 * that existed in the CLI version.</p>
 */
public class Session {

    /**
     * Currently logged-in user.
     * Null when an admin-only session is active or when logged out.
     */
    private static User currentUser;

    /**
     * Role of the current session.
     * Can be USER, ADMIN, or null if logged out.
     */
    private static Role currentRole;

    /*  LOGIN  */

    /**
     * Logs in a regular user.
     *
     * @param user the authenticated user
     */
    public static void loginUser(User user) {
        currentUser = user;
        currentRole = Role.USER;
    }

    /**
     * Logs in an admin-only session.
     *
     * <p>This method exists for flexibility,
     * but in the current implementation admins
     * are logged in via {@link #loginUser(User)}.</p>
     */
    public static void loginAdmin() {
        currentUser = null;
        currentRole = Role.ADMIN;
    }

    /*  GETTERS  */

    /**
     * Returns the currently logged-in user.
     *
     * @return the current user, or {@code null} if none
     */
    public static User getCurrentUser() {
        return currentUser;
    }

    /**
     * Returns the role associated with the current session.
     *
     * @return current role, or {@code null} if logged out
     */
    public static Role getCurrentRole() {
        return currentRole;
    }

    /**
     * Checks whether the current session belongs to an admin.
     *
     * @return {@code true} if admin is logged in
     */
    public static boolean isAdmin() {
        return currentRole == Role.ADMIN;
    }

    /**
     * Checks whether the current session belongs to a regular user.
     *
     * @return {@code true} if user is logged in
     */
    public static boolean isUser() {
        return currentRole == Role.USER;
    }

    /*  LOGOUT  */

    /**
     * Logs out the current session.
     *
     * <p>Clears both user and role information.</p>
     */
    public static void logout() {
        currentUser = null;
        currentRole = null;
    }

    /*  LEGACY SUPPORT  */

    /**
     * Temporary alias maintained for backward compatibility.
     *
     * <p>This allows older code to continue working
     * without modification.</p>
     *
     * @param user the authenticated user
     */
    public static void login(User user) {
        loginUser(user);
    }
}
