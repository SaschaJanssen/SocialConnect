INSERT INTO CLASSIFICATIONS (CLASSIFICATION_ID,DESCRIPTION,CREATED_TS,LAST_UPDATED_TS) VALUES ('RELIABLE','good','2012-07-01 17:00:00.0','2012-07-01 17:00:00.0');
INSERT INTO CLASSIFICATIONS (CLASSIFICATION_ID,DESCRIPTION,CREATED_TS,LAST_UPDATED_TS) VALUES ('NOT_RELIABLE','bad','2012-07-01 17:00:00.0','2012-07-01 17:00:00.0');
INSERT INTO CLASSIFICATIONS (CLASSIFICATION_ID,DESCRIPTION,CREATED_TS,LAST_UPDATED_TS) VALUES ('NOT_CLASSIFIED','not crafted','2012-07-01 17:00:00.0','2012-07-01 17:00:00.0');
INSERT INTO CLASSIFICATIONS (CLASSIFICATION_ID,DESCRIPTION,CREATED_TS,LAST_UPDATED_TS) VALUES ('POSITIVE','positive','2012-07-01 17:00:00.0','2012-07-01 17:00:00.0');
INSERT INTO CLASSIFICATIONS (CLASSIFICATION_ID,DESCRIPTION,CREATED_TS,LAST_UPDATED_TS) VALUES ('NEGATIVE','negative','2012-07-01 17:00:00.0','2012-07-01 17:00:00.0');
INSERT INTO CLASSIFICATIONS (CLASSIFICATION_ID,DESCRIPTION,CREATED_TS,LAST_UPDATED_TS) VALUES ('NEUTRAL','neutral','2012-07-01 17:00:00.0','2012-07-01 17:00:00.0');

INSERT INTO LEARNING_TYPE (LEARNING_TYPE_ID,DESCRIPTION,CREATED_TS,LAST_UPDATED_TS) VALUES ('RATING','descripes if a message is positive, negative or neutral','2012-07-01 17:00:00.0','2012-07-01 17:00:00.0');
INSERT INTO LEARNING_TYPE (LEARNING_TYPE_ID,DESCRIPTION,CREATED_TS,LAST_UPDATED_TS) VALUES ('RELIABILITY','descripes if the message content is reliable or addresse a different subject','2012-07-01 17:00:00.0','2012-07-01 17:00:00.0');

INSERT INTO NETWORKS (NETWORK_ID,DESCRIPTION,CREATED_TS,LAST_UPDATED_TS) VALUES ('FACEBOOK','Facebook','2012-07-01 17:00:00.0','2012-07-01 17:00:00.0');
INSERT INTO NETWORKS (NETWORK_ID,DESCRIPTION,CREATED_TS,LAST_UPDATED_TS) VALUES ('TWITTER','Twitter','2012-07-01 17:00:00.0','2012-07-01 17:00:00.0');

INSERT INTO CONTACT_TYPE (CONTACT_TYPE_ID,DESCRIPTION,CREATED_TS,LAST_UPDATED_TS) VALUES ('FULL_NAME','','2012-07-01 17:00:00.0','2012-07-01 17:00:00.0');
INSERT INTO CONTACT_TYPE (CONTACT_TYPE_ID,DESCRIPTION,CREATED_TS,LAST_UPDATED_TS) VALUES ('POSTAL_ADDRESS','','2012-07-01 17:00:00.0','2012-07-01 17:00:00.0');
INSERT INTO CONTACT_TYPE (CONTACT_TYPE_ID,DESCRIPTION,CREATED_TS,LAST_UPDATED_TS) VALUES ('EMAIL_ADDRESS','','2012-07-01 17:00:00.0','2012-07-01 17:00:00.0');
INSERT INTO CONTACT_TYPE (CONTACT_TYPE_ID,DESCRIPTION,CREATED_TS,LAST_UPDATED_TS) VALUES ('WEB_ADDRESS','','2012-07-01 17:00:00.0','2012-07-01 17:00:00.0');
INSERT INTO CONTACT_TYPE (CONTACT_TYPE_ID,DESCRIPTION,CREATED_TS,LAST_UPDATED_TS) VALUES ('FON_NUMBER','','2012-07-01 17:00:00.0','2012-07-01 17:00:00.0');
INSERT INTO CONTACT_TYPE (CONTACT_TYPE_ID,DESCRIPTION,CREATED_TS,LAST_UPDATED_TS) VALUES ('MOBILE_NUMBER','','2012-07-01 17:00:00.0','2012-07-01 17:00:00.0');

INSERT INTO KEYWORD_TYPE (KEYWORD_TYPE_ID,DESCRIPTION,CREATED_TS,LAST_UPDATED_TS)
				   values('QUERY','direct query','2012-07-01 17:00:00.0','2012-07-01 17:00:00.0');
INSERT INTO KEYWORD_TYPE (KEYWORD_TYPE_ID,DESCRIPTION,CREATED_TS,LAST_UPDATED_TS)
				   values('HASH','Hash #','2012-07-01 17:00:00.0','2012-07-01 17:00:00.0');
INSERT INTO KEYWORD_TYPE (KEYWORD_TYPE_ID,DESCRIPTION,CREATED_TS,LAST_UPDATED_TS)
				   values('MENTIONED','mentioned @','2012-07-01 17:00:00.0','2012-07-01 17:00:00.0');

