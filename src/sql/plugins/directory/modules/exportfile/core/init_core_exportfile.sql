
--
-- Data for table core_admin_right
--
DELETE FROM core_admin_right WHERE id_right = 'EXPORTFILE_MANAGEMENT';
INSERT INTO core_admin_right (id_right,name,level_right,admin_url,description,is_updatable,plugin_name,id_feature_group,icon_url,documentation_url, id_order ) VALUES 
('EXPORTFILE_MANAGEMENT','module.directory.exportfile.adminFeature.ManageExportfile.name',1,'jsp/admin/plugins/directory/modules/exportfile/ManageMappingEntry.jsp','module.directory.exportfile.adminFeature.ManageExportfile.description',0,'exportfile',NULL,NULL,NULL,4);


--
-- Data for table core_user_right
--
DELETE FROM core_user_right WHERE id_right = 'EXPORTFILE_MANAGEMENT';
INSERT INTO core_user_right (id_right,id_user) VALUES ('EXPORTFILE_MANAGEMENT',1);

