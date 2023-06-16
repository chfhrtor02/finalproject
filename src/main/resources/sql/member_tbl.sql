
id 를 number로 변경 후 시퀀스 적용 = Repository 에서 Long 타입으로 변경적용 
id =  회원일련번호
userId = 회워가입 id



CREATE SEQUENCE member_seq
  START WITH 1
  INCREMENT BY 1
  NOCACHE
  NOCYCLE;

CREATE OR REPLACE TRIGGER member_trg
BEFORE INSERT ON member_tbl
FOR EACH ROW
BEGIN
  SELECT member_seq.NEXTVAL INTO :NEW.id FROM dual;
END;


CREATE TABLE member_tbl (
  id NUMBER PRIMARY KEY,
  userID VARCHAR2(20) UNIQUE,
  pw VARCHAR2(20) NOT NULL,
  name VARCHAR2(100 CHAR),
  gender CHAR,
  email VARCHAR2(50 CHAR) NOT NULL,
  mobile VARCHAR2(13 CHAR) NOT NULL,
  address1 VARCHAR2(300 CHAR),
  address2 VARCHAR2(300 CHAR),
  birthday DATE,
  joindate DATE DEFAULT SYSDATE
);




ALTER TABLE member_tbl
MODIFY gender CHAR(1) NULL;
commit;

CREATE OR REPLACE TRIGGER member_trg
BEFORE INSERT ON member_tbl
FOR EACH ROW
BEGIN
  SELECT member_seq.NEXTVAL INTO :NEW.id FROM dual;
END;




CREATE SEQUENCE hibernate_sequence
  START WITH 1
  INCREMENT BY 1
  NOCACHE;