package com.algorithms.auth.userservice.domain.draft;

import com.algorithms.auth.userservice.domain.enums.Gender;
import com.algorithms.auth.userservice.domain.enums.Locale;

import javax.validation.constraints.NotNull;
import java.util.Date;

public class UserDraft {
    protected final long companyId;
    protected final String firstName;
    protected final String middleName;
    protected final String lastName;
    protected final String email;
    protected final String username;
    protected final Gender gender;
    protected final Locale locale;
    protected final String passwordHash;
    protected final String passwordResetToken;
    protected final String securityStamp;
    protected final String emailConfirmationToken;
    protected final boolean emailConfirmed;
    protected final String phoneNumber;
    protected final boolean phoneNumberConfirmed;
    protected final boolean twoFactorEnabled;
    protected final Date lockoutEnd;
    protected final boolean lockoutEnabled;
    protected final int accessFailedCount;
    protected final boolean isPrimaryAdmin;
    protected final String lastIp;
    protected final Date dateCreated;
    protected final Date lastUpdated;
    protected final Date lastLoginDate;


    public UserDraft(long companyId, String firstName, String middleName, String lastName, String email, String username,
                     Gender gender, Locale locale, String passwordHash, String passwordResetToken, String securityStamp,
                     String emailConfirmationToken, boolean emailConfirmed, String phoneNumber, boolean phoneNumberConfirmed,
                     boolean twoFactorEnabled, Date lockoutEnd, boolean lockoutEnabled, int accessFailedCount, boolean isPrimaryAdmin,
                     String lastIp, Date dateCreated, Date lastUpdated, Date lastLoginDate) {
        this.companyId = companyId;
        this.firstName = firstName;
        this.middleName = middleName;
        this.lastName = lastName;
        this.email = email;
        this.username = username;
        this.gender = gender;
        this.locale = locale;
        this.passwordHash = passwordHash;
        this.passwordResetToken = passwordResetToken;
        this.securityStamp = securityStamp;
        this.emailConfirmationToken = emailConfirmationToken;
        this.emailConfirmed = emailConfirmed;
        this.phoneNumber = phoneNumber;
        this.phoneNumberConfirmed = phoneNumberConfirmed;
        this.twoFactorEnabled = twoFactorEnabled;
        this.lockoutEnd = lockoutEnd;
        this.lockoutEnabled = lockoutEnabled;
        this.accessFailedCount = accessFailedCount;
        this.isPrimaryAdmin = isPrimaryAdmin;
        this.lastIp = lastIp;
        this.dateCreated = dateCreated;
        this.lastUpdated = lastUpdated;
        this.lastLoginDate = lastLoginDate;
    }

    public long getCompanyId() {
        return companyId;
    }

    public String getFirstName() {
        return firstName;
    }

    public String getMiddleName() {
        return middleName;
    }

    public String getLastName() {
        return lastName;
    }

    public String getEmail() {
        return email;
    }

    public String getUsername() {
        return username;
    }

    public Gender getGender() {
        return gender;
    }

    public Locale getLocale() {
        return locale;
    }

    public String getPasswordHash() {
        return passwordHash;
    }

    public String getPasswordResetToken() {
        return passwordResetToken;
    }

    public String getSecurityStamp() {
        return securityStamp;
    }

    public String getEmailConfirmationToken() {
        return emailConfirmationToken;
    }

    public boolean isEmailConfirmed() {
        return emailConfirmed;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public boolean isPhoneNumberConfirmed() {
        return phoneNumberConfirmed;
    }

    public boolean isTwoFactorEnabled() {
        return twoFactorEnabled;
    }

    public Date getLockoutEnd() {
        return lockoutEnd;
    }

    public boolean isLockoutEnabled() {
        return lockoutEnabled;
    }

    public int getAccessFailedCount() {
        return accessFailedCount;
    }

    public boolean isPrimaryAdmin() {
        return isPrimaryAdmin;
    }

    public String getLastIp() {
        return lastIp;
    }

    public Date getDateCreated() {
        return dateCreated;
    }

    public Date getLastUpdated() {
        return lastUpdated;
    }

    public Date getLastLoginDate() {
        return lastLoginDate;
    }

    public Builder draft() {
        return new Builder(this);
    }


    public static class Builder {
        protected long companyId;
        protected String firstName;
        protected String middleName;
        protected String lastName;
        protected String email;
        protected String username;
        protected Gender gender;
        protected Locale locale;
        protected String passwordHash;
        protected String passwordResetToken;
        protected String securityStamp;
        protected String emailConfirmationToken;
        protected boolean emailConfirmed;
        protected String phoneNumber;
        protected boolean phoneNumberConfirmed;
        protected boolean twoFactorEnabled;
        protected Date lockoutEnd;
        protected boolean lockoutEnabled;
        protected int accessFailedCount;
        protected boolean isPrimaryAdmin;
        protected String lastIp;
        protected Date dateCreated;
        protected Date lastUpdated;
        protected Date lastLoginDate;

        Builder() {
        }

        public Builder(@NotNull UserDraft draft) {
            this.companyId = draft.companyId;
            this.firstName = draft.firstName;
            this.middleName = draft.middleName;
            this.lastName = draft.lastName;
            this.email = draft.email;
            this.username = draft.username;
            this.gender = draft.gender;
            this.locale = draft.locale;
            this.passwordHash = draft.passwordHash;
            this.passwordResetToken = draft.passwordResetToken;
            this.securityStamp = draft.securityStamp;
            this.emailConfirmationToken = draft.emailConfirmationToken;
            this.emailConfirmed = draft.emailConfirmed;
            this.phoneNumber = draft.phoneNumber;
            this.phoneNumberConfirmed = draft.phoneNumberConfirmed;
            this.twoFactorEnabled = draft.twoFactorEnabled;
            this.lockoutEnd = draft.lockoutEnd;
            this.lockoutEnabled = draft.lockoutEnabled;
            this.accessFailedCount = draft.accessFailedCount;
            this.isPrimaryAdmin = draft.isPrimaryAdmin;
            this.lastIp = draft.lastIp;
            this.dateCreated = draft.dateCreated;
            this.lastUpdated = draft.lastUpdated;
            this.lastLoginDate = draft.lastLoginDate;
        }

        public Builder companyId(long companyId) {
            this.companyId = companyId;
            return this;
        }

        public Builder firstName(String firstName) {
            this.firstName = firstName;
            return this;
        }

        public Builder middleName(String middleName) {
            this.middleName = middleName;
            return this;
        }

        public Builder lastName(String lastName) {
            this.lastName = lastName;
            return this;
        }

        public Builder email(String email) {
            this.email = email;
            return this;
        }

        public Builder username(String username) {
            this.username = username;
            return this;
        }

        public Builder gender(Gender gender) {
            this.gender = gender;
            return this;
        }

        public Builder locale(Locale locale) {
            this.locale = locale;
            return this;
        }

        public Builder passwordHash(String passwordHash) {
            this.passwordHash = passwordHash;
            return this;
        }

        public Builder passwordResetToken(String passwordResetToken) {
            this.passwordResetToken = passwordResetToken;
            return this;
        }

        public Builder securityStamp(String securityStamp) {
            this.securityStamp = securityStamp;
            return this;
        }

        public Builder emailConfirmationToken(String emailConfirmationToken) {
            this.emailConfirmationToken = emailConfirmationToken;
            return this;
        }

        public Builder twoFactorEnabled(boolean twoFactorEnabled) {
            this.twoFactorEnabled = twoFactorEnabled;
            return this;
        }

        public Builder emailConfirmed(boolean emailConfirmed) {
            this.emailConfirmed = emailConfirmed;
            return this;
        }

        public Builder phoneNumber(String phoneNumber) {
            this.phoneNumber = phoneNumber;
            return this;
        }

        public Builder phoneNumberConfirmed(boolean phoneNumberConfirmed) {
            this.phoneNumberConfirmed = phoneNumberConfirmed;
            return this;
        }

        public Builder lockoutEnd(Date lockoutEnd) {
            this.lockoutEnd = lockoutEnd;
            return this;
        }

        public Builder lockoutEnabled(boolean lockoutEnabled) {
            this.lockoutEnabled = lockoutEnabled;
            return this;
        }

        public Builder accessFailedCount(int accessFailedCount) {
            this.accessFailedCount = accessFailedCount;
            return this;
        }

        public Builder isPrimaryAdmin(boolean isPrimaryAdmin) {
            this.isPrimaryAdmin = isPrimaryAdmin;
            return this;
        }

        public Builder lastIp(String lastIp) {
            this.lastIp = lastIp;
            return this;
        }

        public Builder dateCreated(Date dateCreated) {
            this.dateCreated = dateCreated;
            return this;
        }

        public Builder lastUpdated(Date lastUpdated) {
            this.lastUpdated = lastUpdated;
            return this;
        }

        public Builder lastLoginDate(Date lastLoginDate) {
            this.lastLoginDate = lastLoginDate;
            return this;
        }

        public UserDraft build() {
            return new UserDraft(companyId, firstName, middleName, lastName, email, username, gender, locale, passwordHash, passwordResetToken, securityStamp,
                    emailConfirmationToken, emailConfirmed, phoneNumber, phoneNumberConfirmed, twoFactorEnabled, lockoutEnd, lockoutEnabled, accessFailedCount,
                    isPrimaryAdmin, lastIp, dateCreated, lastUpdated, lastLoginDate);
        }
    }


}
