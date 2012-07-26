INSERT INTO CLASSIFICATION_TYPE (CLASSIFICATION_TYPE_ID,DESCRIPTION,CREATED_TS,LAST_UPDATED_TS) VALUES ('SENTIMENT','descripes if a message is positive, negative or neutral','2012-07-01 17:00:00.0','2012-07-01 17:00:00.0');
INSERT INTO CLASSIFICATION_TYPE (CLASSIFICATION_TYPE_ID,DESCRIPTION,CREATED_TS,LAST_UPDATED_TS) VALUES ('RELIABILITY','descripes if the message content is reliable or addresse a different subject','2012-07-01 17:00:00.0','2012-07-01 17:00:00.0');

INSERT INTO CLASSIFICATIONS (CLASSIFICATION_ID,CLASSIFICATION_TYPE_ID,DESCRIPTION,CREATED_TS,LAST_UPDATED_TS) VALUES ('RELIABLE','RELIABILITY','good','2012-07-01 17:00:00.0','2012-07-01 17:00:00.0');
INSERT INTO CLASSIFICATIONS (CLASSIFICATION_ID,CLASSIFICATION_TYPE_ID,DESCRIPTION,CREATED_TS,LAST_UPDATED_TS) VALUES ('NOT_RELIABLE','RELIABILITY','bad','2012-07-01 17:00:00.0','2012-07-01 17:00:00.0');
INSERT INTO CLASSIFICATIONS (CLASSIFICATION_ID,CLASSIFICATION_TYPE_ID,DESCRIPTION,CREATED_TS,LAST_UPDATED_TS) VALUES ('NOT_CLASSIFIED','RELIABILITY','not crafted','2012-07-01 17:00:00.0','2012-07-01 17:00:00.0');
INSERT INTO CLASSIFICATIONS (CLASSIFICATION_ID,CLASSIFICATION_TYPE_ID,DESCRIPTION,CREATED_TS,LAST_UPDATED_TS) VALUES ('POSITIVE','SENTIMENT','positive','2012-07-01 17:00:00.0','2012-07-01 17:00:00.0');
INSERT INTO CLASSIFICATIONS (CLASSIFICATION_ID,CLASSIFICATION_TYPE_ID,DESCRIPTION,CREATED_TS,LAST_UPDATED_TS) VALUES ('NEGATIVE','SENTIMENT','negative','2012-07-01 17:00:00.0','2012-07-01 17:00:00.0');
INSERT INTO CLASSIFICATIONS (CLASSIFICATION_ID,CLASSIFICATION_TYPE_ID,DESCRIPTION,CREATED_TS,LAST_UPDATED_TS) VALUES ('NEUTRAL','SENTIMENT','neutral','2012-07-01 17:00:00.0','2012-07-01 17:00:00.0');


INSERT INTO NETWORKS (NETWORK_ID,DESCRIPTION,CREATED_TS,LAST_UPDATED_TS) VALUES ('FACEBOOK','Facebook','2012-07-01 17:00:00.0','2012-07-01 17:00:00.0');
INSERT INTO NETWORKS (NETWORK_ID,DESCRIPTION,CREATED_TS,LAST_UPDATED_TS) VALUES ('TWITTER','Twitter','2012-07-01 17:00:00.0','2012-07-01 17:00:00.0');
INSERT INTO NETWORKS (NETWORK_ID,DESCRIPTION,CREATED_TS,LAST_UPDATED_TS) VALUES ('YELP','Yelp','2012-07-01 17:00:00.0','2012-07-01 17:00:00.0');
INSERT INTO NETWORKS (NETWORK_ID,DESCRIPTION,CREATED_TS,LAST_UPDATED_TS) VALUES ('QYPE','Qype','2012-07-01 17:00:00.0','2012-07-01 17:00:00.0');
INSERT INTO NETWORKS (NETWORK_ID,DESCRIPTION,CREATED_TS,LAST_UPDATED_TS) VALUES ('OPENTABLE','OpenTable','2012-07-01 17:00:00.0','2012-07-01 17:00:00.0');

INSERT INTO CONTACT_TYPE (CONTACT_TYPE_ID,DESCRIPTION,CREATED_TS,LAST_UPDATED_TS) VALUES ('FULL_NAME','','2012-07-01 17:00:00.0','2012-07-01 17:00:00.0');
INSERT INTO CONTACT_TYPE (CONTACT_TYPE_ID,DESCRIPTION,CREATED_TS,LAST_UPDATED_TS) VALUES ('POSTAL_ADDRESS','','2012-07-01 17:00:00.0','2012-07-01 17:00:00.0');
INSERT INTO CONTACT_TYPE (CONTACT_TYPE_ID,DESCRIPTION,CREATED_TS,LAST_UPDATED_TS) VALUES ('EMAIL_ADDRESS','','2012-07-01 17:00:00.0','2012-07-01 17:00:00.0');
INSERT INTO CONTACT_TYPE (CONTACT_TYPE_ID,DESCRIPTION,CREATED_TS,LAST_UPDATED_TS) VALUES ('WEB_ADDRESS','','2012-07-01 17:00:00.0','2012-07-01 17:00:00.0');
INSERT INTO CONTACT_TYPE (CONTACT_TYPE_ID,DESCRIPTION,CREATED_TS,LAST_UPDATED_TS) VALUES ('FON_NUMBER','','2012-07-01 17:00:00.0','2012-07-01 17:00:00.0');
INSERT INTO CONTACT_TYPE (CONTACT_TYPE_ID,DESCRIPTION,CREATED_TS,LAST_UPDATED_TS) VALUES ('MOBILE_NUMBER','','2012-07-01 17:00:00.0','2012-07-01 17:00:00.0');

INSERT INTO KEYWORD_TYPE (KEYWORD_TYPE_ID,DESCRIPTION,CREATED_TS,LAST_UPDATED_TS)
				   values('PAGE','direct page access','2012-07-01 17:00:00.0','2012-07-01 17:00:00.0');
INSERT INTO KEYWORD_TYPE (KEYWORD_TYPE_ID,DESCRIPTION,CREATED_TS,LAST_UPDATED_TS)
				   values('QUERY','direct query','2012-07-01 17:00:00.0','2012-07-01 17:00:00.0');
INSERT INTO KEYWORD_TYPE (KEYWORD_TYPE_ID,DESCRIPTION,CREATED_TS,LAST_UPDATED_TS)
				   values('HASH','Hash #','2012-07-01 17:00:00.0','2012-07-01 17:00:00.0');
INSERT INTO KEYWORD_TYPE (KEYWORD_TYPE_ID,DESCRIPTION,CREATED_TS,LAST_UPDATED_TS)
				   values('MENTIONED','mentioned @','2012-07-01 17:00:00.0','2012-07-01 17:00:00.0');

