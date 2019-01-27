generate_series('2017-02-20', '2017-02-25', '1 day'::INTERVAL)::DATE

select count(button_click_events.id) from (select generate_series('2017-02-20', '2017-02-25', '1 day'::INTERVAL)::DATE
as day) as days left join button_click_events on days.day=date_trunc('day', button_click_events.created_at) and created_at >= '2017-02-20'
group by day;

generate_series('2017-02-20', '2017-02-25', '1 day'::INTERVAL)::DATE AS date_day
