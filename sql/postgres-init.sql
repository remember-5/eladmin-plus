/*
 Navicat Premium Data Transfer

 Source Server         : postgres-eladmin
 Source Server Type    : PostgreSQL
 Source Server Version : 140002
 Source Host           : 127.0.0.1:5432
 Source Catalog        : eladmin
 Source Schema         : eladmin_template_schema

 Target Server Type    : PostgreSQL
 Target Server Version : 140000
 File Encoding         : 65001

 Date: 17/05/2022 15:58:02
*/


-- ----------------------------
-- Sequence structure for app_version_id_seq
-- ----------------------------
DROP SEQUENCE IF EXISTS "app_version_id_seq";
CREATE SEQUENCE "app_version_id_seq"
INCREMENT 1
MINVALUE  1
MAXVALUE 2147483647
START 1
CACHE 1;

-- ----------------------------
-- Sequence structure for app_version_v2_id_seq
-- ----------------------------
DROP SEQUENCE IF EXISTS "app_version_v2_id_seq";
CREATE SEQUENCE "app_version_v2_id_seq"
INCREMENT 1
MINVALUE  1
MAXVALUE 2147483647
START 1
CACHE 1;

-- ----------------------------
-- Sequence structure for code_column_config_column_id_seq
-- ----------------------------
DROP SEQUENCE IF EXISTS "code_column_config_column_id_seq";
CREATE SEQUENCE "code_column_config_column_id_seq"
INCREMENT 1
MINVALUE  1
MAXVALUE 2147483647
START 1
CACHE 1;

-- ----------------------------
-- Sequence structure for code_gen_config_config_id_seq
-- ----------------------------
DROP SEQUENCE IF EXISTS "code_gen_config_config_id_seq";
CREATE SEQUENCE "code_gen_config_config_id_seq"
INCREMENT 1
MINVALUE  1
MAXVALUE 2147483647
START 1
CACHE 1;

-- ----------------------------
-- Sequence structure for m_user_user_id_seq
-- ----------------------------
DROP SEQUENCE IF EXISTS "m_user_user_id_seq";
CREATE SEQUENCE "m_user_user_id_seq"
INCREMENT 1
MINVALUE  1
MAXVALUE 2147483647
START 1
CACHE 1;

-- ----------------------------
-- Sequence structure for mnt_app_app_id_seq
-- ----------------------------
DROP SEQUENCE IF EXISTS "mnt_app_app_id_seq";
CREATE SEQUENCE "mnt_app_app_id_seq"
INCREMENT 1
MINVALUE  1
MAXVALUE 2147483647
START 1
CACHE 1;

-- ----------------------------
-- Sequence structure for mnt_deploy_deploy_id_seq
-- ----------------------------
DROP SEQUENCE IF EXISTS "mnt_deploy_deploy_id_seq";
CREATE SEQUENCE "mnt_deploy_deploy_id_seq"
INCREMENT 1
MINVALUE  1
MAXVALUE 2147483647
START 1
CACHE 1;

-- ----------------------------
-- Sequence structure for mnt_server_server_id_seq
-- ----------------------------
DROP SEQUENCE IF EXISTS "mnt_server_server_id_seq";
CREATE SEQUENCE "mnt_server_server_id_seq"
INCREMENT 1
MINVALUE  1
MAXVALUE 2147483647
START 1
CACHE 1;

-- ----------------------------
-- Sequence structure for sys_dept_dept_id_seq
-- ----------------------------
DROP SEQUENCE IF EXISTS "sys_dept_dept_id_seq";
CREATE SEQUENCE "sys_dept_dept_id_seq"
INCREMENT 1
MINVALUE  1
MAXVALUE 2147483647
START 1
CACHE 1;

-- ----------------------------
-- Sequence structure for sys_dict_detail_detail_id_seq
-- ----------------------------
DROP SEQUENCE IF EXISTS "sys_dict_detail_detail_id_seq";
CREATE SEQUENCE "sys_dict_detail_detail_id_seq"
INCREMENT 1
MINVALUE  1
MAXVALUE 2147483647
START 1
CACHE 1;

-- ----------------------------
-- Sequence structure for sys_dict_dict_id_seq
-- ----------------------------
DROP SEQUENCE IF EXISTS "sys_dict_dict_id_seq";
CREATE SEQUENCE "sys_dict_dict_id_seq"
INCREMENT 1
MINVALUE  1
MAXVALUE 2147483647
START 1
CACHE 1;

-- ----------------------------
-- Sequence structure for sys_job_job_id_seq
-- ----------------------------
DROP SEQUENCE IF EXISTS "sys_job_job_id_seq";
CREATE SEQUENCE "sys_job_job_id_seq"
INCREMENT 1
MINVALUE  1
MAXVALUE 2147483647
START 1
CACHE 1;

-- ----------------------------
-- Sequence structure for sys_log_log_id_seq
-- ----------------------------
DROP SEQUENCE IF EXISTS "sys_log_log_id_seq";
CREATE SEQUENCE "sys_log_log_id_seq"
INCREMENT 1
MINVALUE  1
MAXVALUE 2147483647
START 1
CACHE 1;

-- ----------------------------
-- Sequence structure for sys_menu_menu_id_seq
-- ----------------------------
DROP SEQUENCE IF EXISTS "sys_menu_menu_id_seq";
CREATE SEQUENCE "sys_menu_menu_id_seq"
INCREMENT 1
MINVALUE  1
MAXVALUE 2147483647
START 1
CACHE 1;

-- ----------------------------
-- Sequence structure for sys_quartz_job_job_id_seq
-- ----------------------------
DROP SEQUENCE IF EXISTS "sys_quartz_job_job_id_seq";
CREATE SEQUENCE "sys_quartz_job_job_id_seq"
INCREMENT 1
MINVALUE  1
MAXVALUE 2147483647
START 1
CACHE 1;

-- ----------------------------
-- Sequence structure for sys_quartz_log_log_id_seq
-- ----------------------------
DROP SEQUENCE IF EXISTS "sys_quartz_log_log_id_seq";
CREATE SEQUENCE "sys_quartz_log_log_id_seq"
INCREMENT 1
MINVALUE  1
MAXVALUE 2147483647
START 1
CACHE 1;

-- ----------------------------
-- Sequence structure for sys_role_role_id_seq
-- ----------------------------
DROP SEQUENCE IF EXISTS "sys_role_role_id_seq";
CREATE SEQUENCE "sys_role_role_id_seq"
INCREMENT 1
MINVALUE  1
MAXVALUE 2147483647
START 1
CACHE 1;

-- ----------------------------
-- Sequence structure for sys_user_user_id_seq
-- ----------------------------
DROP SEQUENCE IF EXISTS "sys_user_user_id_seq";
CREATE SEQUENCE "sys_user_user_id_seq"
INCREMENT 1
MINVALUE  1
MAXVALUE 2147483647
START 1
CACHE 1;

-- ----------------------------
-- Sequence structure for t_cms_cms_id_seq
-- ----------------------------
DROP SEQUENCE IF EXISTS "t_cms_cms_id_seq";
CREATE SEQUENCE "t_cms_cms_id_seq"
INCREMENT 1
MINVALUE  1
MAXVALUE 2147483647
START 1
CACHE 1;

-- ----------------------------
-- Sequence structure for t_cms_column_column_id_seq
-- ----------------------------
DROP SEQUENCE IF EXISTS "t_cms_column_column_id_seq";
CREATE SEQUENCE "t_cms_column_column_id_seq"
INCREMENT 1
MINVALUE  1
MAXVALUE 2147483647
START 1
CACHE 1;

-- ----------------------------
-- Sequence structure for t_message_notification_id_seq
-- ----------------------------
DROP SEQUENCE IF EXISTS "t_message_notification_id_seq";
CREATE SEQUENCE "t_message_notification_id_seq"
INCREMENT 1
MINVALUE  1
MAXVALUE 2147483647
START 1
CACHE 1;

-- ----------------------------
-- Sequence structure for tool_local_storage_storage_id_seq
-- ----------------------------
DROP SEQUENCE IF EXISTS "tool_local_storage_storage_id_seq";
CREATE SEQUENCE "tool_local_storage_storage_id_seq"
INCREMENT 1
MINVALUE  1
MAXVALUE 2147483647
START 1
CACHE 1;

-- ----------------------------
-- Sequence structure for tool_qiniu_content_content_id_seq
-- ----------------------------
DROP SEQUENCE IF EXISTS "tool_qiniu_content_content_id_seq";
CREATE SEQUENCE "tool_qiniu_content_content_id_seq"
INCREMENT 1
MINVALUE  1
MAXVALUE 2147483647
START 1
CACHE 1;

-- ----------------------------
-- Table structure for app_version
-- ----------------------------
DROP TABLE IF EXISTS "app_version";
CREATE TABLE "app_version" (
  "id" int4 NOT NULL DEFAULT nextval('eladmin_template_schema.app_version_id_seq'::regclass),
  "version_name" varchar(255) COLLATE "pg_catalog"."default" NOT NULL,
  "build_code" varchar(255) COLLATE "pg_catalog"."default" NOT NULL,
  "is_new" bool,
  "is_deleted" bool NOT NULL,
  "content" varchar(1024) COLLATE "pg_catalog"."default",
  "url" varchar(255) COLLATE "pg_catalog"."default" NOT NULL,
  "create_date" int2,
  "update_date" timestamp(6),
  "is_must" bool,
  "res_type" int8
)
;
COMMENT ON COLUMN "app_version"."version_name" IS '版本号  这里设定为只有基础功能或大改动时才会有改动';
COMMENT ON COLUMN "app_version"."build_code" IS '打包号  这里设定wgt包改动或功能性改动';
COMMENT ON COLUMN "app_version"."is_new" IS '是否最新';
COMMENT ON COLUMN "app_version"."is_deleted" IS '逻辑删除';
COMMENT ON COLUMN "app_version"."content" IS '升级说明';
COMMENT ON COLUMN "app_version"."url" IS '下载链接';
COMMENT ON COLUMN "app_version"."create_date" IS '创建时间';
COMMENT ON COLUMN "app_version"."update_date" IS '更新时间';
COMMENT ON COLUMN "app_version"."is_must" IS '是否是必须更新';
COMMENT ON COLUMN "app_version"."res_type" IS '资源类型 1、app 2、wgt';
COMMENT ON TABLE "app_version" IS 'App版本';

-- ----------------------------
-- Records of app_version
-- ----------------------------
BEGIN;
COMMIT;

-- ----------------------------
-- Table structure for app_version_v2
-- ----------------------------
DROP TABLE IF EXISTS "app_version_v2";
CREATE TABLE "app_version_v2" (
  "id" int4 NOT NULL DEFAULT nextval('eladmin_template_schema.app_version_v2_id_seq'::regclass),
  "version" varchar(255) COLLATE "pg_catalog"."default" NOT NULL,
  "version_code" varchar(255) COLLATE "pg_catalog"."default" NOT NULL,
  "version_info" varchar(4096) COLLATE "pg_catalog"."default",
  "updata_type" varchar(32) COLLATE "pg_catalog"."default" NOT NULL,
  "res_url" varchar(1024) COLLATE "pg_catalog"."default" NOT NULL,
  "platform" varchar(16) COLLATE "pg_catalog"."default" NOT NULL,
  "create_date" date NOT NULL
)
;
COMMENT ON COLUMN "app_version_v2"."version" IS '版本';
COMMENT ON COLUMN "app_version_v2"."version_code" IS '版本号';
COMMENT ON COLUMN "app_version_v2"."version_info" IS '版本信息';
COMMENT ON COLUMN "app_version_v2"."updata_type" IS ' 更新类型 forcibly 强制更新 solicit 弹窗确认更新 silent 静默更新';
COMMENT ON COLUMN "app_version_v2"."res_url" IS '下载地址';
COMMENT ON COLUMN "app_version_v2"."platform" IS '平台';
COMMENT ON COLUMN "app_version_v2"."create_date" IS '创建时间';

-- ----------------------------
-- Records of app_version_v2
-- ----------------------------
BEGIN;
COMMIT;

-- ----------------------------
-- Table structure for code_column_config
-- ----------------------------
DROP TABLE IF EXISTS "code_column_config";
CREATE TABLE "code_column_config" (
  "column_id" int4 NOT NULL DEFAULT nextval('eladmin_template_schema.code_column_config_column_id_seq'::regclass),
  "table_name" varchar(255) COLLATE "pg_catalog"."default",
  "column_name" varchar(255) COLLATE "pg_catalog"."default",
  "column_type" varchar(255) COLLATE "pg_catalog"."default",
  "dict_name" varchar(255) COLLATE "pg_catalog"."default",
  "extra" varchar(255) COLLATE "pg_catalog"."default",
  "form_show" bool,
  "form_type" varchar(255) COLLATE "pg_catalog"."default",
  "key_type" varchar(255) COLLATE "pg_catalog"."default",
  "list_show" bool,
  "not_null" bool,
  "query_type" varchar(255) COLLATE "pg_catalog"."default",
  "remark" varchar(255) COLLATE "pg_catalog"."default",
  "date_annotation" varchar(255) COLLATE "pg_catalog"."default"
)
;
COMMENT ON COLUMN "code_column_config"."column_id" IS 'ID';
COMMENT ON TABLE "code_column_config" IS '代码生成字段信息存储';

-- ----------------------------
-- Records of code_column_config
-- ----------------------------
BEGIN;
COMMIT;

-- ----------------------------
-- Table structure for code_gen_config
-- ----------------------------
DROP TABLE IF EXISTS "code_gen_config";
CREATE TABLE "code_gen_config" (
  "config_id" int4 NOT NULL DEFAULT nextval('eladmin_template_schema.code_gen_config_config_id_seq'::regclass),
  "table_name" varchar(255) COLLATE "pg_catalog"."default",
  "author" varchar(255) COLLATE "pg_catalog"."default",
  "cover" bool,
  "module_name" varchar(255) COLLATE "pg_catalog"."default",
  "pack" varchar(255) COLLATE "pg_catalog"."default",
  "path" varchar(255) COLLATE "pg_catalog"."default",
  "api_path" varchar(255) COLLATE "pg_catalog"."default",
  "prefix" varchar(255) COLLATE "pg_catalog"."default",
  "api_alias" varchar(255) COLLATE "pg_catalog"."default",
  "menu_headline" varchar(255) COLLATE "pg_catalog"."default",
  "routing_address" varchar(255) COLLATE "pg_catalog"."default",
  "auto_generate_menu" bool,
  "relative_path" bool,
  "admin_jurisdiction" bool,
  "component_path" varchar(255) COLLATE "pg_catalog"."default"
)
;
COMMENT ON COLUMN "code_gen_config"."config_id" IS 'ID';
COMMENT ON COLUMN "code_gen_config"."table_name" IS '表名';
COMMENT ON COLUMN "code_gen_config"."author" IS '作者';
COMMENT ON COLUMN "code_gen_config"."cover" IS '是否覆盖';
COMMENT ON COLUMN "code_gen_config"."module_name" IS '模块名称';
COMMENT ON COLUMN "code_gen_config"."pack" IS '至于哪个包下';
COMMENT ON COLUMN "code_gen_config"."path" IS '前端代码生成的路径';
COMMENT ON COLUMN "code_gen_config"."api_path" IS '前端Api文件路径';
COMMENT ON COLUMN "code_gen_config"."prefix" IS '表前缀';
COMMENT ON COLUMN "code_gen_config"."api_alias" IS '接口名称';
COMMENT ON COLUMN "code_gen_config"."menu_headline" IS '菜单标题';
COMMENT ON COLUMN "code_gen_config"."routing_address" IS '路由地址';
COMMENT ON COLUMN "code_gen_config"."auto_generate_menu" IS '自动生成菜单';
COMMENT ON COLUMN "code_gen_config"."relative_path" IS '是否相对路径';
COMMENT ON COLUMN "code_gen_config"."admin_jurisdiction" IS '是否添加到管理员权限上';
COMMENT ON COLUMN "code_gen_config"."component_path" IS '组件相对路径';
COMMENT ON TABLE "code_gen_config" IS '代码生成器配置';

-- ----------------------------
-- Records of code_gen_config
-- ----------------------------
BEGIN;
COMMIT;

-- ----------------------------
-- Table structure for m_user
-- ----------------------------
DROP TABLE IF EXISTS "m_user";
CREATE TABLE "m_user" (
  "user_id" int4 NOT NULL DEFAULT nextval('eladmin_template_schema.m_user_user_id_seq'::regclass),
  "username" varchar(255) COLLATE "pg_catalog"."default",
  "password" varchar(255) COLLATE "pg_catalog"."default",
  "phone" varchar(255) COLLATE "pg_catalog"."default",
  "ex1" varchar(255) COLLATE "pg_catalog"."default",
  "ex2" varchar(255) COLLATE "pg_catalog"."default",
  "ex3" varchar(255) COLLATE "pg_catalog"."default",
  "ex4" varchar(255) COLLATE "pg_catalog"."default",
  "ex5" varchar(255) COLLATE "pg_catalog"."default",
  "create_time" timestamp(6),
  "update_time" timestamp(6),
  "is_deleted" bool
)
;
COMMENT ON COLUMN "m_user"."username" IS '用户名';
COMMENT ON COLUMN "m_user"."password" IS '密码';
COMMENT ON COLUMN "m_user"."phone" IS '手机号';
COMMENT ON COLUMN "m_user"."ex1" IS '备用字段1';
COMMENT ON COLUMN "m_user"."ex2" IS '备用字段2';
COMMENT ON COLUMN "m_user"."ex3" IS '备用字段3';
COMMENT ON COLUMN "m_user"."ex4" IS '备用字段4';
COMMENT ON COLUMN "m_user"."ex5" IS '备用字段5';
COMMENT ON COLUMN "m_user"."create_time" IS '创建时间';
COMMENT ON COLUMN "m_user"."update_time" IS '修改时间';
COMMENT ON COLUMN "m_user"."is_deleted" IS '1 表示删除，0 表示未删除';
COMMENT ON TABLE "m_user" IS 'API 用户表';

-- ----------------------------
-- Records of m_user
-- ----------------------------
BEGIN;
COMMIT;

-- ----------------------------
-- Table structure for mnt_app
-- ----------------------------
DROP TABLE IF EXISTS "mnt_app";
CREATE TABLE "mnt_app" (
  "app_id" int4 NOT NULL DEFAULT nextval('eladmin_template_schema.mnt_app_app_id_seq'::regclass),
  "name" varchar(255) COLLATE "pg_catalog"."default",
  "upload_path" varchar(255) COLLATE "pg_catalog"."default",
  "deploy_path" varchar(255) COLLATE "pg_catalog"."default",
  "backup_path" varchar(255) COLLATE "pg_catalog"."default",
  "port" int4,
  "start_script" varchar(4000) COLLATE "pg_catalog"."default",
  "deploy_script" varchar(4000) COLLATE "pg_catalog"."default",
  "create_by" varchar(255) COLLATE "pg_catalog"."default",
  "update_by" varchar(255) COLLATE "pg_catalog"."default",
  "create_time" timestamp(6),
  "update_time" timestamp(6)
)
;
COMMENT ON COLUMN "mnt_app"."app_id" IS 'ID';
COMMENT ON COLUMN "mnt_app"."name" IS '应用名称';
COMMENT ON COLUMN "mnt_app"."upload_path" IS '上传目录';
COMMENT ON COLUMN "mnt_app"."deploy_path" IS '部署路径';
COMMENT ON COLUMN "mnt_app"."backup_path" IS '备份路径';
COMMENT ON COLUMN "mnt_app"."port" IS '应用端口';
COMMENT ON COLUMN "mnt_app"."start_script" IS '启动脚本';
COMMENT ON COLUMN "mnt_app"."deploy_script" IS '部署脚本';
COMMENT ON COLUMN "mnt_app"."create_by" IS '创建者';
COMMENT ON COLUMN "mnt_app"."update_by" IS '更新者';
COMMENT ON COLUMN "mnt_app"."create_time" IS '创建日期';
COMMENT ON COLUMN "mnt_app"."update_time" IS '更新时间';
COMMENT ON TABLE "mnt_app" IS '应用管理';

-- ----------------------------
-- Records of mnt_app
-- ----------------------------
BEGIN;
COMMIT;

-- ----------------------------
-- Table structure for mnt_database
-- ----------------------------
DROP TABLE IF EXISTS "mnt_database";
CREATE TABLE "mnt_database" (
  "db_id" varchar(50) COLLATE "pg_catalog"."default" NOT NULL,
  "name" varchar(255) COLLATE "pg_catalog"."default" NOT NULL,
  "jdbc_url" varchar(255) COLLATE "pg_catalog"."default" NOT NULL,
  "user_name" varchar(255) COLLATE "pg_catalog"."default" NOT NULL,
  "pwd" varchar(255) COLLATE "pg_catalog"."default" NOT NULL,
  "create_by" varchar(255) COLLATE "pg_catalog"."default",
  "update_by" varchar(255) COLLATE "pg_catalog"."default",
  "create_time" timestamp(6),
  "update_time" timestamp(6)
)
;
COMMENT ON COLUMN "mnt_database"."db_id" IS 'ID';
COMMENT ON COLUMN "mnt_database"."name" IS '名称';
COMMENT ON COLUMN "mnt_database"."jdbc_url" IS 'jdbc连接';
COMMENT ON COLUMN "mnt_database"."user_name" IS '账号';
COMMENT ON COLUMN "mnt_database"."pwd" IS '密码';
COMMENT ON COLUMN "mnt_database"."create_by" IS '创建者';
COMMENT ON COLUMN "mnt_database"."update_by" IS '更新者';
COMMENT ON COLUMN "mnt_database"."create_time" IS '创建时间';
COMMENT ON COLUMN "mnt_database"."update_time" IS '更新时间';
COMMENT ON TABLE "mnt_database" IS '数据库管理';

-- ----------------------------
-- Records of mnt_database
-- ----------------------------
BEGIN;
COMMIT;

-- ----------------------------
-- Table structure for mnt_deploy
-- ----------------------------
DROP TABLE IF EXISTS "mnt_deploy";
CREATE TABLE "mnt_deploy" (
  "deploy_id" int4 NOT NULL DEFAULT nextval('eladmin_template_schema.mnt_deploy_deploy_id_seq'::regclass),
  "app_id" int8,
  "create_by" varchar(255) COLLATE "pg_catalog"."default",
  "update_by" varchar(255) COLLATE "pg_catalog"."default",
  "create_time" timestamp(6),
  "update_time" timestamp(6)
)
;
COMMENT ON COLUMN "mnt_deploy"."deploy_id" IS 'ID';
COMMENT ON COLUMN "mnt_deploy"."app_id" IS '应用编号';
COMMENT ON COLUMN "mnt_deploy"."create_by" IS '创建者';
COMMENT ON COLUMN "mnt_deploy"."update_by" IS '更新者';
COMMENT ON COLUMN "mnt_deploy"."update_time" IS '更新时间';
COMMENT ON TABLE "mnt_deploy" IS '部署管理';

-- ----------------------------
-- Records of mnt_deploy
-- ----------------------------
BEGIN;
COMMIT;

-- ----------------------------
-- Table structure for mnt_deploy_history
-- ----------------------------
DROP TABLE IF EXISTS "mnt_deploy_history";
CREATE TABLE "mnt_deploy_history" (
  "history_id" varchar(50) COLLATE "pg_catalog"."default" NOT NULL,
  "app_name" varchar(255) COLLATE "pg_catalog"."default" NOT NULL,
  "deploy_date" timestamp(6) NOT NULL,
  "deploy_user" varchar(50) COLLATE "pg_catalog"."default" NOT NULL,
  "ip" varchar(20) COLLATE "pg_catalog"."default" NOT NULL,
  "deploy_id" int8
)
;
COMMENT ON COLUMN "mnt_deploy_history"."history_id" IS 'ID';
COMMENT ON COLUMN "mnt_deploy_history"."app_name" IS '应用名称';
COMMENT ON COLUMN "mnt_deploy_history"."deploy_date" IS '部署日期';
COMMENT ON COLUMN "mnt_deploy_history"."deploy_user" IS '部署用户';
COMMENT ON COLUMN "mnt_deploy_history"."ip" IS '服务器IP';
COMMENT ON COLUMN "mnt_deploy_history"."deploy_id" IS '部署编号';
COMMENT ON TABLE "mnt_deploy_history" IS '部署历史管理';

-- ----------------------------
-- Records of mnt_deploy_history
-- ----------------------------
BEGIN;
COMMIT;

-- ----------------------------
-- Table structure for mnt_deploy_server
-- ----------------------------
DROP TABLE IF EXISTS "mnt_deploy_server";
CREATE TABLE "mnt_deploy_server" (
  "deploy_id" int8 NOT NULL,
  "server_id" int8 NOT NULL
)
;
COMMENT ON COLUMN "mnt_deploy_server"."deploy_id" IS '部署ID';
COMMENT ON COLUMN "mnt_deploy_server"."server_id" IS '服务ID';
COMMENT ON TABLE "mnt_deploy_server" IS '应用与服务器关联';

-- ----------------------------
-- Records of mnt_deploy_server
-- ----------------------------
BEGIN;
COMMIT;

-- ----------------------------
-- Table structure for mnt_server
-- ----------------------------
DROP TABLE IF EXISTS "mnt_server";
CREATE TABLE "mnt_server" (
  "server_id" int4 NOT NULL DEFAULT nextval('eladmin_template_schema.mnt_server_server_id_seq'::regclass),
  "account" varchar(50) COLLATE "pg_catalog"."default",
  "ip" varchar(20) COLLATE "pg_catalog"."default",
  "name" varchar(100) COLLATE "pg_catalog"."default",
  "password" varchar(100) COLLATE "pg_catalog"."default",
  "port" int4,
  "create_by" varchar(255) COLLATE "pg_catalog"."default",
  "update_by" varchar(255) COLLATE "pg_catalog"."default",
  "create_time" timestamp(6),
  "update_time" timestamp(6)
)
;
COMMENT ON COLUMN "mnt_server"."server_id" IS 'ID';
COMMENT ON COLUMN "mnt_server"."account" IS '账号';
COMMENT ON COLUMN "mnt_server"."ip" IS 'IP地址';
COMMENT ON COLUMN "mnt_server"."name" IS '名称';
COMMENT ON COLUMN "mnt_server"."password" IS '密码';
COMMENT ON COLUMN "mnt_server"."port" IS '端口';
COMMENT ON COLUMN "mnt_server"."create_by" IS '创建者';
COMMENT ON COLUMN "mnt_server"."update_by" IS '更新者';
COMMENT ON COLUMN "mnt_server"."create_time" IS '创建时间';
COMMENT ON COLUMN "mnt_server"."update_time" IS '更新时间';
COMMENT ON TABLE "mnt_server" IS '服务器管理';

-- ----------------------------
-- Records of mnt_server
-- ----------------------------
BEGIN;
COMMIT;

-- ----------------------------
-- Table structure for sys_dept
-- ----------------------------
DROP TABLE IF EXISTS "sys_dept";
CREATE TABLE "sys_dept" (
  "dept_id" int4 NOT NULL DEFAULT nextval('eladmin_template_schema.sys_dept_dept_id_seq'::regclass),
  "pid" int8,
  "sub_count" int4,
  "name" varchar(255) COLLATE "pg_catalog"."default" NOT NULL,
  "dept_sort" int4,
  "enabled" bool,
  "create_by" varchar(255) COLLATE "pg_catalog"."default",
  "update_by" varchar(255) COLLATE "pg_catalog"."default",
  "create_time" timestamp(6),
  "update_time" timestamp(6)
)
;
COMMENT ON COLUMN "sys_dept"."dept_id" IS 'ID';
COMMENT ON COLUMN "sys_dept"."pid" IS '上级部门';
COMMENT ON COLUMN "sys_dept"."sub_count" IS '子部门数目';
COMMENT ON COLUMN "sys_dept"."name" IS '名称';
COMMENT ON COLUMN "sys_dept"."dept_sort" IS '排序';
COMMENT ON COLUMN "sys_dept"."enabled" IS '状态';
COMMENT ON COLUMN "sys_dept"."create_by" IS '创建者';
COMMENT ON COLUMN "sys_dept"."update_by" IS '更新者';
COMMENT ON COLUMN "sys_dept"."create_time" IS '创建日期';
COMMENT ON COLUMN "sys_dept"."update_time" IS '更新时间';
COMMENT ON TABLE "sys_dept" IS '部门';

-- ----------------------------
-- Records of sys_dept
-- ----------------------------
BEGIN;
INSERT INTO "sys_dept" VALUES (2, 7, 1, '研发部', 3, 't', 'admin', 'admin', '2019-03-25 09:15:32', '2020-08-02 14:48:47');
INSERT INTO "sys_dept" VALUES (5, 7, 0, '运维部', 4, 't', 'admin', 'admin', '2019-03-25 09:20:44', '2020-05-17 14:27:27');
INSERT INTO "sys_dept" VALUES (6, 8, 0, '测试部', 6, 't', 'admin', 'admin', '2019-03-25 09:52:18', '2020-06-08 11:59:21');
INSERT INTO "sys_dept" VALUES (7, NULL, 2, '华南分部', 0, 't', 'admin', 'admin', '2019-03-25 11:04:50', '2020-06-08 12:08:56');
INSERT INTO "sys_dept" VALUES (8, NULL, 2, '华北分部', 1, 't', 'admin', 'admin', '2019-03-25 11:04:53', '2020-05-14 12:54:00');
INSERT INTO "sys_dept" VALUES (15, 8, 0, 'UI部门', 7, 't', 'admin', 'admin', '2020-05-13 22:56:53', '2020-05-14 12:54:13');
INSERT INTO "sys_dept" VALUES (17, 2, 0, '研发一组', 999, 't', 'admin', 'admin', '2020-08-02 14:49:07', '2020-08-02 14:49:07');
COMMIT;

-- ----------------------------
-- Table structure for sys_dict
-- ----------------------------
DROP TABLE IF EXISTS "sys_dict";
CREATE TABLE "sys_dict" (
  "dict_id" int4 NOT NULL DEFAULT nextval('eladmin_template_schema.sys_dict_dict_id_seq'::regclass),
  "name" varchar(255) COLLATE "pg_catalog"."default" NOT NULL,
  "description" varchar(255) COLLATE "pg_catalog"."default",
  "create_by" varchar(255) COLLATE "pg_catalog"."default",
  "update_by" varchar(255) COLLATE "pg_catalog"."default",
  "create_time" timestamp(6),
  "update_time" timestamp(6)
)
;
COMMENT ON COLUMN "sys_dict"."dict_id" IS 'ID';
COMMENT ON COLUMN "sys_dict"."name" IS '字典名称';
COMMENT ON COLUMN "sys_dict"."description" IS '描述';
COMMENT ON COLUMN "sys_dict"."create_by" IS '创建者';
COMMENT ON COLUMN "sys_dict"."update_by" IS '更新者';
COMMENT ON COLUMN "sys_dict"."create_time" IS '创建日期';
COMMENT ON COLUMN "sys_dict"."update_time" IS '更新时间';
COMMENT ON TABLE "sys_dict" IS '数据字典';

-- ----------------------------
-- Records of sys_dict
-- ----------------------------
BEGIN;
INSERT INTO "sys_dict" VALUES (1, 'user_status', '用户状态', NULL, NULL, '2019-10-27 20:31:36', NULL);
INSERT INTO "sys_dict" VALUES (4, 'dept_status', '部门状态', NULL, NULL, '2019-10-27 20:31:36', NULL);
INSERT INTO "sys_dict" VALUES (5, 'job_status', '岗位状态', NULL, NULL, '2019-10-27 20:31:36', NULL);
INSERT INTO "sys_dict" VALUES (6, 'resource_status', '存储资源状态', 'admin', 'admin', '2021-04-12 15:41:43', '2021-04-12 16:57:00');
INSERT INTO "sys_dict" VALUES (7, 'resources_type', '存储资源类型', 'admin', 'admin', '2021-04-12 16:56:56', '2021-04-12 16:56:56');
INSERT INTO "sys_dict" VALUES (8, 'message_level', '消息级别', 'admin', 'admin', '2021-04-19 17:00:40', '2021-04-19 17:00:40');
INSERT INTO "sys_dict" VALUES (9, 'message_label', '消息标签', 'admin', 'admin', '2021-04-19 17:01:19', '2021-04-19 17:01:19');
INSERT INTO "sys_dict" VALUES (10, 'message_state', '消息状态', 'admin', 'admin', '2021-04-19 17:02:24', '2021-04-19 17:02:24');
INSERT INTO "sys_dict" VALUES (2, 'app_ver_type', 'app版本类型', 'admin', 'admin', '2022-05-16 11:08:18.455', '2022-05-16 11:08:18.455');
INSERT INTO "sys_dict" VALUES (3, 'platform_type', '系统标识', 'admin', 'admin', '2022-05-16 14:47:55.801', '2022-05-16 14:47:55.801');
COMMIT;

-- ----------------------------
-- Table structure for sys_dict_detail
-- ----------------------------
DROP TABLE IF EXISTS "sys_dict_detail";
CREATE TABLE "sys_dict_detail" (
  "detail_id" int4 NOT NULL DEFAULT nextval('eladmin_template_schema.sys_dict_detail_detail_id_seq'::regclass),
  "dict_id" int8,
  "label" varchar(255) COLLATE "pg_catalog"."default" NOT NULL,
  "value" varchar(255) COLLATE "pg_catalog"."default" NOT NULL,
  "dict_sort" int4,
  "create_by" varchar(255) COLLATE "pg_catalog"."default",
  "update_by" varchar(255) COLLATE "pg_catalog"."default",
  "create_time" timestamp(6),
  "update_time" timestamp(6)
)
;
COMMENT ON COLUMN "sys_dict_detail"."detail_id" IS 'ID';
COMMENT ON COLUMN "sys_dict_detail"."dict_id" IS '字典id';
COMMENT ON COLUMN "sys_dict_detail"."label" IS '字典标签';
COMMENT ON COLUMN "sys_dict_detail"."value" IS '字典值';
COMMENT ON COLUMN "sys_dict_detail"."dict_sort" IS '排序';
COMMENT ON COLUMN "sys_dict_detail"."create_by" IS '创建者';
COMMENT ON COLUMN "sys_dict_detail"."update_by" IS '更新者';
COMMENT ON COLUMN "sys_dict_detail"."create_time" IS '创建日期';
COMMENT ON COLUMN "sys_dict_detail"."update_time" IS '更新时间';
COMMENT ON TABLE "sys_dict_detail" IS '数据字典详情';

-- ----------------------------
-- Records of sys_dict_detail
-- ----------------------------
BEGIN;
INSERT INTO "sys_dict_detail" VALUES (1, 1, '激活', 'true', 1, NULL, NULL, '2019-10-27 20:31:36', NULL);
INSERT INTO "sys_dict_detail" VALUES (2, 1, '禁用', 'false', 2, NULL, NULL, NULL, NULL);
INSERT INTO "sys_dict_detail" VALUES (3, 4, '启用', 'true', 1, NULL, NULL, NULL, NULL);
INSERT INTO "sys_dict_detail" VALUES (4, 4, '停用', 'false', 2, NULL, NULL, '2019-10-27 20:31:36', NULL);
INSERT INTO "sys_dict_detail" VALUES (5, 5, '启用', 'true', 1, NULL, NULL, NULL, NULL);
INSERT INTO "sys_dict_detail" VALUES (6, 5, '停用', 'false', 2, NULL, NULL, '2019-10-27 20:31:36', NULL);
INSERT INTO "sys_dict_detail" VALUES (7, 6, '启用', '1', 1, 'admin', 'admin', '2021-04-12 15:41:53', '2021-04-12 15:41:53');
INSERT INTO "sys_dict_detail" VALUES (8, 6, '禁用', '0', 0, 'admin', 'admin', '2021-04-12 15:42:00', '2021-04-12 15:42:00');
INSERT INTO "sys_dict_detail" VALUES (9, 7, 'minIO', '1', 1, 'admin', 'admin', '2021-04-12 16:57:12', '2021-04-12 16:57:12');
INSERT INTO "sys_dict_detail" VALUES (10, 7, '七牛云', '2', 2, 'admin', 'admin', '2021-04-12 16:57:22', '2021-04-12 16:57:22');
INSERT INTO "sys_dict_detail" VALUES (11, 7, '阿里云', '3', 3, 'admin', 'admin', '2021-04-12 16:57:29', '2021-04-12 16:57:29');
INSERT INTO "sys_dict_detail" VALUES (12, 8, '紧急', '0', 0, 'admin', 'admin', '2021-04-19 17:00:57', '2021-04-19 17:00:57');
INSERT INTO "sys_dict_detail" VALUES (13, 8, '普通', '1', 1, 'admin', 'admin', '2021-04-19 17:01:06', '2021-04-19 17:01:06');
INSERT INTO "sys_dict_detail" VALUES (14, 9, '错误', '0', 0, 'admin', 'admin', '2021-04-19 17:01:34', '2021-04-19 17:01:34');
INSERT INTO "sys_dict_detail" VALUES (15, 9, '普通', '1', 1, 'admin', 'admin', '2021-04-19 17:01:44', '2021-04-19 17:01:44');
INSERT INTO "sys_dict_detail" VALUES (16, 9, '待办', '2', 2, 'admin', 'admin', '2021-04-19 17:02:12', '2021-04-19 17:02:12');
INSERT INTO "sys_dict_detail" VALUES (17, 10, '未查看', '0', 0, 'admin', 'admin', '2021-04-19 17:02:46', '2021-04-19 17:02:46');
INSERT INTO "sys_dict_detail" VALUES (18, 10, '进行中', '1', 1, 'admin', 'admin', '2021-04-19 17:02:55', '2021-04-19 17:02:55');
INSERT INTO "sys_dict_detail" VALUES (19, 10, '已处理', '2', 2, 'admin', 'admin', '2021-04-19 17:03:02', '2021-04-19 17:03:02');
INSERT INTO "sys_dict_detail" VALUES (22, 2, '强制更新', 'forcibly', 1, 'admin', 'admin', '2022-05-16 12:50:42.725', '2022-05-16 12:50:42.725');
INSERT INTO "sys_dict_detail" VALUES (23, 2, '弹窗确认更新', 'solicit', 999, 'admin', 'admin', '2022-05-16 12:55:57.612', '2022-05-16 12:55:57.612');
INSERT INTO "sys_dict_detail" VALUES (24, 2, '静默更新', 'silent', 999, 'admin', 'admin', '2022-05-16 12:56:16.373', '2022-05-16 12:56:16.373');
INSERT INTO "sys_dict_detail" VALUES (28, 3, 'windows', 'windows', 999, 'admin', 'admin', '2022-05-16 14:52:22.434', '2022-05-16 14:52:22.434');
INSERT INTO "sys_dict_detail" VALUES (27, 3, 'mac', 'mac', 999, 'admin', 'admin', '2022-05-16 14:52:03.868', '2022-05-16 14:52:34.404');
INSERT INTO "sys_dict_detail" VALUES (26, 3, 'android', 'android', 999, 'admin', 'admin', '2022-05-16 14:49:26.326', '2022-05-16 14:52:40.359');
INSERT INTO "sys_dict_detail" VALUES (25, 3, 'ios', 'ios', 999, 'admin', 'admin', '2022-05-16 14:48:32.441', '2022-05-16 14:52:45.41');
INSERT INTO "sys_dict_detail" VALUES (29, 3, 'linux', 'linux', 999, 'admin', 'admin', '2022-05-16 14:52:58.377', '2022-05-16 14:52:58.377');
COMMIT;

-- ----------------------------
-- Table structure for sys_job
-- ----------------------------
DROP TABLE IF EXISTS "sys_job";
CREATE TABLE "sys_job" (
  "job_id" int4 NOT NULL DEFAULT nextval('eladmin_template_schema.sys_job_job_id_seq'::regclass),
  "name" varchar(255) COLLATE "pg_catalog"."default" NOT NULL,
  "enabled" bool,
  "job_sort" int4,
  "create_by" varchar(255) COLLATE "pg_catalog"."default",
  "update_by" varchar(255) COLLATE "pg_catalog"."default",
  "create_time" timestamp(6),
  "update_time" timestamp(6)
)
;
COMMENT ON COLUMN "sys_job"."job_id" IS 'ID';
COMMENT ON COLUMN "sys_job"."name" IS '岗位名称';
COMMENT ON COLUMN "sys_job"."enabled" IS '岗位状态';
COMMENT ON COLUMN "sys_job"."job_sort" IS '排序';
COMMENT ON COLUMN "sys_job"."create_by" IS '创建者';
COMMENT ON COLUMN "sys_job"."update_by" IS '更新者';
COMMENT ON COLUMN "sys_job"."create_time" IS '创建日期';
COMMENT ON COLUMN "sys_job"."update_time" IS '更新时间';
COMMENT ON TABLE "sys_job" IS '岗位';

-- ----------------------------
-- Records of sys_job
-- ----------------------------
BEGIN;
INSERT INTO "sys_job" VALUES (8, '人事专员', 't', 3, NULL, NULL, '2019-03-29 14:52:28', NULL);
INSERT INTO "sys_job" VALUES (10, '产品经理', 't', 4, NULL, NULL, '2019-03-29 14:55:51', NULL);
INSERT INTO "sys_job" VALUES (11, '全栈开发', 't', 2, NULL, 'admin', '2019-03-31 13:39:30', '2020-05-05 11:33:43');
INSERT INTO "sys_job" VALUES (12, '软件测试', 't', 5, NULL, 'admin', '2019-03-31 13:39:43', '2020-05-10 19:56:26');
COMMIT;

-- ----------------------------
-- Table structure for sys_log
-- ----------------------------
DROP TABLE IF EXISTS "sys_log";
CREATE TABLE "sys_log" (
  "log_id" int4 NOT NULL DEFAULT nextval('eladmin_template_schema.sys_log_log_id_seq'::regclass),
  "description" varchar(255) COLLATE "pg_catalog"."default",
  "log_type" varchar(255) COLLATE "pg_catalog"."default",
  "method" varchar(255) COLLATE "pg_catalog"."default",
  "params" text COLLATE "pg_catalog"."default",
  "request_ip" varchar(255) COLLATE "pg_catalog"."default",
  "time" int8,
  "username" varchar(255) COLLATE "pg_catalog"."default",
  "address" varchar(255) COLLATE "pg_catalog"."default",
  "browser" varchar(255) COLLATE "pg_catalog"."default",
  "exception_detail" text COLLATE "pg_catalog"."default",
  "create_time" timestamp(6)
)
;
COMMENT ON COLUMN "sys_log"."log_id" IS 'ID';
COMMENT ON COLUMN "sys_log"."description" IS '描述';
COMMENT ON COLUMN "sys_log"."log_type" IS '日志类型';
COMMENT ON COLUMN "sys_log"."method" IS '方法名';
COMMENT ON COLUMN "sys_log"."params" IS '参数';
COMMENT ON COLUMN "sys_log"."request_ip" IS '请求ip';
COMMENT ON COLUMN "sys_log"."time" IS '请求耗时';
COMMENT ON COLUMN "sys_log"."username" IS '操作用户';
COMMENT ON COLUMN "sys_log"."address" IS '地址';
COMMENT ON COLUMN "sys_log"."browser" IS '浏览器';
COMMENT ON COLUMN "sys_log"."exception_detail" IS '异常详细';
COMMENT ON COLUMN "sys_log"."create_time" IS '创建日期';
COMMENT ON TABLE "sys_log" IS '系统日志';

-- ----------------------------
-- Records of sys_log
-- ----------------------------
BEGIN;
COMMIT;

-- ----------------------------
-- Table structure for sys_menu
-- ----------------------------
DROP TABLE IF EXISTS "sys_menu";
CREATE TABLE "sys_menu" (
  "menu_id" int4 NOT NULL DEFAULT nextval('eladmin_template_schema.sys_menu_menu_id_seq'::regclass),
  "pid" int8,
  "sub_count" int4,
  "type" int4,
  "title" varchar(255) COLLATE "pg_catalog"."default",
  "name" varchar(255) COLLATE "pg_catalog"."default",
  "component" varchar(255) COLLATE "pg_catalog"."default",
  "menu_sort" int4,
  "icon" varchar(255) COLLATE "pg_catalog"."default",
  "path" varchar(255) COLLATE "pg_catalog"."default",
  "i_frame" bool,
  "cache" bool,
  "hidden" bool,
  "permission" varchar(255) COLLATE "pg_catalog"."default",
  "create_by" varchar(255) COLLATE "pg_catalog"."default",
  "update_by" varchar(255) COLLATE "pg_catalog"."default",
  "create_time" timestamp(6),
  "update_time" timestamp(6)
)
;
COMMENT ON COLUMN "sys_menu"."menu_id" IS 'ID';
COMMENT ON COLUMN "sys_menu"."pid" IS '上级菜单ID';
COMMENT ON COLUMN "sys_menu"."sub_count" IS '子菜单数目';
COMMENT ON COLUMN "sys_menu"."type" IS '菜单类型';
COMMENT ON COLUMN "sys_menu"."title" IS '菜单标题';
COMMENT ON COLUMN "sys_menu"."name" IS '组件名称';
COMMENT ON COLUMN "sys_menu"."component" IS '组件';
COMMENT ON COLUMN "sys_menu"."menu_sort" IS '排序';
COMMENT ON COLUMN "sys_menu"."icon" IS '图标';
COMMENT ON COLUMN "sys_menu"."path" IS '链接地址';
COMMENT ON COLUMN "sys_menu"."i_frame" IS '是否外链';
COMMENT ON COLUMN "sys_menu"."cache" IS '缓存';
COMMENT ON COLUMN "sys_menu"."hidden" IS '隐藏';
COMMENT ON COLUMN "sys_menu"."permission" IS '权限';
COMMENT ON COLUMN "sys_menu"."create_by" IS '创建者';
COMMENT ON COLUMN "sys_menu"."update_by" IS '更新者';
COMMENT ON COLUMN "sys_menu"."create_time" IS '创建日期';
COMMENT ON COLUMN "sys_menu"."update_time" IS '更新时间';
COMMENT ON TABLE "sys_menu" IS '系统菜单';

-- ----------------------------
-- Records of sys_menu
-- ----------------------------
BEGIN;
INSERT INTO "sys_menu" VALUES (2, 1, 3, 1, '用户管理', 'User', 'system/user/index', 2, 'peoples', 'user', 'f', 'f', 'f', 'user:list', NULL, NULL, '2018-12-18 15:14:44', NULL);
INSERT INTO "sys_menu" VALUES (3, 1, 3, 1, '角色管理', 'Role', 'system/role/index', 3, 'role', 'role', 'f', 'f', 'f', 'roles:list', NULL, NULL, '2018-12-18 15:16:07', NULL);
INSERT INTO "sys_menu" VALUES (5, 1, 3, 1, '菜单管理', 'Menu', 'system/menu/index', 5, 'menu', 'menu', 'f', 'f', 'f', 'menu:list', NULL, NULL, '2018-12-18 15:17:28', NULL);
INSERT INTO "sys_menu" VALUES (6, NULL, 5, 0, '系统监控', NULL, NULL, 10, 'monitor', 'monitor', 'f', 'f', 'f', NULL, NULL, NULL, '2018-12-18 15:17:48', NULL);
INSERT INTO "sys_menu" VALUES (7, 6, 1, 1, '操作日志', 'Log', 'monitor/log/index', 11, 'log', 'logs', 'f', 't', 'f', 'logs:list', NULL, 'admin', '2018-12-18 15:18:26', '2021-12-10 15:48:23');
INSERT INTO "sys_menu" VALUES (9, 6, 0, 1, 'SQL监控', 'Sql', 'monitor/sql/index', 18, 'sqlMonitor', 'druid', 'f', 'f', 'f', NULL, NULL, NULL, '2018-12-18 15:19:34', NULL);
INSERT INTO "sys_menu" VALUES (10, NULL, 5, 0, '组件管理', NULL, NULL, 50, 'zujian', 'components', 'f', 'f', 'f', NULL, NULL, NULL, '2018-12-19 13:38:16', NULL);
INSERT INTO "sys_menu" VALUES (11, 10, 0, 1, '图标库', 'Icons', 'components/icons/index', 51, 'icon', 'icon', 'f', 'f', 'f', NULL, NULL, NULL, '2018-12-19 13:38:49', NULL);
INSERT INTO "sys_menu" VALUES (14, 36, 2, 1, '邮件工具', 'Email', 'tools/email/index', 35, 'email', 'email', 'f', 'f', 'f', NULL, NULL, NULL, '2018-12-27 10:13:09', NULL);
INSERT INTO "sys_menu" VALUES (15, 10, 0, 1, '富文本', 'Editor', 'components/Editor', 52, 'fwb', 'tinymce', 'f', 'f', 'f', NULL, NULL, NULL, '2018-12-27 11:58:25', NULL);
INSERT INTO "sys_menu" VALUES (19, 36, 2, 1, '支付宝工具', 'AliPay', 'tools/aliPay/index', 37, 'alipay', 'aliPay', 'f', 'f', 'f', NULL, NULL, NULL, '2018-12-31 14:52:38', NULL);
INSERT INTO "sys_menu" VALUES (21, NULL, 2, 0, '多级菜单', NULL, '', 900, 'menu', 'nested', 'f', 'f', 't', NULL, NULL, 'admin', '2019-01-04 16:22:03', '2021-12-10 16:14:19');
INSERT INTO "sys_menu" VALUES (22, 21, 2, 0, '二级菜单1', NULL, '', 999, 'menu', 'menu1', 'f', 'f', 'f', NULL, NULL, 'admin', '2019-01-04 16:23:29', '2020-06-21 17:27:20');
INSERT INTO "sys_menu" VALUES (23, 21, 0, 1, '二级菜单2', NULL, 'nested/menu2/index', 999, 'menu', 'menu2', 'f', 'f', 'f', NULL, NULL, NULL, '2019-01-04 16:23:57', NULL);
INSERT INTO "sys_menu" VALUES (24, 22, 0, 1, '三级菜单1', 'Test', 'nested/menu1/menu1-1', 999, 'menu', 'menu1-1', 'f', 'f', 'f', NULL, NULL, NULL, '2019-01-04 16:24:48', NULL);
INSERT INTO "sys_menu" VALUES (27, 22, 0, 1, '三级菜单2', NULL, 'nested/menu1/menu1-2', 999, 'menu', 'menu1-2', 'f', 'f', 'f', NULL, NULL, NULL, '2019-01-07 17:27:32', NULL);
INSERT INTO "sys_menu" VALUES (28, 1, 3, 1, '任务调度', 'Timing', 'system/timing/index', 999, 'timing', 'timing', 'f', 'f', 'f', 'timing:list', NULL, NULL, '2019-01-07 20:34:40', NULL);
INSERT INTO "sys_menu" VALUES (30, 36, 0, 1, '代码生成', 'GeneratorIndex', 'generator/index', 32, 'dev', 'generator', 'f', 't', 'f', NULL, NULL, NULL, '2019-01-11 15:45:55', NULL);
INSERT INTO "sys_menu" VALUES (32, 6, 2, 1, '异常日志', 'ErrorLog', 'monitor/log/errorLog', 12, 'error', 'errorLog', 'f', 'f', 'f', 'errorlogs:list', NULL, 'admin', '2019-01-13 13:49:03', '2021-12-10 15:48:32');
INSERT INTO "sys_menu" VALUES (33, 10, 0, 1, 'Markdown', 'Markdown', 'components/MarkDown', 53, 'markdown', 'markdown', 'f', 'f', 'f', NULL, NULL, NULL, '2019-03-08 13:46:44', NULL);
INSERT INTO "sys_menu" VALUES (34, 10, 0, 1, 'Yaml编辑器', 'YamlEdit', 'components/YamlEdit', 54, 'dev', 'yaml', 'f', 'f', 'f', NULL, NULL, NULL, '2019-03-08 15:49:40', NULL);
INSERT INTO "sys_menu" VALUES (35, 1, 3, 1, '部门管理', 'Dept', 'system/dept/index', 6, 'dept', 'dept', 'f', 'f', 'f', 'dept:list', NULL, NULL, '2019-03-25 09:46:00', NULL);
INSERT INTO "sys_menu" VALUES (37, 1, 3, 1, '岗位管理', 'Job', 'system/job/index', 7, 'Steve-Jobs', 'job', 'f', 'f', 'f', 'job:list', NULL, NULL, '2019-03-29 13:51:18', NULL);
INSERT INTO "sys_menu" VALUES (38, 36, 0, 1, '接口文档', 'Swagger', 'tools/swagger/index', 36, 'swagger', 'swagger2', 'f', 'f', 'f', NULL, NULL, NULL, '2019-03-29 19:57:53', NULL);
INSERT INTO "sys_menu" VALUES (39, 1, 3, 1, '字典管理', 'Dict', 'system/dict/index', 8, 'dictionary', 'dict', 'f', 'f', 'f', 'dict:list', NULL, NULL, '2019-04-10 11:49:04', NULL);
INSERT INTO "sys_menu" VALUES (41, 6, 1, 1, '在线用户', 'OnlineUser', 'monitor/online/index', 10, 'Steve-Jobs', 'online', 'f', 'f', 'f', 'online:list', NULL, 'admin', '2019-10-26 22:08:43', '2021-12-10 15:09:37');
INSERT INTO "sys_menu" VALUES (44, 2, 0, 2, '用户新增', NULL, '', 2, '', '', 'f', 'f', 'f', 'user:add', NULL, NULL, '2019-10-29 10:59:46', NULL);
INSERT INTO "sys_menu" VALUES (45, 2, 0, 2, '用户编辑', NULL, '', 3, '', '', 'f', 'f', 'f', 'user:edit', NULL, NULL, '2019-10-29 11:00:08', NULL);
INSERT INTO "sys_menu" VALUES (46, 2, 0, 2, '用户删除', NULL, '', 4, '', '', 'f', 'f', 'f', 'user:del', NULL, NULL, '2019-10-29 11:00:23', NULL);
INSERT INTO "sys_menu" VALUES (48, 3, 0, 2, '角色创建', NULL, '', 2, '', '', 'f', 'f', 'f', 'roles:add', NULL, NULL, '2019-10-29 12:45:34', NULL);
INSERT INTO "sys_menu" VALUES (49, 3, 0, 2, '角色修改', NULL, '', 3, '', '', 'f', 'f', 'f', 'roles:edit', NULL, NULL, '2019-10-29 12:46:16', NULL);
INSERT INTO "sys_menu" VALUES (50, 3, 0, 2, '角色删除', NULL, '', 4, '', '', 'f', 'f', 'f', 'roles:del', NULL, NULL, '2019-10-29 12:46:51', NULL);
INSERT INTO "sys_menu" VALUES (52, 5, 0, 2, '菜单新增', NULL, '', 2, '', '', 'f', 'f', 'f', 'menu:add', NULL, NULL, '2019-10-29 12:55:07', NULL);
INSERT INTO "sys_menu" VALUES (53, 5, 0, 2, '菜单编辑', NULL, '', 3, '', '', 'f', 'f', 'f', 'menu:edit', NULL, NULL, '2019-10-29 12:55:40', NULL);
INSERT INTO "sys_menu" VALUES (54, 5, 0, 2, '菜单删除', NULL, '', 4, '', '', 'f', 'f', 'f', 'menu:del', NULL, NULL, '2019-10-29 12:56:00', NULL);
INSERT INTO "sys_menu" VALUES (56, 35, 0, 2, '部门新增', NULL, '', 2, '', '', 'f', 'f', 'f', 'dept:add', NULL, NULL, '2019-10-29 12:57:09', NULL);
INSERT INTO "sys_menu" VALUES (57, 35, 0, 2, '部门编辑', NULL, '', 3, '', '', 'f', 'f', 'f', 'dept:edit', NULL, NULL, '2019-10-29 12:57:27', NULL);
INSERT INTO "sys_menu" VALUES (58, 35, 0, 2, '部门删除', NULL, '', 4, '', '', 'f', 'f', 'f', 'dept:del', NULL, NULL, '2019-10-29 12:57:41', NULL);
INSERT INTO "sys_menu" VALUES (60, 37, 0, 2, '岗位新增', NULL, '', 2, '', '', 'f', 'f', 'f', 'job:add', NULL, NULL, '2019-10-29 12:58:27', NULL);
INSERT INTO "sys_menu" VALUES (61, 37, 0, 2, '岗位编辑', NULL, '', 3, '', '', 'f', 'f', 'f', 'job:edit', NULL, NULL, '2019-10-29 12:58:45', NULL);
INSERT INTO "sys_menu" VALUES (62, 37, 0, 2, '岗位删除', NULL, '', 4, '', '', 'f', 'f', 'f', 'job:del', NULL, NULL, '2019-10-29 12:59:04', NULL);
INSERT INTO "sys_menu" VALUES (64, 39, 0, 2, '字典新增', NULL, '', 2, '', '', 'f', 'f', 'f', 'dict:add', NULL, NULL, '2019-10-29 13:00:17', NULL);
INSERT INTO "sys_menu" VALUES (65, 39, 0, 2, '字典编辑', NULL, '', 3, '', '', 'f', 'f', 'f', 'dict:edit', NULL, NULL, '2019-10-29 13:00:42', NULL);
INSERT INTO "sys_menu" VALUES (66, 39, 0, 2, '字典删除', NULL, '', 4, '', '', 'f', 'f', 'f', 'dict:del', NULL, NULL, '2019-10-29 13:00:59', NULL);
INSERT INTO "sys_menu" VALUES (73, 28, 0, 2, '任务新增', NULL, '', 2, '', '', 'f', 'f', 'f', 'timing:add', NULL, NULL, '2019-10-29 13:07:28', NULL);
INSERT INTO "sys_menu" VALUES (74, 28, 0, 2, '任务编辑', NULL, '', 3, '', '', 'f', 'f', 'f', 'timing:edit', NULL, NULL, '2019-10-29 13:07:41', NULL);
INSERT INTO "sys_menu" VALUES (75, 28, 0, 2, '任务删除', NULL, '', 4, '', '', 'f', 'f', 'f', 'timing:del', NULL, NULL, '2019-10-29 13:07:54', NULL);
INSERT INTO "sys_menu" VALUES (77, 18, 0, 2, '上传文件', NULL, '', 2, '', '', 'f', 'f', 'f', 'storage:add', NULL, NULL, '2019-10-29 13:09:09', NULL);
INSERT INTO "sys_menu" VALUES (78, 18, 0, 2, '文件编辑', NULL, '', 3, '', '', 'f', 'f', 'f', 'storage:edit', NULL, NULL, '2019-10-29 13:09:22', NULL);
INSERT INTO "sys_menu" VALUES (79, 18, 0, 2, '文件删除', NULL, '', 4, '', '', 'f', 'f', 'f', 'storage:del', NULL, NULL, '2019-10-29 13:09:34', NULL);
INSERT INTO "sys_menu" VALUES (80, 6, 0, 1, '服务监控', 'ServerMonitor', 'monitor/server/index', 14, 'codeConsole', 'server', 'f', 'f', 'f', 'monitor:list', NULL, 'admin', '2019-11-07 13:06:39', '2020-05-04 18:20:50');
INSERT INTO "sys_menu" VALUES (82, 36, 0, 1, '生成配置', 'GeneratorConfig', 'generator/config', 33, 'dev', 'generator/config/:tableName', 'f', 't', 't', '', NULL, NULL, '2019-11-17 20:08:56', NULL);
INSERT INTO "sys_menu" VALUES (83, 10, 0, 1, '图表库', 'Echarts', 'components/Echarts', 50, 'chart', 'echarts', 'f', 't', 'f', '', NULL, NULL, '2019-11-21 09:04:32', NULL);
INSERT INTO "sys_menu" VALUES (90, NULL, 5, 1, '运维管理', 'Mnt', '', 20, 'mnt', 'mnt', 'f', 'f', 'f', NULL, NULL, NULL, '2019-11-09 10:31:08', NULL);
INSERT INTO "sys_menu" VALUES (92, 90, 3, 1, '服务器', 'ServerDeploy', 'mnt/server/index', 22, 'server', 'mnt/serverDeploy', 'f', 'f', 'f', 'serverDeploy:list', NULL, NULL, '2019-11-10 10:29:25', NULL);
INSERT INTO "sys_menu" VALUES (93, 90, 3, 1, '应用管理', 'App', 'mnt/app/index', 23, 'app', 'mnt/app', 'f', 'f', 'f', 'app:list', NULL, NULL, '2019-11-10 11:05:16', NULL);
INSERT INTO "sys_menu" VALUES (94, 90, 3, 1, '部署管理', 'Deploy', 'mnt/deploy/index', 24, 'deploy', 'mnt/deploy', 'f', 'f', 'f', 'deploy:list', NULL, NULL, '2019-11-10 15:56:55', NULL);
INSERT INTO "sys_menu" VALUES (97, 90, 1, 1, '部署备份', 'DeployHistory', 'mnt/deployHistory/index', 25, 'backup', 'mnt/deployHistory', 'f', 'f', 'f', 'deployHistory:list', NULL, NULL, '2019-11-10 16:49:44', NULL);
INSERT INTO "sys_menu" VALUES (98, 90, 3, 1, '数据库管理', 'Database', 'mnt/database/index', 26, 'database', 'mnt/database', 'f', 'f', 'f', 'database:list', NULL, NULL, '2019-11-10 20:40:04', NULL);
INSERT INTO "sys_menu" VALUES (102, 97, 0, 2, '删除', NULL, '', 999, '', '', 'f', 'f', 'f', 'deployHistory:del', NULL, NULL, '2019-11-17 09:32:48', NULL);
INSERT INTO "sys_menu" VALUES (103, 92, 0, 2, '服务器新增', NULL, '', 999, '', '', 'f', 'f', 'f', 'serverDeploy:add', NULL, NULL, '2019-11-17 11:08:33', NULL);
INSERT INTO "sys_menu" VALUES (104, 92, 0, 2, '服务器编辑', NULL, '', 999, '', '', 'f', 'f', 'f', 'serverDeploy:edit', NULL, NULL, '2019-11-17 11:08:57', NULL);
INSERT INTO "sys_menu" VALUES (1, NULL, 8, 0, '系统管理', NULL, NULL, 1, 'system', 'system', 'f', 'f', 'f', NULL, NULL, NULL, '2018-12-18 15:11:29', NULL);
INSERT INTO "sys_menu" VALUES (105, 92, 0, 2, '服务器删除', NULL, '', 999, '', '', 'f', 'f', 'f', 'serverDeploy:del', NULL, NULL, '2019-11-17 11:09:15', NULL);
INSERT INTO "sys_menu" VALUES (106, 93, 0, 2, '应用新增', NULL, '', 999, '', '', 'f', 'f', 'f', 'app:add', NULL, NULL, '2019-11-17 11:10:03', NULL);
INSERT INTO "sys_menu" VALUES (107, 93, 0, 2, '应用编辑', NULL, '', 999, '', '', 'f', 'f', 'f', 'app:edit', NULL, NULL, '2019-11-17 11:10:28', NULL);
INSERT INTO "sys_menu" VALUES (108, 93, 0, 2, '应用删除', NULL, '', 999, '', '', 'f', 'f', 'f', 'app:del', NULL, NULL, '2019-11-17 11:10:55', NULL);
INSERT INTO "sys_menu" VALUES (109, 94, 0, 2, '部署新增', NULL, '', 999, '', '', 'f', 'f', 'f', 'deploy:add', NULL, NULL, '2019-11-17 11:11:22', NULL);
INSERT INTO "sys_menu" VALUES (110, 94, 0, 2, '部署编辑', NULL, '', 999, '', '', 'f', 'f', 'f', 'deploy:edit', NULL, NULL, '2019-11-17 11:11:41', NULL);
INSERT INTO "sys_menu" VALUES (111, 94, 0, 2, '部署删除', NULL, '', 999, '', '', 'f', 'f', 'f', 'deploy:del', NULL, NULL, '2019-11-17 11:12:01', NULL);
INSERT INTO "sys_menu" VALUES (112, 98, 0, 2, '数据库新增', NULL, '', 999, '', '', 'f', 'f', 'f', 'database:add', NULL, NULL, '2019-11-17 11:12:43', NULL);
INSERT INTO "sys_menu" VALUES (113, 98, 0, 2, '数据库编辑', NULL, '', 999, '', '', 'f', 'f', 'f', 'database:edit', NULL, NULL, '2019-11-17 11:12:58', NULL);
INSERT INTO "sys_menu" VALUES (114, 98, 0, 2, '数据库删除', NULL, '', 999, '', '', 'f', 'f', 'f', 'database:del', NULL, NULL, '2019-11-17 11:13:14', NULL);
INSERT INTO "sys_menu" VALUES (116, 36, 0, 1, '生成预览', 'Preview', 'generator/preview', 999, 'java', 'generator/preview/:tableName', 'f', 't', 't', NULL, NULL, NULL, '2019-11-26 14:54:36', NULL);
INSERT INTO "sys_menu" VALUES (125, NULL, 4, 0, '文章管理', NULL, NULL, 999, 'develop', 'cms', 'f', 'f', 'f', NULL, 'admin', 'admin', '2021-11-30 14:54:18', '2021-11-30 14:54:18');
INSERT INTO "sys_menu" VALUES (126, 125, 3, 1, '文章列表', NULL, 'cms/index', 999, NULL, 'cmsIndex', 'f', 'f', 'f', 'cms:list', 'admin', 'admin', '2021-11-30 14:55:17', '2021-11-30 19:42:02');
INSERT INTO "sys_menu" VALUES (127, 125, 0, 1, '文章发布', 'AddCms', 'cms/insert', 999, NULL, 'addCms', 'f', 'f', 't', 'cms:add', 'admin', 'admin', '2021-11-30 15:18:38', '2021-11-30 18:59:19');
INSERT INTO "sys_menu" VALUES (128, 125, 0, 1, '修改文章', 'EditCms', 'cms/editorCms', 999, NULL, 'editCms', 'f', 'f', 't', 'cms:edit', 'admin', 'admin', '2021-11-30 15:19:05', '2021-11-30 18:59:30');
INSERT INTO "sys_menu" VALUES (129, 125, 4, 1, '栏目管理', 'CmsColumn', 'column/index', 999, NULL, 'cmsColumn', 'f', 'f', 'f', 'cmsColumn:list', 'admin', 'admin', '2021-11-30 16:44:28', '2021-11-30 19:53:20');
INSERT INTO "sys_menu" VALUES (130, 126, 0, 2, '审核', NULL, NULL, 999, NULL, NULL, 'f', 'f', 'f', 'cms:edit', 'admin', 'admin', '2021-11-30 19:11:56', '2021-11-30 19:36:17');
INSERT INTO "sys_menu" VALUES (131, 126, 0, 2, '删除文章', NULL, NULL, 999, NULL, NULL, 'f', 'f', 'f', 'cms:del', 'admin', 'admin', '2021-11-30 19:45:46', '2021-11-30 19:45:46');
INSERT INTO "sys_menu" VALUES (132, 126, 0, 2, '新增cms', NULL, NULL, 999, NULL, NULL, 'f', 'f', 'f', 'cms:add', 'admin', 'admin', '2021-11-30 19:46:35', '2021-11-30 19:46:54');
INSERT INTO "sys_menu" VALUES (133, 129, 0, 2, '新增顶级栏目', NULL, NULL, 999, NULL, NULL, 'f', 'f', 'f', 'cmsColumn:addFirstLevelColumn', 'admin', 'admin', '2021-11-30 19:54:29', '2021-11-30 19:54:29');
INSERT INTO "sys_menu" VALUES (134, 129, 0, 2, '新增栏目', NULL, NULL, 999, NULL, NULL, 'f', 'f', 'f', 'cmsColumn:add', 'admin', 'admin', '2021-11-30 19:54:52', '2021-11-30 19:54:52');
INSERT INTO "sys_menu" VALUES (135, 129, 0, 2, '删除栏目', NULL, NULL, 999, NULL, NULL, 'f', 'f', 'f', 'cmsColumn:del', 'admin', 'admin', '2021-11-30 19:55:15', '2021-11-30 19:55:15');
INSERT INTO "sys_menu" VALUES (136, 129, 0, 2, '修改栏目', NULL, NULL, 999, NULL, NULL, 'f', 'f', 'f', 'cmsColumn:edit', 'admin', 'admin', '2021-11-30 19:55:34', '2021-11-30 19:55:34');
INSERT INTO "sys_menu" VALUES (137, 41, 0, 2, '踢出在线用户', NULL, NULL, 0, NULL, NULL, 'f', 'f', 'f', 'online:del', 'admin', 'admin', '2021-12-10 15:10:11', '2021-12-10 15:54:35');
INSERT INTO "sys_menu" VALUES (138, 7, 0, 2, '清空info日志', NULL, NULL, 0, NULL, NULL, 'f', 'f', 'f', 'logs:del', 'admin', 'admin', '2021-12-10 15:52:50', '2021-12-10 15:54:09');
INSERT INTO "sys_menu" VALUES (139, 32, 0, 2, '清空error日志', NULL, NULL, 0, NULL, NULL, 'f', 'f', 'f', 'errorlogs:del', 'admin', 'admin', '2021-12-10 15:53:33', '2021-12-10 15:53:33');
INSERT INTO "sys_menu" VALUES (140, 32, 0, 2, '查看error日志详情', NULL, NULL, 1, NULL, NULL, 'f', 'f', 'f', 'errorlogs:details', 'admin', 'admin', '2021-12-10 15:53:56', '2021-12-10 15:53:56');
INSERT INTO "sys_menu" VALUES (141, 14, 0, 2, '配置邮件', NULL, NULL, 0, NULL, NULL, 'f', 'f', 'f', 'email:configure', 'admin', 'admin', '2021-12-10 16:09:17', '2021-12-10 16:09:17');
INSERT INTO "sys_menu" VALUES (142, 14, 0, 2, '发送邮件', NULL, NULL, 1, NULL, NULL, 'f', 'f', 'f', 'email:send', 'admin', 'admin', '2021-12-10 16:09:43', '2021-12-10 16:09:43');
INSERT INTO "sys_menu" VALUES (143, 19, 0, 2, '配置支付宝', NULL, NULL, 0, NULL, NULL, 'f', 'f', 'f', 'aliPay:configure', 'admin', 'admin', '2021-12-10 16:10:04', '2021-12-10 16:10:04');
INSERT INTO "sys_menu" VALUES (144, 19, 0, 2, '支付宝测试', NULL, NULL, 1, NULL, NULL, 'f', 'f', 'f', 'aliPay:test', 'admin', 'admin', '2021-12-10 16:10:29', '2021-12-10 16:10:29');
INSERT INTO "sys_menu" VALUES (147, 146, 4, 1, 'app版本', NULL, 'appVersion/index', 999, NULL, '/sysSet/appVer', 'f', 'f', 'f', 'version:list', 'test', 'test', '2022-03-02 16:01:26', '2022-03-02 16:07:34');
INSERT INTO "sys_menu" VALUES (148, 147, 0, 2, 'app版本添加', NULL, NULL, 999, NULL, NULL, 'f', 'f', 'f', 'version:add', 'test', 'test', '2022-03-02 16:01:26', '2022-03-02 16:01:26');
INSERT INTO "sys_menu" VALUES (149, 147, 0, 2, 'app版本修改', NULL, NULL, 999, NULL, NULL, 'f', 'f', 'f', 'version:edit', 'test', 'test', '2022-03-02 16:01:26', '2022-03-02 16:01:26');
INSERT INTO "sys_menu" VALUES (150, 147, 0, 2, 'app版本删除', NULL, NULL, 999, NULL, NULL, 'f', 'f', 'f', 'version:del', 'test', 'test', '2022-03-02 16:01:26', '2022-03-02 16:01:26');
INSERT INTO "sys_menu" VALUES (151, 147, 0, 2, 'app版本导入', NULL, NULL, 999, NULL, NULL, 'f', 'f', 'f', 'version:importData', 'test', 'test', '2022-03-02 16:01:27', '2022-03-02 16:01:27');
INSERT INTO "sys_menu" VALUES (36, NULL, 7, 0, '系统工具', NULL, '', 30, 'sys-tools', 'sys-tools', 'f', 'f', 'f', NULL, NULL, NULL, '2019-03-29 10:57:35', NULL);
INSERT INTO "sys_menu" VALUES (157, 154, 0, 2, '版本管理删除', NULL, NULL, 999, NULL, NULL, 'f', 'f', 'f', 'appVersionV2:del', 'admin', 'admin', '2022-05-16 13:04:31.152', '2022-05-16 13:04:31.152');
INSERT INTO "sys_menu" VALUES (158, 154, 0, 2, '版本管理导入', NULL, NULL, 999, NULL, NULL, 'f', 'f', 'f', 'appVersionV2:importData', 'admin', 'admin', '2022-05-16 13:04:31.186', '2022-05-16 13:04:31.186');
INSERT INTO "sys_menu" VALUES (154, 1, 4, 1, '版本管理', NULL, 'appVersionV2/index', 999, NULL, 'systemSet/appverv2', 'f', 'f', 'f', 'appVersionV2:list', 'admin', 'admin', '2022-05-16 13:04:31.021', '2022-05-16 13:04:31.021');
INSERT INTO "sys_menu" VALUES (4, 18, 0, 2, '修改minio配置', NULL, NULL, 5, NULL, NULL, 'f', 'f', 'f', 'minioConfig:edit', 'test', 'test', '2022-05-10 15:18:20.199', '2022-05-10 15:43:54.138');
INSERT INTO "sys_menu" VALUES (18, 36, 4, 1, '存储管理', 'Storage', 'tools/storage/index', 34, 'qiniu', 'storage', 'f', 'f', 'f', 'storage:list', NULL, 'test', '2018-12-31 11:12:15', '2022-05-10 15:16:30.565');
INSERT INTO "sys_menu" VALUES (155, 154, 0, 2, '版本管理添加', NULL, NULL, 999, NULL, NULL, 'f', 'f', 'f', 'appVersionV2:add', 'admin', 'admin', '2022-05-16 13:04:31.077', '2022-05-16 13:04:31.077');
INSERT INTO "sys_menu" VALUES (156, 154, 0, 2, '版本管理修改', NULL, NULL, 999, NULL, NULL, 'f', 'f', 'f', 'appVersionV2:edit', 'admin', 'admin', '2022-05-16 13:04:31.117', '2022-05-16 13:04:31.117');
COMMIT;

-- ----------------------------
-- Table structure for sys_quartz_job
-- ----------------------------
DROP TABLE IF EXISTS "sys_quartz_job";
CREATE TABLE "sys_quartz_job" (
  "job_id" int4 NOT NULL DEFAULT nextval('eladmin_template_schema.sys_quartz_job_job_id_seq'::regclass),
  "bean_name" varchar COLLATE "pg_catalog"."default",
  "cron_expression" varchar COLLATE "pg_catalog"."default",
  "is_pause" bool,
  "job_name" varchar COLLATE "pg_catalog"."default",
  "method_name" varchar COLLATE "pg_catalog"."default",
  "params" varchar COLLATE "pg_catalog"."default",
  "description" varchar COLLATE "pg_catalog"."default",
  "person_in_charge" varchar COLLATE "pg_catalog"."default",
  "email" varchar COLLATE "pg_catalog"."default",
  "sub_task" varchar COLLATE "pg_catalog"."default",
  "pause_after_failure" varchar COLLATE "pg_catalog"."default",
  "create_by" varchar COLLATE "pg_catalog"."default",
  "update_by" varchar COLLATE "pg_catalog"."default",
  "create_time" timestamp(6),
  "update_time" timestamp(6)
)
;
COMMENT ON COLUMN "sys_quartz_job"."job_id" IS 'ID';
COMMENT ON COLUMN "sys_quartz_job"."bean_name" IS 'Spring Bean名称';
COMMENT ON COLUMN "sys_quartz_job"."cron_expression" IS 'cron 表达式';
COMMENT ON COLUMN "sys_quartz_job"."is_pause" IS '状态：1暂停、0启用';
COMMENT ON COLUMN "sys_quartz_job"."job_name" IS '任务名称';
COMMENT ON COLUMN "sys_quartz_job"."method_name" IS '方法名称';
COMMENT ON COLUMN "sys_quartz_job"."params" IS '参数';
COMMENT ON COLUMN "sys_quartz_job"."description" IS '备注';
COMMENT ON COLUMN "sys_quartz_job"."person_in_charge" IS '负责人';
COMMENT ON COLUMN "sys_quartz_job"."email" IS '报警邮箱';
COMMENT ON COLUMN "sys_quartz_job"."sub_task" IS '子任务ID';
COMMENT ON COLUMN "sys_quartz_job"."pause_after_failure" IS '任务失败后是否暂停';
COMMENT ON COLUMN "sys_quartz_job"."create_by" IS '创建者';
COMMENT ON COLUMN "sys_quartz_job"."update_by" IS '更新者';
COMMENT ON COLUMN "sys_quartz_job"."create_time" IS '创建日期';
COMMENT ON COLUMN "sys_quartz_job"."update_time" IS '更新时间';
COMMENT ON TABLE "sys_quartz_job" IS '定时任务';

-- ----------------------------
-- Records of sys_quartz_job
-- ----------------------------
BEGIN;
INSERT INTO "sys_quartz_job" VALUES (2, 'testTask', '0/5 * * * * ?', 't', '测试1', 'run1', 'test', '带参测试，多参使用json', '测试', NULL, NULL, NULL, NULL, 'admin', '2019-08-22 14:08:29', '2020-05-24 13:58:33');
INSERT INTO "sys_quartz_job" VALUES (3, 'testTask', '0/5 * * * * ?', 't', '测试', 'run', '', '不带参测试', 'Zheng Jie', '', '5,6', '1', NULL, 'admin', '2019-09-26 16:44:39', '2020-05-24 14:48:12');
INSERT INTO "sys_quartz_job" VALUES (5, 'Test', '0/5 * * * * ?', 't', '任务告警测试', 'run', NULL, '测试', 'test', '', NULL, '1', 'admin', 'admin', '2020-05-05 20:32:41', '2020-05-05 20:36:13');
INSERT INTO "sys_quartz_job" VALUES (6, 'testTask', '0/5 * * * * ?', 't', '测试3', 'run2', NULL, '测试3', 'Zheng Jie', '', NULL, '1', 'admin', 'admin', '2020-05-05 20:35:41', '2020-05-05 20:36:07');
COMMIT;

-- ----------------------------
-- Table structure for sys_quartz_log
-- ----------------------------
DROP TABLE IF EXISTS "sys_quartz_log";
CREATE TABLE "sys_quartz_log" (
  "log_id" int8 NOT NULL DEFAULT nextval('eladmin_template_schema.sys_quartz_log_log_id_seq'::regclass),
  "bean_name" varchar(255) COLLATE "pg_catalog"."default",
  "create_time" timestamp(6),
  "cron_expression" varchar(255) COLLATE "pg_catalog"."default",
  "exception_detail" text COLLATE "pg_catalog"."default",
  "is_success" bool,
  "job_name" varchar(255) COLLATE "pg_catalog"."default",
  "method_name" varchar(255) COLLATE "pg_catalog"."default",
  "params" varchar(255) COLLATE "pg_catalog"."default",
  "time" int8
)
;
COMMENT ON COLUMN "sys_quartz_log"."log_id" IS 'ID';
COMMENT ON TABLE "sys_quartz_log" IS '定时任务日志';

-- ----------------------------
-- Records of sys_quartz_log
-- ----------------------------
BEGIN;
COMMIT;

-- ----------------------------
-- Table structure for sys_role
-- ----------------------------
DROP TABLE IF EXISTS "sys_role";
CREATE TABLE "sys_role" (
  "role_id" int4 NOT NULL DEFAULT nextval('eladmin_template_schema.sys_role_role_id_seq'::regclass),
  "name" varchar(255) COLLATE "pg_catalog"."default" NOT NULL,
  "level" int4,
  "description" varchar(255) COLLATE "pg_catalog"."default",
  "data_scope" varchar(255) COLLATE "pg_catalog"."default",
  "create_by" varchar(255) COLLATE "pg_catalog"."default",
  "update_by" varchar(255) COLLATE "pg_catalog"."default",
  "create_time" timestamp(6),
  "update_time" timestamp(6)
)
;
COMMENT ON COLUMN "sys_role"."role_id" IS 'ID';
COMMENT ON COLUMN "sys_role"."name" IS '名称';
COMMENT ON COLUMN "sys_role"."level" IS '角色级别';
COMMENT ON COLUMN "sys_role"."description" IS '描述';
COMMENT ON COLUMN "sys_role"."data_scope" IS '数据权限';
COMMENT ON COLUMN "sys_role"."create_by" IS '创建者';
COMMENT ON COLUMN "sys_role"."update_by" IS '更新者';
COMMENT ON COLUMN "sys_role"."create_time" IS '创建日期';
COMMENT ON COLUMN "sys_role"."update_time" IS '更新时间';
COMMENT ON TABLE "sys_role" IS '角色表';

-- ----------------------------
-- Records of sys_role
-- ----------------------------
BEGIN;
INSERT INTO "sys_role" VALUES (2, '普通用户', 2, '-', '本级', NULL, 'test', '2018-11-23 13:09:06', '2022-04-18 13:49:47.197');
INSERT INTO "sys_role" VALUES (1, '超级管理员', 1, '-', '自定义', NULL, 'admin', '2018-11-23 11:04:37', '2022-05-16 13:04:31.708');
COMMIT;

-- ----------------------------
-- Table structure for sys_roles_depts
-- ----------------------------
DROP TABLE IF EXISTS "sys_roles_depts";
CREATE TABLE "sys_roles_depts" (
  "role_id" int8 NOT NULL,
  "dept_id" int8 NOT NULL
)
;
COMMENT ON TABLE "sys_roles_depts" IS '角色部门关联';

-- ----------------------------
-- Records of sys_roles_depts
-- ----------------------------
BEGIN;
COMMIT;

-- ----------------------------
-- Table structure for sys_roles_menus
-- ----------------------------
DROP TABLE IF EXISTS "sys_roles_menus";
CREATE TABLE "sys_roles_menus" (
  "menu_id" int8 NOT NULL,
  "role_id" int8 NOT NULL
)
;
COMMENT ON COLUMN "sys_roles_menus"."menu_id" IS '菜单ID';
COMMENT ON COLUMN "sys_roles_menus"."role_id" IS '角色ID';
COMMENT ON TABLE "sys_roles_menus" IS '角色菜单关联';

-- ----------------------------
-- Records of sys_roles_menus
-- ----------------------------
BEGIN;
INSERT INTO "sys_roles_menus" VALUES (1, 1);
INSERT INTO "sys_roles_menus" VALUES (2, 1);
INSERT INTO "sys_roles_menus" VALUES (3, 1);
INSERT INTO "sys_roles_menus" VALUES (5, 1);
INSERT INTO "sys_roles_menus" VALUES (6, 1);
INSERT INTO "sys_roles_menus" VALUES (7, 1);
INSERT INTO "sys_roles_menus" VALUES (9, 1);
INSERT INTO "sys_roles_menus" VALUES (10, 1);
INSERT INTO "sys_roles_menus" VALUES (11, 1);
INSERT INTO "sys_roles_menus" VALUES (14, 1);
INSERT INTO "sys_roles_menus" VALUES (15, 1);
INSERT INTO "sys_roles_menus" VALUES (18, 1);
INSERT INTO "sys_roles_menus" VALUES (19, 1);
INSERT INTO "sys_roles_menus" VALUES (28, 1);
INSERT INTO "sys_roles_menus" VALUES (30, 1);
INSERT INTO "sys_roles_menus" VALUES (32, 1);
INSERT INTO "sys_roles_menus" VALUES (33, 1);
INSERT INTO "sys_roles_menus" VALUES (34, 1);
INSERT INTO "sys_roles_menus" VALUES (35, 1);
INSERT INTO "sys_roles_menus" VALUES (36, 1);
INSERT INTO "sys_roles_menus" VALUES (37, 1);
INSERT INTO "sys_roles_menus" VALUES (38, 1);
INSERT INTO "sys_roles_menus" VALUES (39, 1);
INSERT INTO "sys_roles_menus" VALUES (41, 1);
INSERT INTO "sys_roles_menus" VALUES (44, 1);
INSERT INTO "sys_roles_menus" VALUES (45, 1);
INSERT INTO "sys_roles_menus" VALUES (46, 1);
INSERT INTO "sys_roles_menus" VALUES (48, 1);
INSERT INTO "sys_roles_menus" VALUES (49, 1);
INSERT INTO "sys_roles_menus" VALUES (50, 1);
INSERT INTO "sys_roles_menus" VALUES (52, 1);
INSERT INTO "sys_roles_menus" VALUES (53, 1);
INSERT INTO "sys_roles_menus" VALUES (54, 1);
INSERT INTO "sys_roles_menus" VALUES (56, 1);
INSERT INTO "sys_roles_menus" VALUES (57, 1);
INSERT INTO "sys_roles_menus" VALUES (58, 1);
INSERT INTO "sys_roles_menus" VALUES (60, 1);
INSERT INTO "sys_roles_menus" VALUES (61, 1);
INSERT INTO "sys_roles_menus" VALUES (62, 1);
INSERT INTO "sys_roles_menus" VALUES (64, 1);
INSERT INTO "sys_roles_menus" VALUES (65, 1);
INSERT INTO "sys_roles_menus" VALUES (66, 1);
INSERT INTO "sys_roles_menus" VALUES (73, 1);
INSERT INTO "sys_roles_menus" VALUES (74, 1);
INSERT INTO "sys_roles_menus" VALUES (75, 1);
INSERT INTO "sys_roles_menus" VALUES (77, 1);
INSERT INTO "sys_roles_menus" VALUES (78, 1);
INSERT INTO "sys_roles_menus" VALUES (79, 1);
INSERT INTO "sys_roles_menus" VALUES (80, 1);
INSERT INTO "sys_roles_menus" VALUES (82, 1);
INSERT INTO "sys_roles_menus" VALUES (83, 1);
INSERT INTO "sys_roles_menus" VALUES (90, 1);
INSERT INTO "sys_roles_menus" VALUES (92, 1);
INSERT INTO "sys_roles_menus" VALUES (93, 1);
INSERT INTO "sys_roles_menus" VALUES (94, 1);
INSERT INTO "sys_roles_menus" VALUES (97, 1);
INSERT INTO "sys_roles_menus" VALUES (98, 1);
INSERT INTO "sys_roles_menus" VALUES (102, 1);
INSERT INTO "sys_roles_menus" VALUES (103, 1);
INSERT INTO "sys_roles_menus" VALUES (104, 1);
INSERT INTO "sys_roles_menus" VALUES (105, 1);
INSERT INTO "sys_roles_menus" VALUES (106, 1);
INSERT INTO "sys_roles_menus" VALUES (107, 1);
INSERT INTO "sys_roles_menus" VALUES (108, 1);
INSERT INTO "sys_roles_menus" VALUES (109, 1);
INSERT INTO "sys_roles_menus" VALUES (110, 1);
INSERT INTO "sys_roles_menus" VALUES (111, 1);
INSERT INTO "sys_roles_menus" VALUES (112, 1);
INSERT INTO "sys_roles_menus" VALUES (113, 1);
INSERT INTO "sys_roles_menus" VALUES (114, 1);
INSERT INTO "sys_roles_menus" VALUES (116, 1);
INSERT INTO "sys_roles_menus" VALUES (125, 1);
INSERT INTO "sys_roles_menus" VALUES (126, 1);
INSERT INTO "sys_roles_menus" VALUES (127, 1);
INSERT INTO "sys_roles_menus" VALUES (128, 1);
INSERT INTO "sys_roles_menus" VALUES (129, 1);
INSERT INTO "sys_roles_menus" VALUES (130, 1);
INSERT INTO "sys_roles_menus" VALUES (131, 1);
INSERT INTO "sys_roles_menus" VALUES (132, 1);
INSERT INTO "sys_roles_menus" VALUES (133, 1);
INSERT INTO "sys_roles_menus" VALUES (134, 1);
INSERT INTO "sys_roles_menus" VALUES (135, 1);
INSERT INTO "sys_roles_menus" VALUES (136, 1);
INSERT INTO "sys_roles_menus" VALUES (137, 1);
INSERT INTO "sys_roles_menus" VALUES (138, 1);
INSERT INTO "sys_roles_menus" VALUES (139, 1);
INSERT INTO "sys_roles_menus" VALUES (140, 1);
INSERT INTO "sys_roles_menus" VALUES (141, 1);
INSERT INTO "sys_roles_menus" VALUES (142, 1);
INSERT INTO "sys_roles_menus" VALUES (143, 1);
INSERT INTO "sys_roles_menus" VALUES (144, 1);
INSERT INTO "sys_roles_menus" VALUES (147, 1);
INSERT INTO "sys_roles_menus" VALUES (148, 1);
INSERT INTO "sys_roles_menus" VALUES (149, 1);
INSERT INTO "sys_roles_menus" VALUES (150, 1);
INSERT INTO "sys_roles_menus" VALUES (151, 1);
INSERT INTO "sys_roles_menus" VALUES (1, 2);
INSERT INTO "sys_roles_menus" VALUES (2, 2);
INSERT INTO "sys_roles_menus" VALUES (4, 1);
INSERT INTO "sys_roles_menus" VALUES (154, 1);
INSERT INTO "sys_roles_menus" VALUES (155, 1);
INSERT INTO "sys_roles_menus" VALUES (156, 1);
INSERT INTO "sys_roles_menus" VALUES (157, 1);
INSERT INTO "sys_roles_menus" VALUES (158, 1);
COMMIT;

-- ----------------------------
-- Table structure for sys_user
-- ----------------------------
DROP TABLE IF EXISTS "sys_user";
CREATE TABLE "sys_user" (
  "user_id" int4 NOT NULL DEFAULT nextval('eladmin_template_schema.sys_user_user_id_seq'::regclass),
  "dept_id" int8,
  "username" varchar(255) COLLATE "pg_catalog"."default",
  "nick_name" varchar(255) COLLATE "pg_catalog"."default",
  "gender" varchar(2) COLLATE "pg_catalog"."default",
  "phone" varchar(255) COLLATE "pg_catalog"."default",
  "email" varchar(255) COLLATE "pg_catalog"."default",
  "avatar_name" varchar(255) COLLATE "pg_catalog"."default",
  "avatar_path" varchar(255) COLLATE "pg_catalog"."default",
  "password" varchar(255) COLLATE "pg_catalog"."default",
  "is_admin" bool,
  "enabled" bool,
  "create_by" varchar(255) COLLATE "pg_catalog"."default",
  "update_by" varchar(255) COLLATE "pg_catalog"."default",
  "pwd_reset_time" timestamp(6),
  "create_time" timestamp(6),
  "update_time" timestamp(6)
)
;
COMMENT ON COLUMN "sys_user"."user_id" IS 'ID';
COMMENT ON COLUMN "sys_user"."dept_id" IS '部门名称';
COMMENT ON COLUMN "sys_user"."username" IS '用户名';
COMMENT ON COLUMN "sys_user"."nick_name" IS '昵称';
COMMENT ON COLUMN "sys_user"."gender" IS '性别';
COMMENT ON COLUMN "sys_user"."phone" IS '手机号码';
COMMENT ON COLUMN "sys_user"."email" IS '邮箱';
COMMENT ON COLUMN "sys_user"."avatar_name" IS '头像地址';
COMMENT ON COLUMN "sys_user"."avatar_path" IS '头像真实路径';
COMMENT ON COLUMN "sys_user"."password" IS '密码';
COMMENT ON COLUMN "sys_user"."is_admin" IS '是否为admin账号';
COMMENT ON COLUMN "sys_user"."enabled" IS '状态：1启用、0禁用';
COMMENT ON COLUMN "sys_user"."create_by" IS '创建者';
COMMENT ON COLUMN "sys_user"."update_by" IS '更新者';
COMMENT ON COLUMN "sys_user"."pwd_reset_time" IS '修改密码的时间';
COMMENT ON COLUMN "sys_user"."create_time" IS '创建日期';
COMMENT ON COLUMN "sys_user"."update_time" IS '更新时间';
COMMENT ON TABLE "sys_user" IS '系统用户';

-- ----------------------------
-- Records of sys_user
-- ----------------------------
BEGIN;
INSERT INTO "sys_user" VALUES (2, 2, 'test', '测试1', '男', '18888888882', '231@qq.com', NULL, NULL, '$2a$10$dBgPJaqznDC/W3xRfyUoPuosqIQqYKOTYHcnjz1F/ydctURcELgaS', 'f', 't', 'admin', 'test1', '2021-12-15 14:48:52', '2020-05-05 11:15:49', '2022-05-09 10:38:59.672');
INSERT INTO "sys_user" VALUES (1, 2, 'admin', '管理员', '男', '18888888888', '201507802@qq.com', 'avatar.png', 'http://118.25.95.207:9000/eladmin/2021-11-30/a5a676dd-3b31-46d1-a906-4d0cf5762e4d.png', '$2a$10$nz5y.NQnk50QsUCtgtgjlOqEnnzH.WMzBqiE51FA7C49KcEu3UfG6', 'f', 't', NULL, 'test', '2021-12-09 17:43:28', '2018-08-23 09:11:56', '2022-05-10 16:05:00.849');
COMMIT;

-- ----------------------------
-- Table structure for sys_users_jobs
-- ----------------------------
DROP TABLE IF EXISTS "sys_users_jobs";
CREATE TABLE "sys_users_jobs" (
  "user_id" int8 NOT NULL,
  "job_id" int8 NOT NULL
)
;
COMMENT ON COLUMN "sys_users_jobs"."user_id" IS '用户ID';
COMMENT ON COLUMN "sys_users_jobs"."job_id" IS '岗位ID';
COMMENT ON TABLE "sys_users_jobs" IS '岗位关联表';

-- ----------------------------
-- Records of sys_users_jobs
-- ----------------------------
BEGIN;
INSERT INTO "sys_users_jobs" VALUES (2, 12);
INSERT INTO "sys_users_jobs" VALUES (1, 11);
COMMIT;

-- ----------------------------
-- Table structure for sys_users_roles
-- ----------------------------
DROP TABLE IF EXISTS "sys_users_roles";
CREATE TABLE "sys_users_roles" (
  "user_id" int8 NOT NULL,
  "role_id" int8 NOT NULL
)
;
COMMENT ON COLUMN "sys_users_roles"."user_id" IS '用户ID';
COMMENT ON COLUMN "sys_users_roles"."role_id" IS '角色ID';
COMMENT ON TABLE "sys_users_roles" IS '用户角色关联';

-- ----------------------------
-- Records of sys_users_roles
-- ----------------------------
BEGIN;
INSERT INTO "sys_users_roles" VALUES (2, 1);
INSERT INTO "sys_users_roles" VALUES (1, 1);
COMMIT;

-- ----------------------------
-- Table structure for t_cms
-- ----------------------------
DROP TABLE IF EXISTS "t_cms";
CREATE TABLE "t_cms" (
  "cms_id" int4 NOT NULL DEFAULT nextval('eladmin_template_schema.t_cms_cms_id_seq'::regclass),
  "agent_id" int8,
  "column_id" int8,
  "title" varchar(255) COLLATE "pg_catalog"."default",
  "author" varchar(255) COLLATE "pg_catalog"."default",
  "content" text COLLATE "pg_catalog"."default",
  "cms_status" int2,
  "audit_person" varchar(255) COLLATE "pg_catalog"."default",
  "audit_status" int2,
  "audit_time" timestamp(6),
  "audit_proposal" varchar(255) COLLATE "pg_catalog"."default",
  "publish_time" timestamp(6),
  "create_time" timestamp(6),
  "update_time" timestamp(6),
  "is_deleted" bool NOT NULL DEFAULT false,
  "annex_url" varchar(255) COLLATE "pg_catalog"."default",
  "by2" varchar(255) COLLATE "pg_catalog"."default",
  "by3" varchar(255) COLLATE "pg_catalog"."default",
  "by4" varchar(255) COLLATE "pg_catalog"."default",
  "by5" varchar(255) COLLATE "pg_catalog"."default"
)
;
COMMENT ON COLUMN "t_cms"."cms_id" IS '自增主键ID';
COMMENT ON COLUMN "t_cms"."agent_id" IS '代理商id';
COMMENT ON COLUMN "t_cms"."column_id" IS '栏目';
COMMENT ON COLUMN "t_cms"."title" IS '标题';
COMMENT ON COLUMN "t_cms"."author" IS '作者';
COMMENT ON COLUMN "t_cms"."content" IS '文章内容';
COMMENT ON COLUMN "t_cms"."cms_status" IS '文章状态';
COMMENT ON COLUMN "t_cms"."audit_person" IS '审核人';
COMMENT ON COLUMN "t_cms"."audit_status" IS '审核状态';
COMMENT ON COLUMN "t_cms"."audit_time" IS '审核时间';
COMMENT ON COLUMN "t_cms"."audit_proposal" IS '审核意见';
COMMENT ON COLUMN "t_cms"."publish_time" IS '发布时间';
COMMENT ON COLUMN "t_cms"."create_time" IS '创建时间';
COMMENT ON COLUMN "t_cms"."update_time" IS '修改时间';
COMMENT ON COLUMN "t_cms"."is_deleted" IS '1 表示删除，0 表示未删除';
COMMENT ON COLUMN "t_cms"."annex_url" IS '附件url';
COMMENT ON TABLE "t_cms" IS '文章表';

-- ----------------------------
-- Records of t_cms
-- ----------------------------
BEGIN;
COMMIT;

-- ----------------------------
-- Table structure for t_cms_column
-- ----------------------------
DROP TABLE IF EXISTS "t_cms_column";
CREATE TABLE "t_cms_column" (
  "column_id" int4 NOT NULL DEFAULT nextval('eladmin_template_schema.t_cms_column_column_id_seq'::regclass),
  "fid" int8,
  "column_name" varchar(255) COLLATE "pg_catalog"."default",
  "agent_id" int8,
  "create_time" timestamp(6),
  "update_time" timestamp(6),
  "is_deleted" bool NOT NULL DEFAULT false,
  "by1" varchar(255) COLLATE "pg_catalog"."default",
  "by2" varchar(255) COLLATE "pg_catalog"."default",
  "by3" varchar(255) COLLATE "pg_catalog"."default",
  "by4" varchar(255) COLLATE "pg_catalog"."default",
  "by5" varchar(255) COLLATE "pg_catalog"."default"
)
;
COMMENT ON COLUMN "t_cms_column"."column_id" IS '自增主键ID';
COMMENT ON COLUMN "t_cms_column"."fid" IS '父id';
COMMENT ON COLUMN "t_cms_column"."column_name" IS '栏目名';
COMMENT ON COLUMN "t_cms_column"."agent_id" IS '代理商id';
COMMENT ON COLUMN "t_cms_column"."create_time" IS '创建时间';
COMMENT ON COLUMN "t_cms_column"."update_time" IS '修改时间';
COMMENT ON COLUMN "t_cms_column"."is_deleted" IS '1 表示删除，0 表示未删除';
COMMENT ON TABLE "t_cms_column" IS '栏目表';

-- ----------------------------
-- Records of t_cms_column
-- ----------------------------
BEGIN;
COMMIT;

-- ----------------------------
-- Table structure for t_message_notification
-- ----------------------------
DROP TABLE IF EXISTS "t_message_notification";
CREATE TABLE "t_message_notification" (
  "id" int4 NOT NULL DEFAULT nextval('eladmin_template_schema.t_message_notification_id_seq'::regclass),
  "user_id" int8,
  "briefly" varchar(255) COLLATE "pg_catalog"."default",
  "details" varchar(255) COLLATE "pg_catalog"."default",
  "message_level" int2,
  "message_label" int2,
  "message_state" int2,
  "create_date" timestamp(6),
  "update_date" timestamp(6),
  "is_deleted" bool NOT NULL DEFAULT false,
  "by1" varchar(255) COLLATE "pg_catalog"."default",
  "by2" varchar(255) COLLATE "pg_catalog"."default",
  "by3" varchar(255) COLLATE "pg_catalog"."default",
  "by4" varchar(255) COLLATE "pg_catalog"."default",
  "by5" varchar(255) COLLATE "pg_catalog"."default"
)
;
COMMENT ON COLUMN "t_message_notification"."user_id" IS '消息通知的用户';
COMMENT ON COLUMN "t_message_notification"."briefly" IS '内容简要';
COMMENT ON COLUMN "t_message_notification"."details" IS '内容详情';
COMMENT ON COLUMN "t_message_notification"."message_level" IS '消息级别(0紧急/1普通)';
COMMENT ON COLUMN "t_message_notification"."message_label" IS '标签(类型:0错误/1普通通知/2待办)';
COMMENT ON COLUMN "t_message_notification"."message_state" IS '状态(0未开始/1进行中/2已处理)';
COMMENT ON COLUMN "t_message_notification"."create_date" IS '创建时间';
COMMENT ON COLUMN "t_message_notification"."update_date" IS '修改时间';
COMMENT ON COLUMN "t_message_notification"."is_deleted" IS '1 表示删除，0 表示未删除';
COMMENT ON TABLE "t_message_notification" IS '消息通知';

-- ----------------------------
-- Records of t_message_notification
-- ----------------------------
BEGIN;
COMMIT;

-- ----------------------------
-- Table structure for tool_alipay_config
-- ----------------------------
DROP TABLE IF EXISTS "tool_alipay_config";
CREATE TABLE "tool_alipay_config" (
  "config_id" int8 NOT NULL,
  "app_id" varchar(255) COLLATE "pg_catalog"."default",
  "charset" varchar(255) COLLATE "pg_catalog"."default",
  "format" varchar(255) COLLATE "pg_catalog"."default",
  "gateway_url" varchar(255) COLLATE "pg_catalog"."default",
  "notify_url" varchar(255) COLLATE "pg_catalog"."default",
  "private_key" text COLLATE "pg_catalog"."default",
  "public_key" text COLLATE "pg_catalog"."default",
  "return_url" varchar(255) COLLATE "pg_catalog"."default",
  "sign_type" varchar(255) COLLATE "pg_catalog"."default",
  "sys_service_provider_id" varchar(255) COLLATE "pg_catalog"."default"
)
;
COMMENT ON COLUMN "tool_alipay_config"."config_id" IS 'ID';
COMMENT ON COLUMN "tool_alipay_config"."app_id" IS '应用ID';
COMMENT ON COLUMN "tool_alipay_config"."charset" IS '编码';
COMMENT ON COLUMN "tool_alipay_config"."format" IS '类型 固定格式json';
COMMENT ON COLUMN "tool_alipay_config"."gateway_url" IS '网关地址';
COMMENT ON COLUMN "tool_alipay_config"."notify_url" IS '异步回调';
COMMENT ON COLUMN "tool_alipay_config"."private_key" IS '私钥';
COMMENT ON COLUMN "tool_alipay_config"."public_key" IS '公钥';
COMMENT ON COLUMN "tool_alipay_config"."return_url" IS '回调地址';
COMMENT ON COLUMN "tool_alipay_config"."sign_type" IS '签名方式';
COMMENT ON COLUMN "tool_alipay_config"."sys_service_provider_id" IS '商户号';
COMMENT ON TABLE "tool_alipay_config" IS '支付宝配置类';

-- ----------------------------
-- Records of tool_alipay_config
-- ----------------------------
BEGIN;
INSERT INTO "tool_alipay_config" VALUES (1, '2016091700532697', 'utf-8', 'JSON', 'https://openapi.alipaydev.com/gateway.do', 'http://api.auauz.net/api/aliPay/notify', 'MIIEvAIBADANBgkqhkiG9w0BAQEFAASCBKYwggSiAgEAAoIBAQC5js8sInU10AJ0cAQ8UMMyXrQ+oHZEkVt5lBwsStmTJ7YikVYgbskx1YYEXTojRsWCb+SH/kDmDU4pK/u91SJ4KFCRMF2411piYuXU/jF96zKrADznYh/zAraqT6hvAIVtQAlMHN53nx16rLzZ/8jDEkaSwT7+HvHiS+7sxSojnu/3oV7BtgISoUNstmSe8WpWHOaWv19xyS+Mce9MY4BfseFhzTICUymUQdd/8hXA28/H6osUfAgsnxAKv7Wil3aJSgaJczWuflYOve0dJ3InZkhw5Cvr0atwpk8YKBQjy5CdkoHqvkOcIB+cYHXJKzOE5tqU7inSwVbHzOLQ3XbnAgMBAAECggEAVJp5eT0Ixg1eYSqFs9568WdetUNCSUchNxDBu6wxAbhUgfRUGZuJnnAll63OCTGGck+EGkFh48JjRcBpGoeoHLL88QXlZZbC/iLrea6gcDIhuvfzzOffe1RcZtDFEj9hlotg8dQj1tS0gy9pN9g4+EBH7zeu+fyv+qb2e/v1l6FkISXUjpkD7RLQr3ykjiiEw9BpeKb7j5s7Kdx1NNIzhkcQKNqlk8JrTGDNInbDM6inZfwwIO2R1DHinwdfKWkvOTODTYa2MoAvVMFT9Bec9FbLpoWp7ogv1JMV9svgrcF9XLzANZ/OQvkbe9TV9GWYvIbxN6qwQioKCWO4GPnCAQKBgQDgW5MgfhX8yjXqoaUy/d1VjI8dHeIyw8d+OBAYwaxRSlCfyQ+tieWcR2HdTzPca0T0GkWcKZm0ei5xRURgxt4DUDLXNh26HG0qObbtLJdu/AuBUuCqgOiLqJ2f1uIbrz6OZUHns+bT/jGW2Ws8+C13zTCZkZt9CaQsrp3QOGDx5wKBgQDTul39hp3ZPwGNFeZdkGoUoViOSd5Lhowd5wYMGAEXWRLlU8z+smT5v0POz9JnIbCRchIY2FAPKRdVTICzmPk2EPJFxYTcwaNbVqL6lN7J2IlXXMiit5QbiLauo55w7plwV6LQmKm9KV7JsZs5XwqF7CEovI7GevFzyD3w+uizAQKBgC3LY1eRhOlpWOIAhpjG6qOoohmeXOphvdmMlfSHq6WYFqbWwmV4rS5d/6LNpNdL6fItXqIGd8I34jzql49taCmi+A2nlR/E559j0mvM20gjGDIYeZUz5MOE8k+K6/IcrhcgofgqZ2ZED1ksHdB/E8DNWCswZl16V1FrfvjeWSNnAoGAMrBplCrIW5xz+J0Hm9rZKrs+AkK5D4fUv8vxbK/KgxZ2KaUYbNm0xv39c+PZUYuFRCz1HDGdaSPDTE6WeWjkMQd5mS6ikl9hhpqFRkyh0d0fdGToO9yLftQKOGE/q3XUEktI1XvXF0xyPwNgUCnq0QkpHyGVZPtGFxwXiDvpvgECgYA5PoB+nY8iDiRaJNko9w0hL4AeKogwf+4TbCw+KWVEn6jhuJa4LFTdSqp89PktQaoVpwv92el/AhYjWOl/jVCm122f9b7GyoelbjMNolToDwe5pF5RnSpEuDdLy9MfE8LnE3PlbE7E5BipQ3UjSebkgNboLHH/lNZA5qvEtvbfvQ==', 'MIIBIjANBgkqhkiG9w0BAQEFAAOCAQ8AMIIBCgKCAQEAut9evKRuHJ/2QNfDlLwvN/S8l9hRAgPbb0u61bm4AtzaTGsLeMtScetxTWJnVvAVpMS9luhEJjt+Sbk5TNLArsgzzwARgaTKOLMT1TvWAK5EbHyI+eSrc3s7Awe1VYGwcubRFWDm16eQLv0k7iqiw+4mweHSz/wWyvBJVgwLoQ02btVtAQErCfSJCOmt0Q/oJQjj08YNRV4EKzB19+f5A+HQVAKy72dSybTzAK+3FPtTtNen/+b5wGeat7c32dhYHnGorPkPeXLtsqqUTp1su5fMfd4lElNdZaoCI7osZxWWUo17vBCZnyeXc9fk0qwD9mK6yRAxNbrY72Xx5VqIqwIDAQAB', 'http://api.auauz.net/api/aliPay/return', 'RSA2', '2088102176044281');
COMMIT;

-- ----------------------------
-- Table structure for tool_email_config
-- ----------------------------
DROP TABLE IF EXISTS "tool_email_config";
CREATE TABLE "tool_email_config" (
  "config_id" int8 NOT NULL,
  "from_user" varchar(255) COLLATE "pg_catalog"."default",
  "host" varchar(255) COLLATE "pg_catalog"."default",
  "pass" varchar(255) COLLATE "pg_catalog"."default",
  "port" varchar(255) COLLATE "pg_catalog"."default",
  "user" varchar(255) COLLATE "pg_catalog"."default"
)
;
COMMENT ON COLUMN "tool_email_config"."config_id" IS 'ID';
COMMENT ON COLUMN "tool_email_config"."from_user" IS '收件人';
COMMENT ON COLUMN "tool_email_config"."host" IS '邮件服务器SMTP地址';
COMMENT ON COLUMN "tool_email_config"."pass" IS '密码';
COMMENT ON COLUMN "tool_email_config"."port" IS '端口';
COMMENT ON COLUMN "tool_email_config"."user" IS '发件者用户名';
COMMENT ON TABLE "tool_email_config" IS '邮箱配置';

-- ----------------------------
-- Records of tool_email_config
-- ----------------------------
BEGIN;
COMMIT;

-- ----------------------------
-- Table structure for tool_local_storage
-- ----------------------------
DROP TABLE IF EXISTS "tool_local_storage";
CREATE TABLE "tool_local_storage" (
  "storage_id" int4 NOT NULL DEFAULT nextval('eladmin_template_schema.tool_local_storage_storage_id_seq'::regclass),
  "real_name" varchar(255) COLLATE "pg_catalog"."default",
  "name" varchar(255) COLLATE "pg_catalog"."default",
  "suffix" varchar(255) COLLATE "pg_catalog"."default",
  "path" varchar(255) COLLATE "pg_catalog"."default",
  "type" varchar(255) COLLATE "pg_catalog"."default",
  "size" varchar(100) COLLATE "pg_catalog"."default",
  "create_by" varchar(255) COLLATE "pg_catalog"."default",
  "update_by" varchar(255) COLLATE "pg_catalog"."default",
  "create_time" timestamp(6),
  "update_time" timestamp(6)
)
;
COMMENT ON COLUMN "tool_local_storage"."storage_id" IS 'ID';
COMMENT ON COLUMN "tool_local_storage"."real_name" IS '文件真实的名称';
COMMENT ON COLUMN "tool_local_storage"."name" IS '文件名';
COMMENT ON COLUMN "tool_local_storage"."suffix" IS '后缀';
COMMENT ON COLUMN "tool_local_storage"."path" IS '路径';
COMMENT ON COLUMN "tool_local_storage"."type" IS '类型';
COMMENT ON COLUMN "tool_local_storage"."size" IS '大小';
COMMENT ON COLUMN "tool_local_storage"."create_by" IS '创建者';
COMMENT ON COLUMN "tool_local_storage"."update_by" IS '更新者';
COMMENT ON COLUMN "tool_local_storage"."create_time" IS '创建日期';
COMMENT ON COLUMN "tool_local_storage"."update_time" IS '更新时间';
COMMENT ON TABLE "tool_local_storage" IS '本地存储';

-- ----------------------------
-- Records of tool_local_storage
-- ----------------------------
BEGIN;
COMMIT;

-- ----------------------------
-- Table structure for tool_qiniu_config
-- ----------------------------
DROP TABLE IF EXISTS "tool_qiniu_config";
CREATE TABLE "tool_qiniu_config" (
  "config_id" int8 NOT NULL,
  "access_key" text COLLATE "pg_catalog"."default",
  "bucket" varchar(255) COLLATE "pg_catalog"."default",
  "host" varchar(255) COLLATE "pg_catalog"."default" NOT NULL,
  "secret_key" text COLLATE "pg_catalog"."default",
  "type" varchar(255) COLLATE "pg_catalog"."default",
  "zone" varchar(255) COLLATE "pg_catalog"."default"
)
;
COMMENT ON COLUMN "tool_qiniu_config"."config_id" IS 'ID';
COMMENT ON COLUMN "tool_qiniu_config"."access_key" IS 'accessKey';
COMMENT ON COLUMN "tool_qiniu_config"."bucket" IS 'Bucket 识别符';
COMMENT ON COLUMN "tool_qiniu_config"."host" IS '外链域名';
COMMENT ON COLUMN "tool_qiniu_config"."secret_key" IS 'secretKey';
COMMENT ON COLUMN "tool_qiniu_config"."type" IS '空间类型';
COMMENT ON COLUMN "tool_qiniu_config"."zone" IS '机房';
COMMENT ON TABLE "tool_qiniu_config" IS '七牛云配置';

-- ----------------------------
-- Records of tool_qiniu_config
-- ----------------------------
BEGIN;
INSERT INTO "tool_qiniu_config" VALUES (1, '1231', '12321', 'https://asdasd.com', '12321', '公开', '华北');
COMMIT;

-- ----------------------------
-- Table structure for tool_qiniu_content
-- ----------------------------
DROP TABLE IF EXISTS "tool_qiniu_content";
CREATE TABLE "tool_qiniu_content" (
  "content_id" int4 NOT NULL DEFAULT nextval('eladmin_template_schema.tool_qiniu_content_content_id_seq'::regclass),
  "bucket" varchar(255) COLLATE "pg_catalog"."default",
  "name" varchar(255) COLLATE "pg_catalog"."default",
  "size" varchar(255) COLLATE "pg_catalog"."default",
  "type" varchar(255) COLLATE "pg_catalog"."default",
  "url" varchar(255) COLLATE "pg_catalog"."default",
  "suffix" varchar(255) COLLATE "pg_catalog"."default",
  "update_time" timestamp(6)
)
;
COMMENT ON COLUMN "tool_qiniu_content"."content_id" IS 'ID';
COMMENT ON COLUMN "tool_qiniu_content"."bucket" IS 'Bucket 识别符';
COMMENT ON COLUMN "tool_qiniu_content"."name" IS '文件名称';
COMMENT ON COLUMN "tool_qiniu_content"."size" IS '文件大小';
COMMENT ON COLUMN "tool_qiniu_content"."type" IS '文件类型：私有或公开';
COMMENT ON COLUMN "tool_qiniu_content"."url" IS '文件url';
COMMENT ON COLUMN "tool_qiniu_content"."suffix" IS '文件后缀';
COMMENT ON COLUMN "tool_qiniu_content"."update_time" IS '上传或同步的时间';
COMMENT ON TABLE "tool_qiniu_content" IS '七牛云文件存储';

-- ----------------------------
-- Records of tool_qiniu_content
-- ----------------------------
BEGIN;
COMMIT;

-- ----------------------------
-- Alter sequences owned by
-- ----------------------------
ALTER SEQUENCE "app_version_id_seq"
OWNED BY "app_version"."id";
SELECT setval('"app_version_id_seq"', 1, false);

-- ----------------------------
-- Alter sequences owned by
-- ----------------------------
ALTER SEQUENCE "app_version_v2_id_seq"
OWNED BY "app_version_v2"."id";
SELECT setval('"app_version_v2_id_seq"', 2, true);

-- ----------------------------
-- Alter sequences owned by
-- ----------------------------
ALTER SEQUENCE "code_column_config_column_id_seq"
OWNED BY "code_column_config"."column_id";
SELECT setval('"code_column_config_column_id_seq"', 51, true);

-- ----------------------------
-- Alter sequences owned by
-- ----------------------------
ALTER SEQUENCE "code_gen_config_config_id_seq"
OWNED BY "code_gen_config"."config_id";
SELECT setval('"code_gen_config_config_id_seq"', 1, false);

-- ----------------------------
-- Alter sequences owned by
-- ----------------------------
ALTER SEQUENCE "m_user_user_id_seq"
OWNED BY "m_user"."user_id";
SELECT setval('"m_user_user_id_seq"', 1, false);

-- ----------------------------
-- Alter sequences owned by
-- ----------------------------
ALTER SEQUENCE "mnt_app_app_id_seq"
OWNED BY "mnt_app"."app_id";
SELECT setval('"mnt_app_app_id_seq"', 1, false);

-- ----------------------------
-- Alter sequences owned by
-- ----------------------------
ALTER SEQUENCE "mnt_deploy_deploy_id_seq"
OWNED BY "mnt_deploy"."deploy_id";
SELECT setval('"mnt_deploy_deploy_id_seq"', 1, false);

-- ----------------------------
-- Alter sequences owned by
-- ----------------------------
ALTER SEQUENCE "mnt_server_server_id_seq"
OWNED BY "mnt_server"."server_id";
SELECT setval('"mnt_server_server_id_seq"', 1, false);

-- ----------------------------
-- Alter sequences owned by
-- ----------------------------
ALTER SEQUENCE "sys_dept_dept_id_seq"
OWNED BY "sys_dept"."dept_id";
SELECT setval('"sys_dept_dept_id_seq"', 1, true);

-- ----------------------------
-- Alter sequences owned by
-- ----------------------------
ALTER SEQUENCE "sys_dict_detail_detail_id_seq"
OWNED BY "sys_dict_detail"."detail_id";
SELECT setval('"sys_dict_detail_detail_id_seq"', 1, true);

-- ----------------------------
-- Alter sequences owned by
-- ----------------------------
ALTER SEQUENCE "sys_dict_dict_id_seq"
OWNED BY "sys_dict"."dict_id";
SELECT setval('"sys_dict_dict_id_seq"', 1, true);

-- ----------------------------
-- Alter sequences owned by
-- ----------------------------
ALTER SEQUENCE "sys_job_job_id_seq"
OWNED BY "sys_job"."job_id";
SELECT setval('"sys_job_job_id_seq"', 1, true);

-- ----------------------------
-- Alter sequences owned by
-- ----------------------------
ALTER SEQUENCE "sys_log_log_id_seq"
OWNED BY "sys_log"."log_id";
SELECT setval('"sys_log_log_id_seq"', 1, true);

-- ----------------------------
-- Alter sequences owned by
-- ----------------------------
ALTER SEQUENCE "sys_menu_menu_id_seq"
OWNED BY "sys_menu"."menu_id";
SELECT setval('"sys_menu_menu_id_seq"', 159, true);

-- ----------------------------
-- Alter sequences owned by
-- ----------------------------
ALTER SEQUENCE "sys_quartz_job_job_id_seq"
OWNED BY "sys_quartz_job"."job_id";
SELECT setval('"sys_quartz_job_job_id_seq"', 1, true);

-- ----------------------------
-- Alter sequences owned by
-- ----------------------------
ALTER SEQUENCE "sys_quartz_log_log_id_seq"
OWNED BY "sys_quartz_log"."log_id";
SELECT setval('"sys_quartz_log_log_id_seq"', 1, true);

-- ----------------------------
-- Alter sequences owned by
-- ----------------------------
ALTER SEQUENCE "sys_role_role_id_seq"
OWNED BY "sys_role"."role_id";
SELECT setval('"sys_role_role_id_seq"', 3, true);

-- ----------------------------
-- Alter sequences owned by
-- ----------------------------
ALTER SEQUENCE "sys_user_user_id_seq"
OWNED BY "sys_user"."user_id";
SELECT setval('"sys_user_user_id_seq"', 3, true);

-- ----------------------------
-- Alter sequences owned by
-- ----------------------------
ALTER SEQUENCE "t_cms_cms_id_seq"
OWNED BY "t_cms"."cms_id";
SELECT setval('"t_cms_cms_id_seq"', 1, false);

-- ----------------------------
-- Alter sequences owned by
-- ----------------------------
ALTER SEQUENCE "t_cms_column_column_id_seq"
OWNED BY "t_cms_column"."column_id";
SELECT setval('"t_cms_column_column_id_seq"', 1, false);

-- ----------------------------
-- Alter sequences owned by
-- ----------------------------
ALTER SEQUENCE "t_message_notification_id_seq"
OWNED BY "t_message_notification"."id";
SELECT setval('"t_message_notification_id_seq"', 1, false);

-- ----------------------------
-- Alter sequences owned by
-- ----------------------------
ALTER SEQUENCE "tool_local_storage_storage_id_seq"
OWNED BY "tool_local_storage"."storage_id";
SELECT setval('"tool_local_storage_storage_id_seq"', 1, false);

-- ----------------------------
-- Alter sequences owned by
-- ----------------------------
ALTER SEQUENCE "tool_qiniu_content_content_id_seq"
OWNED BY "tool_qiniu_content"."content_id";
SELECT setval('"tool_qiniu_content_content_id_seq"', 1, false);

-- ----------------------------
-- Primary Key structure for table app_version
-- ----------------------------
ALTER TABLE "app_version" ADD CONSTRAINT "app_version_pkey" PRIMARY KEY ("id");

-- ----------------------------
-- Primary Key structure for table app_version_v2
-- ----------------------------
ALTER TABLE "app_version_v2" ADD CONSTRAINT "app_version_v2_pkey" PRIMARY KEY ("id");

-- ----------------------------
-- Indexes structure for table code_column_config
-- ----------------------------
CREATE INDEX "idx_table_name" ON "code_column_config" USING btree (
  "table_name" COLLATE "pg_catalog"."default" "pg_catalog"."text_ops" ASC NULLS LAST
);

-- ----------------------------
-- Primary Key structure for table code_column_config
-- ----------------------------
ALTER TABLE "code_column_config" ADD CONSTRAINT "code_column_config_pkey" PRIMARY KEY ("column_id");

-- ----------------------------
-- Indexes structure for table code_gen_config
-- ----------------------------
CREATE INDEX "idx_table_name_copy_1" ON "code_gen_config" USING btree (
  "table_name" COLLATE "pg_catalog"."default" "pg_catalog"."text_ops" ASC NULLS LAST
);

-- ----------------------------
-- Primary Key structure for table code_gen_config
-- ----------------------------
ALTER TABLE "code_gen_config" ADD CONSTRAINT "code_gen_config_pkey" PRIMARY KEY ("config_id");

-- ----------------------------
-- Primary Key structure for table m_user
-- ----------------------------
ALTER TABLE "m_user" ADD CONSTRAINT "m_user_pkey" PRIMARY KEY ("user_id");

-- ----------------------------
-- Primary Key structure for table mnt_app
-- ----------------------------
ALTER TABLE "mnt_app" ADD CONSTRAINT "mnt_app_pkey" PRIMARY KEY ("app_id");

-- ----------------------------
-- Primary Key structure for table mnt_database
-- ----------------------------
ALTER TABLE "mnt_database" ADD CONSTRAINT "_copy_28" PRIMARY KEY ("db_id");

-- ----------------------------
-- Indexes structure for table mnt_deploy
-- ----------------------------
CREATE INDEX "FK6sy157pseoxx4fmcqr1vnvvhy" ON "mnt_deploy" USING btree (
  "app_id" "pg_catalog"."int8_ops" ASC NULLS LAST
);

-- ----------------------------
-- Primary Key structure for table mnt_deploy
-- ----------------------------
ALTER TABLE "mnt_deploy" ADD CONSTRAINT "mnt_deploy_pkey" PRIMARY KEY ("deploy_id");

-- ----------------------------
-- Primary Key structure for table mnt_deploy_history
-- ----------------------------
ALTER TABLE "mnt_deploy_history" ADD CONSTRAINT "_copy_26" PRIMARY KEY ("history_id");

-- ----------------------------
-- Indexes structure for table mnt_deploy_server
-- ----------------------------
CREATE INDEX "FKeaaha7jew9a02b3bk9ghols53" ON "mnt_deploy_server" USING btree (
  "server_id" "pg_catalog"."int8_ops" ASC NULLS LAST
);

-- ----------------------------
-- Primary Key structure for table mnt_deploy_server
-- ----------------------------
ALTER TABLE "mnt_deploy_server" ADD CONSTRAINT "_copy_25" PRIMARY KEY ("deploy_id", "server_id");

-- ----------------------------
-- Indexes structure for table mnt_server
-- ----------------------------
CREATE INDEX "idx_ip" ON "mnt_server" USING btree (
  "ip" COLLATE "pg_catalog"."default" "pg_catalog"."text_ops" ASC NULLS LAST
);

-- ----------------------------
-- Primary Key structure for table mnt_server
-- ----------------------------
ALTER TABLE "mnt_server" ADD CONSTRAINT "mnt_server_pkey" PRIMARY KEY ("server_id");

-- ----------------------------
-- Indexes structure for table sys_dept
-- ----------------------------
CREATE INDEX "inx_enabled" ON "sys_dept" USING btree (
  "enabled" COLLATE "pg_catalog"."default" "pg_catalog"."text_ops" ASC NULLS LAST
);
CREATE INDEX "inx_pid" ON "sys_dept" USING btree (
  "pid" "pg_catalog"."int8_ops" ASC NULLS LAST
);

-- ----------------------------
-- Primary Key structure for table sys_dept
-- ----------------------------
ALTER TABLE "sys_dept" ADD CONSTRAINT "_copy_23" PRIMARY KEY ("dept_id");

-- ----------------------------
-- Primary Key structure for table sys_dict
-- ----------------------------
ALTER TABLE "sys_dict" ADD CONSTRAINT "sys_dict_pkey" PRIMARY KEY ("dict_id");

-- ----------------------------
-- Indexes structure for table sys_dict_detail
-- ----------------------------
CREATE INDEX "FK5tpkputc6d9nboxojdbgnpmyb" ON "sys_dict_detail" USING btree (
  "dict_id" "pg_catalog"."int8_ops" ASC NULLS LAST
);

-- ----------------------------
-- Primary Key structure for table sys_dict_detail
-- ----------------------------
ALTER TABLE "sys_dict_detail" ADD CONSTRAINT "sys_dict_detail_pkey" PRIMARY KEY ("detail_id");

-- ----------------------------
-- Primary Key structure for table sys_job
-- ----------------------------
ALTER TABLE "sys_job" ADD CONSTRAINT "sys_job_pkey" PRIMARY KEY ("job_id");

-- ----------------------------
-- Primary Key structure for table sys_menu
-- ----------------------------
ALTER TABLE "sys_menu" ADD CONSTRAINT "sys_menu_pkey" PRIMARY KEY ("menu_id");

-- ----------------------------
-- Primary Key structure for table sys_quartz_job
-- ----------------------------
ALTER TABLE "sys_quartz_job" ADD CONSTRAINT "sys_quartz_job_pkey" PRIMARY KEY ("job_id");

-- ----------------------------
-- Primary Key structure for table sys_quartz_log
-- ----------------------------
ALTER TABLE "sys_quartz_log" ADD CONSTRAINT "sys_quartz_log_pkey" PRIMARY KEY ("log_id");

-- ----------------------------
-- Primary Key structure for table sys_role
-- ----------------------------
ALTER TABLE "sys_role" ADD CONSTRAINT "sys_role_pkey" PRIMARY KEY ("role_id");

-- ----------------------------
-- Primary Key structure for table sys_user
-- ----------------------------
ALTER TABLE "sys_user" ADD CONSTRAINT "sys_user_pkey" PRIMARY KEY ("user_id");

-- ----------------------------
-- Primary Key structure for table t_cms
-- ----------------------------
ALTER TABLE "t_cms" ADD CONSTRAINT "t_cms_pkey" PRIMARY KEY ("cms_id");

-- ----------------------------
-- Primary Key structure for table t_cms_column
-- ----------------------------
ALTER TABLE "t_cms_column" ADD CONSTRAINT "t_cms_column_pkey" PRIMARY KEY ("column_id");

-- ----------------------------
-- Primary Key structure for table t_message_notification
-- ----------------------------
ALTER TABLE "t_message_notification" ADD CONSTRAINT "t_message_notification_pkey" PRIMARY KEY ("id");

-- ----------------------------
-- Primary Key structure for table tool_alipay_config
-- ----------------------------
ALTER TABLE "tool_alipay_config" ADD CONSTRAINT "tool_alipay_config_pkey" PRIMARY KEY ("config_id");

-- ----------------------------
-- Primary Key structure for table tool_email_config
-- ----------------------------
ALTER TABLE "tool_email_config" ADD CONSTRAINT "tool_email_config_pkey" PRIMARY KEY ("config_id");

-- ----------------------------
-- Primary Key structure for table tool_local_storage
-- ----------------------------
ALTER TABLE "tool_local_storage" ADD CONSTRAINT "tool_local_storage_pkey" PRIMARY KEY ("storage_id");

-- ----------------------------
-- Primary Key structure for table tool_qiniu_config
-- ----------------------------
ALTER TABLE "tool_qiniu_config" ADD CONSTRAINT "tool_qiniu_config_pkey" PRIMARY KEY ("config_id");

-- ----------------------------
-- Primary Key structure for table tool_qiniu_content
-- ----------------------------
ALTER TABLE "tool_qiniu_content" ADD CONSTRAINT "tool_qiniu_content_pkey" PRIMARY KEY ("content_id");
