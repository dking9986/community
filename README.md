##DK
##资料
[spring文档]{https://spring.io/guides}
[spring web文档]{https://spring.io/guides/gs/serving-web-content/}
[github 登录]{https://developer.github.com/apps/building-oauth-apps/creating-an-oauth-app/}
[bootstrap]{https://v3.bootcss.com/getting-started/}
##工具
[git]
[flyway]
##脚本
```sql
create table USER
(
	ID INT auto_increment,
	ACCOUNT_ID VARCHAR(100),
	NAME VARCHAR(50),
	TOKEN CHAR(36),
	GMT_CREATE BIGINT,
	GMT_MODIFIED BIGINT,
	constraint USER_PK
		primary key (ID)
);



```
