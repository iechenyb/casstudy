select ur.* from t_user u ,t_user_role ur 
where  u.id=ur.userid  and account='add' ;

select res.url,res.name, from t_user u ,t_user_role ur ,t_role ro
left join t_role_resource re on ur.roeleid  = re.roeleid  
left join t_resource res on re.resourceid = res.id
where  u.id=ur.userid  and account='upd'
and ur.roeleid=ro.id  