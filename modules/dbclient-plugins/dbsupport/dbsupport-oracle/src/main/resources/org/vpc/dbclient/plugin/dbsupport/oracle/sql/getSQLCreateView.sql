select TEXT from SYS.ALL_VIEWS
where OWNER = ${schema?} and VIEW_NAME= ${view?}