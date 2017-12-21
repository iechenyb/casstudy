package org.jasig.cas.authentication;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

/**
 * CasServer添加验证码校验
 */
public class UsernamePasswordCaptchaSystemCredential extends UsernamePasswordCaptchaCredential {

    private static final long serialVersionUID = 8317889802836113837L;

    @NotNull
    @Size(min = 1, message = "required.system")
    private String system;

    

    public String getSystem() {
		return system;
	}

	public void setSystem(String system) {
		this.system = system;
	}

	@Override
    public int hashCode() {
        final int prime = 31;
        int result = super.hashCode();
        result = prime * result + ((system == null) ? 0 : system.hashCode());
        return result;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj)
            return true;
        if (!super.equals(obj))
            return false;
        if (getClass() != obj.getClass())
            return false;
        UsernamePasswordCaptchaSystemCredential other = (UsernamePasswordCaptchaSystemCredential) obj;
        if (system == null) {
            if (other.system != null)
                return false;
        } else if (!system.equals(other.system))
            return false;
        return true;
    }

    @Override
    public String toString() {
        return "UsernamePasswordCaptchaCredentials [system="+system+"captcha=" + getCaptcha() + ", isRememberMe()=" + isRememberMe()
                + ", getPassword()=" + getPassword() + ", getUsername()=" + getUsername() + ", toString()="
                + super.toString() + ", getClass()=" + getClass() + "]";
    }
}