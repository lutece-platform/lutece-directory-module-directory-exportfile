/*
 * Copyright (c) 2002-2015, Mairie de Paris
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

package fr.paris.lutece.plugins.directory.modules.exportfile.web;

import fr.paris.lutece.plugins.directory.modules.exportfile.business.MappingEntry;
import fr.paris.lutece.plugins.directory.modules.exportfile.business.MappingEntryHome;
import fr.paris.lutece.plugins.directory.modules.exportfile.service.DirectoryService;
import fr.paris.lutece.portal.service.message.AdminMessage;
import fr.paris.lutece.portal.service.message.AdminMessageService;
import fr.paris.lutece.portal.util.mvc.admin.annotations.Controller;
import fr.paris.lutece.portal.util.mvc.commons.annotations.Action;
import fr.paris.lutece.portal.util.mvc.commons.annotations.View;
import fr.paris.lutece.util.url.UrlItem;

import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang.StringUtils;

/**
 * This class provides the user interface to manage MappingEntry features ( manage, create, modify, remove )
 */
@Controller( controllerJsp = "ManageMappingEntry.jsp", controllerPath = "jsp/admin/plugins/directory/modules/exportfile/", right = "EXPORTFILE_MANAGEMENT" )
public class MappingEntryJspBean extends ManageExportfileJspBean
{

    // //////////////////////////////////////////////////////////////////////////
    // Constants

    // templates
    private static final String TEMPLATE_MANAGE_MAPPINGENTRYS = "/admin/plugins/directory/modules/exportfile/manage_mappingentry.html";
    private static final String TEMPLATE_CREATE_MAPPINGENTRY = "/admin/plugins/directory/modules/exportfile/create_mappingentry.html";
    private static final String TEMPLATE_MODIFY_MAPPINGENTRY = "/admin/plugins/directory/modules/exportfile/modify_mappingentry.html";

    // Parameters
    private static final String PARAMETER_ID_MAPPINGENTRY = "id";
    private static final String PARAMETER_ID_DIRECTORY = "idDirectory";

    // Properties for page titles
    private static final String PROPERTY_PAGE_TITLE_MANAGE_MAPPINGENTRYS = "module.directory.exportfile.manage_mappingentrys.pageTitle";
    private static final String PROPERTY_PAGE_TITLE_MODIFY_MAPPINGENTRY = "module.directory.exportfile.modify_mappingentry.pageTitle";
    private static final String PROPERTY_PAGE_TITLE_CREATE_MAPPINGENTRY = "module.directory.exportfile.create_mappingentry.pageTitle";

    // Markers
    private static final String MARK_MAPPINGENTRY_LIST = "mappingentry_list";
    private static final String MARK_MAPPINGENTRY = "mappingentry";

    private static final String JSP_MANAGE_MAPPINGENTRYS = "jsp/admin/plugins/directory/modules/exportfile/ManageMappingEntry.jsp";

    // Properties
    private static final String MESSAGE_CONFIRM_REMOVE_MAPPINGENTRY = "module.directory.exportfile.message.confirmRemoveMappingEntry";
    private static final String PROPERTY_DEFAULT_LIST_MAPPINGENTRY_PER_PAGE = "module.directory.exportfile.listMappingEntrys.itemsPerPage";

    private static final String VALIDATION_ATTRIBUTES_PREFIX = "module.directory.exportfile.model.entity.mappingentry.attribute.";

    // Views
    private static final String VIEW_MANAGE_MAPPINGENTRYS = "manageMappingEntrys";
    private static final String VIEW_CREATE_MAPPINGENTRY = "createMappingEntry";
    private static final String VIEW_MODIFY_MAPPINGENTRY = "modifyMappingEntry";

    // Actions
    private static final String ACTION_CREATE_MAPPINGENTRY = "createMappingEntry";
    private static final String ACTION_MODIFY_MAPPINGENTRY = "modifyMappingEntry";
    private static final String ACTION_REMOVE_MAPPINGENTRY = "removeMappingEntry";
    private static final String ACTION_CONFIRM_REMOVE_MAPPINGENTRY = "confirmRemoveMappingEntry";

    // Infos
    private static final String INFO_MAPPINGENTRY_CREATED = "module.directory.exportfile.info.mappingentry.created";
    private static final String INFO_MAPPINGENTRY_UPDATED = "module.directory.exportfile.info.mappingentry.updated";
    private static final String INFO_MAPPINGENTRY_REMOVED = "module.directory.exportfile.info.mappingentry.removed";

    public static final String MARK_DIRECTORY_LIST = "list_directory";
    public static final String MARK_LIST_ENTRIES = "list_entries";

    // Session variable to store working values
    private MappingEntry _mappingentry;
    private DirectoryService _directoryService = DirectoryService.getService( );

    @View( value = VIEW_MANAGE_MAPPINGENTRYS, defaultView = true )
    public String getManageMappingEntrys( HttpServletRequest request )
    {
        _mappingentry = null;
        List<MappingEntry> listMappingEntrys = (List<MappingEntry>) MappingEntryHome.getMappingEntrysList( );
        Map<String, Object> model = getPaginatedListModel( request, MARK_MAPPINGENTRY_LIST, listMappingEntrys, JSP_MANAGE_MAPPINGENTRYS );

        return getPage( PROPERTY_PAGE_TITLE_MANAGE_MAPPINGENTRYS, TEMPLATE_MANAGE_MAPPINGENTRYS, model );
    }

    /**
     * Returns the form to create a mappingentry
     *
     * @param request
     *            The Http request
     * @return the html code of the mappingentry form
     */
    @View( VIEW_CREATE_MAPPINGENTRY )
    public String getCreateMappingEntry( HttpServletRequest request )
    {
        _mappingentry = ( _mappingentry != null ) ? _mappingentry : new MappingEntry( );
        String strIdDirectory = request.getParameter( PARAMETER_ID_DIRECTORY );
        int nIdDirectory = -1;

        if ( strIdDirectory != null && StringUtils.isNotEmpty( strIdDirectory ) )
        {
            nIdDirectory = Integer.parseInt( strIdDirectory );
            _mappingentry.setIdDirectory( nIdDirectory );
        }

        Map<String, Object> model = getModel( );
        model.put( MARK_MAPPINGENTRY, _mappingentry );
        model.put( MARK_DIRECTORY_LIST, _directoryService.getListDirectories( ) );
        model.put( MARK_LIST_ENTRIES, _directoryService.getListEntries( nIdDirectory, request ) );

        return getPage( PROPERTY_PAGE_TITLE_CREATE_MAPPINGENTRY, TEMPLATE_CREATE_MAPPINGENTRY, model );
    }

    /**
     * Process the data capture form of a new mappingentry
     *
     * @param request
     *            The Http Request
     * @return The Jsp URL of the process result
     */
    @Action( ACTION_CREATE_MAPPINGENTRY )
    public String doCreateMappingEntry( HttpServletRequest request )
    {
        populate( _mappingentry, request );

        // Check constraints
        if ( !validateBean( _mappingentry, VALIDATION_ATTRIBUTES_PREFIX ) )
        {
            return redirectView( request, VIEW_CREATE_MAPPINGENTRY );
        }

        MappingEntryHome.create( _mappingentry );
        addInfo( INFO_MAPPINGENTRY_CREATED, getLocale( ) );

        return redirectView( request, VIEW_MANAGE_MAPPINGENTRYS );
    }

    /**
     * Manages the removal form of a mappingentry whose identifier is in the http request
     *
     * @param request
     *            The Http request
     * @return the html code to confirm
     */
    @Action( ACTION_CONFIRM_REMOVE_MAPPINGENTRY )
    public String getConfirmRemoveMappingEntry( HttpServletRequest request )
    {
        int nId = Integer.parseInt( request.getParameter( PARAMETER_ID_MAPPINGENTRY ) );
        UrlItem url = new UrlItem( getActionUrl( ACTION_REMOVE_MAPPINGENTRY ) );
        url.addParameter( PARAMETER_ID_MAPPINGENTRY, nId );

        String strMessageUrl = AdminMessageService.getMessageUrl( request, MESSAGE_CONFIRM_REMOVE_MAPPINGENTRY, url.getUrl( ), AdminMessage.TYPE_CONFIRMATION );

        return redirect( request, strMessageUrl );
    }

    /**
     * Handles the removal form of a mappingentry
     *
     * @param request
     *            The Http request
     * @return the jsp URL to display the form to manage mappingentrys
     */
    @Action( ACTION_REMOVE_MAPPINGENTRY )
    public String doRemoveMappingEntry( HttpServletRequest request )
    {
        int nId = Integer.parseInt( request.getParameter( PARAMETER_ID_MAPPINGENTRY ) );
        MappingEntryHome.remove( nId );
        addInfo( INFO_MAPPINGENTRY_REMOVED, getLocale( ) );

        return redirectView( request, VIEW_MANAGE_MAPPINGENTRYS );
    }

    /**
     * Returns the form to update info about a mappingentry
     *
     * @param request
     *            The Http request
     * @return The HTML form to update info
     */
    @View( VIEW_MODIFY_MAPPINGENTRY )
    public String getModifyMappingEntry( HttpServletRequest request )
    {
        int nId = Integer.parseInt( request.getParameter( PARAMETER_ID_MAPPINGENTRY ) );
        String strIdDirectory = request.getParameter( PARAMETER_ID_DIRECTORY );
        int nIdDirectory = -1;

        if ( _mappingentry == null || ( _mappingentry.getId( ) != nId ) )
        {
            _mappingentry = MappingEntryHome.findByPrimaryKey( nId );
            nIdDirectory = _mappingentry.getIdDirectory( );
        }
        if ( strIdDirectory != null && StringUtils.isNotEmpty( strIdDirectory ) && StringUtils.isNotBlank( strIdDirectory ) )
        {

            nIdDirectory = Integer.parseInt( strIdDirectory );
        }

        Map<String, Object> model = getModel( );
        model.put( MARK_MAPPINGENTRY, _mappingentry );
        model.put( MARK_DIRECTORY_LIST, _directoryService.getListDirectories( ) );
        model.put( MARK_LIST_ENTRIES, _directoryService.getListEntries( nIdDirectory, request ) );

        return getPage( PROPERTY_PAGE_TITLE_MODIFY_MAPPINGENTRY, TEMPLATE_MODIFY_MAPPINGENTRY, model );
    }

    /**
     * Process the change form of a mappingentry
     *
     * @param request
     *            The Http request
     * @return The Jsp URL of the process result
     */
    @Action( ACTION_MODIFY_MAPPINGENTRY )
    public String doModifyMappingEntry( HttpServletRequest request )
    {
        populate( _mappingentry, request );

        // Check constraints
        if ( !validateBean( _mappingentry, VALIDATION_ATTRIBUTES_PREFIX ) )
        {
            return redirect( request, VIEW_MODIFY_MAPPINGENTRY, PARAMETER_ID_MAPPINGENTRY, _mappingentry.getId( ) );
        }

        MappingEntryHome.update( _mappingentry );
        addInfo( INFO_MAPPINGENTRY_UPDATED, getLocale( ) );

        return redirectView( request, VIEW_MANAGE_MAPPINGENTRYS );
    }
}
