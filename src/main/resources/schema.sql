/*
Ingredient—Holds ingredient information
Taco—Holds essential information about a taco design
Taco_Ingredients—Contains one or more rows for each row in Taco, mapping
the taco to the ingredients for that taco
Taco_Order—Holds essential order details
Taco_Order_Tacos—Contains one or more rows for each row in Taco_Order,
mapping the order to the tacos in the order
 */
create table if not exists Ingredient (
  id varchar(4) not null,
  name varchar(25) not null,
  type varchar(10) not null
);
create table if not exists Taco (
  id identity,
  name varchar(50) not null,
  createdAt timestamp not null
);
create table if not exists Taco_Ingredients (
  taco bigint not null,
  ingredient varchar(4) not null
);

/*
 links tables Taco with Ingredient into Taco_Ingredients
 */
alter table Taco_Ingredients
    add foreign key (taco) references Taco(id);
alter table Taco_Ingredients
    add foreign key (ingredient) references Ingredient(id);

create table if not exists Taco_Order (
  id identity,
    deliveryName varchar(50) not null,
    deliveryStreet varchar(50) not null,
    deliveryCity varchar(50) not null,
    deliveryState varchar(2) not null,
    deliveryZip varchar(10) not null,
    ccNumber varchar(16) not null,
    ccExpiration varchar(5) not null,
    ccCVV varchar(3) not null,
    placedAt timestamp not null
);
create table if not exists Taco_Order_Tacos (
  tacoOrder bigint not null,
  taco bigint not null
);

/*
links tables Taco_Order and Taco into Taco_Order_Tacos
 */
alter table Taco_Order_Tacos
    add foreign key (tacoOrder) references Taco_Order(id);
alter table Taco_Order_Tacos
    add foreign key (taco) references Taco(id);