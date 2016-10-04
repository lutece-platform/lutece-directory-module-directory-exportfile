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

import fr.paris.lutece.plugins.directory.modules.exportfile.business.File;
import fr.paris.lutece.plugins.directory.modules.exportfile.business.FileHome;
import fr.paris.lutece.portal.service.message.AdminMessage;
import fr.paris.lutece.portal.service.message.AdminMessageService;
import fr.paris.lutece.portal.util.mvc.admin.annotations.Controller;
import fr.paris.lutece.portal.util.mvc.commons.annotations.Action;
import fr.paris.lutece.portal.util.mvc.commons.annotations.View;
import fr.paris.lutece.util.url.UrlItem;

import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

/**
 * This class provides the user interface to manage File features ( manage, create, modify, remove )
 */
@Controller( controllerJsp = "ManageFiles.jsp", controllerPath = "jsp/admin/plugins/directory/modules/exportfile/", right = "EXPORTFILE_MANAGEMENT" )
public class FileJspBean extends ManageExportfileJspBean
{

    // //////////////////////////////////////////////////////////////////////////
    // Constants

    // templates
    private static final String TEMPLATE_MANAGE_FILES = "/admin/plugins/directory/modules/exportfile/manage_files.html";

    // Parameters
    private static final String PARAMETER_ID_FILE = "id";

    // Properties for page titles
    private static final String PROPERTY_PAGE_TITLE_MANAGE_FILES = "module.directory.exportfile.manage_files.pageTitle";

    // Markers
    private static final String MARK_FILE_LIST = "file_list";

    private static final String JSP_MANAGE_FILES = "jsp/admin/plugins/directory/modules/exportfile/ManageFiles.jsp";

    // Properties
    private static final String MESSAGE_CONFIRM_REMOVE_FILE = "module.directory.exportfile.message.confirmRemoveFile";

    // Views
    private static final String VIEW_MANAGE_FILES = "manageFiles";

    // Actions

    private static final String ACTION_REMOVE_FILE = "removeFile";
    private static final String ACTION_CONFIRM_REMOVE_FILE = "confirmRemoveFile";

    // Infos

    private static final String INFO_FILE_REMOVED = "module.directory.exportfile.info.file.removed";

    // Session variable to store working values
    private File _file;

    @View( value = VIEW_MANAGE_FILES, defaultView = true )
    public String getManageFiles( HttpServletRequest request )
    {
        _file = null;
        List<File> listFiles = (List<File>) FileHome.getFilesList( );
        Map<String, Object> model = getPaginatedListModel( request, MARK_FILE_LIST, listFiles, JSP_MANAGE_FILES );

        return getPage( PROPERTY_PAGE_TITLE_MANAGE_FILES, TEMPLATE_MANAGE_FILES, model );
    }

    /**
     * Manages the removal form of a file whose identifier is in the http request
     *
     * @param request
     *            The Http request
     * @return the html code to confirm
     */
    @Action( ACTION_CONFIRM_REMOVE_FILE )
    public String getConfirmRemoveFile( HttpServletRequest request )
    {
        int nId = Integer.parseInt( request.getParameter( PARAMETER_ID_FILE ) );
        UrlItem url = new UrlItem( getActionUrl( ACTION_REMOVE_FILE ) );
        url.addParameter( PARAMETER_ID_FILE, nId );

        String strMessageUrl = AdminMessageService.getMessageUrl( request, MESSAGE_CONFIRM_REMOVE_FILE, url.getUrl( ), AdminMessage.TYPE_CONFIRMATION );

        return redirect( request, strMessageUrl );
    }

    /**
     * Handles the removal form of a file
     *
     * @param request
     *            The Http request
     * @return the jsp URL to display the form to manage files
     */
    @Action( ACTION_REMOVE_FILE )
    public String doRemoveFile( HttpServletRequest request )
    {
        int nId = Integer.parseInt( request.getParameter( PARAMETER_ID_FILE ) );
        FileHome.remove( nId );
        addInfo( INFO_FILE_REMOVED, getLocale( ) );

        return redirectView( request, VIEW_MANAGE_FILES );
    }

}
