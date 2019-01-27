DELIMITER //
CREATE PROCEDURE test(ip_num bigint unsigned)
BEGIN
    SELECT * FROM ip_cities where loc_id=(select value from ip_city_indexer where ip_num between lower and upper order by lower desc limit 1) order by loc_id desc limit 1; 
END //

DELIMITER //
CREATE FUNCTION ip_to_num(ip text)
    RETURNS bigint unsigned
    DETERMINISTIC
BEGIN
    DECLARE num bigint unsigned default 0;
    DECLARE counter int unsigned default 1;
    
    WHILE counter <= 4 do
        SET num = num * 256;
        SET num = num + CAST(SUBSTRING_INDEX(SUBSTRING_INDEX(ip, '.', counter), '.', -1) as unsigned integer);
        SET counter = counter + 1;
    END WHILE;
    
    RETURN (num);
END //
DELIMITER ;
	

CREATE PROCEDURE locate_ip_num(ip_num bigint unsigned)
BEGIN
   SELECT * FROM ip_cities where loc_id=(select value from ip_city_indexer where ip_num between lower and upper order by lower desc limit 1) order by loc_id desc limit 1; 
END


UPDATE `mysql`.`proc`
SET name = 'locate_ip_num',
specific_name = 'locate_ip_num'
WHERE db = 'haddock' AND
  name = 'test';
                

CREATE VIEW unique_visitors AS select ip AS ip, 
count(*) AS visits, min(visit_at) AS first_visit, 
max(visit_at) AS last_visit, 
timediff(max(visit_at), min(visit_at)) AS time_spent, 
country AS country, region AS region, city AS city, 
org AS org from visitors group by ip order by count(*);

CREATE VIEW visitors_per_minute AS 
select sec_to_time((floor((time_to_sec(visit_at) / 60)) * 60)) AS minute, 
count(*) AS visitors from visitors group by minute;

CREATE VIEW visitors_per_hour AS 
select sec_to_time((floor((time_to_sec(visit_at) / 3600)) * 3600)) AS hour, 
count(*) AS visitors from visitors group by hour;

select id, visit_at, substring(curse, 1, 40), ip, 
concat_ws('-', country, city), substring(org, instr(org,' ')+1, 30) 
from visitors;

select ip, visits, first_visit, last_visit, time_spent, 
concat_ws('-', country, city), substring(org, instr(org,' ')+1, 40) 
from unique_visitors;

select minutes.minute as minute, 
    ifnull(visitors_per_minute.visitors, 0) as visits 
    from minutes left join visitors_per_minute 
    on (minutes.minute=visitors_per_minute.minute);

create view visitors_per_minute_fixed as 
    select minutes.minute as minute,  
    ifnull(visitors_per_minute.visitors, 0) as visits  
    from minutes left join visitors_per_minute  
    on (minutes.minute=visitors_per_minute.minute);








