-- BoardQuestionVO 테이블 생성
CREATE TABLE BoardQuestionVO (
    id NUMBER,
    subject VARCHAR2(400),
    content CLOB,
    createDate TIMESTAMP,
    CONSTRAINT pk_board_question PRIMARY KEY (id)
);

-- 시퀀스 생성
CREATE SEQUENCE board_question_seq
  START WITH 1
  INCREMENT BY 1
  NOMAXVALUE
  NOCACHE
  NOCYCLE;
  
-- 트리거 생성
CREATE OR REPLACE TRIGGER board_question_trigger
BEFORE INSERT ON BoardQuestionVO
FOR EACH ROW
BEGIN
  SELECT board_question_seq.NEXTVAL INTO :NEW.id FROM dual;
END;
/