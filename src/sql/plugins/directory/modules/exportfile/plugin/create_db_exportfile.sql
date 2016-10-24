
--
-- Structure for table exportfile_file
--

DROP TABLE IF EXISTS exportfile_file;
CREATE TABLE exportfile_file (
id_file int(6) NOT NULL,
iddirectory int(11) NOT NULL default '0',
idfile int(11) NOT NULL default '0',
iscreated SMALLINT NOT NULL,
PRIMARY KEY (id_file)
);

--
-- Structure for table exportfile_mapping_entry
--

DROP TABLE IF EXISTS exportfile_mapping_entry;
CREATE TABLE exportfile_mapping_entry (
id_mappingentry int(6) NOT NULL,
identry int(11) NOT NULL default '0',
iddirectory int(11) NOT NULL default '0',
path varchar(255) NOT NULL,
isactive SMALLINT NOT NULL,
PRIMARY KEY (id_mappingentry)
);

--
-- Structure for table exportfile_fileName
--

DROP TABLE IF EXISTS exportfile_fileName;
CREATE TABLE exportfile_fileName (
mapping_entry int(6) NOT NULL,
attribute varchar(255) NOT NULL,
number_char int(11) NOT NULL default '0',
order_name int(11) NOT NULL,
PRIMARY KEY (mapping_entry, attribute, order_name)
);

