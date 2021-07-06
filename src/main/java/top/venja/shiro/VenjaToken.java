package top.venja.shiro;

import org.apache.shiro.authc.RememberMeAuthenticationToken;

/***
 * Not in use for now
 */
public class VenjaToken implements RememberMeAuthenticationToken {

    private String username;
    private String password;
    private boolean rememberMe;
    private String passToken;

    public VenjaToken(String username, String password) {
        this(username, password, false, null);
    }

    public VenjaToken(String username, String password, boolean rememberMe, String passToken) {
        this.username = username;
        this.password = password;
        this.rememberMe = rememberMe;
        this.passToken = passToken;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    @Override
    public boolean isRememberMe() {
        return false;
    }

    public void setRememberMe(boolean rememberMe) {
        this.rememberMe = rememberMe;
    }

    public String getPassToken() {
        return passToken;
    }

    public void setPassToken(String passToken) {
        this.passToken = passToken;
    }

    @Override
    public Object getPrincipal() {
        return this.getUsername();
    }

    @Override
    public Object getCredentials() {
        return this.getPassword();
    }

    public void clear() {
        this.username = null;
        this.password = null;
        this.rememberMe = false;
        this.passToken = null;
    }

    public String toString() {
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append(this.getClass().getName());
        stringBuilder.append(" - ");
        stringBuilder.append(this.username);
        stringBuilder.append(", rememberMe=").append(this.rememberMe);
        if (this.passToken != null) {
            stringBuilder.append(", passToken=").append(this.passToken);
        }

        return stringBuilder.toString();
    }

}
