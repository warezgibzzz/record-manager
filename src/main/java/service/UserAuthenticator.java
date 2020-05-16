package service;

import security.HashUtilInterface;

public class UserAuthenticator {
    protected HashUtilInterface hashUtil;

    public UserAuthenticator(HashUtilInterface hashUtil)
    {
        this.hashUtil = hashUtil;
    }
}
