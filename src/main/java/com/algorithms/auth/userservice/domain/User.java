package com.algorithms.auth.userservice.domain;

import com.algorithms.auth.userservice.domain.draft.UserDraft;
import com.algorithms.auth.userservice.domain.enums.Gender;
import com.algorithms.auth.userservice.domain.enums.Locale;

import java.util.Date;

public class User extends UserDraft {
    private final long id;


    public User(long id, long companyId, String firstName, String middleName, String lastName, String email, String username,
                Gender gender, Locale locale, String passwordHash, String passwordResetToken, String securityStamp,
                String emailConfirmationToken, boolean emailConfirmed, String phoneNumber, boolean phoneNumberConfirmed,
                boolean twoFactorEnabled, Date lockoutEnd, boolean lockoutEnabled, int accessFailedCount, boolean isPrimaryAdmin,
                String lastIp, Date dateCreated, Date lastUpdated, Date lastLoginDate) {
        super(companyId, firstName, middleName, lastName, email, username, gender, locale, passwordHash, passwordResetToken, securityStamp,
                emailConfirmationToken, emailConfirmed, phoneNumber, phoneNumberConfirmed, twoFactorEnabled, lockoutEnd, lockoutEnabled, accessFailedCount,
                isPrimaryAdmin, lastIp, dateCreated, lastUpdated, lastLoginDate);
        this.id = id;
    }


    public User(long id, UserDraft draft) {
        super(draft.getCompanyId(), draft.getFirstName(), draft.getMiddleName(), draft.getLastName(), draft.getEmail(), draft.getUsername(),
                draft.getGender(), draft.getLocale(), draft.getPasswordHash(), draft.getPasswordResetToken(), draft.getSecurityStamp(),
                draft.getEmailConfirmationToken(), draft.isEmailConfirmed(), draft.getPhoneNumber(), draft.isPhoneNumberConfirmed(),
                draft.isTwoFactorEnabled(), draft.getLockoutEnd(), draft.isLockoutEnabled(), draft.getAccessFailedCount(), draft.isPrimaryAdmin(),
                draft.getLastIp(), draft.getDateCreated(), draft.getLastUpdated(), draft.getLastLoginDate());
        this.id = id;
    }

    public long getId() {
        return id;
    }

    @Override
    public Builder draft() {
        return new Builder(this);
    }
}
