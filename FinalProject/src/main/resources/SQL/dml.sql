<<<<<<< HEAD
SELECT case when count(*) = 1 then 'true'
       else 'false'
       end as result
=======
SELECT case when count(*) = 1 then 'true'
       else 'false'
       end as result
>>>>>>> origin/master
FROM member_tbl WHERE id='testId';