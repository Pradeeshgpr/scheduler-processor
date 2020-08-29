delete from scheduler.trigger_details where id=1;
insert into scheduler.trigger_details(trigger_details.id, trigger_details.created_ts, trigger_details.expression, trigger_details.last_updated,trigger_details.name,trigger_details.status, trigger_details.priority)
value(1, NOW(),'10 0 0 ? * *',NOW(),'test','NOT_PROVISIONED', 10);
delete from scheduler.trigger_details where id=2;
insert into scheduler.trigger_details(trigger_details.id, trigger_details.created_ts, trigger_details.expression, trigger_details.last_updated,trigger_details.name,trigger_details.status, trigger_details.priority)
value(2, NOW(),'10 0 0 ? * *',NOW(),'test','NOT_PROVISIONED',10);
delete from scheduler.trigger_details where id=3;
insert into scheduler.trigger_details(trigger_details.id, trigger_details.created_ts, trigger_details.expression, trigger_details.last_updated,trigger_details.name,trigger_details.status, trigger_details.priority)
value(3, NOW(),'10 0 0 ? * *',NOW(),'test','NOT_PROVISIONED',10);
delete from scheduler.trigger_details where id=4;
insert into scheduler.trigger_details(trigger_details.id, trigger_details.created_ts, trigger_details.expression, trigger_details.last_updated,trigger_details.name,trigger_details.status, trigger_details.priority)
value(4, NOW(),'10 0 0 ? * *',NOW(),'test','PROVISIONED',10);