package com.restdatabus.authorization;

import java.util.List;

public interface AuthorizationUser extends AuthorizationEntity {

    List<AuthorizationGroup> getGroups();
}
