-- DROP TABLE MESSAGES;
-- DROP TABLE CLASSIFICATIONS;
-- DROP TABLE KEYWORDS;
-- DROP TABLE KEYWORD_TYPE;
-- DROP TABLE NETWORKS;
-- DROP TABLE CLASSIFICATIONS;
-- DROP TABLE CUSTOMER_CONTACT;
-- DROP TABLE CUSTOMERS;
-- DROP TABLE CONTACT;
-- DROP TABLE CONTACT_TYPE;

CREATE TABLE CLASSIFICATION_TYPE (
	CLASSIFICATION_TYPE_ID VARCHAR(20) NOT NULL,
	DESCRIPTION VARCHAR(80),
	CREATED_TS TIMESTAMP NOT NULL,
	LAST_UPDATED_TS TIMESTAMP,
	CONSTRAINT PK_clt PRIMARY KEY (CLASSIFICATION_TYPE_ID)
);

CREATE TABLE CLASSIFICATIONS (
	CLASSIFICATION_ID VARCHAR(20) NOT NULL,
	CLASSIFICATION_TYPE_ID VARCHAR(20) NOT NULL,
	DESCRIPTION VARCHAR(80),
	CREATED_TS TIMESTAMP NOT NULL,
	LAST_UPDATED_TS TIMESTAMP,
	CONSTRAINT PK_cs PRIMARY KEY (CLASSIFICATION_ID),
	CONSTRAINT FK_cl_clt
     FOREIGN KEY (CLASSIFICATION_TYPE_ID)
     REFERENCES CLASSIFICATION_TYPE(CLASSIFICATION_TYPE_ID)
);

CREATE TABLE LEARNING_DATA (
	LEARNING_ID INT NOT NULL,
	CLASSIFICATION_TYPE_ID VARCHAR(20) NOT NULL,
	LEARNING_DATA VARCHAR(1024) NOT NULL,
	CLASSIFICATION_ID VARCHAR(20) NOT NULL,
	CREATED_TS TIMESTAMP NOT NULL,
	LAST_UPDATED_TS TIMESTAMP,
	CONSTRAINT PK_ld PRIMARY KEY (LEARNING_ID),
	CONSTRAINT FK_ld_clt
     FOREIGN KEY (CLASSIFICATION_TYPE_ID)
     REFERENCES CLASSIFICATION_TYPE(CLASSIFICATION_TYPE_ID),
    CONSTRAINT FK_ld_c
     FOREIGN KEY (CLASSIFICATION_ID)
     REFERENCES CLASSIFICATIONS(CLASSIFICATION_ID)
);

CREATE TABLE CONTACT_TYPE(
	CONTACT_TYPE_ID VARCHAR(20) NOT NULL,
	DESCRIPTION VARCHAR(80),
	CREATED_TS TIMESTAMP NOT NULL,
	LAST_UPDATED_TS TIMESTAMP,
	CONSTRAINT PK_contTyp PRIMARY KEY (CONTACT_TYPE_ID)
);

CREATE TABLE CUSTOMERS(
	CUSTOMER_ID INT NOT NULL,
	LAST_NETWORK_ACCESS TIMESTAMP NULL,
	CREATED_TS TIMESTAMP NOT NULL,
	LAST_UPDATED_TS TIMESTAMP,
	CONSTRAINT PK_cust PRIMARY KEY (CUSTOMER_ID)
);

CREATE TABLE CONTACT(
	CONTACT_ID INT NOT NULL,
	CONTACT_TYPE_ID VARCHAR(20) NOT NULL,
	INFO_STRING VARCHAR(200),
	CREATED_TS TIMESTAMP NOT NULL,
	LAST_UPDATED_TS TIMESTAMP,
	CONSTRAINT PK_cont PRIMARY KEY (CONTACT_ID),
	CONSTRAINT FK_cont_typ
     FOREIGN KEY (CONTACT_TYPE_ID)
     REFERENCES CONTACT_TYPE(CONTACT_TYPE_ID)
);

CREATE TABLE CUSTOMER_CONTACT(
	CUSTOMER_ID INT NOT NULL,
	CONTACT_ID INT NOT NULL,
	CREATED_TS TIMESTAMP NOT NULL,
	LAST_UPDATED_TS TIMESTAMP,
	CONSTRAINT PK_cust_cont PRIMARY KEY (CONTACT_ID,CUSTOMER_ID),
	CONSTRAINT FK_cc_cust
     FOREIGN KEY (CUSTOMER_ID)
     REFERENCES CUSTOMERS(CUSTOMER_ID),
    CONSTRAINT FK_cc_cont
     FOREIGN KEY (CONTACT_ID)
     REFERENCES CONTACT(CONTACT_ID)
);

CREATE TABLE NETWORKS(
	NETWORK_ID VARCHAR(20) NOT NULL,
	DESCRIPTION VARCHAR(80),
	CREATED_TS TIMESTAMP NOT NULL,
	LAST_UPDATED_TS TIMESTAMP,
	CONSTRAINT PK_net PRIMARY KEY (NETWORK_ID)
);

CREATE TABLE KEYWORD_TYPE(
	KEYWORD_TYPE_ID VARCHAR(20) NOT NULL,
	DESCRIPTION VARCHAR(80),
	CREATED_TS TIMESTAMP NOT NULL,
	LAST_UPDATED_TS TIMESTAMP,
	CONSTRAINT PK_kwT PRIMARY KEY (KEYWORD_TYPE_ID)
);

CREATE TABLE KEYWORDS(
	KEYWORD_TYPE_ID VARCHAR(20) NOT NULL,
	KEYWORD VARCHAR(256) NOT NULL,
	CUSTOMER_ID INT NOT NULL,
	NETWORK_ID VARCHAR(20) NOT NULL,
	CREATED_TS TIMESTAMP NOT NULL,
	LAST_UPDATED_TS TIMESTAMP,
	CONSTRAINT PK_kw PRIMARY KEY (CUSTOMER_ID,KEYWORD_TYPE_ID,NETWORK_ID),
	CONSTRAINT FK_kw_cust
     FOREIGN KEY (CUSTOMER_ID)
     REFERENCES CUSTOMERS(CUSTOMER_ID),
    CONSTRAINT FK_kw_net
     FOREIGN KEY (NETWORK_ID)
     REFERENCES NETWORKS(NETWORK_ID),
    CONSTRAINT FK_kw_type
     FOREIGN KEY (KEYWORD_TYPE_ID)
     REFERENCES KEYWORD_TYPE(KEYWORD_TYPE_ID)
);

CREATE TABLE MESSAGES(
     MESSAGE_ID INT NOT NULL,
     MESSAGE TEXT,
     NETWORK_USER_ID VARCHAR(128),
     LANGUAGE VARCHAR(5),
     GEO_LOCATION VARCHAR(50),
     NETWORK_ID VARCHAR(20) NOT NULL,
     RELIABILITY_ID VARCHAR(20) NOT NULL,
     SENTIMENT_ID VARCHAR(20) NOT NULL,
     NETWORK_USER VARCHAR(80),
     NETWORK_MESSAGE_DATE TIMESTAMP,
     MESSAGE_RECEIVED_DATE TIMESTAMP,
     CUSTOMER_ID INT NOT NULL,
     NETWORK_USER_RATING VARCHAR(6),
     CREATED_TS TIMESTAMP NOT NULL,
	 LAST_UPDATED_TS TIMESTAMP,
     CONSTRAINT PK_msg PRIMARY KEY (MESSAGE_ID),
     CONSTRAINT FK_msg_cust
     FOREIGN KEY (CUSTOMER_ID)
     REFERENCES CUSTOMERS(CUSTOMER_ID),
     CONSTRAINT FK_m_cl
     FOREIGN KEY (RELIABILITY_ID)
     REFERENCES CLASSIFICATIONS(CLASSIFICATION_ID),
     CONSTRAINT FK_m_cl_2
     FOREIGN KEY (SENTIMENT_ID)
     REFERENCES CLASSIFICATIONS(CLASSIFICATION_ID),
     CONSTRAINT FK_msg_net
     FOREIGN KEY (NETWORK_ID)
     REFERENCES NETWORKS(NETWORK_ID)
);