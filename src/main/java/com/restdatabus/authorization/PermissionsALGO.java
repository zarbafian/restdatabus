package com.restdatabus.authorization;

import org.springframework.stereotype.Service;

@Service
public class PermissionsALGO {
/*
    private static final Logger LOGGER = LoggerFactory.getLogger(PermissionsALGO.class);

    private static final String ALL_RESOURCES = "*";

    private static SecurityRule DEFAULT_USER_PERMISSIONS;
    private static SecurityRule DEFAULT_OTHER_PERMISSIONS;

    private static ArrayList<SecurityRule> rules = new ArrayList<SecurityRule>();

    static {
        // Default user permission
        DEFAULT_USER_PERMISSIONS = new SecurityRule("*", USER, PERMISSIONS_ALL);

        // Default other permission
        DEFAULT_OTHER_PERMISSIONS = new SecurityRule("*", OTHER, PERMISSION_READ);

        // Init default annotation
        rules.add(DEFAULT_USER_PERMISSIONS);
        rules.add(DEFAULT_OTHER_PERMISSIONS);
    }

    @Autowired
    private AccountService accountService;

    @Autowired
    private FileService fileService;

    @Autowired
    private CardService cardService;

    @Autowired
    private CardCommentService commentService;

    @Autowired
    private FileCommentService fileCommentService;

    @Autowired
    private UserService userService;

    @Override
    public void isAuthenticated() {

        boolean authenticated = UserUtils.isAuthenticated(accountService);

        if(!authenticated) {
            throw new AccessDeniedException("Must be authenticated");
        }
    }

    @Override
    public void hasPermission(Serializable targetId, ResourceType resourceType, Authorization authorization) {

        LOGGER.debug("> hasPermission - targetId: {} - resourceName: {} - permission: {}", targetId, resourceType.toString(), authorization.toString());

        Set<Authorization> missingAuthorizations = computeMissingAuthorizations(targetId, resourceType, authorization);

        StringBuilder sb = new StringBuilder("Missing permission" + (missingAuthorizations.size() > 1 ? "s" : "") + ": [ ");

        if ( missingAuthorizations.size() > 0 ) {
            for(Authorization mp: missingAuthorizations) {
                sb.append(mp);
                sb.append(" ");
            }
        }
        sb.append("]");

        boolean hasPermission = isEmpty(missingAuthorizations);

        LOGGER.debug("< hasPermission - hasPermission: {}", hasPermission);

        if(!hasPermission) {
            throw new AccessDeniedException(sb.toString());
        }
    }

    @Override
    public boolean checkPermission(Serializable targetId, ResourceType resourceType, Authorization authorization) {

        LOGGER.debug("> checkPermission - targetId: {} - resourceName: {} - permission: {}", targetId, resourceType.toString(), authorization.toString());

        Set<Authorization> missingAuthorizations = computeMissingAuthorizations(targetId, resourceType, authorization);

        boolean hasPermission = isEmpty(missingAuthorizations);

        LOGGER.debug("< checkPermission - returning: {}", hasPermission);

        return hasPermission;
    }

    private boolean isEmpty(Set<Authorization> authorizations) {
        return (authorizations.size() == 0);
    }

    private Set<Authorization> computeMissingAuthorizations(Serializable targetId, ResourceType resourceType, Authorization authorization) {

        // Logged-in user
        User user = UserUtils.getLoggedInUser(accountService);

        // Requested permission
        Authorization[] c = new Authorization[]{ authorization };
        Set<Authorization> requestedPermissions = new HashSet<Authorization>();
        requestedPermissions.addAll(Arrays.asList(c));

        // Accessed resource
        AuthorizationResource resource = findResource((Long) targetId, resourceType);

        // User role
        AuthorizationRole role = ( user.getEntityUniqueId().equals(resource.getOwner().getEntityUniqueId()) ? USER : OTHER );

        // User permissions
        Set<Authorization> authorizations = computePermissions(resourceType, role);

        // Check permissions against required permissions
        Set<Authorization> missingAuthorizations = verifyPermissions(requestedPermissions, authorizations);

        return missingAuthorizations;
    }

    private AuthorizationResource findResource(Long id, ResourceType type) {

        switch (type) {
            case CARD:
                return cardService.findOne(id);
            case FILE:
                return fileService.findOne(id);
            case COMMENT:
                return commentService.findOne(id);
            case FILE_COMMENT:
                return fileCommentService.findOne(id);
            case USER:
                return userService.findOne(id);
            default:
                throw new IllegalArgumentException("Not implemented");
        }
    }

    private static Set<Authorization> computePermissions(ResourceType resourceType, AuthorizationRole role) {

        for(SecurityRule rule: rules) {
            if(rule.getRole() == role) {
                if(resourcesMatch(resourceType.toString(), rule.getTarget())) {
                    return rule.getAuthorizations();
                }
            }
        }
        return new HashSet<Authorization>();
    }

    private static boolean resourcesMatch(String resourceName, String target){
        return ( resourceName.equalsIgnoreCase(target) || ALL_RESOURCES.equals(target) );
    }

    private static Set<Authorization> verifyPermissions(Set<Authorization> requestedPermissions, Set<Authorization> actualPermissions) {

        LOGGER.debug("> verifyPermissions");
        Authorization[] missing = requestedPermissions
                .stream()
                .filter(reqPerm -> !actualPermissions.contains(reqPerm))
                .toArray(Authorization[]::new);
        HashSet<Authorization> missingPermissions = new HashSet<Authorization>();
        missingPermissions.addAll(Arrays.asList(missing));

        LOGGER.debug("< verifyPermissions - missingPermissions: {}", missing);
        return missingPermissions;
    }
    */
}
