package learningresourcefinder.security;
import java.util.Collections;
import java.util.EnumSet;
import java.util.Set;

import learningresourcefinder.exception.UnauthorizedAccessException;
import learningresourcefinder.model.Competence;
import learningresourcefinder.model.PlayList;
import learningresourcefinder.model.Resource;
import learningresourcefinder.model.User;
import learningresourcefinder.model.User.Role;
import learningresourcefinder.repository.UserRepository;
import learningresourcefinder.service.LevelService;
import learningresourcefinder.service.LoginService;
import learningresourcefinder.util.Action;
import learningresourcefinder.web.ContextUtil;



/**
 * Holds the security related information during request execution.
 */

public  class SecurityContext {

    public static final Privilege MASTER_PRIVILEGE = Privilege.MANAGE_USERS;

    private static ThreadLocal<User> user = new ThreadLocal<User>();    // Lazyly retrieved from the DB if needed (if getUser() is called).
    private static ThreadLocal<Long> userId = new ThreadLocal<Long>();  // retrieved in from the session. Non null <=> user logged in.

    // Note from John 2009-07-01: should be useless with Vaadin (no need to communicate between action and JSP...)  ----
    // Contextual custom privileges are usually set by the action (any string) and checked by the jsp through the authorization tag, as non-custom contextual privileges.
    // The difference with the custom, is that you can invent any string for a very little fine grained temporary privilege. The jsp has to know the same string of course.
    // example: edit_is_own_profile; view_influence;...
    private static ThreadLocal<Set<String>> contextualCustomPrivileges = new ThreadLocal<Set<String>>();

 
    /**
     * Removes the security context associated to the request
     */
    public static void clear() {
        user.set(null);
        userId.set(null);
        contextualCustomPrivileges.set(null);
    }

    public static void assertUserHasPrivilege(Privilege privilege) {
        if (!isUserHasPrivilege(privilege)) {
            throw new UnauthorizedAccessException(privilege);
        }
    }

    public static void assertUserIsLoggedIn() {
        if (getUser() == null) {
            throw new UnauthorizedAccessException();
        }
    }


    // Probably the most used method of this class (from outside).
    public static  boolean isUserHasPrivilege(Privilege privilege) {
        return isUserHasAllPrivileges(EnumSet.of(privilege));
    }


    public static  boolean isUserHasAllPrivileges(EnumSet<Privilege> privileges)  {
        if (getUser() == null) {
            return false;
        }
        EnumSet<Privilege> currentPrivileges = getAllAssociatedPrivileges(getUser());
       return currentPrivileges.containsAll(privileges);
    }
    

    /**
     * @return true if the user has one of the privileges
     */
    public static boolean isUserHasOneOfPrivileges(EnumSet<Privilege> privileges) {
        if (getUser() == null) {
            return false;
        }
        if (Collections.disjoint(getAllAssociatedPrivileges(getUser()), privileges) == false) {  // There is at least one element in common.
            return true;
        } else {
            return false;
        }
    }

    /**
     * @return All privilege associated to a community role
     */
    public static EnumSet<Privilege> getAssociatedPrivilege(Role role) {
        EnumSet<Privilege> associatedPrivileges = EnumSet.noneOf(Privilege.class);
        if (role != null) {
            for (Privilege privilege : Privilege.values()) {
                if (role.isHigherOrEquivalent(privilege.getAssociatedRole())) {
                    associatedPrivileges.add(privilege);
                }
            }
        }
        return associatedPrivileges;
    }

    /**
     * @return all the privilege associated to the given user including
     *         associations present in the DB and association derived from
     *         user's role.
     */
    public static EnumSet<Privilege> getAllAssociatedPrivileges(User user) {
        EnumSet<Privilege> allUserPrivileges = EnumSet.noneOf(Privilege.class);
        if (user != null) {
              
            allUserPrivileges.addAll(user.getPrivileges());
           
            allUserPrivileges.addAll(getAssociatedPrivilege(user.getRole()));
        }
        return allUserPrivileges;
    }


    public static User getUser()  {
       
        if (getUserId() == null) {  // Not logged in.
            return null;
        }
        
        if (user.get() == null) {  // User not loaded yet.
            User user = ((UserRepository) ContextUtil.getSpringBean(UserRepository.class)).find(getUserId());
            

            setUser( user );  // Lazy loading if needed.
        }

        return user.get();
    }


    private static  void setUser(User userParam) {
        //Security constraint
        if (user.get() != null) {
            throw new IllegalStateException("Bug: Could not set a new user on the security context once a user has already been set");
        }
        if (userId.get() == null) {
            userId.set(userParam.getId());
        }
        user.set(userParam);
    } 

    //method to know if the user is logged or not
    public static boolean isUserLoggedIn() {
		return getUserId() != null;
    }


    //get the value of the threadlocal userId 
    public static  Long getUserId() {

       if (userId.get() == null) { // Then try to get it from the HttpSession.

            LoginService loginService = (LoginService) ContextUtil.getSpringBean(LoginService.class);              // This is not beauty, but life is sometimes ugly. -- no better idea (except making SecurityContext a bean managed by Spring... but for not much benefit...) -- John 2009-07-02

           Long id = loginService.getLoggedInUserIdFromSession();  
 
           if (id != null) {  // A user is effectively logged in.
                userId.set(id);  // remember it in the SecurityContext.
           }
        }
       
        return userId.get();
    }
 

    public static boolean isUserHasRole(Role role) {
        User user = getUser();
        if(user == null || user.getRole() == null){
            return false;
        } 
        return user.getRole() == role;
    }

    public static void assertUserHasRole(Role role) {
        if (!isUserHasRole(role)) {
               throw new UnauthorizedAccessException("You need the following role: " + role);
           }
   }

    
    //////////////////////////////////////////////////// Specific rights /////////////////////////////////////
    //////////////////////////////////////////////////// Specific rights /////////////////////////////////////
    //////////////////////////////////////////////////// Specific rights /////////////////////////////////////
    //////////////////////////////////////////////////// Specific rights /////////////////////////////////////
    //////////////////////////////////////////////////// Specific rights /////////////////////////////////////

    public static boolean canCurrentUserViewPrivateData(User user2) {
        return canCurrentUserChangeUser(user2) || isUserHasPrivilege(Privilege.VIEW_PRIVATE_DATA_OF_USERS); 
    }

    public static boolean canCurrentUserChangeUser(User user2) { 
        return user2.equals(getUser()) // If the user is editing himself
                || isUserHasPrivilege(Privilege.MANAGE_USERS);     // or If this user has the privilege to edit other users
    }

    public static boolean canCurrentUserEditPlayList(PlayList playList) {
        if (isUserHasPrivilege(Privilege.MANAGE_PLAYLIST)) {               // If this user has the privilege to edit other playlist
            return true;
        }
    	if (playList.getCreatedBy() == null) {  // May happen in case of playlist created by a batch program.
    		return false;
    	}
        return playList.getCreatedBy().equals(getUser()); // If the user is editing his own playlist
    }
    
    public static boolean canCurrentUserEditResource(Resource resource) { 
        return resource.getCreatedBy().equals(getUser()) // If the user is editing his own resource
                || isUserHasPrivilege(Privilege.MANAGE_RESOURCE);     // or If this user has the privilege to edit other resource
    }

    public static  void assertCurrentUserMayEditThisUser(User user) {
        if (! canCurrentUserChangeUser(user)) {
            throw new UnauthorizedAccessException(" cannot edit that user: " + user.getUserName());
        }
    }
    
    public static  void assertCurrentUserMayEditThisPlaylist(PlayList playList) {
        if (! canCurrentUserEditPlayList(playList)) {
            throw new UnauthorizedAccessException(" cannot edit that playlist: " + playList.getName());
        }
    }
    
    public static  void assertCurrentUserMayEditThisResource(Resource resource) {
        if (! canCurrentUserEditResource(resource)) {
            throw new UnauthorizedAccessException(" cannot edit that resource: " + resource.getName());
        }
    }
    
    public static  void assertCurrentUserMayEditThisCompetence() {
        if (! canCurrentUserEditCompetence()) {
            throw new UnauthorizedAccessException(" cannot edit a competence");
        }
    }
    
    public static boolean canCurrentUserEditCompetence() { 
        return isUserHasPrivilege(Privilege.MANAGE_COMPETENCE);     // If this user has the privilege to edit other competence
    }

    public static boolean canCurrentDoAction(Action action) {
        return  ((LevelService) ContextUtil.getSpringBean(LevelService.class)).canDoAction(getUser(), action);
    }
    
    public static void assertCanCurrentDoAction(Action action) {
        if (!canCurrentDoAction(action)) {
            throw new UnauthorizedAccessException(" cannot do that action: " + action.getDescribe());
        }
    }


}
