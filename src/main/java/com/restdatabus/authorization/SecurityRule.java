package com.restdatabus.authorization;

import java.util.Set;

public class SecurityRule {

    private String target;
    private AuthorizationRole role;
    private Set<Action> authorizations;

    public SecurityRule(String target, AuthorizationRole role, Set<Action> authorizations) {
        this.target = target;
        this.role = role;
        this.authorizations = authorizations;
    }

    public String getTarget() {
        return target;
    }

    public AuthorizationRole getRole() {
        return role;
    }

    public Set<Action> getAuthorizations() {
        return authorizations;
    }
}
