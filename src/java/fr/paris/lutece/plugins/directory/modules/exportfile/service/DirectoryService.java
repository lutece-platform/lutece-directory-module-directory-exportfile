/*
 * Copyright (c) 2002-2013, Mairie de Paris
 * All rights reserved.
 *
 * Redistribution and use in source and binary forms, with or without
 * modification, are permitted provided that the following conditions
 * are met:
 *
 *  1. Redistributions of source code must retain the above copyright notice
 *     and the following disclaimer.
 *
 *  2. Redistributions in binary form must reproduce the above copyright notice
 *     and the following disclaimer in the documentation and/or other materials
 *     provided with the distribution.
 *
 *  3. Neither the name of 'Mairie de Paris' nor 'Lutece' nor the names of its
 *     contributors may be used to endorse or promote products derived from
 *     this software without specific prior written permission.
 *
 * THIS SOFTWARE IS PROVIDED BY THE COPYRIGHT HOLDERS AND CONTRIBUTORS "AS IS"
 * AND ANY EXPRESS OR IMPLIED WARRANTIES, INCLUDING, BUT NOT LIMITED TO, THE
 * IMPLIED WARRANTIES OF MERCHANTABILITY AND FITNESS FOR A PARTICULAR PURPOSE
 * ARE DISCLAIMED. IN NO EVENT SHALL THE COPYRIGHT HOLDERS OR CONTRIBUTORS BE
 * LIABLE FOR ANY DIRECT, INDIRECT, INCIDENTAL, SPECIAL, EXEMPLARY, OR
 * CONSEQUENTIAL DAMAGES (INCLUDING, BUT NOT LIMITED TO, PROCUREMENT OF
 * SUBSTITUTE GOODS OR SERVICES; LOSS OF USE, DATA, OR PROFITS; OR BUSINESS
 * INTERRUPTION) HOWEVER CAUSED AND ON ANY THEORY OF LIABILITY, WHETHER IN
 * CONTRACT, STRICT LIABILITY, OR TORT (INCLUDING NEGLIGENCE OR OTHERWISE)
 * ARISING IN ANY WAY OUT OF THE USE OF THIS SOFTWARE, EVEN IF ADVISED OF THE
 * POSSIBILITY OF SUCH DAMAGE.
 *
 * License 1.0
 */
package fr.paris.lutece.plugins.directory.modules.exportfile.service;

import fr.paris.lutece.plugins.directory.business.DirectoryHome;
import fr.paris.lutece.plugins.directory.business.EntryFilter;
import fr.paris.lutece.plugins.directory.business.EntryHome;
import fr.paris.lutece.plugins.directory.business.File;
import fr.paris.lutece.plugins.directory.business.IEntry;
import fr.paris.lutece.plugins.directory.business.Record;
import fr.paris.lutece.plugins.directory.business.RecordField;
import fr.paris.lutece.plugins.directory.business.RecordFieldFilter;
import fr.paris.lutece.plugins.directory.business.RecordFieldHome;
import fr.paris.lutece.plugins.directory.business.RecordHome;
import fr.paris.lutece.plugins.directory.service.DirectoryPlugin;
import fr.paris.lutece.plugins.directory.utils.DirectoryUtils;
import fr.paris.lutece.portal.service.admin.AdminUserService;
import fr.paris.lutece.portal.service.plugin.Plugin;
import fr.paris.lutece.portal.service.plugin.PluginService;
import fr.paris.lutece.portal.service.util.AppPathService;
import fr.paris.lutece.portal.service.util.AppPropertiesService;
import fr.paris.lutece.util.ReferenceItem;
import fr.paris.lutece.util.ReferenceList;

import org.apache.commons.lang.StringUtils;

import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

/**
 * DirectoryService
 */
public class DirectoryService
{
    private static final String PROPERTY_ENTRY_TYPE_CAMERA = "module.directory.exportfile.entry_type.camera";
    private static final String PROPERTY_ENTRY_TYPE_FILE = "module.directory.exportfile.entry_type.file";
    private static final String PROPERTY_ENTRY_TYPE_IMAGE = "directory.resource_rss.entry_type_image";
    private static final String PROPERTY_ENTRY_TYPE_GEOLOCATION = "directory.entry_type.geolocation";

    private static final String DATE_CREATION_RECORD_TITLE = "creation date";
    public static final String DATE_CREATION_RECORD_CODE = "9999999";

    private static DirectoryService _singleton;

    /**
     * The plugin directory.
     */
    public final Plugin _pluginDirectory = PluginService.getPlugin( DirectoryPlugin.PLUGIN_NAME );

    /**
     * @return instance DirectoryService
     */
    public static DirectoryService getService( )
    {
        if ( _singleton == null )
        {
            _singleton = new DirectoryService( );

            return _singleton;
        }

        return _singleton;
    }

    /**
     * Get the list of directorise
     * 
     * @return a ReferenceList
     */
    public ReferenceList getListDirectories( )
    {
        Plugin pluginDirectory = PluginService.getPlugin( DirectoryPlugin.PLUGIN_NAME );
        ReferenceList listDirectories = DirectoryHome.getDirectoryList( pluginDirectory );
        ReferenceList refenreceListDirectories = new ReferenceList( );
        refenreceListDirectories.addItem( DirectoryUtils.CONSTANT_ID_NULL, StringUtils.EMPTY );

        if ( listDirectories != null )
        {
            refenreceListDirectories.addAll( listDirectories );
        }

        return refenreceListDirectories;
    }

    /**
     * Method to get directory entries list
     * 
     * @param nIdDirectory
     *            id directory
     * @param request
     *            request
     * @return ReferenceList entries list
     */
    public ReferenceList getListEntries( int nIdDirectory, HttpServletRequest request )
    {
        if ( nIdDirectory != -1 )
        {
            Plugin pluginDirectory = PluginService.getPlugin( DirectoryPlugin.PLUGIN_NAME );
            List<IEntry> listEntries = DirectoryUtils.getFormEntries( nIdDirectory, pluginDirectory, AdminUserService.getAdminUser( request ) );
            ReferenceList referenceList = new ReferenceList( );

            for ( IEntry entry : listEntries )
            {
                if ( entry.getEntryType( ).getComment( ) )
                {
                    continue;
                }

                if ( entry.getEntryType( ).getGroup( ) )
                {
                    if ( entry.getChildren( ) != null )
                    {
                        for ( IEntry child : entry.getChildren( ) )
                        {
                            if ( child.getEntryType( ).getComment( ) )
                            {
                                continue;
                            }

                            ReferenceItem referenceItem = new ReferenceItem( );
                            referenceItem.setCode( String.valueOf( child.getIdEntry( ) ) );
                            referenceItem.setName( child.getTitle( ) );
                            referenceList.add( referenceItem );
                        }
                    }
                }
                else
                {
                    ReferenceItem referenceItem = new ReferenceItem( );
                    referenceItem.setCode( String.valueOf( entry.getIdEntry( ) ) );
                    referenceItem.setName( entry.getTitle( ) );
                    referenceList.add( referenceItem );
                }
            }

            ReferenceItem referenceItem = new ReferenceItem( );
            referenceItem.setCode( DATE_CREATION_RECORD_CODE );
            referenceItem.setName( DATE_CREATION_RECORD_TITLE );
            referenceList.add( referenceItem );
            return referenceList;
        }
        else
        {
            return new ReferenceList( );
        }
    }

    /**
     * get Entry list of directory
     * 
     * @param nidDirectory
     * @return Entry list of directory
     */
    public List<IEntry> getListEntries( int nidDirectory )
    {
        Plugin pluginDirectory = PluginService.getPlugin( DirectoryPlugin.PLUGIN_NAME );
        List<IEntry> listEntries = new ArrayList<IEntry>( );
        EntryFilter entryFilter = new EntryFilter( );
        entryFilter.setIdDirectory( nidDirectory );

        listEntries = EntryHome.getEntryList( entryFilter, pluginDirectory );

        return listEntries;
    }

    /**
     * get record field value
     * 
     * @param nIdEntry
     * @param nIdRecord
     * @param nIdDirectory
     * @return record field value
     */
    public File getRecordFieldValue( int nIdEntry, int nIdRecord, int nIdDirectory )
    {
        Plugin pluginDirectory = PluginService.getPlugin( DirectoryPlugin.PLUGIN_NAME );

        // RecordField
        IEntry entry = EntryHome.findByPrimaryKey( nIdEntry, pluginDirectory );

        if ( ( entry != null ) )
        {

            RecordFieldFilter recordFieldFilter = new RecordFieldFilter( );
            recordFieldFilter.setIdDirectory( nIdDirectory );
            recordFieldFilter.setIdEntry( entry.getIdEntry( ) );
            recordFieldFilter.setIdRecord( nIdRecord );

            List<RecordField> listRecordFields = RecordFieldHome.getRecordFieldList( recordFieldFilter, pluginDirectory );

            if ( ( entry.getEntryType( ).getIdType( ) == AppPropertiesService.getPropertyInt( PROPERTY_ENTRY_TYPE_IMAGE, 10 ) )
                    || entry.getEntryType( ).getIdType( ) == AppPropertiesService.getPropertyInt( PROPERTY_ENTRY_TYPE_FILE, 8 )
                    || entry.getEntryType( ).getIdType( ) == AppPropertiesService.getPropertyInt( PROPERTY_ENTRY_TYPE_CAMERA, 23 ) )
            {
                if ( listRecordFields.size( ) >= 1 )
                {
                    return listRecordFields.get( 0 ).getFile( );
                }
                else
                {
                    return null;
                }
            }

        }

        return null;
    }

    /**
     * get Identifiant of the record
     * 
     * @param nIdResource
     * @return id Record
     */
    public List<Record> getListRecord( int nIdDirectory, int nIdEntry )
    {
        RecordFieldFilter filter = new RecordFieldFilter( );
        filter.setIdDirectory( nIdDirectory );
        filter.setIdEntry( nIdEntry );

        List<Record> listRecord = RecordHome.getListRecord( filter, _pluginDirectory );

        return listRecord;
    }

    public Timestamp getDateCreation( int nIdRecord, int nIdDirectory )
    {
        Plugin pluginDirectory = PluginService.getPlugin( DirectoryPlugin.PLUGIN_NAME );

        RecordFieldFilter recordFieldFilter = new RecordFieldFilter( );
        recordFieldFilter.setIdDirectory( nIdDirectory );
        recordFieldFilter.setIdRecord( nIdRecord );

        List<RecordField> listRecordFields = RecordFieldHome.getRecordFieldList( recordFieldFilter, pluginDirectory );

        if ( ( listRecordFields != null ) && !listRecordFields.isEmpty( ) && ( listRecordFields.get( 0 ) != null ) )
        {
            return listRecordFields.get( 0 ).getRecord( ).getDateCreation( );
        }

        return null;
    }

    /**
     * get record field value
     * 
     * @param nIdEntry
     * @param nIdRecord
     * @param nIdDirectory
     * @return record field value
     */
    public String getRecordFieldStringValue( int nIdEntry, int nIdRecord, int nIdDirectory )
    {
        String strRecordFieldValue = StringUtils.EMPTY;
        Plugin pluginDirectory = PluginService.getPlugin( DirectoryPlugin.PLUGIN_NAME );

        IEntry entry = EntryHome.findByPrimaryKey( nIdEntry, pluginDirectory );

        if ( ( entry != null ) )
        {

            RecordFieldFilter recordFieldFilter = new RecordFieldFilter( );
            recordFieldFilter.setIdDirectory( nIdDirectory );
            recordFieldFilter.setIdEntry( entry.getIdEntry( ) );
            recordFieldFilter.setIdRecord( nIdRecord );

            List<RecordField> listRecordFields = RecordFieldHome.getRecordFieldList( recordFieldFilter, pluginDirectory );

            if ( entry.getEntryType( ).getIdType( ) == AppPropertiesService.getPropertyInt( PROPERTY_ENTRY_TYPE_GEOLOCATION, 16 ) )
            {
                if ( listRecordFields.size( ) >= 4 )
                {
                    return listRecordFields.get( 2 ).getValue( ) + ", " + listRecordFields.get( 3 ).getValue( );
                }
                else
                {
                    return StringUtils.EMPTY;
                }
            }

            if ( ( entry.getEntryType( ).getIdType( ) == AppPropertiesService.getPropertyInt( PROPERTY_ENTRY_TYPE_IMAGE, 10 ) )
                    || ( entry.getEntryType( ).getIdType( ) == 8 ) )
            {
                if ( listRecordFields.size( ) >= 1 )
                {
                    return listRecordFields.get( 0 ).getFile( ).getTitle( );
                }
                else
                {
                    return StringUtils.EMPTY;
                }
            }

            if ( ( listRecordFields != null ) && !listRecordFields.isEmpty( ) && ( listRecordFields.get( 0 ) != null ) )
            {
                RecordField recordFieldIdDemand = listRecordFields.get( 0 );
                strRecordFieldValue = recordFieldIdDemand.getValue( );

                if ( recordFieldIdDemand.getField( ) != null )
                {
                    strRecordFieldValue = recordFieldIdDemand.getField( ).getTitle( );
                }
            }
        }

        return strRecordFieldValue;
    }

}
