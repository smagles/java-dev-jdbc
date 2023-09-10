insert into `residents` (name, member_id, flat_id)
select m.name, m.id as member_id, m.flat_id
from members m
order by RAND()
LIMIT 50;
