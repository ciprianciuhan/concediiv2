drop table concedii;
drop table angajati;
drop table departamente;

create table departamente(
id_departament int(3),
nume_departament varchar(20) not null,
id_manager int(3) ,
oras varchar(20) not null,
constraint pk_departament primary key(id_departament)
);

create table angajati(
id_angajat int(3),
prenume varchar(20) not null,
nume varchar(20) not null,
id_departament int(3) not null,
CNP varchar(13) not null,
constraint fk_departament foreign key(id_departament) references departamente(id_departament) on delete cascade,
constraint pk_angajat primary key(id_angajat)
);

create table concedii(
id_concediu int(3),
data_incepere date not null,
data_finalizare date not null,
id_angajat int(3) not null,
constraint fk_angajat foreign key(id_angajat) references angajati(id_angajat) on delete cascade,
constraint pk_concediu primary key(id_concediu),
constraint Data_gresita check (data_incepere<data_finalizare)
);

insert into departamente values (100,'HR', 510,'Bucuresti');
insert into departamente values(200,'IT',520, 'Bucuresti');
insert into departamente values(300,'Sales',530,'Copenhaga');
insert into departamente values(400,'Payroll',540,'Timisoara');
insert into departamente values(500,'Customer Support',550,'Mumbai');

insert into angajati values(510,'Ciprian','Cucu',100,'1910824156561');
insert into angajati values(511,'Sabina','Munteanu',100,'5030120380676');
insert into angajati values(512,'Valentin','Popescu',100,'1900122187283');
insert into angajati values(513,'Dan','Ionescu',100,'2900602401156');
insert into angajati values(514,'Nicolae','Protopopescu',100,'2900122194068');

insert into angajati values(520,'Ionut-Alexandru','Craciun',200,'2970611291461');
insert into angajati values(521,'Ruxandra','Nicolau',200,'2900507223446');
insert into angajati values(522,'Andrei','Cerbulescu',200,'1911128197446');
insert into angajati values(523,'Ciprian','Roaba',200,'5010606394109');
insert into angajati values(524,'Mitica','Georgescu',200,'5001107224549');

insert into angajati values(530,'Nicholas','Klaus',300,'5020209328940');
insert into angajati values(531,'Ludwig','Larsen',300,'2980729110042');
insert into angajati values(532,'Asger','Rosander',300,'2951223107971');
insert into angajati values(533,'Mihai','Popescu',300,'6000313320671');
insert into angajati values(534,'Lukas','Rasmunssen',300,'2890319015681');

insert into angajati values(540,'Andrei-Liviu','Iliescu',400,'5021119054178');
insert into angajati values(541,'Miruna','Ciuca',400,'2931111425668');
insert into angajati values(542,'Geanina','Rosca',400,'5010504389515');
insert into angajati values(543,'Stefan','Cernea',400,'1870110390471');
insert into angajati values(544,'Rebeca','Dumitru',400,'5031018142421');

insert into angajati values(550,'Rajni','Kar',500,'2951213017303');
insert into angajati values(551,'Aparna','Sasha',500,'2911121515832');
insert into angajati values(552,'Reyamsh','Chaudhari',500,'2970331262494');
insert into angajati values(553,'Kalyani','Kapoor',500,'1890423165219');
insert into angajati values(554,'Chetan','Bandi',500,'2890406272101');


insert into concedii values(200,'2020-05-05','2020-05-08',511);
insert into concedii values(201,'2020-04-05','2020-04-08',511);
insert into concedii values(202,'2020-06-07','2020-06-14',522);
insert into concedii values(203,'2020-01-01','2020-01-18',523);
insert into concedii values(204,'2020-01-02','2020-01-04',550);
insert into concedii values(205,'2020-06-07','2020-06-18',550);
insert into concedii values(206,'2020-11-07','2020-12-18',520);
insert into concedii values(207,'2020-03-03','2020-04-01',522);
insert into concedii values(208,'2020-07-07','2020-07-18',554);
insert into concedii values(209,'2020-08-27','2020-09-05',550);
insert into concedii values(210,'2020-02-13','2020-02-21',550);
insert into concedii values(211,'2020-06-08','2020-06-12',551);

select * from departamente;
select * from angajati;
select * from concedii;
