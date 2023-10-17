create schema if not exists photos;
create table photos.photo_meta_data
(
    id bigserial not null primary key,
    photo_name varchar(100) not null,
    media_type varchar(3) not null,
    is_archive boolean not null,
    create_date timestamp not null,
    last_modified_date timestamp not null
);

create table photos.photo
(
  id bigserial not null primary key,
  weight smallint not null,
  height smallint not null,
  binary_content_id varchar(100) not null,
  meta_data_id bigserial not null,

  foreign key (meta_data_id) references photos.photo(id)
);

drop schema  photos cascade;
