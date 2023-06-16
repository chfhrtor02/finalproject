CREATE TABLE BoardAnswerVO (
    id NUMBER,
    content CLOB,
    createTime TIMESTAMP,
    question_id NUMBER,
    CONSTRAINT pk_board_answer PRIMARY KEY (id),
    CONSTRAINT fk_board_question FOREIGN KEY (question_id) REFERENCES BoardQuestionVO (id)
);

-- 시퀀스 생성
CREATE SEQUENCE board_answer_seq
  START WITH 1
  INCREMENT BY 1
  NOMAXVALUE
  NOCACHE
  NOCYCLE;


-- 트리거 생성
CREATE OR REPLACE TRIGGER board_answer_trigger
BEFORE INSERT ON BoardAnswerVO
FOR EACH ROW
BEGIN
  SELECT board_answer_seq.NEXTVAL INTO :NEW.id FROM dual;
END;
/
