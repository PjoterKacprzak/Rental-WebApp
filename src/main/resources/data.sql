INSERT into roles (id,name) values
(1,'ROLE_ADMIN'),
(2,'ROLE_ACTUATOR'),
(3,'ROLE_USER');





insert into clients(id,username,password, email ) values
(1,'test','$2a$10$hdGBqr5VZVM15rtAIdHweuvL.cZrrKzljGbl49iVVRuNZLBkOIxDO','test@gmail.com'),
(2,'test2','$2a$10$hdGBqr5VZVM15rtAIdHweuvL.cZrrKzljGbl49iVVRuNZLBkOIxDO','test2@gmail.com'),
(3,'admin','$2a$10$hdGBqr5VZVM15rtAIdHweuvL.cZrrKzljGbl49iVVRuNZLBkOIxDO','admin@gmail.com'),
(4,'admin2','$2a$10$hdGBqr5VZVM15rtAIdHweuvL.cZrrKzljGbl49iVVRuNZLBkOIxDO','admin2@gmail.com');



insert into clients_roles(user_id,role_id) values
(1,3),
(2,3),
(3,1),
(4,1);




insert into equipments(id,eq_name,eq_type,eq_mark,price,quantity) values
(1,'excavator 453T','excavator','volvo',33.12,2),
(2,'excavator RTDFG445','excavator','renault',30.4,1),
(3,'roller BP/34','roller','volvo',19.3,4),
(4,'roller TJG/44','roller','renault',33.3,1),
(5,'mixer WOW','mixer','stihl',10.12,2),
(6,'mixer BiggestWOW','mixer','stihl',5.12,2),
(10,'generator SS-Power','generator', 'sumera',78,5),
(11,'generator Deb2','generator', 'stanley',84.32,7),
(12,'forklift 21-C','forklift', 'hyster',114.25,12),
(13,'forklift 72','forklift', 'linde',140.00,11),
(14,'jackhammer daisy','jackhammer', 'makita',45.00,18),
(15,'jackhammer C4ZYG4Y','jackhammer', 'bosch',65.50,24);



