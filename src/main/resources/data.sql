delete from role where id = 1;
delete from role where id = 2;
delete from role where id = 3;
delete from role where id = 4;

insert into role(id, name)  values (1, 'ROLE_ADMIN');
insert into role(id, name)  values (2, 'ROLE_USER');
insert into role(id, name)  values (3, 'ROLE_TEACHER');
insert into role(id, name)  values (4, 'ROLE_STUDENT');


-- INSERT INTO users
--     (id, address, birth_date, first_name, gender, last_name, password, personal_email, phone_number, username)
-- VALUES
--     (1, 'Tashkent', '2000-01-01', 'avazxon', 'male', 'nazirov', '1234', 'avazxonnazirov334@gmail.com', '+998999928775', 'avazxonnazirov334@gmail.com'),
--     (2, 'Tashkent', '2004-01-01', 'parizoda', 'female', 'togaeva', '1234', 'tparizoda2004@gmail.com', '+998901231805', 'tparizoda2004@gmail.com');
