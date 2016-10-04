<%@ page errorPage="../../../../ErrorPage.jsp" %>

<jsp:include page="../../../../AdminHeader.jsp" />

<jsp:useBean id="manageexportfile" scope="session" class="fr.paris.lutece.plugins.directory.modules.exportfile.web.ManageExportfileJspBean" />

<% manageexportfile.init( request, manageexportfile.RIGHT_MANAGEEXPORTFILE ); %>
<%= manageexportfile.getManageExportfileHome ( request ) %>


<%@ include file="../../../../AdminFooter.jsp" %>