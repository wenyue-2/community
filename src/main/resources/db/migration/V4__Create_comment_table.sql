create table comment
(
	id int auto_increment,
	parent_id int not null comment 'question id',
	type int null comment '回复的类型（回复中可能还有回复）',
	commentator int null comment '评论人id',
	gmt_create bigint null comment '创建时间',
	gmt_modified bigint null comment '修改时间',
	like_count bigint null comment '点赞数',
	content varchar(1024) null,
	constraint comment_pk
		primary key (id)
);