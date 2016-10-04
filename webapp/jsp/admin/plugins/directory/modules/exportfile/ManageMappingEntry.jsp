<jsp:useBean id="manageexportfileMappingEntry" scope="session" class="fr.paris.lutece.plugins.directory.modules.exportfile.web.MappingEntryJspBean" />
<% String strContent = manageexportfileMappingEntry.processController ( request , response ); %>

<%@ page errorPage="../../../../ErrorPage.jsp" %>
<jsp:include page="../../../../AdminHeader.jsp" />

<%= strContent %>

<%@ include file="../../../../AdminFooter.jsp" %>