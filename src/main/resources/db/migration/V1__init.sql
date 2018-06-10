
CREATE TABLE `company` (
    `id` bigint(20) unsigned NOT NULL AUTO_INCREMENT,
    `name` nvarchar(255) NOT NULL,
    `date_created` datetime NOT NULL,
    PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;


CREATE TABLE `account` (
    `id` bigint(20) unsigned NOT NULL AUTO_INCREMENT,
    `company_id` bigint(20) unsigned NOT NULL,
    `name` nvarchar(255) NOT NULL,
    `date_added` datetime NOT NULL,
    `required_length` int(11) NOT NULL DEFAULT '0',
    `require_non_letter_or_digit` bit(1) NOT NULL DEFAULT b'0',
    `require_digit` bit(1) NOT NULL DEFAULT b'0',
    `require_lowercase` bit(1) NOT NULL DEFAULT b'0',
    `require_uppercase` bit(1) NOT NULL DEFAULT b'0',
    PRIMARY KEY (`id`),
    KEY `fk_account_company` (`company_id`),
    CONSTRAINT `fk_account_company` FOREIGN KEY (`company_id`) REFERENCES `company` (`id`) ON DELETE CASCADE
) ENGINE=InnoDB DEFAULT CHARSET=utf8;


CREATE TABLE `client` (
    `id` bigint(20) unsigned NOT NULL AUTO_INCREMENT,
    `account_id` bigint(20) unsigned NOT NULL,
    `name` varchar(255) NOT NULL,
    `client_id` varchar(255) NOT NULL,
    `client_secret` varchar(255) NOT NULL,
    `client_type` varchar(255) NOT NULL,
    `jwt_expiration` bigint(20) NOT NULL,
    `use_username_password_authentication` bit(1) NOT NULL DEFAULT b'0',
    `date_added` datetime NOT NULL,
    `description` longtext,
    PRIMARY KEY (`id`),
    KEY `fk_client_account` (`account_id`),
    CONSTRAINT `fk_client_account` FOREIGN KEY (`account_id`) REFERENCES `account` (`id`) ON DELETE CASCADE
) ENGINE=InnoDB DEFAULT CHARSET=utf8;


CREATE TABLE `user` (
    `id` bigint(20) unsigned NOT NULL AUTO_INCREMENT,
    `company_id` bigint(20) unsigned NOT NULL,
    `first_name` varchar(255) DEFAULT NULL,
    `middle_name` varchar(255) DEFAULT NULL,
    `last_name` varchar(255) DEFAULT NULL,
    `email` varchar(255) DEFAULT NULL,
    `username` varchar(255) DEFAULT NULL,
    `gender` varchar(15) DEFAULT NULL,
    `locale` varchar(50) NOT NULL,
    `password_hash` varchar(255) DEFAULT NULL,
    `password_reset_token` varchar(255) DEFAULT NULL,
    `security_stamp` varchar(255) DEFAULT NULL,
    `email_confirmation_token` varchar(255) DEFAULT NULL,
    `email_confirmed` bit(1) NOT NULL,
    `phone_number` varchar(255) DEFAULT NULL,
    `phone_number_confirmed` bit(1) NOT NULL DEFAULT b'0',
    `two_factor_enabled` bit(1) NOT NULL DEFAULT b'0',
    `lockout_end` datetime DEFAULT NULL,
    `lockout_enabled` bit(1) NOT NULL DEFAULT b'0',
    `access_failed_count` int(11) NOT NULL,
    `is_primary_admin` bit(1) NOT NULL DEFAULT b'0',
    `last_ip` varchar(50) DEFAULT NULL,
    `date_created` datetime NOT NULL,
    `last_updated` datetime NOT NULL,
    `last_login_date` datetime NOT NULL,
    PRIMARY KEY (`id`),
    KEY `fk_user_company` (`company_id`),
    CONSTRAINT `fk_user_company` FOREIGN KEY (`company_id`) REFERENCES `company` (`id`) ON DELETE CASCADE
) ENGINE=InnoDB DEFAULT CHARSET=utf8;


CREATE TABLE `allowed_url` (
    `id` bigint(20) unsigned NOT NULL AUTO_INCREMENT,
    `client_id` bigint(20) unsigned NOT NULL,
    `url` varchar(255) NOT NULL,
    `url_type` varchar(50) NOT NULL,
    PRIMARY KEY (`id`),
    KEY `fk_allowed_url_client` (`client_id`),
    CONSTRAINT `fk_allowed_url_client` FOREIGN KEY (`client_id`) REFERENCES `client` (`id`) ON DELETE CASCADE
) ENGINE=InnoDB DEFAULT CHARSET=utf8;


CREATE TABLE `identity` (
    `id` bigint(20) unsigned NOT NULL AUTO_INCREMENT,
    `user_id` bigint(20) unsigned NOT NULL,
    `provider` varchar(255) NOT NULL,
    `provider_id` varchar(50) NOT NULL,
    PRIMARY KEY (`id`),
    KEY `fk_identity_user` (`user_id`),
    CONSTRAINT `fk_identity_user` FOREIGN KEY (`user_id`) REFERENCES `user` (`id`) ON DELETE CASCADE
) ENGINE=InnoDB DEFAULT CHARSET=utf8;


DELIMITER $$
CREATE PROCEDURE sp_delete_company(IN companyId BIGINT(20) UNSIGNED)
BEGIN
    DELETE FROM `allowed_url` WHERE client_id IN (SELECT id FROM client WHERE account_id IN (SELECT id FROM account WHERE company_id = companyId));
    DELETE FROM `user` WHERE company_id = companyId;
    DELETE FROM `client` WHERE account_id IN (SELECT id FROM account WHERE company_id = companyId);
    DELETE FROM `account` WHERE company_id = companyId;
    DELETE FROM `company` WHERE id = companyId;
END$$
DELIMITER ;


DELIMITER $$
CREATE PROCEDURE sp_delete_account(IN accountId BIGINT(20) UNSIGNED)
BEGIN
    DELETE FROM `allowed_url` WHERE client_id IN (SELECT id FROM `client` WHERE account_id = accountId);
    DELETE FROM `client` WHERE account_id = accountId;
    DELETE FROM `account` WHERE id = accountId;
END$$
DELIMITER ;


DELIMITER $$
CREATE PROCEDURE sp_delete_client(IN clientId BIGINT(20) UNSIGNED)
BEGIN
    DELETE FROM `allowed_url` WHERE client_id = clientId;
    DELETE FROM `client` WHERE id = clientId;
END$$
DELIMITER ;


DELIMITER $$
CREATE PROCEDURE sp_delete_user(IN userId BIGINT(20) UNSIGNED)
BEGIN
    DELETE FROM `identity` WHERE user_id = userId;
    DELETE FROM `user` WHERE id = userId;
END$$
DELIMITER ;