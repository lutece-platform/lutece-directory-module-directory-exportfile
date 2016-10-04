/*
 * Copyright (c) 2002-2015, Mairie de Paris
 * All rights reserved.
 *
 * Redistribution and use in source and binary forms, with or without
 * modification, are permitted provided that the following conditions
 * are met:
 *
 *  1. Redistributions of source code must retain the above copyright notice
 *	 and the following disclaimer.
 *
 *  2. Redistributions in binary form must reproduce the above copyright notice
 *	 and the following disclaimer in the documentation and/or other materials
 *	 provided with the distribution.
 *
 *  3. Neither the name of 'Mairie de Paris' nor 'Lutece' nor the names of its
 *	 contributors may be used to endorse or promote products derived from
 *	 this software without specific prior written permission.
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

package fr.paris.lutece.plugins.directory.modules.exportfile.business;

import fr.paris.lutece.portal.service.plugin.Plugin;
import fr.paris.lutece.util.sql.DAOUtil;

import java.util.ArrayList;
import java.util.Collection;

/**
 * This class provides Data Access methods for MappingEntry objects
 */

public final class MappingEntryDAO implements IMappingEntryDAO
{
    // Constants
    private static final String SQL_QUERY_NEW_PK = "SELECT max( id_mappingentry ) FROM exportfile_mapping_entry";
    private static final String SQL_QUERY_SELECT = "SELECT id_mappingentry, idEntry, idDirectory, isActive FROM exportfile_mapping_entry WHERE id_mappingentry = ?";
    private static final String SQL_QUERY_INSERT = "INSERT INTO exportfile_mapping_entry ( id_mappingentry, idEntry, idDirectory, isActive ) VALUES ( ?, ?, ?, ? ) ";
    private static final String SQL_QUERY_DELETE = "DELETE FROM exportfile_mapping_entry WHERE id_mappingentry = ? ";
    private static final String SQL_QUERY_UPDATE = "UPDATE exportfile_mapping_entry SET id_mappingentry = ?, idEntry = ?, idDirectory = ?, isActive = ? WHERE id_mappingentry = ?";
    private static final String SQL_QUERY_SELECTALL = "SELECT id_mappingentry, idEntry, idDirectory, isActive FROM exportfile_mapping_entry";
    private static final String SQL_QUERY_SELECTALL_ID = "SELECT id_mappingentry FROM exportfile_mapping_entry";

    /**
     * Generates a new primary key
     * 
     * @param plugin
     *            The Plugin
     * @return The new primary key
     */
    public int newPrimaryKey( Plugin plugin )
    {
        DAOUtil daoUtil = new DAOUtil( SQL_QUERY_NEW_PK, plugin );
        daoUtil.executeQuery( );

        int nKey = 1;

        if ( daoUtil.next( ) )
        {
            nKey = daoUtil.getInt( 1 ) + 1;
        }

        daoUtil.free( );

        return nKey;
    }

    /**
     * {@inheritDoc }
     */
    @Override
    public void insert( MappingEntry mappingEntry, Plugin plugin )
    {
        DAOUtil daoUtil = new DAOUtil( SQL_QUERY_INSERT, plugin );

        mappingEntry.setId( newPrimaryKey( plugin ) );

        daoUtil.setInt( 1, mappingEntry.getId( ) );
        daoUtil.setInt( 2, mappingEntry.getIdEntry( ) );
        daoUtil.setInt( 3, mappingEntry.getIdDirectory( ) );
        daoUtil.setBoolean( 4, mappingEntry.getIsActive( ) );

        daoUtil.executeUpdate( );
        daoUtil.free( );
    }

    /**
     * {@inheritDoc }
     */
    @Override
    public MappingEntry load( int nKey, Plugin plugin )
    {
        DAOUtil daoUtil = new DAOUtil( SQL_QUERY_SELECT, plugin );
        daoUtil.setInt( 1, nKey );
        daoUtil.executeQuery( );

        MappingEntry mappingEntry = null;

        if ( daoUtil.next( ) )
        {
            mappingEntry = new MappingEntry( );
            mappingEntry.setId( daoUtil.getInt( 1 ) );
            mappingEntry.setIdEntry( daoUtil.getInt( 2 ) );
            mappingEntry.setIdDirectory( daoUtil.getInt( 3 ) );
            mappingEntry.setIsActive( daoUtil.getBoolean( 4 ) );
        }

        daoUtil.free( );
        return mappingEntry;
    }

    /**
     * {@inheritDoc }
     */
    @Override
    public void delete( int nKey, Plugin plugin )
    {
        DAOUtil daoUtil = new DAOUtil( SQL_QUERY_DELETE, plugin );
        daoUtil.setInt( 1, nKey );
        daoUtil.executeUpdate( );
        daoUtil.free( );
    }

    /**
     * {@inheritDoc }
     */
    @Override
    public void store( MappingEntry mappingEntry, Plugin plugin )
    {
        DAOUtil daoUtil = new DAOUtil( SQL_QUERY_UPDATE, plugin );

        daoUtil.setInt( 1, mappingEntry.getId( ) );
        daoUtil.setInt( 2, mappingEntry.getIdEntry( ) );
        daoUtil.setInt( 3, mappingEntry.getIdDirectory( ) );
        daoUtil.setBoolean( 4, mappingEntry.getIsActive( ) );
        daoUtil.setInt( 5, mappingEntry.getId( ) );

        daoUtil.executeUpdate( );
        daoUtil.free( );
    }

    /**
     * {@inheritDoc }
     */
    @Override
    public Collection<MappingEntry> selectMappingEntrysList( Plugin plugin )
    {
        Collection<MappingEntry> mappingEntryList = new ArrayList<MappingEntry>( );
        DAOUtil daoUtil = new DAOUtil( SQL_QUERY_SELECTALL, plugin );
        daoUtil.executeQuery( );

        while ( daoUtil.next( ) )
        {
            MappingEntry mappingEntry = new MappingEntry( );

            mappingEntry.setId( daoUtil.getInt( 1 ) );
            mappingEntry.setIdEntry( daoUtil.getInt( 2 ) );
            mappingEntry.setIdDirectory( daoUtil.getInt( 3 ) );
            mappingEntry.setIsActive( daoUtil.getBoolean( 4 ) );

            mappingEntryList.add( mappingEntry );
        }

        daoUtil.free( );
        return mappingEntryList;
    }

    /**
     * {@inheritDoc }
     */
    @Override
    public Collection<Integer> selectIdMappingEntrysList( Plugin plugin )
    {
        Collection<Integer> mappingEntryList = new ArrayList<Integer>( );
        DAOUtil daoUtil = new DAOUtil( SQL_QUERY_SELECTALL_ID, plugin );
        daoUtil.executeQuery( );

        while ( daoUtil.next( ) )
        {
            mappingEntryList.add( daoUtil.getInt( 1 ) );
        }

        daoUtil.free( );
        return mappingEntryList;
    }
}
