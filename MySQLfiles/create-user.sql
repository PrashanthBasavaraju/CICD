CREATE DATABASE wealth_wise;

CREATE USER 'wealthwise'@'%' IDENTIFIED BY 'wealthwise';
GRANT ALL PRIVILEGES ON wealth_wise.* TO 'wealthwise'@'%';
FLUSH PRIVILEGES;

CREATE DATABASE wealth_wise_dev;

CREATE USER 'wealthwise_dev'@'%' IDENTIFIED BY 'wealthwise_dev';
GRANT ALL PRIVILEGES ON wealth_wise_dev.* TO 'wealthwise_dev'@'%';
FLUSH PRIVILEGES;

CREATE DATABASE wealth_wise_uat;

CREATE USER 'wealthwise_uat'@'%' IDENTIFIED BY 'wealthwise_uat';
GRANT ALL PRIVILEGES ON wealth_wise_uat.* TO 'wealthwise_uat'@'%';
FLUSH PRIVILEGES;