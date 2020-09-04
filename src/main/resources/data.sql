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
